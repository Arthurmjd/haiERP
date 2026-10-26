# =============================================================
# HaiERP 一键启动：MySQL -> Redis -> 后端 -> 前端
# 由 start-all.bat 双击调用，也可在 PowerShell 中直接运行
# =============================================================

try {
    . "$PSScriptRoot\_common.ps1"

    Write-Banner
    Import-HaiErpEnv -AllowPrompt | Out-Null

    # ---------- 1. 依赖工具 ----------
    $missing = @()
    if (-not (Resolve-Maven)) { $missing += 'Maven (mvn)  — 需要 3.9+，并把 bin 加入 PATH' }
    if (-not (Find-Executable -Name 'java.exe' -FallbackPaths @()) -and -not $env:JAVA_HOME) {
        $missing += 'JDK 21 (java) — 需要配置 JAVA_HOME 或把 bin 加入 PATH'
    }
    if (-not (Resolve-Node)) { $missing += 'Node.js (node) — 需要 22 LTS' }
    if (-not (Resolve-Npm))  { $missing += 'npm — 随 Node.js 安装' }
    if ($missing.Count -gt 0) {
        throw ("缺少必要工具：`n  - " + ($missing -join "`n  - "))
    }
    Write-Step '依赖工具检查通过（java / mvn / node / npm）' 'Green'

    # ---------- 2. 端口占用 ----------
    # 已经在跑就跳过对应组件，让脚本可以重复双击（幂等）
    $backendPort   = [int]$env:SERVER_PORT
    $launchBackend  = $true
    $launchFrontend = $true

    if (Test-Port -Port $backendPort) {
        $owner = Get-PortOwner -Port $backendPort
        Write-Step "后端端口 $backendPort 已被占用（$owner），跳过启动后端" 'Yellow'
        Write-Step "  如果这不是本项目的进程，请运行 scripts\stop-all.bat 释放端口" 'DarkGray'
        $launchBackend = $false
    }
    if (Test-Port -Port 5173) {
        $owner = Get-PortOwner -Port 5173
        Write-Step "前端端口 5173 已被占用（$owner），跳过启动前端" 'Yellow'
        Write-Step "  如果这不是本项目的进程，请运行 scripts\stop-all.bat 释放端口" 'DarkGray'
        $launchFrontend = $false
    }
    if ($launchBackend -and $launchFrontend) {
        Write-Step "端口 $backendPort / 5173 空闲" 'Green'
    }

    # ---------- 3. MySQL ----------
    $dbPort = [int]$env:DB_PORT
    if (Test-Port -TargetHost $env:DB_HOST -Port $dbPort) {
        Write-Step "MySQL 已在运行（$($env:DB_HOST):$dbPort）" 'Green'
    } else {
        $svc = Resolve-MySqlService
        if (-not $svc) {
            throw ("MySQL 端口 $dbPort 未监听，且本机没有找到 MySQL 服务。`n" +
                   "  请安装 MySQL 8.x 并启动，或手动启动后再运行本脚本。")
        }
        if ($svc.Status -eq 'Running') {
            throw ("MySQL 服务 $($svc.Name) 状态为 Running，但端口 $dbPort 不通。`n" +
                   "  请检查 my.ini 中的 port 是否与 env.local.ps1 的 DB_PORT 一致。")
        }
        Write-Step "正在启动 MySQL 服务 $($svc.Name) ..." 'Yellow'
        try {
            Start-Service -Name $svc.Name -ErrorAction Stop
        } catch {
            throw ("无法启动 MySQL 服务 $($svc.Name)，通常是权限不足。`n" +
                   "  方法一：右键 scripts\start-all.bat -> 以管理员身份运行`n" +
                   "  方法二：管理员 PowerShell 执行  net start $($svc.Name)")
        }
        if (-not (Wait-Port -TargetHost $env:DB_HOST -Port $dbPort -TimeoutSec 40)) {
            throw "MySQL 服务已启动，但端口 $dbPort 在 40 秒内仍未就绪。"
        }
        Write-Step 'MySQL 已启动' 'Green'
    }

    # ---------- 4. 数据库连通性 ----------
    $mysql = Resolve-MySqlClient
    if (-not $mysql) {
        Write-Step '未找到 mysql 客户端，跳过数据库连通性检查' 'Yellow'
    } else {
        $mysqlArgs = @(
            "--host=$($env:DB_HOST)",
            "--port=$($env:DB_PORT)",
            "--user=$($env:DB_USER)",
            '--connect-timeout=8',
            '-N', '-B',
            "--password=$($env:DB_PASSWORD)"
        )
        $query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='$($env:DB_NAME)';"
        $result = Invoke-NativeCapture -FilePath $mysql -Arguments ($mysqlArgs + @('-e', $query))

        if ($result.ExitCode -ne 0) {
            $detail = (Select-MeaningfulLine -Lines $result.Lines) -join ' | '
            if (-not $detail) { $detail = "mysql 退出码 $($result.ExitCode)" }
            throw ("MySQL 登录失败（用户 $($env:DB_USER)@$($env:DB_HOST):$($env:DB_PORT)）。`n" +
                   "  请编辑 $($script:HaiErpEnvFile) 修正 DB_PASSWORD，或删除该文件后重新运行以重新配置。`n" +
                   "  数据库返回：$detail")
        }

        $found = $false
        foreach ($line in (Select-MeaningfulLine -Lines $result.Lines)) {
            if ("$line".Trim() -eq $env:DB_NAME) { $found = $true }
        }
        if (-not $found) {
            throw ("数据库 $($env:DB_NAME) 不存在。请先在 MySQL 中初始化（见 README「首次建立演示库」）：`n`n" +
                   "  CREATE DATABASE $($env:DB_NAME) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`n" +
                   "  USE $($env:DB_NAME);`n" +
                   "  SOURCE sql/platform-schema.sql;`n" +
                   "  SOURCE sql/init_all.sql;`n" +
                   "  SOURCE sql/erp-permissions.sql;`n" +
                   "  SOURCE sql/platform-demo-users.sql;`n")
        }
        Write-Step "数据库连接正常（$($env:DB_NAME)）" 'Green'
    }

    # ---------- 5. Redis ----------
    $redisPort = [int]$env:REDIS_PORT
    $redisCli  = Resolve-RedisCli

    if (Test-Port -TargetHost $env:REDIS_HOST -Port $redisPort) {
        if (Test-RedisPing -Cli $redisCli) {
            Write-Step "Redis 已在运行（$($env:REDIS_HOST):$redisPort）" 'Green'
        } else {
            Write-Step "Redis 已在运行，但认证失败 —— 它可能设置了不同的密码，后端可能连不上" 'Yellow'
        }
    } else {
        $redisServer = Resolve-RedisServer
        if (-not $redisServer) {
            throw ("Redis 端口 $redisPort 未监听，且未找到 redis-server.exe。`n" +
                   "  请安装 Redis，或在 env.local.ps1 中设置 `$RedisHome 指向 Redis 安装目录。")
        }

        # redis.conf 里的 dir ./ 会指向程序目录（Program Files 无写权限），
        # 因此改用可写目录并显式传参，不加载 redis.conf
        $dataDir = Join-Path $env:LOCALAPPDATA 'HaiERP\redis'
        if (-not (Test-Path $dataDir)) {
            New-Item -ItemType Directory -Path $dataDir -Force | Out-Null
        }

        $redisArgs = @('--port', "$redisPort", '--bind', $env:REDIS_HOST, '--dir', $dataDir)
        if ($env:REDIS_PASSWORD) { $redisArgs += @('--requirepass', $env:REDIS_PASSWORD) }

        Write-Step "正在启动 Redis（$($env:REDIS_HOST):$redisPort）..." 'Yellow'
        Start-Process -FilePath $redisServer -ArgumentList $redisArgs `
            -WorkingDirectory $dataDir -WindowStyle Minimized | Out-Null

        if (-not (Wait-Port -TargetHost $env:REDIS_HOST -Port $redisPort -TimeoutSec 20)) {
            throw "Redis 启动失败（20 秒内端口未就绪）。可执行文件：$redisServer"
        }
        if (-not (Test-RedisPing -Cli $redisCli)) {
            throw "Redis 已监听 $redisPort，但 PING 失败。请检查 env.local.ps1 中的 REDIS_PASSWORD。"
        }
        Write-Step 'Redis 已启动' 'Green'
    }

    # ---------- 6. 打开后端 / 前端 ----------
    if ($launchBackend -or $launchFrontend) {
        Write-Step '正在打开开发窗口...' 'Yellow'
        Open-DevWindows -Backend $launchBackend -Frontend $launchFrontend
    } else {
        Write-Step '前后端均已在运行，无需重复启动' 'Green'
    }

    # ---------- 7. 汇总 ----------
    Start-Sleep -Seconds 2
    Write-Host ''
    Write-Host '  ----------------------------------------------' -ForegroundColor DarkGray
    Write-Host '   前端   http://127.0.0.1:5173' -ForegroundColor Green
    Write-Host "   后端   http://127.0.0.1:$backendPort" -ForegroundColor Green
    Write-Host "   接口文档 http://127.0.0.1:$backendPort/swagger-ui/index.html" -ForegroundColor DarkGray
    Write-Host '   演示账号 admin / Demo@2026!' -ForegroundColor Gray
    Write-Host '  ----------------------------------------------' -ForegroundColor DarkGray
    Write-Host '   首次构建后端需要 2-5 分钟，' -ForegroundColor DarkGray
    Write-Host '   后端窗口出现 Started MyFactoryAIApplication 即成功。' -ForegroundColor DarkGray
    Write-Host ''

    exit 0
} catch {
    Write-Host ''
    Write-Host "  [启动失败] $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ''
    exit 1
}

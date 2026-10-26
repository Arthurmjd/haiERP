# =============================================================
# HaiERP 脚本公共库
# 被 start-all / run-backend / run-frontend / watch-backend-log / stop-all 复用
# 注意：本文件由各脚本 dot-source，不要单独运行
# 兼容 Windows PowerShell 5.1
# =============================================================

$ErrorActionPreference = 'Stop'
try { [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false } catch { }

$script:HaiErpRoot    = Split-Path -Parent $PSScriptRoot
$script:HaiErpScripts = $PSScriptRoot
$script:HaiErpEnvFile = Join-Path $PSScriptRoot 'env.local.ps1'

# ---------------------- 输出 ----------------------

function Write-Step {
    param([string]$Message, [string]$Color = 'Gray')
    Write-Host ("  [{0}] {1}" -f (Get-Date -Format 'HH:mm:ss'), $Message) -ForegroundColor $Color
}

function Write-Banner {
    Write-Host ''
    Write-Host '  ==================================================' -ForegroundColor Cyan
    Write-Host '         HaiERP 一键启动  (MySQL / Redis / 前后端)' -ForegroundColor Cyan
    Write-Host '  ==================================================' -ForegroundColor Cyan
    Write-Host ''
}

# ---------------------- 端口 ----------------------

# 用 TCP 连接探测，不依赖 Get-NetTCPConnection，也不需要管理员权限
function Test-Port {
    param(
        [string]$TargetHost = '127.0.0.1',
        [int]$Port,
        [int]$TimeoutMs = 800
    )
    $client = New-Object System.Net.Sockets.TcpClient
    try {
        $iar = $client.BeginConnect($TargetHost, $Port, $null, $null)
        if (-not $iar.AsyncWaitHandle.WaitOne($TimeoutMs, $false)) { return $false }
        $client.EndConnect($iar)
        return $true
    } catch {
        return $false
    } finally {
        $client.Close()
    }
}

function Wait-Port {
    param(
        [string]$TargetHost = '127.0.0.1',
        [int]$Port,
        [int]$TimeoutSec = 30
    )
    $deadline = (Get-Date).AddSeconds($TimeoutSec)
    while ((Get-Date) -lt $deadline) {
        if (Test-Port -TargetHost $TargetHost -Port $Port) { return $true }
        Start-Sleep -Milliseconds 500
    }
    return $false
}

function Get-PortOwner {
    param([int]$Port)
    try {
        $conn = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction Stop | Select-Object -First 1
        if ($conn) {
            $proc = Get-Process -Id $conn.OwningProcess -ErrorAction SilentlyContinue
            if ($proc) { return ("{0} (PID {1})" -f $proc.ProcessName, $proc.Id) }
            return ("PID {0}" -f $conn.OwningProcess)
        }
    } catch { }
    return '未知进程'
}

function Get-PortPids {
    param([int]$Port)
    $result = @()
    try {
        $conns = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction Stop
        foreach ($c in $conns) { if ($result -notcontains $c.OwningProcess) { $result += $c.OwningProcess } }
    } catch { }
    return $result
}

# ---------------------- 外部工具定位 ----------------------

function Find-Executable {
    param([string]$Name, [string[]]$FallbackPaths)
    $cmd = Get-Command $Name -ErrorAction SilentlyContinue
    if ($cmd) { return $cmd.Source }
    foreach ($p in $FallbackPaths) {
        if ($p -and (Test-Path $p)) { return $p }
    }
    return $null
}

function Resolve-Maven {
    $paths = @()
    $paths += (Join-Path $env:ProgramFiles 'apache-maven-3.9.11-bin\apache-maven-3.9.11\bin\mvn.cmd')
    if ($env:ProgramFiles) {
        $dirs = Get-ChildItem -Path $env:ProgramFiles -Filter 'apache-maven-*' -Directory -ErrorAction SilentlyContinue
        foreach ($d in $dirs) { $paths += (Join-Path $d.FullName 'bin\mvn.cmd') }
    }
    $paths += 'C:\apache-maven\bin\mvn.cmd'
    return (Find-Executable -Name 'mvn.cmd' -FallbackPaths $paths)
}

function Resolve-Node {
    $paths = @()
    $paths += (Join-Path $env:ProgramFiles 'nodejs\node.exe')
    $paths += 'C:\nodejs\node.exe'
    return (Find-Executable -Name 'node.exe' -FallbackPaths $paths)
}

function Resolve-Npm {
    $paths = @()
    $paths += (Join-Path $env:ProgramFiles 'nodejs\npm.cmd')
    $paths += 'C:\nodejs\npm.cmd'
    return (Find-Executable -Name 'npm.cmd' -FallbackPaths $paths)
}

function Resolve-RedisServer {
    if ($script:RedisHome) {
        $p = Join-Path $script:RedisHome 'redis-server.exe'
        if (Test-Path $p) { return $p }
    }
    $paths = @()
    if ($env:ProgramFiles) {
        $dirs = Get-ChildItem -Path $env:ProgramFiles -Filter 'Redis*' -Directory -ErrorAction SilentlyContinue
        foreach ($d in $dirs) {
            $paths += (Join-Path $d.FullName 'redis-server.exe')
            $inner = Get-ChildItem -Path $d.FullName -Filter 'redis-server.exe' -File -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
            if ($inner) { $paths += $inner.FullName }
        }
    }
    $paths += 'C:\Redis\redis-server.exe'
    $paths += 'C:\redis\redis-server.exe'
    return (Find-Executable -Name 'redis-server.exe' -FallbackPaths $paths)
}

function Resolve-RedisCli {
    $server = Resolve-RedisServer
    if ($server) {
        $cli = Join-Path (Split-Path -Parent $server) 'redis-cli.exe'
        if (Test-Path $cli) { return $cli }
    }
    return (Find-Executable -Name 'redis-cli.exe' -FallbackPaths @('C:\Redis\redis-cli.exe'))
}

function Resolve-MySqlClient {
    if ($script:MySqlHome) {
        $p = Join-Path $script:MySqlHome 'mysql.exe'
        if (Test-Path $p) { return $p }
        $p = Join-Path $script:MySqlHome 'bin\mysql.exe'
        if (Test-Path $p) { return $p }
    }
    $paths = @()
    if ($env:ProgramFiles) {
        $dirs = Get-ChildItem -Path (Join-Path $env:ProgramFiles 'MySQL') -Directory -ErrorAction SilentlyContinue
        foreach ($d in $dirs) { $paths += (Join-Path $d.FullName 'bin\mysql.exe') }
    }
    return (Find-Executable -Name 'mysql.exe' -FallbackPaths $paths)
}

function Resolve-MySqlService {
    $svc = Get-Service -Name 'MySQL*' -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($svc) { return $svc }
    return $null
}

function Assert-JavaHome {
    if ($env:JAVA_HOME) {
        $javaExe = Join-Path $env:JAVA_HOME 'bin\java.exe'
        if (Test-Path $javaExe) { return }
    }
    $java = Find-Executable -Name 'java.exe' -FallbackPaths @()
    if (-not $java) {
        throw '未找到 java。请安装 JDK 21，并配置 JAVA_HOME 或把 java 加入 PATH。'
    }
    $env:JAVA_HOME = Split-Path -Parent (Split-Path -Parent $java)
    Write-Step "已自动推断 JAVA_HOME=$($env:JAVA_HOME)" 'DarkGray'
}

# ---------------------- 原生命令调用 ----------------------

# $ErrorActionPreference='Stop' 会把原生命令写到 stderr 的普通输出
# 当成终止性错误抛出（NativeCommandError），导致真实错误信息被吞掉。
# 统一用本函数调用外部命令：捕获 stdout+stderr，返回退出码。
function Invoke-NativeCapture {
    param(
        [Parameter(Mandatory = $true)][string]$FilePath,
        [string[]]$Arguments = @()
    )
    $previous = $ErrorActionPreference
    $ErrorActionPreference = 'Continue'
    try {
        $lines = @()
        & $FilePath @Arguments 2>&1 | ForEach-Object { $lines += "$_" }
        $exitCode = $LASTEXITCODE
        if ($null -eq $exitCode) { $exitCode = 0 }
        return [pscustomobject]@{ ExitCode = $exitCode; Lines = $lines }
    } finally {
        $ErrorActionPreference = $previous
    }
}

# 过滤掉 mysql / redis-cli 的密码提示等噪声
function Select-MeaningfulLine {
    param([string[]]$Lines)
    $noise = 'Using a password on the command line|Warning: Using a password'
    return @($Lines | Where-Object { $_ -and ($_ -notmatch $noise) })
}

# 校验 MySQL 账号密码能否登录；找不到客户端时视为通过（不阻塞）
function Test-MySqlLogin {
    param(
        [string]$DbHost,
        [string]$DbPort,
        [string]$DbUser,
        [string]$DbPassword
    )
    $mysql = Resolve-MySqlClient
    if (-not $mysql) { return $true }

    $mysqlArgs = @(
        "--host=$DbHost",
        "--port=$DbPort",
        "--user=$DbUser",
        '--connect-timeout=8',
        '-N', '-B',
        "--password=$DbPassword",
        '-e', 'SELECT 1;'
    )
    $result = Invoke-NativeCapture -FilePath $mysql -Arguments $mysqlArgs
    return ($result.ExitCode -eq 0)
}

# ---------------------- 本地配置 ----------------------

function New-HaiErpEnvFile {
    # HAIERP_NONINTERACTIVE=1 时完全不提问，全部取值来自环境变量或默认值（供脚本化安装使用）
    $nonInteractive = [bool][Environment]::GetEnvironmentVariable('HAIERP_NONINTERACTIVE')

    Write-Host ''
    Write-Host '  首次运行，需要生成本地配置 scripts\env.local.ps1' -ForegroundColor Cyan
    Write-Host '  （该文件保存密码，已被 .gitignore 忽略，不会提交到仓库）' -ForegroundColor DarkGray
    Write-Host ''

    $dbHost = '127.0.0.1'
    $dbPort = '3306'
    $dbName = 'hai_erp_demo'
    $dbUser = 'root'
    $dbPassword = ''
    $redisHost = '127.0.0.1'
    $redisPort = '6379'
    $redisPassword = ''

    if ($nonInteractive) {
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_DB_HOST');       if ($tmp) { $dbHost = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_DB_PORT');       if ($tmp) { $dbPort = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_DB_NAME');       if ($tmp) { $dbName = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_DB_USER');       if ($tmp) { $dbUser = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_DB_PASSWORD');   if ($null -ne $tmp) { $dbPassword = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_REDIS_HOST');    if ($tmp) { $redisHost = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_REDIS_PORT');    if ($tmp) { $redisPort = $tmp }
        $tmp = [Environment]::GetEnvironmentVariable('HAIERP_REDIS_PASSWORD');if ($null -ne $tmp) { $redisPassword = $tmp }
        Write-Host "  非交互模式：使用环境变量生成配置（MySQL $dbUser@${dbHost}:$dbPort/$dbName）" -ForegroundColor DarkGray
    } else {
        $tmp = Read-Host ("  MySQL 主机 [{0}]" -f $dbHost);   if ($tmp) { $dbHost = $tmp }
        $tmp = Read-Host ("  MySQL 端口 [{0}]" -f $dbPort);   if ($tmp) { $dbPort = $tmp }
        $tmp = Read-Host ("  数据库名 [{0}]" -f $dbName);     if ($tmp) { $dbName = $tmp }
        $tmp = Read-Host ("  MySQL 用户 [{0}]" -f $dbUser);   if ($tmp) { $dbUser = $tmp }

        $attempt = 0
        while ($true) {
            $attempt++
            $secure = Read-Host '  MySQL 密码（输入时不显示）' -AsSecureString
            if ($secure -and $secure.Length -gt 0) {
                $dbPassword = [System.Net.NetworkCredential]::new('', $secure).Password
            } else {
                $dbPassword = ''
            }

            if (Test-MySqlLogin -DbHost $dbHost -DbPort $dbPort -DbUser $dbUser -DbPassword $dbPassword) {
                Write-Host '  数据库连接验证通过。' -ForegroundColor Green
                break
            }

            Write-Host '  这个密码连不上 MySQL。' -ForegroundColor Red
            if ($attempt -ge 3) {
                Write-Host '  已重试 3 次，先按当前输入保存；稍后可直接编辑 env.local.ps1 修正。' -ForegroundColor Yellow
                break
            }
            $again = Read-Host '  重新输入密码？(Y/n)'
            if ($again -match '^[nN]') { break }
        }

        $tmp = Read-Host ("  Redis 主机 [{0}]" -f $redisHost); if ($tmp) { $redisHost = $tmp }
        $tmp = Read-Host ("  Redis 端口 [{0}]" -f $redisPort); if ($tmp) { $redisPort = $tmp }
        $tmp = Read-Host '  Redis 密码 [留空表示无密码]'
        if ($tmp) { $redisPassword = $tmp }
    }

    # 单引号字符串内需要转义单引号
    $dbPasswordEsc    = "$dbPassword".Replace("'", "''")
    $redisPasswordEsc = "$redisPassword".Replace("'", "''")
    $tokenSecret      = [guid]::NewGuid().ToString('N')

    $content = @"
# HaiERP 本地开发配置
# 自动生成于 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')
# 已被 .gitignore 忽略，请勿提交。字段说明见 env.local.example.ps1
`$env:DB_HOST = '$dbHost'
`$env:DB_PORT = '$dbPort'
`$env:DB_NAME = '$dbName'
`$env:DB_USER = '$dbUser'
`$env:DB_PASSWORD = '$dbPasswordEsc'
`$env:REDIS_HOST = '$redisHost'
`$env:REDIS_PORT = '$redisPort'
`$env:REDIS_PASSWORD = '$redisPasswordEsc'
`$env:TOKEN_SECRET = '$tokenSecret'
"@

    Set-Content -Path $script:HaiErpEnvFile -Value $content -Encoding UTF8
    Write-Host ''
    Write-Host "  已写入 $($script:HaiErpEnvFile)" -ForegroundColor Green
    Write-Host '  下次启动不再询问；需要修改时直接编辑该文件。' -ForegroundColor DarkGray
    Write-Host ''
}

function Import-HaiErpEnv {
    param([switch]$AllowPrompt)

    if (-not (Test-Path $script:HaiErpEnvFile)) {
        if (-not $AllowPrompt) {
            throw ("缺少本地配置 $($script:HaiErpEnvFile)`n" +
                   "  该文件被 .gitignore 忽略，需要首次生成。`n" +
                   "  请运行 scripts\start-all.bat，或复制 scripts\env.local.example.ps1 为 env.local.ps1 后填写。")
        }
        New-HaiErpEnvFile
    }

    . $script:HaiErpEnvFile

    if (-not $env:DB_HOST)    { $env:DB_HOST = '127.0.0.1' }
    if (-not $env:DB_PORT)    { $env:DB_PORT = '3306' }
    if (-not $env:DB_NAME)    { $env:DB_NAME = 'hai_erp_demo' }
    if (-not $env:DB_USER)    { $env:DB_USER = 'root' }
    if (-not $env:REDIS_HOST) { $env:REDIS_HOST = '127.0.0.1' }
    if (-not $env:REDIS_PORT) { $env:REDIS_PORT = '6379' }

    # 后端 token.secret 必填，缺失会导致启动失败，这里自动补一个并持久化
    if (-not $env:TOKEN_SECRET) {
        $env:TOKEN_SECRET = [guid]::NewGuid().ToString('N')
        Add-Content -Path $script:HaiErpEnvFile -Value "`$env:TOKEN_SECRET = '$($env:TOKEN_SECRET)'" -Encoding UTF8
        Write-Step '已自动生成 TOKEN_SECRET 并写入 env.local.ps1' 'DarkGray'
    }

    if ($RedisHome) { $script:RedisHome = $RedisHome }
    if ($MySqlHome) { $script:MySqlHome = $MySqlHome }

    return $true
}

# ---------------------- Redis 检测 ----------------------

function Test-RedisPing {
    param([string]$Cli)
    if (-not $Cli -or -not (Test-Path $Cli)) { return $true }   # 没有 cli 时不阻塞启动

    $redisArgs = @('-h', $env:REDIS_HOST, '-p', $env:REDIS_PORT)
    # 用 REDISCLI_AUTH 传密码，避免密码出现在进程命令行里
    $previousAuth = $env:REDISCLI_AUTH
    if ($env:REDIS_PASSWORD) { $env:REDISCLI_AUTH = $env:REDIS_PASSWORD }

    try {
        $result = Invoke-NativeCapture -FilePath $Cli -Arguments ($redisArgs + @('ping'))
        if ($result.ExitCode -ne 0) { return $false }
        return (($result.Lines | Out-String) -match 'PONG')
    } catch {
        return $false
    } finally {
        if ($null -eq $previousAuth) {
            Remove-Item Env:\REDISCLI_AUTH -ErrorAction SilentlyContinue
        } else {
            $env:REDISCLI_AUTH = $previousAuth
        }
    }
}

# ---------------------- 开发窗口 ----------------------

function Open-DevWindows {
    $backendScript  = Join-Path $script:HaiErpScripts 'run-backend.ps1'
    $frontendScript = Join-Path $script:HaiErpScripts 'run-frontend.ps1'
    $root           = $script:HaiErpRoot

    $wt = Get-Command wt.exe -ErrorAction SilentlyContinue
    if ($wt) {
        # 一次性提交，避免多次调用 wt 时 split-pane 目标窗口错乱
        $wtArgs = @(
            '-w', '-1',
            'new-tab', '--title', 'HaiERP-Backend', '-d', $root,
            'powershell', '-NoProfile', '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', $backendScript,
            ';',
            'split-pane', '-V', '--size', '0.5', '--title', 'HaiERP-Frontend', '-d', $root,
            'powershell', '-NoProfile', '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', $frontendScript
        )
        try {
            & $wt.Source @wtArgs
            if ($LASTEXITCODE -eq 0) { return }
            Write-Step "Windows Terminal 返回 $LASTEXITCODE，改用独立窗口" 'Yellow'
        } catch {
            Write-Step 'Windows Terminal 调用失败，改用独立窗口' 'Yellow'
        }
    }

    Start-Process -FilePath 'powershell.exe' `
        -ArgumentList @('-NoProfile', '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', $backendScript) `
        -WorkingDirectory $root | Out-Null
    Start-Process -FilePath 'powershell.exe' `
        -ArgumentList @('-NoProfile', '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', $frontendScript) `
        -WorkingDirectory $root | Out-Null
}


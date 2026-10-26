# =============================================================
# 启动后端；源码有变更时自动增量构建
# 由 start-all 在独立窗口中调用
# =============================================================

try {
    . "$PSScriptRoot\_common.ps1"
    Import-HaiErpEnv | Out-Null

    # 运行器脚本要让 mvn / java 的输出原样流到控制台，
    # 不能把它们的 stderr 当成终止性错误
    $ErrorActionPreference = 'Continue'

    $backendDir = Join-Path $script:HaiErpRoot 'backend'
    Set-Location $backendDir

    Assert-JavaHome

    $mvn = Resolve-Maven
    if (-not $mvn) { throw '未找到 mvn。请安装 Maven 3.9+ 并把 bin 加入 PATH。' }

    $jar = Join-Path $backendDir 'myfactoryai-admin\target\myfactoryai-admin.jar'

    # 增量判断：jar 不存在，或存在比 jar 更新的源码文件
    $needBuild = $true
    if (Test-Path $jar) {
        $jarTime = (Get-Item $jar).LastWriteTimeUtc
        $newer = Get-ChildItem -Path $backendDir -Recurse -File -ErrorAction SilentlyContinue |
            Where-Object {
                $_.FullName -notmatch '\\target\\' -and
                $_.FullName -notmatch '\\logs\\' -and
                $_.LastWriteTimeUtc -gt $jarTime -and
                ($_.Extension -in '.java', '.xml', '.yml', '.yaml', '.properties')
            } | Select-Object -First 1
        if (-not $newer) { $needBuild = $false }
    }

    if ($needBuild) {
        Write-Host ''
        Write-Host '检测到源码变更（或首次运行），正在构建后端...' -ForegroundColor Yellow
        Write-Host '首次构建需要 2-5 分钟，请耐心等待。' -ForegroundColor DarkGray
        Write-Host ''
        & $mvn -DskipTests package
        if ($LASTEXITCODE -ne 0) {
            Write-Host ''
            Write-Host '后端构建失败，请查看上方 Maven 输出。' -ForegroundColor Red
            Write-Host '常见原因：JDK 版本不是 21，或依赖下载失败。' -ForegroundColor DarkGray
            return
        }
        Write-Host ''
        Write-Host '构建成功。' -ForegroundColor Green
    } else {
        Write-Host '未检测到源码变更，跳过构建。' -ForegroundColor DarkGray
    }

    if (-not (Test-Path $jar)) { throw "构建产物不存在：$jar" }

    $backendPort = if ($env:SERVER_PORT) { $env:SERVER_PORT } else { '8080' }

    Write-Host ''
    Write-Host "正在启动后端 http://127.0.0.1:$backendPort ..." -ForegroundColor Green
    Write-Host "  数据库：$env:DB_NAME @ $($env:DB_HOST):$($env:DB_PORT)（用户 $env:DB_USER）" -ForegroundColor DarkGray
    Write-Host '看到 Started MyFactoryAIApplication 即启动成功。' -ForegroundColor DarkGray
    Write-Host ''

    & java -jar $jar "--server.port=$backendPort" --server.address=127.0.0.1

    Write-Host ''
    Write-Host '后端进程已退出。' -ForegroundColor Yellow
} catch {
    Write-Host ''
    Write-Host "[后端启动失败] $($_.Exception.Message)" -ForegroundColor Red
}

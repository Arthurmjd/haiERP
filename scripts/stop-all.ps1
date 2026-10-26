# =============================================================
# 停止 HaiERP 开发进程（后端 8080 / 前端 5173，可选 Redis）
# 不会停止 MySQL（它通常是随系统启动的服务）
# =============================================================

try {
    . "$PSScriptRoot\_common.ps1"

    if (Test-Path $script:HaiErpEnvFile) {
        Import-HaiErpEnv | Out-Null
    } else {
        $env:REDIS_PORT = '6379'
    }
    if (-not $env:SERVER_PORT) { $env:SERVER_PORT = '8080' }

    Write-Host ''
    Write-Host '  HaiERP 停止脚本' -ForegroundColor Cyan
    Write-Host ''

    $targets = @()

    foreach ($port in @([int]$env:SERVER_PORT, 5173)) {
        $pids = Get-PortPids -Port $port
        foreach ($procId in $pids) {
            $proc = Get-Process -Id $procId -ErrorAction SilentlyContinue
            if ($proc) {
                $targets += [pscustomobject]@{ Port = $port; Id = $procId; Name = $proc.ProcessName }
            }
        }
    }

    $withRedis = $false
    $answer = Read-Host '  同时停止 Redis？(y/N)'
    if ($answer -match '^[yY]') { $withRedis = $true }

    if ($withRedis) {
        $redisPort = [int]$env:REDIS_PORT
        $pids = Get-PortPids -Port $redisPort
        foreach ($procId in $pids) {
            $proc = Get-Process -Id $procId -ErrorAction SilentlyContinue
            if ($proc) {
                $targets += [pscustomobject]@{ Port = $redisPort; Id = $procId; Name = $proc.ProcessName }
            }
        }
    }

    if ($targets.Count -eq 0) {
        Write-Host ''
        Write-Host '  没有需要停止的进程。' -ForegroundColor Green
        Write-Host ''
        exit 0
    }

    Write-Host ''
    Write-Host '  将停止以下进程：' -ForegroundColor Yellow
    foreach ($t in $targets) {
        Write-Host ("    端口 {0,-5} {1} (PID {2})" -f $t.Port, $t.Name, $t.Id)
    }
    Write-Host ''

    $confirm = Read-Host '  确认停止？(Y/n)'
    if ($confirm -and $confirm -notmatch '^[yY]') {
        Write-Host '  已取消。' -ForegroundColor DarkGray
        exit 0
    }

    foreach ($t in $targets) {
        try {
            Stop-Process -Id $t.Id -Force -ErrorAction Stop
            Write-Host ("  已停止 {0} (PID {1})" -f $t.Name, $t.Id) -ForegroundColor Green
        } catch {
            Write-Host ("  无法停止 PID {0}：{1}" -f $t.Id, $_.Exception.Message) -ForegroundColor Red
        }
    }

    Write-Host ''
    Write-Host '  完成。MySQL 服务未受影响。' -ForegroundColor DarkGray
    Write-Host ''
    exit 0
} catch {
    Write-Host ''
    Write-Host "  [停止失败] $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ''
    exit 1
}

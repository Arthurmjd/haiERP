# HaiERP 一键启动：Redis + 后端 + 前端 + 后端日志监听
$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root
. "$PSScriptRoot\env.local.ps1"

function Test-Port([int]$Port) {
    return [bool](Get-NetTCPConnection -State Listen -LocalPort $Port -ErrorAction SilentlyContinue)
}

Write-Host '==========================================' -ForegroundColor Cyan
Write-Host '            HaiERP 一键启动' -ForegroundColor Cyan
Write-Host '==========================================' -ForegroundColor Cyan

if (Test-Port 8080) {
    Write-Host '端口 8080 已被占用，后端可能已在运行，请先关闭它。' -ForegroundColor Red
    Read-Host '按回车退出'
    exit 1
}
if (Test-Port 5173) {
    Write-Host '端口 5173 已被占用，前端可能已在运行，请先关闭它。' -ForegroundColor Red
    Read-Host '按回车退出'
    exit 1
}

# ---- Redis ----
if (Test-Port 6379) {
    & "$RedisHome\redis-cli.exe" -a $env:REDIS_PASSWORD --no-auth-warning ping 2>$null | Out-Null
    if ($LASTEXITCODE -eq 0) {
        Write-Host 'Redis 已在运行，密码校验通过。' -ForegroundColor Green
    } else {
        Write-Host "Redis 已在运行，但密码不是 $($env:REDIS_PASSWORD)，后端可能连接失败。" -ForegroundColor Yellow
    }
} else {
    Write-Host '正在启动 Redis...' -ForegroundColor Yellow
    Start-Process -FilePath "$RedisHome\redis-server.exe" `
        -ArgumentList "`"$RedisHome\redis.windows.conf`"", '--requirepass', $env:REDIS_PASSWORD `
        -WorkingDirectory $RedisHome -WindowStyle Minimized
    Start-Sleep -Seconds 1
    if (Test-Port 6379) {
        Write-Host "Redis 已启动（6379，密码 $($env:REDIS_PASSWORD)）。" -ForegroundColor Green
    } else {
        Write-Host "Redis 启动失败，请检查路径：$RedisHome" -ForegroundColor Red
    }
}

# ---- 打开窗口 ----
Write-Host '正在打开后端 / 前端 / 日志窗口...' -ForegroundColor Yellow

$wt = Get-Command wt.exe -ErrorAction SilentlyContinue
if ($wt) {
    & wt.exe -w -1 new-tab --title 'HaiERP-Backend' -d $Root powershell -NoExit -ExecutionPolicy Bypass -File 'scripts\run-backend.ps1'
    Start-Sleep -Milliseconds 800
    & wt.exe -w 0 split-pane -H --size 0.32 --title 'Backend Log' -d $Root powershell -NoExit -ExecutionPolicy Bypass -File 'scripts\watch-backend-log.ps1'
    Start-Sleep -Milliseconds 400
    & wt.exe -w 0 move-focus up
    Start-Sleep -Milliseconds 200
    & wt.exe -w 0 split-pane -V --size 0.5 --title 'HaiERP-Frontend' -d $Root powershell -NoExit -ExecutionPolicy Bypass -File 'scripts\run-frontend.ps1'
} else {
    Start-Process powershell -ArgumentList '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', "$PSScriptRoot\run-backend.ps1"
    Start-Process powershell -ArgumentList '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', "$PSScriptRoot\run-frontend.ps1"
    Start-Process powershell -ArgumentList '-NoExit', '-ExecutionPolicy', 'Bypass', '-File', "$PSScriptRoot\watch-backend-log.ps1"
}

Start-Sleep -Seconds 2
Write-Host ''
Write-Host '前端： http://127.0.0.1:5173' -ForegroundColor Green
Write-Host '后端： http://127.0.0.1:8080' -ForegroundColor Green
Write-Host '演示账号：admin / Demo@2026!' -ForegroundColor Gray
Write-Host '首次构建后端需要几分钟，日志出现 Started MyFactoryAIApplication 即启动成功。' -ForegroundColor Gray

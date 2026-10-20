# 实时监听后端日志
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false
$Root = Split-Path -Parent $PSScriptRoot
$log = Join-Path $Root 'backend\logs\sys-info.log'

Write-Host "监听后端日志：$log" -ForegroundColor Cyan
Write-Host '（等待后端写出第一行日志…）' -ForegroundColor DarkGray

while (-not (Test-Path $log)) { Start-Sleep -Milliseconds 500 }
Get-Content -Path $log -Wait -Tail 100 -Encoding UTF8

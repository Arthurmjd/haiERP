# =============================================================
# 实时监听后端日志（可选工具）
# 后端日志由 logback 写到 backend\logs\sys-info.log
# =============================================================

. "$PSScriptRoot\_common.ps1"

$logPath = Join-Path $script:HaiErpRoot 'backend\logs\sys-info.log'

Write-Host ''
Write-Host "监听后端日志：$logPath" -ForegroundColor Cyan
Write-Host '按 Ctrl+C 退出监听（不会停止后端）。' -ForegroundColor DarkGray
Write-Host ''

# 等日志文件出现，最多 5 分钟，期间给出进度提示
$deadline = (Get-Date).AddMinutes(5)
$waited = 0
while (-not (Test-Path $logPath)) {
    if ((Get-Date) -gt $deadline) {
        Write-Host ''
        Write-Host '5 分钟内没有等到日志文件。后端可能仍在构建，或构建失败。' -ForegroundColor Yellow
        Write-Host '请查看后端窗口的输出。' -ForegroundColor DarkGray
        return
    }
    if ($waited % 10 -eq 0) {
        Write-Host '  等待后端写出第一行日志...' -ForegroundColor DarkGray
    }
    Start-Sleep -Seconds 1
    $waited++
}

Write-Host '  日志已就绪，开始输出。' -ForegroundColor Green
Write-Host ''

Get-Content -Path $logPath -Wait -Tail 100 -Encoding UTF8

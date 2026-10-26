# ============================================================
#  海岚 ERP 一键停止脚本
#  依次停止：前端(5173) -> 后端(8080) -> Redis(6379)
#  用法：在 PowerShell 中执行  .\stop-all.ps1
# ============================================================

$ErrorActionPreference = 'SilentlyContinue'

Write-Host '================ 海岚 ERP 停止 ================' -ForegroundColor Cyan

function Stop-Port($port, $name) {
    $conns = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue
    if ($conns) {
        $pids = $conns | Select-Object -ExpandProperty OwningProcess -Unique
        foreach ($procId in $pids) {
            Write-Host "  停止 $name (PID $procId，端口 $port)..." -ForegroundColor Yellow
            Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
        }
        Write-Host "  $name 已停止" -ForegroundColor Green
    } else {
        Write-Host "  $name 未在运行（端口 $port）" -ForegroundColor Gray
    }
}

Stop-Port 5173 '前端 (Vite)'
Stop-Port 8080 '后端 (Spring Boot)'
Stop-Port 6379 'Redis'

Write-Host '================ 全部停止完成 ================' -ForegroundColor Cyan

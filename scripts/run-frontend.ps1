# =============================================================
# 启动前端 Vite 开发服务器；node_modules 缺失时自动安装依赖
# 由 start-all 在独立窗口中调用
# =============================================================

try {
    . "$PSScriptRoot\_common.ps1"
    Import-HaiErpEnv | Out-Null

    # 让 npm / vite 的输出原样流到控制台
    $ErrorActionPreference = 'Continue'

    $frontendDir = Join-Path $script:HaiErpRoot 'frontend'
    Set-Location $frontendDir

    $npm = Resolve-Npm
    if (-not $npm) { throw '未找到 npm。请安装 Node.js 22 LTS 并把 bin 加入 PATH。' }

    if (-not (Test-Path 'node_modules')) {
        Write-Host ''
        Write-Host '未找到 node_modules，正在安装前端依赖...' -ForegroundColor Yellow
        Write-Host ''
        if (Test-Path 'package-lock.json') {
            & $npm ci --no-audit --no-fund
        } else {
            & $npm install --no-audit --no-fund
        }
        if ($LASTEXITCODE -ne 0) {
            throw '前端依赖安装失败，请查看上方 npm 输出。'
        }
        Write-Host ''
        Write-Host '依赖安装完成。' -ForegroundColor Green
    }

    Write-Host ''
    Write-Host '正在启动前端 http://127.0.0.1:5173 ...' -ForegroundColor Green
    Write-Host ''

    & $npm run dev

    Write-Host ''
    Write-Host '前端进程已退出。' -ForegroundColor Yellow
} catch {
    Write-Host ''
    Write-Host "[前端启动失败] $($_.Exception.Message)" -ForegroundColor Red
}

# 启动前端开发服务器；node_modules 不存在时自动安装依赖
$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false
$Root = Split-Path -Parent $PSScriptRoot
Set-Location "$Root\frontend"

if (-not (Test-Path 'node_modules')) {
    Write-Host '首次启动，正在安装前端依赖...' -ForegroundColor Yellow
    & npm.cmd install --no-audit --no-fund
    if ($LASTEXITCODE -ne 0) {
        Write-Host '依赖安装失败。' -ForegroundColor Red
        return
    }
}

Write-Host '正在启动前端（Vite）...' -ForegroundColor Green
& npm.cmd run dev

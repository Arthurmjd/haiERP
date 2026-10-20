# 启动后端；代码有变更时自动重新构建
$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false
$Root = Split-Path -Parent $PSScriptRoot
Set-Location "$Root\backend"
. "$PSScriptRoot\env.local.ps1"

$jar = Join-Path $Root 'backend\myfactoryai-admin\target\myfactoryai-admin.jar'

$needBuild = -not (Test-Path $jar)
if (-not $needBuild) {
    $jarTime = (Get-Item $jar).LastWriteTimeUtc
    $newer = Get-ChildItem -Path "$Root\backend" -Recurse -File -ErrorAction SilentlyContinue |
        Where-Object {
            $_.FullName -notmatch '\\target\\' -and
            $_.LastWriteTimeUtc -gt $jarTime -and
            $_.Extension -in '.java', '.xml', '.yml', '.yaml', '.properties'
        } | Select-Object -First 1
    if ($newer) { $needBuild = $true }
}

if ($needBuild) {
    Write-Host '检测到代码变更，正在构建后端（首次可能需要几分钟）...' -ForegroundColor Yellow
    & mvn.cmd -DskipTests package
    if ($LASTEXITCODE -ne 0) {
        Write-Host '后端构建失败，请查看上方错误。' -ForegroundColor Red
        return
    }
}

Write-Host '正在启动后端...' -ForegroundColor Green
& java -jar $jar --server.address=127.0.0.1

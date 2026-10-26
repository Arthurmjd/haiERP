# ============================================================
#  海岚 ERP 一键启动脚本
#  依次启动：Redis -> 后端(8080) -> 前端(5173)
#  用法：在 PowerShell 中执行  .\start-all.ps1
# ============================================================

$ErrorActionPreference = 'Continue'

# ---- 路径配置（按需修改）----
$JDK_HOME    = 'E:\soft\Java\jdk-21'
$REDIS_EXE   = 'E:\soft\redis-x64\redis-server.exe'
$BACKEND_DIR = 'E:\haiERP\backend'
$FRONTEND_DIR= 'E:\haiERP\frontend'
$JAR_PATH    = "$BACKEND_DIR\myfactoryai-admin\target\myfactoryai-admin.jar"
# Node 可执行文件（用于启动前端）
$NODE_EXE    = 'C:\Program Files\nodejs\node.exe'
$VITE_JS     = "$FRONTEND_DIR\node_modules\vite\bin\vite.js"
# npm：仅用于首次安装依赖时调用
$NPM_CMD     = 'C:\Program Files\nodejs\npm.cmd'
if (-not (Test-Path $NPM_CMD)) { $NPM_CMD = 'npm.cmd' }

# ---- 日志目录 ----
$LOG_DIR = "$env:TEMP\hai-erp-logs"
New-Item -ItemType Directory -Path $LOG_DIR -Force | Out-Null

# ---- 数据库 / Redis 连接配置（按需修改）----
$DB_URL    = 'jdbc:mysql://127.0.0.1:3306/hai_erp_demo?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true'
$DB_USER   = 'root'
# 优先读取环境变量，未设置时回落到本机演示默认值
$DB_PASSWORD = if ($env:DB_PASSWORD) { $env:DB_PASSWORD } else { '1234' }
$REDIS_HOST = '127.0.0.1'
$REDIS_PORT = '6379'
$TOKEN_SECRET = 'demo-token-secret-20261020'

$JAVA_EXE = "$JDK_HOME\bin\java.exe"

Write-Host '================ 海岚 ERP 启动 ================' -ForegroundColor Cyan

# ---------- 1. Redis ----------
Write-Host '[1/4] 检查 Redis...' -ForegroundColor Yellow
$redisRunning = Get-NetTCPConnection -LocalPort $REDIS_PORT -State Listen -ErrorAction SilentlyContinue
if ($redisRunning) {
    Write-Host "      Redis 已在 $REDIS_PORT 端口运行" -ForegroundColor Green
} else {
    if (-not (Test-Path $REDIS_EXE)) {
        Write-Host "      未找到 Redis：$REDIS_EXE" -ForegroundColor Red
        Write-Host '      请修改脚本中的 $REDIS_EXE 路径' -ForegroundColor Red
    } else {
        Write-Host "      启动 Redis（$REDIS_PORT）..." -ForegroundColor Yellow
        Start-Process -FilePath $REDIS_EXE -ArgumentList "--port $REDIS_PORT",'--maxmemory 256mb' -WindowStyle Hidden
        Start-Sleep -Seconds 2
        $redisRunning = Get-NetTCPConnection -LocalPort $REDIS_PORT -State Listen -ErrorAction SilentlyContinue
        if ($redisRunning) { Write-Host '      Redis 已启动 ✓' -ForegroundColor Green }
        else { Write-Host '      Redis 启动失败' -ForegroundColor Red }
    }
}

# ---------- 2. 后端 ----------
Write-Host '[2/4] 检查后端...' -ForegroundColor Yellow
$backendRunning = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue
if ($backendRunning) {
    Write-Host '      后端已在 8080 端口运行' -ForegroundColor Green
} else {
    if (-not (Test-Path $JAR_PATH)) {
        Write-Host "      未找到 jar：$JAR_PATH" -ForegroundColor Red
        Write-Host '      请先在 backend 目录执行 mvn -DskipTests package 打包' -ForegroundColor Red
    } else {
        # 设置后端所需环境变量
        $env:DB_URL = $DB_URL
        $env:DB_USER = $DB_USER
        $env:DB_PASSWORD = $DB_PASSWORD
        $env:REDIS_HOST = $REDIS_HOST
        $env:REDIS_PORT = $REDIS_PORT
        $env:TOKEN_SECRET = $TOKEN_SECRET

        Write-Host '      启动后端（8080），日志见下方文件...' -ForegroundColor Yellow
        # 必须显式 --server.port=8080，否则环境变量 SERVER__PORT 会覆盖端口
        $backendLog = "$LOG_DIR\backend.log"
        Start-Process -FilePath $JAVA_EXE `
            -ArgumentList '-jar', $JAR_PATH, '--server.address=127.0.0.1', '--server.port=8080' `
            -WorkingDirectory $BACKEND_DIR `
            -RedirectStandardOutput $backendLog -RedirectStandardError "$LOG_DIR\backend.err.log" `
            -WindowStyle Hidden

        Write-Host '      等待后端就绪（约 15~30 秒）...' -ForegroundColor Yellow
        $ready = $false
        for ($i = 0; $i -lt 45; $i++) {
            Start-Sleep -Seconds 2
            try {
                $r = Invoke-WebRequest -Uri 'http://127.0.0.1:8080/captchaImage' -UseBasicParsing -TimeoutSec 2
                if ($r.StatusCode -eq 200) { $ready = $true; break }
            } catch {}
            # 若进程已退出，提前结束等待
            if (-not (Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue)) {
                if ($i -gt 3) { break }
            }
        }
        if ($ready) {
            Write-Host '      后端已就绪 ✓' -ForegroundColor Green
        } else {
            Write-Host '      后端启动失败，请查看日志：' -ForegroundColor Red
            Write-Host "        $backendLog" -ForegroundColor Gray
            Write-Host "        $LOG_DIR\backend.err.log" -ForegroundColor Gray
        }
    }
}

# ---------- 3. 前端 ----------
Write-Host '[3/4] 检查前端...' -ForegroundColor Yellow
$frontendRunning = Get-NetTCPConnection -LocalPort 5173 -State Listen -ErrorAction SilentlyContinue
if ($frontendRunning) {
    Write-Host '      前端已在 5173 端口运行' -ForegroundColor Green
} else {
    if (-not (Test-Path "$FRONTEND_DIR\node_modules")) {
        Write-Host '      首次运行，安装依赖（npm ci，可能较慢）...' -ForegroundColor Yellow
        Push-Location $FRONTEND_DIR
        & $NPM_CMD ci
        Pop-Location
    }
    Write-Host '      启动前端（Vite）...' -ForegroundColor Yellow
    $frontendLog = "$LOG_DIR\frontend.log"
    # 直接用 node.exe 调 vite.js，避免 npm.cmd 需要 cmd.exe 的问题
    if ((Test-Path $NODE_EXE) -and (Test-Path $VITE_JS)) {
        Start-Process -FilePath $NODE_EXE -ArgumentList $VITE_JS,'--host','127.0.0.1','--port','5173' `
            -WorkingDirectory $FRONTEND_DIR `
            -RedirectStandardOutput $frontendLog -RedirectStandardError "$LOG_DIR\frontend.err.log" `
            -WindowStyle Hidden
    } else {
        Start-Process -FilePath $NPM_CMD -ArgumentList 'run','dev' `
            -WorkingDirectory $FRONTEND_DIR `
            -RedirectStandardOutput $frontendLog -RedirectStandardError "$LOG_DIR\frontend.err.log" `
            -WindowStyle Hidden
    }
    Start-Sleep -Seconds 6
    $frontendRunning = Get-NetTCPConnection -LocalPort 5173 -State Listen -ErrorAction SilentlyContinue
    if ($frontendRunning) { Write-Host '      前端已启动 ✓' -ForegroundColor Green }
    else {
        Write-Host '      前端可能未就绪，请查看日志：' -ForegroundColor Red
        Write-Host "        $frontendLog" -ForegroundColor Gray
    }
}

Write-Host ''
Write-Host '================ 启动完成 ================' -ForegroundColor Cyan
Write-Host '  前端登录页 : http://127.0.0.1:5173/' -ForegroundColor White
Write-Host '  演示预览页 : http://127.0.0.1:5173/preview' -ForegroundColor White
Write-Host '  后端接口   : http://127.0.0.1:8080' -ForegroundColor White
Write-Host "  运行日志   : $LOG_DIR" -ForegroundColor White
Write-Host ''
Write-Host '  登录账号   : admin / sales / planner / production / wh / finance / hr / boss' -ForegroundColor White
Write-Host '  登录密码   : Demo@2026!' -ForegroundColor White
Write-Host ''
Write-Host '  停止服务   : .\stop-all.ps1' -ForegroundColor Gray
Write-Host ''

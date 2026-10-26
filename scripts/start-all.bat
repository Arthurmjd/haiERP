@echo off
setlocal
chcp 65001 >nul 2>&1
title HaiERP Launcher
cd /d "%~dp0"

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0start-all.ps1" %*
set "RC=%ERRORLEVEL%"

echo.
if not "%RC%"=="0" (
    echo ============================================================
    echo  [FAILED] exit code %RC%
    echo  Read the message above, fix it, then run this file again.
    echo ============================================================
    echo.
    pause
    exit /b %RC%
)

echo Backend and frontend are running in separate windows.
echo Press any key to close THIS window - the services keep running.
pause >nul
exit /b 0

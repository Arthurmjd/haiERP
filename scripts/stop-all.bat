@echo off
setlocal
chcp 65001 >nul 2>&1
title HaiERP Stop
cd /d "%~dp0"

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0stop-all.ps1" %*
set "RC=%ERRORLEVEL%"

echo.
pause
exit /b %RC%

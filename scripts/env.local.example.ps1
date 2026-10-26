# =============================================================
# HaiERP 本地配置模板
#
# 用法：复制本文件为 env.local.ps1（同目录），按实际情况修改。
#       env.local.ps1 已被 .gitignore 忽略，不会提交到仓库。
#
# 也可以不复制，直接运行 scripts\start-all.bat，
# 首次会自动引导生成 env.local.ps1。
# =============================================================

# ---------- MySQL ----------
$env:DB_HOST = '127.0.0.1'
$env:DB_PORT = '3306'
$env:DB_NAME = 'hai_erp_demo'
$env:DB_USER = 'root'
$env:DB_PASSWORD = 'CHANGE_ME'

# ---------- Redis ----------
$env:REDIS_HOST = '127.0.0.1'
$env:REDIS_PORT = '6379'
# 留空表示 Redis 无密码（本地开发推荐留空）
$env:REDIS_PASSWORD = ''

# ---------- 后端 JWT 密钥 ----------
# 留空时 start-all 会自动生成一个并写回 env.local.ps1
$env:TOKEN_SECRET = ''

# ---------- 工具路径（可选，留空则自动探测 PATH 和常见安装目录） ----------
# 例：'C:\Program Files\Redis-8.2.3-Windows-x64-cygwin-with-Service\Redis-8.2.3-Windows-x64-cygwin-with-Service'
$RedisHome = ''
# 例：'C:\Program Files\MySQL\MySQL Server 8.4\bin'
$MySqlHome = ''

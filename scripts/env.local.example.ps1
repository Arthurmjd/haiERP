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

# 可选：脚本会用上面四项自动拼出 JDBC URL 传给后端。
# 只有需要额外连接参数时才取消注释，直接指定完整 URL（其余四项仍用于连库自检）。
# $env:DB_URL = 'jdbc:mysql://127.0.0.1:3306/hai_erp_demo?useSSL=false&serverTimezone=Asia/Shanghai'

# ---------- 端口 ----------
# 后端监听端口，默认 8080
$env:SERVER_PORT = '8080'

# ---------- Redis ----------
$env:REDIS_HOST = '127.0.0.1'
$env:REDIS_PORT = '6379'
# 留空表示 Redis 无密码（本地开发推荐留空）
$env:REDIS_PASSWORD = ''

# ---------- 后端 JWT 密钥 ----------
# 留空时 start-all 会自动生成一个并写回 env.local.ps1
$env:TOKEN_SECRET = ''

# ---------- AI 问答（DeepSeek） ----------
# 留空则 AI 问答降级为「数据摘要」，只展示查询到的数据，不生成分析文字。
# 申请密钥：https://platform.deepseek.com/api_keys
$env:OPENAI_API_KEY = ''
# 可选：默认走 DeepSeek 官方；改用中转站时填中转站地址（带不带 /v1 都行）
$env:OPENAI_BASE_URL = 'https://api.deepseek.com/v1'
# 可选：deepseek-v4-flash（快、便宜）或 deepseek-v4-pro（强推理）
$env:OPENAI_MODEL = 'deepseek-v4-flash'

# ---------- 工具路径（可选，留空则自动探测 PATH 和常见安装目录） ----------
# 例：'C:\Program Files\Redis-8.2.3-Windows-x64-cygwin-with-Service\Redis-8.2.3-Windows-x64-cygwin-with-Service'
$RedisHome = ''
# 例：'C:\Program Files\MySQL\MySQL Server 8.4\bin'
$MySqlHome = ''

# ---------- JDK / Maven（可选） ----------
# 系统已设 JAVA_HOME、且 mvn 已在 PATH 中时，下面两行不用管。
# 不想改系统环境变量，就在这里直接指定，只在启动脚本内生效：
# $env:JAVA_HOME  = 'E:\soft\Java\jdk-21'
# $env:MAVEN_HOME = 'E:\soft\apache-maven-3.9.4-bin\apache-maven-3.9.4'

# 海岚 ERP

服装制造 ERP 实训项目。前端为 Vue 3 + TypeScript + Element Plus，后端为 Java 21 + Spring Boot（基于 myFactoryAI/RuoYi 底座），数据使用 MySQL 和 Redis。同一网址登录后按角色进入不同工作台。

组内理解订单到采购、生产、出货、成本和 AI 的接力关系，请看[业务逻辑图](docs/业务逻辑图.md)。

想启用 AI 问答（DeepSeek），按[AI 问答接入 DeepSeek](docs/AI问答接入DeepSeek.md) 配一个环境变量即可；不配也能用，只是降级为数据摘要。

## 环境准备

每位组员在自己的电脑上安装并确认：

| 工具 | 要求 | 检查命令 |
| --- | --- | --- |
| JDK | 21 | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| Node.js | 22.12+（建议 22 LTS） | `node -v` |
| MySQL | 8.x | `mysql --version` |
| Redis | 本机可用 | `redis-cli ping` 返回 `PONG` |

以下以 Windows 为例。克隆仓库后，分别在**仓库根目录**打开终端。MySQL 默认端口为 3306，Redis 为 6379；前端会把 `/api` 请求转发给本机 8080 端口的后端。无需另装一个 RuoYi 项目。

## 只看页面

暂时没有配置数据库时，可以先在 PowerShell 中运行：

```powershell
cd frontend
npm ci
npm run dev
```

打开 <http://127.0.0.1:5173/preview>，可切换岗位查看演示页面。这是浏览器会话内的模拟数据，不会写入 MySQL，也不能代替真实接口联调。后续启动前端时已安装依赖就无需重复执行 `npm ci`。

## 首次建立演示库

**仅在全新、可丢弃的本地 MySQL 库中执行一次。** `sql/init_all.sql` 会删除并重建 ERP 业务表；不要对已有业务库执行。`sql/myfactoryai-free.sql` 是旧参考系统的历史导出，不要导入本项目演示库，也无需上传 GitHub。仓库内其余初始化 SQL 已足够运行。

确保 MySQL 已启动。在仓库根目录打开 `cmd.exe`，进入 MySQL 客户端：

```bat
mysql -u root -p
```

根据提示输入**你自己的 MySQL 密码**，不要把密码发到群里或写进仓库。看到 `mysql>` 后，逐行运行：

```sql
CREATE DATABASE hai_erp_demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hai_erp_demo;
SOURCE sql/platform-schema.sql;
SOURCE sql/init_all.sql;
SOURCE sql/erp-permissions.sql;
SOURCE sql/platform-demo-users.sql;
SOURCE sql/self_check.sql;
exit
```

若 `CREATE DATABASE` 提示库已存在，先停止并确认库中数据，不要继续运行初始化。自检应有 20 行 `PASS`。若提示找不到 `mysql`，把 MySQL 的 `bin` 目录加入系统 `PATH`，再打开新的终端。

## 启动服务

### 一键启动（推荐）

双击 `scripts/start-all.bat`。脚本会依次完成：

1. 检查 java / mvn / node / npm 是否可用
2. 启动 MySQL（服务已在运行则跳过）
3. 校验数据库能登录、`hai_erp_demo` 库存在
4. 启动 Redis（已在运行则跳过）
5. 打开后端与前端两个窗口，后端按需自动增量构建

**首次运行**会询问 MySQL 密码等配置，写入 `scripts/env.local.ps1`。
该文件已被 `.gitignore` 忽略，不会提交，之后每次双击都不再询问。

关闭服务：双击 `scripts/stop-all.bat`（可选择是否一并停止 Redis；MySQL 作为系统服务不受影响）。

重复双击不会报错：端口 8080 / 5173 已被占用时，脚本会提示并跳过对应组件，只补启动没起来的部分。
需要全部重来，先运行 `scripts/stop-all.bat`。

后端窗口出现 `Started MyFactoryAIApplication` 才算启动成功。若启动十几秒后窗口就退出，
先看 `开发启动说明.md` 的「常见启动故障」章节 —— 最常见的原因是连错了数据库。

| 文件 | 作用 |
| --- | --- |
| `scripts/start-all.bat` | 一键启动入口（双击） |
| `scripts/stop-all.bat` | 停止前后端进程（双击） |
| `scripts/env.local.example.ps1` | 本地配置模板；不双击启动时可复制为 `env.local.ps1` 手工填写 |
| `scripts/watch-backend-log.ps1` | 单独查看后端日志（可选） |

若不想用脚本，也可以按下面的手动步骤启动。

### 手动启动

1. 启动 Redis。在单独终端运行 `redis-server`；若 Redis 已作为服务启动，用 `redis-cli ping` 确认返回 `PONG` 即可。
2. 在仓库根目录打开一个新的 PowerShell 终端，启动后端。以下密码输入方式兼容 Windows PowerShell 5.1 和 PowerShell 7：

```powershell
cd backend
$env:DB_URL = 'jdbc:mysql://127.0.0.1:3306/hai_erp_demo?useSSL=false&serverTimezone=Asia/Shanghai'
$env:DB_USER = 'root'
$dbPassword = Read-Host 'MySQL 密码' -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new('', $dbPassword).Password
Remove-Variable dbPassword
$env:REDIS_HOST = '127.0.0.1'
$env:TOKEN_SECRET = [guid]::NewGuid().ToString('N')
mvn clean -DskipTests package
java -jar myfactoryai-admin/target/myfactoryai-admin.jar --server.address=127.0.0.1
```

看到 `Started MyFactoryAIApplication` 后，后端应监听 <http://127.0.0.1:8080>。如果 MySQL 账号不是 `root`，修改 `DB_USER`。Redis 使用其他端口时，增加 `$env:REDIS_PORT = '端口号'`。AI 问答不要求模型密钥即可启动；需要模型分析时，在后端终端设置自己的 `OPENAI_API_KEY`，不要提交密钥。

3. 在仓库根目录再打开一个 PowerShell 终端，启动前端：

```powershell
cd frontend
npm ci
npm run dev
```

打开 <http://127.0.0.1:5173> 登录。前后端终端要保持运行；停止时在各自终端按 `Ctrl+C`。再次运行前端时通常只需 `npm run dev`。

## 本地演示账号

| 岗位 | 用户名 |
| --- | --- |
| 管理员 | `admin` |
| 销售 | `sales` |
| 计划员 | `planner` |
| 生产主管 | `production` |
| 仓管员 | `wh` |
| 财务 | `finance` |
| 人事 | `hr` |
| 厂长 | `boss` |

八个账号的临时密码都是 `Demo@2026!`，由 `sql/platform-demo-users.sql` 创建。它们**只供本机演示**，不得用于公开部署。演示种子的业务日期固定在 2026 年 10 月；新建订单时交期需选择当前日期之后。

## 验证与排错

- 后端测试：在 `backend` 目录运行 `mvn test`。
- 前端构建：在 `frontend` 目录运行 `npm run build`。
- 前端提示 `ECONNREFUSED 127.0.0.1:8080`：后端未启动或端口不一致，先查看后端终端是否显示启动成功。
- 登录失败：确认四份 SQL 按上述顺序导入、MySQL 和 Redis 均在运行，且后端连接的是 `hai_erp_demo`。
- 修改后端代码后：先停止旧后端，再执行 `mvn clean -DskipTests package` 并重启 JAR；运行中的旧 JAR 不会自动更新，且在 Windows 上可能占用文件导致打包失败。删除或移动源码后必须带 `clean`，否则 `target/` 里的旧 class 和 mapper XML 会继续被打进 JAR，引发启动报错。

更多实现状态和验收事项见 [开发启动说明](开发启动说明.md)、[需求分析](AI-ERP需求分析文档.md)、[接口设计](AI-ERP接口设计文档.md) 和 [数据库设计](AI-ERP数据库设计文档.md)。

## 组内协作

每位组员在自己的分支修改代码或文档，通过 Pull Request 合并。提交前运行相关构建或测试；数据库密码、API 密钥、本地日志、`target/`、`node_modules/` 和 `dist/` 都不要提交。涉及 SQL 初始化顺序或接口契约的改动，同时更新本 README 和对应设计文档。

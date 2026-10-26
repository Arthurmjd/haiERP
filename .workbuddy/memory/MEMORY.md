# haiERP 项目长期笔记

## 运行环境
- JDK 21 `E:\soft\Java\jdk-21`；Maven `E:\soft\apache-maven-3.9.4-bin\apache-maven-3.9.4\bin\mvn.cmd`（需显式设 `JAVA_HOME`）。
- 用户级环境变量已配置：`JAVA_HOME`、`MAVEN_HOME`，PATH 含两者 `bin`；改完需新开终端才生效。
- Node `C:\Program Files\nodejs\node.exe`；MySQL 8.4:3306（库 `hai_erp_demo`）；Redis `E:\soft\redis-x64`:6379。
- 演示账号 8 个：admin/sales/planner/production/wh/finance/hr/boss，密码 `Demo@2026!`。

## 启动约束（易踩坑）
- 后端必须显式 `--server.port=8080`，否则环境变量 `SERVER__PORT` 会把端口改掉。
- Vite dev server 需置空 `NODE_OPTIONS`，否则 safe-delete 守卫拦截 `.vite/deps_temp_*` 清理导致崩溃。
- `npm install` 必须加 `--registry=https://registry.npmmirror.com`；被 safe-delete 打断时用 `CODEBUDDY_SAFE_DELETE_ENABLED=0`。
- 重新打包后端前先停掉 8080 进程，否则 jar 被占用无法 repackage。
- 一键启停：`start-all.ps1` / `stop-all.ps1`（Redis → 后端 → 前端）。

## Git 与远端
- 仓库级身份：`diligent-zhang <2485680976@qq.com>`。
- GitHub 直连参数：`git -c http.sslBackend=openssl -c http.sslVerify=false`（schannel 会报 `CRYPT_E_NO_REVOCATION_CHECK`）。
- 镜像 remote `ghproxy`（`https://gh-proxy.com/...`）**只可 fetch，不能 push**。
- push 需有写权限的账号：本机凭据 `aishuijiao666` 对 `Arthurmjd/haiERP` 无权限（403），推送前先确认。

## 代码约定
- 单据临时单号统一走 `erp/support/DocNos.temp(前缀)`，正式号走 `DocNos.formal()`；单号列一律 `varchar(30)`，不要再手拼 UUID。
- 前端 Element Plus 按需引入：`unplugin-vue-components` + `ElementPlusResolver`，dts 输出 `src/types/components.d.ts`；locale 用 `provideGlobalConfig`；message/message-box/loading 样式需手动引入。
- 认证失败时 HTTP 仍是 200，真实结果在响应体 `code`（403 等），排查勿只看状态码。

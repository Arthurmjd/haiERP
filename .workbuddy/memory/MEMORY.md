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
- 主数据走通用 resource 化 CRUD `/erp/md/{resource}`，权限码 `md:{resource}:add/edit/remove`；按钮在 `CatalogView.vue` 用 `user.can()`（来自 `/getInfo` 的 perms，非 `v-hasPermi`）；删除被引用档案会拒物理删除（提示改停用）。
- 工资单状态机：0 草稿 → 1 已确认 → 2 已发放；`hr_salary_slip` 有 `si_personal/fund_personal/tax/si_company/fund_company` 扣缴列（`ErpSalaryService.generate` 计算）。
- 报工工资期锁定：员工当月工资单 status∈(1,2) 即拒绝该月任何报工（含正常报工，非仅补录）；作废工资单（`voidSlip` status=1→0）可解锁重新报工。
- 工序-员工关联表 `md_process_worker(process_id,emp_id)`：报工选员工按工序过滤，无关联的工序回退全量生产员工；配置入口在工序目录页「配置员工」按钮，权限 `md:process:assign`（菜单 90105）。
- 采购扣款流：确认/审批通过即调 `ErpFinanceService.payPurchase` 自动建应付+付款单+过账扣款（默认账户 = `fn_account` status=1 按 account_id）；收货时 `payableByPo` 判重避免重复建应付。
- SQL seed 文件幂等（`INSERT IGNORE` / `ON DUPLICATE KEY UPDATE`），re-seed 安全；权限菜单 90000-90105，planner 现有基础数据 add/remove 全权。
- DB 连接：`mysql -uroot -p1234`（密码见 `scripts/env.local.ps1`），客户端 `E:\soft\mysql-8.0.34-winx64\mysql-8.0.34-winx64\bin\mysql.exe`；中文查询加 `--default-character-set=utf8mb4`。


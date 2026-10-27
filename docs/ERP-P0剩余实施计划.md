# 海岚 ERP：P0 剩余实施计划（收口清单）

> **状态：P0 已全部收口（归档）。** 截至 2026-10-28 的当前事实：`sql/self_check.sql` 29 项、`sql/p2-demo-data-consistency.sql` 9 项、`sql/p3-chain-integrity.sql` 15 项、`sql/p4-rbac-completion.sql` 7 项，合计 60 项全部 PASS；后端 43 个控制器 / 260 个方法级接口 / 228 处生效的 `@PreAuthorize`；前端 28 个页面、财务页 8 个标签。下文保留当时的收口清单与端到端断言记录。
>
> 版本：v1.1（2026-10-27）。P0 已收口：迁移已落库、权限已生效、端到端验收已跑通。
> 已完成：数据库脚本、后端服务/接口、业务事件挂接、前端页面与 API；后端 `mvn test`、前端 `npm run build` 通过。
> 2026-10-27 实测：`hai_erp_demo` 迁移+一次性修正完成；61 条端到端断言首轮 46 通过，修复 2 个真实缺陷后相关链路全部通过。
> 剩余：P1/P2 项见第 5 节；演示库留有本次验收测试数据（已备份到 `backup_hai_erp_before_p0.sql`）。

---

## 1. 完成度总览

| 项 | 状态 | 说明 |
|---|---|---|
| 资金域（账户/流水/应收/应付/收付款） | 代码完成 | `ErpFinanceService`、`ErpFinanceController` |
| 待办 / 审批 / 期间 | 代码完成 | `ErpWorkflowService`、`ErpWorkflowController` |
| 采购阈值审批 | 代码完成 | `pu:purchaseOrder:approve/reject` |
| 收货→应付、发货→应收、退货冲减 | 代码完成 | 事务内生成，幂等键兜底 |
| 工资计算/批量/确认/发放 | 代码完成 | `hr_payroll_config` + `ErpPayrollMapper` |
| 报价手工建单/转订单 | 代码完成 | `sd:quotation:add/convert` |
| 工单排期/预留/分批领料/退料/一键生成 | 代码完成 | `st_reservation`、`pr:workOrder:return/generate` |
| 前端页面与导航 | 代码完成 | 待办、审批、会计资金、工资、工单、报价 |
| 数据库落库 | 已完成（2026-10-27） | 函数表/业务表/新增列/菜单授权均已落库；旧工资单 15 张 1→2 |
| 权限生效 | 已生效 | finance/boss/hr 等角色可访问；反结账 90125 授予 boss |
| 端到端验收 | 已跑通 | 见第 3 节与第 4 节「本次修复」 |

---

## 2. 立即要做的落地步骤

### 2.1 备份并执行迁移（重要）

```bash
# 1) 备份
mysqldump -uroot -p hai_erp_demo > backup_hai_erp_before_p0.sql

# 2) 执行增量迁移（可重复执行新增表/列/菜单）
mysql -uroot -p hai_erp_demo < sql/ai-erp-12-finance-workflow.sql

# 3) 一次性：旧工资单 status=1（旧语义“已发”）迁到新语义 2（已发放）
#    仅执行一次，执行后不要再跑，否则会把新确认未发放的单据误标为已发。
mysql -uroot -p hai_erp_demo < sql/p0-data-fix-salary-status.sql
```

> 注意：上述 UPDATE 已从 `sql/ai-erp-12-finance-workflow.sql` 移出，改为独立脚本 `sql/p0-data-fix-salary-status.sql`，保证迁移脚本可反复执行；一次性修正脚本只跑一次。

### 2.2 权限与菜单生效

- 执行迁移后，管理员需重新登录（或清 Redis 权限缓存）才能看到新菜单与权限点。
- 检查 `sys_role_menu` 是否已给 `finance/boss/hr/planner/production/wh/sales` 授予 90110–90135。
- 前端侧边栏是静态链接，按 `user.can(...)` 过滤；后端菜单只用于权限。

### 2.3 启动与冒烟

```bash
# 后端
cd backend && mvn spring-boot:run -pl myfactoryai-admin

# 前端
cd frontend && npm run dev
```

冒烟接口（按顺序）：

1. `GET /erp/finance/overview` → 返回 6 个 KPI。
2. `GET /erp/workflow/todos` → 财务/厂长/HR 各能看到对应待办。
3. 采购单金额 > 10000 → 提交后 `approval_status=1`，厂长审批中心可见；审批后才计入在途。
4. 采购收货 → `fn_payable` 新增，财务待办出现「待付款」。
5. 登记付款并确认 → 应付余额下降、账户余额下降、`fn_cash_txn` 新增。
6. 发货 → `fn_receivable` 新增，财务待办出现「待收款」；收款确认后余额归零。
7. 工资单确认 → 状态 1；发放 → 状态 2、资金流水、计件明细置已发。
8. 月末结账 → 当月再做收货/发货/收付款/报工应被拒绝。

---

## 3. 端到端验收场景（下一次会话逐条跑）

| # | 场景 | 预期 |
|---|---|---|
| 1 | 重复收货、重复付款、重复发货 | 同一 `requestKey` 只生效一次；参数不同复用键返回 2007 |
| 2 | 采购 12,000（阈值 10,000） | 提交后待审批；厂长通过前不计在途、不可收货 |
| 3 | 收货 40/100 | 应付 40×单价；再收 60 后应付累计、状态全部收货 |
| 4 | 发货 60/100 | 应收按发货金额；订单未发齐；再发 40 才发齐 |
| 5 | 收款部分核销 | 应收状态 1，余额正确；全额后状态 2 |
| 6 | 工资确认后报工 | 报工被拒（工资期已确认）；发放后不再可改 |
| 7 | 工单预留 100、另一工单领料 | 可用量扣预留，避免超领；退料后库存与 issued/returned 对账 |
| 8 | 期间结账后补录 | 收货/发货/收付款/报工均拒绝 |

---

## 4. 已知风险与待修

1. **工资状态迁移**：已拆成独立脚本 `sql/p0-data-fix-salary-status.sql`，迁移脚本可重复执行；该修正脚本只跑一次（本次已将 15 张旧单 1→2）。
2. **收款/付款单允许无核销明细**：当前可登记一笔“纯收付款”，不挂 AR/AP；若业务要求必须核销，需在 `createPayment` 增加校验。
3. **工资发放的内部确认**：`payPayroll` 创建无核销明细的付款单后调用 `confirmPayment`；本次实测通过，资金流水单条、工资单 1→2，无重复过账。
4. **待办可见性**：`myTodos` 按登录角色过滤；admin 不会看到业务角色待办（admin 通常也不需要），如需要可给 admin 角色额外 JOIN。
5. **库存预留与 MRP**：MRP 供给仍按库存+在途，未扣预留；多订单抢料时建议后续把 `st_reservation` 纳入 `v_mrp_demand`。
6. **前端演示模式**：新增写操作在 demo 模式会提示“不支持”，联调需连真实后端。
7. **菜单组件路径**：`sys_menu.path/component` 是给 RuoYi 动态菜单用的；当前前端是静态路由，`/finance`、`/todos`、`/approvals` 必须与 `App.vue`/`router.ts` 一致，联调时核对。
8. **前端现有页面动作**：工资批量/发放、工单退料、订单生成工单、报价新建/转订单已写入，但未逐项点击验证；构建通过不代表交互正确。

### 4.1 本次验收发现并已修复（2026-10-27）

| # | 问题 | 修复 |
|---|---|---|
| 1 | 收付款确认抛 `java.sql.Date cannot be cast to LocalDate`，导致付款/工资发放全体失败 | `ErpFinanceService.confirmPayment` 改用 `toLocalDate(...)` 兼容 `java.sql.Date` |
| 2 | 财务/厂长均无 `fn:period:reopen`，反结账接口 403 | 迁移脚本给 boss 授予菜单 90125；财务保留结账 90124 |
| 3 | 报工未校验会计期间，结账后仍可补录 | `ErpWorkOrderService.report` 增加 `workflow.assertOpen(reportDate)` |
| 4 | 工资发放后仍可再报工，产生滞留在已发期间的计件工资 | `closedWagePeriod` 改为 `status IN (1,2)` |
| 5 | 已发放工资单重新生成报“状态已变化”，语义不清 | `ErpSalaryService.generate` 显式拦截 status=2 |

---

## 5. P1 / P2 待办（本次未做）

**P1**
- 客户信用额度校验（`md_customer.credit_limit` 已加列，确认时未强制）。
- 盘点单（当前只有调整流水）、安全库存预警、报工复核。
- 工资条详情与导出、部门/岗位维护、供应商准交率、看板趋势、预警处理流。
- Excel 导入导出、审计记录查询页。

**P2**
- 质检/返修、外发加工应付、多仓/库位、APS 排产、销售提成、复式凭证/总账、条码。

---

## 6. 回滚

- 新增表可直接 `DROP`；新增列在确认无引用后可 `DROP COLUMN`。
- 业务代码回滚到本次提交前即可；数据库新增表不影响旧逻辑。
- 资金流水只增，不可删除；测试数据建议在独立 schema 中跑。

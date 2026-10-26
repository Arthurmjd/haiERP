# 海岚 ERP：P0 剩余实施计划（收口清单）

> 版本：v1.0（2026-10-26）。用于下一次会话继续完成 P0 闭环。
> 已完成：数据库脚本、后端服务/接口、业务事件挂接、前端页面与 API；后端 `mvn test` 通过，前端 `npm run build` 通过（exit 0）。
> 未完成：真实数据库迁移、权限刷新、端到端场景验证、少量数据/口径校正。

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
| 数据库落库 | **未执行** | 需在演示库执行迁移 |
| 权限生效 | **未验证** | 需重新登录/清缓存 |
| 端到端验收 | **未执行** | 见第 3 节 |

---

## 2. 立即要做的落地步骤

### 2.1 备份并执行迁移（重要）

```bash
# 1) 备份
mysqldump -uroot -p hai_erp_demo > backup_hai_erp_before_p0.sql

# 2) 执行增量迁移（可重复执行新增表/列）
mysql -uroot -p hai_erp_demo < sql/ai-erp-12-finance-workflow.sql

# 3) 一次性：旧工资单 status=1（旧语义“已发”）迁到新语义 2（已发放）
#    仅执行一次，执行后不要再跑，否则会把新确认未发放的单据误标为已发。
mysql -uroot -p hai_erp_demo -e "
UPDATE hr_salary_slip
SET status=2, paid_time=IFNULL(paid_time,NOW())
WHERE status=1 AND paid_time IS NULL;"
```

> 注意：`sql/ai-erp-12-finance-workflow.sql` 末尾目前包含上述 UPDATE。若要反复执行迁移，请先把它注释掉，或只在首次执行；建议下一次把该语句移出迁移脚本，改为独立的一次性数据修正脚本。

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

1. **工资状态迁移**：如上，一次性 UPDATE 不能重复执行；建议移出迁移脚本。
2. **收款/付款单允许无核销明细**：当前可登记一笔“纯收付款”，不挂 AR/AP；若业务要求必须核销，需在 `createPayment` 增加校验。
3. **工资发放的内部确认**：`payPayroll` 创建付款单后调用 `confirmPayment`，付款单没有 `payment_line`，核销待办由工资待办单独完成；已在逻辑中处理，联调时确认无重复过账。
4. **待办可见性**：`myTodos` 按登录角色过滤；admin 不会看到业务角色待办（admin 通常也不需要），如需要可给 admin 角色额外 JOIN。
5. **库存预留与 MRP**：MRP 供给仍按库存+在途，未扣预留；多订单抢料时建议后续把 `st_reservation` 纳入 `v_mrp_demand`。
6. **前端演示模式**：新增写操作在 demo 模式会提示“不支持”，联调需连真实后端。
7. **菜单组件路径**：`sys_menu.path/component` 是给 RuoYi 动态菜单用的；当前前端是静态路由，`/finance`、`/todos`、`/approvals` 必须与 `App.vue`/`router.ts` 一致，联调时核对。
8. **前端现有页面动作**：工资批量/发放、工单退料、订单生成工单、报价新建/转订单已写入，但未逐项点击验证；构建通过不代表交互正确。

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

# 海岚 ERP 业务链路与职责分工手册

> 版本：v1.0（2026-10-20）。依据《AI-ERP需求分析文档》v2.0、《AI-ERP接口设计文档》v2.0、《AI-ERP数据库设计文档》v2.0、`sql/erp-permissions.sql` 以及后端 Controller 上的 `@PreAuthorize` 实测整理。>   
> 用途：说清楚"一张订单从客户询单到成本落账、再到下一次报价"这条链路上，**每一步谁发起、谁审核、系统做了什么、改了哪些表、单据状态怎么走**。可直接用于组内分工、答辩讲解和验收测试设计。

---

## 0. 先讲清楚一件事：本系统的"审批"是什么形态

首期**没有工作流引擎、没有多级会签、没有待办中心**。"审批"在本系统里落地为两件事的组合：

1. **两段式确认**：每张业务单据都是 `草稿 → 生效` 两段。草稿可以随便改、不产生任何库存/账务副作用；一旦执行"确认/下单/发货/结算"动作，状态推进、副作用落账、关键字段冻结，就不能再直接改或删（需求 4.6、接口文档 §7）。
2. **权限点分离**：创建用 `:add`、确认用 `:confirm`。这两个权限点在 `sys_role_menu` 里是**分开授权的**，所以"谁申请"和"谁审批"在权限层面天然可分——目前种子数据把两者都给了同一个角色，但随时可以把 `:confirm` 收给主管，立刻变成真正的审批。

因此下表里的"审批人"是**当前种子数据的实际执行者**，而"建议审批人"是把它改成真审批流时应当授予的人。这个区别很重要，别在答辩时说"我们有三级审批流"，那不是事实。

---

## 1. 角色与权限地图

八个角色，同一网址同一账号体系，登录后按角色进不同岗位工作台，一个账号多角色可切换视角（但后台仍按真实权限鉴权，前端隐藏按钮只是体验优化，不是安全边界）。

| 角色                | 用户名          | 在本链路中的定位         | 关键写权限                                                                                          |
| ----------------- | ------------ | ---------------- | ---------------------------------------------------------------------------------------------- |
| 管理员 `admin`       | `admin`      | **平台守门人** + 兜底  | 全部权限（`*:*:*`）；系统管理（用户/角色/菜单/部门）只授给他                                                              |
| 销售 `sales`        | `sales`      | 客户、报价、订单、出货、退货   | 客户增改删、`sd:order:add/confirm/edit/remove/cancel`、`sd:quotation:add/edit/accept/expire/convert`、出货单建单/发货/签收、退货登记/处理 |
| 计划员 `planner`     | `planner`    | 主数据、MRP 缺料、采购、下工单 | 主数据全量维护（分类/颜色/尺码/款式/物料/供应商/工序/SKU 生成）、`pu:suggestion:generate`、`pu:purchaseOrder:add/edit/confirm/receive/remove/cancel`、`pr:workOrder:add` |
| 生产主管 `production` | `production` | 派工、领料、报工、完工、退料   | `pr:workOrder:add/issue/finish/remove/cancel/generate/return`、`pr:process:report`、`md:process:assign` |
| 仓管 `wh`           | `wh`         | 收货、领料、完工入库、发货、盘点 | `pu:purchaseOrder:receive`、`pr:workOrder:issue/finish`、`sh:shipment:add/ship/sign/remove/cancel`、`sh:return:add/handle`、`st:txn:adjust` |
| 财务 `finance`      | `finance`    | 成本结算 + **资金全流程**   | `fn:settlement:calc/confirm/remove`、`fn:account:add/edit`、`fn:payment:add/confirm`、`fn:period:close/reopen`、`fn:payroll:pay`、资金域全部 `list/query` |
| 人事 `hr`           | `hr`         | 计件与工资            | `hr:employee:add/edit/remove`、`hr:salarySlip:generate/generateBatch/confirm/query`                                        |
| 厂长 `boss`         | `boss`       | 看板、审批、只读为主     | 业务单据 `list/query`、`pu:purchaseOrder:approve/reject`、资金域只读（账户/应收/应付/收付款/期间 `list`）；**无资金写权限** |

三条敏感数据红线（需求 §2）：工资明细默认仅 `hr`/`admin`；成本明细默认仅 `finance`/`admin`/授权后的 `boss`；**没有 `fn:settlement:list` 的角色，看板 `/erp/dashboard/overview` 不会返回 `standardGrossProfit`**（接口文档 §6，服务端硬校验，不是前端遮掩）。

> `sql/p4-rbac-completion.sql` 负责维护这张表：它按「谁执行动作就授给谁」补齐按钮权限，并自带 7 项自检——财务能打开财务页并登记收付款、销售能新建报价并转订单、生产能一键生成工单、人事能批量生成工资单、除运维专用的 `monitor:operlog:remove` 外没有无人拥有的按钮权限、厂长资金权限保持只读。

---

## 2. 主链路全景：一条订单跑通 19 步

贯穿示例：客户「锐跑体育」要订 **HL-T26 运动上衣**，藏青/浅灰两色 × S/M/L 三码，共 3000 件，承诺交期 2026-11-15。

| #  | 阶段                    | 谁发起                       | 谁审核/确认                                            | 产出单据                                            | 状态变化                                   |
| -- | --------------------- | ------------------------- | ------------------------------------------------- | ----------------------------------------------- | -------------------------------------- |
| 0  | 主数据与设计（款式/BOM/工序/SKU） | planner（技术资料）              | planner 自检启用                                        | `md_style`/`md_bom`/`md_style_process`/`md_sku` | 主数据无状态机，停用不影响历史快照                      |
| 1  | 客户档案                  | sales                     | sales                                             | `md_customer`                                   | —                                      |
| 2  | 报价                    | sales                     | 客户接受 → sales 点"接受"                                | `sd_quotation`                                  | 0待确认 → 1已接受 / 2已失效                     |
| 3  | 销售订单（草稿）              | sales                     | 可带 `quoteId` 由报价转单                               | `sd_order` + 色码行；报价置为已转单                          | 0 草稿；报价 1 → **3 已转单**（一次且不可逆）         |
| 4  | **订单确认**              | sales 提交                  | `sd:order:confirm`（现为 sales，建议销售主管）               | 冻结色码/数量/单价/交期                                   | 0 → 1 已确认                              |
| 5  | MRP 净缺料               | planner 查看 `v_mrp_demand` | 系统算，不需人审                                          | `pu:suggestion` 读视图                             | —                                      |
| 6  | 生成采购单（草稿）             | planner                   | —                                                 | `pu_purchase_order` + 明细                        | 0 草稿（**不计在途**）                         |
| 7  | **采购下单/审批**           | planner 提交                | 小额直接确认；超阈值走 boss 审批                                | 采购单生效 + 应付与付款单                                   | 0 → 1 已下单（**此刻才计入在途**）；小额或审批通过即自动付款   |
| 8  | 分批收货                  | wh                        | `pu:purchaseOrder:receive`                        | 库存流水 `st_stock_txn`                             | 1 → 2 部分收货 → 3 全部收货；**已有应付时不重复挂账**     |
| 9  | 下达工单（冻结）              | planner / production      | 系统冻结校验                                            | `pr_work_order` + SKU/物料/工序快照                   | 0 待下达                                  |
| 10 | **领料开工**              | production 申请             | `pr:workOrder:issue`（production 或 wh 执行）          | 领料流水                                            | 0 → 1 生产中                              |
| 11 | 报工                    | production                | `pr:process:report`                               | `pr_process_report` + 计件 `hr_piece_wage`        | 工序累计合格/不良数                             |
| 12 | **完工入库**              | production                | `pr:workOrder:finish`（production 或 wh）            | 成品入库流水                                          | 1 → 2 已完工；订单 1 → 3 已完工                 |
| 13 | 出货（建单→发货→签收）          | sales 建单                  | wh 发货（ship）、wh/sales 签收（sign）                      | `sh_shipment`                                   | 0 → 1 已发货 → 2 已签收；订单 3 → 4 已发齐 → 5 已完成 |
| 14 | **应收生成**              | 系统（发货即开票）                 | 无需人工                                              | `fn_receivable`                                 | 0 未收；金额 = 出货量 × 订单单价，到期日 = 发货日 + 客户账期 |
| 15 | **收款核销**              | finance 确认                | `fn:payment:confirm`                              | `fn_payment` + `fn_payment_line` + `fn_cash_txn` | 应收 0 → 1 部分收款 → 2 已收清；账户余额同步          |
| 16 | **标准成本结算**            | finance 试算                | `fn:settlement:confirm`（finance，建议成本主管复核）         | `fn_cost_settlement` + `fn_cost_detail` 快照      | 0 试算 → 1 确认（冻结）                        |
| 17 | 生成新报价（闭环）             | finance 从结算生成             | sales 对客发出 → 客户接受                                 | `sd_quotation`（带 `source_settle_id`）            | 回到步骤 2                                 |
| 18 | 计件工资与发放               | hr 生成                     | hr 确认；finance 发放                                  | `hr_salary_slip` + 工资付款流水                       | 草稿 0 → 已确认 1 → 已发放 2                  |



---

## 3. 逐步详解

### 步骤 0　主数据与设计：BOM 和工艺是整条链的源头

**谁做**：`admin`（现实里是技术/资料员，本系统目前只有 admin 有写权限）。

**做什么**（`POST /erp/md/style/{styleId}/bom`、`/process`）：

1. 品类、颜色、尺码、物料、供应商、工序工价先建好；
2. 建款式 HL-T26，挂 BOM（面料、罗纹、拉链、洗水唛，各带单位、单件用量、损耗率）；
3. 挂工艺路线（裁剪→车缝→整烫→检验），每道工序设工价、是否委外；
4. `POST /erp/md/sku/generate` 按 `(styleId, colorId, sizeId)` 去重生成 6 个 SKU。

**硬校验**：`md_bom.unit` 必须与物料主单位一致，不一致时**拒绝启用/下单**——防止"数字对、单位错"这种最致命的事故（数据库文档 §2 末）。

**为什么必须先做**：后面工单冻结的是**这一刻**的 BOM 用量和工价。主数据之后改价改量，不会回改已冻结的工单和已确认的成本（需求 §4.5、数据库文档 §2）。这是整个成本可信度的地基。

### 步骤 1–2　报价：链路的起点，也是上一轮的终点

**谁做**：`sales`。系统维护客户档案（sales 有 `md:customer:add/edit`），录入报价单。

**关键设计**：报价的成本依据**只能来自已确认结算**——`POST /erp/sd/quotation/fromSettlement` 入参 `{settleIds, requestKey}`，只接受 `status=1` 的结算单，把 `unitCost` 复制为 `costRef` 并记下 `sourceSettleId` 和价源。首轮没有历史结算时，走人工估价；从第二轮起，报价就有据可查（数据库文档 §3.3）。

**审批点**：对客的审批权在客户手上——`POST /erp/sd/quotation/accept/{id}`（权限 `sd:quotation:accept`，sales 持有）标记客户已接受，`expire/{id}` 标记失效。两者都只允许从"待确认 0"出发，重复或并发操作报状态冲突。

### 步骤 3–4　销售订单：第一个真正的"申请 → 审批"

**申请**：sales 建 `sd_order`，主子表，子表是色码行（款式 × 颜色 × 尺码 × 数量 × 单价）。服务端校验行唯一、数量单价为正，**金额由服务端汇总**——客户端不传 `totalAmount`（接口文档 §1），前端算的合计一律不作数。

**审批（订单确认）**：`POST /erp/sd/order/confirm/{id}`，权限 `sd:order:confirm`，状态 0 → 1。

确认这一下做了三件事：

1. **冻结**色码、数量、单价、承诺交期——此后编辑接口不再接受这些字段；
2. **放行**——只有"已确认"的订单才进入 MRP 需求计算，草稿订单不产生物料需求；
3. **设卡**——取消只允许没有下游单据和记账副作用的订单，有工单/发货的订单不允许直接取消（冲销后置）。

**现状与建议**：现在 `sales` 同时有 `add` 和 `confirm`，等于自审。要做成分级审批，把 `sd:order:confirm` 从 sales 收回、只给销售主管/厂长即可，代码零改动。

### 步骤 5　MRP 净缺料：系统算，不靠人拍脑袋

**谁看**：`planner`（`GET /erp/pu/suggestion/list`，权限 `pu:suggestion:list`）。

口径（数据库文档 §3.1），别被简化版误导：

```
总需求 = 活动订单的 BOM 计划需求 − 对应工单已领料量
在途   = 已下单采购明细数量 − 该明细已收货量        // 草稿不算，部分到货只计剩余
供给   = 当前现存库存 + 在途
净缺口 = max(总需求 − 供给, 0)
```

三条容易踩的坑：**采购草稿不计在途**（所以"生成了采购单但没下单"的料，缺料建议里照样缺，这是故意的）；**已领料的部分不再算需求**；**建议采购量按采购单位向上取整**，金额按当前参考价估算。

### 步骤 6–7　采购：第二个"申请 → 审批"

**申请**：planner 在缺料建议页勾选物料、指定供应商，`POST /erp/pu/suggestion/generate` 生成采购单**草稿**。重复请求不会重复建单（`requestKey` 幂等）。

**审批（采购下单）**：`POST /erp/pu/purchaseOrder/confirm/{id}`，权限 `pu:purchaseOrder:confirm`，0 → 1。

**这一步是本系统最典型的审批语义**：草稿阶段采购单对库存、对在途、对 MRP **零影响**；点下"下单"的瞬间，这批料才计入在途，MRP 净缺口立刻下降。**金额分级审批是这一层最值得加的业务规则**——超过阈值需厂长确认，目前没有。

### 步骤 8　收货：仓管的独立确认，是对采购的实物校验

**谁做**：`wh`（`POST /erp/pu/purchaseOrder/receive`，权限 `pu:purchaseOrder:receive`）。planner 也有此权限，但职责上应由仓管执行——**采购和收货是不同的人，这就是职责分离（SoD）**。

入参 `{poId, actualArrivalDate, items:[{itemId, qty, batchNo}], requestKey}`，规则：

- **不得超收**：超过明细未收量直接拒绝（错误码 2006）；
- **按明细行写流水**：`st_stock_txn.source_line_id` 指向 `pu_purchase_order_item.item_id`，不是只记个总数；
- **分批收货**：收一半就是状态 2"部分收货"，在途余额只减已收部分；
- **幂等**：同一个 `requestKey` 重复提交返回首次结果，不会重复入库（这是验收必测项）。

### 步骤 9　工单：把"计划"冻结成"承诺"

**谁做**：`planner` 或 `production`（两者都有 `pr:workOrder:add`）。

`POST /erp/pr/workOrder` 入参 `{orderId, styleId}`。系统在这一刻做**三冻结**：

- `pr_work_order_sku`：冻结该工单每个 SKU 的计划数量（`UNIQUE(wo_id, sku_id)`）；
- `pr_work_order_material`：冻结物料、单位、单件用量、损耗率、计划领用量、累计已领（`UNIQUE(wo_id, material_id)`）；
- `pr_work_order_process`：冻结工序工价 `unit_price`/委外价 `outsource_price`。

约束：**一个订单款式只能有一个工单**（`(order_id, style_id)` 唯一），首期禁止拆单。自建备货工单的 `order_id` 可为空。

### 步骤 10　领料开工：齐套检查是硬闸门

**申请**：production。`POST /erp/pr/workOrder/issue/{id}`，权限 `pr:workOrder:issue`（production 与 wh 均有）。

规则（需求 §4.2）：**只检查现存库存，在途未到货的料不能领**。整个"检查 + 扣减结存 + 写流水"在**一个事务**内完成，并发领料靠行锁/条件更新兜底，数据库约束保证数量非负。不满足齐套返回 2005，库存不足返回 2003。

状态：工单 0 待下达 → 1 生产中。

### 步骤 11　报工：唯一同时产生"生产进度 + 员工工资"的动作

**谁做**：`production`。`POST /erp/pr/workOrder/process/report`，权限 `pr:process:report`。

入参 `{workOrderProcessId, skuId, empId, doneQty, badQty, reportDate, requestKey}`，规则（需求 §4.3）：

- `skuId` **必须属于该工单冻结的色码行**；
- `doneQty` = 本次**合格**数，`badQty` = 另外产生的不良数，两者不得为负；
- 每个 SKU × 工序的累计合格数**不得超过计划数**；
- 内部工序必填 `empId`，**委外工序不传员工、不产生员工计件**（只记外发价和完成数）；
- 不良数不计件、不入合格成品，返修后置。

**副作用**：`pr_process_report` 一笔事件 → `hr_piece_wage` 一笔计件（`report_id` 唯一），保证**一次报工只产生一次工资**；同时累加 `pr_work_order_process.done_qty/bad_qty`，并与逐 SKU 事件汇总对账。

### 步骤 12　完工入库：上限由报工决定，不由人填

**谁做**：production 或 wh（`POST /erp/pr/workOrder/finish/{id}`，权限 `pr:workOrder:finish`）。

入参 `{skuItems:[{skuId, goodQty, batchNo}], requestKey}`。**每个 SKU 的入库量上限 = min(冻结计划数, 最终工序该 SKU 累计合格数)**——也就是说，没报工就没法凭空入库，这条约束把生产数据和库存数据焊死了。

状态：工单 → 2 已完工；订单 1 → 3 已完工。

### 步骤 13　出货：建单、发货、签收三权分立

| 动作   | 权限                 | 谁做         | 规则                                |
| ---- | ------------------ | ---------- | --------------------------------- |
| 建出货单 | `sh:shipment:add`  | sales      | 行含 `skuId, batchNo, qty`，草稿不扣库存   |
| 发货   | `sh:shipment:ship` | **wh**     | 校验订单该 SKU 未发量 + 批次库存后出库，写流水，0 → 1 |
| 签收   | `sh:shipment:sign` | sales 或 wh | 1 → 2，签收日期单独记录                    |

**发齐判定是这条链路上最容易讲错的一点**（需求 §4.4、数据库文档 §3.2）：

- 按订单**每个色码行**累计有效发货量，全部达到订购量，整单才算"已发齐"；
- **部分发货不算发齐**，`full_ship_date` 保持为空；
- 发齐日期 = 让最后一个未满足行达标的**那一批发货日**；
- 全部签收后才从 4 推进到 5；已出库的出货单不允许直接取消。

**准交率** = 统计期内"已发齐且发齐日期 ≤ 承诺交期"的订单数 ÷ 统计期内已发齐订单数。未发齐订单不进分母，另列"逾期未发齐数量"。**无分母时显示"暂无数据"，不显示 100%**。

### 步骤 14　标准成本：整条链路的收口，也是最严的审批

**谁做**：`finance`。两段式，权限分离得非常干净：

1. **试算** `POST /erp/fn/settlement/calc`（`fn:settlement:calc`）：按冻结 BOM、选定参考采购价、冻结工价、费率生成/更新**草稿**及 `fn_cost_detail` 明细。草稿可以反复覆盖。
2. **确认** `POST /erp/fn/settlement/confirm/{id}`（`fn:settlement:confirm`）：前置条件是**工单已完工 + 订单该款式已发齐 + 明细可复算**。确认后 0 → 1，**冻结，拒绝重算与删除**（错误码 3002）。

口径：标准材料 = Σ(计划用量 × 冻结价)；标准人工 = Σ(计划合格数 × 冻结工价)；标准制造费用 = (材料+人工) × 冻结费率。标准毛利 = 约定收入 − 标准成本。

**必须讲清的边界**：这是**标准成本，不是实际成本**。实际领料差异、委外实际结算、退货折让、经营净利润**都不混入**。已确认结算**不回查** `v_material_last_price`——所以供应商后来调价，历史毛利按确认时的快照复算，数值不变（这是验收必测项）。

### 步骤 15　闭环：确认成本 → 新报价

`finance`（或 sales）调 `POST /erp/sd/quotation/fromSettlement`，只能引用**已确认**结算，复制 `unitCost` 为 `costRef` 并记录 `sourceSettleId`。sales 对客发出，客户接受后回到步骤 2。**跑完这一步，这条链路才算真正闭环**——第一轮靠估价，第二轮开始靠实测成本。

### 步骤 16　支线：计件工资

`hr` 用 `POST /erp/hr/salarySlip/generate` 按员工+工资期汇总（数据只读派生自报工事件，不是手填），`POST /erp/hr/salarySlip/confirm/{id}` 确认，**已确认的工资期不能覆盖**。复杂税费计算不在首期。

---

## 4. "申请 → 审批"对照表（答辩用这张）

| 单据     | 申请人（权限点）                          | 当前审批人                               | 建议审批人            | 审批做了什么                  |
| ------ | --------------------------------- | ----------------------------------- | ---------------- | ----------------------- |
| 销售订单   | sales（`sd:order:add`）             | sales（`sd:order:confirm`）           | 销售主管/厂长          | 冻结色码数量单价交期，进入 MRP       |
| 采购单    | planner（`pu:purchaseOrder:add`）   | planner（`pu:purchaseOrder:confirm`） | 厂长（按金额分级）        | 生效并计入在途                 |
| 采购收货   | 系统/supplier 到货                    | wh（`pu:purchaseOrder:receive`）      | wh + 质检（后置）      | 增加库存、减在途、改收货状态          |
| 工单领料   | production（`pr:workOrder:issue`）  | production / wh                     | 同上 + 仓管复核        | 齐套校验、扣库存、写流水            |
| 报工     | production（`pr:process:report`）   | production（班长复核可选）                  | 生产主管             | 累计进度 + 生成计件工资           |
| 完工入库   | production（`pr:workOrder:finish`） | production / wh                     | wh 实物验收          | 成品入库，受报工合格数封顶           |
| 出货发货   | sales（`sh:shipment:add`）          | wh（`sh:shipment:ship`）              | wh 发货 + sales 签收 | 出库扣减，判定是否发齐             |
| 成本结算   | finance（`fn:settlement:calc`）     | finance（`fn:settlement:confirm`）    | 成本主管/厂长          | 冻结成本快照，不可重算             |
| 工资单    | hr（`hr:salarySlip:generate`）      | hr（`hr:salarySlip:confirm`）         | 财务/厂长            | 冻结工资期                   |
| 报价     | finance/sales（`fromSettlement`）   | 客户 → sales（`accept`）                | 销售主管             | 报价生效/失效                 |
| 库存盘点调整 | wh（`st:txn:adjust`）               | wh                                  | 财务监盘             | 只生成差异流水，不得冒充当采购/生产/销售来源 |

---

## 5. 单据状态机（一句话记法）

| 单据                        | 状态流转                                              |
| ------------------------- | ------------------------------------------------- |
| 销售订单 `sd_order`           | 0草稿 → 1已确认 → 2生产中 → 3已完工 → 4已发齐 → 5已完成；无下游可 → 9取消 |
| 采购单 `pu_purchase_order`   | 0草稿 → 1已下单 → 2部分收货 → 3全部收货                        |
| 工单 `pr_work_order`        | 0待下达 → 1生产中 → 2已完工                                |
| 出货单 `sh_shipment`         | 0草稿 → 1已发货 → 2已签收                                 |
| 成本结算 `fn_cost_settlement` | 0试算 → 1确认                                         |
| 报价 `sd_quotation`         | 0待确认 → 1已接受 / 2已失效                                |

非法跳转返回 `2001`；**已记账动作不得通过取消回滚**——这是原则，不是可选项。

---

## 6. 三条支线

**退货**（sales 登记 → wh 处理）：`POST /erp/sh/return` 记录来源出货、SKU、数量、退款金额及是否回库；`POST /erp/sh/return/handle/{id}` 按 **SKU/批次**核对并写回库流水——不能只按退货单总量检查。不得超原出货数量，不回库不得生成入库流水。换货的替代发运后置；**退款只在独立净收入报表反映，不冲回已确认标准成本**。

**盘点**（wh）：`POST /erp/st/txn/adjust` 输入实际数量、批次、仓库、原因，服务端**只生成差异流水**并更新结存。手工入/出库限定仓管，且**禁止冒充采购/生产/销售来源类型**——防止用手工调整掩盖真实业务差异。

**AI 与看板**（boss 为主）：`/ai/erp/ask` 的 `mode` 只能是 `orders/inventory/shortage/cost/ontime`，先校验工作台权限、再校验对应读权限，最多返回 20 条只读记录，附数据时间与来源。**模型只能解释，不能改写业务状态**；模型不可用时，规则预警和看板照常工作。

---

## 7. 贯穿全链路的一致性红线

1. **只增不改**：库存流水、报工事件、计件事件一旦写入永不修改。要纠错走冲销（首期冲销后置，直接拒绝删除）。
2. **原子提交**：状态变更 + 库存副作用 + 计件副作用 + `biz_action_request` 记录在同一事务内。
3. **幂等**：收货、领料、报工、完工、发货、退货回库都带 `requestKey`，由 `biz_action_request` 保证同一请求只生效一次；参数不同却复用同一键返回冲突（2007）。
4. **服务端权威**：金额、合计、可发量、发齐判定、成本全部服务端算，前端只显示。
5. **冻结优先**：工单冻结 BOM/工价，结算冻结价格快照，主数据后续变更不回改历史。
6. **留痕**：所有动作记录操作人、时间、来源单据。

---

## 8. 现状缺口与改进建议

按"改动成本从低到高"排列，都是真实存在的问题，不是吹毛求疵：

1. **主数据写权限过度集中在 admin**。款式/BOM/工序/物料/供应商的新增编辑目前**只有 admin 有**（`90052–90069` 系列菜单没分给任何业务角色）。建议新增"技术/资料员"角色承接 BOM 与工艺维护，否则 admin 会成为流程瓶颈。
2. **供应商维护缺位**：`planner` 对 `md_supplier` 只有 `list`，建采购单却不能维护供应商。要么给 planner 授权，要么明确"供应商由 admin 统一维护"。
3. **自审问题**：订单、采购、成本、工资目前都由同一角色既建又确认。加真审批只需改 `sys_role_menu` 授权（把 `:confirm` 收给主管），**不需要改代码**——这是权限点分离设计带来的红利，值得重点讲。
4. **无金额分级审批**：采购单没有按金额分级的审批规则。可在 `pu:purchaseOrder:confirm` 之外引入阈值路由（后续版本）。
5. **冲销缺失**：已记账单据的冲销流程后置。目前只能拒绝操作，长跑会积累"想改改不了"的单据。
6. **报工无复核**：报工直接生成计件工资，缺少班长/质检复核环节，是工资准确性的单点风险。

---

## 9. 验收时重点测这 8 条

1. 重复收货/重复报工各提交两次 → 只生效一次（幂等）
2. 部分收货后在途余额正确；领料后 MRP 需求下降
3. 库存不足时出库被拒（2003）；并发出库不超卖
4. 超收、超发被拒（2006）
5. 部分发货不算发齐；全部发齐只置一次 `full_ship_date`
6. 调价后已确认成本快照不变（3002 拒绝重算）
7. 报价 `source_settle_id` 可追溯且只指向已确认结算
8. 无成本权限的角色访问看板拿不到 `standardGrossProfit`；AI 越权查询返回 4002

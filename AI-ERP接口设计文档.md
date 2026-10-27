# AI 驱动精益 ERP 系统 - 接口设计文档

> 版本：**v3.1**，2026-10-28。
> 依据：`master` 分支代码实测核对 —— **43 个 Controller（自研 20 个）、260 个方法级端点（自研 135 个）、228 处生效的 `@PreAuthorize`（另有 21 处历史注释未启用）**，逐条比对 `@RequestMapping` / `@PreAuthorize` / 请求体记录类与数据库 `sys_menu`（186 项菜单、184 个权限码）。
> 配套：《AI-ERP需求分析文档》《AI-ERP技术方案文档》《AI-ERP数据库设计文档》。启动与联调步骤见 `开发启动说明.md`。

## 0. 本次修订要点

相对 v2.0（2026-10-14）的实际变化：

| 类别 | 变化 |
| --- | --- |
| 新增资金域 | `/erp/finance/*` 16 个端点：资金账户、收付款单、应收应付、账龄汇总、月度现金流、会计期间、资金概览 |
| 新增协作流 | `/erp/workflow/todos`、`/erp/workflow/approvals`；采购超阈值转审批 |
| 新增删除/作废 | 7 类单据统一提供 `DELETE /{id}` 与 `POST /cancel/{id}`，规则集中在 `ErpDocService` |
| 新增草稿编辑 | `PUT /erp/sd/order/{id}`、`PUT /erp/pu/purchaseOrder/{id}` |
| 新增审批动作 | `POST /erp/pu/purchaseOrder/approve/{id}`、`/reject/{id}`，阈值 `erp.purchase.approval.threshold`，默认 10000 |
| 新增生产动作 | `POST /erp/pr/workOrder/generate/{orderId}`（按订单生成工单）、`POST /erp/pr/workOrder/return`（退料） |
| 新增人事动作 | 员工离职/复职 `POST /erp/hr/employee/{id}/status`、工资批量生成 `generateBatch`、批量确认 `confirmBatch`、发放 `pay`、作废 `void` |
| 新增主数据 | 主数据的增删改改为 `{resource}` 动态路由；BOM/工序独立保存端点；工序派工 `GET|POST|DELETE /erp/md/process/{id}/worker[/{empId}]` |
| **修正** | v2.0 把 `/list` 一律写成"分页"，实际只有单据类分页；看板、库存、缺料返回**全量** `data` 数组，见 2.1 |
| **修正** | 报价单状态机补 `3 已转单`：建单时带 `quoteId` 会把已接受报价置 3，同一报价只能转一次，见第 5 节 |
| **已落地** | 资金域与协作流建表脚本 `sql/ai-erp-12-finance-workflow.sql` 已导入演示库（`fn_account`、`fn_cash_txn`、`fn_payment`、`biz_approval`、`biz_period`、`biz_todo` 等表均在）；ERP 业务域权限码已全量入库，见第 7 节 |

## 1. 底座协议

后端基于 RuoYi 派生底座。业务前缀 `/erp/{md|sd|pu|pr|st|sh|hr|fn}/...`，资金域另有 `/erp/finance/...`，看板为 `/erp/dashboard/...`，AI 为 `/ai/...`。

| 接口形态 | 实际响应字段 | 前端取值 |
| --- | --- | --- |
| `POST /login` | 顶层 `code,msg,token` | `response.token` |
| `GET /getInfo` | 顶层 `code,msg,user,roles,permissions` | 从顶层读取 |
| `GET /getRouters` | 顶层 `code,msg,data` | `response.data` 为路由树 |
| `TableDataInfo` 分页 | 顶层 `code,msg,rows,total` | 分页适配层 |
| `AjaxResult` | 顶层 `code,msg,data` | 业务 API 读 `data` |

- 业务成功码统一 `200`；**认证/权限失败时 HTTP 仍是 200**，真实结果在响应体 `code`（401/403），排查不能只看状态码。
- 所有 ID 在 JSON 中按字符串处理，前端不得转 Number 再传回。金额与数量为十进制，前端只做展示与输入校验，合计以服务端为准。
- 客户端不得传 `createBy/updateBy/delFlag/totalAmount/settlementAmount/status` 等权威字段，传了也会被服务端覆盖。
- 日期 `yyyy-MM-dd`，时间 `yyyy-MM-dd HH:mm:ss`，统一业务时区（Asia/Shanghai）。

## 2. 通用约定

### 2.1 列表接口的两种形态（易错点）

| 形态 | 返回 | 端点 |
| --- | --- | --- |
| **分页** | `TableDataInfo{code,msg,rows,total}` | 共 22 个：主数据 9 类列表、员工、计件明细、工资单、报价、工单、库存流水、出货、退货、结算、销售订单、采购单、看板准时率 |
| **全量** | `AjaxResult{code,msg,data:[...]}` | `GET /erp/st/inventory/list`、`GET /erp/pu/suggestion/list`、`GET /erp/dashboard/{overview,mrp,alerts}`、`GET /erp/finance/*`、`GET /erp/workflow/*` |

分页参数统一 `pageNum`（从 1 开始）、`pageSize`（默认 10，服务端夹取上限 500）。只有**销售订单与采购单**列表支持 `keyword` 模糊匹配（单号/客户名、单号/供应商名）；**主数据、报价、退货、结算列表只接受 `pageNum/pageSize`**，没有 `keyword`；工单、计件、工资单、库存流水、出货单列表另接受 `month`（`yyyy-MM`）按月过滤。需要关键字检索的页面，前端需在服务端补参数或在客户端过滤。

### 2.2 权限

权限格式 `{domain}:{resource}:{action}`，`@PreAuthorize` 一律写在 Controller，前端按钮显隐不能替代授权。

- 主数据使用**动态权限**：`@ss.hasPermi('md:' + #resource + ':add')`，`resource` 取值 `category/color/size/style/sku/material/process/customer/supplier`。
- 主数据详情 `GET /erp/md/{resource}/{id}` 校验的是 `md:{resource}:list`，不是 `query`。
- 权限不足由 RuoYi 全局异常处理包装，返回体 `code=403`、HTTP 200。

### 2.3 幂等 `requestKey`

会产生库存、资金或状态副作用的动作必须携带 `requestKey`，由 `biz_action_request` 统一约束：同键同参返回首次结果，同键不同参返回 `2007`。适用端点：收货、领料、报工、完工、退料、发货、退货处理、盘点、结算试算、收付款确认、工资发放、采购建议生成、报价转单。

一次动作可产生多条流水时，幂等键落在动作级（`biz_action_request`），不在每条流水上单独唯一。

### 2.4 删除与作废

删除只对草稿开放并**物理删除**（含子表）；已推进的单据只能作废（`status=9`）。两者都会检查下游引用，被引用时返回 `2007` 并指明引用单据。

| 单据 | 可删除状态 | 可作废状态 | 下游引用检查 |
| --- | --- | --- | --- |
| 销售订单 `sd:order` | 0 草稿 | 0、1 | 工单、出货单（未作废） |
| 采购单 `pu:purchaseOrder` | 0 草稿 | 0、1 | 无（明细随单删） |
| 生产工单 `pr:workOrder` | 0 待开工 | 0 待开工 | 报工记录；删除时释放库存预留 |
| 出货单 `sh:shipment` | 0 待发货 | 0 待发货 | 退货单（未作废） |
| 退货单 `sh:return` | 0 待处理 | 0 待处理 | 无 |
| 报价单 `sd:quotation` | 0 待确认 | 不支持（用 `expire`） | 无 |
| 成本结算 `fn:settlement` | 0 试算 | 不支持 | 引用该结算的报价明细 |

### 2.5 审批与会计期间

- **采购审批**：`confirm` 时若 `totalAmount > erp.purchase.approval.threshold`（默认 `10000`），单据不置为已下单，而是 `approval_status=1` 待审批并返回 `{status:"pending_approval"}`；未超阈值直接 `status=1` 返回 `{status:"confirmed"}`。审批通过才 `status=1`、`approval_status=2`，驳回停在 `status=0`、`approval_status=3`，可改后重新提交（待审批状态为 0 或 3 时都允许重新提交）。
- **采购付款联动**：`confirm` 未超阈值、以及 `approve` 通过这两种情况，都会立即调用资金域生成采购付款并扣减账户余额，因此付款流水在审批或下单当下就可见，不是收货后才付。
- **会计期间**：`biz_period.period` 格式 `yyyy-MM`。已结账期间（`status=1`）拒绝补录与过账，返回 `2001`；受校验的动作包括**采购收货、发货、退货处理、报工、盘点、收付款建单与确认、工资生成与发放**。关闭/重开需要 `fn:period:close` / `fn:period:reopen`。

## 3. 接口总览

> 「权限」列为服务端 `@PreAuthorize` 要求的权限码，ERP 业务域的权限码已全部在 `sys_menu` 入库。权限码存在不等于角色可用，实际可用性见第 6 节矩阵；未获授权的角色调用返回 **HTTP 200 + 响应体 `code=403`**。

本节共列出 **123 个**自研端点（ERP 业务 121 个 + AI 2 个）。另有单据冻结 `/system/billFrozen` 6 个、编码规则 `/system/billcodeRule` 6 个属于系统管理模块，不在此展开；自研端点合计 135 个，全库（含 RuoYi 底座 125 个）共 260 个。

### 3.1 主数据 `/erp/md`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/md/{category\|color\|size\|style\|sku\|material\|process\|customer\|supplier}/list` | `md:{res}:list` | 分页，仅 `pageNum/pageSize` |
| GET | `/erp/md/{resource}/{id}` | `md:{res}:list` | 主数据详情 |
| POST | `/erp/md/{resource}` | `md:{res}:add` | 新增；`sku` 走生成接口，直接新增返回 1001 |
| PUT | `/erp/md/{resource}/{id}` | `md:{res}:edit` | 修改 |
| DELETE | `/erp/md/{resource}/{id}` | `md:{res}:remove` | 删除，被引用时拒绝 |
| POST | `/erp/md/sku/generate` | `md:sku:generate` | `{styleId,colorId,sizeId}`，按三元组去重，已存在直接返回原 id |
| GET | `/erp/md/sku/options` | `md:sku:generate` | 返回 `{styles,colors,sizes}` 选项集 |
| GET | `/erp/md/style/{id}/bom` | `md:style:list` | 款式 BOM 明细 |
| PUT | `/erp/md/style/{id}/bom` | `md:style:edit` | `{items:[{materialId,qty,lossRate}]}` 整体覆盖 |
| GET | `/erp/md/style/{id}/process` | `md:style:list` | 款式工序明细 |
| PUT | `/erp/md/style/{id}/process` | `md:style:edit` | `{items:[{processId,seq,unitPrice}]}` 整体覆盖 |
| GET | `/erp/md/style/options` | `md:style:edit` | 工序/BOM 编辑页的选项集 |
| GET | `/erp/md/process/{id}/worker` | `md:process:assign` | 工序可选员工列表 |
| POST | `/erp/md/process/{id}/worker/{empId}` | `md:process:assign` | 给工序指派员工（报工校验用） |
| DELETE | `/erp/md/process/{id}/worker/{empId}` | `md:process:assign` | 取消工序与员工的绑定 |
| GET | `/erp/md/orderOptions` | `sd:order:add` | 建单页选项（客户、款式、色码、SKU） |
| GET | `/erp/md/purchaseOptions` | `pu:purchaseOrder:add` | 采购单页选项（供应商、物料） |
| GET | `/erp/md/workOrderOptions` | `pr:process:report` | 报工页员工选项，可带 `workOrderProcessId` 按工序筛人 |

主数据字段（新增/修改入参，选填项可省略）：`category{name,sort,status}`、`color{code,name,sort,status}`、`size{code,name,sizeGroup,sort,status}`、`style{code,name,categoryId,gender,season,unit,baseCost,status}`、`material{code,name,category,spec,unit,refPrice,status}`、`process{code,name,sort,status}`、`customer|supplier{code,name,contact,phone,address,creditDays,status}`、`sku{barcode,status}`。

### 3.2 销售 `/erp/sd`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/sd/order/list` | `sd:order:list` | 分页，支持 `keyword` |
| GET | `/erp/sd/order/{id}` | `sd:order:query` | 主子表聚合，含每行已发/未发数量 |
| POST | `/erp/sd/order` | `sd:order:add` | 建草稿，服务端校验行唯一与正数并计算总额；带 `quoteId` 时校验报价已接受且客户一致 |
| PUT | `/erp/sd/order/{id}` | `sd:order:edit` | 修改草稿（仅 status=0） |
| POST | `/erp/sd/order/confirm/{id}` | `sd:order:confirm` | 0→1，冻结色码、数量、单价、交期 |
| DELETE | `/erp/sd/order/{id}` | `sd:order:remove` | 删草稿 |
| POST | `/erp/sd/order/cancel/{id}` | `sd:order:cancel` | 0/1→9 |
| GET | `/erp/sd/quotation/list` | `sd:quotation:list` | 分页 |
| GET | `/erp/sd/quotation/{id}` | `sd:quotation:query` | 明细 |
| POST | `/erp/sd/quotation` | `sd:quotation:add` | 直接建报价草稿 |
| POST | `/erp/sd/quotation/fromSettlement` | `sd:quotation:fromSettlement` | 由已确认结算转报价，复制 `unitCost` 为 `costRef` |
| POST | `/erp/sd/quotation/accept/{id}` | `sd:quotation:accept` | 0→1 |
| POST | `/erp/sd/quotation/expire/{id}` | `sd:quotation:expire` | 0→2 |
| DELETE | `/erp/sd/quotation/{id}` | `sd:quotation:remove` | 删草稿 |

**报价转订单**没有独立的 `convert` 端点，是在 `POST /erp/sd/order` 带 `quoteId` 时隐式完成的：报价须为 `status=1` 且客户与订单一致，建单成功后报价置 `status=3 已转单`。同一张报价只能转一次——再次带同一 `quoteId` 建单返回 `2006`，避免重复生产与重复应收。

### 3.3 采购 `/erp/pu`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/pu/suggestion/list` | `pu:suggestion:list` | **全量**净缺料：需求、库存、在途剩余、净缺口 |
| POST | `/erp/pu/suggestion/generate` | `pu:suggestion:generate` | 按物料+供应商生成采购草稿，重复请求不重复建单 |
| GET | `/erp/pu/purchaseOrder/list` | `pu:purchaseOrder:list` | 分页，支持 `keyword` |
| GET | `/erp/pu/purchaseOrder/{id}` | `pu:purchaseOrder:query` | 主子表 |
| POST | `/erp/pu/purchaseOrder` | `pu:purchaseOrder:add` | 建草稿 |
| PUT | `/erp/pu/purchaseOrder/{id}` | `pu:purchaseOrder:edit` | 改草稿 |
| POST | `/erp/pu/purchaseOrder/confirm/{id}` | `pu:purchaseOrder:confirm` | 下单；超阈值转审批，`status` 区分；未超阈值下单并立即生成采购付款 |
| POST | `/erp/pu/purchaseOrder/approve/{id}` | `pu:purchaseOrder:approve` | `{comment}`；`status=0,approval_status=1` → 已下单，同时自动付款 |
| POST | `/erp/pu/purchaseOrder/reject/{id}` | `pu:purchaseOrder:reject` | `{comment}`；`approval_status=3`，单据停在草稿 |
| POST | `/erp/pu/purchaseOrder/receive` | `pu:purchaseOrder:receive` | 分批收货，不得超明细未收量 |
| DELETE | `/erp/pu/purchaseOrder/{id}` | `pu:purchaseOrder:remove` | 删草稿 |
| POST | `/erp/pu/purchaseOrder/cancel/{id}` | `pu:purchaseOrder:cancel` | 0/1→9 |

### 3.4 生产 `/erp/pr`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/pr/workOrder/list` | `pr:workOrder:list` | 分页，支持 `month` 按月过滤 |
| GET | `/erp/pr/workOrder/{id}` | `pr:workOrder:query` | 含 SKU、用料、工序与累计数量 |
| POST | `/erp/pr/workOrder` | `pr:workOrder:add` | `{orderId,styleId}`，冻结 SKU/BOM/工价 |
| POST | `/erp/pr/workOrder/generate/{orderId}` | `pr:workOrder:generate` | 按订单自动生成（一订单款式一工单） |
| POST | `/erp/pr/workOrder/issue/{id}` | `pr:workOrder:issue` | 领料开工：扣原料库存、工单 0→1、订单 1→2 |
| POST | `/erp/pr/workOrder/return` | `pr:workOrder:return` | 退料回库，仅生产中工单 |
| POST | `/erp/pr/workOrder/process/report` | `pr:process:report` | 报工，须先报完前置工序，内部工序必填 `empId` 并生成计件 |
| POST | `/erp/pr/workOrder/finish/{id}` | `pr:workOrder:finish` | 完工入库，受最终工序合格量约束 |
| DELETE | `/erp/pr/workOrder/{id}` | `pr:workOrder:remove` | 删待开工工单，释放预留 |
| POST | `/erp/pr/workOrder/cancel/{id}` | `pr:workOrder:cancel` | 0→9 |

### 3.5 库存 `/erp/st`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/st/inventory/list` | `st:inventory:list` | **全量**结存（物料+成品+批次） |
| GET | `/erp/st/txn/list` | `st:txn:list` | 分页只增流水 |
| POST | `/erp/st/txn/adjust` | `st:txn:adjust` | 盘点：只生成差异流水，`requestKey` 幂等 |

### 3.6 出货与退货 `/erp/sh`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/sh/shipment/list` | `sh:shipment:list` | 分页，支持 `month` 按月过滤 |
| GET | `/erp/sh/shipment/{id}` | `sh:shipment:query` | 主子表 |
| POST | `/erp/sh/shipment` | `sh:shipment:add` | 建草稿，行含 `skuId,batchNo,qty` |
| POST | `/erp/sh/shipment/ship/{id}` | `sh:shipment:ship` | 确认发货，校验未发量与批次库存后出库 |
| POST | `/erp/sh/shipment/sign/{id}` | `sh:shipment:sign` | 登记签收 |
| DELETE | `/erp/sh/shipment/{id}` | `sh:shipment:remove` | 删草稿 |
| POST | `/erp/sh/shipment/cancel/{id}` | `sh:shipment:cancel` | 0→9 |
| GET | `/erp/sh/return/list` | `sh:return:list` | 分页 |
| GET | `/erp/sh/return/{id}` | `sh:return:query` | 主子表 |
| POST | `/erp/sh/return` | `sh:return:add` | 登记退货，`isRestock=1` 才回补库存 |
| POST | `/erp/sh/return/handle/{id}` | `sh:return:handle` | 处理退货并按批次回库 |
| DELETE | `/erp/sh/return/{id}` | `sh:return:remove` | 删待处理退货 |
| POST | `/erp/sh/return/cancel/{id}` | `sh:return:cancel` | 0→9 |

### 3.7 人事 `/erp/hr`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/hr/employee/list` | `hr:employee:list` | 分页 |
| GET | `/erp/hr/employee/options` | `hr:employee:list` | 在用部门/岗位选项 |
| GET | `/erp/hr/employee/{id}` | `hr:employee:list` | 详情 |
| POST | `/erp/hr/employee` | `hr:employee:add` | 工号唯一；`empType` 1 计件 2 固定薪 |
| PUT | `/erp/hr/employee/{id}` | `hr:employee:edit` | 修改 |
| POST | `/erp/hr/employee/{id}/status` | `hr:employee:edit` | `{status}`：0 离职 / 1 复职 |
| DELETE | `/erp/hr/employee/{id}` | `hr:employee:remove` | 无下游引用时删除 |
| GET | `/erp/hr/pieceWage/list` | `hr:pieceWage:list` | 分页，从报工事件派生只读，支持 `month` |
| GET | `/erp/hr/salarySlip/list` | `hr:salarySlip:list` | 分页，支持 `month` |
| POST | `/erp/hr/salarySlip/generate` | `hr:salarySlip:generate` | 单人单期间生成草稿 |
| POST | `/erp/hr/salarySlip/generateBatch` | `hr:salarySlip:generateBatch` | `{wagePeriod}` 整期批量生成 |
| POST | `/erp/hr/salarySlip/confirm/{id}` | `hr:salarySlip:confirm` | 确认后锁死该期间报工补录 |
| POST | `/erp/hr/salarySlip/confirmBatch` | `hr:salarySlip:confirm` | `{wagePeriod}` 整期批量确认，逐条返回 `confirmed` 与 `skipped[].reason` |
| POST | `/erp/hr/salarySlip/pay/{id}` | `fn:payroll:pay` | `{accountId,payDate,requestKey}`；生成工资付款单并扣账户余额，工资单转已发放 |
| POST | `/erp/hr/salarySlip/void/{id}` | `hr:salarySlip:confirm` | 已确认未发放的工资单退回草稿（1→0）；已发放不可作废 |

### 3.8 财务 `/erp/fn` 与 `/erp/finance`

**成本结算**

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/fn/settlement/list` | `fn:settlement:list` | 分页 |
| GET | `/erp/fn/settlement/{id}` | `fn:settlement:query` | 分项快照 + 标准成本标识 |
| POST | `/erp/fn/settlement/calc` | `fn:settlement:calc` | 试算/更新草稿，已确认不可重算 |
| POST | `/erp/fn/settlement/confirm/{id}` | `fn:settlement:confirm` | 仅在工单完工、款式发齐且可复算时确认 |
| DELETE | `/erp/fn/settlement/{id}` | `fn:settlement:remove` | 删试算草稿 |

**资金域**（共 16 个端点，`fn_account`、`fn_cash_txn`、`fn_payment`、`biz_period` 等表已建）

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/finance/accounts` | `fn:account:list` | 资金账户列表（含当前余额） |
| POST | `/erp/finance/accounts` | `fn:account:add` | 建账户，`accountType` 1 现金 2 银行 3 电子 |
| PUT | `/erp/finance/accounts/{id}` | `fn:account:edit` | 改账户 |
| GET | `/erp/finance/cash` | `fn:cashTxn:list` | 资金流水 |
| GET | `/erp/finance/receivables` | `fn:receivable:list` | 应收（含已收/未收） |
| GET | `/erp/finance/payables` | `fn:payable:list` | 应付 |
| GET | `/erp/finance/aging-summary` | `fn:receivable:list` | 账龄汇总（按账龄区间统计应收/应付余额） |
| GET | `/erp/finance/cash-flow-monthly` | `fn:cashTxn:list` | 月度现金流（按月汇总收入/支出） |
| GET | `/erp/finance/payments` | `fn:payment:list` | 收付款单列表 |
| GET | `/erp/finance/payments/{id}` | `fn:payment:list` | 收付款单详情 |
| POST | `/erp/finance/payments` | `fn:payment:add` | 建收付款单，行可核销到应收/应付 |
| POST | `/erp/finance/payments/confirm/{id}` | `fn:payment:confirm` | 确认过账，写流水并更新账户余额 |
| GET | `/erp/finance/overview` | `fn:account:list` | 资金概览（账户合计、应收应付） |
| GET | `/erp/finance/periods` | `fn:period:list` | 会计期间列表 |
| POST | `/erp/finance/period/close` | `fn:period:close` | `{period:"yyyy-MM"}` 结账 |
| POST | `/erp/finance/period/reopen` | `fn:period:reopen` | 反结账，未关闭时报 2001 |

### 3.9 看板 `/erp/dashboard`

| 方法 | 路径 | 权限 | 返回 |
| --- | --- | --- | --- |
| GET | `/erp/dashboard/overview` | `dashboard:overview:list` | 准交率、逾期数、净缺料数；无 `fn:settlement:list` 时剔除 `standardGrossProfit` |
| GET | `/erp/dashboard/mrp` | `dashboard:mrp:list` | 物料需求/库存/在途/净缺口（全量） |
| GET | `/erp/dashboard/ontime` | `dashboard:ontime:list` | 分页，发齐日期、逾期天数，支持 `asOfDate` |
| GET | `/erp/dashboard/styleCost` | `dashboard:styleCost:list` | 分页，已确认结算的款式标准成本与毛利 |
| GET | `/erp/dashboard/alerts` | `dashboard:alerts:list` | 确定性规则预警及触发值 |

### 3.10 协作流 `/erp/workflow`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/erp/workflow/todos` | `workflow:todo:list` | 当前登录人的待办（按角色过滤） |
| GET | `/erp/workflow/approvals` | `workflow:approval:list` | 待审批单据 |

### 3.11 AI `/ai`

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| POST | `/ai/erp/ask` | `dashboard:overview:list` | `{mode,question}`，`mode∈{orders,inventory,shortage,cost,ontime}`，最多 20 条只读记录 |
| POST | `/ai/alerts/explain` | `dashboard:alerts:explain` | 对已有预警生成解释，模型不可用时回落规则说明 |

`/ai/erp/ask` 先校验工作台权限 `dashboard:overview:list`，再按 `mode` 校验对应业务读权限（orders→`sd:order:list`、inventory→`st:inventory:list`、shortage→`pu:suggestion:list`、cost→`dashboard:styleCost:list`、ontime→`dashboard:ontime:list`），越权返回 `4002`。模型只解释服务端查出的只读结果，不生成 SQL、不改单据；每用户每分钟限 10 次，问题长度 1~500 字；走 DeepSeek HTTP 接口（`DeepSeekConfig`），未配置或调用失败时降级为数据摘要并置 `modelUsed=false`。全库 AI 端点仅此 2 个，没有 `/ai/chat`、`/ai/nl2sql`、`/ai/metadata`。

## 4. 关键报文

### 4.1 销售订单

```json
// POST /erp/sd/order
{ "customerId": "3", "deliveryDate": "2026-11-20", "quoteId": "12",
  "items": [{ "styleId": "5", "colorId": "2", "sizeId": "7", "qty": 300, "unitPrice": 128.00 }] }
// 200 → { "code":200, "data": { "orderNo":"SO2026000007", "id":"8" } }
```

`quoteId` 可空；带值时即为"报价转订单"，要求该报价 `status=1` 且客户一致，建单成功后报价置 `3 已转单`。服务端校验：行不为空且 ≤ 100 行、`(styleId,colorId,sizeId)` 不重复、数量为正、交期不早于当天、SKU 属于对应款式色码；总额由服务端计算。

### 4.2 采购收货

```json
// POST /erp/pu/purchaseOrder/receive
{ "poId": "9", "actualArrivalDate": "2026-10-27",
  "items": [{ "itemId": "21", "qty": 500, "batchNo": "B20261027-1" }],
  "requestKey": "rcv-9-20261027-1" }
```

约束：`actualArrivalDate` **不得晚于当天**；累计收货量不得超明细数量；按 `itemId` 写库存流水，收满转已到货。

### 4.3 报工与完工

```json
// POST /erp/pr/workOrder/process/report
{ "workOrderProcessId": "33", "skuId": "18", "empId": "6",
  "doneQty": 100, "badQty": 2, "reportDate": "2026-10-27", "requestKey": "rep-33-1" }

// POST /erp/pr/workOrder/finish/{id}
{ "skuItems": [{ "skuId": "18", "goodQty": 98, "batchNo": "F20261027-1" }], "requestKey": "fin-8-1" }
```

`doneQty` 是本次合格数，`badQty` 是另计的不良数；`reportDate` 不得晚于当天；内部工序必须填 `empId` 才生成计件工资，委外工序不传员工。完工入库量不得超过冻结计划量与**最终工序**累计合格数。

### 4.4 出货与退货

```json
// POST /erp/sh/shipment
{ "orderId": "8", "logisticsCompany": "顺丰", "logisticsNo": "SF123456",
  "items": [{ "skuId": "18", "batchNo": "F20261027-1", "qty": 100 }] }

// POST /erp/sh/return
{ "shipmentId": "3", "returnType": 1, "isRestock": 1, "reason": "色差",
  "refundAmount": 1280.00, "items": [{ "skuId": "18", "batchNo": "F20261027-1", "qty": 10 }] }
```

`returnType`：1 退货入库 2 换货 3 报废。类型与回库标记是**联动校验**的：1/2 必须 `isRestock=1`，3 报废必须 `isRestock=0`；换货与报废的 `refundAmount` 必须为 0，只有退货入库可带退款金额。明细不得超原出货数量。

### 4.5 成本结算与转报价

```json
// POST /erp/fn/settlement/calc
{ "orderId": "8", "styleId": "5", "requestKey": "calc-8-1" }

// POST /erp/sd/quotation/fromSettlement
{ "settleIds": ["4"], "requestKey": "qt-from-4" }
```

结算按冻结 BOM、单价、工价、费率生成草稿与明细；确认需对应工单已完工、订单款式已发齐。转报价只接受**已确认**结算，复制 `unitCost` 为 `costRef` 并记 `sourceSettleId`。

### 4.6 资金收付款

```json
// POST /erp/finance/payments
{ "accountId": "1", "direction": 1, "bizType": 1,
  "counterpartyType": 1, "counterpartyId": "3", "amount": 38400.00,
  "payDate": "2026-10-27", "remark": "订单 SO2026000007 首款",
  "lines": [{ "targetType": 1, "targetId": "5", "amount": 38400.00 }],
  "requestKey": "pay-20261027-1" }
```

`direction`：1 收 2 付；`bizType`：1 销售收款 2 采购付款 3 工资发放 4 退款 5 其他收 6 其他付；`targetType`：1 应收 2 应付。确认后才写流水、动账户余额。

### 4.7 盘点

```json
// POST /erp/st/txn/adjust
{ "itemType": 1, "itemId": "12", "batchNo": "B20261001", "actualQty": 480.5,
  "reason": "月末盘点差异", "requestKey": "adj-12-20261027" }
```

服务端只写差异流水（盘盈 bizType=5 / 盘亏 6）并更新结存，不直接改库存数。

## 5. 状态机

| 单据 | 状态流转 |
| --- | --- |
| 销售订单 | 0 草稿 → 1 已确认 → 2 生产中 → 3 已完工 → 4 已出货 → 5 已完成；0/1 可 → 9 已取消 |
| 采购单 | 0 草稿 → 1 已下单 → 2 部分到货 → 3 已到货；0/1 可 → 9 已取消 |
| 采购审批 | 0 免审/未提交 → 1 待审批 → 2 已通过 / 3 已驳回 |
| 生产工单 | 0 待开工 → 1 生产中 → 2 已完工；0 可 → 9 已取消 |
| 工单工序 | 0 待开工 → 1 进行中 → 2 已完成 |
| 出货单 | 0 待发货 → 1 已发货 → 2 已签收；0 可 → 9 已取消 |
| 退货单 | 0 待处理 → 1 已处理；0 可 → 9 已取消 |
| 报价单 | 0 待确认 → 1 已接受 / 2 已失效；1 已接受 → 3 已转单（建单带 `quoteId` 时写入，不可逆且只能一次） |
| 成本结算 | 0 试算 → 1 已确认 |
| 工资单 | 0 草稿 → 1 已确认 → 2 已发放；1 可作废回 0，2 不可回退 |
| 应收/应付 | 0 未收付 → 1 部分 → 2 已结清 |
| 会计期间 | 0 开放 → 1 已结账（可反结账回 0） |

关键联动：

- 领料开工一次推动三件事 —— 扣原料库存、工单 0→1、订单 1→2。
- 工单完工以**最终工序**合格量校验，必须逐道工序报工；同一 SKU 完工量不得超过冻结计划量与最终工序合格量。
- 出货只要求订单处于 2/3（生产中或已完工），**不要求已完工**；发齐转 4，全部签收后转 5。
- 报价转订单只能转一次：`sd_quotation` 由 1 置 3，重复用同一 `quoteId` 建单返回 `2006`。
- 结算确认后才允许从结算单生成报价单，形成成本闭环。
- 工资单确认后锁死该工资期的报工补录；发放后状态不可回退，已发放工资单不允许重新生成。
- 非法跳转统一 `2001`；已记账动作不得通过取消回滚。

## 6. 角色权限矩阵

按数据库 `sys_role_menu` 现状（admin 持 `*:*:*` 不受限制）：

| 域 | sales | planner | production | wh | finance | hr | boss |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 主数据 | 客户增/改/删 | 9 类主数据增删改查；SKU 生成与删除（分类/颜色/尺码/工序的增改也在此） | 物料/款式/SKU 只读；工序查与派工 | 无 | 无 | 无 | 9 类主数据只读 |
| 销售 | 订单增改/确认/作废/删除；报价**新建/编辑**/接受/失效/**转订单**/删除；出货建单/发货/签收；退货建单/处理 | 订单、出货、退货只读 | 订单只读 | 出货全流程、退货全流程 | 订单/报价只读 | 无 | 订单/报价只读 |
| 采购 | 无 | 建议生成；采购单增改删、确认、收货、作废 | 无 | 采购单只读、收货 | 无 | 无 | 采购单只读、审批通过/驳回 |
| 生产 | 无 | 工单建单/删除/作废（**不含领料、报工**） | 工单建单/删除/作废、**一键生成**、领料、报工、完工、**退料** | 工单只读、领料、完工 | 无 | 无 | 工单只读 |
| 库存 | 无 | 无 | 结存/流水只读 | 结存、流水、盘点 | 无 | 无 | 结存/流水只读 |
| 成本 | 无 | 无 | 无 | 无 | 结算试算/确认/查询/删除 | 无 | 结算只读 |
| 人事 | 无 | 无 | 无 | 无 | 无 | 员工增删改查、计件、工资单生成（单条+**批量**）/确认/查询 | 工资单只读 |
| 资金与协作 | 无 | 无 | 无 | 无 | **账户增改、流水、应收应付、收付款登记与确认、期间结账/反结账、工资发放**；成本结算；订单/出货/报价只读 | 无 | 账户/流水/应收/应付/收付款/期间只读、待办与审批 |
| 看板 | overview/alerts/ontime | overview/mrp/alerts | overview/alerts | overview/alerts | overview/ontime/styleCost/alerts | overview | 全部 6 项 |

三点需要留意：

- **权限重叠是有意的**：收货权限同时给了 planner 和 wh；领料/完工权限同时给了 production 和 wh。这是业务并行导致的，改动前需确认现场分工。
- **资金域按「谁执行谁授权」拆分**：`finance` 拿到资金全流程（账户增改、收付款登记与确认、期间结账/反结账、工资发放），`boss` 只保留只读 + 采购审批。这样「同一个财务页面，finance 能登记收付款、boss 只能看」的对比可以现场演示。
- **授权由脚本保证**：`sql/p4-rbac-completion.sql` 补齐新增按钮权限并带 7 项自检，其中「无遗留孤儿按钮权限」要求除运维专用的 `monitor:operlog:remove` 外，每个按钮至少有一个岗位能用；「厂长资金权限保持只读」防止补权限时把写权限给到 boss。本轮就靠它修掉了「finance 打不开财务页」「销售不能新建报价」两个真实缺口。

## 7. 实现进度与依赖（重要）

### 7.1 已实现：全链路可跑通

后端：6 个 Maven 业务模块 —— `ruoyi-common`(103)、`ruoyi-framework`(44)、`ruoyi-quartz`(18)、`myfactoryai-system`(61)、`myfactoryai-manufacturing`(55)、`myfactoryai-admin`(44)，合计 **325 个非测试 Java 文件**，另有 6 个测试类 32 个用例。**43 个 REST 控制器、260 个方法级端点、228 处生效的 `@PreAuthorize`**（自研 20 个控制器、135 个端点）。业务代码集中在 manufacturing 的 `net.factoryopen.myfactoryai.erp` 包（domain/mapper/service/support 共 55 个文件、约 5050 行），自研 Service 15 个、Mapper 17 个。AI 能力没有独立模块，`DeepSeekConfig` 位于 `myfactoryai-admin` 的 `web.ai` 包。

前端：Vue 3 + TypeScript + Vite + Element Plus + Pinia，`src` 下 31 个 `.vue`（views 28 个）、15 个 `.ts`，`api` 6 个模块（catalog/client/erp/finance/labels/system）共 100 余个接口函数，覆盖 ERP 主流程与系统管理页面，`router` 26 条路由。财务页 `FinanceView` 8 个标签：总览/账户/资金流水/月度现金流/应收/应付/账龄汇总/收付款。

数据库 `hai_erp`：66 张表（业务表 47 张：`md_`/`hr_`/`sd_`/`pu_`/`pr_`/`st_`/`sh_`/`fn_`/`biz_`，`sys_` 19 张）、9 个视图、186 项菜单、8 个角色、8 个演示账号、7 个部门、7 个岗位。

### 7.2 已落地：资金域与协作流建表脚本

`sql/ai-erp-12-finance-workflow.sql` 已导入当前演示库：`fn_account`、`fn_cash_txn`、`fn_payment`、`fn_payment_line`、`fn_receivable`、`fn_payable`、`biz_approval`、`biz_period`、`biz_todo`、`biz_action_request` 均存在。因此 `/erp/finance/*` 16 个端点、`/erp/workflow/*` 2 个端点、工资发放 `pay` 链路都可实际调用，不再是"调用即报表不存在"。

### 7.3 已落地：ERP 权限码全量入库

代码引用 **176 个权限码**（含 `md:{resource}` 动态展开的 9 个资源 × 4 个动作），`sys_menu` 已入库 **184 个**。ERP 业务域的权限码 **100% 入库**，第 3 节表格不再有 † 标记。

仍未入库的 22 个权限码不属于 ERP 业务，来自 RuoYi 底座与辅助模块：

| 域 | 未入库权限码 |
| --- | --- |
| 监控 `monitor`（16） | `monitor:cache:list`、`monitor:job:{list,query,add,edit,remove,export,changeStatus}`、`monitor:logininfor:{list,remove,export,unlock}`、`monitor:online:{list,forceLogout}`、`monitor:operlog:export`、`monitor:server:list` |
| 单据冻结 `system:billFrozen`（6） | `system:billFrozen:{list,query,add,edit,remove,export}` |

演示库只保留了 `monitor:operlog:list` 与 `monitor:operlog:remove` 两个监控按钮。后果：**非 admin 角色访问监控页与单据冻结页会被拒（HTTP 200 + 响应体 `code=403`）**，ERP 主链路不受影响。

### 7.4 演示前检查清单

1. 备份 `sys_menu`、`sys_role_menu` 与业务表；
2. 跑 `sql/self_check.sql`（29 项）做基础完整性自检，再跑 `sql/p2-demo-data-consistency.sql`（9 项）与 `sql/p3-chain-integrity.sql`（14 项）校验演示数据与全链路一致性；
3. 确认 `biz_period` 中当期未结账 —— 已结账期间会让收货、报工、收付款、工资发放统一返回 `2001`；
4. 按第 6 节矩阵核对角色授权，重点确认 finance 是否需要资金域、sales 是否可以新建报价；
5. 用 admin 跑通第 9 节业务链路，再逐个角色账号抽查 403 边界（只看 HTTP 状态码会误判为成功）。

## 8. 错误码

| code | 用途 |
| --- | --- |
| 1001 | 入参不合法（含日期越界、行数超限、记录不存在、SKU 不能直接新增） |
| 1002 | 数据重复（编码/工号/明细行已存在） |
| 2001 | 状态不允许（含期间已结账、审批状态不符、单据已作废、非法跳转、AI 提问超限流） |
| 2003 | 余额或库存不足（资金账户、应收应付余额、成品/物料库存） |
| 2005 | 试算前提不满足（BOM/工序/取价/数量缺失或不合法） |
| 2006 | 数据已变化（并发修改、完工超计划或超最终工序合格量、报价重复转单、缺少待审批记录） |
| 2007 | 请求记录不一致（`requestKey` 同键不同参）；单据被下游引用，不能删除或作废 |
| 4002 | 没有该业务数据权限（AI 按 `mode` 校验失败） |

错误消息为可展示中文，SQL 与堆栈不进响应。认证/权限不足返回 **HTTP 200**，真实结果在响应体 `code`（401/403），不得用空列表掩盖越权。AI 不可用时规则预警仍返回确定性数据。

## 9. 验收要点

**接口层**：登录 → `getInfo` → `getRouters` → 分页 → 详情 → 403 响应体判定。

**业务链路**：下单（可带 `quoteId` 完成报价转单）→ 确认 → 缺料 → 采购（含超阈值审批、下单即付款）→ 收货 → 工单 → 领料 → 逐道报工 → 完工 → 出货 → 发货 → 签收 → 结算 → 确认 → 转报价 → 接受 → 工资生成 → 确认 → 发放 → 期间结账。

**边界**：重复收货/报工（`requestKey`）、同一报价重复转单、部分收货后在途、库存不足与并发出库、部分发货不算发齐、已确认成本调价后不变、删除有下游单据被拒、已结账期间拒绝补录、AI 工具越权被拒。

**数据口径**：`sql/self_check.sql`（29 项）、`sql/p2-demo-data-consistency.sql`（9 项）、`sql/p3-chain-integrity.sql`（14 项）全部通过，演示数据满足订单/工单/出货/结算的链路一致性。

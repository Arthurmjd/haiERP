# AI 驱动精益 ERP 系统 - 接口设计文档

> 版本：v2.0，2026-10-14。对应《AI-ERP需求分析文档》《AI-ERP技术方案文档》《AI-ERP数据库设计文档》v2.0。
> 状态：接口契约已定稿，v2.0 全新演示库 SQL 已同步。主要业务端点和页面已进入开发实现；仍需真实 MySQL/Redis 联调，部分目标端点尚未上线。具体范围和验收步骤见《开发启动说明》。

## 1. 底座协议

后端基于 RuoYi 派生底座。业务接口前缀 `/erp/{md|hr|sd|pu|pr|st|sh|fn}/...`；看板为 `/erp/dashboard/...`，AI 为 `/ai/...`。业务端点采用 RuoYi 的 `AjaxResult` 成功码 `200`，分页使用 `TableDataInfo`。所有 ID 在 JSON 中为字符串，前端不得转成 Number 再传回。金额与数量是十进制数，前端只显示、校验输入，不计算权威合计。

**平台接口以实际底座响应为准：**

| 接口 | 实际成功响应中的关键字段 | 前端取值 |
| --- | --- | --- |
| `POST /login` | 顶层 `code,msg,token` | `response.token` |
| `GET /getInfo` | 顶层 `code,msg,user,roles,permissions` | 从顶层读取 |
| `GET /getRouters` | 顶层 `code,msg,data` | `response.data` 为路由树 |
| 分页列表 | 顶层 `code,msg,rows,total` | 分页适配层读取 |
| 普通业务详情 | 顶层 `code,msg,data` | 业务 API 读取 `data` |
| 文件/SSE | Blob 或事件流 | 不走普通 JSON 拆包 |

若后端全局将 Long 序列化为 String，`total` 也可能是字符串。前端分页适配层验证其为非负安全整数后转换。`data` 为空或缺失的成功动作仍算成功，不按字段真假判断。业务错误保持 `code,msg`，401 清除登录态，403 显示无权限，业务错误码见下。

请求参数：`pageNum` 从 1 开始，`pageSize` 默认 10、最大 500；`orderByColumn` 用每个资源显式白名单映射，`isAsc` 仅允许 `asc/desc`。后端不得将客户端排序字段直接拼 SQL。日期为 `yyyy-MM-dd`，时间用 `yyyy-MM-dd HH:mm:ss`，双方采用统一业务时区。客户端不传 `createBy/updateBy/delFlag/totalAmount/settlementAmount` 等权威字段。

权限格式 `{domain}:{resource}:{action}`。查询、详情、导出、业务动作、看板和 AI 均加服务端权限校验；按钮显隐不能代替授权。平台八角色和敏感数据授权见需求文档。`@PreAuthorize` 放在 Controller；AI 工具在内部调用 Service 时再次检查对应业务权限。

## 2. 通用资源与错误码

每个可编辑资源可按需要提供以下约定；流水、看板等只读资源不机械提供增删改。

| 方法 | 路径形态 | 权限后缀 | 语义 |
| --- | --- | --- | --- |
| GET | `/erp/{domain}/{resource}/list` | `list` | 分页查询 |
| GET | `/erp/{domain}/{resource}/{id}` | `query` | 明细或主子表聚合 |
| POST | `/erp/{domain}/{resource}` | `add` | 创建草稿 |
| PUT | `/erp/{domain}/{resource}` | `edit` | 只修改允许编辑的草稿字段 |
| DELETE | `/erp/{domain}/{resource}/{ids}` | `remove` | 无业务副作用且无下游引用时停用/删除 |
| POST | `/erp/{domain}/{resource}/export` | `export` | 按与列表相同条件导出 |

单据状态只能通过动作端点改变。重复动作携带 `requestKey`，由 `biz_action_request` 对跨业务动作统一约束；相同请求返回首次结果，参数不同却复用同一键返回冲突。一次动作可产生多条库存流水，不能在每条流水上单独约束 `requestKey` 唯一。动作记录及副作用在同一事务内完成。

| code | 用途 |
| --- | --- |
| 1001/1002 | 输入不合法 / 编码冲突 |
| 2001/2002 | 状态不允许 / 已有下游或记账副作用 |
| 2003/2005 | 库存不足 / 开工齐套条件不满足 |
| 2006/2007 | 来源数量不一致 / 重复请求键与原请求内容冲突 |
| 3001/3002 | 无法核算标准成本 / 已确认不可重算 |
| 4001/4002 | AI 服务不可用 / AI 查询越权或不符合策略 |

错误消息为可展示的中文，内部 SQL 与堆栈不进响应。读权限不足返回 403，不能通过空列表掩盖越权。AI 模型不可用时，规则预警端点仍返回确定性数据。

## 3. 主数据与销售

主数据资源：`/erp/md/category`、`color`、`size`、`style`、`sku`、`material`、`process`、`customer`、`supplier`。款式下的 BOM 和工序为 `/erp/md/style/{styleId}/bom` 与 `/erp/md/style/{styleId}/process`；SKU 按需生成接口为 `POST /erp/md/sku/generate`，必须按 `(styleId,colorId,sizeId)` 去重。BOM 单位与物料主单位不一致时拒绝确认使用。

销售订单：`/erp/sd/order` 主子表。`POST /erp/sd/order` 入参 `{customerId,deliveryDate,items:[{styleId,colorId,sizeId,qty,unitPrice}]}`；服务端校验行唯一和正数，并计算总额。`POST /erp/sd/order/confirm/{id}` 将草稿 0 改为已确认 1；此后冻结色码、数量、单价和承诺交期。取消只允许没有下游和记账副作用的单据。`GET /erp/sd/order/{id}` 返回色码行及每行已发/未发数量。

报价：`/erp/sd/quotation` 主子表。`POST /erp/sd/quotation/fromSettlement` 入参 `{settleIds:["1"],requestKey}`，只引用已确认结算，复制 `unitCost` 为报价 `costRef`，保存 `sourceSettleId` 和价源。由成本模块发起的入口可调用同一 Service，不另写一套报价逻辑。

员工：`GET /erp/hr/employee/options` 返回在用部门；`GET /erp/hr/employee/{id}`、`POST /erp/hr/employee`、`PUT /erp/hr/employee/{id}` 分别需要 `hr:employee:list/add/edit`。工号唯一，生产计件和职能固定薪分别使用类型 1、2。

报价状态：`POST /erp/sd/quotation/accept/{id}` 与 `POST /erp/sd/quotation/expire/{id}` 分别需要 `sd:quotation:accept/expire`，仅允许待确认 0 进入已接受 1 或已失效 2；重复或竞争操作报状态冲突。

## 4. 采购、生产与库存

| 动作 | 端点与权限 | 入参与必须结果 |
| --- | --- | --- |
| 净缺料 | `GET /erp/pu/suggestion/list`；`pu:suggestion:list` | 读取修订后的 `v_mrp_demand`，给出需求、库存、在途剩余、净缺口 |
| 建采购单 | `POST /erp/pu/suggestion/generate`；`pu:suggestion:generate` | 选物料及供应商，保存采购草稿；重复请求不重复建单 |
| 下单 | `POST /erp/pu/purchaseOrder/confirm/{id}`；`pu:purchaseOrder:confirm` | 0→1，草稿在此前不计在途 |
| 分批收货 | `POST /erp/pu/purchaseOrder/receive`；`pu:purchaseOrder:receive` | `{poId,actualArrivalDate,items:[{itemId,qty,batchNo}],requestKey}`；不得超明细未收量；按 itemId 写流水 |
| 建工单 | `POST /erp/pr/workOrder`；`pr:workOrder:add` | `{orderId,styleId}`；一订单款式一工单，冻结 SKU、BOM、工价 |
| 下达领料 | `POST /erp/pr/workOrder/issue/{id}`；`pr:workOrder:issue` | `{requestKey}`；按冻结用料检查现存库存，扣减并写流水；在途不可领 |
| 报工 | `POST /erp/pr/workOrder/process/report`；`pr:process:report` | `{workOrderProcessId,skuId,empId,doneQty,badQty,reportDate,requestKey}`；`skuId` 属于该工单，内部工序必填 `empId`，委外工序不传员工；`doneQty` 是本次合格数，`badQty` 为另外的不良数 |
| 完工 | `POST /erp/pr/workOrder/finish/{id}`；`pr:workOrder:finish` | `{skuItems:[{skuId,goodQty,batchNo}],requestKey}`；各 SKU 入库量不超过冻结计划和最终工序合格数 |

采购单与工单另外提供分页和详情。收货累计由关联采购明细的库存流水核对；工序累计由报工事件核对。外发工序可记录外发价和完成数，但不能生成员工计件。工单取消在已领料或报工后拒绝，冲销留后续版本。

`GET /erp/st/inventory/list` 查询结存，`GET /erp/st/txn/list` 查询只增流水；`POST /erp/st/txn/adjust` 输入实际盘点数量、批次、仓库和原因，服务端只生成差异流水并更新结存。手工入/出库首期限定仓管，并禁止冒充采购、生产和销售来源类型。

## 5. 出货、人事与标准成本

出货单：`POST /erp/sh/shipment` 创建主子表草稿，行含 `skuId,batchNo,qty`；`POST /erp/sh/shipment/ship/{id}` 入参 `{requestKey}`，校验订单该 SKU 的未发量及批次库存后出库；`POST /erp/sh/shipment/sign/{id}` 记录签收。部分发货不把订单置为已发齐；最后一行发齐时更新订单状态 4 和发齐日期，全部签收后才可推进完成状态 5。出货取消在已出库后拒绝。

退货：`POST /erp/sh/return` 登记来源出货、SKU、数量、退款金额及回库意图；`POST /erp/sh/return/handle/{id}` 按 SKU/批次核对并写回库流水。不得超原出货数量；换货替代发运后置。

计件明细 `GET /erp/hr/pieceWage/list` 从报工事件只读派生。`POST /erp/hr/salarySlip/generate` 按员工和工资期汇总草稿，已确认的工资期不能覆盖；`POST /erp/hr/salarySlip/confirm/{id}` 确认。复杂税费计算不作为首期验收。

成本：`POST /erp/fn/settlement/calc` 入参 `{orderId,styleId,requestKey}`，按冻结 BOM、价、工价和费率生成/更新试算草稿及成本明细；`POST /erp/fn/settlement/confirm/{id}` 仅在对应工单已完工、订单对应款式已发齐且明细可复算时确认。已确认结算拒绝重算与删除。`GET /erp/fn/settlement/{id}` 返回分项快照和“标准成本”标识。报价引用只允许已确认结算。

## 6. 看板与 AI

| 接口 | 权限 | 核心字段 |
| --- | --- | --- |
| `GET /erp/dashboard/overview` | `dashboard:overview:list` | 已发齐准交率、逾期未发齐数量、净缺料数；仅有 `fn:settlement:list` 权限时返回 `standardGrossProfit` |
| `GET /erp/dashboard/mrp` | `dashboard:mrp:list` | 每种物料剩余需求、库存、在途剩余和净缺口 |
| `GET /erp/dashboard/ontime` | `dashboard:ontime:list` | 订单发齐日期、发齐状态、逾期天数；支持 `asOfDate` |
| `GET /erp/dashboard/styleCost` | `dashboard:styleCost:list` | 已确认结算的款式标准成本与毛利 |
| `GET /erp/dashboard/alerts` | `dashboard:alerts:list` | 确定性规则预警、触发值及来源单据 |

首期 ERP 业务问答为 `POST /ai/erp/ask`，请求 `{mode,question}`，`mode` 只能为 `orders/inventory/shortage/cost/ontime`。接口先检查工作台权限，再按所选范围检查对应读权限，最多返回 20 条只读记录、数据时间、来源与可选模型分析；未配置模型或超时返回确定性摘要。`POST /ai/alerts/explain` 对已有预警生成解释，模型不可用时返回规则说明。底座原有 `/ai/chat` 是通用聊天，不连接 ERP 业务工具；原计划自动选工具的 SSE 对话留待后续。`/ai/nl2sql/*` 与 `/ai/metadata/*` 只允许管理员。

## 7. 状态与验收

销售订单：0草稿→1已确认→2生产中→3已完工→4已发齐→5已完成；无下游时可→9取消。采购单：0草稿→1已下单→2部分收货→3全部收货。工单：0待下达→1生产中→2已完工。出货单：0草稿→1已发货→2已签收。结算：0试算→1确认。非法跳转返回 2001，已记账动作不得通过取消回滚。主数据停用不影响历史快照。

首个前后端联调应覆盖登录、`getInfo`、`getRouters`、分页、普通详情、403、SSE。业务验收应覆盖重复收货/报工、部分收货后在途、库存不足与并发出库、部分发货不算发齐、已确认成本调价后不变，以及 AI 工具越权被拒。

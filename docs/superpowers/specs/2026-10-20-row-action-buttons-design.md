# 列表行操作按钮与草稿编辑设计

> **状态：已完成（归档）。** 本设计已实现，见 `docs/superpowers/plans/2026-10-20-row-action-buttons.md`。下文保留 2026-10-20 的设计论证；当前项目规模为 43 个控制器 / 260 个方法级接口 / 47 张业务表 + 9 个视图，配置驱动页面已覆盖主数据与多类列表页。

> 范围：销售订单、采购订单、通用档案页统一“编辑 / 详情”操作按钮；销售订单与采购单在草稿状态支持编辑。
> 代码基线：2026-10-20，分支 `master`。

## 背景

当前列表页操作不一致：销售订单只有“详情”，采购订单没有操作列，通用档案页依赖整行点击，用户无法直观发现可执行操作。同时订单与采购单只有新建接口，创建后无法修改草稿。

## 目标

1. 列表页统一提供明确的“编辑 / 详情”等行操作按钮。
2. 销售订单、采购单在草稿状态（`status = 0`）可编辑，已确认单据不可编辑。
3. 不改变已上线业务单据的状态流转与库存/成本逻辑。

## 非目标

- 不做删除操作。
- 不支持已确认单据编辑。
- 不新增报价单、工单、出货、退货、结算等单据的编辑能力。
- 不新增通用操作列组件（页面数量少，避免过早抽象）。

## 后端设计

### 接口

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| PUT | `/erp/sd/order/{id}` | `sd:order:edit` | 更新草稿销售订单 |
| PUT | `/erp/pu/purchaseOrder/{id}` | `pu:purchaseOrder:edit` | 更新草稿采购单 |

请求体与现有新建接口一致：

- 订单：`{ customerId, deliveryDate, items: [{ styleId, colorId, sizeId, qty, unitPrice }] }`
- 采购：`{ supplierId, expectDate, items: [{ materialId, qty, unitPrice }] }`

### 服务逻辑

1. 校验 `id` 合法。
2. `SELECT ... FOR UPDATE` 锁定单据并读取状态；不存在或 `status != 0` 时抛出业务异常（已确认单据不可编辑）。
3. 复用新建时的完整字段校验（客户/供应商有效、日期不早于今天、1–100 条明细、数量/单价精度、SKU/物料有效、重复明细、金额上限）。
4. 事务内：
   - 更新表头（客户/供应商、日期、重新计算的总额、`update_by`、`update_time`）；
   - 删除原明细并重新插入；
   - 保留原单号、创建人、创建时间与状态。
5. 返回 `{ orderId, orderNo }` / `{ poId, poNo }`。

为避免逻辑重复，把新建用的校验提取为共享私有方法，新建与更新共用。

### Mapper 新增

- `lockDraft(id)`：`SELECT status FROM ... WHERE id = ? AND del_flag = '0' FOR UPDATE`
- `update(id, customerId/supplierId, date, totalAmount, updatedBy)`：仅 `status = 0` 时更新
- `deleteLines(id)`：删除该单据全部明细

### 权限种子

在 `sql/erp-permissions.sql` 新增：

- `90079` 编辑订单，父级 `90002`，`sd:order:edit`
- `90080` 编辑采购，父级 `90008`，`pu:purchaseOrder:edit`

角色授权：

- `sales` 增加 `90079`；
- `planner`（buyer）增加 `90080`；
- `supervisor` 覆盖区间扩展到 `90080`。

已有数据库需重新执行该脚本后生效；管理员（`*:*:*`）无需额外授权。

## 前端设计

### 路由

- `/orders/:id/edit` → `OrderFormView`，权限 `sd:order:edit`
- `/purchases/:id/edit` → `PurchaseFormView`，权限 `pu:purchaseOrder:edit`

### 表单双模式

`OrderFormView`：

- 存在 `route.params.id` 时为编辑模式；
- 加载详情回填客户、交期、明细（SKU、数量、单价）；
- 标题显示“编辑销售订单”，按钮文案“保存修改”；
- 提交调用 `updateOrder`，成功后跳转 `/orders/:id`；
- 若详情状态非草稿，提示“已确认单据不可编辑”并返回列表。

`PurchaseFormView` 同样处理供应商、预计到货与物料明细。

### API

`frontend/src/api/erp.ts` 新增：

- `updateOrder(id, draft)` → `PUT /erp/sd/order/{id}`
- `updatePurchase(id, draft)` → `PUT /erp/pu/purchaseOrder/{id}`

演示模式在 `demo.ts` 补充对应更新方法，保证 `/preview` 下可完整演示。

### 列表操作列

- `OrdersView`：操作列 → `详情`、`编辑`（仅 `status === 0` 且 `user.can('sd:order:edit')`）。
- `PurchasesView`：新增操作列 → `详情`、`编辑`（仅 `status === 0` 且 `user.can('pu:purchaseOrder:edit')`）。
- `CatalogView`：合并为一个操作列：
  - 可维护主数据：`编辑`（打开现有编辑弹窗，权限沿用 add/edit 规则）；
  - 有详情路由：`详情`（报价、工单、出货、退货、结算）；
  - 款式：`编辑` + `BOM / 工序`；
  - 工资单：`确认`；
  - 无可用操作时整列不渲染。
- 行点击跳转保留；所有按钮使用 `.stop` 防止冒泡。

### 交互约定

- 按钮文字统一为“详情 / 编辑 / 确认 / BOM / 工序”。
- 无权限的按钮不渲染，不使用禁用态。
- 单据“编辑”入口只对草稿显示，避免用户进入不可编辑页面；档案编辑使用现有 `editPermission`。

## 验证

1. 后端单测：非草稿更新被拒绝；草稿更新成功并替换明细；非法明细被拒绝。
2. `mvn -pl myfactoryai-admin -am test` 通过。
3. `npm run build` 类型检查通过。
4. 演示模式手工验证：
   - 草稿订单显示“编辑”，进入后数据回填，保存后详情更新；
   - 已确认订单不显示“编辑”；
   - 采购单同样验证；
   - 档案页各 tab 的按钮按权限正确显示；
   - 详情页与原有状态操作不受影响。

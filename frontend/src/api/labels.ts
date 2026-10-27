/**
 * 业务枚举字典：状态码 → 中文标签。
 *
 * 取值与含义以 sql/ai-erp-0*-module.sql 里各字段的 COMMENT 为准，
 * 新增页面请统一从这里取标签，不要在各视图里再写一套三元表达式，
 * 否则很容易漏掉 9（已取消）这类分支，把「已取消」显示成「待开工」。
 */

export type TagType = 'primary' | 'success' | 'info' | 'warning' | 'danger'

export interface EnumEntry {
  label: string
  type?: TagType
}

export type EnumDict = Record<number, EnumEntry>

export const enums = {
  /** 主数据通用状态：1启用 0停用 */
  masterStatus: {
    1: { label: '启用', type: 'success' },
    0: { label: '停用', type: 'info' },
  },
  /** 员工：1在职 0离职 */
  employeeStatus: {
    1: { label: '在职', type: 'success' },
    0: { label: '离职', type: 'info' },
  },
  /** 部门：1正常 0停用 */
  deptStatus: {
    1: { label: '正常', type: 'success' },
    0: { label: '停用', type: 'info' },
  },
  /** 计件工资 / 工资单：0草稿 1已确认 2已发放 */
  payrollStatus: {
    0: { label: '草稿', type: 'info' },
    1: { label: '已确认', type: 'primary' },
    2: { label: '已发放', type: 'success' },
  },
  /** 销售订单：0草稿 1已确认 2生产中 3已完工 4已出货 5已完成 9已取消 */
  orderStatus: {
    0: { label: '草稿', type: 'info' },
    1: { label: '已确认', type: 'primary' },
    2: { label: '生产中', type: 'warning' },
    3: { label: '已完工', type: 'primary' },
    4: { label: '已出货', type: 'primary' },
    5: { label: '已完成', type: 'success' },
    9: { label: '已取消', type: 'danger' },
  },
  /** 报价单：0待确认 1已接受 2已失效 */
  quoteStatus: {
    0: { label: '待确认', type: 'info' },
    1: { label: '已接受', type: 'success' },
    2: { label: '已失效', type: 'danger' },
  },
  /** 采购单：0草稿 1已下单 2部分到货 3已到货 9已取消 */
  purchaseStatus: {
    0: { label: '草稿', type: 'info' },
    1: { label: '已下单', type: 'primary' },
    2: { label: '部分到货', type: 'warning' },
    3: { label: '已到货', type: 'success' },
    9: { label: '已取消', type: 'danger' },
  },
  /** 生产工单：0待开工 1生产中 2已完工 9已取消 */
  workOrderStatus: {
    0: { label: '待开工', type: 'info' },
    1: { label: '生产中', type: 'warning' },
    2: { label: '已完工', type: 'success' },
    9: { label: '已取消', type: 'danger' },
  },
  /** 工单工序：0待开工 1进行中 2已完成 */
  processStatus: {
    0: { label: '待开工', type: 'info' },
    1: { label: '进行中', type: 'warning' },
    2: { label: '已完成', type: 'success' },
  },
  /** 出货单：0待发货 1已发货 2已签收 9已取消 */
  shipmentStatus: {
    0: { label: '待发货', type: 'info' },
    1: { label: '已发货', type: 'primary' },
    2: { label: '已签收', type: 'success' },
    9: { label: '已取消', type: 'danger' },
  },
  /** 退货单：0待处理 1已处理 9已取消 */
  returnStatus: {
    0: { label: '待处理', type: 'warning' },
    1: { label: '已处理', type: 'success' },
    9: { label: '已取消', type: 'danger' },
  },
  /** 成本结算：0试算 1已确认 */
  settlementStatus: {
    0: { label: '试算', type: 'warning' },
    1: { label: '已确认', type: 'success' },
  },
  /** 性别：0女 1男 2中性（款式可选中性，员工只有 0/1） */
  gender: {
    0: { label: '女' },
    1: { label: '男' },
    2: { label: '中性' },
  },
  /** 员工类型：1生产(计件) 2职能(固定薪) */
  empType: {
    1: { label: '生产计件' },
    2: { label: '职能固定薪' },
  },
  /** 物料类别：1面料 2辅料 */
  materialCategory: {
    1: { label: '面料' },
    2: { label: '辅料' },
  },
  /** 部门类型：1生产 2职能 */
  deptType: {
    1: { label: '生产' },
    2: { label: '职能' },
  },
  /** 库存对象类型：1物料 2成品 */
  itemType: {
    1: { label: '物料' },
    2: { label: '成品' },
  },
  /** 出入库方向：1入库 2出库 */
  txnDirection: {
    1: { label: '入库', type: 'success' },
    2: { label: '出库', type: 'warning' },
  },
  /** 出入库业务类型：1采购入库 2生产领料 3完工入库 4销售出库 5盘盈 6盘亏 7退货回库 */
  txnBizType: {
    1: { label: '采购入库' },
    2: { label: '生产领料' },
    3: { label: '完工入库' },
    4: { label: '销售出库' },
    5: { label: '盘盈' },
    6: { label: '盘亏' },
    7: { label: '退货回库' },
  },
  /** 资金方向：1收 2付 */
  financeDirection: {
    1: { label: '收', type: 'success' },
    2: { label: '付', type: 'warning' },
  },
  /** 资金业务类型：1销售收款 2采购付款 3工资发放 4退款 5其他收 6其他付 */
  financeBizType: {
    1: { label: '销售收款' },
    2: { label: '采购付款' },
    3: { label: '工资发放' },
    4: { label: '退款' },
    5: { label: '其他收' },
    6: { label: '其他付' },
  },
  /** 应收/应付状态：0未收付 1部分 2已结清 9作废 */
  arApStatus: {
    0: { label: '未收付', type: 'warning' },
    1: { label: '部分', type: 'primary' },
    2: { label: '已结清', type: 'success' },
    9: { label: '作废', type: 'danger' },
  },
  /** 资金账户类型：1现金 2银行 3电子 */
  accountType: {
    1: { label: '现金' },
    2: { label: '银行' },
    3: { label: '电子' },
  },
  /** 收付款单：0待确认 1已确认 */
  paymentStatus: {
    0: { label: '待确认', type: 'warning' },
    1: { label: '已确认', type: 'success' },
  },
  /** 会计期间：0开放 1已结账 */
  periodStatus: {
    0: { label: '开放', type: 'info' },
    1: { label: '已结账', type: 'success' },
  },
  /** 退货类型：1退货入库 2换货 3报废 */
  returnType: {
    1: { label: '退货入库' },
    2: { label: '换货' },
    3: { label: '报废' },
  },
  /** 是否回库：1回库 0不回库 */
  restockFlag: {
    0: { label: '不回库' },
    1: { label: '回库' },
  },
  /** 参考成本来源：0手工 1历史结算 2BOM 预估 */
  costSource: {
    0: { label: '手工' },
    1: { label: '历史结算' },
    2: { label: 'BOM 预估' },
  },
  /** 是否外发：0否 1是 */
  outsourcedFlag: {
    0: { label: '否' },
    1: { label: '是' },
  },
} satisfies Record<string, EnumDict>

export type EnumKey = keyof typeof enums

/** 取中文标签；字典里没有的码值退回「未知(码值)」，避免页面出现裸数字 */
export function enumLabel(key: EnumKey | undefined, value: unknown): string {
  if (value === null || value === undefined || value === '') return '—'
  if (!key) return String(value)
  const code = Number(value)
  if (Number.isNaN(code)) return String(value)
  const dict: EnumDict = enums[key]
  return dict[code]?.label ?? `未知(${String(value)})`
}

/** 取 el-tag 的 type；未配置时返回 undefined，el-tag 会用默认样式 */
export function enumTagType(key: EnumKey | undefined, value: unknown): TagType | undefined {
  if (!key || value === null || value === undefined || value === '') return undefined
  const code = Number(value)
  if (Number.isNaN(code)) return undefined
  const dict: EnumDict = enums[key]
  return dict[code]?.type
}

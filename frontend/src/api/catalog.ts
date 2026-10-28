import { http, pageTotal, type Page } from './client'

import type { EnumKey } from './labels'

export interface CatalogColumn { prop: string; label: string; width?: number; enum?: EnumKey }
export interface CatalogSpec {
  title: string
  path: string
  permission: string
  columns: CatalogColumn[]
  /** 行点击跳转的详情路由前缀（如 /production/workOrders），未声明则行点击仅打开主数据编辑 */
  detailRoute?: string
  /** 详情页所需权限点 */
  detailPermission?: string
  /** 列表筛选器类型：month = 显示年月选择器并把 YYYY-MM 传给后端 */
  filter?: 'month'
}
export interface MasterField { key: string; label: string; kind?: 'text' | 'number' | 'status' | 'gender' | 'category' | 'employeeType' | 'date' | 'department'; required?: boolean; defaultValue?: string | number }
type CatalogGroup = Record<string, CatalogSpec>

const common = [{ prop: 'code', label: '编码', width: 150 }, { prop: 'name', label: '名称' }]
export const catalogs: Record<string, CatalogGroup> = {
  master: {
    styles: { title: '款式', path: '/erp/md/style/list', permission: 'md:style:list', columns: [...common, { prop: 'season', label: '季节' }, { prop: 'unit', label: '单位' }, { prop: 'baseCost', label: '基准成本' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    skus: { title: 'SKU', path: '/erp/md/sku/list', permission: 'md:sku:list', columns: [...common, { prop: 'styleName', label: '款式' }, { prop: 'colorName', label: '颜色' }, { prop: 'sizeName', label: '尺码' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    materials: { title: '物料', path: '/erp/md/material/list', permission: 'md:material:list', columns: [...common, { prop: 'spec', label: '规格' }, { prop: 'unit', label: '单位' }, { prop: 'refPrice', label: '参考价' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    customers: { title: '客户', path: '/erp/md/customer/list', permission: 'md:customer:list', columns: [...common, { prop: 'contact', label: '联系人' }, { prop: 'phone', label: '电话' }, { prop: 'creditDays', label: '账期' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    suppliers: { title: '供应商', path: '/erp/md/supplier/list', permission: 'md:supplier:list', columns: [...common, { prop: 'contact', label: '联系人' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    processes: { title: '工序', path: '/erp/md/process/list', permission: 'md:process:list', columns: [...common, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    colors: { title: '颜色', path: '/erp/md/color/list', permission: 'md:color:list', columns: [...common, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    sizes: { title: '尺码', path: '/erp/md/size/list', permission: 'md:size:list', columns: [...common, { prop: 'sizeGroup', label: '尺码组' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
    categories: { title: '品类', path: '/erp/md/category/list', permission: 'md:category:list', columns: [{ prop: 'name', label: '品类名称' }, { prop: 'sort', label: '排序' }, { prop: 'status', label: '状态', enum: 'masterStatus' }] },
  },
  hr: {
    employees: { title: '员工', path: '/erp/hr/employee/list', permission: 'hr:employee:list', columns: [...common, { prop: 'position', label: '岗位' }, { prop: 'empType', label: '类型', enum: 'empType' }, { prop: 'hireDate', label: '入职日期' }, { prop: 'baseSalary', label: '基本工资' }, { prop: 'siBase', label: '社保基数' }, { prop: 'status', label: '状态', enum: 'employeeStatus' }] },
    wages: { title: '计件工资', path: '/erp/hr/pieceWage/list', permission: 'hr:pieceWage:list', filter: 'month', columns: [{ prop: 'employeeName', label: '员工' }, { prop: 'wagePeriod', label: '工资期' }, { prop: 'workOrderId', label: '工单' }, { prop: 'qty', label: '数量' }, { prop: 'unitPrice', label: '单价' }, { prop: 'amount', label: '金额' }, { prop: 'status', label: '状态', enum: 'payrollStatus' }] },
    salary: { title: '工资单', path: '/erp/hr/salarySlip/list', permission: 'hr:salarySlip:list', filter: 'month', columns: [{ prop: 'employeeName', label: '员工' }, { prop: 'wagePeriod', label: '工资期' }, { prop: 'baseSalary', label: '基本工资' }, { prop: 'pieceAmount', label: '计件工资' }, { prop: 'siPersonal', label: '社保个人', width: 100 }, { prop: 'fundPersonal', label: '公积金', width: 100 }, { prop: 'tax', label: '个税', width: 90 }, { prop: 'grossPay', label: '应发' }, { prop: 'netPay', label: '实发' }, { prop: 'status', label: '状态', enum: 'payrollStatus' }] },
  },
  sales: {
    quotations: { title: '报价单', path: '/erp/sd/quotation/list', permission: 'sd:quotation:list', detailRoute: '/sales/quotations', detailPermission: 'sd:quotation:query', columns: [...common, { prop: 'customerName', label: '客户' }, { prop: 'quoteDate', label: '报价日期' }, { prop: 'totalAmount', label: '金额' }, { prop: 'status', label: '状态', enum: 'quoteStatus' }] },
  },
  production: {
    workOrders: { title: '生产工单', path: '/erp/pr/workOrder/list', permission: 'pr:workOrder:list', filter: 'month', detailRoute: '/production/workOrders', detailPermission: 'pr:workOrder:query', columns: [...common, { prop: 'orderNo', label: '订单号' }, { prop: 'styleName', label: '款式' }, { prop: 'qty', label: '计划数量' }, { prop: 'planEndDate', label: '计划完工' }, { prop: 'status', label: '状态', enum: 'workOrderStatus' }] },
  },
  inventory: {
    transactions: { title: '出入库流水', path: '/erp/st/txn/list', permission: 'st:txn:list', filter: 'month', columns: [{ prop: 'txnNo', label: '流水号' }, { prop: 'txnType', label: '方向', enum: 'txnDirection' }, { prop: 'bizType', label: '业务类型', enum: 'txnBizType' }, { prop: 'itemCode', label: '编码' }, { prop: 'itemName', label: '物料/产品' }, { prop: 'batchNo', label: '批次' }, { prop: 'qty', label: '数量' }, { prop: 'txnTime', label: '发生时间' }] },
  },
  shipping: {
    shipments: { title: '出货单', path: '/erp/sh/shipment/list', permission: 'sh:shipment:list', filter: 'month', detailRoute: '/shipping/shipments', detailPermission: 'sh:shipment:query', columns: [...common, { prop: 'orderNo', label: '订单号' }, { prop: 'customerName', label: '客户' }, { prop: 'shipDate', label: '出货日期' }, { prop: 'logisticsCompany', label: '物流公司' }, { prop: 'status', label: '状态', enum: 'shipmentStatus' }] },
    returns: { title: '退货单', path: '/erp/sh/return/list', permission: 'sh:return:list', detailRoute: '/shipping/returns', detailPermission: 'sh:return:query', columns: [...common, { prop: 'shipmentId', label: '原出货单' }, { prop: 'customerName', label: '客户' }, { prop: 'returnDate', label: '退货日期' }, { prop: 'reason', label: '原因' }, { prop: 'refundAmount', label: '退款' }, { prop: 'status', label: '状态', enum: 'returnStatus' }] },
  },
  cost: {
    settlements: { title: '标准成本结算', path: '/erp/fn/settlement/list', permission: 'fn:settlement:list', detailRoute: '/cost/settlements', detailPermission: 'fn:settlement:query', columns: [...common, { prop: 'orderId', label: '订单' }, { prop: 'styleName', label: '款式' }, { prop: 'qty', label: '数量' }, { prop: 'materialCost', label: '材料' }, { prop: 'laborCost', label: '人工' }, { prop: 'totalCost', label: '总成本' }, { prop: 'grossProfit', label: '毛利' }, { prop: 'status', label: '状态', enum: 'settlementStatus' }] },
  },
  reports: {
    ontime: { title: '交付准时率', path: '/erp/dashboard/ontime', permission: 'dashboard:ontime:list', columns: [{ prop: 'orderNo', label: '订单号' }, { prop: 'customerName', label: '客户' }, { prop: 'deliveryDate', label: '承诺交期' }, { prop: 'fullShipDate', label: '发齐日期' }, { prop: 'deliveryState', label: '交付状态' }, { prop: 'delayDays', label: '偏差天数' }] },
    styleCost: { title: '款式成本', path: '/erp/dashboard/styleCost', permission: 'dashboard:styleCost:list', columns: [{ prop: 'styleNo', label: '款号' }, { prop: 'styleName', label: '款式' }, { prop: 'totalQty', label: '数量' }, { prop: 'avgUnitCost', label: '单位成本' }, { prop: 'totalRevenue', label: '收入' }, { prop: 'totalGrossProfit', label: '毛利' }, { prop: 'grossMargin', label: '毛利率' }] },
  },
}
export const masterForms: Record<string, MasterField[]> = {
  employees: [{ key: 'code', label: '工号', required: true }, { key: 'name', label: '姓名', required: true }, { key: 'gender', label: '性别', kind: 'gender', defaultValue: 0 }, { key: 'phone', label: '电话' }, { key: 'deptId', label: '部门', kind: 'department' }, { key: 'position', label: '岗位' }, { key: 'empType', label: '员工类型', kind: 'employeeType', defaultValue: 1 }, { key: 'hireDate', label: '入职日期', kind: 'date' }, { key: 'baseSalary', label: '基本工资', kind: 'number', defaultValue: 0 }, { key: 'siBase', label: '社保基数', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  categories: [{ key: 'name', label: '品类名称', required: true }, { key: 'sort', label: '排序', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  colors: [{ key: 'code', label: '编码', required: true }, { key: 'name', label: '颜色名称', required: true }, { key: 'sort', label: '排序', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  sizes: [{ key: 'code', label: '编码', required: true }, { key: 'name', label: '尺码名称', required: true }, { key: 'sizeGroup', label: '尺码组', required: true, defaultValue: '服装码' }, { key: 'sort', label: '排序', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  styles: [{ key: 'code', label: '款号', required: true }, { key: 'name', label: '款式名称', required: true }, { key: 'categoryId', label: '品类 ID' }, { key: 'gender', label: '适用性别', kind: 'gender', defaultValue: 2 }, { key: 'season', label: '季节' }, { key: 'unit', label: '单位', required: true, defaultValue: '件' }, { key: 'baseCost', label: '基准成本', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  materials: [{ key: 'code', label: '编码', required: true }, { key: 'name', label: '物料名称', required: true }, { key: 'category', label: '类别', kind: 'category', defaultValue: 1 }, { key: 'spec', label: '规格' }, { key: 'unit', label: '单位', required: true, defaultValue: '米' }, { key: 'refPrice', label: '参考单价', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  processes: [{ key: 'code', label: '编码', required: true }, { key: 'name', label: '工序名称', required: true }, { key: 'sort', label: '排序', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  customers: [{ key: 'code', label: '编码', required: true }, { key: 'name', label: '客户名称', required: true }, { key: 'contact', label: '联系人' }, { key: 'phone', label: '电话' }, { key: 'address', label: '地址' }, { key: 'creditDays', label: '账期（天）', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  suppliers: [{ key: 'code', label: '编码', required: true }, { key: 'name', label: '供应商名称', required: true }, { key: 'contact', label: '联系人' }, { key: 'phone', label: '电话' }, { key: 'address', label: '地址' }, { key: 'creditDays', label: '账期（天）', kind: 'number', defaultValue: 0 }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
  skus: [{ key: 'barcode', label: '条码' }, { key: 'status', label: '状态', kind: 'status', defaultValue: 1 }],
}
export const masterResource = (key: string) => key === 'categories' ? 'category' : key === 'processes' ? 'process' : key === 'employees' ? 'employee' : key.slice(0, -1)

export async function catalogPage(spec: CatalogSpec, pageNum: number, asOfDate?: string, month?: string) {
  const result = (await http.get<Page<Record<string, unknown>>>(spec.path, {
    params: { pageNum, pageSize: 10, ...(asOfDate ? { asOfDate } : {}), ...(month ? { month } : {}) },
  })).data
  return { rows: result.rows, total: pageTotal(result.total) }
}

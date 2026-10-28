import { http, payload, pageTotal, type Envelope, type Page } from './client'
import { enumLabel } from './labels'

export interface Option { id: string; name: string; code?: string; styleId?: string; colorId?: string; sizeId?: string }
export interface OrderOptions { customers: Option[]; styles: Option[]; colors: Option[]; sizes: Option[]; skus: Option[] }
export interface OrderLine { itemId: string; styleId: string; styleName: string; skuId: string; skuCode: string; colorId: string; colorName: string; sizeId: string; sizeName: string; qty: number; unitPrice: number; amount: number; shippedQty: number; remainingQty: number }
export interface OrderWorkOrder { woId: string; woNo: string; styleId: string; styleName: string; qty: number; status: number; planEndDate: string | null; actualEndDate: string | null }
export interface Order { orderId: string; orderNo: string; customerId: string; customerName: string; orderDate: string; deliveryDate: string; status: number; totalAmount: number; fullShipDate: string | null; fulfillmentState: string; lineCount?: number; orderQty?: number; items?: OrderLine[]; workOrders?: OrderWorkOrder[] }
export interface OrderDraft { customerId: string; deliveryDate: string; items: { styleId: string; colorId: string; sizeId: string; qty: number; unitPrice: number }[] }
export interface MrpRow { materialId: string; materialCode: string; materialName: string; unit: string; demandQty: number; stockQty: number; onWayQty: number; gapQty: number; suggestQty: number; estAmount: number; shortageFlag: number }
export interface InventoryRow { inventoryId: string; itemType: number; itemId: string; itemCode: string; itemName: string; batchNo: string; qty: number; safeStock: number; unit: string }
export interface Overview { orderCount: number; overdueCount: number; shortageCount: number; standardGrossProfit?: number; ontimeRate: number | null }
export interface PurchaseOption extends Option { unit?: string; refPrice?: number }
export interface PurchaseOptions { suppliers: PurchaseOption[]; materials: PurchaseOption[] }
export interface PurchaseLine { itemId: string; materialId: string; materialCode: string; materialName: string; unit: string; qty: number; unitPrice: number; amount: number; receivedQty: number; remainingQty: number }
export interface PurchaseOrder { poId: string; poNo: string; supplierId: string; supplierName: string; orderDate: string; expectDate: string; actualArrivalDate: string | null; status: number; totalAmount: number; lineCount?: number; items?: PurchaseLine[] }
export interface PurchaseDraft { supplierId: string; expectDate: string; items: { materialId: string; qty: number; unitPrice: number }[] }
export interface PurchaseReceipt { poId: string; actualArrivalDate: string; items: { itemId: string; qty: number; batchNo: string }[]; requestKey: string }
export interface WorkOrderSku { skuId: string; skuCode: string; planQty: number; completedQty: number }
export interface WorkOrderMaterial { id: string; materialId: string; materialCode: string; materialName: string; unit: string; planQty: number; issuedQty: number }
export interface WorkOrderProcess { id: string; processName: string; seq: number; planQty: number; doneQty: number; badQty: number; isOutsourced: number; status: number }
export interface WorkOrder { woId: string; woNo: string; orderId: string; orderNo?: string; styleId: string; styleName: string; qty: number; status: number; planEndDate: string | null; actualStartDate: string | null; actualEndDate: string | null; skus: WorkOrderSku[]; materials: WorkOrderMaterial[]; processes: WorkOrderProcess[] }
export interface ShipmentLine { itemId: string; skuId: string; skuCode: string; batchNo: string; qty: number }
export interface PendingShipmentLine { skuId: string; skuCode: string; remaining: number; available: number }
export interface PendingShipmentOrder { orderId: string; orderNo: string; customerName: string; status: number; deliveryDate: string; lines: PendingShipmentLine[] }
export interface Shipment { shipmentId: string; shipmentNo: string; orderId: string; customerName: string; shipDate: string | null; logisticsCompany: string | null; logisticsNo: string | null; status: number; items: ShipmentLine[] }
export interface ReturnOrder { returnId: string; returnNo: string; shipmentId: string; orderId: string; reason: string; returnType: number; isRestock: number; refundAmount: number; returnDate: string | null; status: number; items: ShipmentLine[] }
export interface StockLine { skuId: string; batchNo: string; qty: number }
export interface CostDetailLine { lineType: number; sourceId: string; qty: number; unitPrice: number; amount: number; priceSource: string }
export interface Settlement { settleId: string; settleNo: string; orderId: string; styleId: string; styleName: string; qty: number; materialCost: number; laborCost: number; overheadRate: number; overheadCost: number; totalCost: number; unitCost: number; revenue: number; grossProfit: number; grossMargin: number; status: number; calcTime: string | null; costBasis: string; details: CostDetailLine[] }
export interface QuotationLine { itemId: string; styleId: string; styleName: string; qty: number; unitPrice: number; costRef: number; costSrc: number; sourceSettleId: string; amount: number }
export interface Quotation { quoteId: string; quoteNo: string; customerName: string; quoteDate: string; status: number; totalAmount: number; items: QuotationLine[] }
export interface StyleBomLine { materialId: string; materialCode: string; materialName: string; qty: number; lossRate: number; unit: string }
export interface StyleProcessLine { processId: string; processName: string; seq: number; unitPrice: number }
export interface Alert { id: string; alertType: string; title: string; subject: string; triggerValue: number; unit: string; sourceType: string; sourceId: string }
export interface AiAnswer { answer: string; rows: Record<string, unknown>[]; source: string; dataTime: string; modelUsed: boolean }
/** 操作日志（RuoYi sys_oper_log）：写操作留痕，删除/作废/确认都能查到是谁做的 */
export interface OperLogRow {
  operId: number; title: string; businessType: number; method: string;
  operName: string; operUrl: string; operIp: string; status: number;
  operTime: string; errorMsg?: string | null
}
export interface OperLogQuery {
  pageNum: number; pageSize?: number; title?: string; businessType?: number;
  status?: number; beginTime?: string; endTime?: string
}

/** 订单状态文案统一由 labels.ts 提供，避免各页面各写一套 */
export const orderStatusLabel = (status: number) => enumLabel('orderStatus', status)

export const erpApi = {
  operLogs: async (query: OperLogQuery) => {
    const result = (await http.get<Page<OperLogRow>>('/monitor/operlog/list', { params: query })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  options: () => payload<OrderOptions>(http.get('/erp/md/orderOptions')),
  orders: async (pageNum: number, keyword: string) => {
    const result = (await http.get<Page<Order>>('/erp/sd/order/list', { params: { pageNum, pageSize: 10, keyword } })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  order: (id: string) => payload<Order>(http.get(`/erp/sd/order/${encodeURIComponent(id)}`)),
  createOrder: (draft: OrderDraft) => payload<{ orderId: string; orderNo: string }>(http.post('/erp/sd/order', draft)),
  updateOrder: (id: string, draft: OrderDraft) => payload<{ orderId: string; orderNo: string }>(http.put(`/erp/sd/order/${encodeURIComponent(id)}`, draft)),
  confirmOrder: async (id: string) => {
    await http.post<Envelope>(`/erp/sd/order/confirm/${encodeURIComponent(id)}`)
  },
  mrp: (keyword = '') => payload<MrpRow[]>(http.get('/erp/pu/suggestion/list', { params: { keyword: keyword || undefined } })),
  inventory: (keyword = '') => payload<InventoryRow[]>(http.get('/erp/st/inventory/list', { params: { keyword: keyword || undefined } })),
  overview: () => payload<Overview>(http.get('/erp/dashboard/overview')),
  purchaseOptions: () => payload<PurchaseOptions>(http.get('/erp/md/purchaseOptions')),
  purchases: async (pageNum: number, keyword: string) => {
    const result = (await http.get<Page<PurchaseOrder>>('/erp/pu/purchaseOrder/list', { params: { pageNum, pageSize: 10, keyword } })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  purchase: (id: string) => payload<PurchaseOrder>(http.get(`/erp/pu/purchaseOrder/${encodeURIComponent(id)}`)),
  createPurchase: (draft: PurchaseDraft) => payload<{ poId: string; poNo: string }>(http.post('/erp/pu/purchaseOrder', draft)),
  updatePurchase: (id: string, draft: PurchaseDraft) => payload<{ poId: string; poNo: string }>(http.put(`/erp/pu/purchaseOrder/${encodeURIComponent(id)}`, draft)),
  confirmPurchase: async (id: string) => {
    await http.post<Envelope>(`/erp/pu/purchaseOrder/confirm/${encodeURIComponent(id)}`)
  },
  receivePurchase: (receipt: PurchaseReceipt) => payload<{ poId: string }>(http.post('/erp/pu/purchaseOrder/receive', receipt)),
  workOrder: (id: string) => payload<WorkOrder>(http.get(`/erp/pr/workOrder/${encodeURIComponent(id)}`)),
  createWorkOrder: (orderId: string, styleId: string) => payload<{ woId: string; woNo: string }>(http.post('/erp/pr/workOrder', { orderId, styleId })),
  issueWorkOrder: async (id: string, requestKey: string) => { await http.post<Envelope>(`/erp/pr/workOrder/issue/${encodeURIComponent(id)}`, { requestKey }) },
  reportProcess: async (request: { workOrderProcessId: string; skuId: string; empId: string | null; doneQty: number; badQty: number; reportDate: string; requestKey: string }) => { await http.post<Envelope>('/erp/pr/workOrder/process/report', request) },
  finishWorkOrder: async (id: string, items: { skuId: string; goodQty: number; batchNo: string }[], requestKey: string) => { await http.post<Envelope>(`/erp/pr/workOrder/finish/${encodeURIComponent(id)}`, { skuItems: items, requestKey }) },
  workOrderOptions: (workOrderProcessId?: string) => payload<{ workers: Option[] }>(http.get('/erp/md/workOrderOptions', { params: workOrderProcessId ? { workOrderProcessId } : {} })),
  processWorkers: (processId: string) => payload<{ assigned: { id: string; name: string; position?: string; empType?: number; status?: number }[]; candidates: { id: string; name: string; position?: string }[] }>(http.get(`/erp/md/process/${encodeURIComponent(processId)}/worker`)),
  addProcessWorker: async (processId: string, empId: string) => { await http.post<Envelope>(`/erp/md/process/${encodeURIComponent(processId)}/worker/${encodeURIComponent(empId)}`) },
  removeProcessWorker: async (processId: string, empId: string) => { await http.delete<Envelope>(`/erp/md/process/${encodeURIComponent(processId)}/worker/${encodeURIComponent(empId)}`) },
  shipment: (id: string) => payload<Shipment>(http.get(`/erp/sh/shipment/${encodeURIComponent(id)}`)),
  /** 待出货订单（仓管员入口）：只含订单号/客户/未发数量/成品可用量，不含金额 */
  pendingShipments: () => payload<PendingShipmentOrder[]>(http.get('/erp/sh/pendingShipments')),
  /** 按 SKU 查可用成品批次（含各批次合计结存），出货弹窗下拉用 */
  productBatches: (skuIds: string[]) => payload<{ skuId: string; batchNo: string; qty: number | string }[]>(
    http.get('/erp/sh/shipment/batches', { params: { skuIds: skuIds.join(',') } })),
  createShipment: (orderId: string, logisticsCompany: string, logisticsNo: string, items: StockLine[]) => payload<{ shipmentId: string; shipmentNo: string }>(http.post('/erp/sh/shipment', { orderId, logisticsCompany, logisticsNo, items })),
  ship: async (id: string, requestKey: string) => { await http.post<Envelope>(`/erp/sh/shipment/ship/${encodeURIComponent(id)}`, { requestKey }) },
  sign: async (id: string) => { await http.post<Envelope>(`/erp/sh/shipment/sign/${encodeURIComponent(id)}`) },
  returnOrder: (id: string) => payload<ReturnOrder>(http.get(`/erp/sh/return/${encodeURIComponent(id)}`)),
  createReturn: (shipmentId: string, returnType: number, isRestock: number, reason: string, refundAmount: number, items: StockLine[]) => payload<{ returnId: string; returnNo: string }>(http.post('/erp/sh/return', { shipmentId, returnType, isRestock, reason, refundAmount, items })),
  handleReturn: async (id: string, requestKey: string) => { await http.post<Envelope>(`/erp/sh/return/handle/${encodeURIComponent(id)}`, { requestKey }) },
  settlement: (id: string) => payload<Settlement>(http.get(`/erp/fn/settlement/${encodeURIComponent(id)}`)),
  calculateCost: (orderId: string, styleId: string, requestKey: string) => payload<{ settleId: string }>(http.post('/erp/fn/settlement/calc', { orderId, styleId, requestKey })),
  confirmCost: async (id: string) => { await http.post<Envelope>(`/erp/fn/settlement/confirm/${encodeURIComponent(id)}`) },
  salaryEmployees: async () => {
    const result = (await http.get<Page<{ id: string; code: string; name: string; status: number }>>('/erp/hr/employee/list', { params: { pageNum: 1, pageSize: 500 } })).data
    return result.rows.filter(item => item.status === 1).map(item => ({ id: item.id, name: `${item.code} · ${item.name}` }))
  },
  employeeDepartments: () => payload<Option[]>(http.get('/erp/hr/employee/options')),
  generateSalary: (empId: string, wagePeriod: string) => payload<{ id: string }>(http.post('/erp/hr/salarySlip/generate', { empId, wagePeriod })),
  confirmSalary: async (id: string) => { await http.post<Envelope>(`/erp/hr/salarySlip/confirm/${encodeURIComponent(id)}`) },
  confirmSalaryBatch: async (wagePeriod: string) => { return payload<{ wagePeriod: string; confirmed: number; skipped: { id: string; empId: string; empName: string; reason: string }[] }>(http.post('/erp/hr/salarySlip/confirmBatch', { wagePeriod })) },
  voidSalary: async (id: string) => { await http.post<Envelope>(`/erp/hr/salarySlip/void/${encodeURIComponent(id)}`) },
  quotation: (id: string) => payload<Quotation>(http.get(`/erp/sd/quotation/${encodeURIComponent(id)}`)),
  quotationFromSettlement: (settleIds: string[], requestKey: string) => payload<{ quoteId: string; quoteNo: string }>(http.post('/erp/sd/quotation/fromSettlement', { settleIds, requestKey })),
  transitionQuotation: async (id: string, action: 'accept' | 'expire') => { await http.post<Envelope>(`/erp/sd/quotation/${action}/${encodeURIComponent(id)}`) },
  masterRecord: (resource: string, id: string) => payload<Record<string, unknown>>(http.get(`${masterPath(resource)}/${encodeURIComponent(id)}`)),
  createMaster: (resource: string, values: Record<string, unknown>) => payload<{ id: string }>(http.post(masterPath(resource), values)),
  updateMaster: async (resource: string, id: string, values: Record<string, unknown>) => { await http.put<Envelope>(`${masterPath(resource)}/${encodeURIComponent(id)}`, values) },
  generateSku: (styleId: string, colorId: string, sizeId: string) => payload<{ id: string }>(http.post('/erp/md/sku/generate', { styleId, colorId, sizeId })),
  skuOptions: () => payload<Pick<OrderOptions, 'styles' | 'colors' | 'sizes'>>(http.get('/erp/md/sku/options')),
  styleBom: (id: string) => payload<StyleBomLine[]>(http.get(`/erp/md/style/${encodeURIComponent(id)}/bom`)),
  styleProcesses: (id: string) => payload<StyleProcessLine[]>(http.get(`/erp/md/style/${encodeURIComponent(id)}/process`)),
  stylePlanOptions: () => payload<{ materials: PurchaseOption[]; processes: Option[] }>(http.get('/erp/md/style/options')),
  saveStyleBom: async (id: string, items: { materialId: string; qty: number; lossRate: number }[]) => { await http.put<Envelope>(`/erp/md/style/${encodeURIComponent(id)}/bom`, { items }) },
  saveStyleProcesses: async (id: string, items: { processId: string; seq: number; unitPrice: number }[]) => { await http.put<Envelope>(`/erp/md/style/${encodeURIComponent(id)}/process`, { items }) },
  alerts: () => payload<Alert[]>(http.get('/erp/dashboard/alerts')),
  explainAlert: (alertId: string) => payload<{ alertId: string; explanation: string; modelUsed: boolean }>(http.post('/ai/alerts/explain', { alertId })),
  askAi: (mode: string, question: string) => payload<AiAnswer>(http.post('/ai/erp/ask', { mode, question })),
  adjustStock: (itemType: number, itemId: string, batchNo: string, actualQty: number, reason: string, requestKey: string) => payload<{ itemId: string; previousQty: number; actualQty: number; delta: number }>(http.post('/erp/st/txn/adjust', { itemType, itemId, batchNo, actualQty, reason, requestKey })),
  generatePurchaseFromMrp: (supplierId: string, expectDate: string, materialIds: string[], requestKey: string) => payload<{ poId: string; poNo: string }>(http.post('/erp/pu/suggestion/generate', { supplierId, expectDate, materialIds, requestKey })),

  removeMaster: async (resource: string, id: string) => {
    await http.delete<Envelope>(`${masterPath(resource)}/${encodeURIComponent(id)}`)
  },
  changeEmployeeStatus: async (id: string, status: 0 | 1) => {
    await http.post<Envelope>(`/erp/hr/employee/${encodeURIComponent(id)}/status`, { status })
  },
  deleteOrder: (id: string) => docDelete('/erp/sd/order', id),
  cancelOrder: (id: string) => docCancel('/erp/sd/order', id),
  deletePurchase: (id: string) => docDelete('/erp/pu/purchaseOrder', id),
  cancelPurchase: (id: string) => docCancel('/erp/pu/purchaseOrder', id),
  deleteWorkOrder: (id: string) => docDelete('/erp/pr/workOrder', id),
  cancelWorkOrder: (id: string) => docCancel('/erp/pr/workOrder', id),
  deleteShipment: (id: string) => docDelete('/erp/sh/shipment', id),
  cancelShipment: (id: string) => docCancel('/erp/sh/shipment', id),
  deleteReturn: (id: string) => docDelete('/erp/sh/return', id),
  cancelReturn: (id: string) => docCancel('/erp/sh/return', id),
  deleteQuotation: (id: string) => docDelete('/erp/sd/quotation', id),
  deleteSettlement: (id: string) => docDelete('/erp/fn/settlement', id),
}

async function docDelete(path: string, id: string) {
  await http.delete<Envelope>(`${path}/${encodeURIComponent(id)}`)
}

async function docCancel(path: string, id: string) {
  await http.post<Envelope>(`${path}/cancel/${encodeURIComponent(id)}`)
}

function masterPath(resource: string) { return resource === 'employee' ? '/erp/hr/employee' : `/erp/md/${encodeURIComponent(resource)}` }

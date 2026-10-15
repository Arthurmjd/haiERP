import { http, payload, pageTotal, type Envelope, type Page } from './client'
import { demoApi, isDemoMode } from './demo'

export interface Option { id: string; name: string; code?: string; styleId?: string; colorId?: string; sizeId?: string }
export interface OrderOptions { customers: Option[]; styles: Option[]; colors: Option[]; sizes: Option[]; skus: Option[] }
export interface OrderLine { itemId: string; styleId: string; styleName: string; skuId: string; skuCode: string; colorId: string; colorName: string; sizeId: string; sizeName: string; qty: number; unitPrice: number; amount: number; shippedQty: number; remainingQty: number }
export interface Order { orderId: string; orderNo: string; customerId: string; customerName: string; orderDate: string; deliveryDate: string; status: number; totalAmount: number; fullShipDate: string | null; fulfillmentState: string; lineCount?: number; orderQty?: number; items?: OrderLine[] }
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
export interface WorkOrder { woId: string; woNo: string; orderId: string; styleId: string; styleName: string; qty: number; status: number; planEndDate: string | null; actualStartDate: string | null; actualEndDate: string | null; skus: WorkOrderSku[]; materials: WorkOrderMaterial[]; processes: WorkOrderProcess[] }
export interface ShipmentLine { itemId: string; skuId: string; skuCode: string; batchNo: string; qty: number }
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

const orderStatuses: Record<number, string> = { 0: '草稿', 1: '已确认', 2: '生产中', 3: '已完工', 4: '已发齐', 5: '已完成', 9: '已取消' }
export const orderStatusLabel = (status: number) => orderStatuses[status] || `状态 ${status}`

export const erpApi = {
  options: () => isDemoMode() ? demoApi.options() : payload<OrderOptions>(http.get('/erp/md/orderOptions')),
  orders: async (pageNum: number, keyword: string) => {
    if (isDemoMode()) return demoApi.orders(pageNum, keyword)
    const result = (await http.get<Page<Order>>('/erp/sd/order/list', { params: { pageNum, pageSize: 10, keyword } })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  order: (id: string) => isDemoMode() ? demoApi.order(id) : payload<Order>(http.get(`/erp/sd/order/${encodeURIComponent(id)}`)),
  createOrder: (draft: OrderDraft) => isDemoMode() ? demoApi.createOrder(draft) : payload<{ orderId: string; orderNo: string }>(http.post('/erp/sd/order', draft)),
  confirmOrder: async (id: string) => {
    if (isDemoMode()) return demoApi.confirmOrder(id)
    await http.post<Envelope>(`/erp/sd/order/confirm/${encodeURIComponent(id)}`)
  },
  mrp: () => isDemoMode() ? demoApi.mrp() : payload<MrpRow[]>(http.get('/erp/pu/suggestion/list')),
  inventory: () => isDemoMode() ? demoApi.inventory() : payload<InventoryRow[]>(http.get('/erp/st/inventory/list')),
  overview: () => isDemoMode() ? demoApi.overview() : payload<Overview>(http.get('/erp/dashboard/overview')),
  purchaseOptions: () => isDemoMode() ? demoApi.purchaseOptions() : payload<PurchaseOptions>(http.get('/erp/md/purchaseOptions')),
  purchases: async (pageNum: number, keyword: string) => {
    if (isDemoMode()) return demoApi.purchases(pageNum, keyword)
    const result = (await http.get<Page<PurchaseOrder>>('/erp/pu/purchaseOrder/list', { params: { pageNum, pageSize: 10, keyword } })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  purchase: (id: string) => isDemoMode() ? demoApi.purchase(id) : payload<PurchaseOrder>(http.get(`/erp/pu/purchaseOrder/${encodeURIComponent(id)}`)),
  createPurchase: (draft: PurchaseDraft) => isDemoMode() ? demoApi.createPurchase(draft) : payload<{ poId: string; poNo: string }>(http.post('/erp/pu/purchaseOrder', draft)),
  confirmPurchase: async (id: string) => {
    if (isDemoMode()) return demoApi.confirmPurchase(id)
    await http.post<Envelope>(`/erp/pu/purchaseOrder/confirm/${encodeURIComponent(id)}`)
  },
  receivePurchase: (receipt: PurchaseReceipt) => isDemoMode() ? demoApi.receivePurchase(receipt) : payload<{ poId: string }>(http.post('/erp/pu/purchaseOrder/receive', receipt)),
  workOrder: (id: string) => isDemoMode() ? demoApi.workOrder(id) : payload<WorkOrder>(http.get(`/erp/pr/workOrder/${encodeURIComponent(id)}`)),
  createWorkOrder: (orderId: string, styleId: string) => isDemoMode() ? demoApi.createWorkOrder(orderId, styleId) : payload<{ woId: string; woNo: string }>(http.post('/erp/pr/workOrder', { orderId, styleId })),
  issueWorkOrder: async (id: string, requestKey: string) => { if (isDemoMode()) return demoApi.issueWorkOrder(id); await http.post<Envelope>(`/erp/pr/workOrder/issue/${encodeURIComponent(id)}`, { requestKey }) },
  reportProcess: async (request: { workOrderProcessId: string; skuId: string; empId: string | null; doneQty: number; badQty: number; reportDate: string; requestKey: string }) => { if (isDemoMode()) return demoApi.reportProcess(request); await http.post<Envelope>('/erp/pr/workOrder/process/report', request) },
  finishWorkOrder: async (id: string, items: { skuId: string; goodQty: number; batchNo: string }[], requestKey: string) => { if (isDemoMode()) return demoApi.finishWorkOrder(id, items); await http.post<Envelope>(`/erp/pr/workOrder/finish/${encodeURIComponent(id)}`, { skuItems: items, requestKey }) },
  workOrderOptions: () => isDemoMode() ? demoApi.workOrderOptions() : payload<{ workers: Option[] }>(http.get('/erp/md/workOrderOptions')),
  shipment: (id: string) => isDemoMode() ? demoApi.shipment(id) : payload<Shipment>(http.get(`/erp/sh/shipment/${encodeURIComponent(id)}`)),
  createShipment: (orderId: string, logisticsCompany: string, logisticsNo: string, items: StockLine[]) => isDemoMode() ? demoApi.createShipment(orderId, logisticsCompany, logisticsNo, items) : payload<{ shipmentId: string; shipmentNo: string }>(http.post('/erp/sh/shipment', { orderId, logisticsCompany, logisticsNo, items })),
  ship: async (id: string, requestKey: string) => { if (isDemoMode()) return demoApi.ship(id); await http.post<Envelope>(`/erp/sh/shipment/ship/${encodeURIComponent(id)}`, { requestKey }) },
  sign: async (id: string) => { if (isDemoMode()) return demoApi.sign(id); await http.post<Envelope>(`/erp/sh/shipment/sign/${encodeURIComponent(id)}`) },
  returnOrder: (id: string) => isDemoMode() ? demoApi.returnOrder(id) : payload<ReturnOrder>(http.get(`/erp/sh/return/${encodeURIComponent(id)}`)),
  createReturn: (shipmentId: string, returnType: number, isRestock: number, reason: string, refundAmount: number, items: StockLine[]) => isDemoMode() ? demoApi.createReturn(shipmentId, returnType, isRestock, reason, refundAmount, items) : payload<{ returnId: string; returnNo: string }>(http.post('/erp/sh/return', { shipmentId, returnType, isRestock, reason, refundAmount, items })),
  handleReturn: async (id: string, requestKey: string) => { if (isDemoMode()) return demoApi.handleReturn(id); await http.post<Envelope>(`/erp/sh/return/handle/${encodeURIComponent(id)}`, { requestKey }) },
  settlement: (id: string) => isDemoMode() ? demoApi.settlement(id) : payload<Settlement>(http.get(`/erp/fn/settlement/${encodeURIComponent(id)}`)),
  calculateCost: (orderId: string, styleId: string, requestKey: string) => isDemoMode() ? demoApi.calculateCost(orderId, styleId) : payload<{ settleId: string }>(http.post('/erp/fn/settlement/calc', { orderId, styleId, requestKey })),
  confirmCost: async (id: string) => { if (isDemoMode()) return demoApi.confirmCost(id); await http.post<Envelope>(`/erp/fn/settlement/confirm/${encodeURIComponent(id)}`) },
  salaryEmployees: async () => {
    if (isDemoMode()) return demoApi.salaryEmployees()
    const result = (await http.get<Page<{ id: string; code: string; name: string; status: number }>>('/erp/hr/employee/list', { params: { pageNum: 1, pageSize: 500 } })).data
    return result.rows.filter(item => item.status === 1).map(item => ({ id: item.id, name: `${item.code} · ${item.name}` }))
  },
  employeeDepartments: () => isDemoMode() ? Promise.resolve<Option[]>([{ id: '1', name: '裁剪车间' }, { id: '2', name: '缝制车间' }, { id: '6', name: '财务部' }]) : payload<Option[]>(http.get('/erp/hr/employee/options')),
  generateSalary: (empId: string, wagePeriod: string) => isDemoMode() ? demoApi.generateSalary(empId, wagePeriod) : payload<{ id: string }>(http.post('/erp/hr/salarySlip/generate', { empId, wagePeriod })),
  confirmSalary: async (id: string) => { if (isDemoMode()) return demoApi.confirmSalary(id); await http.post<Envelope>(`/erp/hr/salarySlip/confirm/${encodeURIComponent(id)}`) },
  quotation: (id: string) => isDemoMode() ? demoApi.quotation(id) : payload<Quotation>(http.get(`/erp/sd/quotation/${encodeURIComponent(id)}`)),
  quotationFromSettlement: (settleIds: string[], requestKey: string) => isDemoMode() ? demoApi.quotationFromSettlement(settleIds) : payload<{ quoteId: string; quoteNo: string }>(http.post('/erp/sd/quotation/fromSettlement', { settleIds, requestKey })),
  transitionQuotation: async (id: string, action: 'accept' | 'expire') => { if (isDemoMode()) return demoApi.transitionQuotation(id, action); await http.post<Envelope>(`/erp/sd/quotation/${action}/${encodeURIComponent(id)}`) },
  masterRecord: (resource: string, id: string) => payload<Record<string, unknown>>(http.get(`${masterPath(resource)}/${encodeURIComponent(id)}`)),
  createMaster: (resource: string, values: Record<string, unknown>) => isDemoMode() ? demoApi.saveMaster(resource, null, values) : payload<{ id: string }>(http.post(masterPath(resource), values)),
  updateMaster: async (resource: string, id: string, values: Record<string, unknown>) => { if (isDemoMode()) { await demoApi.saveMaster(resource, id, values); return } await http.put<Envelope>(`${masterPath(resource)}/${encodeURIComponent(id)}`, values) },
  generateSku: (styleId: string, colorId: string, sizeId: string) => isDemoMode() ? demoApi.generateSku(styleId, colorId, sizeId) : payload<{ id: string }>(http.post('/erp/md/sku/generate', { styleId, colorId, sizeId })),
  skuOptions: () => isDemoMode() ? demoApi.options() : payload<Pick<OrderOptions, 'styles' | 'colors' | 'sizes'>>(http.get('/erp/md/sku/options')),
  styleBom: (id: string) => isDemoMode() ? demoApi.styleBom(id) : payload<StyleBomLine[]>(http.get(`/erp/md/style/${encodeURIComponent(id)}/bom`)),
  styleProcesses: (id: string) => isDemoMode() ? demoApi.styleProcesses(id) : payload<StyleProcessLine[]>(http.get(`/erp/md/style/${encodeURIComponent(id)}/process`)),
  stylePlanOptions: () => isDemoMode() ? demoApi.stylePlanOptions() : payload<{ materials: PurchaseOption[]; processes: Option[] }>(http.get('/erp/md/style/options')),
  saveStyleBom: async (id: string, items: { materialId: string; qty: number; lossRate: number }[]) => { if (isDemoMode()) return demoApi.saveStyleBom(id, items); await http.put<Envelope>(`/erp/md/style/${encodeURIComponent(id)}/bom`, { items }) },
  saveStyleProcesses: async (id: string, items: { processId: string; seq: number; unitPrice: number }[]) => { if (isDemoMode()) return demoApi.saveStyleProcesses(id, items); await http.put<Envelope>(`/erp/md/style/${encodeURIComponent(id)}/process`, { items }) },
  alerts: () => isDemoMode() ? demoApi.alerts() : payload<Alert[]>(http.get('/erp/dashboard/alerts')),
  explainAlert: (alertId: string) => isDemoMode() ? demoApi.explainAlert(alertId) : payload<{ alertId: string; explanation: string; modelUsed: boolean }>(http.post('/ai/alerts/explain', { alertId })),
  askAi: (mode: string, question: string) => isDemoMode() ? demoApi.askAi(mode, question) : payload<AiAnswer>(http.post('/ai/erp/ask', { mode, question })),
  adjustStock: (itemType: number, itemId: string, batchNo: string, actualQty: number, reason: string, requestKey: string) => isDemoMode() ? demoApi.adjustStock(itemType, itemId, batchNo, actualQty, reason) : payload<{ itemId: string; previousQty: number; actualQty: number; delta: number }>(http.post('/erp/st/txn/adjust', { itemType, itemId, batchNo, actualQty, reason, requestKey })),
  generatePurchaseFromMrp: (supplierId: string, expectDate: string, materialIds: string[], requestKey: string) => isDemoMode() ? demoApi.generatePurchaseFromMrp(supplierId, expectDate, materialIds) : payload<{ poId: string; poNo: string }>(http.post('/erp/pu/suggestion/generate', { supplierId, expectDate, materialIds, requestKey })),
}

function masterPath(resource: string) { return resource === 'employee' ? '/erp/hr/employee' : `/erp/md/${encodeURIComponent(resource)}` }

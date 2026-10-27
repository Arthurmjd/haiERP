import type { AiAnswer, Alert, InventoryRow, MrpRow, Order, OrderDraft, OrderLine, OrderOptions, Overview, PurchaseDraft, PurchaseOrder, PurchaseOptions, PurchaseReceipt, Quotation, ReturnOrder, Settlement, Shipment, StockLine, StyleBomLine, StyleProcessLine, WorkOrder } from './erp'

const modeKey = 'hai-erp-demo'
const ordersKey = 'hai-erp-demo-orders'
const purchasesKey = 'hai-erp-demo-purchases'
const workOrdersKey = 'hai-erp-demo-work-orders'
const shipmentsKey = 'hai-erp-demo-shipments'
const returnsKey = 'hai-erp-demo-returns'
const settlementsKey = 'hai-erp-demo-settlements'
const salariesKey = 'hai-erp-demo-salaries'
const quotationsKey = 'hai-erp-demo-quotations'
const masterChangesKey = 'hai-erp-demo-master-changes'
const stylePlansKey = 'hai-erp-demo-style-plans'
const inventoryKey = 'hai-erp-demo-inventory'

export const isDemoMode = () => import.meta.env.DEV && sessionStorage.getItem(modeKey) === '1'

export function enterDemoMode() {
  if (!import.meta.env.DEV) return
  sessionStorage.setItem(modeKey, '1')
}

export function leaveDemoMode() {
  sessionStorage.removeItem(modeKey)
  sessionStorage.removeItem(ordersKey)
  sessionStorage.removeItem(purchasesKey)
  sessionStorage.removeItem(workOrdersKey)
  sessionStorage.removeItem(shipmentsKey)
  sessionStorage.removeItem(returnsKey)
  sessionStorage.removeItem(settlementsKey)
  sessionStorage.removeItem(salariesKey)
  sessionStorage.removeItem(quotationsKey)
  sessionStorage.removeItem(masterChangesKey)
  sessionStorage.removeItem(stylePlansKey)
  sessionStorage.removeItem(inventoryKey)
}

const options: OrderOptions = {
  customers: [
    { id: '1', code: 'C001', name: '跃动体育' },
    { id: '2', code: 'C002', name: '星野运动' },
    { id: '3', code: 'C003', name: '蓝途户外' },
  ],
  styles: [
    { id: '1', code: 'TS-101', name: '速干训练上衣' },
    { id: '2', code: 'PT-205', name: '弹力训练长裤' },
    { id: '3', code: 'SK-310', name: '运动中筒袜' },
  ],
  colors: [
    { id: '1', name: '墨绿' }, { id: '2', name: '石墨黑' }, { id: '3', name: '云白' },
  ],
  sizes: [
    { id: '1', name: 'S' }, { id: '2', name: 'M' }, { id: '3', name: 'L' }, { id: '4', name: '均码' },
  ],
  skus: [
    { id: '1', code: 'TS101-G-M', styleId: '1', colorId: '1', sizeId: '2', name: '速干训练上衣 / 墨绿 / M' },
    { id: '2', code: 'TS101-G-L', styleId: '1', colorId: '1', sizeId: '3', name: '速干训练上衣 / 墨绿 / L' },
    { id: '3', code: 'TS101-W-M', styleId: '1', colorId: '3', sizeId: '2', name: '速干训练上衣 / 云白 / M' },
    { id: '4', code: 'PT205-B-M', styleId: '2', colorId: '2', sizeId: '2', name: '弹力训练长裤 / 石墨黑 / M' },
    { id: '5', code: 'PT205-B-L', styleId: '2', colorId: '2', sizeId: '3', name: '弹力训练长裤 / 石墨黑 / L' },
    { id: '6', code: 'SK310-W-OS', styleId: '3', colorId: '3', sizeId: '4', name: '运动中筒袜 / 云白 / 均码' },
  ],
}

function dateOffset(days: number) {
  const date = new Date()
  date.setDate(date.getDate() + days)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

function line(skuId: string, qty: number, unitPrice: number, shippedQty = 0, itemId = skuId): OrderLine {
  const sku = options.skus.find(item => item.id === skuId)!
  return {
    itemId, skuId, skuCode: sku.code!, styleId: sku.styleId!,
    styleName: options.styles.find(item => item.id === sku.styleId)!.name,
    colorId: sku.colorId!, colorName: options.colors.find(item => item.id === sku.colorId)!.name,
    sizeId: sku.sizeId!, sizeName: options.sizes.find(item => item.id === sku.sizeId)!.name,
    qty, unitPrice, amount: qty * unitPrice, shippedQty, remainingQty: qty - shippedQty,
  }
}

function seedOrders(): Order[] {
  const year = new Date().getFullYear()
  const seeded: Array<{ id: string; customerId: string; status: number; orderDate: string; deliveryDate: string; fullShipDate: string | null; items: OrderLine[] }> = [
    { id: '1', customerId: '1', status: 2, orderDate: dateOffset(-21), deliveryDate: dateOffset(-3), fullShipDate: null, items: [line('1', 240, 78, 80), line('2', 180, 78, 0, '2')] },
    { id: '2', customerId: '2', status: 1, orderDate: dateOffset(-7), deliveryDate: dateOffset(10), fullShipDate: null, items: [line('4', 160, 128), line('5', 120, 128, 0, '4')] },
    { id: '3', customerId: '3', status: 4, orderDate: dateOffset(-35), deliveryDate: dateOffset(-5), fullShipDate: dateOffset(-6), items: [line('6', 500, 24, 500)] },
    { id: '4', customerId: '1', status: 0, orderDate: dateOffset(-1), deliveryDate: dateOffset(18), fullShipDate: null, items: [line('3', 90, 78)] },
  ]
  return seeded.map(item => ({
    orderId: item.id, orderNo: `SO${year}${item.id.padStart(6, '0')}`,
    customerId: item.customerId, customerName: options.customers.find(customer => customer.id === item.customerId)!.name,
    orderDate: item.orderDate, deliveryDate: item.deliveryDate, status: item.status,
    totalAmount: item.items.reduce((sum, row) => sum + row.amount, 0),
    fullShipDate: item.fullShipDate, fulfillmentState: item.fullShipDate ? '准时发齐' : '未发齐',
    lineCount: item.items.length, orderQty: item.items.reduce((sum, row) => sum + row.qty, 0), items: item.items,
  }))
}

function readOrders(): Order[] {
  const stored = sessionStorage.getItem(ordersKey)
  if (stored) {
    try {
      const parsed: unknown = JSON.parse(stored)
      if (Array.isArray(parsed)) return parsed as Order[]
    } catch { /* Reset an invalid local preview snapshot. */ }
  }
  const orders = seedOrders()
  sessionStorage.setItem(ordersKey, JSON.stringify(orders))
  return orders
}

function saveOrders(orders: Order[]) {
  sessionStorage.setItem(ordersKey, JSON.stringify(orders))
}

const inventory: InventoryRow[] = [
  { inventoryId: '1', itemType: 1, itemId: '1', itemCode: 'FAB-001', itemName: '速干针织面料', batchNo: 'B2609-18', qty: 85, safeStock: 120, unit: '米' },
  { inventoryId: '2', itemType: 1, itemId: '2', itemCode: 'FAB-002', itemName: '弹力梭织面料', batchNo: 'B2610-02', qty: 210, safeStock: 100, unit: '米' },
  { inventoryId: '3', itemType: 1, itemId: '3', itemCode: 'ACC-012', itemName: '松紧腰带', batchNo: 'A2610-04', qty: 75, safeStock: 100, unit: '米' },
  { inventoryId: '4', itemType: 1, itemId: '4', itemCode: 'ACC-018', itemName: '领口包边条', batchNo: 'A2609-22', qty: 310, safeStock: 100, unit: '米' },
  { inventoryId: '5', itemType: 2, itemId: '6', itemCode: 'SK310-W-OS', itemName: '运动中筒袜 / 云白', batchNo: 'FG2610-03', qty: 120, safeStock: 50, unit: '双' },
]
function readInventory(): InventoryRow[] {
  const saved = sessionStorage.getItem(inventoryKey)
  if (saved) { try { return JSON.parse(saved) as InventoryRow[] } catch { /* Reset invalid preview data. */ } }
  sessionStorage.setItem(inventoryKey, JSON.stringify(inventory)); return inventory.map(item => ({ ...item }))
}

const mrp: MrpRow[] = [
  { materialId: '1', materialCode: 'FAB-001', materialName: '速干针织面料', unit: '米', demandQty: 380, stockQty: 85, onWayQty: 120, gapQty: 175, suggestQty: 175, estAmount: 5775, shortageFlag: 1 },
  { materialId: '2', materialCode: 'FAB-002', materialName: '弹力梭织面料', unit: '米', demandQty: 260, stockQty: 210, onWayQty: 80, gapQty: 0, suggestQty: 0, estAmount: 0, shortageFlag: 0 },
  { materialId: '3', materialCode: 'ACC-012', materialName: '松紧腰带', unit: '米', demandQty: 160, stockQty: 75, onWayQty: 0, gapQty: 85, suggestQty: 85, estAmount: 552.5, shortageFlag: 1 },
  { materialId: '4', materialCode: 'ACC-018', materialName: '领口包边条', unit: '米', demandQty: 205, stockQty: 310, onWayQty: 0, gapQty: 0, suggestQty: 0, estAmount: 0, shortageFlag: 0 },
]

const purchaseOptions: PurchaseOptions = {
  suppliers: [{ id: '1', name: '华织面料' }, { id: '2', name: '卓越辅料' }],
  materials: [
    { id: '1', code: 'FAB-001', name: '速干针织面料', unit: '米', refPrice: 33 },
    { id: '2', code: 'FAB-002', name: '弹力梭织面料', unit: '米', refPrice: 42 },
    { id: '3', code: 'ACC-012', name: '松紧腰带', unit: '米', refPrice: 6.5 },
    { id: '4', code: 'ACC-018', name: '领口包边条', unit: '米', refPrice: 3.2 },
  ],
}

function readPurchases(): PurchaseOrder[] {
  const saved = sessionStorage.getItem(purchasesKey)
  if (saved) {
    try { return JSON.parse(saved) as PurchaseOrder[] } catch { /* Reset invalid preview data. */ }
  }
  const seeded: PurchaseOrder[] = [{
    poId: '1', poNo: `PO${new Date().getFullYear()}000001`, supplierId: '1', supplierName: '华织面料',
    orderDate: dateOffset(-4), expectDate: dateOffset(5), actualArrivalDate: null,
    status: 2, totalAmount: 5775, lineCount: 1,
    items: [{ itemId: '1', materialId: '1', materialCode: 'FAB-001', materialName: '速干针织面料', unit: '米', qty: 175, unitPrice: 33, amount: 5775, receivedQty: 40, remainingQty: 135 }],
  }]
  sessionStorage.setItem(purchasesKey, JSON.stringify(seeded))
  return seeded
}

function savePurchases(purchases: PurchaseOrder[]) { sessionStorage.setItem(purchasesKey, JSON.stringify(purchases)) }

function readWorkOrders(): WorkOrder[] {
  const saved = sessionStorage.getItem(workOrdersKey)
  if (saved) {
    try { return JSON.parse(saved) as WorkOrder[] } catch { /* Reset invalid preview data. */ }
  }
  const seeded: WorkOrder[] = [{
    woId: '1', woNo: `WO${new Date().getFullYear()}000001`, orderId: '1', styleId: '1', styleName: '速干训练上衣',
    qty: 420, status: 1, planEndDate: dateOffset(8), actualStartDate: dateOffset(-2), actualEndDate: null,
    skus: [{ skuId: '1', skuCode: 'TS101-BK-M', planQty: 240, completedQty: 0 }, { skuId: '2', skuCode: 'TS101-BK-L', planQty: 180, completedQty: 0 }],
    materials: [{ id: '1', materialId: '1', materialCode: 'FAB-001', materialName: '速干针织面料', unit: '米', planQty: 380, issuedQty: 380 }],
    processes: [{ id: '1', processName: '裁剪', seq: 1, planQty: 420, doneQty: 0, badQty: 0, isOutsourced: 0, status: 0 }, { id: '2', processName: '缝制', seq: 2, planQty: 420, doneQty: 0, badQty: 0, isOutsourced: 0, status: 0 }],
  }]
  sessionStorage.setItem(workOrdersKey, JSON.stringify(seeded))
  return seeded
}
function saveWorkOrders(orders: WorkOrder[]) { sessionStorage.setItem(workOrdersKey, JSON.stringify(orders)) }

function readShipments(): Shipment[] {
  const saved = sessionStorage.getItem(shipmentsKey)
  if (saved) { try { return JSON.parse(saved) as Shipment[] } catch { /* Reset invalid preview data. */ } }
  const seeded: Shipment[] = [{ shipmentId: '1', shipmentNo: `SH${new Date().getFullYear()}000001`, orderId: '3', customerName: '蓝途户外', shipDate: dateOffset(-6), logisticsCompany: '顺丰速运', logisticsNo: 'SF20260001', status: 2, items: [{ itemId: '1', skuId: '6', skuCode: 'SK310-W-OS', batchNo: 'FG2610-03', qty: 500 }] }]
  sessionStorage.setItem(shipmentsKey, JSON.stringify(seeded))
  return seeded
}
function saveShipments(rows: Shipment[]) { sessionStorage.setItem(shipmentsKey, JSON.stringify(rows)) }
function readReturns(): ReturnOrder[] {
  const saved = sessionStorage.getItem(returnsKey)
  if (saved) { try { return JSON.parse(saved) as ReturnOrder[] } catch { /* Reset invalid preview data. */ } }
  sessionStorage.setItem(returnsKey, '[]'); return []
}
function saveReturns(rows: ReturnOrder[]) { sessionStorage.setItem(returnsKey, JSON.stringify(rows)) }
function readSettlements(): Settlement[] {
  const saved = sessionStorage.getItem(settlementsKey)
  if (saved) { try { return JSON.parse(saved) as Settlement[] } catch { /* Reset invalid preview data. */ } }
  const seeded: Settlement[] = [{ settleId: '1', settleNo: `CS${new Date().getFullYear()}000001`, orderId: '3', styleId: '3', styleName: '运动中筒袜', qty: 500, materialCost: 4400, laborCost: 1100, overheadRate: 8, overheadCost: 440, totalCost: 5940, unitCost: 11.88, revenue: 12000, grossProfit: 6060, grossMargin: 50.5, status: 1, calcTime: dateOffset(-5), costBasis: 'standard', details: [{ lineType: 1, sourceId: '1', qty: 500, unitPrice: 8.8, amount: 4400, priceSource: 'purchase_snapshot' }, { lineType: 2, sourceId: '1', qty: 500, unitPrice: 2.2, amount: 1100, priceSource: 'work_order_snapshot' }, { lineType: 3, sourceId: '0', qty: 1, unitPrice: 440, amount: 440, priceSource: 'frozen_overhead_rate' }] }]
  sessionStorage.setItem(settlementsKey, JSON.stringify(seeded)); return seeded
}
function saveSettlements(rows: Settlement[]) { sessionStorage.setItem(settlementsKey, JSON.stringify(rows)) }
interface DemoSalary { id: string; employeeName: string; empId: string; wagePeriod: string; baseSalary: number; pieceAmount: number; grossPay: number; netPay: number; status: number }
function readSalaries(): DemoSalary[] {
  const saved = sessionStorage.getItem(salariesKey)
  if (saved) { try { return JSON.parse(saved) as DemoSalary[] } catch { /* Reset invalid preview data. */ } }
  sessionStorage.setItem(salariesKey, '[]'); return []
}
function saveSalaries(rows: DemoSalary[]) { sessionStorage.setItem(salariesKey, JSON.stringify(rows)) }
function readQuotations(): Quotation[] {
  const saved = sessionStorage.getItem(quotationsKey)
  if (saved) { try { return JSON.parse(saved) as Quotation[] } catch { /* Reset invalid preview data. */ } }
  sessionStorage.setItem(quotationsKey, '[]'); return []
}
function saveQuotations(rows: Quotation[]) { sessionStorage.setItem(quotationsKey, JSON.stringify(rows)) }
function readMasterChanges(): Record<string, Record<string, unknown>[]> {
  try { return JSON.parse(sessionStorage.getItem(masterChangesKey) || '{}') as Record<string, Record<string, unknown>[]> }
  catch { return {} }
}
function readStylePlans(): Record<string, { bom: StyleBomLine[]; processes: StyleProcessLine[] }> {
  try { return JSON.parse(sessionStorage.getItem(stylePlansKey) || '{}') as Record<string, { bom: StyleBomLine[]; processes: StyleProcessLine[] }> }
  catch { return {} }
}

export const demoApi = {
  // 演示模式下的操作日志：给出几条样例，真实环境读 sys_oper_log
  operLogs: async (query: { pageNum: number; pageSize?: number; title?: string; businessType?: number; status?: number }) => {
    const all = [
      { operId: 3, title: '销售订单', businessType: 1, method: 'ErpOrderController.create()', operName: '销售员', operUrl: '/erp/sd/order', operIp: '127.0.0.1', status: 0, operTime: '2026-10-26 09:12:30', errorMsg: null },
      { operId: 2, title: '销售订单确认', businessType: 2, method: 'ErpOrderController.confirm()', operName: '销售员', operUrl: '/erp/sd/order/confirm/8', operIp: '127.0.0.1', status: 0, operTime: '2026-10-26 09:13:02', errorMsg: null },
      { operId: 1, title: '采购单作废', businessType: 2, method: 'ErpPurchaseController.cancel()', operName: '计划员', operUrl: '/erp/pu/purchaseOrder/cancel/8', operIp: '127.0.0.1', status: 0, operTime: '2026-10-26 10:02:41', errorMsg: null },
    ]
    const filtered = all.filter(row =>
      (!query.title || row.title.includes(query.title)) &&
      (query.businessType === undefined || row.businessType === query.businessType) &&
      (query.status === undefined || row.status === query.status))
    const size = query.pageSize || 10
    return { rows: filtered.slice((query.pageNum - 1) * size, query.pageNum * size), total: filtered.length }
  },
  generatePurchaseFromMrp: async (supplierId: string, expectDate: string, materialIds: string[]) => {
    const items = materialIds.map(materialId => {
      const row = mrp.find(item => item.materialId === materialId && item.shortageFlag === 1)
      const material = purchaseOptions.materials.find(item => item.id === materialId)
      if (!row || !material) throw new Error('物料没有有效净缺口')
      return { materialId, qty: row.suggestQty, unitPrice: material.refPrice || 0 }
    })
    return demoApi.createPurchase({ supplierId, expectDate, items })
  },
  adjustStock: async (itemType: number, itemId: string, batchNo: string, actualQty: number, reason: string) => {
    const rows = readInventory(); const row = rows.find(item => item.itemType === itemType && item.itemId === itemId && item.batchNo === batchNo)
    if (!row || actualQty < 0 || !reason.trim()) throw new Error('盘点对象、数量或原因不合法')
    const previousQty = row.qty; row.qty = actualQty
    sessionStorage.setItem(inventoryKey, JSON.stringify(rows))
    return { itemId, previousQty, actualQty, delta: actualQty - previousQty }
  },
  alerts: async (): Promise<Alert[]> => [
    ...mrp.filter(item => item.shortageFlag === 1).map(item => ({ id: `mrp:${item.materialId}`, alertType: 'shortage', title: '物料缺口', subject: item.materialCode, triggerValue: item.gapQty, unit: item.unit, sourceType: 'md_material', sourceId: item.materialId })),
    ...readOrders().filter(item => item.status > 0 && item.status < 4 && item.deliveryDate < dateOffset(0)).map(item => ({ id: `order:${item.orderId}`, alertType: 'overdue', title: '订单逾期未发齐', subject: item.orderNo, triggerValue: Math.max(1,Math.round((Date.now() - new Date(item.deliveryDate).getTime()) / 86400000)), unit: '天', sourceType: 'sd_order', sourceId: item.orderId })),
  ],
  explainAlert: async (alertId: string) => {
    const alert = (await demoApi.alerts()).find(item => item.id === alertId)
    if (!alert) throw new Error('预警不存在')
    const explanation = alert.alertType === 'shortage' ? `物料 ${alert.subject} 当前净缺口 ${alert.triggerValue}${alert.unit}。请核对在途采购和生产计划。` : `订单 ${alert.subject} 已逾期 ${alert.triggerValue} 天且尚未发齐。请核对工单与成品库存。`
    return { alertId, explanation, modelUsed: false }
  },
  stylePlanOptions: async () => ({ materials: purchaseOptions.materials, processes: [{ id: '1', name: '裁剪' }, { id: '2', name: '缝制' }, { id: '3', name: '整烫' }] }),
  styleBom: async (id: string) => readStylePlans()[id]?.bom || [{ materialId: '1', materialCode: 'FAB-001', materialName: '速干针织面料', qty: 0.8, lossRate: 5, unit: '米' }],
  styleProcesses: async (id: string) => readStylePlans()[id]?.processes || [{ processId: '1', processName: '裁剪', seq: 1, unitPrice: 1.2 }, { processId: '2', processName: '缝制', seq: 2, unitPrice: 3.5 }],
  saveStyleBom: async (id: string, items: { materialId: string; qty: number; lossRate: number }[]) => {
    if (!items.length) throw new Error('BOM 至少有一项')
    const plans = readStylePlans(); const options = await demoApi.stylePlanOptions()
    plans[id] = { bom: items.map(item => { const material = options.materials.find(value => value.id === item.materialId); if (!material) throw new Error('物料无效'); return { ...item, materialCode: material.code || '', materialName: material.name, unit: material.unit || '' } }), processes: await demoApi.styleProcesses(id) }
    sessionStorage.setItem(stylePlansKey, JSON.stringify(plans))
  },
  saveStyleProcesses: async (id: string, items: { processId: string; seq: number; unitPrice: number }[]) => {
    if (!items.length) throw new Error('至少一道工序')
    const plans = readStylePlans(); const options = await demoApi.stylePlanOptions()
    plans[id] = { bom: await demoApi.styleBom(id), processes: items.map(item => ({ ...item, processName: options.processes.find(value => value.id === item.processId)?.name || '' })) }
    sessionStorage.setItem(stylePlansKey, JSON.stringify(plans))
  },
  masterRows: (resource: string, base: Record<string, unknown>[]) => {
    const changes = readMasterChanges()[resource] || []
    const merged = base.map(item => ({ ...item, ...changes.find(value => value.id === item.id) }))
    return [...merged, ...changes.filter(item => !base.some(value => value.id === item.id))]
  },
  saveMaster: async (resource: string, id: string | null, values: Record<string, unknown>) => {
    const all = readMasterChanges(); const rows = all[resource] || []
    const newId = id || String(Date.now())
    const old = rows.find(item => item.id === newId)
    const entry = { ...old, ...values, id: newId }
    if (old) Object.assign(old, entry); else rows.push(entry)
    all[resource] = rows; sessionStorage.setItem(masterChangesKey, JSON.stringify(all))
    return { id: newId }
  },
  generateSku: async (styleId: string, colorId: string, sizeId: string) => {
    const style = options.styles.find(item => item.id === styleId)
    const color = options.colors.find(item => item.id === colorId)
    const size = options.sizes.find(item => item.id === sizeId)
    if (!style || !color || !size) throw new Error('请选择有效款式、颜色和尺码')
    const row = await demoApi.saveMaster('sku', null, { code: `${style.code}-${color.code || color.name}-${size.code || size.name}`, name: style.name, styleName: style.name, colorName: color.name, sizeName: size.name, status: 1 })
    return row
  },
  quotations: async () => readQuotations(),
  quotation: async (id: string) => {
    const found = readQuotations().find(item => item.quoteId === id)
    if (!found) throw new Error('报价单不存在')
    return found
  },
  transitionQuotation: async (id: string, action: 'accept' | 'expire') => {
    const rows = readQuotations(); const found = rows.find(item => item.quoteId === id)
    if (!found || found.status !== 0) throw new Error('报价单不存在或已处理')
    found.status = action === 'accept' ? 1 : 2
    saveQuotations(rows)
  },
  quotationFromSettlement: async (settleIds: string[]) => {
    const settlements = readSettlements().filter(item => settleIds.includes(item.settleId))
    if (!settlements.length || settlements.length !== settleIds.length || settlements.some(item => item.status !== 1)) throw new Error('只能引用已确认结算')
    const rows = readQuotations(); const quoteId = String(Math.max(...rows.map(item => Number(item.quoteId)), 0) + 1)
    const quoteNo = `QT${new Date().getFullYear()}${quoteId.padStart(6, '0')}`
    const items = settlements.map((item, index) => ({ itemId: `${quoteId}-${index}`, styleId: item.styleId, styleName: item.styleName, qty: item.qty, unitPrice: Math.round(item.revenue / item.qty * 100) / 100, costRef: item.unitCost, costSrc: 1, sourceSettleId: item.settleId, amount: item.revenue }))
    rows.push({ quoteId, quoteNo, customerName: readOrders().find(item => item.orderId === settlements[0].orderId)?.customerName || '客户', quoteDate: dateOffset(0), status: 0, totalAmount: items.reduce((sum, item) => sum + item.amount, 0), items })
    saveQuotations(rows); return { quoteId, quoteNo }
  },
  askAi: async (mode: string, question: string): Promise<AiAnswer> => {
    if (!question.trim() || question.length > 500) throw new Error('问题长度应为 1 到 500 字')
    let rows: Record<string, unknown>[]
    if (mode === 'orders') rows = (await demoApi.orders(1, '')).rows.slice(0, 20).map(item => ({ orderNo: item.orderNo, deliveryDate: item.deliveryDate, status: item.status, totalAmount: item.totalAmount }))
    else if (mode === 'inventory') rows = (await demoApi.inventory()).slice(0, 20).map(item => ({ itemCode: item.itemCode, batchNo: item.batchNo, qty: item.qty, safeStock: item.safeStock }))
    else if (mode === 'shortage') rows = (await demoApi.mrp()).filter(item => item.shortageFlag).slice(0, 20).map(item => ({ materialCode: item.materialCode, demandQty: item.demandQty, stockQty: item.stockQty, onWayQty: item.onWayQty, gapQty: item.gapQty }))
    else if (mode === 'cost') rows = (await demoApi.settlements()).filter(item => item.status === 1).slice(0, 20).map(item => ({ styleName: item.styleName, totalQty: item.qty, avgUnitCost: item.unitCost, totalRevenue: item.revenue, totalGrossProfit: item.grossProfit, grossMargin: item.grossMargin }))
    else if (mode === 'ontime') rows = (await demoApi.orders(1, '')).rows.slice(0, 20).map(item => ({ orderNo: item.orderNo, deliveryDate: item.deliveryDate, fullShipDate: item.fullShipDate, deliveryState: item.fulfillmentState }))
    else throw new Error('查询范围不合法')
    return { answer: rows.length ? `已查询 ${rows.length} 条记录，请核对下方数据。` : '当前范围没有记录。', rows, source: mode, dataTime: new Date().toLocaleString('sv-SE'), modelUsed: false }
  },
  settlements: async () => readSettlements(),
  settlement: async (id: string) => {
    const found = readSettlements().find(item => item.settleId === id)
    if (!found) throw new Error('结算单不存在')
    return found
  },
  calculateCost: async (orderId: string, styleId: string) => {
    const order = readOrders().find(item => item.orderId === orderId)
    const lines = order?.items?.filter(item => item.styleId === styleId) || []
    if (!order || !lines.length) throw new Error('订单款式不存在')
    const rows = readSettlements(); const existing = rows.find(item => item.orderId === orderId && item.styleId === styleId)
    if (existing?.status === 1) throw new Error('已确认结算不得重算')
    const settleId = existing?.settleId || String(Math.max(...rows.map(item => Number(item.settleId)), 0) + 1)
    const qty = lines.reduce((sum, item) => sum + item.qty, 0)
    const revenue = lines.reduce((sum, item) => sum + item.amount, 0)
    const materialCost = Math.round(qty * 18 * 100) / 100; const laborCost = Math.round(qty * 4 * 100) / 100
    const overheadCost = Math.round((materialCost + laborCost) * 8) / 100
    const totalCost = materialCost + laborCost + overheadCost; const grossProfit = revenue - totalCost
    const calculated: Settlement = { settleId, settleNo: `CS${new Date().getFullYear()}${settleId.padStart(6, '0')}`, orderId, styleId, styleName: lines[0].styleName, qty, materialCost, laborCost, overheadRate: 8, overheadCost, totalCost, unitCost: Math.round(totalCost / qty * 100) / 100, revenue, grossProfit, grossMargin: revenue ? Math.round(grossProfit / revenue * 10000) / 100 : 0, status: 0, calcTime: dateOffset(0), costBasis: 'standard', details: [{ lineType: 1, sourceId: '1', qty, unitPrice: 18, amount: materialCost, priceSource: 'reference_snapshot' }, { lineType: 2, sourceId: '1', qty, unitPrice: 4, amount: laborCost, priceSource: 'route_estimate' }, { lineType: 3, sourceId: '0', qty: 1, unitPrice: overheadCost, amount: overheadCost, priceSource: 'frozen_overhead_rate' }] }
    if (existing) Object.assign(existing, calculated); else rows.push(calculated)
    saveSettlements(rows); return { settleId }
  },
  confirmCost: async (id: string) => {
    const rows = readSettlements(); const settlement = rows.find(item => item.settleId === id)
    const wo = readWorkOrders().find(item => item.orderId === settlement?.orderId && item.styleId === settlement?.styleId)
    const order = readOrders().find(item => item.orderId === settlement?.orderId)
    if (!settlement || settlement.status !== 0 || wo?.status !== 2 || order?.items?.some(item => item.styleId === settlement.styleId && item.remainingQty > 0)) throw new Error('工单未完工或该款未发齐')
    settlement.status = 1; saveSettlements(rows)
  },
  salaryEmployees: async () => demoApi.masterRows('employee', [{ id: '1', code: 'E001', name: '张师傅', empType: 1, status: 1 }, { id: '2', code: 'E002', name: '李师傅', empType: 1, status: 1 }])
    .filter(item => Number(item.status) === 1).map(item => ({ id: String(item.id), name: `${item.code} · ${item.name}` })),
  salaries: async () => readSalaries(),
  generateSalary: async (empId: string, wagePeriod: string) => {
    const rows = readSalaries(); const employee = (await demoApi.salaryEmployees()).find(item => item.id === empId)
    if (!employee || !/^\d{4}-(0[1-9]|1[0-2])$/.test(wagePeriod)) throw new Error('员工或工资期无效')
    const existing = rows.find(item => item.empId === empId && item.wagePeriod === wagePeriod)
    if (existing?.status === 1) throw new Error('工资单已确认')
    const id = existing?.id || String(Math.max(...rows.map(item => Number(item.id)), 0) + 1)
    const item = { id, empId, employeeName: employee.name, wagePeriod, baseSalary: 2200, pieceAmount: 520, grossPay: 2720, netPay: 2720, status: 0 }
    if (existing) Object.assign(existing, item); else rows.push(item)
    saveSalaries(rows); return { id }
  },
  confirmSalary: async (id: string) => {
    const rows = readSalaries(); const item = rows.find(value => value.id === id)
    if (!item || item.status !== 0) throw new Error('工资单状态不允许确认')
    item.status = 1; saveSalaries(rows)
  },
  workOrders: async () => readWorkOrders(),
  shipments: async () => readShipments(),
  returns: async () => readReturns(),
  shipment: async (id: string) => {
    const found = readShipments().find(item => item.shipmentId === id)
    if (!found) throw new Error('出货单不存在')
    return found
  },
  createShipment: async (orderId: string, logisticsCompany: string, logisticsNo: string, items: StockLine[]) => {
    const order = readOrders().find(item => item.orderId === orderId)
    if (!order || ![2, 3].includes(order.status) || !items.length) throw new Error('订单状态或出货明细无效')
    for (const item of items) {
      const line = order.items?.find(value => value.skuId === item.skuId)
      if (!line || item.qty <= 0 || item.qty > line.remainingQty || !item.batchNo.trim()) throw new Error('出货数量或批次不合法')
    }
    const rows = readShipments(); const shipmentId = String(Math.max(...rows.map(item => Number(item.shipmentId)), 0) + 1)
    const shipmentNo = `SH${new Date().getFullYear()}${shipmentId.padStart(6, '0')}`
    rows.push({ shipmentId, shipmentNo, orderId, customerName: order.customerName, shipDate: null, logisticsCompany, logisticsNo, status: 0, items: items.map((item, index) => ({ ...item, itemId: `${shipmentId}-${index}`, skuCode: order.items?.find(value => value.skuId === item.skuId)?.skuCode || '' })) })
    saveShipments(rows); return { shipmentId, shipmentNo }
  },
  ship: async (id: string) => {
    const rows = readShipments(); const shipment = rows.find(item => item.shipmentId === id)
    const orders = readOrders(); const order = orders.find(item => item.orderId === shipment?.orderId)
    if (!shipment || shipment.status !== 0 || !order) throw new Error('出货单状态不允许发货')
    for (const item of shipment.items) {
      const line = order.items?.find(value => value.skuId === item.skuId)
      if (!line || item.qty > line.remainingQty) throw new Error('出货超过未发数量')
      line.shippedQty += item.qty; line.remainingQty -= item.qty
    }
    shipment.status = 1; shipment.shipDate = dateOffset(0)
    if (order.items?.every(item => item.remainingQty === 0)) { order.status = 4; order.fullShipDate = dateOffset(0); order.fulfillmentState = '已发齐' }
    saveShipments(rows); saveOrders(orders)
  },
  sign: async (id: string) => {
    const rows = readShipments(); const shipment = rows.find(item => item.shipmentId === id)
    if (!shipment || shipment.status !== 1) throw new Error('出货单状态不允许签收')
    shipment.status = 2; saveShipments(rows)
    const orders = readOrders(); const order = orders.find(item => item.orderId === shipment.orderId)
    if (order?.status === 4 && rows.filter(item => item.orderId === shipment.orderId).every(item => item.status === 2)) { order.status = 5; saveOrders(orders) }
  },
  returnOrder: async (id: string) => {
    const found = readReturns().find(item => item.returnId === id)
    if (!found) throw new Error('退货单不存在')
    return found
  },
  createReturn: async (shipmentId: string, returnType: number, isRestock: number, reason: string, refundAmount: number, items: StockLine[]) => {
    const shipment = readShipments().find(item => item.shipmentId === shipmentId)
    if (!shipment || shipment.status === 0 || !items.length || refundAmount < 0 || (returnType === 2 && refundAmount !== 0) || (returnType === 3 && isRestock !== 0)) throw new Error('退货信息无效')
    for (const item of items) {
      const line = shipment.items.find(value => value.skuId === item.skuId && value.batchNo === item.batchNo)
      if (!line || item.qty <= 0 || item.qty > line.qty) throw new Error('退货超过原出货数量')
    }
    const rows = readReturns(); const returnId = String(Math.max(...rows.map(item => Number(item.returnId)), 0) + 1)
    const returnNo = `RT${new Date().getFullYear()}${returnId.padStart(6, '0')}`
    rows.push({ returnId, returnNo, shipmentId, orderId: shipment.orderId, reason, returnType, isRestock, refundAmount, returnDate: null, status: 0,
      items: items.map((item, index) => ({ ...item, itemId: `${returnId}-${index}`, skuCode: shipment.items.find(value => value.skuId === item.skuId)?.skuCode || '' })) })
    saveReturns(rows); return { returnId, returnNo }
  },
  handleReturn: async (id: string) => {
    const rows = readReturns(); const item = rows.find(value => value.returnId === id)
    if (!item || item.status !== 0) throw new Error('退货单状态不允许处理')
    item.status = 1; item.returnDate = dateOffset(0); saveReturns(rows)
  },
  workOrderOptions: async () => ({ workers: demoApi.masterRows('employee', [{ id: '1', code: 'E001', name: '张师傅', empType: 1, status: 1 }, { id: '2', code: 'E002', name: '李师傅', empType: 1, status: 1 }])
    .filter(item => Number(item.status) === 1 && Number(item.empType) === 1).map(item => ({ id: String(item.id), name: `${item.code} · ${item.name}` })) }),
  workOrder: async (id: string) => {
    const found = readWorkOrders().find(item => item.woId === id)
    if (!found) throw new Error('工单不存在')
    return found
  },
  createWorkOrder: async (orderId: string, styleId: string) => {
    const source = readOrders().find(item => item.orderId === orderId)
    if (!source || ![1, 2].includes(source.status)) throw new Error('订单未确认')
    const orders = readWorkOrders()
    if (orders.some(item => item.orderId === orderId && item.styleId === styleId)) throw new Error('该订单款式已有工单')
    const lines = (source.items || []).filter(item => item.styleId === styleId)
    if (!lines.length) throw new Error('订单中没有该款式')
    const woId = String(Math.max(...orders.map(item => Number(item.woId)), 0) + 1)
    const woNo = `WO${new Date().getFullYear()}${woId.padStart(6, '0')}`
    const qty = lines.reduce((sum, item) => sum + item.qty, 0)
    orders.push({ woId, woNo, orderId, styleId, styleName: lines[0].styleName, qty, status: 0, planEndDate: source.deliveryDate, actualStartDate: null, actualEndDate: null,
      skus: lines.map(item => ({ skuId: item.skuId, skuCode: item.skuCode, planQty: item.qty, completedQty: 0 })),
      materials: [{ id: `${woId}-1`, materialId: '1', materialCode: 'FAB-001', materialName: '速干针织面料', unit: '米', planQty: qty, issuedQty: 0 }],
      processes: [{ id: `${woId}-1`, processName: '裁剪', seq: 1, planQty: qty, doneQty: 0, badQty: 0, isOutsourced: 0, status: 0 }, { id: `${woId}-2`, processName: '缝制', seq: 2, planQty: qty, doneQty: 0, badQty: 0, isOutsourced: 0, status: 0 }] })
    saveWorkOrders(orders)
    return { woId, woNo }
  },
  issueWorkOrder: async (id: string) => {
    const orders = readWorkOrders(); const order = orders.find(item => item.woId === id)
    if (!order || order.status !== 0) throw new Error('工单状态不允许领料')
    order.materials.forEach(item => { item.issuedQty = item.planQty })
    order.status = 1; order.actualStartDate = dateOffset(0); saveWorkOrders(orders)
  },
  reportProcess: async (request: { workOrderProcessId: string; skuId: string; empId: string | null; doneQty: number; badQty: number }) => {
    const orders = readWorkOrders(); const order = orders.find(item => item.processes.some(process => process.id === request.workOrderProcessId))
    const process = order?.processes.find(item => item.id === request.workOrderProcessId)
    if (!order || order.status !== 1 || !process || !order.skus.some(item => item.skuId === request.skuId)) throw new Error('报工工单或 SKU 无效')
    if (request.doneQty < 0 || request.badQty < 0 || request.doneQty + request.badQty <= 0 || process.doneQty + request.doneQty > process.planQty) throw new Error('报工数量超出计划')
    if (!process.isOutsourced && !request.empId) throw new Error('请选择员工')
    const prior = order.processes.find(item => item.seq === process.seq - 1)
    if (prior && process.doneQty + request.doneQty > prior.doneQty) throw new Error('前一道工序合格数不足')
    process.doneQty += request.doneQty; process.badQty += request.badQty; process.status = process.doneQty === process.planQty ? 2 : 1
    saveWorkOrders(orders)
  },
  finishWorkOrder: async (id: string, items: { skuId: string; goodQty: number; batchNo: string }[]) => {
    const orders = readWorkOrders(); const order = orders.find(item => item.woId === id)
    if (!order || order.status !== 1) throw new Error('工单状态不允许完工')
    const last = order.processes.at(-1)
    if (!last || items.reduce((sum, item) => sum + item.goodQty, 0) > last.doneQty - order.skus.reduce((sum, item) => sum + item.completedQty, 0)) throw new Error('最终工序合格数不足')
    for (const item of items) {
      const sku = order.skus.find(value => value.skuId === item.skuId)
      if (!sku || item.goodQty <= 0 || !item.batchNo.trim() || sku.completedQty + item.goodQty > sku.planQty) throw new Error('完工明细无效')
      sku.completedQty += item.goodQty
    }
    if (order.skus.every(item => item.completedQty === item.planQty)) { order.status = 2; order.actualEndDate = dateOffset(0) }
    saveWorkOrders(orders)
  },
  purchaseOptions: async () => purchaseOptions,
  purchases: async (pageNum: number, keyword: string) => {
    const matched = readPurchases().filter(item => `${item.poNo}${item.supplierName}`.toLowerCase().includes(keyword.toLowerCase()))
    return { rows: matched.reverse().slice((pageNum - 1) * 10, pageNum * 10), total: matched.length }
  },
  purchase: async (id: string) => {
    const found = readPurchases().find(item => item.poId === id)
    if (!found) throw new Error('采购单不存在')
    return found
  },
  createPurchase: async (draft: PurchaseDraft) => {
    const purchases = readPurchases()
    const supplier = purchaseOptions.suppliers.find(item => item.id === draft.supplierId)
    if (!supplier) throw new Error('供应商不存在')
    const poId = String(Math.max(...purchases.map(item => Number(item.poId)), 0) + 1)
    const poNo = `PO${new Date().getFullYear()}${poId.padStart(6, '0')}`
    const items = draft.items.map((item, index) => {
      const material = purchaseOptions.materials.find(value => value.id === item.materialId)
      if (!material) throw new Error('物料不存在')
      return { itemId: `${poId}-${index}`, materialId: material.id, materialCode: material.code!, materialName: material.name, unit: material.unit!, qty: item.qty, unitPrice: item.unitPrice, amount: Math.round(item.qty * item.unitPrice * 100) / 100, receivedQty: 0, remainingQty: item.qty }
    })
    purchases.push({ poId, poNo, supplierId: supplier.id, supplierName: supplier.name, orderDate: dateOffset(0), expectDate: draft.expectDate, actualArrivalDate: null, status: 0, totalAmount: items.reduce((sum, item) => sum + item.amount, 0), lineCount: items.length, items })
    savePurchases(purchases)
    return { poId, poNo }
  },
  updatePurchase: async (id: string, draft: PurchaseDraft) => {
    const purchases = readPurchases()
    const purchase = purchases.find(item => item.poId === id)
    if (!purchase || purchase.status !== 0) throw new Error('仅草稿采购单可编辑')
    const supplier = purchaseOptions.suppliers.find(item => item.id === draft.supplierId)
    if (!supplier || !draft.items.length) throw new Error('供应商或采购明细无效')
    const items = draft.items.map((item, index) => {
      const material = purchaseOptions.materials.find(value => value.id === item.materialId)
      if (!material) throw new Error('物料不存在')
      return { itemId: `${id}-${index}`, materialId: material.id, materialCode: material.code!, materialName: material.name, unit: material.unit!, qty: item.qty, unitPrice: item.unitPrice, amount: Math.round(item.qty * item.unitPrice * 100) / 100, receivedQty: 0, remainingQty: item.qty }
    })
    purchase.supplierId = supplier.id
    purchase.supplierName = supplier.name
    purchase.expectDate = draft.expectDate
    purchase.items = items
    purchase.totalAmount = items.reduce((sum, item) => sum + item.amount, 0)
    purchase.lineCount = items.length
    savePurchases(purchases)
    return { poId: purchase.poId, poNo: purchase.poNo }
  },
  confirmPurchase: async (id: string) => {
    const purchases = readPurchases()
    const purchase = purchases.find(item => item.poId === id)
    if (!purchase || purchase.status !== 0) throw new Error('采购单状态不允许确认')
    purchase.status = 1
    savePurchases(purchases)
  },
  receivePurchase: async (receipt: PurchaseReceipt) => {
    const purchases = readPurchases()
    const purchase = purchases.find(item => item.poId === receipt.poId)
    if (!purchase || ![1, 2].includes(purchase.status)) throw new Error('采购单状态不允许收货')
    for (const line of receipt.items) {
      const item = purchase.items?.find(value => value.itemId === line.itemId)
      if (!item || line.qty <= 0 || line.qty > item.remainingQty || !line.batchNo.trim()) throw new Error('收货数量或批次不合法')
      item.receivedQty += line.qty
      item.remainingQty -= line.qty
    }
    purchase.status = purchase.items?.every(item => item.remainingQty === 0) ? 3 : 2
    purchase.actualArrivalDate = purchase.status === 3 ? receipt.actualArrivalDate : null
    savePurchases(purchases)
    return { poId: receipt.poId }
  },
  options: async () => options,
  orders: async (pageNum: number, keyword: string) => {
    const matched = readOrders().filter(order => `${order.orderNo}${order.customerName}`.toLowerCase().includes(keyword.toLowerCase()))
    return { rows: matched.reverse().slice((pageNum - 1) * 10, pageNum * 10), total: matched.length }
  },
  order: async (id: string) => {
    const order = readOrders().find(item => item.orderId === id)
    if (!order) throw new Error('演示订单不存在')
    return order
  },
  createOrder: async (draft: OrderDraft) => {
    const orders = readOrders()
    const customer = options.customers.find(item => item.id === draft.customerId)
    if (!customer || !draft.items.length) throw new Error('客户或订单明细无效')
    const items = draft.items.map((item, index) => {
      const sku = options.skus.find(candidate => candidate.styleId === item.styleId && candidate.colorId === item.colorId && candidate.sizeId === item.sizeId)
      if (!sku) throw new Error('SKU 不存在')
      return line(sku.id, item.qty, item.unitPrice, 0, `${Date.now()}-${index}`)
    })
    const orderId = String(Math.max(...orders.map(item => Number(item.orderId)), 0) + 1)
    const orderNo = `SO${new Date().getFullYear()}${orderId.padStart(6, '0')}`
    orders.push({ orderId, orderNo, customerId: customer.id, customerName: customer.name, orderDate: dateOffset(0), deliveryDate: draft.deliveryDate, status: 0, totalAmount: items.reduce((sum, item) => sum + item.amount, 0), fullShipDate: null, fulfillmentState: '未发齐', lineCount: items.length, orderQty: items.reduce((sum, item) => sum + item.qty, 0), items })
    saveOrders(orders)
    return { orderId, orderNo }
  },
  updateOrder: async (id: string, draft: OrderDraft) => {
    const orders = readOrders()
    const order = orders.find(item => item.orderId === id)
    if (!order || order.status !== 0) throw new Error('仅草稿订单可编辑')
    const customer = options.customers.find(item => item.id === draft.customerId)
    if (!customer || !draft.items.length) throw new Error('客户或订单明细无效')
    const items = draft.items.map((item, index) => {
      const sku = options.skus.find(candidate => candidate.styleId === item.styleId && candidate.colorId === item.colorId && candidate.sizeId === item.sizeId)
      if (!sku) throw new Error('SKU 不存在')
      return line(sku.id, item.qty, item.unitPrice, 0, `${Date.now()}-${index}`)
    })
    order.customerId = customer.id
    order.customerName = customer.name
    order.deliveryDate = draft.deliveryDate
    order.items = items
    order.totalAmount = items.reduce((sum, item) => sum + item.amount, 0)
    order.lineCount = items.length
    order.orderQty = items.reduce((sum, item) => sum + item.qty, 0)
    saveOrders(orders)
    return { orderId: order.orderId, orderNo: order.orderNo }
  },
  confirmOrder: async (id: string) => {
    const orders = readOrders()
    const order = orders.find(item => item.orderId === id)
    if (!order || order.status !== 0) throw new Error('订单状态不允许确认')
    order.status = 1
    saveOrders(orders)
  },
  mrp: async () => mrp,
  inventory: async () => readInventory(),
  overview: async (): Promise<Overview> => {
    const orders = readOrders()
    return {
      orderCount: orders.filter(order => order.status !== 9).length,
      overdueCount: orders.filter(order => order.status > 0 && order.status < 4 && order.deliveryDate < dateOffset(0)).length,
      shortageCount: mrp.filter(row => row.shortageFlag === 1).length,
      standardGrossProfit: 42860,
      ontimeRate: 100,
    }
  },
}

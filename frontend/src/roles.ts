export const roleKeys = ['admin', 'sales', 'planner', 'production', 'wh', 'finance', 'hr', 'boss'] as const
export type RoleKey = typeof roleKeys[number]

export interface RoleProfile {
  label: string
  heading: string
  focus: string
  navigation: string[]
}

export const roleProfiles: Record<RoleKey, RoleProfile> = {
  admin: { label: '管理员', heading: '运营工作台', focus: '全业务运行概览', navigation: ['/', '/orders', '/purchases', '/mrp', '/production/workOrders', '/inventory', '/shipping/shipments', '/cost/settlements', '/hr/employees', '/reports/ontime', '/ai', '/system/operlog'] },
  sales: { label: '销售', heading: '销售工作台', focus: '报价、订单与客户交付', navigation: ['/', '/orders', '/sales/quotations', '/master/customers', '/shipping/shipments', '/shipping/returns', '/reports/ontime'] },
  planner: { label: '计划员', heading: '计划采购工作台', focus: '缺料、采购与生产准备', navigation: ['/', '/mrp', '/purchases', '/master/materials', '/master/suppliers', '/production/workOrders', '/orders'] },
  production: { label: '生产主管', heading: '生产工作台', focus: '工单、领料与报工', navigation: ['/', '/production/workOrders', '/inventory', '/master/styles', '/master/processes', '/orders'] },
  wh: { label: '仓管员', heading: '仓储工作台', focus: '收货、库存与发运', navigation: ['/', '/inventory', '/inventory/transactions', '/purchases', '/production/workOrders', '/shipping/shipments', '/shipping/returns'] },
  finance: { label: '财务', heading: '财务工作台', focus: '标准成本与经营核算', navigation: ['/', '/cost/settlements', '/sales/quotations', '/reports/ontime', '/orders', '/shipping/shipments'] },
  hr: { label: '人事', heading: '人事工作台', focus: '员工、计件与工资', navigation: ['/', '/hr/employees', '/hr/wages', '/hr/salary'] },
  boss: { label: '厂长', heading: '经营工作台', focus: '经营指标与交付风险', navigation: ['/', '/reports/ontime', '/mrp', '/orders', '/production/workOrders', '/inventory', '/cost/settlements', '/ai'] },
}

const aliases: Record<string, RoleKey> = {
  supervisor: 'boss', salesAssistant: 'sales', buyer: 'planner', mc: 'production', dataVisitor: 'boss',
}

export function recognizedRoles(roles: string[]): RoleKey[] {
  return [...new Set(roles.map(role => roleKeys.includes(role as RoleKey) ? role as RoleKey : aliases[role]).filter((role): role is RoleKey => Boolean(role)))]
}

// Demo permissions mirror the role grants relevant to the current frontend.
const demoRules: Record<RoleKey, RegExp[]> = {
  admin: [/./],
  sales: [/^dashboard:(overview|alerts|ontime):/, /^sd:/, /^md:customer:/, /^sh:/],
  planner: [/^dashboard:(overview|alerts|mrp):/, /^pu:/, /^md:(style|sku|material|supplier):list$/, /^pr:workOrder:(list|query|add)$/, /^sd:order:(list|query)$/, /^sh:(shipment|return):list$/],
  production: [/^dashboard:(overview|alerts):/, /^pr:/, /^st:inventory:list$/, /^md:(style|sku|material|process):list$/, /^sd:order:(list|query)$/],
  wh: [/^dashboard:(overview|alerts):/, /^st:/, /^pu:purchaseOrder:(list|query|receive)$/, /^pr:workOrder:(list|query|issue|finish)$/, /^sh:/],
  finance: [/^dashboard:(overview|alerts|ontime|styleCost):/, /^fn:/, /^sd:order:(list|query)$/, /^sd:quotation:/, /^sh:(shipment|return):(list|query)$/],
  hr: [/^dashboard:(overview|alerts):/, /^hr:/],
  boss: [/^dashboard:/, /^(sd|pu|pr|st|sh|fn|md):.*:(list|query)$/],
}

export function demoCan(role: RoleKey, permission: string): boolean {
  return demoRules[role].some(rule => rule.test(permission))
}

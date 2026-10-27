import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useUserStore } from './stores/user'
import { catalogs } from './api/catalog'

// 详情页组件按 catalog.detailRoute 前缀分发；新增详情类型只需在此处登记一次
const detailComponents: Record<string, { name: string; component: RouteRecordRaw['component'] }> = {
  '/production/workOrders': { name: 'work-order-detail', component: () => import('./views/WorkOrderDetailView.vue') },
  '/shipping/shipments': { name: 'shipment-detail', component: () => import('./views/ShipmentDetailView.vue') },
  '/shipping/returns': { name: 'return-detail', component: () => import('./views/ShipmentDetailView.vue') },
  '/cost/settlements': { name: 'settlement-detail', component: () => import('./views/SettlementDetailView.vue') },
  '/sales/quotations': { name: 'quotation-detail', component: () => import('./views/QuotationDetailView.vue') },
}

const catalogDetailRoutes: RouteRecordRaw[] = Object.values(catalogs).flatMap((group) =>
  Object.values(group)
    .filter((spec) => spec.detailRoute && spec.detailPermission && detailComponents[spec.detailRoute])
    .map((spec) => {
      const detail = detailComponents[spec.detailRoute!]!
      return {
        path: `${spec.detailRoute}/:id`,
        name: detail.name,
        component: detail.component,
        meta: { permission: spec.detailPermission! },
      } as RouteRecordRaw
    }),
)

const router = createRouter({
  history: createWebHistory(),
  routes: [

    { path: '/login', name: 'login', component: () => import('./views/LoginView.vue'), meta: { public: true } },
    { path: '/', name: 'dashboard', component: () => import('./views/HomeView.vue'), meta: { permission: 'dashboard:overview:list' } },
    { path: '/todos', name: 'todos', component: () => import('./views/TodoView.vue'), meta: { permission: 'workflow:todo:list' } },
    { path: '/approvals', name: 'approvals', component: () => import('./views/ApprovalView.vue'), meta: { permission: 'workflow:approval:list' } },
    { path: '/finance', name: 'finance', alias: ['/finance/receivables', '/finance/payables', '/finance/payments'], component: () => import('./views/FinanceView.vue'), meta: { permission: 'fn:account:list' } },
    { path: '/orders', name: 'orders', component: () => import('./views/OrdersView.vue'), meta: { permission: 'sd:order:list' } },
    { path: '/orders/new', name: 'new-order', component: () => import('./views/OrderFormView.vue'), meta: { permission: 'sd:order:add' } },
    { path: '/orders/:id/edit', name: 'edit-order', component: () => import('./views/OrderFormView.vue'), meta: { permission: 'sd:order:edit' } },
    { path: '/orders/:id', name: 'order-detail', component: () => import('./views/OrderDetailView.vue'), meta: { permission: 'sd:order:query' } },
    { path: '/purchases', name: 'purchases', component: () => import('./views/PurchasesView.vue'), meta: { permission: 'pu:purchaseOrder:list' } },
    { path: '/purchases/new', name: 'new-purchase', component: () => import('./views/PurchaseFormView.vue'), meta: { permission: 'pu:purchaseOrder:add' } },
    { path: '/purchases/:id/edit', name: 'edit-purchase', component: () => import('./views/PurchaseFormView.vue'), meta: { permission: 'pu:purchaseOrder:edit' } },
    { path: '/purchases/:id', name: 'purchase-detail', component: () => import('./views/PurchaseDetailView.vue'), meta: { permission: 'pu:purchaseOrder:query' } },
    ...catalogDetailRoutes,
    { path: '/master/styles/:id', name: 'style-plan', component: () => import('./views/StylePlanView.vue'), meta: { permission: 'md:style:list' } },
    { path: '/mrp', name: 'mrp', component: () => import('./views/MrpView.vue'), meta: { permission: 'pu:suggestion:list' } },
    { path: '/inventory', name: 'inventory', component: () => import('./views/InventoryView.vue'), meta: { permission: 'st:inventory:list' } },
    { path: '/ai', name: 'ai-assistant', component: () => import('./views/AiAssistantView.vue'), meta: { permission: 'dashboard:overview:list' } },
    { path: '/system/operlog', name: 'oper-log', component: () => import('./views/OperLogView.vue'), meta: { permission: 'monitor:operlog:list' } },
    { path: '/system/users', name: 'sys-user', component: () => import('./views/system/UserView.vue'), meta: { permission: 'system:user:list' } },
    { path: '/system/roles', name: 'sys-role', component: () => import('./views/system/RoleView.vue'), meta: { permission: 'system:role:list' } },
    { path: '/system/menus', name: 'sys-menu', component: () => import('./views/system/MenuView.vue'), meta: { permission: 'system:menu:list' } },
    { path: '/system/org', name: 'sys-org', component: () => import('./views/system/OrgView.vue'), meta: { permission: 'system:dept:list' } },
    { path: '/:group(master|hr|sales|production|inventory|shipping|cost|reports)/:key', name: 'catalog', component: () => import('./views/CatalogView.vue') },
    { path: '/forbidden', name: 'forbidden', component: () => import('./views/ForbiddenView.vue') },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach(async (to) => {
  const user = useUserStore()
  if (to.meta.public) return user.token ? '/' : true
  if (!user.token) return { name: 'login', query: { redirect: to.fullPath } }
  if (!user.ready) {
    try { await user.load() } catch { user.logout(); return { name: 'login' } }
  }
  const catalog = to.name === 'catalog' ? catalogs[String(to.params.group)]?.[String(to.params.key)] : undefined
  if (to.name === 'catalog' && !catalog) return { name: 'dashboard' }
  const permission = catalog?.permission || to.meta.permission as string | undefined
  if (to.name === 'ai-assistant' && !user.canUseAi) return { name: 'forbidden' }
  if (permission && !user.can(permission)) return { name: 'forbidden' }
  return true
})

window.addEventListener('erp-unauthorized', () => {
  const user = useUserStore()
  user.logout()
  router.push({ name: 'login' })
})

export default router

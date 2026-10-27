import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from './stores/user'
import { catalogs } from './api/catalog'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/preview', name: 'preview', component: () => import('./views/LoginView.vue') },
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
    { path: '/production/workOrders/:id', name: 'work-order-detail', component: () => import('./views/WorkOrderDetailView.vue'), meta: { permission: 'pr:workOrder:query' } },
    { path: '/shipping/shipments/:id', name: 'shipment-detail', component: () => import('./views/ShipmentDetailView.vue'), meta: { permission: 'sh:shipment:query' } },
    { path: '/shipping/returns/:id', name: 'return-detail', component: () => import('./views/ShipmentDetailView.vue'), meta: { permission: 'sh:return:query' } },
    { path: '/cost/settlements/:id', name: 'settlement-detail', component: () => import('./views/SettlementDetailView.vue'), meta: { permission: 'fn:settlement:query' } },
    { path: '/sales/quotations/:id', name: 'quotation-detail', component: () => import('./views/QuotationDetailView.vue'), meta: { permission: 'sd:quotation:query' } },
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
  if (to.name === 'preview') {
    if (!import.meta.env.DEV) return { name: 'login' }
    user.enterDemo()
    return { name: 'dashboard' }
  }
  if (to.meta.public) return user.demo || user.token ? '/' : true
  if (user.demo && to.name === 'login') return { name: 'dashboard' }
  if (!user.demo && !user.token) return { name: 'login', query: { redirect: to.fullPath } }
  if (!user.demo && !user.ready) {
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
  if (user.demo) return
  user.logout()
  router.push({ name: 'login' })
})

export default router

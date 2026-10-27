<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter, RouterLink, RouterView } from 'vue-router'
import { Avatar, Bell, ChatDotRound, Coin, DataAnalysis, Document, DocumentChecked, Goods, Menu, OfficeBuilding, Operation, ShoppingCart, SwitchButton, Tickets, TrendCharts, User, Van, Wallet } from '@element-plus/icons-vue'
import { catalogs } from './api/catalog'
import { roleProfiles, type RoleKey } from './roles'
import { useUserStore } from './stores/user'

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const menuOpen = ref(false)
const isLogin = computed(() => route.name === 'login')
const links = [
  { path: '/', text: '工作台', icon: DataAnalysis, permission: 'dashboard:overview:list' },
  { path: '/todos', text: '待办中心', icon: Bell, permission: 'workflow:todo:list' },
  { path: '/approvals', text: '审批中心', icon: DocumentChecked, permission: 'workflow:approval:list' },
  { path: '/finance', text: '会计资金', icon: Wallet, permission: 'fn:account:list' },
  { path: '/orders', text: '销售订单', icon: Document, permission: 'sd:order:list' },
  { path: '/purchases', text: '采购订单', icon: ShoppingCart, permission: 'pu:purchaseOrder:list' },
  { path: '/mrp', text: '缺料建议', icon: TrendCharts, permission: 'pu:suggestion:list' },
  { path: '/inventory', text: '库存结存', icon: Goods, permission: 'st:inventory:list' },
  { path: '/master/styles', text: '主数据', icon: Operation, permission: 'md:style:list' },
  { path: '/master/customers', text: '客户档案', icon: User, permission: 'md:customer:list' },
  { path: '/master/suppliers', text: '供应商', icon: ShoppingCart, permission: 'md:supplier:list' },
  { path: '/master/materials', text: '物料档案', icon: Goods, permission: 'md:material:list' },
  { path: '/master/processes', text: '工序档案', icon: Operation, permission: 'md:process:list' },
  { path: '/hr/employees', text: '人事', icon: User, permission: 'hr:employee:list' },
  { path: '/hr/wages', text: '计件工资', icon: Coin, permission: 'hr:pieceWage:list' },
  { path: '/hr/salary', text: '工资单', icon: Document, permission: 'hr:salarySlip:list' },
  { path: '/sales/quotations', text: '销售报价', icon: Document, permission: 'sd:quotation:list' },
  { path: '/production/workOrders', text: '生产工单', icon: Operation, permission: 'pr:workOrder:list' },
  { path: '/inventory/transactions', text: '库存流水', icon: Goods, permission: 'st:txn:list' },
  { path: '/shipping/shipments', text: '出货退货', icon: Van, permission: 'sh:shipment:list' },
  { path: '/cost/settlements', text: '标准成本', icon: Coin, permission: 'fn:settlement:list' },
  { path: '/reports/ontime', text: '经营报表', icon: DataAnalysis, permission: 'dashboard:ontime:list' },
  { path: '/ai', text: '业务问答', icon: ChatDotRound, permission: 'dashboard:overview:list' },
  { path: '/system/operlog', text: '操作日志', icon: Tickets, permission: 'monitor:operlog:list' },
]

/** 系统管理分组：仅管理员可见，单独成区 */
const systemLinks = [
  { path: '/system/users', text: '用户管理', icon: User, permission: 'system:user:list' },
  { path: '/system/roles', text: '角色管理', icon: Avatar, permission: 'system:role:list' },
  { path: '/system/menus', text: '菜单管理', icon: Menu, permission: 'system:menu:list' },
  { path: '/system/org', text: '组织架构', icon: OfficeBuilding, permission: 'system:dept:list' },
]
const primaryLinks = computed(() => user.profile.navigation
  .map(path => links.find(link => link.path === path))
  .filter((link): link is typeof links[number] => Boolean(link && user.can(link.permission))))
const secondaryLinks = computed(() => links.filter(link => user.can(link.permission) && (link.path !== '/ai' || user.canUseAi) && !primaryLinks.value.some(primary => primary.path === link.path)))
const visibleLinks = computed(() => [...primaryLinks.value, ...secondaryLinks.value])
const visibleSystemLinks = computed(() => systemLinks.filter(link => user.can(link.permission)))
const currentTitle = computed(() => {
  if (route.name === 'new-order') return '新建订单'
  if (route.name === 'order-detail') return '订单详情'
  if (route.name === 'new-purchase') return '新建采购单'
  if (route.name === 'purchase-detail') return '采购详情'
  if (route.name === 'work-order-detail') return '工单详情'
  if (route.name === 'shipment-detail') return '出货详情'
  if (route.name === 'return-detail') return '退货详情'
  if (route.name === 'settlement-detail') return '成本结算'
  if (route.name === 'quotation-detail') return '报价详情'
  if (route.name === 'style-plan') return '款式工艺'
  if (route.name === 'catalog') return catalogs[String(route.params.group)]?.[String(route.params.key)]?.title || '业务数据'
  if (route.path.startsWith('/finance')) return '会计资金'
  return links.find(link => link.path === route.path)?.text || '工作台'
})

function signOut() {
  user.logout()
  menuOpen.value = false
  router.push('/login')
}
function switchRole(role: string) {
  user.switchRole(role as RoleKey)
  menuOpen.value = false
  router.push('/')
}
</script>

<template>
  <RouterView v-if="isLogin" />
  <div v-else class="app-shell">
    <aside class="sidebar">
      <RouterLink to="/" class="brand" aria-label="海岚 ERP 工作台">
        <span class="brand-mark">H</span><span class="brand-copy"><strong>海岚 ERP</strong><small>{{ user.profile.heading }}</small></span>
      </RouterLink>
      <div class="nav-caption">{{ user.profile.label }} · 业务导航</div>
      <nav class="side-nav" aria-label="主导航">
        <RouterLink v-for="link in primaryLinks" :key="link.path" :to="link.path" class="nav-link" :class="{ active: route.path === link.path || (link.path === '/orders' || link.path === '/purchases' || link.path === '/finance') && route.path.startsWith(link.path + '/') }">
          <el-icon :size="18"><component :is="link.icon" /></el-icon><span>{{ link.text }}</span>
        </RouterLink>
      </nav>
      <template v-if="secondaryLinks.length"><div class="nav-caption nav-caption-secondary">其他授权</div><nav class="side-nav" aria-label="其他授权导航">
        <RouterLink v-for="link in secondaryLinks" :key="link.path" :to="link.path" class="nav-link"><el-icon :size="18"><component :is="link.icon" /></el-icon><span>{{ link.text }}</span></RouterLink>
      </nav></template>
      <template v-if="visibleSystemLinks.length"><div class="nav-caption nav-caption-secondary">系统管理</div><nav class="side-nav" aria-label="系统管理导航">
        <RouterLink v-for="link in visibleSystemLinks" :key="link.path" :to="link.path" class="nav-link" :class="{ active: route.path === link.path }"><el-icon :size="18"><component :is="link.icon" /></el-icon><span>{{ link.text }}</span></RouterLink>
      </nav></template>
      <div class="sidebar-foot"><span class="status-dot"></span> {{ user.demo ? '演示预览' : '业务系统' }}</div>
    </aside>

    <div class="main-area">
      <header class="topbar">
        <div class="topbar-left">
          <el-button class="mobile-menu" circle :icon="Menu" aria-label="打开导航" @click="menuOpen = true" />
          <span class="topbar-title">{{ currentTitle }}</span>
        </div>
        <div class="topbar-actions">
          <el-tag v-if="user.demo" type="warning" effect="plain" size="small">演示数据</el-tag>
          <el-select v-if="user.availableRoles.length > 1" :model-value="user.activeRole" class="role-select" aria-label="切换工作台" size="small" @change="switchRole">
            <el-option v-for="role in user.availableRoles" :key="role" :label="roleProfiles[role].label" :value="role" />
          </el-select>
          <span class="session-name">{{ user.name }}</span>
          <el-button text :icon="SwitchButton" :aria-label="user.demo ? '退出预览' : '退出登录'" :title="user.demo ? '退出预览' : '退出登录'" @click="signOut" />
        </div>
      </header>
      <main class="workspace"><RouterView /></main>
    </div>

    <el-drawer v-model="menuOpen" direction="ltr" size="260px" :with-header="false" class="mobile-drawer">
      <RouterLink to="/" class="brand" @click="menuOpen = false"><span class="brand-mark">H</span><span class="brand-copy"><strong>海岚 ERP</strong><small>{{ user.profile.heading }}</small></span></RouterLink>
      <nav class="side-nav" aria-label="手机导航">
        <RouterLink v-for="link in visibleLinks" :key="link.path" :to="link.path" class="nav-link" @click="menuOpen = false">
          <el-icon :size="18"><component :is="link.icon" /></el-icon><span>{{ link.text }}</span>
        </RouterLink>
      </nav>
      <template v-if="visibleSystemLinks.length"><div class="nav-caption nav-caption-secondary">系统管理</div><nav class="side-nav" aria-label="手机系统管理导航">
        <RouterLink v-for="link in visibleSystemLinks" :key="link.path" :to="link.path" class="nav-link" @click="menuOpen = false">
          <el-icon :size="18"><component :is="link.icon" /></el-icon><span>{{ link.text }}</span>
        </RouterLink>
      </nav></template>
    </el-drawer>
  </div>
</template>

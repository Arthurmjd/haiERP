<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { RouterLink } from 'vue-router'
import { Refresh } from '@element-plus/icons-vue'
import { catalogPage, catalogs, type CatalogSpec } from '../api/catalog'
import { erpApi } from '../api/erp'
import { financeApi } from '../api/finance'
import { enumLabel, enumTagType, type EnumKey } from '../api/labels'
import { useUserStore } from '../stores/user'
import type { RoleKey } from '../roles'

interface Metric {
  label: string
  path: string
  permission: string
  columns: { prop: string; label: string; enum?: EnumKey }[]
  load: () => Promise<{ total: number; rows: object[] }>
}

const user = useUserStore()
const loading = ref(false)
const error = ref('')
const values = ref<Record<string, number>>({})
const recentRows = ref<object[]>([])
let requestId = 0

function catalogMetric(label: string, path: string, spec: CatalogSpec): Metric {
  return { label, path, permission: spec.permission, columns: spec.columns.slice(0, 4), load: () => catalogPage(spec, 1) }
}

const allMetrics: Record<Exclude<RoleKey, 'admin'>, Metric[]> = {
  sales: [
    { label: '销售订单', path: '/orders', permission: 'sd:order:list', columns: [{ prop: 'orderNo', label: '订单号' }, { prop: 'customerName', label: '客户' }, { prop: 'deliveryDate', label: '交期' }, { prop: 'totalAmount', label: '金额' }], load: async () => erpApi.orders(1, '') },
    catalogMetric('报价单', '/sales/quotations', catalogs.sales.quotations),
    catalogMetric('出货单', '/shipping/shipments', catalogs.shipping.shipments),
  ],
  planner: [
    { label: '缺料项目', path: '/mrp', permission: 'pu:suggestion:list', columns: [{ prop: 'materialCode', label: '物料编码' }, { prop: 'materialName', label: '物料' }, { prop: 'gapQty', label: '净缺口' }, { prop: 'suggestQty', label: '建议采购' }], load: async () => { const rows = (await erpApi.mrp()).filter(row => row.shortageFlag || row.gapQty > 0); return { total: rows.length, rows } } },
    { label: '采购订单', path: '/purchases', permission: 'pu:purchaseOrder:list', columns: [], load: async () => erpApi.purchases(1, '') },
    catalogMetric('物料档案', '/master/materials', catalogs.master.materials),
  ],
  production: [
    catalogMetric('生产工单', '/production/workOrders', catalogs.production.workOrders),
    { label: '库存批次', path: '/inventory', permission: 'st:inventory:list', columns: [], load: async () => { const rows = await erpApi.inventory(); return { total: rows.length, rows } } },
    catalogMetric('工序档案', '/master/processes', catalogs.master.processes),
  ],
  wh: [
    { label: '库存批次', path: '/inventory', permission: 'st:inventory:list', columns: [{ prop: 'itemCode', label: '物料/产品编码' }, { prop: 'itemName', label: '名称' }, { prop: 'batchNo', label: '批次' }, { prop: 'qty', label: '结存' }], load: async () => { const rows = await erpApi.inventory(); return { total: rows.length, rows } } },
    catalogMetric('待处理出货', '/shipping/shipments', catalogs.shipping.shipments),
    { label: '采购订单', path: '/purchases', permission: 'pu:purchaseOrder:list', columns: [], load: async () => erpApi.purchases(1, '') },
  ],
  finance: [
    catalogMetric('标准成本结算', '/cost/settlements', catalogs.cost.settlements),
    catalogMetric('报价单', '/sales/quotations', catalogs.sales.quotations),
    catalogMetric('交付记录', '/reports/ontime', catalogs.reports.ontime),
  ],
  hr: [
    catalogMetric('员工档案', '/hr/employees', catalogs.hr.employees),
    catalogMetric('计件明细', '/hr/wages', catalogs.hr.wages),
    catalogMetric('工资单', '/hr/salary', catalogs.hr.salary),
  ],
  // 厂长（经营工作台）：审批 / 待办 / 资金 / 交付，老板最关心的几张牌
  boss: [
    { label: '待办事项', path: '/todos', permission: 'workflow:todo:list', columns: [{ prop: 'title', label: '待办' }, { prop: 'todoType', label: '类型' }, { prop: 'dueDate', label: '截止日期' }], load: async () => { const rows = await financeApi.todos(); return { total: rows.length, rows } } },
    { label: '待审批', path: '/approvals', permission: 'workflow:approval:list', columns: [{ prop: 'docType', label: '单据类型' }, { prop: 'amount', label: '金额' }, { prop: 'applicant', label: '申请人' }], load: async () => { const rows = await financeApi.approvals(); return { total: rows.length, rows } } },
    { label: '资金余额（元）', path: '/finance', permission: 'fn:account:list', columns: [], load: async () => { const rows = await financeApi.accounts(); const total = rows.filter(row => Number(row.status) === 1).reduce((sum, row) => sum + Number(row.currentBalance || 0), 0); return { total: Math.round(total), rows: [] } } },
    { label: '应收余额（元）', path: '/finance/receivables', permission: 'fn:receivable:list', columns: [], load: async () => { const rows = await financeApi.receivables(); const total = rows.filter(row => Number(row.status) !== 9).reduce((sum, row) => sum + Number(row.balance || 0), 0); return { total: Math.round(total), rows: [] } } },
    { label: '销售订单', path: '/orders', permission: 'sd:order:list', columns: [{ prop: 'orderNo', label: '订单号' }, { prop: 'customerName', label: '客户' }, { prop: 'deliveryDate', label: '交期' }, { prop: 'totalAmount', label: '金额' }], load: async () => erpApi.orders(1, '') },
    { label: '生产工单', path: '/production/workOrders', permission: 'pr:workOrder:list', columns: [], load: async () => catalogPage(catalogs.production.workOrders, 1) },
  ],
}

const metrics = computed(() => (allMetrics[user.activeRole as keyof typeof allMetrics] || []).filter(metric => user.can(metric.permission)))
const firstMetric = computed(() => metrics.value[0])
const shortcuts = computed(() => user.profile.navigation.slice(1).filter(path => metrics.value.some(metric => metric.path === path)))

async function load() {
  const current = ++requestId
  loading.value = true
  error.value = ''
  values.value = {}
  recentRows.value = []
  const requestedMetrics = metrics.value
  const result = await Promise.allSettled(requestedMetrics.map(metric => metric.load()))
  if (current !== requestId) return
  result.forEach((item, index) => {
    if (item.status === 'fulfilled') {
      values.value[requestedMetrics[index].path] = item.value.total
      if (index === 0) recentRows.value = item.value.rows.slice(0, 5)
    }
  })
  if (result.every(item => item.status === 'rejected')) error.value = '工作台数据暂不可用'
  loading.value = false
}

watch(() => user.activeRole, load, { immediate: true })
</script>

<template>
  <div class="page-head"><div><h1>{{ user.profile.heading }}</h1><p>{{ user.profile.focus }}</p></div><el-button :icon="Refresh" :loading="loading" @click="load">刷新</el-button></div>
  <div v-if="error" class="error-state">{{ error }}</div>
  <div class="kpi-grid role-kpis">
    <RouterLink v-for="metric in metrics" :key="metric.path" :to="metric.path" class="kpi role-kpi">
      <div class="kpi-label">{{ metric.label }}</div><div class="kpi-value">{{ values[metric.path] ?? '—' }}</div>
      <span class="role-kpi-action">查看明细 →</span>
    </RouterLink>
  </div>
  <section class="role-section">
    <div class="line-toolbar"><h2>常用业务</h2></div>
    <div class="role-actions"><RouterLink v-for="path in shortcuts" :key="path" :to="path"><el-button plain>{{ metrics.find(metric => metric.path === path)?.label }}</el-button></RouterLink></div>
  </section>
  <section v-if="firstMetric" class="role-section">
    <div class="line-toolbar"><h2>最近{{ firstMetric.label }}</h2><RouterLink :to="firstMetric.path"><el-button text type="primary">查看全部</el-button></RouterLink></div>
    <div class="table-wrap"><el-table :data="recentRows" v-loading="loading" stripe empty-text="暂无记录">
      <el-table-column v-for="column in firstMetric.columns" :key="column.prop" :label="column.label" min-width="130" show-overflow-tooltip><template #default="scope"><el-tag v-if="column.enum" size="small" :type="enumTagType(column.enum, scope.row[column.prop])">{{ enumLabel(column.enum, scope.row[column.prop]) }}</el-tag><span v-else>{{ scope.row[column.prop] }}</span></template></el-table-column>
    </el-table></div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'
import { ElMessage } from 'element-plus'
import { erpApi, orderStatusLabel, type Alert, type Overview, type Order } from '../api/erp'
import { enumTagType } from '../api/labels'
import { useUserStore } from '../stores/user'

const user = useUserStore()
const overview = ref<Overview | null>(null)
const orders = ref<Order[]>([])
const alerts = ref<Alert[]>([])
const explanation = ref('')
const explainOpen = ref(false)
const explainLoading = ref(false)
const loading = ref(true)
const error = ref('')
const currency = (value: number | undefined) => `¥${Number(value || 0).toLocaleString('zh-CN', { maximumFractionDigits: 2 })}`
async function load() {
  loading.value = true; error.value = ''
  try {
    overview.value = await erpApi.overview()
    if (user.can('sd:order:list')) orders.value = (await erpApi.orders(1, '')).rows.slice(0, 5)
    if (user.can('dashboard:alerts:list')) alerts.value = await erpApi.alerts()
  } catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
async function explain(alert: Alert) {
  explainOpen.value = true; explanation.value = ''; explainLoading.value = true
  try { explanation.value = (await erpApi.explainAlert(alert.id)).explanation }
  catch (e) { explanation.value = (e as Error).message }
  finally { explainLoading.value = false }
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>{{ user.profile.heading }}</h1><p>{{ user.profile.focus }}</p></div><el-button @click="load">刷新</el-button></div>
  <div v-if="loading" class="loading-state">正在加载…</div>
  <div v-else-if="error" class="error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="overview">
    <div class="kpi-grid">
      <div class="kpi"><div class="kpi-label">有效订单</div><div class="kpi-value">{{ overview.orderCount ?? 0 }}</div></div>
      <div class="kpi"><div class="kpi-label">逾期订单</div><div class="kpi-value kpi-warning">{{ overview.overdueCount ?? 0 }}</div></div>
      <div class="kpi"><div class="kpi-label">缺料项目</div><div class="kpi-value kpi-warning">{{ overview.shortageCount ?? 0 }}</div></div>
      <div class="kpi"><div class="kpi-label">准时交付率</div><div class="kpi-value">{{ overview.ontimeRate == null ? '—' : `${overview.ontimeRate}%` }}</div></div>
      <div v-if="user.can('fn:settlement:list')" class="kpi"><div class="kpi-label">标准毛利</div><div class="kpi-value">{{ currency(overview.standardGrossProfit) }}</div></div>
    </div>
    <section v-if="user.can('sd:order:list')" class="panel"><div class="line-toolbar"><h2>最近订单</h2><RouterLink to="/orders"><el-button text type="primary">查看全部</el-button></RouterLink></div>
      <div class="table-wrap"><el-table :data="orders" stripe empty-text="暂无订单"><el-table-column prop="orderNo" label="订单号" min-width="150"/><el-table-column prop="customerName" label="客户" min-width="150"/><el-table-column prop="deliveryDate" label="交期" min-width="120"/><el-table-column label="金额" min-width="120"><template #default="scope">{{ currency(scope.row.totalAmount) }}</template></el-table-column><el-table-column label="状态" width="90"><template #default="scope"><el-tag :type="enumTagType('orderStatus', scope.row.status)" size="small">{{ orderStatusLabel(scope.row.status) }}</el-tag></template></el-table-column></el-table></div>
    </section>
    <section v-if="user.can('dashboard:alerts:list')" class="panel"><h2 class="panel-title">业务预警</h2><div class="table-wrap"><el-table :data="alerts" stripe empty-text="暂无预警"><el-table-column prop="title" label="类型" min-width="140"/><el-table-column prop="subject" label="对象" min-width="160"/><el-table-column label="触发值" min-width="110"><template #default="scope">{{ scope.row.triggerValue }} {{ scope.row.unit }}</template></el-table-column><el-table-column v-if="user.can('dashboard:alerts:explain')" label="分析" width="100"><template #default="scope"><el-button text type="primary" @click="explain(scope.row)">查看</el-button></template></el-table-column></el-table></div></section>
  </template>
  <el-dialog v-model="explainOpen" title="预警分析" width="min(500px, 94vw)"><div v-loading="explainLoading" class="alert-explanation">{{ explanation }}</div></el-dialog>
</template>

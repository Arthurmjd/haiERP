<script setup lang="ts">
import { ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { erpApi, type Quotation } from '../api/erp'
import { useUserStore } from '../stores/user'
const route = useRoute()
const router = useRouter()
const user = useUserStore()
const quotation = ref<Quotation | null>(null)
const loading = ref(true)
const saving = ref(false)
const error = ref('')
async function transition(action: 'accept' | 'expire') {
  if (!quotation.value || quotation.value.status !== 0) return
  const label = action === 'accept' ? '接受' : '失效'
  try { await ElMessageBox.confirm(`确认将该报价标记为已${label}？`, `${label}报价`, { type: 'warning' }) }
  catch { return }
  saving.value = true
  try { await erpApi.transitionQuotation(quotation.value.quoteId, action); ElMessage.success(`报价已${label}`); await load() }
  catch (e) { ElMessage.error((e as Error).message); await load() }
  finally { saving.value = false }
}
async function load() {
  loading.value = true; error.value = ''
  try { quotation.value = await erpApi.quotation(String(route.params.id)) }
  catch (e) { error.value = (e as Error).message }
  finally { loading.value = false }
}
watch(() => route.params.id, load, { immediate: true })
</script>
<template>
  <div class="page-head"><div><h1>{{ quotation?.quoteNo || '报价单' }}</h1><p>报价与成本参考</p></div><div class="head-actions"><el-button @click="router.push('/sales/quotations')">返回列表</el-button><el-button v-if="quotation?.status === 0 && user.can('sd:quotation:expire')" :loading="saving" @click="transition('expire')">标记失效</el-button><el-button v-if="quotation?.status === 0 && user.can('sd:quotation:accept')" type="primary" :loading="saving" @click="transition('accept')">接受报价</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="quotation"><section class="panel"><h2 class="panel-title">报价信息</h2><div class="detail-grid"><div><div class="detail-label">客户</div><div class="detail-value">{{ quotation.customerName }}</div></div><div><div class="detail-label">日期</div><div class="detail-value">{{ quotation.quoteDate }}</div></div><div><div class="detail-label">金额</div><div class="detail-value">¥{{ Number(quotation.totalAmount).toFixed(2) }}</div></div><div><div class="detail-label">状态</div><el-tag>{{ quotation.status === 1 ? '已接受' : quotation.status === 2 ? '已失效' : '待确认' }}</el-tag></div></div></section><section class="panel"><h2 class="panel-title">报价明细</h2><div class="table-wrap"><el-table :data="quotation.items" stripe><el-table-column prop="styleName" label="款式" min-width="170"/><el-table-column prop="qty" label="数量" min-width="90"/><el-table-column prop="unitPrice" label="报价单价" min-width="110"/><el-table-column prop="costRef" label="参考成本" min-width="110"/><el-table-column prop="amount" label="金额" min-width="120"/><el-table-column prop="sourceSettleId" label="来源结算 ID" min-width="130"/></el-table></div></section></template>
</template>

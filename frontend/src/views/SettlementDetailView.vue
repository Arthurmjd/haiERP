<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { erpApi, type Settlement } from '../api/erp'
import { enumLabel, enumTagType } from '../api/labels'
import { useUserStore } from '../stores/user'
const route = useRoute()
const router = useRouter()
const user = useUserStore()
const settlement = ref<Settlement | null>(null)
const loading = ref(true)
const saving = ref(false)
const error = ref('')
async function load() {
  loading.value = true; error.value = ''
  try { settlement.value = await erpApi.settlement(String(route.params.id)) }
  catch (e) { error.value = (e as Error).message }
  finally { loading.value = false }
}
async function confirm() {
  if (!settlement.value) return
  try { await ElMessageBox.confirm(`确认 ${settlement.value.settleNo} 的标准成本？`, '确认结算', { type: 'warning' }) } catch { return }
  saving.value = true
  try { await erpApi.confirmCost(settlement.value.settleId); ElMessage.success('标准成本已确认'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
async function createQuotation() {
  if (!settlement.value) return
  saving.value = true
  try { const created = await erpApi.quotationFromSettlement([settlement.value.settleId], crypto.randomUUID()); router.push(`/sales/quotations/${created.quoteId}`) }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
watch(() => route.params.id, load, { immediate: true })
</script>
<template>
  <div class="page-head"><div><h1>{{ settlement?.settleNo || '成本结算' }}</h1><p>订单款式标准成本</p></div><div class="head-actions"><el-button @click="router.push('/cost/settlements')">返回列表</el-button><el-button v-if="settlement?.status === 0 && user.can('fn:settlement:confirm')" type="primary" :loading="saving" @click="confirm">确认结算</el-button><el-button v-if="settlement?.status === 1 && user.can('sd:quotation:fromSettlement')" type="primary" :loading="saving" @click="createQuotation">生成报价单</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="settlement"><section class="panel"><h2 class="panel-title">核算结果</h2><div class="detail-grid"><div><div class="detail-label">款式</div><div class="detail-value">{{ settlement.styleName }}</div></div><div><div class="detail-label">数量</div><div class="detail-value">{{ settlement.qty }}</div></div><div><div class="detail-label">状态</div><el-tag :type="enumTagType('settlementStatus', settlement.status)">{{ enumLabel('settlementStatus', settlement.status) }}</el-tag></div><div><div class="detail-label">单件成本</div><div class="detail-value">¥{{ Number(settlement.unitCost).toFixed(2) }}</div></div><div><div class="detail-label">材料成本</div><div class="detail-value">¥{{ Number(settlement.materialCost).toFixed(2) }}</div></div><div><div class="detail-label">人工成本</div><div class="detail-value">¥{{ Number(settlement.laborCost).toFixed(2) }}</div></div><div><div class="detail-label">制造费用</div><div class="detail-value">¥{{ Number(settlement.overheadCost).toFixed(2) }} ({{ settlement.overheadRate }}%)</div></div><div><div class="detail-label">总成本</div><div class="detail-value">¥{{ Number(settlement.totalCost).toFixed(2) }}</div></div><div><div class="detail-label">收入</div><div class="detail-value">¥{{ Number(settlement.revenue).toFixed(2) }}</div></div><div><div class="detail-label">毛利</div><div class="detail-value">¥{{ Number(settlement.grossProfit).toFixed(2) }}</div></div><div><div class="detail-label">毛利率</div><div class="detail-value">{{ settlement.grossMargin }}%</div></div></div></section><section class="panel"><h2 class="panel-title">成本明细</h2><div class="table-wrap"><el-table :data="settlement.details" stripe><el-table-column label="类型" min-width="110"><template #default="scope">{{ scope.row.lineType === 1 ? '材料' : scope.row.lineType === 2 ? '工序' : '制造费用' }}</template></el-table-column><el-table-column prop="sourceId" label="来源 ID" min-width="100"/><el-table-column prop="qty" label="数量" min-width="100"/><el-table-column prop="unitPrice" label="单价" min-width="100"/><el-table-column prop="amount" label="金额" min-width="120"/><el-table-column prop="priceSource" label="取价来源" min-width="180"/></el-table></div></section></template>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { erpApi, type AiAnswer } from '../api/erp'
import { useUserStore } from '../stores/user'

const user = useUserStore()
const modes = [
  { value: 'orders', label: '订单', permission: 'sd:order:list' },
  { value: 'inventory', label: '库存', permission: 'st:inventory:list' },
  { value: 'shortage', label: '缺料', permission: 'pu:suggestion:list' },
  { value: 'cost', label: '成本', permission: 'dashboard:styleCost:list' },
  { value: 'ontime', label: '交付', permission: 'dashboard:ontime:list' },
]
const visibleModes = computed(() => modes.filter(item => user.can(item.permission)))
const mode = ref(visibleModes.value[0]?.value || '')
const question = ref('')
const loading = ref(false)
const result = ref<AiAnswer | null>(null)
const labels: Record<string, string> = {
  orderNo: '订单号', deliveryDate: '交期', status: '状态', totalAmount: '金额',
  itemCode: '物料 / SKU', batchNo: '批次', qty: '数量', safeStock: '安全库存',
  materialCode: '物料编码', demandQty: '需求', stockQty: '现存', onWayQty: '在途', gapQty: '缺口',
  styleName: '款式', styleNo: '款号', totalQty: '数量', avgUnitCost: '单位成本',
  totalRevenue: '收入', totalGrossProfit: '毛利', grossMargin: '毛利率',
  fullShipDate: '发齐日期', deliveryState: '交付状态', delayDays: '偏差天数',
}
const columns = computed(() => result.value?.rows[0] ? Object.keys(result.value.rows[0]) : [])
async function ask() {
  if (!mode.value || !question.value.trim()) return ElMessage.warning('请填写问题并选择查询范围')
  loading.value = true; result.value = null
  try { result.value = await erpApi.askAi(mode.value, question.value.trim()) }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { loading.value = false }
}
</script>

<template>
  <div class="page-head"><div><h1>业务问答</h1></div></div>
  <section class="panel ai-workspace">
    <el-form label-position="top">
      <el-form-item label="查询范围"><el-radio-group v-model="mode" @change="result=null"><el-radio-button v-for="item in visibleModes" :key="item.value" :label="item.label" :value="item.value"/></el-radio-group></el-form-item>
      <el-form-item label="问题"><el-input v-model="question" type="textarea" :rows="3" :maxlength="500" show-word-limit @keydown.ctrl.enter="ask"/></el-form-item>
      <el-button type="primary" :loading="loading" :disabled="!mode || !question.trim()" @click="ask">查询分析</el-button>
    </el-form>
  </section>
  <section v-if="result" class="panel ai-result" aria-live="polite">
    <div class="line-toolbar"><h2>分析结果</h2><span class="detail-label">{{ result.dataTime.replace('T', ' ').slice(0, 19) }} · {{ result.modelUsed ? '模型分析' : '数据摘要' }}</span></div>
    <p class="ai-answer">{{ result.answer }}</p>
    <div class="table-wrap"><el-table :data="result.rows" stripe empty-text="暂无记录"><el-table-column v-for="key in columns" :key="key" :prop="key" :label="labels[key] || key" min-width="130"/></el-table></div>
  </section>
</template>

<style scoped>
.ai-workspace { max-width: 980px; }
.ai-workspace :deep(.el-radio-group) { display: flex; flex-wrap: wrap; gap: 4px; }
.ai-result { margin-top: 18px; }
.ai-answer { margin: 0 0 18px; line-height: 1.7; white-space: pre-wrap; }
</style>

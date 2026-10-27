<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { http } from '../api/client'
import { erpApi, type Option, type WorkOrder } from '../api/erp'
import { enumLabel, enumTagType } from '../api/labels'
import { useUserStore } from '../stores/user'

type MaterialRow = WorkOrder['materials'][number] & { returnedQty?: number | null }

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const order = ref<WorkOrder | null>(null)
const workers = ref<Option[]>([])
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const reportOpen = ref(false)
const finishOpen = ref(false)
const report = ref({ workOrderProcessId: '', skuId: '', empId: '', doneQty: 0, badQty: 0, reportDate: localDate(), requestKey: '' })
const finishLines = ref<{ skuId: string; skuCode: string; remaining: number; goodQty: number; batchNo: string }[]>([])
const finishKey = ref('')
const returnOpen = ref(false)
const returnKey = ref('')
const returnLines = ref<{ materialId: string; materialCode: string; materialName: string; unit: string; max: number; qty: number; batchNo: string }[]>([])
const materials = computed(() => (order.value?.materials || []) as MaterialRow[])
function localDate() { const d = new Date(); return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}` }
async function load() {
  loading.value = true; error.value = ''
  try { order.value = await erpApi.workOrder(String(route.params.id)) }
  catch (e) { error.value = (e as Error).message }
  finally { loading.value = false }
}
async function removeWorkOrder() {
  if (!order.value) return
  try { await ElMessageBox.confirm(`确定删除待开工工单 ${order.value.woNo}？SKU、用料、工序明细会一并删除。`, '删除工单', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' }) }
  catch { return }
  saving.value = true
  try { await erpApi.deleteWorkOrder(order.value.woId); ElMessage.success('工单已删除'); router.push('/production/workOrders') }
  catch (e) { ElMessage.error((e as Error).message); saving.value = false }
}
async function cancelWorkOrder() {
  if (!order.value) return
  try { await ElMessageBox.confirm(`确定作废工单 ${order.value.woNo}？`, '作废工单', { type: 'warning', confirmButtonText: '作废', cancelButtonText: '取消' }) }
  catch { return }
  saving.value = true
  try { await erpApi.cancelWorkOrder(order.value.woId); ElMessage.success('工单已作废'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
async function issue() {
  if (!order.value) return
  try { await ElMessageBox.confirm(`领用 ${order.value.woNo} 的冻结物料并开工？`, '领料开工', { type: 'warning' }) } catch { return }
  saving.value = true
  try { await erpApi.issueWorkOrder(order.value.woId, crypto.randomUUID()); ElMessage.success('领料完成'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
function demoBlocked() {
  return false
}
function openReturn() {
  if (demoBlocked()) return
  returnLines.value = materials.value
    .filter(item => Number(item.issuedQty) - Number(item.returnedQty ?? 0) > 0)
    .map(item => ({ materialId: item.materialId, materialCode: item.materialCode, materialName: item.materialName, unit: item.unit, max: Number(item.issuedQty) - Number(item.returnedQty ?? 0), qty: 0, batchNo: '' }))
  if (!returnLines.value.length) return ElMessage.warning('该工单没有可退物料')
  returnKey.value = crypto.randomUUID()
  returnOpen.value = true
}
async function saveReturn() {
  if (!order.value) return
  const items = returnLines.value.filter(item => item.qty > 0).map(item => ({ materialId: Number(item.materialId), batchNo: item.batchNo.trim(), qty: item.qty }))
  if (!items.length) return ElMessage.warning('请填写退料数量')
  if (returnLines.value.some(item => item.qty < 0 || item.qty > item.max || (item.qty > 0 && !item.batchNo.trim()))) return ElMessage.warning('填写有效退料批次和数量')
  saving.value = true
  try {
    await http.post('/erp/pr/workOrder/return', { woId: Number(order.value.woId), items, requestKey: returnKey.value })
    returnOpen.value = false; ElMessage.success('退料完成'); await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
async function openReport() {
  report.value = { workOrderProcessId: '', skuId: '', empId: '', doneQty: 0, badQty: 0, reportDate: localDate(), requestKey: crypto.randomUUID() }
  workers.value = []
  reportOpen.value = true
}
async function loadWorkers(workOrderProcessId: string) {
  report.value.empId = ''
  if (!workOrderProcessId) { workers.value = []; return }
  const proc = order.value?.processes.find(item => item.id === workOrderProcessId)
  if (proc?.isOutsourced) { workers.value = []; return }
  try {
    const result = await erpApi.workOrderOptions(workOrderProcessId)
    workers.value = result.workers
  } catch (e) { ElMessage.error((e as Error).message); workers.value = [] }
}
watch(() => report.value.workOrderProcessId, (id) => { if (id) loadWorkers(id) })
async function saveReport() {
  if (!order.value) return
  const process = order.value.processes.find(item => item.id === report.value.workOrderProcessId)
  if (!process || !report.value.skuId || report.value.doneQty < 0 || report.value.badQty < 0 || report.value.doneQty + report.value.badQty <= 0 || !report.value.reportDate || (!process.isOutsourced && !report.value.empId)) return ElMessage.warning('请填写有效报工明细')
  saving.value = true
  try {
    await erpApi.reportProcess({ ...report.value, empId: process.isOutsourced ? null : report.value.empId })
    reportOpen.value = false; ElMessage.success('报工已登记'); await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
function openFinish() {
  finishLines.value = (order.value?.skus || []).filter(item => item.planQty > item.completedQty).map(item => ({ skuId: item.skuId, skuCode: item.skuCode, remaining: item.planQty - item.completedQty, goodQty: 0, batchNo: '' }))
  finishKey.value = crypto.randomUUID(); finishOpen.value = true
}
async function saveFinish() {
  if (!order.value) return
  const items = finishLines.value.filter(item => item.goodQty > 0).map(item => ({ skuId: item.skuId, goodQty: item.goodQty, batchNo: item.batchNo.trim() }))
  if (!items.length || finishLines.value.some(item => item.goodQty < 0 || item.goodQty > item.remaining || (item.goodQty > 0 && !item.batchNo.trim()))) return ElMessage.warning('填写有效完工数量和批次')
  saving.value = true
  try { await erpApi.finishWorkOrder(order.value.woId, items, finishKey.value); finishOpen.value = false; ElMessage.success('成品已入库'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
onMounted(load)
</script>

<template>
  <div class="page-head"><div><h1>{{ order?.woNo || '工单详情' }}</h1><p>生产进度与领料入库</p></div><div class="head-actions"><el-button @click="router.push('/production/workOrders')">返回列表</el-button><el-button v-if="order && [0,1].includes(order.status) && user.can('pr:workOrder:issue')" type="primary" :loading="saving" @click="issue">{{ order.status === 0 ? '领料开工' : '补领料' }}</el-button><el-button v-if="order?.status === 1 && user.can('pr:workOrder:return')" :loading="saving" @click="openReturn">退料</el-button><el-button v-if="order?.status === 1 && user.can('pr:process:report')" @click="openReport">登记报工</el-button><el-button v-if="order?.status === 1 && user.can('pr:workOrder:finish')" type="primary" @click="openFinish">完工入库</el-button><el-button v-if="order?.status === 0 && user.can('pr:workOrder:remove')" type="danger" plain :loading="saving" @click="removeWorkOrder">删除</el-button><el-button v-if="order && [0,1].includes(order.status) && user.can('pr:workOrder:cancel')" type="danger" plain :loading="saving" @click="cancelWorkOrder">作废</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="order">
    <section class="panel"><h2 class="panel-title">工单信息</h2><div class="detail-grid"><div><div class="detail-label">款式</div><div class="detail-value">{{ order.styleName }}</div></div><div><div class="detail-label">计划数量</div><div class="detail-value">{{ order.qty }}</div></div><div><div class="detail-label">状态</div><el-tag :type="enumTagType('workOrderStatus', order.status)">{{ enumLabel('workOrderStatus', order.status) }}</el-tag></div><div><div class="detail-label">计划完工</div><div class="detail-value">{{ order.planEndDate || '—' }}</div></div><div><div class="detail-label">实际开工</div><div class="detail-value">{{ order.actualStartDate || '—' }}</div></div><div><div class="detail-label">实际完工</div><div class="detail-value">{{ order.actualEndDate || '—' }}</div></div></div></section>
    <section class="panel"><h2 class="panel-title">色码计划</h2><div class="table-wrap"><el-table :data="order.skus" stripe><el-table-column prop="skuCode" label="SKU" min-width="170"/><el-table-column prop="planQty" label="计划" min-width="90"/><el-table-column prop="completedQty" label="已入库" min-width="90"/><el-table-column label="待入库" min-width="90"><template #default="scope">{{ scope.row.planQty - scope.row.completedQty }}</template></el-table-column></el-table></div></section>
    <section class="panel"><h2 class="panel-title">冻结用料</h2><div class="table-wrap"><el-table :data="order.materials" stripe><el-table-column prop="materialCode" label="编码" min-width="140"/><el-table-column prop="materialName" label="物料" min-width="160"/><el-table-column prop="unit" label="单位" min-width="70"/><el-table-column prop="planQty" label="计划用量" min-width="100"/><el-table-column prop="issuedQty" label="已领" min-width="100"/><el-table-column label="已退" min-width="90"><template #default="scope">{{ scope.row.returnedQty ?? 0 }}</template></el-table-column></el-table></div></section>
    <section class="panel"><h2 class="panel-title">工序进度</h2><div class="table-wrap"><el-table :data="order.processes" stripe><el-table-column prop="seq" label="顺序" width="70"/><el-table-column prop="processName" label="工序" min-width="140"/><el-table-column prop="planQty" label="计划" min-width="90"/><el-table-column prop="doneQty" label="合格" min-width="90"/><el-table-column prop="badQty" label="不良" min-width="90"/><el-table-column label="方式" min-width="90"><template #default="scope">{{ scope.row.isOutsourced ? '外发' : '内部' }}</template></el-table-column></el-table></div></section>
  </template>
  <el-dialog v-model="reportOpen" title="登记报工" width="min(540px, 94vw)"><el-form label-position="top"><el-form-item label="工序"><el-select v-model="report.workOrderProcessId" style="width:100%"><el-option v-for="item in order?.processes" :key="item.id" :label="`${item.seq}. ${item.processName}`" :value="item.id"/></el-select></el-form-item><el-form-item label="SKU"><el-select v-model="report.skuId" style="width:100%"><el-option v-for="item in order?.skus" :key="item.skuId" :label="item.skuCode" :value="item.skuId"/></el-select></el-form-item><el-form-item v-if="!order?.processes.find(item => item.id === report.workOrderProcessId)?.isOutsourced" label="员工"><el-select v-model="report.empId" style="width:100%"><el-option v-for="item in workers" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><div class="order-form-grid"><el-form-item label="合格数"><el-input-number v-model="report.doneQty" :min="0" :precision="0"/></el-form-item><el-form-item label="不良数"><el-input-number v-model="report.badQty" :min="0" :precision="0"/></el-form-item></div><el-form-item label="报工日期"><el-date-picker v-model="report.reportDate" type="date" value-format="YYYY-MM-DD" :disabled-date="(date: Date) => date.getTime() > Date.now()"/></el-form-item></el-form><template #footer><el-button @click="reportOpen=false">取消</el-button><el-button type="primary" :loading="saving" @click="saveReport">保存报工</el-button></template></el-dialog>
  <el-dialog v-model="finishOpen" title="完工入库" width="min(660px, 94vw)"><div v-for="item in finishLines" :key="item.skuId" class="receipt-line"><span>{{ item.skuCode }} · 待入库 {{ item.remaining }}</span><el-input-number v-model="item.goodQty" :min="0" :max="item.remaining" :precision="0" aria-label="本次入库数量"/><el-input v-model="item.batchNo" placeholder="成品批次" aria-label="成品批次"/></div><template #footer><el-button @click="finishOpen=false">取消</el-button><el-button type="primary" :loading="saving" @click="saveFinish">确认入库</el-button></template></el-dialog>
  <el-dialog v-model="returnOpen" title="工单退料" width="min(760px, 96vw)"><p style="color:var(--el-text-color-secondary);font-size:13px">仅可退尚未退回的已领物料，退料后将回补库存。</p><div v-for="item in returnLines" :key="item.materialId" class="receipt-line"><span>{{ item.materialCode }} · {{ item.materialName }}（可退 {{ item.max }} {{ item.unit }}）</span><el-input-number v-model="item.qty" :min="0" :max="item.max" :precision="2" aria-label="退料数量"/><el-input v-model="item.batchNo" placeholder="批次号" aria-label="批次号"/></div><template #footer><el-button @click="returnOpen=false">取消</el-button><el-button type="primary" :loading="saving" @click="saveReturn">确认退料</el-button></template></el-dialog>
</template>

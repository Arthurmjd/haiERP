<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { erpApi, type ReturnOrder, type Shipment } from '../api/erp'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const isReturn = computed(() => route.name === 'return-detail')
const shipment = ref<Shipment | null>(null)
const returned = ref<ReturnOrder | null>(null)
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const returnOpen = ref(false)
const returnType = ref(1)
const reason = ref('')
const refundAmount = ref(0)
const returnLines = ref<{ skuId: string; skuCode: string; batchNo: string; remaining: number; qty: number }[]>([])
async function load() {
  loading.value = true; error.value = ''
  try {
    if (isReturn.value) { returned.value = await erpApi.returnOrder(String(route.params.id)); shipment.value = null }
    else { shipment.value = await erpApi.shipment(String(route.params.id)); returned.value = null }
  } catch (e) { error.value = (e as Error).message }
  finally { loading.value = false }
}
async function ship() {
  if (!shipment.value) return
  try { await ElMessageBox.confirm(`确认 ${shipment.value.shipmentNo} 出库？`, '确认发货', { type: 'warning' }) } catch { return }
  saving.value = true
  try { await erpApi.ship(shipment.value.shipmentId, crypto.randomUUID()); ElMessage.success('已发货并扣减库存'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
async function sign() {
  if (!shipment.value) return
  saving.value = true
  try { await erpApi.sign(shipment.value.shipmentId); ElMessage.success('签收已登记'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
function openReturn() {
  returnType.value = 1; reason.value = ''; refundAmount.value = 0
  returnLines.value = (shipment.value?.items || []).map(item => ({ skuId: item.skuId, skuCode: item.skuCode, batchNo: item.batchNo, remaining: item.qty, qty: 0 }))
  returnOpen.value = true
}
async function createReturn() {
  if (!shipment.value) return
  const items = returnLines.value.filter(item => item.qty > 0).map(item => ({ skuId: item.skuId, batchNo: item.batchNo, qty: item.qty }))
  if (!items.length || !reason.value.trim() || refundAmount.value < 0 || (returnType.value === 2 && refundAmount.value !== 0)) return ElMessage.warning('填写退货原因、数量和退款金额')
  saving.value = true
  try {
    const created = await erpApi.createReturn(shipment.value.shipmentId, returnType.value, returnType.value === 3 ? 0 : 1, reason.value.trim(), refundAmount.value, items)
    returnOpen.value = false; router.push(`/shipping/returns/${created.returnId}`)
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
async function handleReturn() {
  if (!returned.value) return
  try { await ElMessageBox.confirm(`处理 ${returned.value.returnNo}？`, '确认退货', { type: 'warning' }) } catch { return }
  saving.value = true
  try { await erpApi.handleReturn(returned.value.returnId, crypto.randomUUID()); ElMessage.success('退货已处理'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
watch(() => route.fullPath, load, { immediate: true })
</script>

<template>
  <div class="page-head"><div><h1>{{ isReturn ? returned?.returnNo || '退货详情' : shipment?.shipmentNo || '出货详情' }}</h1><p>{{ isReturn ? '退货处理与回库记录' : '出库与签收进度' }}</p></div><div class="head-actions"><el-button @click="router.push(isReturn ? '/shipping/returns' : '/shipping/shipments')">返回列表</el-button><template v-if="shipment"><el-button v-if="shipment.status === 0 && user.can('sh:shipment:ship')" type="primary" :loading="saving" @click="ship">确认发货</el-button><el-button v-if="shipment.status === 1 && user.can('sh:shipment:sign')" :loading="saving" @click="sign">登记签收</el-button><el-button v-if="shipment.status > 0 && user.can('sh:return:add')" @click="openReturn">登记退货</el-button></template><el-button v-if="returned?.status === 0 && user.can('sh:return:handle')" type="primary" :loading="saving" @click="handleReturn">处理退货</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="shipment"><section class="panel"><h2 class="panel-title">出货信息</h2><div class="detail-grid"><div><div class="detail-label">客户</div><div class="detail-value">{{ shipment.customerName }}</div></div><div><div class="detail-label">状态</div><el-tag :type="shipment.status === 2 ? 'success' : 'info'">{{ shipment.status === 2 ? '已签收' : shipment.status === 1 ? '已发货' : '待发货' }}</el-tag></div><div><div class="detail-label">出货日期</div><div class="detail-value">{{ shipment.shipDate || '—' }}</div></div><div><div class="detail-label">物流</div><div class="detail-value">{{ shipment.logisticsCompany || '—' }} {{ shipment.logisticsNo || '' }}</div></div></div></section><section class="panel"><h2 class="panel-title">出货明细</h2><div class="table-wrap"><el-table :data="shipment.items" stripe><el-table-column prop="skuCode" label="SKU" min-width="160"/><el-table-column prop="batchNo" label="批次" min-width="140"/><el-table-column prop="qty" label="数量" min-width="90"/></el-table></div></section></template>
  <template v-else-if="returned"><section class="panel"><h2 class="panel-title">退货信息</h2><div class="detail-grid"><div><div class="detail-label">类型</div><div class="detail-value">{{ returned.returnType === 1 ? '退货退款' : returned.returnType === 2 ? '换货' : '报废' }}</div></div><div><div class="detail-label">状态</div><el-tag :type="returned.status === 1 ? 'success' : 'info'">{{ returned.status === 1 ? '已处理' : '待处理' }}</el-tag></div><div><div class="detail-label">处理日期</div><div class="detail-value">{{ returned.returnDate || '—' }}</div></div><div><div class="detail-label">回库</div><div class="detail-value">{{ returned.isRestock ? '是' : '否' }}</div></div><div><div class="detail-label">退款金额</div><div class="detail-value">¥{{ Number(returned.refundAmount).toFixed(2) }}</div></div><div><div class="detail-label">原因</div><div class="detail-value">{{ returned.reason }}</div></div></div></section><section class="panel"><h2 class="panel-title">退货明细</h2><div class="table-wrap"><el-table :data="returned.items" stripe><el-table-column prop="skuCode" label="SKU" min-width="160"/><el-table-column prop="batchNo" label="原批次" min-width="140"/><el-table-column prop="qty" label="数量" min-width="90"/></el-table></div></section></template>
  <el-dialog v-model="returnOpen" title="登记退货" width="min(680px, 94vw)"><el-form label-position="top"><el-form-item label="退货类型"><el-radio-group v-model="returnType"><el-radio-button :value="1">退货退款</el-radio-button><el-radio-button :value="2">换货</el-radio-button><el-radio-button :value="3">报废</el-radio-button></el-radio-group></el-form-item><el-form-item label="原因"><el-input v-model="reason" maxlength="255"/></el-form-item><el-form-item label="退款金额"><el-input-number v-model="refundAmount" :min="0" :precision="2" :disabled="returnType === 2"/></el-form-item></el-form><div v-for="item in returnLines" :key="item.skuId + item.batchNo" class="receipt-line"><span>{{ item.skuCode }} · {{ item.batchNo }} · 原发 {{ item.remaining }}</span><el-input-number v-model="item.qty" :min="0" :max="item.remaining" :precision="0" aria-label="退货数量"/></div><template #footer><el-button @click="returnOpen=false">取消</el-button><el-button type="primary" :loading="saving" @click="createReturn">保存退货单</el-button></template></el-dialog>
</template>

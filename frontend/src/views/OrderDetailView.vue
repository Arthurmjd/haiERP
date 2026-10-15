<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { erpApi, orderStatusLabel, type Order } from '../api/erp'
import { useUserStore } from '../stores/user'
const route = useRoute()
const router = useRouter()
const user = useUserStore()
const order = ref<Order | null>(null)
const loading = ref(true)
const confirming = ref(false)
const creatingWorkOrder = ref(false)
const shipping = ref(false)
const logisticsCompany = ref('')
const logisticsNo = ref('')
const shipLines = ref<{ skuId: string; skuCode: string; remaining: number; qty: number; batchNo: string }[]>([])
const error = ref('')
const currency = (value: number) => `¥${Number(value || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })}`
async function load() {
  loading.value = true; error.value = ''
  try { order.value = await erpApi.order(String(route.params.id)) }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
async function confirm() {
  if (!order.value) return
  try { await ElMessageBox.confirm(`确认订单 ${order.value.orderNo}？确认后将进入后续业务流程。`, '确认订单', { type: 'warning', confirmButtonText: '确认', cancelButtonText: '取消' }) }
  catch { return }
  confirming.value = true
  try { await erpApi.confirmOrder(order.value.orderId); ElMessage.success('订单已确认'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { confirming.value = false }
}
async function createWorkOrder(styleId: string) {
  if (!order.value) return
  creatingWorkOrder.value = true
  try { const created = await erpApi.createWorkOrder(order.value.orderId, styleId); ElMessage.success('工单已创建'); router.push(`/production/workOrders/${created.woId}`) }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { creatingWorkOrder.value = false }
}
function openShipment() {
  shipLines.value = (order.value?.items || []).filter(item => item.remainingQty > 0).map(item => ({ skuId: item.skuId, skuCode: item.skuCode, remaining: item.remainingQty, qty: 0, batchNo: '' }))
  logisticsCompany.value = ''; logisticsNo.value = ''; shipping.value = true
}
async function createShipment() {
  if (!order.value) return
  const items = shipLines.value.filter(item => item.qty > 0).map(item => ({ skuId: item.skuId, qty: item.qty, batchNo: item.batchNo.trim() }))
  if (!items.length || shipLines.value.some(item => item.qty < 0 || item.qty > item.remaining || (item.qty > 0 && !item.batchNo.trim()))) return ElMessage.warning('填写有效出货数量和成品批次')
  creatingWorkOrder.value = true
  try { const created = await erpApi.createShipment(order.value.orderId, logisticsCompany.value.trim(), logisticsNo.value.trim(), items); shipping.value = false; router.push(`/shipping/shipments/${created.shipmentId}`) }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { creatingWorkOrder.value = false }
}
async function calculateCost(styleId: string) {
  if (!order.value) return
  creatingWorkOrder.value = true
  try { const result = await erpApi.calculateCost(order.value.orderId, styleId, crypto.randomUUID()); router.push(`/cost/settlements/${result.settleId}`) }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { creatingWorkOrder.value = false }
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>{{ order?.orderNo || '订单详情' }}</h1><p>销售订单与交付明细</p></div><div class="head-actions"><el-button @click="router.push('/orders')">返回列表</el-button><el-button v-if="order?.status === 0 && user.can('sd:order:confirm')" type="primary" :loading="confirming" @click="confirm">确认订单</el-button><el-button v-if="order && [2,3].includes(order.status) && user.can('sh:shipment:add')" type="primary" @click="openShipment">创建出货单</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="order"><section class="panel"><h2 class="panel-title">订单信息</h2><div class="detail-grid"><div><div class="detail-label">客户</div><div class="detail-value">{{ order.customerName }}</div></div><div><div class="detail-label">下单日期</div><div class="detail-value">{{ order.orderDate }}</div></div><div><div class="detail-label">交期</div><div class="detail-value">{{ order.deliveryDate }}</div></div><div><div class="detail-label">状态</div><el-tag :type="order.status === 0 ? 'info' : 'success'">{{ orderStatusLabel(order.status) }}</el-tag></div><div><div class="detail-label">订单金额</div><div class="detail-value">{{ currency(order.totalAmount) }}</div></div><div><div class="detail-label">实际完成发货</div><div class="detail-value">{{ order.fullShipDate || '—' }}</div></div></div></section>
    <section class="panel"><h2 class="panel-title">产品明细</h2><div class="table-wrap"><el-table :data="order.items || []" stripe><el-table-column prop="skuCode" label="SKU" min-width="160"/><el-table-column prop="styleName" label="产品" min-width="150"/><el-table-column prop="colorName" label="颜色" min-width="90"/><el-table-column prop="sizeName" label="尺码" min-width="80"/><el-table-column prop="qty" label="订购数量" width="100"/><el-table-column prop="shippedQty" label="已发数量" width="100"/><el-table-column prop="remainingQty" label="未发数量" width="100"/><el-table-column label="单价" width="110"><template #default="scope">{{ currency(scope.row.unitPrice) }}</template></el-table-column><el-table-column label="金额" width="130"><template #default="scope">{{ currency(scope.row.amount) }}</template></el-table-column></el-table></div><div class="form-actions"><el-button v-for="styleId in [...new Set((order.items || []).map(item => item.styleId))]" v-if="user.can('fn:settlement:calc')" :key="styleId" :loading="creatingWorkOrder" @click="calculateCost(styleId)">试算 {{ order.items?.find(item => item.styleId === styleId)?.styleName }} 成本</el-button><el-button v-for="styleId in [...new Set((order.items || []).map(item => item.styleId))]" v-if="[1,2].includes(order.status) && user.can('pr:workOrder:add')" :key="styleId" :loading="creatingWorkOrder" @click="createWorkOrder(styleId)">为 {{ order.items?.find(item => item.styleId === styleId)?.styleName }} 建工单</el-button></div></section>
  </template>
  <el-dialog v-model="shipping" title="创建出货单" width="min(690px, 94vw)"><el-form label-position="top" class="order-form-grid"><el-form-item label="物流公司"><el-input v-model="logisticsCompany" maxlength="50"/></el-form-item><el-form-item label="物流单号"><el-input v-model="logisticsNo" maxlength="50"/></el-form-item></el-form><div v-for="item in shipLines" :key="item.skuId" class="receipt-line"><span>{{ item.skuCode }} · 未发 {{ item.remaining }}</span><el-input-number v-model="item.qty" :min="0" :max="item.remaining" :precision="0" aria-label="本次出货数量"/><el-input v-model="item.batchNo" placeholder="成品批次" aria-label="成品批次"/></div><template #footer><el-button @click="shipping=false">取消</el-button><el-button type="primary" :loading="creatingWorkOrder" @click="createShipment">保存出货单</el-button></template></el-dialog>
</template>

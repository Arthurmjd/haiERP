<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { http, payload } from '../api/client'
import { erpApi, orderStatusLabel, type Order } from '../api/erp'
import { enumLabel, enumTagType } from '../api/labels'
import { useUserStore } from '../stores/user'
const route = useRoute()
const router = useRouter()
const user = useUserStore()
const order = ref<Order | null>(null)
const loading = ref(true)
const confirming = ref(false)
const creatingWorkOrder = ref(false)
const generating = ref(false)
const shipping = ref(false)
const logisticsCompany = ref('')
const logisticsNo = ref('')
const shipLines = ref<ShipLine[]>([])
interface BatchOption { batchNo: string; qty: number }
interface ShipLine { skuId: string; skuCode: string; remaining: number; qty: number; batchNo: string; batches: BatchOption[]; loading: boolean }
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
async function generateWorkOrders() {
  if (!order.value) return
  generating.value = true
  try {
    const created = await payload<{ woId: string; woNo: string }[]>(http.post(`/erp/pr/workOrder/generate/${encodeURIComponent(order.value.orderId)}`))
    if (created.length) ElMessage.success(`已生成 ${created.length} 张工单`)
    else ElMessage.info('该订单款式均已有工单，未新建')
    await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { generating.value = false }
}
async function removeOrder() {
  if (!order.value) return
  try { await ElMessageBox.confirm(`确定删除草稿订单 ${order.value.orderNo}？删除后不可恢复。`, '删除订单', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' }) }
  catch { return }
  confirming.value = true
  try { await erpApi.deleteOrder(order.value.orderId); ElMessage.success('订单已删除'); router.push('/orders') }
  catch (e) { ElMessage.error((e as Error).message); confirming.value = false }
}
async function cancelOrder() {
  if (!order.value) return
  try { await ElMessageBox.confirm(`确定作废订单 ${order.value.orderNo}？作废后单据保留但不再参与业务。`, '作废订单', { type: 'warning', confirmButtonText: '作废', cancelButtonText: '取消' }) }
  catch { return }
  confirming.value = true
  try { await erpApi.cancelOrder(order.value.orderId); ElMessage.success('订单已作废'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { confirming.value = false }
}
/** 出货数量上限：订单未发量与所选批次结存取小 */
function batchMax(line: ShipLine) {
  const batch = line.batches.find(item => item.batchNo === line.batchNo)
  return Math.min(line.remaining, batch ? batch.qty : 0)
}
async function openShipment() {
  const items = (order.value?.items || []).filter(item => item.remainingQty > 0)
  shipLines.value = items.map(item => ({
    skuId: item.skuId, skuCode: item.skuCode, remaining: item.remainingQty,
    qty: 0, batchNo: '', batches: [], loading: true,
  }))
  logisticsCompany.value = ''; logisticsNo.value = ''; shipping.value = true
  try {
    // 批次不再靠人手打：直接列出该 SKU 实际入过库的成品批次与可用数量
    const rows = await erpApi.productBatches(items.map(item => item.skuId))
    for (const line of shipLines.value) {
      line.batches = rows.filter(row => String(row.skuId) === String(line.skuId))
        .map(row => ({ batchNo: row.batchNo, qty: Number(row.qty) }))
        .sort((a, b) => b.qty - a.qty)
      const picked = line.batches[0]
      if (picked) { line.batchNo = picked.batchNo; line.qty = Math.min(line.remaining, picked.qty) }
    }
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { shipLines.value.forEach(line => { line.loading = false }) }
}
async function createShipment() {
  if (!order.value) return
  for (const line of shipLines.value) {
    if (line.qty <= 0) continue
    if (!line.batchNo) return ElMessage.warning(`${line.skuCode} 没有可用成品批次，请先在生产工单里完工入库`)
    const batch = line.batches.find(item => item.batchNo === line.batchNo)
    if (!batch) return ElMessage.warning(`${line.skuCode} 的批次 ${line.batchNo} 不存在`)
    if (line.qty > batch.qty) return ElMessage.warning(`${line.skuCode} 批次 ${batch.batchNo} 只剩 ${batch.qty}，不能出货 ${line.qty}`)
    if (line.qty > line.remaining) return ElMessage.warning(`${line.skuCode} 出货数量超过订单未发数量`)
  }
  const items = shipLines.value.filter(item => item.qty > 0).map(item => ({ skuId: item.skuId, qty: item.qty, batchNo: item.batchNo.trim() }))
  if (!items.length) return ElMessage.warning('填写有效出货数量和成品批次')
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
  <div class="page-head"><div><h1>{{ order?.orderNo || '订单详情' }}</h1><p>销售订单与交付明细</p></div><div class="head-actions"><el-button @click="router.push('/orders')">返回列表</el-button><el-button v-if="order?.status === 0 && user.can('sd:order:confirm')" type="primary" :loading="confirming" @click="confirm">确认订单</el-button><el-button v-if="order && [1,2].includes(order.status) && user.can('pr:workOrder:generate')" type="primary" :loading="generating" @click="generateWorkOrders">一键生成工单</el-button><el-button v-if="order && [2,3].includes(order.status) && user.can('sh:shipment:add')" type="primary" @click="openShipment">创建出货单</el-button><el-button v-if="order?.status === 0 && user.can('sd:order:remove')" type="danger" plain :loading="confirming" @click="removeOrder">删除</el-button><el-button v-if="order && order.status !== 9 && user.can('sd:order:cancel')" type="danger" plain :loading="confirming" @click="cancelOrder">作废</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="order"><section class="panel"><h2 class="panel-title">订单信息</h2><div class="detail-grid"><div><div class="detail-label">客户</div><div class="detail-value">{{ order.customerName }}</div></div><div><div class="detail-label">下单日期</div><div class="detail-value">{{ order.orderDate }}</div></div><div><div class="detail-label">交期</div><div class="detail-value">{{ order.deliveryDate }}</div></div><div><div class="detail-label">状态</div><el-tag :type="enumTagType('orderStatus', order.status)">{{ orderStatusLabel(order.status) }}</el-tag></div><div><div class="detail-label">订单金额</div><div class="detail-value">{{ currency(order.totalAmount) }}</div></div><div><div class="detail-label">实际完成发货</div><div class="detail-value">{{ order.fullShipDate || '—' }}</div></div></div></section>
    <section class="panel"><h2 class="panel-title">关联生产工单</h2><div v-if="!(order.workOrders || []).length" style="font-size:13px;color:var(--el-text-color-secondary)">{{ order.status >= 1 ? '这张订单还没有工单：点右上角「一键生成工单」补建（款式缺 BOM 或工序时会失败）。' : '订单确认后会自动按款式生成生产工单。' }}</div><div v-else class="table-wrap"><el-table :data="order.workOrders || []" stripe><el-table-column prop="woNo" label="工单号" min-width="170"/><el-table-column prop="styleName" label="款式" min-width="150"/><el-table-column prop="qty" label="数量" width="90"/><el-table-column prop="planEndDate" label="计划完工" width="120"/><el-table-column label="状态" width="110"><template #default="scope"><el-tag :type="enumTagType('workOrderStatus', scope.row.status)" size="small">{{ enumLabel('workOrderStatus', scope.row.status) }}</el-tag></template></el-table-column><el-table-column label="操作" width="100"><template #default="scope"><el-button text type="primary" @click="router.push(`/production/workOrders/${scope.row.woId}`)">查看</el-button></template></el-table-column></el-table></div></section>
    <section class="panel"><h2 class="panel-title">产品明细</h2><div class="table-wrap"><el-table :data="order.items || []" stripe><el-table-column prop="skuCode" label="SKU" min-width="160"/><el-table-column prop="styleName" label="产品" min-width="150"/><el-table-column prop="colorName" label="颜色" min-width="90"/><el-table-column prop="sizeName" label="尺码" min-width="80"/><el-table-column prop="qty" label="订购数量" width="100"/><el-table-column prop="shippedQty" label="已发数量" width="100"/><el-table-column prop="remainingQty" label="未发数量" width="100"/><el-table-column label="单价" width="110"><template #default="scope">{{ currency(scope.row.unitPrice) }}</template></el-table-column><el-table-column label="金额" width="130"><template #default="scope">{{ currency(scope.row.amount) }}</template></el-table-column></el-table></div><div class="form-actions"><template v-if="user.can('fn:settlement:calc')"><el-button v-for="styleId in [...new Set((order.items || []).map(item => item.styleId))]" :key="styleId" :loading="creatingWorkOrder" @click="calculateCost(styleId)">试算 {{ order.items?.find(item => item.styleId === styleId)?.styleName }} 成本</el-button></template><template v-if="[1,2].includes(order.status) && user.can('pr:workOrder:add')"><el-button v-for="styleId in [...new Set((order.items || []).map(item => item.styleId))]" :key="styleId" :loading="creatingWorkOrder" @click="createWorkOrder(styleId)">为 {{ order.items?.find(item => item.styleId === styleId)?.styleName }} 建工单</el-button></template></div></section>
  </template>
  <el-dialog v-model="shipping" title="创建出货单" width="min(760px, 96vw)"><el-form label-position="top" class="order-form-grid"><el-form-item label="物流公司"><el-input v-model="logisticsCompany" maxlength="50"/></el-form-item><el-form-item label="物流单号"><el-input v-model="logisticsNo" maxlength="50"/></el-form-item></el-form><div v-for="item in shipLines" :key="item.skuId" class="receipt-line"><span>{{ item.skuCode }} · 未发 {{ item.remaining }}</span><el-input-number v-model="item.qty" :min="0" :max="batchMax(item)" :precision="0" aria-label="本次出货数量"/><el-select v-model="item.batchNo" placeholder="选择成品批次" aria-label="成品批次" style="width:210px" :loading="item.loading" :disabled="!item.batches.length"><el-option v-for="batch in item.batches" :key="batch.batchNo" :label="`${batch.batchNo}（可用 ${batch.qty}）`" :value="batch.batchNo"/></el-select></div><p v-if="shipLines.some(item => !item.loading && !item.batches.length)" style="margin:10px 0 0;font-size:13px;color:var(--el-color-warning)">有 SKU 暂无成品库存：请先在生产工单里「完工入库」，入库时填的批次会出现在这里。</p><template #footer><el-button @click="shipping=false">取消</el-button><el-button type="primary" :loading="creatingWorkOrder" @click="createShipment">保存出货单</el-button></template></el-dialog>
</template>

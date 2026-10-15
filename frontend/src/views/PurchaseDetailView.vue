<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { erpApi, type PurchaseOrder } from '../api/erp'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const purchase = ref<PurchaseOrder | null>(null)
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const receiving = ref(false)
const arrivalDate = ref('')
const receiptLines = ref<{ itemId: string; qty: number; batchNo: string; remainingQty: number; materialName: string }[]>([])
async function load() { loading.value = true; error.value = ''; try { purchase.value = await erpApi.purchase(String(route.params.id)) } catch (e) { error.value = (e as Error).message } finally { loading.value = false } }
async function confirm() {
  if (!purchase.value) return
  try { await ElMessageBox.confirm(`确认采购单 ${purchase.value.poNo}？`, '确认采购', { type: 'warning' }) } catch { return }
  saving.value = true
  try { await erpApi.confirmPurchase(purchase.value.poId); ElMessage.success('采购单已确认'); await load() } catch (e) { ElMessage.error((e as Error).message) } finally { saving.value = false }
}
function openReceipt() {
  receiptLines.value = (purchase.value?.items || []).filter(item => Number(item.remainingQty) > 0).map(item => ({ itemId: item.itemId, qty: 0, batchNo: '', remainingQty: Number(item.remainingQty), materialName: item.materialName }))
  arrivalDate.value = new Date().toISOString().slice(0, 10)
  receiving.value = true
}
async function receive() {
  if (!purchase.value) return
  const items = receiptLines.value.filter(item => item.qty > 0).map(item => ({ itemId: item.itemId, qty: item.qty, batchNo: item.batchNo.trim() }))
  if (!items.length || receiptLines.value.some(item => item.qty < 0 || item.qty > item.remainingQty || (item.qty > 0 && !item.batchNo.trim()))) return ElMessage.warning('填写有效收货数量和批次')
  saving.value = true
  try { await erpApi.receivePurchase({ poId: purchase.value.poId, actualArrivalDate: arrivalDate.value, items, requestKey: crypto.randomUUID() }); receiving.value = false; ElMessage.success('收货已入账'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>{{ purchase?.poNo || '采购详情' }}</h1><p>采购明细与累计收货</p></div><div class="head-actions"><el-button @click="router.push('/purchases')">返回列表</el-button><el-button v-if="purchase?.status === 0 && user.can('pu:purchaseOrder:confirm')" type="primary" :loading="saving" @click="confirm">确认采购</el-button><el-button v-if="purchase && [1,2].includes(purchase.status) && user.can('pu:purchaseOrder:receive')" type="primary" @click="openReceipt">登记收货</el-button></div></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else-if="purchase"><section class="panel"><h2 class="panel-title">采购信息</h2><div class="detail-grid"><div><div class="detail-label">供应商</div><div class="detail-value">{{ purchase.supplierName }}</div></div><div><div class="detail-label">下单日期</div><div class="detail-value">{{ purchase.orderDate }}</div></div><div><div class="detail-label">预计到货</div><div class="detail-value">{{ purchase.expectDate }}</div></div><div><div class="detail-label">实际到齐</div><div class="detail-value">{{ purchase.actualArrivalDate || '—' }}</div></div><div><div class="detail-label">采购金额</div><div class="detail-value">¥{{ Number(purchase.totalAmount).toFixed(2) }}</div></div></div></section>
    <section class="panel"><h2 class="panel-title">物料明细</h2><div class="table-wrap"><el-table :data="purchase.items || []" stripe><el-table-column prop="materialCode" label="编码" min-width="140"/><el-table-column prop="materialName" label="物料" min-width="160"/><el-table-column prop="unit" label="单位" width="70"/><el-table-column prop="qty" label="采购量" width="105"/><el-table-column prop="receivedQty" label="已收" width="105"/><el-table-column prop="remainingQty" label="未收" width="105"/><el-table-column label="单价" width="100"><template #default="scope">¥{{ Number(scope.row.unitPrice).toFixed(2) }}</template></el-table-column><el-table-column label="金额" width="120"><template #default="scope">¥{{ Number(scope.row.amount).toFixed(2) }}</template></el-table-column></el-table></div></section>
  </template>
  <el-dialog v-model="receiving" title="登记收货" width="min(680px, 94vw)"><el-form-item label="到货日期"><el-date-picker v-model="arrivalDate" type="date" value-format="YYYY-MM-DD"/></el-form-item><div v-for="item in receiptLines" :key="item.itemId" class="receipt-line"><span>{{ item.materialName }} · 未收 {{ item.remainingQty }}</span><el-input-number v-model="item.qty" :min="0" :max="item.remainingQty" :precision="3" controls-position="right" aria-label="本次收货数量"/><el-input v-model="item.batchNo" placeholder="批次号" aria-label="批次号"/></div><template #footer><el-button @click="receiving=false">取消</el-button><el-button type="primary" :loading="saving" @click="receive">确认收货</el-button></template></el-dialog>
</template>

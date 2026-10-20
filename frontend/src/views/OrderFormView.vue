<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Delete, Plus } from '@element-plus/icons-vue'
import { erpApi, type OrderDraft, type OrderOptions } from '../api/erp'

type Line = { skuId: string; qty: number; unitPrice: number }
const route = useRoute()
const router = useRouter()
const editing = computed(() => Boolean(route.params.id))
const orderNo = ref('')
const options = ref<OrderOptions>({ customers: [], styles: [], colors: [], sizes: [], skus: [] })
const form = reactive<{ customerId: string; deliveryDate: string; lines: Line[] }>({ customerId: '', deliveryDate: '', lines: [{ skuId: '', qty: 1, unitPrice: 0 }] })
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const total = computed(() => form.lines.reduce((sum, line) => sum + Number(line.qty || 0) * Number(line.unitPrice || 0), 0))
const cancelPath = computed(() => editing.value ? `/orders/${route.params.id}` : '/orders')
const nameOf = (items: { id: string; name: string }[], id?: string) => items.find(item => item.id === id)?.name || '—'
function skuName(id: string) { const sku = options.value.skus.find(item => item.id === id); return sku ? `${sku.code} · ${nameOf(options.value.styles, sku.styleId)} / ${nameOf(options.value.colors, sku.colorId)} / ${nameOf(options.value.sizes, sku.sizeId)}` : '' }
function resetForm() {
  orderNo.value = ''
  form.customerId = ''
  form.deliveryDate = ''
  form.lines = [{ skuId: '', qty: 1, unitPrice: 0 }]
  error.value = ''
}
async function load() {
  loading.value = true
  error.value = ''
  resetForm()
  try {
    options.value = await erpApi.options()
    if (!editing.value) return
    const order = await erpApi.order(String(route.params.id))
    if (order.status !== 0) {
      ElMessage.warning('仅草稿订单可编辑')
      await router.replace(`/orders/${order.orderId}`)
      return
    }
    orderNo.value = order.orderNo
    form.customerId = order.customerId
    form.deliveryDate = order.deliveryDate
    const items = order.items || []
    form.lines = items.map(item => ({ skuId: item.skuId, qty: item.qty, unitPrice: item.unitPrice }))
    if (!form.lines.length) form.lines = [{ skuId: '', qty: 1, unitPrice: 0 }]
  }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
async function submit() {
  if (!form.customerId || !form.deliveryDate || !form.lines.length) return ElMessage.warning('请选择客户、交期和订单明细')
  const now = new Date()
  const today = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
  if (form.deliveryDate < today) return ElMessage.warning('交期不能早于今天')
  if (form.lines.some(line => !line.skuId || !Number.isInteger(line.qty) || line.qty <= 0 || line.unitPrice <= 0 || Math.abs(line.unitPrice * 100 - Math.round(line.unitPrice * 100)) > 1e-6)) return ElMessage.warning('请填写有效 SKU、数量和单价')
  if (new Set(form.lines.map(line => line.skuId)).size !== form.lines.length) return ElMessage.warning('同一 SKU 只能添加一次')
  saving.value = true
  try {
    const draft: OrderDraft = { customerId: form.customerId, deliveryDate: form.deliveryDate, items: form.lines.map(line => { const sku = options.value.skus.find(item => item.id === line.skuId); if (!sku) throw new Error('所选 SKU 不存在或已停用，请重新选择'); return { styleId: sku.styleId!, colorId: sku.colorId!, sizeId: sku.sizeId!, qty: line.qty, unitPrice: line.unitPrice } }) }
    if (editing.value) {
      await erpApi.updateOrder(String(route.params.id), draft)
      ElMessage.success(`订单 ${orderNo.value} 已更新`)
      await router.replace(`/orders/${route.params.id}`)
    } else {
      const result = await erpApi.createOrder(draft)
      ElMessage.success(`订单 ${result.orderNo} 已创建`)
      await router.replace(`/orders/${result.orderId}`)
    }
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
watch(() => route.params.id, load, { immediate: true })
</script>
<template>
  <div class="page-head"><div><h1>{{ editing ? '编辑销售订单' : '新建销售订单' }}</h1><p>{{ editing ? `单号 ${orderNo}` : '填写客户、交期及产品色码明细' }}</p></div><el-button @click="router.push(cancelPath)">{{ editing ? '返回详情' : '返回列表' }}</el-button></div>
  <div v-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else><section class="panel"><h2 class="panel-title">订单信息</h2><div class="order-form-grid"><el-form-item label="客户" required><el-select v-model="form.customerId" filterable placeholder="选择客户" :loading="loading" style="width:100%"><el-option v-for="item in options.customers" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="交期" required><el-date-picker v-model="form.deliveryDate" type="date" value-format="YYYY-MM-DD" placeholder="选择交期" style="width:100%"/></el-form-item></div></section>
    <section class="panel"><div class="line-toolbar"><h2>产品明细</h2><el-button :icon="Plus" :disabled="form.lines.length >= 100" @click="form.lines.push({ skuId: '', qty: 1, unitPrice: 0 })">添加一行</el-button></div>
      <div v-for="(line, index) in form.lines" :key="index" class="line-row"><el-select v-model="line.skuId" filterable placeholder="选择 SKU / 色码" aria-label="SKU"><el-option v-for="sku in options.skus" :key="sku.id" :label="skuName(sku.id)" :value="sku.id"/></el-select><el-input :model-value="skuName(line.skuId)" readonly placeholder="产品与色码" aria-label="产品与色码"/><el-input-number v-model="line.qty" :min="1" :max="999999" :precision="0" controls-position="right" aria-label="数量"/><el-input-number v-model="line.unitPrice" :min="0" :max="99999999" :precision="2" controls-position="right" aria-label="单价"/><div class="line-total">¥{{ (line.qty * line.unitPrice).toFixed(2) }}</div><el-button :icon="Delete" circle title="删除明细" aria-label="删除明细" :disabled="form.lines.length === 1" @click="form.lines.splice(index, 1)"/></div>
      <div class="line-total">订单合计　¥{{ total.toFixed(2) }}</div>
    </section><div class="form-actions"><el-button @click="router.push(cancelPath)">取消</el-button><el-button type="primary" :loading="saving" @click="submit">{{ editing ? '保存修改' : '保存草稿' }}</el-button></div>
  </template>
</template>

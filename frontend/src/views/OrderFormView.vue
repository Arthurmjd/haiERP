<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Delete, Plus } from '@element-plus/icons-vue'
import { erpApi, type OrderOptions } from '../api/erp'

type Line = { skuId: string; qty: number; unitPrice: number }
const router = useRouter()
const options = ref<OrderOptions>({ customers: [], styles: [], colors: [], sizes: [], skus: [] })
const form = reactive<{ customerId: string; deliveryDate: string; lines: Line[] }>({ customerId: '', deliveryDate: '', lines: [{ skuId: '', qty: 1, unitPrice: 0 }] })
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const total = computed(() => form.lines.reduce((sum, line) => sum + Number(line.qty || 0) * Number(line.unitPrice || 0), 0))
const nameOf = (items: { id: string; name: string }[], id?: string) => items.find(item => item.id === id)?.name || '—'
function skuName(id: string) { const sku = options.value.skus.find(item => item.id === id); return sku ? `${sku.code} · ${nameOf(options.value.styles, sku.styleId)} / ${nameOf(options.value.colors, sku.colorId)} / ${nameOf(options.value.sizes, sku.sizeId)}` : '' }
async function load() {
  loading.value = true
  try { options.value = await erpApi.options() }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
async function submit() {
  if (!form.customerId || !form.deliveryDate || !form.lines.length) return ElMessage.warning('请选择客户、交期和订单明细')
  const now = new Date()
  const today = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
  if (form.deliveryDate < today) return ElMessage.warning('交期不能早于今天')
  if (form.lines.some(line => !line.skuId || !Number.isInteger(line.qty) || line.qty <= 0 || line.unitPrice <= 0 || Math.round(line.unitPrice * 100) !== line.unitPrice * 100)) return ElMessage.warning('请填写有效 SKU、数量和单价')
  if (new Set(form.lines.map(line => line.skuId)).size !== form.lines.length) return ElMessage.warning('同一 SKU 只能添加一次')
  saving.value = true
  try {
    const items = form.lines.map(line => { const sku = options.value.skus.find(item => item.id === line.skuId)!; return { styleId: sku.styleId!, colorId: sku.colorId!, sizeId: sku.sizeId!, qty: line.qty, unitPrice: line.unitPrice } })
    const result = await erpApi.createOrder({ customerId: form.customerId, deliveryDate: form.deliveryDate, items })
    ElMessage.success(`订单 ${result.orderNo} 已创建`)
    await router.replace(`/orders/${result.orderId}`)
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>新建销售订单</h1><p>填写客户、交期及产品色码明细</p></div><el-button @click="router.push('/orders')">返回列表</el-button></div>
  <div v-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else><section class="panel"><h2 class="panel-title">订单信息</h2><div class="order-form-grid"><el-form-item label="客户" required><el-select v-model="form.customerId" filterable placeholder="选择客户" :loading="loading" style="width:100%"><el-option v-for="item in options.customers" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="交期" required><el-date-picker v-model="form.deliveryDate" type="date" value-format="YYYY-MM-DD" placeholder="选择交期" style="width:100%"/></el-form-item></div></section>
    <section class="panel"><div class="line-toolbar"><h2>产品明细</h2><el-button :icon="Plus" :disabled="form.lines.length >= 100" @click="form.lines.push({ skuId: '', qty: 1, unitPrice: 0 })">添加一行</el-button></div>
      <div v-for="(line, index) in form.lines" :key="index" class="line-row"><el-select v-model="line.skuId" filterable placeholder="选择 SKU / 色码" aria-label="SKU"><el-option v-for="sku in options.skus" :key="sku.id" :label="skuName(sku.id)" :value="sku.id"/></el-select><el-input :model-value="skuName(line.skuId)" readonly placeholder="产品与色码" aria-label="产品与色码"/><el-input-number v-model="line.qty" :min="1" :max="999999" :precision="0" controls-position="right" aria-label="数量"/><el-input-number v-model="line.unitPrice" :min="0" :max="99999999" :precision="2" controls-position="right" aria-label="单价"/><div class="line-total">¥{{ (line.qty * line.unitPrice).toFixed(2) }}</div><el-button :icon="Delete" circle title="删除明细" aria-label="删除明细" :disabled="form.lines.length === 1" @click="form.lines.splice(index, 1)"/></div>
      <div class="line-total">订单合计　¥{{ total.toFixed(2) }}</div>
    </section><div class="form-actions"><el-button @click="router.push('/orders')">取消</el-button><el-button type="primary" :loading="saving" @click="submit">保存草稿</el-button></div>
  </template>
</template>

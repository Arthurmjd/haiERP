<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Delete, Plus } from '@element-plus/icons-vue'
import { erpApi, type PurchaseDraft, type PurchaseOptions } from '../api/erp'

const route = useRoute()
const router = useRouter()
const editing = computed(() => Boolean(route.params.id))
const poNo = ref('')
const options = ref<PurchaseOptions>({ suppliers: [], materials: [] })
const form = reactive({ supplierId: '', expectDate: '', items: [{ materialId: '', qty: 1, unitPrice: 0 }] })
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const total = computed(() => form.items.reduce((sum, item) => sum + item.qty * item.unitPrice, 0))
const cancelPath = computed(() => editing.value ? `/purchases/${route.params.id}` : '/purchases')
function selectMaterial(id: string, index: number) { form.items[index].unitPrice = Number(options.value.materials.find(item => item.id === id)?.refPrice || 0) }
function resetForm() {
  poNo.value = ''
  form.supplierId = ''
  form.expectDate = ''
  form.items = [{ materialId: '', qty: 1, unitPrice: 0 }]
  error.value = ''
}
async function load() {
  loading.value = true; error.value = ''; resetForm()
  try {
    options.value = await erpApi.purchaseOptions()
    if (!editing.value) return
    const purchase = await erpApi.purchase(String(route.params.id))
    if (purchase.status !== 0) {
      ElMessage.warning('仅草稿采购单可编辑')
      await router.replace(`/purchases/${purchase.poId}`)
      return
    }
    poNo.value = purchase.poNo
    form.supplierId = purchase.supplierId
    form.expectDate = purchase.expectDate
    const items = purchase.items || []
    form.items = items.map(item => ({ materialId: item.materialId, qty: item.qty, unitPrice: item.unitPrice }))
    if (!form.items.length) form.items = [{ materialId: '', qty: 1, unitPrice: 0 }]
  } catch (e) { error.value = (e as Error).message } finally { loading.value = false }
}
async function submit() {
  const today = new Date().toISOString().slice(0, 10)
  if (!form.supplierId || !form.expectDate || form.expectDate < today) return ElMessage.warning('请选择供应商和有效到货日期')
  if (form.items.some(item => !item.materialId || !Number.isFinite(item.qty) || item.qty <= 0 || item.unitPrice <= 0 || Math.round(item.qty * 1000) !== item.qty * 1000)) return ElMessage.warning('物料、数量或单价不合法')
  if (new Set(form.items.map(item => item.materialId)).size !== form.items.length) return ElMessage.warning('物料不能重复')
  saving.value = true
  try {
    const draft: PurchaseDraft = { supplierId: form.supplierId, expectDate: form.expectDate, items: form.items }
    if (editing.value) {
      await erpApi.updatePurchase(String(route.params.id), draft)
      ElMessage.success(`采购单 ${poNo.value} 已更新`)
      await router.replace(`/purchases/${route.params.id}`)
    } else {
      const created = await erpApi.createPurchase(draft)
      ElMessage.success('采购草稿已保存')
      await router.replace(`/purchases/${created.poId}`)
    }
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
watch(() => route.params.id, load, { immediate: true })
</script>
<template>
  <div class="page-head"><div><h1>{{ editing ? '编辑采购单' : '新建采购单' }}</h1><p>{{ editing ? `单号 ${poNo}` : '选择供应商和采购物料' }}</p></div><el-button @click="router.push(cancelPath)">{{ editing ? '返回详情' : '返回列表' }}</el-button></div>
  <div v-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else><section class="panel"><h2 class="panel-title">采购信息</h2><div class="order-form-grid"><el-form-item label="供应商" required><el-select v-model="form.supplierId" filterable placeholder="选择供应商" :loading="loading" style="width:100%"><el-option v-for="item in options.suppliers" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="预计到货" required><el-date-picker v-model="form.expectDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" style="width:100%"/></el-form-item></div></section>
    <section class="panel"><div class="line-toolbar"><h2>物料明细</h2><el-button :icon="Plus" :disabled="form.items.length >= 100" @click="form.items.push({ materialId: '', qty: 1, unitPrice: 0 })">添加一行</el-button></div>
      <div v-for="(item, index) in form.items" :key="index" class="line-row"><el-select v-model="item.materialId" filterable placeholder="选择物料" @change="selectMaterial(item.materialId, index)"><el-option v-for="material in options.materials" :key="material.id" :label="`${material.code} · ${material.name}`" :value="material.id"/></el-select><el-input :model-value="options.materials.find(material => material.id === item.materialId)?.unit || ''" readonly placeholder="单位"/><el-input-number v-model="item.qty" :min="0.001" :precision="3" controls-position="right" aria-label="采购数量"/><el-input-number v-model="item.unitPrice" :min="0.01" :precision="2" controls-position="right" aria-label="单价"/><div class="line-total">¥{{ (item.qty * item.unitPrice).toFixed(2) }}</div><el-button :icon="Delete" circle title="删除明细" :disabled="form.items.length === 1" @click="form.items.splice(index,1)"/></div>
      <div class="line-total">采购合计　¥{{ total.toFixed(2) }}</div></section><div class="form-actions"><el-button @click="router.push(cancelPath)">取消</el-button><el-button type="primary" :loading="saving" @click="submit">{{ editing ? '保存修改' : '保存草稿' }}</el-button></div>
  </template>
</template>

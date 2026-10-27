<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { erpApi, type MrpRow, type PurchaseOption } from '../api/erp'
import { useUserStore } from '../stores/user'
const router = useRouter()
const user = useUserStore()
const rows = ref<MrpRow[]>([])
const loading = ref(true)
const error = ref('')
const onlyShortage = ref(true)
const keyword = ref('')
const selected = ref<MrpRow[]>([])
const generating = ref(false)
const saving = ref(false)
const suppliers = ref<PurchaseOption[]>([])
const supplierId = ref('')
const expectDate = ref('')
const filtered = computed(() => rows.value.filter(row => !onlyShortage.value || Number(row.shortageFlag) === 1))
async function load() { loading.value = true; error.value = ''; try { rows.value = await erpApi.mrp(keyword.value.trim()) } catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) } finally { loading.value = false } }
let debounceTimer: ReturnType<typeof setTimeout> | undefined
watch(keyword, () => { clearTimeout(debounceTimer); debounceTimer = setTimeout(load, 300) })
async function openGenerate() {
  if (!selected.value.length) return ElMessage.warning('选择缺料物料')
  try { suppliers.value = (await erpApi.purchaseOptions()).suppliers; generating.value = true }
  catch (e) { ElMessage.error((e as Error).message) }
}
async function generate() {
  if (!supplierId.value || !expectDate.value) return ElMessage.warning('请选择供应商和到货日期')
  saving.value = true
  try { const result = await erpApi.generatePurchaseFromMrp(supplierId.value, expectDate.value, selected.value.map(item => item.materialId), crypto.randomUUID()); generating.value = false; ElMessage.success('采购草稿已生成'); router.push(`/purchases/${result.poId}`) }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>缺料建议</h1><p>按当前需求、库存和在途量计算</p></div><div class="head-actions"><el-button v-if="user.can('pu:suggestion:generate')" type="primary" :disabled="!selected.length" @click="openGenerate">生成采购草稿</el-button><el-button @click="load">刷新</el-button></div></div>
  <section class="panel"><div class="filter-row"><el-input v-model="keyword" placeholder="物料编码或名称" clearable/><el-checkbox v-model="onlyShortage">只看缺料</el-checkbox><span class="muted">共 {{ filtered.length }} 项</span></div><div v-if="error" class="error-state">{{ error }}</div><div class="table-wrap"><el-table v-loading="loading" :data="filtered" stripe empty-text="暂无匹配物料" @selection-change="(rows: MrpRow[]) => selected = rows"><el-table-column v-if="user.can('pu:suggestion:generate')" type="selection" width="48" :selectable="(row: MrpRow) => Number(row.shortageFlag) === 1"/><el-table-column prop="materialCode" label="物料编码" min-width="145"/><el-table-column prop="materialName" label="物料名称" min-width="175"/><el-table-column prop="unit" label="单位" width="70"/><el-table-column prop="demandQty" label="需求" width="95"/><el-table-column prop="stockQty" label="库存" width="95"/><el-table-column prop="onWayQty" label="在途" width="95"/><el-table-column label="缺口" width="95"><template #default="scope"><span :class="{ 'kpi-warning': Number(scope.row.gapQty) > 0 }">{{ scope.row.gapQty }}</span></template></el-table-column><el-table-column prop="suggestQty" label="建议采购" width="100"/><el-table-column label="预估金额" min-width="110"><template #default="scope">¥{{ Number(scope.row.estAmount || 0).toFixed(2) }}</template></el-table-column></el-table></div></section>
  <el-dialog v-model="generating" title="生成采购草稿" width="min(480px, 94vw)"><el-form label-position="top"><el-form-item label="供应商"><el-select v-model="supplierId" style="width:100%"><el-option v-for="supplier in suppliers" :key="supplier.id" :label="supplier.name" :value="supplier.id"/></el-select></el-form-item><el-form-item label="预计到货"><el-date-picker v-model="expectDate" type="date" value-format="YYYY-MM-DD" style="width:100%"/></el-form-item></el-form><template #footer><el-button @click="generating=false">取消</el-button><el-button type="primary" :loading="saving" @click="generate">生成草稿</el-button></template></el-dialog>
</template>

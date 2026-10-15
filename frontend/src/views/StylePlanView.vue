<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { erpApi, type Option, type PurchaseOption, type StyleBomLine, type StyleProcessLine } from '../api/erp'
import { useUserStore } from '../stores/user'
const route = useRoute()
const router = useRouter()
const user = useUserStore()
const bom = ref<StyleBomLine[]>([])
const processes = ref<StyleProcessLine[]>([])
const materials = ref<PurchaseOption[]>([])
const processOptions = ref<Option[]>([])
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const editBom = ref(false)
const editProcesses = ref(false)
const bomDraft = ref<{ materialId: string; qty: number; lossRate: number }[]>([])
const processDraft = ref<{ processId: string; seq: number; unitPrice: number }[]>([])
async function load() {
  loading.value = true; error.value = ''
  try { const id = String(route.params.id); [bom.value, processes.value] = await Promise.all([erpApi.styleBom(id), erpApi.styleProcesses(id)]) }
  catch (e) { error.value = (e as Error).message }
  finally { loading.value = false }
}
async function options() {
  const result = await erpApi.stylePlanOptions()
  materials.value = result.materials; processOptions.value = result.processes
}
async function openBom() {
  try { await options(); bomDraft.value = bom.value.map(item => ({ materialId: item.materialId, qty: Number(item.qty), lossRate: Number(item.lossRate) })); editBom.value = true }
  catch (e) { ElMessage.error((e as Error).message) }
}
async function openProcesses() {
  try { await options(); processDraft.value = processes.value.map(item => ({ processId: item.processId, seq: Number(item.seq), unitPrice: Number(item.unitPrice) })); editProcesses.value = true }
  catch (e) { ElMessage.error((e as Error).message) }
}
async function saveBom() {
  if (!bomDraft.value.length || bomDraft.value.some(item => !item.materialId || item.qty <= 0 || item.lossRate < 0 || item.lossRate > 100) || new Set(bomDraft.value.map(item => item.materialId)).size !== bomDraft.value.length) return ElMessage.warning('检查物料、用量和重复项')
  saving.value = true
  try { await erpApi.saveStyleBom(String(route.params.id),bomDraft.value); editBom.value = false; ElMessage.success('BOM 已保存'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
async function saveProcesses() {
  if (!processDraft.value.length || processDraft.value.some(item => !item.processId || item.seq <= 0 || item.unitPrice < 0) || new Set(processDraft.value.map(item => item.processId)).size !== processDraft.value.length || new Set(processDraft.value.map(item => item.seq)).size !== processDraft.value.length) return ElMessage.warning('检查工序、顺序和重复项')
  saving.value = true
  try { await erpApi.saveStyleProcesses(String(route.params.id),processDraft.value); editProcesses.value = false; ElMessage.success('工艺路线已保存'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
watch(() => route.params.id, load, { immediate: true })
</script>
<template>
  <div class="page-head"><div><h1>款式工艺</h1></div><el-button @click="router.push('/master/styles')">返回款式</el-button></div>
  <div v-if="loading" class="loading-state">正在加载…</div><div v-else-if="error" class="panel error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
  <template v-else><section class="panel"><div class="line-toolbar"><h2>BOM 用料</h2><el-button v-if="user.can('md:style:edit')" @click="openBom">编辑 BOM</el-button></div><div class="table-wrap"><el-table :data="bom" stripe><el-table-column prop="materialCode" label="物料编码" min-width="130"/><el-table-column prop="materialName" label="物料" min-width="170"/><el-table-column prop="qty" label="单件用量" min-width="100"/><el-table-column prop="unit" label="单位" min-width="70"/><el-table-column prop="lossRate" label="损耗率 %" min-width="100"/></el-table></div></section><section class="panel"><div class="line-toolbar"><h2>工艺路线</h2><el-button v-if="user.can('md:style:edit')" @click="openProcesses">编辑工序</el-button></div><div class="table-wrap"><el-table :data="processes" stripe><el-table-column prop="seq" label="顺序" width="75"/><el-table-column prop="processName" label="工序" min-width="160"/><el-table-column prop="unitPrice" label="件单价" min-width="110"/></el-table></div></section></template>
  <el-dialog v-model="editBom" title="编辑 BOM" width="min(740px, 94vw)"><div v-for="(item,index) in bomDraft" :key="index" class="plan-line"><el-select v-model="item.materialId" placeholder="物料" aria-label="物料"><el-option v-for="option in materials" :key="option.id" :label="`${option.code} · ${option.name}`" :value="option.id"/></el-select><el-input-number v-model="item.qty" :min="0.001" :precision="3" aria-label="单件用量"/><el-input-number v-model="item.lossRate" :min="0" :max="100" :precision="2" aria-label="损耗率"/><el-button text type="danger" @click="bomDraft.splice(index,1)">移除</el-button></div><el-button @click="bomDraft.push({ materialId:'',qty:1,lossRate:0 })">添加物料</el-button><template #footer><el-button @click="editBom=false">取消</el-button><el-button type="primary" :loading="saving" @click="saveBom">保存 BOM</el-button></template></el-dialog>
  <el-dialog v-model="editProcesses" title="编辑工艺路线" width="min(720px, 94vw)"><div v-for="(item,index) in processDraft" :key="index" class="plan-line"><el-select v-model="item.processId" placeholder="工序" aria-label="工序"><el-option v-for="option in processOptions" :key="option.id" :label="option.name" :value="option.id"/></el-select><el-input-number v-model="item.seq" :min="1" :precision="0" aria-label="顺序"/><el-input-number v-model="item.unitPrice" :min="0" :precision="2" aria-label="件单价"/><el-button text type="danger" @click="processDraft.splice(index,1)">移除</el-button></div><el-button @click="processDraft.push({ processId:'',seq:processDraft.length+1,unitPrice:0 })">添加工序</el-button><template #footer><el-button @click="editProcesses=false">取消</el-button><el-button type="primary" :loading="saving" @click="saveProcesses">保存工艺路线</el-button></template></el-dialog>
</template>

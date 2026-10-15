<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { catalogPage, catalogs, masterForms, masterResource } from '../api/catalog'
import { erpApi, type Option, type OrderOptions } from '../api/erp'
import { isDemoMode } from '../api/demo'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const group = computed(() => String(route.params.group))
const key = computed(() => String(route.params.key))
const spec = computed(() => catalogs[group.value]?.[key.value])
const tabs = computed(() => Object.entries(catalogs[group.value] || {}).filter(([, item]) => user.can(item.permission)))
const rows = ref<Record<string, unknown>[]>([])
const total = ref(0)
const page = ref(1)
const asOfDate = ref(new Date().toLocaleDateString('sv-SE'))
const loading = ref(false)
const error = ref('')
const salaryOpen = ref(false)
const salarySaving = ref(false)
const salaryEmployees = ref<Option[]>([])
const salaryEmployeeId = ref('')
const salaryMonth = ref(new Date().toISOString().slice(0, 7))
const masterOpen = ref(false)
const masterSaving = ref(false)
const masterId = ref<string | null>(null)
const masterDraft = ref<Record<string, any>>({})
const departments = ref<Option[]>([])
const masterFields = computed(() => masterForms[key.value] || [])
const resource = computed(() => masterResource(key.value))
const editableCatalog = computed(() => group.value === 'master' && key.value !== 'skus' || group.value === 'hr' && key.value === 'employees')
const editPermission = computed(() => group.value === 'hr' ? 'hr:employee:edit' : `md:${resource.value}:edit`)
const addPermission = computed(() => group.value === 'hr' ? 'hr:employee:add' : `md:${resource.value}:add`)
const skuOpen = ref(false)
const skuOptions = ref<Pick<OrderOptions, 'styles' | 'colors' | 'sizes'> | null>(null)
const skuDraft = ref({ styleId: '', colorId: '', sizeId: '' })
function openRow(row: Record<string, unknown>) {
  if (group.value === 'production' && key.value === 'workOrders' && user.can('pr:workOrder:query')) router.push(`/production/workOrders/${row.id}`)
  if (group.value === 'shipping' && key.value === 'shipments' && user.can('sh:shipment:query')) router.push(`/shipping/shipments/${row.id}`)
  if (group.value === 'shipping' && key.value === 'returns' && user.can('sh:return:query')) router.push(`/shipping/returns/${row.id}`)
  if (group.value === 'cost' && key.value === 'settlements' && user.can('fn:settlement:query')) router.push(`/cost/settlements/${row.id}`)
  if (group.value === 'sales' && key.value === 'quotations' && user.can('sd:quotation:query')) router.push(`/sales/quotations/${row.id}`)
  if (editableCatalog.value && user.can(editPermission.value)) openMaster(row)
}
async function openMaster(row?: Record<string, unknown>) {
  masterId.value = row ? String(row.id) : null
  try {
    if (resource.value === 'employee') departments.value = await erpApi.employeeDepartments()
    const detail = row && !isDemoMode() ? await erpApi.masterRecord(resource.value, masterId.value!) : row || {}
    masterDraft.value = Object.fromEntries(masterFields.value.map(field => [field.key, detail[field.key] ?? field.defaultValue ?? (field.kind === 'number' ? 0 : '')]))
    if (resource.value === 'employee') masterDraft.value.deptId = String(masterDraft.value.deptId || '')
    masterOpen.value = true
  } catch (e) { ElMessage.error((e as Error).message) }
}
async function saveMaster() {
  if (masterFields.value.some(field => field.required && !String(masterDraft.value[field.key] ?? '').trim())) return ElMessage.warning('请填写必填字段')
  masterSaving.value = true
  try {
    if (masterId.value) await erpApi.updateMaster(resource.value, masterId.value, masterDraft.value)
    else await erpApi.createMaster(resource.value, masterDraft.value)
    masterOpen.value = false; ElMessage.success('档案已保存'); await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { masterSaving.value = false }
}
async function openSku() {
  try { skuOptions.value = await erpApi.skuOptions(); skuDraft.value = { styleId: '', colorId: '', sizeId: '' }; skuOpen.value = true }
  catch (e) { ElMessage.error((e as Error).message) }
}
async function generateSku() {
  if (!skuDraft.value.styleId || !skuDraft.value.colorId || !skuDraft.value.sizeId) return ElMessage.warning('请选择完整色码')
  masterSaving.value = true
  try { await erpApi.generateSku(skuDraft.value.styleId, skuDraft.value.colorId, skuDraft.value.sizeId); skuOpen.value = false; ElMessage.success('SKU 已生成'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { masterSaving.value = false }
}
async function openSalary() {
  try { salaryEmployees.value = await erpApi.salaryEmployees(); salaryOpen.value = true }
  catch (e) { ElMessage.error((e as Error).message) }
}
async function generateSalary() {
  if (!salaryEmployeeId.value || !salaryMonth.value) return ElMessage.warning('请选择员工和工资期')
  salarySaving.value = true
  try { await erpApi.generateSalary(salaryEmployeeId.value, salaryMonth.value); salaryOpen.value = false; ElMessage.success('工资单已生成'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { salarySaving.value = false }
}
async function confirmSalary(id: string) {
  salarySaving.value = true
  try { await erpApi.confirmSalary(id); ElMessage.success('工资单已确认'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { salarySaving.value = false }
}

async function load() {
  if (!spec.value) return
  loading.value = true; error.value = ''
  try { const result = await catalogPage(spec.value, page.value, group.value === 'reports' && key.value === 'ontime' ? asOfDate.value : undefined); rows.value = result.rows; total.value = result.total }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
watch([group, key], () => { page.value = 1; load() }, { immediate: true })
</script>
<template>
  <div v-if="spec"><div class="page-head"><div><h1>{{ spec.title }}</h1></div><div class="head-actions"><el-date-picker v-if="group === 'reports' && key === 'ontime'" v-model="asOfDate" type="date" value-format="YYYY-MM-DD" :disabled-date="(date: Date) => date.getTime() > Date.now()" @change="page=1; load()"/><el-button v-if="editableCatalog && user.can(addPermission)" type="primary" @click="openMaster()">新增{{ spec.title }}</el-button><el-button v-if="group === 'master' && key === 'skus' && user.can('md:sku:generate')" type="primary" @click="openSku">生成 SKU</el-button><el-button v-if="group === 'hr' && key === 'salary' && user.can('hr:salarySlip:generate')" type="primary" @click="openSalary">生成工资单</el-button><el-button @click="load">刷新</el-button></div></div>
    <el-tabs :model-value="key" class="catalog-tabs" @tab-change="(name: string | number) => router.push(`/${group}/${name}`)"><el-tab-pane v-for="[tabKey, item] in tabs" :key="tabKey" :label="item.title" :name="tabKey"/></el-tabs>
    <section class="panel"><div v-if="error" class="error-state">{{ error }}</div><div class="table-wrap"><el-table v-loading="loading" :data="rows" stripe empty-text="暂无数据" @row-click="openRow"><el-table-column v-for="column in spec.columns" :key="column.prop" :prop="column.prop" :label="column.label" :min-width="column.width || 120"/><el-table-column v-if="group === 'master' && key === 'styles'" label="工艺" width="100"><template #default="scope"><el-button text type="primary" @click.stop="router.push(`/master/styles/${scope.row.id}`)">BOM / 工序</el-button></template></el-table-column><el-table-column v-if="group === 'hr' && key === 'salary' && user.can('hr:salarySlip:confirm')" label="操作" width="100"><template #default="scope"><el-button v-if="scope.row.status === 0" text type="primary" :loading="salarySaving" @click.stop="confirmSalary(String(scope.row.id))">确认</el-button></template></el-table-column></el-table></div><div class="pagination"><el-pagination v-model:current-page="page" background layout="prev, pager, next, total" :total="total" :page-size="10" @current-change="load"/></div></section>
    <el-dialog v-model="salaryOpen" title="生成工资单" width="min(460px, 94vw)"><el-form label-position="top"><el-form-item label="员工"><el-select v-model="salaryEmployeeId" style="width:100%"><el-option v-for="item in salaryEmployees" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="工资期"><el-date-picker v-model="salaryMonth" type="month" value-format="YYYY-MM" style="width:100%"/></el-form-item></el-form><template #footer><el-button @click="salaryOpen=false">取消</el-button><el-button type="primary" :loading="salarySaving" @click="generateSalary">生成</el-button></template></el-dialog>
    <el-dialog v-model="masterOpen" :title="`${masterId ? '编辑' : '新增'}${spec.title}`" width="min(580px, 94vw)"><el-form label-position="top" class="order-form-grid"><el-form-item v-for="field in masterFields" :key="field.key" :label="field.label" :required="field.required"><el-select v-if="field.kind === 'status'" v-model="masterDraft[field.key]" style="width:100%"><el-option :label="resource === 'employee' ? '在职' : '启用'" :value="1"/><el-option :label="resource === 'employee' ? '离职' : '停用'" :value="0"/></el-select><el-select v-else-if="field.kind === 'gender'" v-model="masterDraft[field.key]" style="width:100%"><el-option label="女" :value="0"/><el-option label="男" :value="1"/><el-option v-if="resource !== 'employee'" label="中性" :value="2"/></el-select><el-select v-else-if="field.kind === 'employeeType'" v-model="masterDraft[field.key]" style="width:100%"><el-option label="生产计件" :value="1"/><el-option label="职能固定薪" :value="2"/></el-select><el-select v-else-if="field.kind === 'department'" v-model="masterDraft[field.key]" clearable style="width:100%"><el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id"/></el-select><el-select v-else-if="field.kind === 'category'" v-model="masterDraft[field.key]" style="width:100%"><el-option label="面料" :value="1"/><el-option label="辅料" :value="2"/></el-select><el-date-picker v-else-if="field.kind === 'date'" v-model="masterDraft[field.key]" type="date" value-format="YYYY-MM-DD" style="width:100%"/><el-input-number v-else-if="field.kind === 'number'" v-model="masterDraft[field.key]" :min="0" style="width:100%"/><el-input v-else v-model="masterDraft[field.key]"/></el-form-item></el-form><template #footer><el-button @click="masterOpen=false">取消</el-button><el-button type="primary" :loading="masterSaving" @click="saveMaster">保存</el-button></template></el-dialog>
    <el-dialog v-model="skuOpen" title="生成 SKU" width="min(500px, 94vw)"><el-form label-position="top"><el-form-item label="款式"><el-select v-model="skuDraft.styleId" style="width:100%"><el-option v-for="item in skuOptions?.styles" :key="item.id" :label="`${item.code} · ${item.name}`" :value="item.id"/></el-select></el-form-item><el-form-item label="颜色"><el-select v-model="skuDraft.colorId" style="width:100%"><el-option v-for="item in skuOptions?.colors" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="尺码"><el-select v-model="skuDraft.sizeId" style="width:100%"><el-option v-for="item in skuOptions?.sizes" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item></el-form><template #footer><el-button @click="skuOpen=false">取消</el-button><el-button type="primary" :loading="masterSaving" @click="generateSku">生成</el-button></template></el-dialog>
  </div>
</template>

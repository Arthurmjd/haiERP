<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { catalogPage, catalogs, masterForms, masterResource } from '../api/catalog'
import { http, payload } from '../api/client'
import { erpApi, type Option, type OrderOptions } from '../api/erp'
import { enumLabel, enumTagType } from '../api/labels'
import { isDemoMode } from '../api/demo'
import { useUserStore } from '../stores/user'

interface AccountOption { id: string; code?: string; name: string; status?: number }

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const group = computed(() => String(route.params.group))
const key = computed(() => String(route.params.key))
const spec = computed(() => catalogs[group.value]?.[key.value])
const tabs = computed(() => Object.entries(catalogs[group.value] || {}).filter(([, item]) => user.can(item.permission)))
const showTabs = computed(() => group.value !== 'hr' && tabs.value.length > 1)
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
const batchOpen = ref(false)
const batchSaving = ref(false)
const batchPeriod = ref(new Date().toISOString().slice(0, 7))
const payOpen = ref(false)
const paySaving = ref(false)
const payDate = ref(new Date().toLocaleDateString('sv-SE'))
const payTarget = ref<{ id: string; employeeName: string; netPay: number } | null>(null)
const payAccounts = ref<AccountOption[]>([])
const payAccountId = ref('')
const quoteOpen = ref(false)
const quoteSaving = ref(false)
const quoteOptions = ref<OrderOptions | null>(null)
const quoteForm = ref({ customerId: '', quoteDate: new Date().toLocaleDateString('sv-SE'), items: [{ styleId: '', qty: 1, unitPrice: 0, costRef: null as number | null | undefined }] })
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
// 删除覆盖主数据全部（含 SKU）与员工；编辑仍沿用 editableCatalog
const removableCatalog = computed(() => group.value === 'master' || group.value === 'hr' && key.value === 'employees')
const removePermission = computed(() => group.value === 'hr' ? 'hr:employee:remove' : `md:${resource.value}:remove`)
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
function demoBlocked() {
  if (isDemoMode()) { ElMessage.warning('演示模式不支持'); return true }
  return false
}
function openBatch() {
  if (demoBlocked()) return
  batchPeriod.value = new Date().toISOString().slice(0, 7)
  batchOpen.value = true
}
async function generateBatch() {
  if (!batchPeriod.value) return ElMessage.warning('请选择工资期')
  batchSaving.value = true
  try {
    const count = await payload<number>(http.post('/erp/hr/salarySlip/generateBatch', { wagePeriod: batchPeriod.value }))
    batchOpen.value = false; ElMessage.success(`已生成 ${count} 张工资单`); await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { batchSaving.value = false }
}
async function openPay(row: Record<string, unknown>) {
  if (demoBlocked()) return
  payTarget.value = { id: String(row.id), employeeName: String(row.employeeName || ''), netPay: Number(row.netPay || 0) }
  payDate.value = new Date().toLocaleDateString('sv-SE')
  try {
    const list = await payload<AccountOption[]>(http.get('/erp/finance/accounts'))
    payAccounts.value = list.filter(item => Number(item.status) === 1)
    payAccountId.value = payAccounts.value[0]?.id || ''
    payOpen.value = true
  } catch (e) { ElMessage.error((e as Error).message) }
}
async function paySalary() {
  if (!payTarget.value) return
  if (!payAccountId.value || !payDate.value) return ElMessage.warning('请选择资金账户和发放日期')
  paySaving.value = true
  try {
    await http.post(`/erp/hr/salarySlip/pay/${encodeURIComponent(payTarget.value.id)}`, {
      accountId: Number(payAccountId.value), payDate: payDate.value, requestKey: crypto.randomUUID(),
    })
    payOpen.value = false; ElMessage.success('工资已发放'); await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { paySaving.value = false }
}
async function openQuote() {
  if (demoBlocked()) return
  try {
    quoteOptions.value = await erpApi.options()
    quoteForm.value = { customerId: '', quoteDate: new Date().toLocaleDateString('sv-SE'), items: [{ styleId: '', qty: 1, unitPrice: 0, costRef: null }] }
    quoteOpen.value = true
  } catch (e) { ElMessage.error((e as Error).message) }
}
function addQuoteLine() {
  if (quoteForm.value.items.length >= 100) return
  quoteForm.value.items.push({ styleId: '', qty: 1, unitPrice: 0, costRef: null })
}
function removeQuoteLine(index: number) {
  if (quoteForm.value.items.length > 1) quoteForm.value.items.splice(index, 1)
}
async function createQuote() {
  if (!quoteForm.value.customerId || !quoteForm.value.quoteDate) return ElMessage.warning('请选择客户和报价日期')
  if (quoteForm.value.items.some(item => !item.styleId || !Number.isInteger(item.qty) || item.qty <= 0 || item.unitPrice <= 0 || (item.costRef ?? 0) < 0)) return ElMessage.warning('请填写有效款式、数量和单价')
  quoteSaving.value = true
  try {
    const created = await payload<{ quoteId: string; quoteNo: string }>(http.post('/erp/sd/quotation', {
      customerId: Number(quoteForm.value.customerId),
      quoteDate: quoteForm.value.quoteDate,
      items: quoteForm.value.items.map(item => ({ styleId: Number(item.styleId), qty: item.qty, unitPrice: item.unitPrice, costRef: item.costRef ?? null })),
    }))
    quoteOpen.value = false; ElMessage.success(`报价单 ${created.quoteNo} 已创建`); await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { quoteSaving.value = false }
}

async function load() {
  if (!spec.value) return
  loading.value = true; error.value = ''
  try { const result = await catalogPage(spec.value, page.value, group.value === 'reports' && key.value === 'ontime' ? asOfDate.value : undefined); rows.value = result.rows; total.value = result.total }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}

function rowTitle(row: Record<string, unknown>) {
  return String(row.name ?? row.code ?? row.id ?? '')
}

async function removeRow(row: Record<string, unknown>) {
  try {
    await ElMessageBox.confirm(
      `确定删除「${rowTitle(row)}」吗？已被业务引用的档案不允许删除，系统会提示改用停用。`,
      '删除确认', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
  } catch { return }
  try {
    await erpApi.removeMaster(resource.value, String(row.id))
    ElMessage.success('已删除')
    await load()
  } catch (e) { ElMessage.error((e as Error).message) }
}

async function toggleEmployee(row: Record<string, unknown>) {
  const target: 0 | 1 = Number(row.status) === 1 ? 0 : 1
  const label = target === 0 ? '离职' : '复职'
  try {
    await ElMessageBox.confirm(`确定将「${rowTitle(row)}」标记为${label}吗？`, `${label}确认`,
      { type: 'warning', confirmButtonText: label, cancelButtonText: '取消' })
  } catch { return }
  try {
    await erpApi.changeEmployeeStatus(String(row.id), target)
    ElMessage.success(`已${label}`)
    await load()
  } catch (e) { ElMessage.error((e as Error).message) }
}
watch([group, key], () => { page.value = 1; load() }, { immediate: true })
</script>
<template>
  <div v-if="spec"><div class="page-head"><div><h1>{{ spec.title }}</h1></div><div class="head-actions"><el-date-picker v-if="group === 'reports' && key === 'ontime'" v-model="asOfDate" type="date" value-format="YYYY-MM-DD" :disabled-date="(date: Date) => date.getTime() > Date.now()" @change="page=1; load()"/><el-button v-if="editableCatalog && user.can(addPermission)" type="primary" @click="openMaster()">新增{{ spec.title }}</el-button><el-button v-if="group === 'master' && key === 'skus' && user.can('md:sku:generate')" type="primary" @click="openSku">生成 SKU</el-button><el-button v-if="group === 'hr' && key === 'salary' && user.can('hr:salarySlip:generate')" type="primary" @click="openSalary">生成工资单</el-button><el-button v-if="group === 'hr' && key === 'salary' && user.can('hr:salarySlip:generateBatch')" @click="openBatch">批量生成</el-button><el-button v-if="group === 'sales' && key === 'quotations' && user.can('sd:quotation:add')" type="primary" @click="openQuote">新建报价</el-button><el-button @click="load">刷新</el-button></div></div>
    <el-tabs v-if="showTabs" :model-value="key" class="catalog-tabs" @tab-change="(name: string | number) => router.push(`/${group}/${name}`)"><el-tab-pane v-for="[tabKey, item] in tabs" :key="tabKey" :label="item.title" :name="tabKey"/></el-tabs>
    <section class="panel"><div v-if="error" class="error-state">{{ error }}</div><div class="table-wrap"><el-table v-loading="loading" :data="rows" stripe empty-text="暂无数据" @row-click="openRow"><el-table-column v-for="column in spec.columns" :key="column.prop" :label="column.label" :min-width="column.width || 120"><template #default="scope"><el-tag v-if="column.enum" size="small" :type="enumTagType(column.enum, scope.row[column.prop])">{{ enumLabel(column.enum, scope.row[column.prop]) }}</el-tag><span v-else>{{ scope.row[column.prop] }}</span></template></el-table-column><el-table-column v-if="group === 'hr' && key === 'employees' && user.can('hr:employee:edit')" label="员工操作" width="100"><template #default="scope"><el-button v-if="Number(scope.row.status) === 1" text type="warning" size="small" @click.stop="toggleEmployee(scope.row)">离职</el-button><el-button v-else text type="success" size="small" @click.stop="toggleEmployee(scope.row)">复职</el-button></template></el-table-column><el-table-column v-if="removableCatalog && user.can(removePermission)" label="操作" width="120"><template #default="scope"><el-button v-if="editableCatalog && user.can(editPermission)" text type="primary" size="small" @click.stop="openMaster(scope.row)">编辑</el-button><el-button text type="danger" size="small" @click.stop="removeRow(scope.row)">删除</el-button></template></el-table-column><el-table-column v-if="group === 'master' && key === 'styles'" label="工艺" width="100"><template #default="scope"><el-button text type="primary" @click.stop="router.push(`/master/styles/${scope.row.id}`)">BOM / 工序</el-button></template></el-table-column><el-table-column v-if="group === 'hr' && key === 'salary' && (user.can('hr:salarySlip:confirm') || user.can('fn:payroll:pay'))" label="操作" width="150"><template #default="scope"><el-button v-if="Number(scope.row.status) === 0 && user.can('hr:salarySlip:confirm')" text type="primary" :loading="salarySaving" @click.stop="confirmSalary(String(scope.row.id))">确认</el-button><el-button v-if="Number(scope.row.status) === 1 && user.can('fn:payroll:pay')" text type="success" :loading="salarySaving" @click.stop="openPay(scope.row)">发放</el-button></template></el-table-column></el-table></div><div class="pagination"><el-pagination v-model:current-page="page" background layout="prev, pager, next, total" :total="total" :page-size="10" @current-change="load"/></div></section>
    <el-dialog v-model="salaryOpen" title="生成工资单" width="min(460px, 94vw)"><el-form label-position="top"><el-form-item label="员工"><el-select v-model="salaryEmployeeId" style="width:100%"><el-option v-for="item in salaryEmployees" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="工资期"><el-date-picker v-model="salaryMonth" type="month" value-format="YYYY-MM" style="width:100%"/></el-form-item></el-form><template #footer><el-button @click="salaryOpen=false">取消</el-button><el-button type="primary" :loading="salarySaving" @click="generateSalary">生成</el-button></template></el-dialog>
    <el-dialog v-model="batchOpen" title="批量生成工资单" width="min(420px, 94vw)"><el-form label-position="top"><el-form-item label="工资期"><el-date-picker v-model="batchPeriod" type="month" value-format="YYYY-MM" style="width:100%"/></el-form-item></el-form><p style="color:var(--el-text-color-secondary);font-size:13px">将为所有在职员工生成或刷新该工资期工资单。</p><template #footer><el-button @click="batchOpen=false">取消</el-button><el-button type="primary" :loading="batchSaving" @click="generateBatch">生成</el-button></template></el-dialog>
    <el-dialog v-model="payOpen" title="发放工资" width="min(460px, 94vw)"><el-form label-position="top"><el-form-item label="工资单"><el-input :model-value="payTarget ? `${payTarget.employeeName} · 实发 ¥${payTarget.netPay.toFixed(2)}` : ''" readonly/></el-form-item><el-form-item label="资金账户"><el-select v-model="payAccountId" style="width:100%"><el-option v-for="item in payAccounts" :key="item.id" :label="`${item.code || ''} ${item.name}`.trim()" :value="item.id"/></el-select></el-form-item><el-form-item label="发放日期"><el-date-picker v-model="payDate" type="date" value-format="YYYY-MM-DD" style="width:100%"/></el-form-item></el-form><template #footer><el-button @click="payOpen=false">取消</el-button><el-button type="primary" :loading="paySaving" @click="paySalary">确认发放</el-button></template></el-dialog>
    <el-dialog v-model="quoteOpen" title="新建报价" width="min(780px, 96vw)"><el-form label-position="top"><div class="order-form-grid"><el-form-item label="客户"><el-select v-model="quoteForm.customerId" filterable style="width:100%"><el-option v-for="item in quoteOptions?.customers" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="报价日期"><el-date-picker v-model="quoteForm.quoteDate" type="date" value-format="YYYY-MM-DD" style="width:100%"/></el-form-item></div></el-form><div v-for="(item, index) in quoteForm.items" :key="index" class="receipt-line"><el-select v-model="item.styleId" filterable placeholder="款式" style="min-width:170px"><el-option v-for="style in quoteOptions?.styles" :key="style.id" :label="`${style.code || ''} ${style.name}`.trim()" :value="style.id"/></el-select><el-input-number v-model="item.qty" :min="1" :max="999999" :precision="0" aria-label="数量"/><el-input-number v-model="item.unitPrice" :min="0" :max="99999999" :precision="2" aria-label="报价单价"/><el-input-number v-model="item.costRef" :min="0" :max="99999999" :precision="2" placeholder="参考成本" aria-label="参考成本"/><el-button text type="danger" :disabled="quoteForm.items.length === 1" @click="removeQuoteLine(index)">删除</el-button></div><el-button text type="primary" @click="addQuoteLine">添加一行</el-button><template #footer><el-button @click="quoteOpen=false">取消</el-button><el-button type="primary" :loading="quoteSaving" @click="createQuote">保存报价</el-button></template></el-dialog>
    <el-dialog v-model="masterOpen" :title="`${masterId ? '编辑' : '新增'}${spec.title}`" width="min(580px, 94vw)"><el-form label-position="top" class="order-form-grid"><el-form-item v-for="field in masterFields" :key="field.key" :label="field.label" :required="field.required"><el-select v-if="field.kind === 'status'" v-model="masterDraft[field.key]" style="width:100%"><el-option :label="resource === 'employee' ? '在职' : '启用'" :value="1"/><el-option :label="resource === 'employee' ? '离职' : '停用'" :value="0"/></el-select><el-select v-else-if="field.kind === 'gender'" v-model="masterDraft[field.key]" style="width:100%"><el-option label="女" :value="0"/><el-option label="男" :value="1"/><el-option v-if="resource !== 'employee'" label="中性" :value="2"/></el-select><el-select v-else-if="field.kind === 'employeeType'" v-model="masterDraft[field.key]" style="width:100%"><el-option label="生产计件" :value="1"/><el-option label="职能固定薪" :value="2"/></el-select><el-select v-else-if="field.kind === 'department'" v-model="masterDraft[field.key]" clearable style="width:100%"><el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id"/></el-select><el-select v-else-if="field.kind === 'category'" v-model="masterDraft[field.key]" style="width:100%"><el-option label="面料" :value="1"/><el-option label="辅料" :value="2"/></el-select><el-date-picker v-else-if="field.kind === 'date'" v-model="masterDraft[field.key]" type="date" value-format="YYYY-MM-DD" style="width:100%"/><el-input-number v-else-if="field.kind === 'number'" v-model="masterDraft[field.key]" :min="0" style="width:100%"/><el-input v-else v-model="masterDraft[field.key]"/></el-form-item></el-form><template #footer><el-button @click="masterOpen=false">取消</el-button><el-button type="primary" :loading="masterSaving" @click="saveMaster">保存</el-button></template></el-dialog>
    <el-dialog v-model="skuOpen" title="生成 SKU" width="min(500px, 94vw)"><el-form label-position="top"><el-form-item label="款式"><el-select v-model="skuDraft.styleId" style="width:100%"><el-option v-for="item in skuOptions?.styles" :key="item.id" :label="`${item.code} · ${item.name}`" :value="item.id"/></el-select></el-form-item><el-form-item label="颜色"><el-select v-model="skuDraft.colorId" style="width:100%"><el-option v-for="item in skuOptions?.colors" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item><el-form-item label="尺码"><el-select v-model="skuDraft.sizeId" style="width:100%"><el-option v-for="item in skuOptions?.sizes" :key="item.id" :label="item.name" :value="item.id"/></el-select></el-form-item></el-form><template #footer><el-button @click="skuOpen=false">取消</el-button><el-button type="primary" :loading="masterSaving" @click="generateSku">生成</el-button></template></el-dialog>
  </div>
</template>

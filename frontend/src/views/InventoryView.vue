<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { erpApi, type InventoryRow } from '../api/erp'
import { useUserStore } from '../stores/user'
const user = useUserStore()
const rows = ref<InventoryRow[]>([])
const keyword = ref('')
const itemType = ref('all')
const loading = ref(true)
const error = ref('')
const adjusting = ref(false)
const saving = ref(false)
const selected = ref<InventoryRow | null>(null)
const actualQty = ref(0)
const reason = ref('')
function openAdjust(row: InventoryRow) { selected.value = row; actualQty.value = Number(row.qty); reason.value = ''; adjusting.value = true }
async function saveAdjust() {
  if (!selected.value || actualQty.value < 0 || !reason.value.trim()) return ElMessage.warning('填写实际数量与盘点原因')
  saving.value = true
  try { await erpApi.adjustStock(selected.value.itemType, selected.value.itemId, selected.value.batchNo, actualQty.value, reason.value.trim(), crypto.randomUUID()); adjusting.value = false; ElMessage.success('盘点差异已记账'); await load() }
  catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
const filtered = computed(() => rows.value.filter(row => (itemType.value === 'all' || String(row.itemType) === itemType.value) && (!keyword.value || `${row.itemCode}${row.itemName}${row.batchNo || ''}`.toLowerCase().includes(keyword.value.toLowerCase()))))
async function load() { loading.value = true; error.value = ''; try { rows.value = await erpApi.inventory() } catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) } finally { loading.value = false } }
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>库存结存</h1><p>物料和成品的批次库存</p></div><el-button @click="load">刷新</el-button></div>
  <section class="panel"><div class="filter-row"><el-input v-model="keyword" placeholder="编码、名称或批次" clearable/><el-segmented v-model="itemType" :options="[{ label: '全部', value: 'all' }, { label: '物料', value: '1' }, { label: '成品', value: '2' }]"/><span class="muted">共 {{ filtered.length }} 项</span></div><div v-if="error" class="error-state">{{ error }}</div><div class="table-wrap"><el-table v-loading="loading" :data="filtered" stripe empty-text="暂无匹配库存"><el-table-column label="类型" width="85"><template #default="scope">{{ Number(scope.row.itemType) === 1 ? '物料' : '成品' }}</template></el-table-column><el-table-column prop="itemCode" label="编码" min-width="150"/><el-table-column prop="itemName" label="名称" min-width="180"/><el-table-column prop="batchNo" label="批次" min-width="130"/><el-table-column prop="unit" label="单位" width="75"/><el-table-column prop="qty" label="结存数量" min-width="110"/><el-table-column prop="safeStock" label="安全库存" min-width="110"/><el-table-column label="预警" width="95"><template #default="scope"><el-tag v-if="Number(scope.row.qty) < Number(scope.row.safeStock)" type="warning" size="small">低于安全库存</el-tag><span v-else class="muted">—</span></template></el-table-column><el-table-column v-if="user.can('st:txn:adjust')" label="操作" width="90"><template #default="scope"><el-button text type="primary" @click="openAdjust(scope.row as InventoryRow)">盘点</el-button></template></el-table-column></el-table></div></section>
  <el-dialog v-model="adjusting" title="盘点库存" width="min(450px, 94vw)"><p>{{ selected?.itemCode }} · {{ selected?.batchNo || '无批次' }}</p><el-form label-position="top"><el-form-item label="账面数量"><span>{{ selected?.qty }} {{ selected?.unit }}</span></el-form-item><el-form-item label="实际数量"><el-input-number v-model="actualQty" :min="0" :precision="3"/></el-form-item><el-form-item label="盘点原因"><el-input v-model="reason" maxlength="255"/></el-form-item></el-form><template #footer><el-button @click="adjusting=false">取消</el-button><el-button type="primary" :loading="saving" @click="saveAdjust">确认调整</el-button></template></el-dialog>
</template>

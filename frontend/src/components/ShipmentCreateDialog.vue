<script setup lang="ts">
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { http, payload } from '../api/client'
import { erpApi, type PendingShipmentOrder } from '../api/erp'

interface BatchOption { batchNo: string; qty: number }
interface ShipLine { skuId: string; skuCode: string; remaining: number; available: number; qty: number; batchNo: string; batches: BatchOption[] }

const props = defineProps<{ modelValue: boolean; order: PendingShipmentOrder | null }>()
const emit = defineEmits<{ 'update:modelValue': [value: boolean]; created: [shipmentId: string] }>()

const lines = ref<ShipLine[]>([])
const logisticsCompany = ref('')
const logisticsNo = ref('')
const loading = ref(false)
const saving = ref(false)

watch(() => props.modelValue, async (open) => {
  if (!open || !props.order) return
  logisticsCompany.value = ''
  logisticsNo.value = ''
  lines.value = props.order.lines.map(item => ({
    skuId: item.skuId, skuCode: item.skuCode, remaining: Number(item.remaining),
    available: Number(item.available), qty: 0, batchNo: '', batches: [],
  }))
  loading.value = true
  try {
    const rows = await erpApi.productBatches(lines.value.map(item => item.skuId))
    for (const line of lines.value) {
      line.batches = rows.filter(row => String(row.skuId) === String(line.skuId))
        .map(row => ({ batchNo: row.batchNo, qty: Number(row.qty) }))
        .sort((a, b) => b.qty - a.qty)
      const picked = line.batches[0]
      if (picked) {
        line.batchNo = picked.batchNo
        line.qty = Math.min(line.remaining, picked.qty)
      }
    }
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { loading.value = false }
})

function batchMax(line: ShipLine) {
  const batch = line.batches.find(item => item.batchNo === line.batchNo)
  return Math.min(line.remaining, batch ? batch.qty : 0)
}

async function save() {
  if (!props.order) return
  for (const line of lines.value) {
    if (line.qty <= 0) continue
    if (!line.batchNo) return ElMessage.warning(`${line.skuCode} 没有可用成品批次，请先完工入库`)
    const batch = line.batches.find(item => item.batchNo === line.batchNo)
    if (!batch) return ElMessage.warning(`${line.skuCode} 的批次 ${line.batchNo} 不存在`)
    if (line.qty > batch.qty) return ElMessage.warning(`${line.skuCode} 批次 ${batch.batchNo} 只剩 ${batch.qty}，不能出货 ${line.qty}`)
    if (line.qty > line.remaining) return ElMessage.warning(`${line.skuCode} 出货数量超过未发数量 ${line.remaining}`)
  }
  const items = lines.value.filter(item => item.qty > 0).map(item => ({ skuId: item.skuId, qty: item.qty, batchNo: item.batchNo.trim() }))
  if (!items.length) return ElMessage.warning('填写有效出货数量和成品批次')
  saving.value = true
  try {
    const created = await payload<{ shipmentId: string; shipmentNo: string }>(http.post('/erp/sh/shipment', {
      orderId: props.order.orderId,
      logisticsCompany: logisticsCompany.value.trim(),
      logisticsNo: logisticsNo.value.trim(),
      items,
    }))
    ElMessage.success(`出货单 ${created.shipmentNo} 已创建，请在详情页确认发货`)
    emit('update:modelValue', false)
    emit('created', created.shipmentId)
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
</script>

<template>
  <el-dialog :model-value="modelValue" :title="`为 ${order?.orderNo || ''} 创建出货单`" width="min(760px, 96vw)" @update:model-value="emit('update:modelValue', $event)">
    <el-form label-position="top" class="order-form-grid">
      <el-form-item label="客户"><el-input :model-value="order?.customerName" readonly/></el-form-item>
      <el-form-item label="交期"><el-input :model-value="order?.deliveryDate" readonly/></el-form-item>
      <el-form-item label="物流公司"><el-input v-model="logisticsCompany" maxlength="50"/></el-form-item>
      <el-form-item label="物流单号"><el-input v-model="logisticsNo" maxlength="50"/></el-form-item>
    </el-form>
    <div v-loading="loading">
      <div v-for="line in lines" :key="line.skuId" class="receipt-line">
        <span>{{ line.skuCode }} · 未发 {{ line.remaining }} · 成品可用 {{ line.available }}</span>
        <el-input-number v-model="line.qty" :min="0" :max="batchMax(line)" :precision="0" aria-label="本次出货数量"/>
        <el-select v-model="line.batchNo" placeholder="选择成品批次" aria-label="成品批次" style="width:210px" :disabled="!line.batches.length">
          <el-option v-for="batch in line.batches" :key="batch.batchNo" :label="`${batch.batchNo}（可用 ${batch.qty}）`" :value="batch.batchNo"/>
        </el-select>
      </div>
    </div>
    <p v-if="!loading && lines.some(line => !line.batches.length)" style="margin:10px 0 0;font-size:13px;color:var(--el-color-warning)">
      有 SKU 暂无成品库存：请先在生产工单里「完工入库」，入库时填的批次会出现在这里。
    </p>
    <template #footer>
      <el-button @click="emit('update:modelValue', false)">取消</el-button>
      <el-button type="primary" :loading="saving" @click="save">保存出货单</el-button>
    </template>
  </el-dialog>
</template>

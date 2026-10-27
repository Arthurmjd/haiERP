<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { financeApi, type PendingApproval } from '../api/finance'
import { useUserStore } from '../stores/user'

const user = useUserStore()
const rows = ref<PendingApproval[]>([])
const loading = ref(true)
const acting = ref('')
const error = ref('')
const purchases = computed(() => rows.value.filter(row => row.docType === 'pu_purchase_order'))
const currency = (value: number | undefined | null) => `¥${Number(value || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`

async function load() {
  loading.value = true; error.value = ''
  try { rows.value = await financeApi.approvals() }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
async function decide(row: PendingApproval, action: 'approve' | 'reject') {
  if (user.demo) return ElMessage.warning('演示模式不支持审批操作，请连接后端')
  let comment = ''
  try {
    const result = await ElMessageBox.prompt(
      action === 'approve' ? '可填写审批意见（选填）' : '请填写驳回原因',
      action === 'approve' ? `通过采购单 #${row.docId}` : `驳回采购单 #${row.docId}`,
      {
        inputType: 'textarea',
        inputPlaceholder: '审批意见',
        inputValidator: (value: string) => {
          if (action === 'approve') return true
          return value && value.trim() ? true : '驳回时请填写原因'
        },
        confirmButtonText: action === 'approve' ? '通过' : '驳回',
        cancelButtonText: '取消',
        type: 'warning',
      },
    )
    comment = (result.value || '').trim()
  } catch { return }
  acting.value = row.approvalId
  try {
    if (action === 'approve') await financeApi.approvePurchase(row.docId, comment)
    else await financeApi.rejectPurchase(row.docId, comment)
    ElMessage.success(action === 'approve' ? '采购单已通过' : '采购单已驳回')
    await load()
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { acting.value = '' }
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>审批中心</h1><p>待审批采购单，通过后进入收货流程</p></div><el-button @click="load">刷新</el-button></div>
  <el-alert v-if="user.demo" type="info" :closable="false" show-icon title="演示模式暂不提供审批数据，审批按钮已禁用。" class="demo-tip" />
  <section class="panel">
    <div v-if="error" class="error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
    <div class="table-wrap">
      <el-table v-loading="loading" :data="purchases" stripe empty-text="暂无待审批采购单">
        <el-table-column label="采购单号" width="140"><template #default="scope">#{{ scope.row.docId }}</template></el-table-column>
        <el-table-column label="单据类型" width="110"><template #default="scope">{{ scope.row.docType === 'pu_purchase_order' ? '采购单' : scope.row.docType }}</template></el-table-column>
        <el-table-column label="审批类型" width="110"><template #default="scope">{{ scope.row.approvalType === 'purchase' ? '采购审批' : scope.row.approvalType }}</template></el-table-column>
        <el-table-column label="金额" min-width="130"><template #default="scope"><span class="money">{{ currency(scope.row.amount) }}</span></template></el-table-column>
        <el-table-column prop="applicant" label="申请人" min-width="120" />
        <el-table-column prop="createTime" label="申请时间" min-width="170" />
        <el-table-column v-if="user.can('pu:purchaseOrder:approve')" label="操作" width="150" fixed="right"><template #default="scope"><el-button text type="primary" :disabled="user.demo" :loading="acting === scope.row.approvalId" @click="decide(scope.row, 'approve')">通过</el-button><el-button text type="danger" :disabled="user.demo" :loading="acting === scope.row.approvalId" @click="decide(scope.row, 'reject')">驳回</el-button></template></el-table-column>
      </el-table>
    </div>
  </section>
</template>
<style scoped>
.demo-tip { margin-bottom: 16px; }
</style>

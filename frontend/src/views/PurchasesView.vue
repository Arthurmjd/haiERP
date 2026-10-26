<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { erpApi, type PurchaseOrder } from '../api/erp'
import { enumLabel, enumTagType } from '../api/labels'
import { useUserStore } from '../stores/user'

const router = useRouter()
const user = useUserStore()
const rows = ref<PurchaseOrder[]>([])
const page = ref(1)
const total = ref(0)
const keyword = ref('')
const applied = ref('')
const loading = ref(false)
const error = ref('')
const statusName = (status: number) => enumLabel('purchaseStatus', status)
async function load() {
  loading.value = true; error.value = ''
  try { const result = await erpApi.purchases(page.value, applied.value); rows.value = result.rows; total.value = result.total }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
function search() { page.value = 1; applied.value = keyword.value.trim(); load() }
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>采购订单</h1><p>查看供应商订单与到货进度</p></div><el-button v-if="user.can('pu:purchaseOrder:add')" type="primary" @click="router.push('/purchases/new')">新建采购单</el-button></div>
  <section class="panel"><div class="filter-row"><el-input v-model="keyword" placeholder="采购单号或供应商" clearable @keyup.enter="search" @clear="search"/><el-button type="primary" @click="search">查询</el-button><el-button @click="load">刷新</el-button></div>
    <div v-if="error" class="error-state">{{ error }}</div><div class="table-wrap"><el-table v-loading="loading" :data="rows" stripe empty-text="暂无采购单" @row-click="(row: PurchaseOrder) => { if (user.can('pu:purchaseOrder:query')) router.push(`/purchases/${row.poId}`) }"><el-table-column prop="poNo" label="采购单号" min-width="150"/><el-table-column prop="supplierName" label="供应商" min-width="150"/><el-table-column prop="orderDate" label="下单日期" min-width="115"/><el-table-column prop="expectDate" label="预计到货" min-width="115"/><el-table-column prop="lineCount" label="明细" width="75"/><el-table-column label="金额" min-width="120"><template #default="scope">¥{{ Number(scope.row.totalAmount).toFixed(2) }}</template></el-table-column><el-table-column label="状态" width="105"><template #default="scope"><el-tag :type="enumTagType('purchaseStatus', scope.row.status)" size="small">{{ statusName(scope.row.status) }}</el-tag></template></el-table-column><el-table-column label="操作" width="150"><template #default="scope"><el-button v-if="user.can('pu:purchaseOrder:query')" text type="primary" @click.stop="router.push(`/purchases/${scope.row.poId}`)">详情</el-button><el-button v-if="scope.row.status === 0 && user.can('pu:purchaseOrder:edit')" text type="primary" @click.stop="router.push(`/purchases/${scope.row.poId}/edit`)">编辑</el-button></template></el-table-column></el-table></div>
    <div class="pagination"><el-pagination v-model:current-page="page" background layout="prev, pager, next, total" :total="total" :page-size="10" @current-change="load"/></div>
  </section>
</template>

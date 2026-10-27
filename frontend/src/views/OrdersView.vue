<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { erpApi, orderStatusLabel, type Order } from '../api/erp'
import { enumTagType } from '../api/labels'
import { usePagedList } from '../composables/usePagedList'
import ListPagination from '../components/ListPagination.vue'
import { useUserStore } from '../stores/user'
const router = useRouter()
const user = useUserStore()
const keyword = ref('')
const appliedKeyword = ref('')
const currency = (n: number) => `¥${Number(n || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })}`
const { rows, total, page, loading, error, load, search, changePage } =
  usePagedList<Order>((pageNum) => erpApi.orders(pageNum, appliedKeyword.value))
function onSearch() { appliedKeyword.value = keyword.value.trim(); search() }
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>销售订单</h1><p>查看订单进度与交期</p></div><el-button v-if="user.can('sd:order:add')" type="primary" @click="router.push('/orders/new')">新建订单</el-button></div>
  <section class="panel"><div class="filter-row"><el-input v-model="keyword" placeholder="订单号或客户名称" clearable @keyup.enter="onSearch" @clear="onSearch"/><el-button type="primary" @click="onSearch">查询</el-button><el-button @click="load">刷新</el-button></div>
    <div v-if="error" class="error-state">{{ error }}</div>
    <div class="table-wrap"><el-table v-loading="loading" :data="rows" stripe empty-text="暂无订单" @row-click="(row: Order) => { if (user.can('sd:order:query')) router.push(`/orders/${row.orderId}`) }"><el-table-column prop="orderNo" label="订单号" min-width="160"/><el-table-column prop="customerName" label="客户" min-width="170"/><el-table-column prop="orderDate" label="下单日期" min-width="120"/><el-table-column prop="deliveryDate" label="交期" min-width="120"/><el-table-column prop="lineCount" label="明细数" width="90"/><el-table-column label="金额" min-width="130"><template #default="scope"><span class="money">{{ currency(scope.row.totalAmount) }}</span></template></el-table-column><el-table-column label="状态" width="100"><template #default="scope"><el-tag :type="enumTagType('orderStatus', scope.row.status)" size="small">{{ orderStatusLabel(scope.row.status) }}</el-tag></template></el-table-column><el-table-column label="操作" width="150"><template #default="scope"><el-button v-if="user.can('sd:order:query')" text type="primary" @click.stop="router.push(`/orders/${scope.row.orderId}`)">详情</el-button><el-button v-if="scope.row.status === 0 && user.can('sd:order:edit')" text type="primary" @click.stop="router.push(`/orders/${scope.row.orderId}/edit`)">编辑</el-button></template></el-table-column></el-table></div>
    <ListPagination :total="total" :page="page" @change="changePage" />
  </section>
</template>

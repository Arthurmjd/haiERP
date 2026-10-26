<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { financeApi, type WorkflowTodo } from '../api/finance'
import { useUserStore } from '../stores/user'

const router = useRouter()
const user = useUserStore()
const rows = ref<WorkflowTodo[]>([])
const loading = ref(true)
const error = ref('')
const typeLabels: Record<string, string> = {
  collect: '应收收款', pay: '应付付款', receive: '采购收货', approval: '采购审批',
  plan: '计划处理', payroll: '工资发放', issue: '生产领料', report: '生产报工', ship: '安排出货',
}
const typeLabel = (type: string) => typeLabels[type] || type || '待办'
const today = new Date().toLocaleDateString('sv-SE')
const isOverdue = (row: WorkflowTodo) => Boolean(row.dueDate && row.dueDate < today)

async function load() {
  loading.value = true; error.value = ''
  try { rows.value = await financeApi.todos() }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
function open(row: WorkflowTodo) {
  if (row.path) router.push(row.path)
}
onMounted(load)
</script>
<template>
  <div class="page-head"><div><h1>待办中心</h1><p>按当前角色汇总待处理业务，点击行进入办理</p></div><el-button @click="load">刷新</el-button></div>
  <el-alert v-if="user.demo" type="info" :closable="false" show-icon title="演示模式暂不提供待办数据，连接后端后按登录角色展示。" class="demo-tip" />
  <section class="panel">
    <div v-if="error" class="error-state">{{ error }} <el-button text type="primary" @click="load">重试</el-button></div>
    <div class="table-wrap">
      <el-table v-loading="loading" :data="rows" stripe empty-text="暂无待办任务，事项均已处理" @row-click="open">
        <el-table-column label="类型" width="130"><template #default="scope"><el-tag size="small" effect="plain">{{ typeLabel(scope.row.todoType) }}</el-tag></template></el-table-column>
        <el-table-column prop="title" label="标题" min-width="260" />
        <el-table-column label="截止日" width="140"><template #default="scope"><span :class="{ 'kpi-warning': isOverdue(scope.row) }">{{ scope.row.dueDate || '—' }}</span><el-tag v-if="isOverdue(scope.row)" type="danger" size="small" class="overdue-tag">逾期</el-tag></template></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="80"><template #default><el-button text type="primary">前往</el-button></template></el-table-column>
      </el-table>
    </div>
  </section>
</template>
<style scoped>
.demo-tip { margin-bottom: 16px; }
.overdue-tag { margin-left: 6px; }
</style>

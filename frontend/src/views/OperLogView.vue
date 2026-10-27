<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { erpApi, type OperLogRow } from '../api/erp'

const rows = ref<OperLogRow[]>([])
const total = ref(0)
const page = ref(1)
const loading = ref(false)

const title = ref('')
const businessType = ref<number | undefined>(undefined)
const status = ref<number | undefined>(undefined)

/** 与 RuoYi BusinessType 对应 */
const businessTypes = [
  { value: 1, label: '新增' },
  { value: 2, label: '修改' },
  { value: 3, label: '删除' },
  { value: 4, label: '授权' },
  { value: 5, label: '导出' },
  { value: 6, label: '导入' },
  { value: 7, label: '强退' },
  { value: 8, label: '生成代码' },
  { value: 9, label: '清空数据' },
  { value: 0, label: '其它' },
]
const businessTypeLabel = (value: number) => businessTypes.find(item => item.value === value)?.label || `类型 ${value}`
const resultLabel = (value: number) => (value === 0 ? '成功' : '失败')

async function load() {
  loading.value = true
  try {
    const result = await erpApi.operLogs({
      pageNum: page.value,
      pageSize: 10,
      title: title.value.trim() || undefined,
      businessType: businessType.value,
      status: status.value,
    })
    rows.value = result.rows
    total.value = result.total
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    loading.value = false
  }
}

function search() {
  page.value = 1
  load()
}

function reset() {
  title.value = ''
  businessType.value = undefined
  status.value = undefined
  page.value = 1
  load()
}

function changePage(next: number) {
  page.value = next
  load()
}

onMounted(load)
</script>

<template>
  <div class="page-head">
    <div>
      <h1>操作日志</h1>
      <p class="muted">记录每一次新增、修改、删除与状态流转，可追溯到具体操作人</p>
    </div>
  </div>
  <section class="panel">
    <el-form :inline="true" class="log-filter">
      <el-form-item label="模块">
        <el-input v-model="title" placeholder="如：销售订单" clearable style="width: 180px" @keyup.enter="search" />
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="businessType" placeholder="全部" clearable style="width: 130px">
          <el-option v-for="item in businessTypes" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="结果">
        <el-select v-model="status" placeholder="全部" clearable style="width: 120px">
          <el-option label="成功" :value="0" />
          <el-option label="失败" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </section>
  <section class="panel">
    <div class="table-wrap">
      <el-table v-loading="loading" :data="rows" stripe empty-text="暂无操作记录">
        <el-table-column prop="operTime" label="操作时间" min-width="170" />
        <el-table-column prop="title" label="模块" min-width="120" />
        <el-table-column label="类型" width="90"><template #default="scope">{{ businessTypeLabel(scope.row.businessType) }}</template></el-table-column>
        <el-table-column prop="operName" label="操作人" width="110" />
        <el-table-column prop="method" label="方法" min-width="260" show-overflow-tooltip />
        <el-table-column prop="operUrl" label="请求地址" min-width="220" show-overflow-tooltip />
        <el-table-column prop="operIp" label="来源 IP" width="130" />
        <el-table-column label="结果" width="90"><template #default="scope"><el-tag :type="scope.row.status === 0 ? 'success' : 'danger'" size="small">{{ resultLabel(scope.row.status) }}</el-tag></template></el-table-column>
        <el-table-column label="错误信息" min-width="160" show-overflow-tooltip><template #default="scope">{{ scope.row.errorMsg || '—' }}</template></el-table-column>
      </el-table>
    </div>
    <div class="pagination">
      <el-pagination v-model:current-page="page" background layout="prev, pager, next, total" :total="total" :page-size="10" @current-change="changePage" />
    </div>
  </section>
</template>

<style scoped>
.log-filter { margin-bottom: -8px; }
</style>

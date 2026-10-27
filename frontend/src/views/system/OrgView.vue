<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { systemApi, type DeptForm, type PostForm, type SysDept, type SysPost } from '../../api/system'

const statusLabel = (value?: string) => (value === '1' ? '停用' : '正常')

// ---------------------------------------------------------------
// 部门
// ---------------------------------------------------------------
const deptRows = ref<SysDept[]>([])
const deptLoading = ref(false)
const deptDialogVisible = ref(false)
const deptDialogTitle = ref('新增部门')
const deptSubmitting = ref(false)
const deptForm = reactive<DeptForm>({ deptName: '', parentId: '0', orderNum: 1, leader: '', phone: '', email: '', status: '0' })

function buildDeptTree(list: SysDept[]): SysDept[] {
  const map = new Map<string, SysDept>()
  list.forEach(item => map.set(item.deptId, { ...item, children: [] }))
  const roots: SysDept[] = []
  map.forEach(node => {
    const parentId = node.parentId || '0'
    const parent = map.get(parentId)
    if (parent && parentId !== '0') parent.children!.push(node)
    else roots.push(node)
  })
  return roots
}

const deptTree = computed(() => buildDeptTree(deptRows.value))
const deptTreeProps = { children: 'children', label: 'deptName' }

const deptParentOptions = computed(() => {
  const options: { id: string; label: string }[] = [{ id: '0', label: '顶级部门' }]
  const walk = (list: SysDept[], depth: number) => {
    for (const item of list) {
      options.push({ id: item.deptId, label: `${'　'.repeat(depth)}${item.deptName}` })
      if (item.children?.length) walk(item.children, depth + 1)
    }
  }
  walk(deptTree.value, 0)
  return options
})

function openDeptCreate(parentId?: string) {
  deptForm.deptId = undefined
  deptForm.deptName = ''
  deptForm.parentId = parentId || '0'
  deptForm.orderNum = 1
  deptForm.leader = ''
  deptForm.phone = ''
  deptForm.email = ''
  deptForm.status = '0'
  deptDialogTitle.value = '新增部门'
  deptDialogVisible.value = true
}

function openDeptEdit(row: SysDept) {
  deptForm.deptId = row.deptId
  deptForm.deptName = row.deptName
  deptForm.parentId = row.parentId
  deptForm.orderNum = Number(row.orderNum ?? 1)
  deptForm.leader = row.leader || ''
  deptForm.phone = row.phone || ''
  deptForm.email = row.email || ''
  deptForm.status = row.status
  deptDialogTitle.value = `编辑部门 · ${row.deptName}`
  deptDialogVisible.value = true
}

async function submitDept() {
  if (!deptForm.deptName.trim()) return ElMessage.warning('部门名称不能为空')
  if (deptForm.deptId && deptForm.deptId === deptForm.parentId) return ElMessage.warning('上级部门不能选择自己')
  deptSubmitting.value = true
  try {
    if (deptForm.deptId) await systemApi.updateDept({ ...deptForm })
    else await systemApi.createDept({ ...deptForm })
    ElMessage.success(deptForm.deptId ? '部门已更新' : '部门已创建')
    deptDialogVisible.value = false
    loadDepartments()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    deptSubmitting.value = false
  }
}

async function removeDept(row: SysDept) {
  try {
    await ElMessageBox.confirm(`确认删除部门「${row.deptName}」吗？`, '删除确认', {
      type: 'warning', confirmButtonText: '确认删除', cancelButtonText: '取消',
    })
    await systemApi.removeDept(row.deptId)
    ElMessage.success('部门已删除')
    loadDepartments()
  } catch (e) {
    if (e === 'cancel' || e === 'close') return
    ElMessage.error((e as Error).message)
  }
}

async function loadDepartments() {
  deptLoading.value = true
  try {
    deptRows.value = await systemApi.departments()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    deptLoading.value = false
  }
}

// ---------------------------------------------------------------
// 岗位
// ---------------------------------------------------------------
const postRows = ref<SysPost[]>([])
const postLoading = ref(false)
const postDialogVisible = ref(false)
const postDialogTitle = ref('新增岗位')
const postSubmitting = ref(false)
const postForm = reactive<PostForm>({ postCode: '', postName: '', postSort: 1, status: '0', remark: '' })

function openPostCreate() {
  postForm.postId = undefined
  postForm.postCode = ''
  postForm.postName = ''
  postForm.postSort = postRows.value.length + 1
  postForm.status = '0'
  postForm.remark = ''
  postDialogTitle.value = '新增岗位'
  postDialogVisible.value = true
}

function openPostEdit(row: SysPost) {
  postForm.postId = row.postId
  postForm.postCode = row.postCode
  postForm.postName = row.postName
  postForm.postSort = row.postSort
  postForm.status = row.status
  postForm.remark = row.remark || ''
  postDialogTitle.value = `编辑岗位 · ${row.postName}`
  postDialogVisible.value = true
}

async function submitPost() {
  if (!postForm.postCode.trim()) return ElMessage.warning('岗位编码不能为空')
  if (!postForm.postName.trim()) return ElMessage.warning('岗位名称不能为空')
  postSubmitting.value = true
  try {
    if (postForm.postId) await systemApi.updatePost({ ...postForm })
    else await systemApi.createPost({ ...postForm })
    ElMessage.success(postForm.postId ? '岗位已更新' : '岗位已创建')
    postDialogVisible.value = false
    loadPosts()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    postSubmitting.value = false
  }
}

async function removePost(row: SysPost) {
  try {
    await ElMessageBox.confirm(`确认删除岗位「${row.postName}」吗？`, '删除确认', {
      type: 'warning', confirmButtonText: '确认删除', cancelButtonText: '取消',
    })
    await systemApi.removePost(row.postId)
    ElMessage.success('岗位已删除')
    loadPosts()
  } catch (e) {
    if (e === 'cancel' || e === 'close') return
    ElMessage.error((e as Error).message)
  }
}

async function loadPosts() {
  postLoading.value = true
  try {
    postRows.value = await systemApi.posts()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    postLoading.value = false
  }
}

onMounted(() => {
  loadDepartments()
  loadPosts()
})
</script>

<template>
  <div class="page-head">
    <div>
      <h1>组织架构</h1>
      <p class="muted">维护部门层级与岗位定义，是用户归属与数据权限划分的基础</p>
    </div>
    <div class="head-actions">
      <el-button @click="openDeptCreate()">新增部门</el-button>
      <el-button type="primary" @click="openPostCreate">新增岗位</el-button>
    </div>
  </div>

  <section class="panel">
    <h2 class="panel-title">部门管理</h2>
    <div class="table-wrap">
      <el-table
        v-loading="deptLoading"
        :data="deptTree"
        row-key="deptId"
        default-expand-all
        :tree-props="deptTreeProps"
        stripe
        empty-text="暂无部门"
      >
        <el-table-column prop="deptName" label="部门名称" min-width="200" />
        <el-table-column prop="leader" label="负责人" min-width="110"><template #default="scope">{{ scope.row.leader || '—' }}</template></el-table-column>
        <el-table-column prop="phone" label="联系电话" min-width="130"><template #default="scope">{{ scope.row.phone || '—' }}</template></el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="180"><template #default="scope">{{ scope.row.email || '—' }}</template></el-table-column>
        <el-table-column prop="orderNum" label="排序" width="70" />
        <el-table-column label="状态" width="90"><template #default="scope">
          <el-tag :type="scope.row.status === '1' ? 'info' : 'success'" size="small">{{ statusLabel(scope.row.status) }}</el-tag>
        </template></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <div class="table-actions">
              <el-button link type="primary" @click="openDeptEdit(scope.row as SysDept)">编辑</el-button>
              <el-button link type="primary" @click="openDeptCreate(scope.row.deptId)">新增下级</el-button>
              <el-button link type="danger" @click="removeDept(scope.row as SysDept)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </section>

  <section class="panel">
    <h2 class="panel-title">岗位管理</h2>
    <div class="table-wrap">
      <el-table v-loading="postLoading" :data="postRows" stripe empty-text="暂无岗位">
        <el-table-column prop="postCode" label="岗位编码" min-width="140"><template #default="scope"><span class="mono">{{ scope.row.postCode }}</span></template></el-table-column>
        <el-table-column prop="postName" label="岗位名称" min-width="140" />
        <el-table-column prop="postSort" label="排序" width="80" />
        <el-table-column label="状态" width="90"><template #default="scope">
          <el-tag :type="scope.row.status === '1' ? 'info' : 'success'" size="small">{{ statusLabel(scope.row.status) }}</el-tag>
        </template></el-table-column>
        <el-table-column prop="remark" label="备注" min-width="200"><template #default="scope">{{ scope.row.remark || '—' }}</template></el-table-column>
        <el-table-column label="操作" width="130" fixed="right">
          <template #default="scope">
            <div class="table-actions">
              <el-button link type="primary" @click="openPostEdit(scope.row as SysPost)">编辑</el-button>
              <el-button link type="danger" @click="removePost(scope.row as SysPost)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </section>

  <el-dialog v-model="deptDialogVisible" :title="deptDialogTitle" width="560px" destroy-on-close>
    <el-form :model="deptForm" label-width="92px">
      <el-form-item label="上级部门">
        <el-select v-model="deptForm.parentId" style="width: 100%">
          <el-option v-for="item in deptParentOptions" :key="item.id" :label="item.label" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="部门名称" required>
        <el-input v-model="deptForm.deptName" placeholder="如：生产部" maxlength="30" />
      </el-form-item>
      <el-form-item label="负责人">
        <el-input v-model="deptForm.leader" placeholder="负责人姓名" maxlength="20" />
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="deptForm.phone" placeholder="手机号" maxlength="11" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="deptForm.email" placeholder="邮箱地址" maxlength="50" />
      </el-form-item>
      <el-form-item label="显示顺序">
        <el-input-number v-model="deptForm.orderNum" :min="0" :max="999" style="width: 140px" />
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="deptForm.status">
          <el-radio value="0">正常</el-radio>
          <el-radio value="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="deptDialogVisible = false">取消</el-button>
      <el-button type="primary" :loading="deptSubmitting" @click="submitDept">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="postDialogVisible" :title="postDialogTitle" width="520px" destroy-on-close>
    <el-form :model="postForm" label-width="92px">
      <el-form-item label="岗位编码" required>
        <el-input v-model="postForm.postCode" placeholder="如：teamLeader" maxlength="64" />
      </el-form-item>
      <el-form-item label="岗位名称" required>
        <el-input v-model="postForm.postName" placeholder="如：车间组长" maxlength="50" />
      </el-form-item>
      <el-form-item label="显示顺序">
        <el-input-number v-model="postForm.postSort" :min="0" :max="999" style="width: 140px" />
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="postForm.status">
          <el-radio value="0">正常</el-radio>
          <el-radio value="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="postForm.remark" type="textarea" :rows="2" placeholder="选填" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="postDialogVisible = false">取消</el-button>
      <el-button type="primary" :loading="postSubmitting" @click="submitPost">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { systemApi, type RoleForm, type SysMenu, type SysRole } from '../../api/system'
import { usePagedList } from '../../composables/usePagedList'
import ListPagination from '../../components/ListPagination.vue'

const query = reactive({ roleName: '', roleKey: '', status: '' })

const { rows, total, page, loading, load, search, changePage } =
  usePagedList<SysRole>((pageNum, pageSize) => systemApi.roles({
    pageNum,
    pageSize,
    roleName: query.roleName.trim() || undefined,
    roleKey: query.roleKey.trim() || undefined,
    status: query.status || undefined,
  }))

/** RuoYi 数据范围 */
const dataScopes = [
  { value: '1', label: '全部数据权限' },
  { value: '2', label: '自定义数据权限' },
  { value: '3', label: '本部门数据权限' },
  { value: '4', label: '本部门及以下数据权限' },
  { value: '5', label: '仅本人数据权限' },
]
const dataScopeLabel = (value?: string) => dataScopes.find(item => item.value === value)?.label || '—'
const statusLabel = (value?: string) => (value === '1' ? '停用' : '正常')

// ---------------------------------------------------------------
// 编辑弹窗
// ---------------------------------------------------------------
const dialogVisible = ref(false)
const dialogTitle = ref('新增角色')
const submitting = ref(false)
const form = reactive<RoleForm>({
  roleName: '', roleKey: '', roleSort: 10, dataScope: '5', status: '0', menuIds: [], deptIds: [], remark: '',
})

function resetForm() {
  form.roleId = undefined
  form.roleName = ''
  form.roleKey = ''
  form.roleSort = 10
  form.dataScope = '5'
  form.status = '0'
  form.menuIds = []
  form.deptIds = []
  form.remark = ''
}

function openCreate() {
  resetForm()
  dialogTitle.value = '新增角色'
  dialogVisible.value = true
}

async function openEdit(row: SysRole) {
  resetForm()
  dialogTitle.value = `编辑角色 · ${row.roleName}`
  try {
    const auth = await systemApi.roleMenus(row.roleId)
    form.roleId = row.roleId
    form.roleName = row.roleName
    form.roleKey = row.roleKey
    form.roleSort = row.roleSort
    form.dataScope = row.dataScope || '5'
    form.status = row.status
    form.remark = row.remark || ''
    form.menuIds = [...auth.checkedKeys]
    dialogVisible.value = true
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function submit() {
  if (!form.roleName.trim()) return ElMessage.warning('角色名称不能为空')
  if (!form.roleKey.trim()) return ElMessage.warning('权限字符不能为空')
  if (!/^[a-zA-Z][a-zA-Z0-9_]*$/.test(form.roleKey)) return ElMessage.warning('权限字符只能包含字母、数字和下划线，且以字母开头')
  submitting.value = true
  try {
    if (form.roleId) {
      await systemApi.updateRole({ ...form })
      await systemApi.grantRoleMenus(form.roleId, form.menuIds || [])
    } else {
      const result = await systemApi.createRole({ ...form })
      if (result?.roleId && form.menuIds?.length) await systemApi.grantRoleMenus(result.roleId, form.menuIds)
    }
    ElMessage.success(form.roleId ? '角色已更新' : '角色已创建')
    dialogVisible.value = false
    load()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    submitting.value = false
  }
}

// ---------------------------------------------------------------
// 分配菜单
// ---------------------------------------------------------------
const grantVisible = ref(false)
const grantTarget = ref<SysRole | null>(null)
const menuTree = ref<SysMenu[]>([])
const checkedMenuIds = ref<string[]>([])
const grantSubmitting = ref(false)

async function openGrant(row: SysRole) {
  try {
    const auth = await systemApi.roleMenus(row.roleId)
    grantTarget.value = row
    menuTree.value = auth.menus
    checkedMenuIds.value = [...auth.checkedKeys]
    grantVisible.value = true
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function submitGrant() {
  if (!grantTarget.value) return
  grantSubmitting.value = true
  try {
    await systemApi.grantRoleMenus(grantTarget.value.roleId, checkedMenuIds.value)
    ElMessage.success('菜单权限已更新')
    grantVisible.value = false
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    grantSubmitting.value = false
  }
}

async function toggleStatus(row: SysRole) {
  const next = row.status === '1' ? '0' : '1'
  try {
    await systemApi.changeRoleStatus(row.roleId, next)
    row.status = next
    ElMessage.success(next === '1' ? '角色已停用' : '角色已启用')
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function removeRow(row: SysRole) {
  try {
    await ElMessageBox.confirm(`确认删除角色「${row.roleName}」吗？该角色的权限将同时失效。`, '删除确认', {
      type: 'warning', confirmButtonText: '确认删除', cancelButtonText: '取消',
    })
    await systemApi.removeRoles([row.roleId])
    ElMessage.success('角色已删除')
    load()
  } catch (e) {
    if (e === 'cancel' || e === 'close') return
    ElMessage.error((e as Error).message)
  }
}

// ---------------------------------------------------------------
// 数据加载
// ---------------------------------------------------------------
const treeProps = { children: 'children', label: 'menuName' }

const menuTreeData = computed(() => menuTree.value)

function reset() {
  query.roleName = ''
  query.roleKey = ''
  query.status = ''
  search()
}

onMounted(load)
</script>

<template>
  <div class="page-head">
    <div>
      <h1>角色管理</h1>
      <p class="muted">定义岗位角色与可见菜单，角色决定了用户在系统里的操作边界</p>
    </div>
    <div class="head-actions">
      <el-button type="primary" @click="openCreate">新增角色</el-button>
    </div>
  </div>

  <section class="panel">
    <el-form :inline="true" class="role-filter">
      <el-form-item label="角色名称">
        <el-input v-model="query.roleName" placeholder="如：销售" clearable style="width: 150px" @keyup.enter="search" />
      </el-form-item>
      <el-form-item label="权限字符">
        <el-input v-model="query.roleKey" placeholder="如：sales" clearable style="width: 150px" @keyup.enter="search" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" placeholder="全部" clearable style="width: 110px">
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
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
      <el-table v-loading="loading" :data="rows" stripe empty-text="暂无角色">
        <el-table-column prop="roleName" label="角色名称" min-width="130" />
        <el-table-column prop="roleKey" label="权限字符" min-width="130"><template #default="scope"><span class="mono">{{ scope.row.roleKey }}</span></template></el-table-column>
        <el-table-column prop="roleSort" label="排序" width="80" />
        <el-table-column label="数据范围" min-width="150"><template #default="scope">{{ dataScopeLabel(scope.row.dataScope) }}</template></el-table-column>
        <el-table-column label="状态" width="90"><template #default="scope">
          <el-tag :type="scope.row.status === '1' ? 'info' : 'success'" size="small">{{ statusLabel(scope.row.status) }}</el-tag>
        </template></el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="160" />
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="scope">
            <div class="table-actions">
              <el-button link type="primary" @click="openEdit(scope.row as SysRole)">编辑</el-button>
              <el-button link type="primary" @click="openGrant(scope.row as SysRole)">分配菜单</el-button>
              <el-button link type="primary" @click="toggleStatus(scope.row as SysRole)">{{ scope.row.status === '1' ? '启用' : '停用' }}</el-button>
              <el-button link type="danger" @click="removeRow(scope.row as SysRole)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <ListPagination :total="total" :page="page" @change="changePage" />
  </section>

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="580px" destroy-on-close>
    <el-form :model="form" label-width="92px">
      <el-form-item label="角色名称" required>
        <el-input v-model="form.roleName" placeholder="如：车间组长" maxlength="30" />
      </el-form-item>
      <el-form-item label="权限字符" required>
        <el-input v-model="form.roleKey" placeholder="如：teamLeader" maxlength="100" />
      </el-form-item>
      <el-form-item label="显示顺序" required>
        <el-input-number v-model="form.roleSort" :min="0" :max="999" style="width: 140px" />
      </el-form-item>
      <el-form-item label="数据范围">
        <el-select v-model="form.dataScope" style="width: 100%">
          <el-option v-for="item in dataScopes" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio value="0">正常</el-radio>
          <el-radio value="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="选填" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" :loading="submitting" @click="submit">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="grantVisible" title="分配菜单权限" width="560px">
    <p class="muted" style="margin-top: 0">勾选「{{ grantTarget?.roleName }}」可见的菜单与操作按钮。</p>
    <div class="grant-tree">
      <el-tree
        ref="menuTreeRef"
        :data="menuTreeData"
        :props="treeProps"
        node-key="menuId"
        show-checkbox
        default-expand-all
        :default-checked-keys="checkedMenuIds"
        @check="(checked: any) => checkedMenuIds = (checked as any).checkedKeys.concat((checked as any).halfCheckedKeys)"
      />
    </div>
    <template #footer>
      <el-button @click="grantVisible = false">取消</el-button>
      <el-button type="primary" :loading="grantSubmitting" @click="submitGrant">保存</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.role-filter { margin-bottom: -8px; }
.grant-tree { max-height: 420px; overflow-y: auto; border: 1px solid #e2e9e7; border-radius: 6px; padding: 10px; }
</style>

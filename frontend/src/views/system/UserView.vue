<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { systemApi, type SysDept, type SysRole, type SysUser, type UserForm } from '../../api/system'

const rows = ref<SysUser[]>([])
const total = ref(0)
const page = ref(1)
const pageSize = 10
const loading = ref(false)
const departments = ref<SysDept[]>([])
const roles = ref<SysRole[]>([])

const query = reactive({ userName: '', nickName: '', phoneNumber: '', status: '', deptId: '' })

const statusLabel = (value?: string) => (value === '1' ? '停用' : '正常')
const sexLabel = (value?: string) => (value === '1' ? '女' : value === '2' ? '未知' : '男')

// ---------------------------------------------------------------
// 编辑弹窗
// ---------------------------------------------------------------
const dialogVisible = ref(false)
const dialogTitle = ref('新增用户')
const submitting = ref(false)
const form = reactive<UserForm>({
  userName: '', nickName: '', password: '', email: '', phonenumber: '',
  sex: '0', status: '0', deptId: null, roleIds: [], postIds: [], remark: '',
})

function resetForm() {
  form.userId = undefined
  form.userName = ''
  form.nickName = ''
  form.password = ''
  form.email = ''
  form.phonenumber = ''
  form.sex = '0'
  form.status = '0'
  form.deptId = null
  form.roleIds = []
  form.postIds = []
  form.remark = ''
}

function openCreate() {
  resetForm()
  dialogTitle.value = '新增用户'
  dialogVisible.value = true
}

async function openEdit(row: SysUser) {
  resetForm()
  dialogTitle.value = `编辑用户 · ${row.userName}`
  try {
    const detail = await systemApi.user(row.userId)
    form.userId = detail.userId
    form.userName = detail.userName
    form.nickName = detail.nickName
    form.email = detail.email || ''
    form.phonenumber = detail.phonenumber || ''
    form.sex = detail.sex || '0'
    form.status = detail.status
    form.deptId = detail.deptId ?? null
    form.remark = detail.remark || ''
    const auth = await systemApi.userAuthRoles(row.userId)
    form.roleIds = auth.roles
      .filter(role => auth.user.roles?.some(item => item.roleId === role.roleId))
      .map(role => role.roleId)
    dialogVisible.value = true
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function submit() {
  if (!form.userName.trim()) return ElMessage.warning('登录账号不能为空')
  if (!form.nickName.trim()) return ElMessage.warning('用户昵称不能为空')
  if (!form.userId && !form.password) return ElMessage.warning('新增用户必须设置初始密码')
  if (form.email && !/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(form.email)) return ElMessage.warning('邮箱格式不正确')
  if (form.phonenumber && form.phonenumber.length > 11) return ElMessage.warning('手机号码长度不能超过 11 位')
  submitting.value = true
  try {
    if (form.userId) {
      await systemApi.updateUser({ ...form })
      if (form.roleIds?.length) await systemApi.grantUserRoles(form.userId, form.roleIds)
    } else {
      const result = await systemApi.createUser({ ...form })
      if (result?.userId && form.roleIds?.length) await systemApi.grantUserRoles(result.userId, form.roleIds)
    }
    ElMessage.success(form.userId ? '用户已更新' : '用户已创建')
    dialogVisible.value = false
    load()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    submitting.value = false
  }
}

// ---------------------------------------------------------------
// 重置密码 / 状态 / 删除
// ---------------------------------------------------------------
const resetVisible = ref(false)
const resetTarget = ref<SysUser | null>(null)
const resetPasswordValue = ref('')
const resetSubmitting = ref(false)

function openReset(row: SysUser) {
  resetTarget.value = row
  resetPasswordValue.value = ''
  resetVisible.value = true
}

async function submitReset() {
  if (!resetTarget.value) return
  if (resetPasswordValue.value.length < 5) return ElMessage.warning('密码长度不能少于 5 位')
  resetSubmitting.value = true
  try {
    await systemApi.resetPassword(resetTarget.value.userId, resetPasswordValue.value)
    ElMessage.success('密码已重置')
    resetVisible.value = false
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    resetSubmitting.value = false
  }
}

async function toggleStatus(row: SysUser) {
  const next = row.status === '1' ? '0' : '1'
  try {
    await systemApi.changeUserStatus(row.userId, next)
    row.status = next
    ElMessage.success(next === '1' ? '账号已停用' : '账号已启用')
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function removeRow(row: SysUser) {
  try {
    await ElMessageBox.confirm(`确认删除用户「${row.nickName}（${row.userName}）」吗？`, '删除确认', {
      type: 'warning', confirmButtonText: '确认删除', cancelButtonText: '取消',
    })
    await systemApi.removeUsers([row.userId])
    ElMessage.success('用户已删除')
    load()
  } catch (e) {
    if (e === 'cancel' || e === 'close') return
    ElMessage.error((e as Error).message)
  }
}

// ---------------------------------------------------------------
// 授权角色
// ---------------------------------------------------------------
const grantVisible = ref(false)
const grantTarget = ref<SysUser | null>(null)
const grantRoleIds = ref<string[]>([])
const grantSubmitting = ref(false)

async function openGrant(row: SysUser) {
  try {
    const auth = await systemApi.userAuthRoles(row.userId)
    grantTarget.value = auth.user
    grantRoleIds.value = auth.roles
      .filter(role => auth.user.roles?.some(item => item.roleId === role.roleId))
      .map(role => role.roleId)
    grantVisible.value = true
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function submitGrant() {
  if (!grantTarget.value) return
  grantSubmitting.value = true
  try {
    await systemApi.grantUserRoles(grantTarget.value.userId, grantRoleIds.value)
    ElMessage.success('角色已分配')
    grantVisible.value = false
    load()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    grantSubmitting.value = false
  }
}

// ---------------------------------------------------------------
// 数据加载
// ---------------------------------------------------------------
const deptOptions = computed(() => {
  const flat: { deptId: string; label: string }[] = []
  const walk = (list: SysDept[], depth: number) => {
    for (const dept of list) {
      flat.push({ deptId: dept.deptId, label: `${'　'.repeat(depth)}${dept.deptName}` })
      if (dept.children?.length) walk(dept.children, depth + 1)
    }
  }
  walk(departments.value, 0)
  return flat
})

async function load() {
  loading.value = true
  try {
    const result = await systemApi.users({
      pageNum: page.value,
      pageSize,
      userName: query.userName.trim() || undefined,
      nickName: query.nickName.trim() || undefined,
      phoneNumber: query.phoneNumber.trim() || undefined,
      status: query.status || undefined,
      deptId: query.deptId || undefined,
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
  query.userName = ''
  query.nickName = ''
  query.phoneNumber = ''
  query.status = ''
  query.deptId = ''
  page.value = 1
  load()
}

function changePage(next: number) {
  page.value = next
  load()
}

onMounted(async () => {
  try {
    const [depts, roleList] = await Promise.all([
      systemApi.departments(),
      systemApi.allRoles(),
    ])
    departments.value = depts
    roles.value = roleList
  } catch {
    ElMessage.warning('组织架构加载失败，用户列表仍可使用')
  }
  load()
})
</script>

<template>
  <div class="page-head">
    <div>
      <h1>用户管理</h1>
      <p class="muted">维护登录账号、所属部门与岗位，决定谁能进入系统、能看哪些业务</p>
    </div>
    <div class="head-actions">
      <el-button type="primary" @click="openCreate">新增用户</el-button>
    </div>
  </div>

  <section class="panel">
    <el-form :inline="true" class="user-filter">
      <el-form-item label="账号">
        <el-input v-model="query.userName" placeholder="登录账号" clearable style="width: 150px" @keyup.enter="search" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="query.nickName" placeholder="用户昵称" clearable style="width: 150px" @keyup.enter="search" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="query.phoneNumber" placeholder="手机号码" clearable style="width: 150px" @keyup.enter="search" />
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="query.deptId" placeholder="全部" clearable style="width: 140px">
          <el-option v-for="item in deptOptions" :key="item.deptId" :label="item.label" :value="item.deptId" />
        </el-select>
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
      <el-table v-loading="loading" :data="rows" stripe empty-text="暂无用户">
        <el-table-column prop="userName" label="登录账号" min-width="120" />
        <el-table-column prop="nickName" label="昵称" min-width="100" />
        <el-table-column label="部门" min-width="110"><template #default="scope">{{ scope.row.dept?.deptName || '—' }}</template></el-table-column>
        <el-table-column label="角色" min-width="140"><template #default="scope">
          <el-tag v-for="name in (scope.row.roleNames || '').split(',').filter(Boolean)" :key="name" size="small" effect="plain" style="margin-right: 4px">{{ name }}</el-tag>
          <span v-if="!scope.row.roleNames" class="muted">未分配</span>
        </template></el-table-column>
        <el-table-column label="性别" width="70"><template #default="scope">{{ sexLabel(scope.row.sex) }}</template></el-table-column>
        <el-table-column prop="phonenumber" label="手机号" min-width="120"><template #default="scope">{{ scope.row.phonenumber || '—' }}</template></el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="170" show-overflow-tooltip><template #default="scope">{{ scope.row.email || '—' }}</template></el-table-column>
        <el-table-column label="状态" width="90"><template #default="scope">
          <el-tag :type="scope.row.status === '1' ? 'info' : 'success'" size="small">{{ statusLabel(scope.row.status) }}</el-tag>
        </template></el-table-column>
        <el-table-column label="操作" width="260" fixed="right">
          <template #default="scope">
            <div class="table-actions">
              <el-button link type="primary" @click="openEdit(scope.row)">编辑</el-button>
              <el-button link type="primary" @click="openGrant(scope.row)">授权</el-button>
              <el-button link type="primary" @click="openReset(scope.row)">重置密码</el-button>
              <el-button link type="primary" @click="toggleStatus(scope.row)">{{ scope.row.status === '1' ? '启用' : '停用' }}</el-button>
              <el-button link type="danger" @click="removeRow(scope.row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination">
      <el-pagination v-model:current-page="page" background layout="prev, pager, next, total" :total="total" :page-size="pageSize" @current-change="changePage" />
    </div>
  </section>

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="620px" destroy-on-close>
    <el-form :model="form" label-width="92px">
      <el-form-item label="登录账号" required>
        <el-input v-model="form.userName" :disabled="Boolean(form.userId)" placeholder="用于登录，如 zhangsan" maxlength="30" />
      </el-form-item>
      <el-form-item label="用户昵称" required>
        <el-input v-model="form.nickName" placeholder="真实姓名或岗位称呼" maxlength="30" />
      </el-form-item>
      <el-form-item v-if="!form.userId" label="初始密码" required>
        <el-input v-model="form.password" type="password" show-password placeholder="不少于 5 位" />
      </el-form-item>
      <el-form-item label="所属部门">
        <el-select v-model="form.deptId" placeholder="选择部门" clearable style="width: 100%">
          <el-option v-for="item in deptOptions" :key="item.deptId" :label="item.label" :value="item.deptId" />
        </el-select>
      </el-form-item>
      <el-form-item label="角色">
        <el-select v-model="form.roleIds" multiple placeholder="可分配多个角色" style="width: 100%">
          <el-option v-for="role in roles" :key="role.roleId" :label="`${role.roleName}（${role.roleKey}）`" :value="role.roleId" />
        </el-select>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.sex">
          <el-radio value="0">男</el-radio>
          <el-radio value="1">女</el-radio>
          <el-radio value="2">未知</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.phonenumber" placeholder="11 位手机号" maxlength="11" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" placeholder="name@hai-erp.local" maxlength="50" />
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

  <el-dialog v-model="resetVisible" title="重置密码" width="420px">
    <p class="muted" style="margin-top: 0">将为「{{ resetTarget?.nickName }}（{{ resetTarget?.userName }}）」设置新密码。</p>
    <el-input v-model="resetPasswordValue" type="password" show-password placeholder="输入新密码，不少于 5 位" />
    <template #footer>
      <el-button @click="resetVisible = false">取消</el-button>
      <el-button type="primary" :loading="resetSubmitting" @click="submitReset">确认重置</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="grantVisible" title="分配角色" width="460px">
    <p class="muted" style="margin-top: 0">为「{{ grantTarget?.nickName }}」勾选可访问的角色。</p>
    <el-checkbox-group v-model="grantRoleIds" style="display: flex; flex-direction: column; gap: 8px">
      <el-checkbox v-for="role in roles" :key="role.roleId" :value="role.roleId">
        {{ role.roleName }}<span class="muted"> · {{ role.roleKey }}</span>
      </el-checkbox>
    </el-checkbox-group>
    <template #footer>
      <el-button @click="grantVisible = false">取消</el-button>
      <el-button type="primary" :loading="grantSubmitting" @click="submitGrant">保存</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.user-filter { margin-bottom: -8px; }
</style>

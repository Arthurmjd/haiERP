<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { systemApi, type MenuForm, type SysMenu } from '../../api/system'

const rows = ref<SysMenu[]>([])
const loading = ref(false)
const query = reactive({ menuName: '', status: '' })

const menuTypes = [
  { value: 'M', label: '目录' },
  { value: 'C', label: '菜单' },
  { value: 'F', label: '按钮' },
]
const menuTypeLabel = (value?: string) => menuTypes.find(item => item.value === value)?.label || '—'
const statusLabel = (value?: string) => (value === '1' ? '停用' : '正常')

/** 把平铺列表组装成树 */
function buildTree(list: SysMenu[]): SysMenu[] {
  const map = new Map<string, SysMenu>()
  list.forEach(item => map.set(item.menuId, { ...item, children: [] }))
  const roots: SysMenu[] = []
  map.forEach(node => {
    const parentId = node.parentId || '0'
    const parent = map.get(parentId)
    if (parent && parentId !== '0') parent.children!.push(node)
    else roots.push(node)
  })
  return roots
}

const treeData = computed(() => buildTree(rows.value))

const treeProps = { children: 'children', label: 'menuName' }

// ---------------------------------------------------------------
// 编辑弹窗
// ---------------------------------------------------------------
const dialogVisible = ref(false)
const dialogTitle = ref('新增菜单')
const submitting = ref(false)
const form = reactive<MenuForm>({
  menuName: '', parentId: '0', orderNum: 1, path: '', component: '',
  isFrame: '1', isCache: '0', menuType: 'C', visible: '0', status: '0', perms: '', icon: '#', remark: '',
})

/** 上级菜单下拉：排除目录自身与自己的后代 */
const parentOptions = computed(() => {
  const options: { id: string; label: string }[] = [{ id: '0', label: '顶级目录' }]
  const walk = (list: SysMenu[], depth: number) => {
    for (const item of list) {
      options.push({ id: item.menuId, label: `${'　'.repeat(depth)}${item.menuName}` })
      if (item.children?.length) walk(item.children, depth + 1)
    }
  }
  walk(treeData.value, 0)
  return options
})

function resetForm() {
  form.menuId = undefined
  form.menuName = ''
  form.parentId = '0'
  form.orderNum = 1
  form.path = ''
  form.component = ''
  form.isFrame = '1'
  form.isCache = '0'
  form.menuType = 'C'
  form.visible = '0'
  form.status = '0'
  form.perms = ''
  form.icon = '#'
  form.remark = ''
}

function openCreate(parentId?: string, menuType?: string) {
  resetForm()
  if (parentId) {
    form.parentId = parentId
    form.menuType = menuType || 'C'
  }
  dialogTitle.value = '新增菜单'
  dialogVisible.value = true
}

function openEdit(row: SysMenu) {
  resetForm()
  dialogTitle.value = `编辑菜单 · ${row.menuName}`
  form.menuId = row.menuId
  form.menuName = row.menuName
  form.parentId = row.parentId
  form.orderNum = row.orderNum ?? 1
  form.path = row.path || ''
  form.component = row.component || ''
  form.menuType = row.menuType
  form.visible = row.visible || '0'
  form.status = row.status
  form.perms = row.perms || ''
  form.icon = row.icon || '#'
  dialogVisible.value = true
}

async function submit() {
  if (!form.menuName.trim()) return ElMessage.warning('菜单名称不能为空')
  const path = (form.path || '').trim()
  if (form.menuType === 'C' && !path) return ElMessage.warning('菜单类型必须填写路由地址')
  if (form.menuType === 'C' && path.includes('http')) return ElMessage.warning('路由地址不能包含 http 链接')
  submitting.value = true
  try {
    if (form.menuId) await systemApi.updateMenu({ ...form })
    else await systemApi.createMenu({ ...form })
    ElMessage.success(form.menuId ? '菜单已更新' : '菜单已创建')
    dialogVisible.value = false
    load()
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    submitting.value = false
  }
}

async function removeRow(row: SysMenu) {
  try {
    await ElMessageBox.confirm(`确认删除菜单「${row.menuName}」吗？`, '删除确认', {
      type: 'warning', confirmButtonText: '确认删除', cancelButtonText: '取消',
    })
    await systemApi.removeMenu(row.menuId)
    ElMessage.success('菜单已删除')
    load()
  } catch (e) {
    if (e === 'cancel' || e === 'close') return
    ElMessage.error((e as Error).message)
  }
}

// ---------------------------------------------------------------
// 数据加载
// ---------------------------------------------------------------
async function load() {
  loading.value = true
  try {
    rows.value = await systemApi.menus({
      menuName: query.menuName.trim() || undefined,
      status: query.status || undefined,
    })
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    loading.value = false
  }
}

function search() {
  load()
}

function reset() {
  query.menuName = ''
  query.status = ''
  load()
}

onMounted(load)
</script>

<template>
  <div class="page-head">
    <div>
      <h1>菜单管理</h1>
      <p class="muted">维护菜单树与按钮级权限标识，是前端路由与后端接口鉴权的共同依据</p>
    </div>
    <div class="head-actions">
      <el-button type="primary" @click="openCreate()">新增菜单</el-button>
    </div>
  </div>

  <section class="panel">
    <el-form :inline="true" class="menu-filter">
      <el-form-item label="菜单名称">
        <el-input v-model="query.menuName" placeholder="按名称筛选" clearable style="width: 180px" @keyup.enter="search" />
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
      <el-table
        v-loading="loading"
        :data="treeData"
        row-key="menuId"
        default-expand-all
        :tree-props="treeProps"
        stripe
        empty-text="暂无菜单"
      >
        <el-table-column prop="menuName" label="菜单名称" min-width="200" />
        <el-table-column label="类型" width="80"><template #default="scope">
          <el-tag size="small" effect="plain">{{ menuTypeLabel(scope.row.menuType) }}</el-tag>
        </template></el-table-column>
        <el-table-column prop="perms" label="权限标识" min-width="180"><template #default="scope"><span class="mono">{{ scope.row.perms || '—' }}</span></template></el-table-column>
        <el-table-column prop="path" label="路由地址" min-width="140"><template #default="scope">{{ scope.row.path || '—' }}</template></el-table-column>
        <el-table-column prop="component" label="组件路径" min-width="150"><template #default="scope">{{ scope.row.component || '—' }}</template></el-table-column>
        <el-table-column prop="orderNum" label="排序" width="70" />
        <el-table-column label="状态" width="90"><template #default="scope">
          <el-tag :type="scope.row.status === '1' ? 'info' : 'success'" size="small">{{ statusLabel(scope.row.status) }}</el-tag>
        </template></el-table-column>
        <el-table-column label="操作" width="230" fixed="right">
          <template #default="scope">
            <div class="table-actions">
              <el-button link type="primary" @click="openEdit(scope.row)">编辑</el-button>
              <el-button v-if="scope.row.menuType !== 'F'" link type="primary" @click="openCreate(scope.row.menuId, scope.row.menuType === 'M' ? 'C' : 'F')">新增下级</el-button>
              <el-button link type="danger" @click="removeRow(scope.row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </section>

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="620px" destroy-on-close>
    <el-form :model="form" label-width="92px">
      <el-form-item label="上级菜单">
        <el-select v-model="form.parentId" style="width: 100%">
          <el-option v-for="item in parentOptions" :key="item.id" :label="item.label" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="菜单类型" required>
        <el-radio-group v-model="form.menuType">
          <el-radio value="M">目录</el-radio>
          <el-radio value="C">菜单</el-radio>
          <el-radio value="F">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单名称" required>
        <el-input v-model="form.menuName" placeholder="显示在侧边栏或按钮上的文字" maxlength="50" />
      </el-form-item>
      <el-form-item v-if="form.menuType !== 'F'" label="路由地址">
        <el-input v-model="form.path" placeholder="如 system/user" />
      </el-form-item>
      <el-form-item v-if="form.menuType === 'C'" label="组件路径">
        <el-input v-model="form.component" placeholder="如 system/user" />
      </el-form-item>
      <el-form-item label="权限标识">
        <el-input v-model="form.perms" placeholder="如 system:user:list" />
      </el-form-item>
      <el-form-item label="显示顺序">
        <el-input-number v-model="form.orderNum" :min="0" :max="999" style="width: 140px" />
      </el-form-item>
      <el-form-item v-if="form.menuType !== 'F'" label="图标">
        <el-input v-model="form.icon" placeholder="Element Plus 图标名，如 user" />
      </el-form-item>
      <el-form-item v-if="form.menuType !== 'F'" label="显示状态">
        <el-radio-group v-model="form.visible">
          <el-radio value="0">显示</el-radio>
          <el-radio value="1">隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单状态">
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
</template>

<style scoped>
.menu-filter { margin-bottom: -8px; }
</style>

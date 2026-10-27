import { http, pageTotal, payload, type Page } from './client'
import { isDemoMode } from './demo'

// ---------------------------------------------------------------
// 系统管理：用户 / 角色 / 菜单 / 部门 / 岗位
// 与后端 RuoYi 控制器一一对应，权限字符串见 sql/system-permissions.sql
// ---------------------------------------------------------------

export interface SysUser {
  userId: string
  userName: string
  nickName: string
  email?: string
  phonenumber?: string
  sex?: string
  status: string
  deptId?: string | number | null
  dept?: { deptId: string; deptName: string } | null
  roles?: SysRole[]
  /** 角色名称汇总，逗号分隔（列表接口返回） */
  roleNames?: string
  roleIds?: string[]
  postIds?: string[]
  loginDate?: string
  createTime?: string
  remark?: string
}

export interface SysRole {
  roleId: string
  roleName: string
  roleKey: string
  roleSort: number
  dataScope?: string
  status: string
  menuIds?: string[]
  deptIds?: string[]
  createTime?: string
  remark?: string
}

export interface SysMenu {
  menuId: string
  menuName: string
  parentId: string
  parentName?: string
  orderNum?: number
  path?: string
  component?: string
  menuType: string
  visible?: string
  status: string
  perms?: string
  icon?: string
  children?: SysMenu[]
}

export interface SysDept {
  deptId: string
  deptName: string
  parentId: string
  parentName?: string
  orderNum?: number | string
  leader?: string
  phone?: string
  email?: string
  status: string
  children?: SysDept[]
}

export interface SysPost {
  postId: string
  postCode: string
  postName: string
  postSort: number
  status: string
  remark?: string
}

export interface UserQuery {
  pageNum: number
  pageSize?: number
  userName?: string
  nickName?: string
  phoneNumber?: string
  status?: string
  deptId?: string | number
}

export interface RoleQuery {
  pageNum: number
  pageSize?: number
  roleName?: string
  roleKey?: string
  status?: string
}

export interface MenuQuery {
  menuName?: string
  status?: string
}

export interface DeptQuery {
  deptName?: string
  status?: string
}

/** 用户表单（新增/编辑） */
export interface UserForm {
  userId?: string
  userName: string
  nickName: string
  password?: string
  email?: string
  phonenumber?: string
  sex?: string
  status: string
  deptId?: string | number | null
  roleIds?: string[]
  postIds?: string[]
  remark?: string
}

/** 角色表单 */
export interface RoleForm {
  roleId?: string
  roleName: string
  roleKey: string
  roleSort: number
  dataScope?: string
  status: string
  menuIds?: string[]
  deptIds?: string[]
  remark?: string
}

/** 菜单表单 */
export interface MenuForm {
  menuId?: string
  menuName: string
  parentId: string
  orderNum?: number
  path?: string
  component?: string
  isFrame?: string
  isCache?: string
  menuType: string
  visible?: string
  status: string
  perms?: string
  icon?: string
  remark?: string
}

/** 部门表单 */
export interface DeptForm {
  deptId?: string
  deptName: string
  parentId: string
  orderNum?: number
  leader?: string
  phone?: string
  email?: string
  status: string
}

/** 岗位表单 */
export interface PostForm {
  postId?: string
  postCode: string
  postName: string
  postSort: number
  status: string
  remark?: string
}

interface Envelope<T = unknown> {
  code: number
  msg: string
  data?: T
  [key: string]: unknown
}

/** 授权角色：GET /system/user/authRole/{userId} —— 注意后端把 user / roles 放在响应顶层 */
interface AuthRoleResult {
  user: SysUser
  roles: SysRole[]
}

/** 授权菜单：GET /system/menu/roleMenuTreeselect/{roleId} —— checkedKeys / menus 在响应顶层 */
interface RoleMenuResult {
  checkedKeys: string[]
  menus: SysMenu[]
}

// ---------------------------------------------------------------
// 演示模式数据
// ---------------------------------------------------------------

const demoDepartments: SysDept[] = [
  { deptId: '100', deptName: '海岚服饰', parentId: '0', orderNum: 1, leader: '管理员', status: '0' },
  { deptId: '101', deptName: '销售部', parentId: '100', orderNum: 1, leader: '销售', status: '0' },
  { deptId: '102', deptName: '计划部', parentId: '100', orderNum: 2, leader: '计划员', status: '0' },
  { deptId: '103', deptName: '生产部', parentId: '100', orderNum: 3, leader: '生产主管', status: '0' },
  { deptId: '104', deptName: '仓储部', parentId: '100', orderNum: 4, leader: '仓管员', status: '0' },
  { deptId: '105', deptName: '财务部', parentId: '100', orderNum: 5, leader: '财务', status: '0' },
  { deptId: '106', deptName: '人事部', parentId: '100', orderNum: 6, leader: '人事', status: '0' },
]

const demoPosts: SysPost[] = [
  { postId: '1', postCode: 'sales', postName: '销售专员', postSort: 1, status: '0' },
  { postId: '2', postCode: 'planner', postName: '计划专员', postSort: 2, status: '0' },
  { postId: '3', postCode: 'production', postName: '生产主管', postSort: 3, status: '0' },
  { postId: '4', postCode: 'worker', postName: '车间工人', postSort: 4, status: '0' },
  { postId: '5', postCode: 'warehouse', postName: '仓管员', postSort: 5, status: '0' },
  { postId: '6', postCode: 'finance', postName: '成本会计', postSort: 6, status: '0' },
  { postId: '7', postCode: 'hr', postName: '人事专员', postSort: 7, status: '0' },
]

const demoRoles: SysRole[] = [
  { roleId: '1', roleName: '管理员', roleKey: 'admin', roleSort: 1, dataScope: '1', status: '0' },
  { roleId: '22', roleName: '销售', roleKey: 'sales', roleSort: 20, dataScope: '5', status: '0' },
  { roleId: '23', roleName: '财务', roleKey: 'finance', roleSort: 21, dataScope: '5', status: '0' },
  { roleId: '24', roleName: '计划员', roleKey: 'planner', roleSort: 30, dataScope: '5', status: '0' },
  { roleId: '25', roleName: '生产主管', roleKey: 'production', roleSort: 31, dataScope: '5', status: '0' },
  { roleId: '26', roleName: '仓管员', roleKey: 'wh', roleSort: 32, dataScope: '5', status: '0' },
  { roleId: '27', roleName: '人事员', roleKey: 'hr', roleSort: 33, dataScope: '5', status: '0' },
  { roleId: '28', roleName: '厂长', roleKey: 'boss', roleSort: 34, dataScope: '2', status: '0' },
]

const demoUsers: SysUser[] = [
  { userId: '1', userName: 'admin', nickName: '管理员', status: '0', deptId: '100', email: 'admin@hai-erp.local', phonenumber: '13800000001', sex: '0' },
  { userId: '2', userName: 'sales', nickName: '销售', status: '0', deptId: '101', email: 'sales@hai-erp.local', phonenumber: '13800000002', sex: '1' },
  { userId: '3', userName: 'planner', nickName: '计划员', status: '0', deptId: '102', email: 'planner@hai-erp.local', phonenumber: '13800000003', sex: '0' },
  { userId: '4', userName: 'production', nickName: '生产主管', status: '0', deptId: '103', email: 'production@hai-erp.local', phonenumber: '13800000004', sex: '0' },
  { userId: '5', userName: 'wh', nickName: '仓管员', status: '0', deptId: '104', email: 'wh@hai-erp.local', phonenumber: '13800000005', sex: '0' },
  { userId: '6', userName: 'finance', nickName: '财务', status: '0', deptId: '105', email: 'finance@hai-erp.local', phonenumber: '13800000006', sex: '1' },
  { userId: '7', userName: 'hr', nickName: '人事', status: '0', deptId: '106', email: 'hr@hai-erp.local', phonenumber: '13800000007', sex: '1' },
  { userId: '8', userName: 'boss', nickName: '厂长', status: '0', deptId: '100', email: 'boss@hai-erp.local', phonenumber: '13800000008', sex: '0' },
]

const demoMenus: SysMenu[] = [
  { menuId: '90200', menuName: '系统管理', parentId: '0', orderNum: 30, path: 'system', menuType: 'M', status: '0', icon: 'system' },
  { menuId: '90201', menuName: '用户管理', parentId: '90200', orderNum: 1, path: 'user', component: 'system/user', menuType: 'C', status: '0', perms: 'system:user:list', icon: 'user' },
  { menuId: '90202', menuName: '角色管理', parentId: '90200', orderNum: 2, path: 'role', component: 'system/role', menuType: 'C', status: '0', perms: 'system:role:list', icon: 'peoples' },
  { menuId: '90203', menuName: '菜单管理', parentId: '90200', orderNum: 3, path: 'menu', component: 'system/menu', menuType: 'C', status: '0', perms: 'system:menu:list', icon: 'tree-table' },
  { menuId: '90204', menuName: '部门管理', parentId: '90200', orderNum: 4, path: 'dept', component: 'system/dept', menuType: 'C', status: '0', perms: 'system:dept:list', icon: 'tree' },
  { menuId: '90210', menuName: '岗位管理', parentId: '90200', orderNum: 6, path: 'post', component: 'system/post', menuType: 'C', status: '0', perms: 'system:post:list', icon: 'post' },
  { menuId: '90000', menuName: '海岚 ERP', parentId: '0', orderNum: 20, path: 'erp', menuType: 'M', status: '0', icon: 'dashboard' },
  { menuId: '90001', menuName: '工作台', parentId: '90000', orderNum: 1, path: 'dashboard', component: 'erp/dashboard', menuType: 'C', status: '0', perms: 'dashboard:overview:list', icon: 'dashboard' },
  { menuId: '90002', menuName: '销售订单', parentId: '90000', orderNum: 2, path: 'orders', component: 'erp/orders', menuType: 'C', status: '0', perms: 'sd:order:list', icon: 'list' },
]

const demoMenuStore = new Map<string, SysMenu>(demoMenus.map(menu => [menu.menuId, { ...menu }]))

function deptName(deptId?: string | number | null): string {
  if (deptId === undefined || deptId === null || deptId === '') return '—'
  return demoDepartments.find(dept => dept.deptId === String(deptId))?.deptName || '—'
}

function roleNames(userId: string): string {
  const roleKeys: Record<string, string> = { '1': 'admin', '2': 'sales', '3': 'planner', '4': 'production', '5': 'wh', '6': 'finance', '7': 'hr', '8': 'boss' }
  const role = demoRoles.find(item => item.roleKey === roleKeys[userId])
  return role?.roleName || '—'
}
const demoSystemApi = {
  users: async (query: UserQuery) => {
    const filtered = demoUsers.filter(user =>
      (!query.userName || user.userName.includes(query.userName)) &&
      (!query.nickName || user.nickName.includes(query.nickName)) &&
      (!query.phoneNumber || (user.phonenumber || '').includes(query.phoneNumber)) &&
      (query.status === undefined || query.status === '' || user.status === query.status) &&
      (query.deptId === undefined || query.deptId === '' || String(user.deptId) === String(query.deptId)))
    const size = query.pageSize || 10
    return {
      rows: filtered.slice((query.pageNum - 1) * size, query.pageNum * size).map(user => ({
        ...user,
        dept: user.deptId ? { deptId: String(user.deptId), deptName: deptName(user.deptId) } : null,
        roleNames: roleNames(user.userId),
      })),
      total: filtered.length,
    }
  },
  user: async (userId: string) => {
    const user = demoUsers.find(item => item.userId === userId)
    if (!user) throw new Error('用户不存在')
    return { ...user, dept: user.deptId ? { deptId: String(user.deptId), deptName: deptName(user.deptId) } : null }
  },
  userRoles: async (userId: string): Promise<AuthRoleResult> => {
    const user = demoUsers.find(item => item.userId === userId)
    if (!user) throw new Error('用户不存在')
    const roleKeyByUser: Record<string, string> = { '1': 'admin', '2': 'sales', '3': 'planner', '4': 'production', '5': 'wh', '6': 'finance', '7': 'hr', '8': 'boss' }
    const role = demoRoles.find(item => item.roleKey === roleKeyByUser[userId])
    return {
      user: { ...user, roles: role ? [role] : [] },
      roles: demoRoles.filter(item => item.roleKey !== 'admin'),
    }
  },
  saveUser: async (form: UserForm) => {
    if (!form.userName.trim()) throw new Error('登录账号不能为空')
    if (!form.nickName.trim()) throw new Error('用户昵称不能为空')
    if (!form.userId && demoUsers.some(user => user.userName === form.userName)) throw new Error(`登录账号 ${form.userName} 已存在`)
    return { userId: form.userId || String(demoUsers.length + 1) }
  },
  removeUsers: async (userIds: string[]) => {
    if (userIds.includes('1')) throw new Error('当前用户不能删除')
  },
  resetPassword: async (_userId: string, _password: string) => undefined,
  changeUserStatus: async (_userId: string, _status: string) => undefined,

  roles: async (query: RoleQuery) => {
    const filtered = demoRoles.filter(role =>
      (!query.roleName || role.roleName.includes(query.roleName)) &&
      (!query.roleKey || role.roleKey.includes(query.roleKey)) &&
      (query.status === undefined || query.status === '' || role.status === query.status))
    const size = query.pageSize || 10
    return { rows: filtered.slice((query.pageNum - 1) * size, query.pageNum * size), total: filtered.length }
  },
  allRoles: async () => demoRoles,
  saveRole: async (form: RoleForm) => {
    if (!form.roleName.trim()) throw new Error('角色名称不能为空')
    if (!form.roleKey.trim()) throw new Error('权限字符不能为空')
    return { roleId: form.roleId || String(demoRoles.length + 1) }
  },
  removeRoles: async (roleIds: string[]) => {
    if (roleIds.includes('1')) throw new Error('管理员角色不允许删除')
  },
  roleMenus: async (_roleId: string): Promise<RoleMenuResult> => ({
    checkedKeys: demoMenus.filter(menu => menu.parentId !== '0').map(menu => menu.menuId),
    menus: demoMenus,
  }),

  menus: async (query: MenuQuery) => {
    const filtered = demoMenus.filter(menu =>
      (!query.menuName || menu.menuName.includes(query.menuName)) &&
      (query.status === undefined || query.status === '' || menu.status === query.status))
    return filtered
  },
  saveMenu: async (form: MenuForm) => {
    if (!form.menuName.trim()) throw new Error('菜单名称不能为空')
    if (form.menuId) {
      const target = demoMenuStore.get(form.menuId)
      if (target) Object.assign(target, form)
      return { menuId: form.menuId }
    }
    const menuId = String(Math.max(...[...demoMenuStore.keys()].map(Number)) + 1)
    demoMenuStore.set(menuId, { ...form, menuId })
    return { menuId }
  },
  removeMenu: async (menuId: string) => {
    if (demoMenuStore.get(menuId)?.children?.length) throw new Error('存在子菜单,不允许删除')
    demoMenuStore.delete(menuId)
  },

  departments: async (query: DeptQuery) => demoDepartments.filter(dept =>
    (!query.deptName || dept.deptName.includes(query.deptName)) &&
    (query.status === undefined || query.status === '' || dept.status === query.status)),
  saveDept: async (form: DeptForm) => {
    if (!form.deptName.trim()) throw new Error('部门名称不能为空')
    return { deptId: form.deptId || String(demoDepartments.length + 100) }
  },
  removeDept: async (deptId: string) => {
    if (demoDepartments.some(dept => dept.parentId === deptId)) throw new Error('存在下级部门,不允许删除')
  },

  posts: async () => demoPosts,
  savePost: async (form: PostForm) => {
    if (!form.postCode.trim()) throw new Error('岗位编码不能为空')
    if (!form.postName.trim()) throw new Error('岗位名称不能为空')
    return { postId: form.postId || String(demoPosts.length + 1) }
  },
  removePost: async (postId: string) => {
    const post = demoPosts.find(item => item.postId === postId)
    if (post && demoUsers.length) throw new Error('岗位下仍有用户,无法删除')
  },
}

// ---------------------------------------------------------------
// 对外接口
// ---------------------------------------------------------------

export const systemApi = {
  // ---------------- 用户 ----------------
  users: async (query: UserQuery) => {
    if (isDemoMode()) return demoSystemApi.users(query)
    const result = (await http.get<Page<SysUser>>('/system/user/list', { params: query })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  user: async (userId: string) => {
    if (isDemoMode()) return demoSystemApi.user(userId)
    return payload<SysUser>(http.get(`/system/user/${encodeURIComponent(userId)}`))
  },
  createUser: async (form: UserForm) => {
    if (isDemoMode()) return demoSystemApi.saveUser(form)
    return payload<{ userId: string }>(http.post('/system/user', form))
  },
  updateUser: async (form: UserForm) => {
    if (isDemoMode()) return demoSystemApi.saveUser(form)
    return payload<{ userId: string }>(http.put('/system/user', form))
  },
  removeUsers: async (userIds: string[]) => {
    if (isDemoMode()) return demoSystemApi.removeUsers(userIds)
    return payload<number>(http.delete(`/system/user/${userIds.join(',')}`))
  },
  resetPassword: async (userId: string, password: string) => {
    if (isDemoMode()) return demoSystemApi.resetPassword(userId, password)
    return payload<void>(http.put('/system/user/resetPwd', { userId, password }))
  },
  changeUserStatus: async (userId: string, status: string) => {
    if (isDemoMode()) return demoSystemApi.changeUserStatus(userId, status)
    return payload<void>(http.put('/system/user/changeStatus', { userId, status }))
  },
  userAuthRoles: async (userId: string) => {
    if (isDemoMode()) return demoSystemApi.userRoles(userId)
    const result = (await http.get<Envelope & Partial<AuthRoleResult>>(`/system/user/authRole/${encodeURIComponent(userId)}`)).data
    if (!result.user || !result.roles) throw new Error('服务端未返回授权信息')
    return { user: result.user, roles: result.roles }
  },
  grantUserRoles: async (userId: string, roleIds: string[]) => {
    if (isDemoMode()) return undefined
    return payload<void>(http.put('/system/user/authRole', null, { params: { userId, roleIds: roleIds.join(',') } }))
  },

  // ---------------- 角色 ----------------
  roles: async (query: RoleQuery) => {
    if (isDemoMode()) return demoSystemApi.roles(query)
    const result = (await http.get<Page<SysRole>>('/system/role/list', { params: query })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  allRoles: async (): Promise<SysRole[]> => {
    if (isDemoMode()) return demoSystemApi.allRoles()
    return payload<SysRole[]>(http.get('/system/role/optionselect'))
  },
  createRole: async (form: RoleForm) => {
    if (isDemoMode()) return demoSystemApi.saveRole(form)
    return payload<{ roleId: string }>(http.post('/system/role', form))
  },
  updateRole: async (form: RoleForm) => {
    if (isDemoMode()) return demoSystemApi.saveRole(form)
    return payload<void>(http.put('/system/role', form))
  },
  removeRoles: async (roleIds: string[]) => {
    if (isDemoMode()) return demoSystemApi.removeRoles(roleIds)
    return payload<number>(http.delete(`/system/role/${roleIds.join(',')}`))
  },
  changeRoleStatus: async (roleId: string, status: string) => {
    if (isDemoMode()) return undefined
    return payload<void>(http.put('/system/role/changeStatus', { roleId, status }))
  },
  roleMenus: async (roleId: string) => {
    if (isDemoMode()) return demoSystemApi.roleMenus(roleId)
    const result = (await http.get<Envelope & Partial<RoleMenuResult>>(`/system/menu/roleMenuTreeselect/${encodeURIComponent(roleId)}`)).data
    if (!result.menus) throw new Error('服务端未返回菜单信息')
    return { menus: result.menus, checkedKeys: result.checkedKeys || [] }
  },
  grantRoleMenus: async (roleId: string, menuIds: string[]) => {
    if (isDemoMode()) return undefined
    return payload<void>(http.put('/system/role', { roleId, menuIds }))
  },

  // ---------------- 菜单 ----------------
  menus: async (query: MenuQuery = {}) => {
    if (isDemoMode()) return demoSystemApi.menus(query)
    return payload<SysMenu[]>(http.get('/system/menu/list', { params: query }))
  },
  createMenu: async (form: MenuForm) => {
    if (isDemoMode()) return demoSystemApi.saveMenu(form)
    return payload<{ menuId: string }>(http.post('/system/menu', form))
  },
  updateMenu: async (form: MenuForm) => {
    if (isDemoMode()) return demoSystemApi.saveMenu(form)
    return payload<void>(http.put('/system/menu', form))
  },
  removeMenu: async (menuId: string) => {
    if (isDemoMode()) return demoSystemApi.removeMenu(menuId)
    return payload<number>(http.delete(`/system/menu/${encodeURIComponent(menuId)}`))
  },

  // ---------------- 部门 ----------------
  departments: async (query: DeptQuery = {}) => {
    if (isDemoMode()) return demoSystemApi.departments(query)
    return payload<SysDept[]>(http.get('/system/dept/list', { params: query }))
  },
  createDept: async (form: DeptForm) => {
    if (isDemoMode()) return demoSystemApi.saveDept(form)
    return payload<{ deptId: string }>(http.post('/system/dept', form))
  },
  updateDept: async (form: DeptForm) => {
    if (isDemoMode()) return demoSystemApi.saveDept(form)
    return payload<void>(http.put('/system/dept', form))
  },
  removeDept: async (deptId: string) => {
    if (isDemoMode()) return demoSystemApi.removeDept(deptId)
    return payload<number>(http.delete(`/system/dept/${encodeURIComponent(deptId)}`))
  },

  // ---------------- 岗位 ----------------
  posts: async (): Promise<SysPost[]> => {
    if (isDemoMode()) return demoSystemApi.posts()
    return payload<SysPost[]>(http.get('/system/post/list'))
  },
  createPost: async (form: PostForm) => {
    if (isDemoMode()) return demoSystemApi.savePost(form)
    return payload<{ postId: string }>(http.post('/system/post', form))
  },
  updatePost: async (form: PostForm) => {
    if (isDemoMode()) return demoSystemApi.savePost(form)
    return payload<void>(http.put('/system/post', form))
  },
  removePost: async (postId: string) => {
    if (isDemoMode()) return demoSystemApi.removePost(postId)
    return payload<number>(http.delete(`/system/post/${encodeURIComponent(postId)}`))
  },
}

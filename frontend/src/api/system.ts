import { http, pageTotal, payload, type Page } from './client'

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

// ---------------------------------------------------------------
// 对外接口
// ---------------------------------------------------------------

export const systemApi = {
  // ---------------- 用户 ----------------
  users: async (query: UserQuery) => {
    const result = (await http.get<Page<SysUser>>('/system/user/list', { params: query })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  user: async (userId: string) => {
    return payload<SysUser>(http.get(`/system/user/${encodeURIComponent(userId)}`))
  },
  createUser: async (form: UserForm) => {
    return payload<{ userId: string }>(http.post('/system/user', form))
  },
  updateUser: async (form: UserForm) => {
    return payload<{ userId: string }>(http.put('/system/user', form))
  },
  removeUsers: async (userIds: string[]) => {
    return payload<number>(http.delete(`/system/user/${userIds.join(',')}`))
  },
  resetPassword: async (userId: string, password: string) => {
    return payload<void>(http.put('/system/user/resetPwd', { userId, password }))
  },
  changeUserStatus: async (userId: string, status: string) => {
    return payload<void>(http.put('/system/user/changeStatus', { userId, status }))
  },
  userAuthRoles: async (userId: string) => {
    const result = (await http.get<Envelope & Partial<AuthRoleResult>>(`/system/user/authRole/${encodeURIComponent(userId)}`)).data
    if (!result.user || !result.roles) throw new Error('服务端未返回授权信息')
    return { user: result.user, roles: result.roles }
  },
  grantUserRoles: async (userId: string, roleIds: string[]) => {
    return payload<void>(http.put('/system/user/authRole', null, { params: { userId, roleIds: roleIds.join(',') } }))
  },

  // ---------------- 角色 ----------------
  roles: async (query: RoleQuery) => {
    const result = (await http.get<Page<SysRole>>('/system/role/list', { params: query })).data
    return { rows: result.rows, total: pageTotal(result.total) }
  },
  allRoles: async (): Promise<SysRole[]> => {
    return payload<SysRole[]>(http.get('/system/role/optionselect'))
  },
  createRole: async (form: RoleForm) => {
    return payload<{ roleId: string }>(http.post('/system/role', form))
  },
  updateRole: async (form: RoleForm) => {
    return payload<void>(http.put('/system/role', form))
  },
  removeRoles: async (roleIds: string[]) => {
    return payload<number>(http.delete(`/system/role/${roleIds.join(',')}`))
  },
  changeRoleStatus: async (roleId: string, status: string) => {
    return payload<void>(http.put('/system/role/changeStatus', { roleId, status }))
  },
  roleMenus: async (roleId: string) => {
    const result = (await http.get<Envelope & Partial<RoleMenuResult>>(`/system/menu/roleMenuTreeselect/${encodeURIComponent(roleId)}`)).data
    if (!result.menus) throw new Error('服务端未返回菜单信息')
    return { menus: result.menus, checkedKeys: result.checkedKeys || [] }
  },
  grantRoleMenus: async (roleId: string, menuIds: string[]) => {
    return payload<void>(http.put('/system/role', { roleId, menuIds }))
  },

  // ---------------- 菜单 ----------------
  menus: async (query: MenuQuery = {}) => {
    return payload<SysMenu[]>(http.get('/system/menu/list', { params: query }))
  },
  createMenu: async (form: MenuForm) => {
    return payload<{ menuId: string }>(http.post('/system/menu', form))
  },
  updateMenu: async (form: MenuForm) => {
    return payload<void>(http.put('/system/menu', form))
  },
  removeMenu: async (menuId: string) => {
    return payload<number>(http.delete(`/system/menu/${encodeURIComponent(menuId)}`))
  },

  // ---------------- 部门 ----------------
  departments: async (query: DeptQuery = {}) => {
    return payload<SysDept[]>(http.get('/system/dept/list', { params: query }))
  },
  createDept: async (form: DeptForm) => {
    return payload<{ deptId: string }>(http.post('/system/dept', form))
  },
  updateDept: async (form: DeptForm) => {
    return payload<void>(http.put('/system/dept', form))
  },
  removeDept: async (deptId: string) => {
    return payload<number>(http.delete(`/system/dept/${encodeURIComponent(deptId)}`))
  },

  // ---------------- 岗位 ----------------
  posts: async (): Promise<SysPost[]> => {
    return payload<SysPost[]>(http.get('/system/post/list'))
  },
  createPost: async (form: PostForm) => {
    return payload<{ postId: string }>(http.post('/system/post', form))
  },
  updatePost: async (form: PostForm) => {
    return payload<void>(http.put('/system/post', form))
  },
  removePost: async (postId: string) => {
    return payload<number>(http.delete(`/system/post/${encodeURIComponent(postId)}`))
  },
}

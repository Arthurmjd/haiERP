import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { http, type Envelope } from '../api/client'
import { recognizedRoles, roleProfiles, type RoleKey } from '../roles'
import { clearToken, getToken, setToken } from '../utils/auth'

interface UserInfo extends Envelope { user: { userName: string; nickName?: string }; roles: string[]; permissions: string[] }
interface Captcha extends Envelope { captchaEnabled?: boolean; img?: string; uuid?: string }

export const useUserStore = defineStore('user', () => {
  const token = ref(getToken())
  const name = ref('')
  const roles = ref<string[]>([])
  const permissions = ref<string[]>([])
  const ready = ref(false)
  const selectedRole = ref<RoleKey | null>(null)
  const availableRoles = computed(() => recognizedRoles(roles.value))
  const activeRole = computed<RoleKey | null>(() => {
    if (selectedRole.value && availableRoles.value.includes(selectedRole.value)) return selectedRole.value
    return availableRoles.value[0] ?? null
  })
  const emptyProfile = { label: '未分配角色', heading: '工作台', focus: '请联系管理员分配角色', navigation: ['/'] }
  const profile = computed(() => activeRole.value ? roleProfiles[activeRole.value] : emptyProfile)

  async function captcha() { return (await http.get<Captcha>('/captchaImage')).data }

  async function login(username: string, password: string, code = '', uuid = '') {
    const result = (await http.post<Envelope & { token: string }>('/login', { username, password, code, uuid })).data
    if (!result.token) throw new Error('登录响应缺少 Token')
    token.value = result.token
    setToken(result.token)
    ready.value = false
  }

  async function load() {
    if (!token.value) throw new Error('未登录')
    const info = (await http.get<UserInfo>('/getInfo')).data
    name.value = info.user.nickName || info.user.userName
    roles.value = info.roles || []
    permissions.value = info.permissions || []
    if (!availableRoles.value.includes(selectedRole.value as RoleKey)) selectedRole.value = null
    ready.value = true
  }

  function permissionMatches(granted: string, required: string): boolean {
    if (granted === required) return true
    if (!granted.includes('*')) return false
    const pattern = '^' + granted.split('*').map((seg) => seg.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')).join('.*') + '$'
    return new RegExp(pattern).test(required)
  }

  function can(permission: string) {
    return permissions.value.some((granted) => permissionMatches(granted, permission))
  }
  const canUseAi = computed(() => can('dashboard:overview:list') && [
    'sd:order:list', 'st:inventory:list', 'pu:suggestion:list', 'dashboard:styleCost:list', 'dashboard:ontime:list',
  ].some(can))

  function switchRole(role: RoleKey) {
    if (!availableRoles.value.includes(role)) return
    selectedRole.value = role
  }

  function logout() {
    token.value = ''
    name.value = ''
    roles.value = []
    permissions.value = []
    ready.value = false
    selectedRole.value = null
    clearToken()
  }

  return { token, name, roles, permissions, ready, availableRoles, activeRole, profile, can, canUseAi, switchRole, captcha, login, load, logout }
})

import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { http, payload, type Envelope } from '../api/client'
import { enterDemoMode, isDemoMode, leaveDemoMode } from '../api/demo'
import { demoCan, recognizedRoles, roleKeys, roleProfiles, type RoleKey } from '../roles'

interface UserInfo extends Envelope { user: { userName: string; nickName?: string }; roles: string[]; permissions: string[] }
interface Captcha extends Envelope { captchaEnabled?: boolean; img?: string; uuid?: string }

export const useUserStore = defineStore('user', () => {
  const savedDemoRole = sessionStorage.getItem('hai-erp-demo-role')
  const token = ref(localStorage.getItem('hai-erp-token') || '')
  const name = ref(isDemoMode() ? '演示访客' : '')
  const roles = ref<string[]>([])
  const permissions = ref<string[]>([])
  const menus = ref<unknown[]>([])
  const ready = ref(false)
  const demo = ref(isDemoMode())
  const demoRole = ref<RoleKey>(roleKeys.includes(savedDemoRole as RoleKey) ? savedDemoRole as RoleKey : 'admin')
  const selectedRole = ref<RoleKey | null>(null)
  const availableRoles = computed(() => demo.value ? [...roleKeys] : recognizedRoles(roles.value))
  const activeRole = computed<RoleKey>(() => {
    if (demo.value) return demoRole.value
    return selectedRole.value && availableRoles.value.includes(selectedRole.value) ? selectedRole.value : availableRoles.value[0] || 'admin'
  })
  const profile = computed(() => roleProfiles[activeRole.value])

  async function captcha() { return (await http.get<Captcha>('/captchaImage')).data }

  async function login(username: string, password: string, code = '', uuid = '') {
    const result = (await http.post<Envelope & { token: string }>('/login', { username, password, code, uuid })).data
    if (!result.token) throw new Error('登录响应缺少 Token')
    token.value = result.token
    demo.value = false
    leaveDemoMode()
    localStorage.setItem('hai-erp-token', result.token)
    ready.value = false
  }

  async function load() {
    if (!token.value) throw new Error('未登录')
    const info = (await http.get<UserInfo>('/getInfo')).data
    name.value = info.user.nickName || info.user.userName
    roles.value = info.roles || []
    permissions.value = info.permissions || []
    menus.value = await payload<unknown[]>(http.get('/getRouters'))
    if (!availableRoles.value.includes(selectedRole.value as RoleKey)) selectedRole.value = null
    ready.value = true
  }

  function can(permission: string) {
    return demo.value ? demoCan(activeRole.value, permission) : permissions.value.includes('*:*:*') || permissions.value.includes(permission)
  }
  const canUseAi = computed(() => can('dashboard:overview:list') && [
    'sd:order:list', 'st:inventory:list', 'pu:suggestion:list', 'dashboard:styleCost:list', 'dashboard:ontime:list',
  ].some(can))

  function switchRole(role: RoleKey) {
    if (!availableRoles.value.includes(role)) return
    if (demo.value) {
      demoRole.value = role
      sessionStorage.setItem('hai-erp-demo-role', role)
    } else selectedRole.value = role
  }

  function enterDemo() {
    logout()
    enterDemoMode()
    demo.value = isDemoMode()
    name.value = '演示访客'
    sessionStorage.setItem('hai-erp-demo-role', demoRole.value)
    ready.value = true
  }

  function logout() {
    demo.value = false
    leaveDemoMode()
    token.value = ''
    name.value = ''
    roles.value = []
    permissions.value = []
    menus.value = []
    ready.value = false
    selectedRole.value = null
    localStorage.removeItem('hai-erp-token')
  }

  return { token, name, roles, permissions, menus, ready, demo, availableRoles, activeRole, profile, can, canUseAi, switchRole, captcha, login, load, enterDemo, logout }
})

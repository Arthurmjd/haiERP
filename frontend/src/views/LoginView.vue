<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../stores/user'

const user = useUserStore()
const route = useRoute()
const router = useRouter()
const username = ref('')
const password = ref('')
const code = ref('')
const uuid = ref('')
const captchaImage = ref('')
const captchaEnabled = ref(false)
const busy = ref(false)
const loginError = ref('')

async function refreshCaptcha() {
  try {
    const result = await user.captcha()
    loginError.value = ''
    captchaEnabled.value = result.captchaEnabled !== false
    captchaImage.value = result.img ? `data:image/png;base64,${result.img}` : ''
    uuid.value = result.uuid || ''
    code.value = ''
  } catch { loginError.value = '登录服务暂不可用' }
}

async function submit() {
  if (!username.value.trim() || !password.value) return ElMessage.warning('请输入账号和密码')
  if (captchaEnabled.value && !code.value.trim()) return ElMessage.warning('请输入验证码')
  busy.value = true
  try {
    await user.login(username.value.trim(), password.value, code.value.trim(), uuid.value)
    await user.load()
    const redirect = typeof route.query.redirect === 'string' && route.query.redirect.startsWith('/') && !route.query.redirect.startsWith('//') ? route.query.redirect : '/'
    await router.replace(redirect)
  } catch (error) {
    user.logout()
    ElMessage.error((error as Error).message)
    await refreshCaptcha()
  } finally { busy.value = false }
}
onMounted(refreshCaptcha)

</script>

<template>
  <div class="login-page">
    <section class="login-side">
      <div class="brand"><span class="brand-mark">H</span><span class="brand-copy"><strong>海岚 ERP</strong><small>运营工作台</small></span></div>
      <div><h1>从订单到交付，掌握每一步。</h1><p>销售、缺料与库存统一协作</p></div>
      <small>HAI ERP</small>
    </section>
    <main class="login-main"><form class="login-box" @submit.prevent="submit">
      <h2>登录工作台</h2><p>使用平台账号继续</p>
      <el-alert v-if="loginError" :title="loginError" type="warning" :closable="false" style="margin-bottom:16px" />
      <el-input v-model="username" placeholder="账号" autocomplete="username" size="large" aria-label="账号" />
      <el-input v-model="password" placeholder="密码" type="password" show-password autocomplete="current-password" size="large" aria-label="密码" />
      <div v-if="captchaEnabled" class="captcha-row"><el-input v-model="code" placeholder="验证码" size="large" aria-label="验证码" /><img v-if="captchaImage" class="captcha-image" :src="captchaImage" alt="点击刷新验证码" @click="refreshCaptcha" /></div>
      <el-button class="login-submit" type="primary" size="large" native-type="submit" :loading="busy">登录</el-button>
    </form></main>
  </div>
</template>

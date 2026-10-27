<script setup lang="ts">
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { systemApi, type SysUser } from '../api/system'

const visible = defineModel<boolean>({ required: true })
const props = defineProps<{ target: SysUser | null }>()

const password = ref('')
const submitting = ref(false)

watch(visible, (open) => { if (open) password.value = '' })

async function submit() {
  if (!props.target) return
  if (password.value.length < 5) return ElMessage.warning('密码长度不能少于 5 位')
  submitting.value = true
  try {
    await systemApi.resetPassword(props.target.userId, password.value)
    ElMessage.success('密码已重置')
    visible.value = false
  } catch (e) {
    ElMessage.error((e as Error).message)
  } finally {
    submitting.value = false
  }
}
</script>
<template>
  <el-dialog v-model="visible" title="重置密码" width="420px">
    <p class="muted" style="margin-top: 0">将为「{{ target?.nickName }}（{{ target?.userName }}）」设置新密码。</p>
    <el-input v-model="password" type="password" show-password placeholder="输入新密码，不少于 5 位" />
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="submitting" @click="submit">确认重置</el-button>
    </template>
  </el-dialog>
</template>
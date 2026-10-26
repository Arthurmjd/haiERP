<script setup lang="ts">
import { computed } from 'vue'
import { useUserStore } from '../stores/user'
import DashboardView from './DashboardView.vue'
import RoleDashboardView from './RoleDashboardView.vue'

const user = useUserStore()
const managementView = computed(() => user.ready && (user.activeRole === 'admin' || user.activeRole === 'boss'))
</script>

<template>
  <DashboardView v-if="managementView" />
  <RoleDashboardView v-else-if="user.ready" />
</template>

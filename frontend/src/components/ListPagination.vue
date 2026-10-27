<script setup lang="ts">
defineProps<{ total: number; page: number }>()
const emit = defineEmits<{ change: [page: number] }>()
</script>
<template>
  <div class="pagination">
    <el-pagination
      :current-page="page"
      background
      layout="prev, pager, next, total"
      :total="total"
      :page-size="10"
      @current-change="(p: number) => emit('change', p)"
    />
  </div>
</template>
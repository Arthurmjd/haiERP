<script setup lang="ts">import { useRouter } from 'vue-router'; const router = useRouter()</script>
<template><div class="panel empty-state"><h1>没有访问权限</h1><p>请联系管理员开通此业务权限。</p><el-button type="primary" style="margin-top:18px" @click="router.push('/')">返回工作台</el-button></div></template>

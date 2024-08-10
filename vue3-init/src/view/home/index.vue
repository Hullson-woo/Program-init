<template>
  <div>这是首页</div>
  <div style="margin-top: 10px">
    <button @click="toUser()">用户管理</button>
  </div>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router'
  const router = useRouter()
  const toUser = () => {
    router.push({
      path: '/user',
    })
  }

  const title = import.meta.env.VITE_APP_TITLE
  console.log(title)
</script>

<style scoped></style>

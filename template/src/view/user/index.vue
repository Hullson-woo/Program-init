<template>
  <div>用户管理</div>
  {{ username }}
  <div>
    <button @click="updateUser">修改用户为test</button>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue'
  import { userStore } from '@/store/user'
  const store = userStore()

  let username = ref('')

  const getUsername = () => {
    username.value = store.getUsername
  }
  getUsername()

  const updateUser = () => {
    store.setUsername('test')
    username.value = store.getUsername
  }
</script>

<style scoped></style>

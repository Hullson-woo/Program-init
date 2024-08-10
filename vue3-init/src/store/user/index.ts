import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
  state: () => ({
    username: 'admin',
  }),

  getters: {
    getUsername: (state) => state.username,
  },

  actions: {
    setUsername(username: string) {
      this.username = username
    },
  },
})

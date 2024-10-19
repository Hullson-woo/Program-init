import { createApp } from 'vue'
import App from './App.vue'
// 挂载router
import router from '@/router/index.ts'// 引入router
import { createPinia } from 'pinia' // 引入pinia



const app = createApp(App)
app.use(router)
app.use(createPinia())
app.mount('#app')

import { createApp } from 'vue'
import App from './App.vue'
// 引入路由器
import router from '@/router/index'
// 引入pinia
import pinia from './store'
// 引入全局样式
import './style.css'

const app = createApp(App)
app.use(router)
app.use(pinia)
app.mount('#app')

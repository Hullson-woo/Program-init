import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/home',
    name: 'home',
    meta: {
      title: '首页',
      isEnable: true,
    },
    component: () => import('@/view/home/index.vue'),
  },
  {
    path: '/user',
    name: 'user',
    meta: {
      title: '用户管理',
      isEnable: true,
    },
    component: () => import('@/view/user/index.vue'),
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router

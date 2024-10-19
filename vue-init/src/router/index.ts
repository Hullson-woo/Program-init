import { createRouter, createWebHistory } from "vue-router"; // 导入路由
// 1. 配置路由  lintaibai--进来时候的初始页面
const routes= [
    {
        path: "/", // 默认路由 home页面
        component: () => import("../views/index.vue"),
    },
];
// 2.返回一个 router 实列，为函数，配置 history 模式
const router = createRouter({
    history: createWebHistory(),
    routes,
});

// 3.导出路由   去 main.ts 注册 router.ts
export default router

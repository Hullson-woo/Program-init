import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import eslint from 'vite-plugin-eslint'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue(), eslint()],
  resolve: {
    alias: {
      // 配置 "@" 指向 'src' 目录
      '@': resolve(__dirname, 'src'),
    },
  },
  // 配置打包路径
  base: './',
  server: {
    // 配置启动端口号
    port: 31088,
    // 配置服务启动时是否自动打开浏览器
    open: false,
    // 配置跨域
    cors: false,

    // 配置代理
    // proxy: {
    // 	'/api': {
    // 		target: 'http://localhost:8080',
    // 		changeOrigin: true,
    // 		secure: false,
    // 		rewrite: (path) => path.replace('/api', '/'),
    // 	},
    // },
  },
})

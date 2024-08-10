import axios, { AxiosResponse } from 'axios'
import { userStore } from '@/store/user'

// 创建axios示例
const request = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_URL,
  timeout: 60000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  },
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // todo 修改为获取token，此处只是示例代码
    const username = userStore().getUsername
    if (username != '') {
      config.headers!['Authorization'] = username
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const { code, msg } = response.data
    if (code === 200) {
      return response.data
    } else {
      // todo 后端异常处理
      return Promise.reject(new Error(msg || 'error'))
    }
  },
  (error) => {
    // todo 响应状态码非200异常处理
    return Promise.reject(error.message)
  },
)

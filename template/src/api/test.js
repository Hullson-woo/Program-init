import request from '@/request/index.js'
const url = `${import.meta.env.VITE_APP_BASE_URL}/advisor`

export function testApi() {
  return request({
    url: `${url}/test`,
    method: 'get',
  })
}

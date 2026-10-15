import axios from 'axios'

export interface Envelope<T = unknown> {
  code: number
  msg: string
  data?: T
  [key: string]: unknown
}

export interface Page<T> extends Envelope {
  rows: T[]
  total: number | string
}

export const http = axios.create({ baseURL: '/api', timeout: 15000 })

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('hai-erp-token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

http.interceptors.response.use((response) => {
  const value = response.data as Envelope
  if (!value || value.code !== 200) {
    if (value?.code === 401) {
      localStorage.removeItem('hai-erp-token')
      window.dispatchEvent(new Event('erp-unauthorized'))
    }
    throw new Error(value?.msg || '请求失败')
  }
  return response
}, (error) => {
  if (error.response?.status === 401) {
    localStorage.removeItem('hai-erp-token')
    window.dispatchEvent(new Event('erp-unauthorized'))
  }
  if (error.response?.status === 403) throw new Error('没有权限，请联系管理员授权')
  throw new Error(error.response?.data?.msg || error.message || '网络连接失败')
})

export async function payload<T>(promise: Promise<{ data: Envelope<T> }>): Promise<T> {
  const response = (await promise).data
  if (response.data === undefined) throw new Error('服务端未返回数据')
  return response.data
}

export function pageTotal(value: number | string): number {
  const total = typeof value === 'string' ? Number(value) : value
  if (!Number.isSafeInteger(total) || total < 0) throw new Error('分页总数无效')
  return total
}

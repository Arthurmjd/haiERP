import axios from 'axios'
import { clearToken, getToken } from '../utils/auth'
import { ApiError } from '../utils/api-error'

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
  const token = getToken()
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

http.interceptors.response.use((response) => {
  const value = response.data as Envelope
  if (!value || value.code !== 200) {
    if (value?.code === 401) {
      clearToken()
      window.dispatchEvent(new Event('erp-unauthorized'))
    }
    throw new ApiError(value?.msg || '请求失败', { status: response.status, code: value?.code, payload: value })
  }
  return response
}, (error) => {
  const status = error.response?.status as number | undefined
  if (status === 401) {
    clearToken()
    window.dispatchEvent(new Event('erp-unauthorized'))
  }
  if (status === 403) throw new ApiError('没有权限，请联系管理员授权', { status })
  const data = error.response?.data
  const msg = data && typeof data === 'object' && 'msg' in data && typeof data.msg === 'string'
    ? data.msg
    : error.message || '网络连接失败'
  throw new ApiError(msg, { status, code: typeof data?.code === 'number' ? data.code : undefined, payload: data })
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

/**
 * API 错误：保留 HTTP 状态码、业务码与原始载荷，便于调用处按 status 走不同处理。
 */
export class ApiError extends Error {
  readonly status?: number
  readonly code?: number
  readonly payload?: unknown

  constructor(message: string, options?: { status?: number; code?: number; payload?: unknown }) {
    super(message)
    this.name = 'ApiError'
    this.status = options?.status
    this.code = options?.code
    this.payload = options?.payload
  }
}
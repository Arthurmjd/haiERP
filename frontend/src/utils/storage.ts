/**
 * sessionStorage/localStorage 统一封装：集中 key 管理、JSON 读写容错。
 * key 参数不带命名空间前缀，内部统一加 'hai-erp-'。
 */
const NS = 'hai-erp-'

function fullKey(key: string): string {
  return key.startsWith(NS) ? key : NS + key
}

export function readSession(key: string): string | null {
  return sessionStorage.getItem(fullKey(key))
}

export function writeSession(key: string, value: string): void {
  sessionStorage.setItem(fullKey(key), value)
}

export function removeSession(key: string): void {
  sessionStorage.removeItem(fullKey(key))
}

/** JSON 读取，坏数据返回 fallback 而不是抛异常 */
export function readSessionJson<T>(key: string, fallback: T): T {
  try {
    const raw = readSession(key)
    return raw === null ? fallback : (JSON.parse(raw) as T)
  } catch {
    return fallback
  }
}

export function writeSessionJson(key: string, value: unknown): void {
  writeSession(key, JSON.stringify(value))
}
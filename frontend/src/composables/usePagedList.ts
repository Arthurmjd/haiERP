import { ref } from 'vue'
import { ElMessage } from 'element-plus'

export const LIST_PAGE_SIZE = 10

export interface PagedResult<T> {
  rows: T[]
  total: number
}

export function usePagedList<T>(fetcher: (pageNum: number, pageSize: number) => Promise<PagedResult<T>>) {
  const rows = ref<T[]>([])
  const total = ref(0)
  const page = ref(1)
  const loading = ref(false)
  const error = ref('')

  async function load() {
    loading.value = true
    error.value = ''
    try {
      const result = await fetcher(page.value, LIST_PAGE_SIZE)
      rows.value = result.rows
      total.value = result.total
    } catch (e) {
      error.value = (e as Error).message
      ElMessage.error(error.value)
    } finally {
      loading.value = false
    }
  }

  function search() {
    page.value = 1
    load()
  }

  function changePage(next: number) {
    page.value = next
    load()
  }

  return { rows, total, page, pageSize: LIST_PAGE_SIZE, loading, error, load, search, changePage }
}
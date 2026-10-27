import { http, payload, type Envelope } from './client'
import { isDemoMode } from './demo'

export interface WorkflowTodo {
  todoId: string
  todoType: string
  targetType: string
  targetId: string
  assigneeRole: string
  title: string
  path: string
  dueDate: string | null
  createTime: string
}

export interface PendingApproval {
  approvalId: string
  docType: string
  docId: string
  approvalType: string
  amount: number
  applicant: string
  createTime: string
}

export interface FinanceOverview {
  cashBalance: number
  monthIn: number
  monthOut: number
  receivableBalance: number
  payableBalance: number
  receivableOverdue: number
  payableOverdue: number
}

export interface FinanceAccount {
  id: string
  code: string
  name: string
  accountType: number
  openingBalance: number
  currentBalance: number
  status: number
  remark: string | null
}

export interface FinanceAccountDraft {
  accountNo: string
  accountName: string
  accountType: number
  openingBalance: number
  status: number
  remark: string
}

export interface CashTxn {
  id: string
  txnNo: string
  accountName: string
  direction: number
  amount: number
  bizType: number
  txnDate: string
  sourceType: string | null
  sourceId: string | null
  remark: string | null
}

export interface Receivable {
  id: string
  code: string
  customerName: string
  orderId: string
  amount: number
  receivedAmount: number
  balance: number
  dueDate: string
  status: number
  overdueDays: number
  agingBucket: string
}

export interface Payable {
  id: string
  code: string
  supplierName: string
  poId: string
  amount: number
  paidAmount: number
  balance: number
  dueDate: string
  status: number
  overdueDays: number
  agingBucket: string
}

export interface FinancePayment {
  id: string
  code: string
  direction: number
  bizType: number
  accountName: string
  amount: number
  payDate: string
  status: number
  remark: string | null
}

export interface PaymentLine {
  targetType: number
  targetId: string
  amount: number
}

export interface PaymentDraft {
  accountId: string
  direction: number
  bizType: number
  amount: number
  payDate: string
  lines: PaymentLine[]
  remark: string
  requestKey: string
}

export interface FinancePeriod {
  period: string
  status: number
  closedBy: string | null
  closedTime: string | null
}

const demoError = () => new Error('演示模式不支持资金操作，请连接后端')
const emptyOverview = (): FinanceOverview => ({
  cashBalance: 0, monthIn: 0, monthOut: 0,
  receivableBalance: 0, payableBalance: 0, receivableOverdue: 0, payableOverdue: 0,
})

export const financeApi = {
  // 待办 / 审批
  todos: () => isDemoMode() ? Promise.resolve<WorkflowTodo[]>([]) : payload<WorkflowTodo[]>(http.get('/erp/workflow/todos')),
  approvals: () => isDemoMode() ? Promise.resolve<PendingApproval[]>([]) : payload<PendingApproval[]>(http.get('/erp/workflow/approvals')),
  approvePurchase: async (docId: string, comment: string) => {
    if (isDemoMode()) throw demoError()
    await http.post<Envelope>(`/erp/pu/purchaseOrder/approve/${encodeURIComponent(docId)}`, { comment })
  },
  rejectPurchase: async (docId: string, comment: string) => {
    if (isDemoMode()) throw demoError()
    await http.post<Envelope>(`/erp/pu/purchaseOrder/reject/${encodeURIComponent(docId)}`, { comment })
  },

  // 资金总览 / 账户
  overview: () => isDemoMode() ? Promise.resolve(emptyOverview()) : payload<FinanceOverview>(http.get('/erp/finance/overview')),
  accounts: () => isDemoMode() ? Promise.resolve<FinanceAccount[]>([]) : payload<FinanceAccount[]>(http.get('/erp/finance/accounts')),
  createAccount: async (draft: FinanceAccountDraft) => {
    if (isDemoMode()) throw demoError()
    return payload<{ accountId: string }>(http.post('/erp/finance/accounts', draft))
  },
  updateAccount: async (id: string, draft: FinanceAccountDraft) => {
    if (isDemoMode()) throw demoError()
    return payload<{ accountId: string }>(http.put(`/erp/finance/accounts/${encodeURIComponent(id)}`, draft))
  },

  // 流水 / 应收应付
  cashTxns: () => isDemoMode() ? Promise.resolve<CashTxn[]>([]) : payload<CashTxn[]>(http.get('/erp/finance/cash')),
  receivables: () => isDemoMode() ? Promise.resolve<Receivable[]>([]) : payload<Receivable[]>(http.get('/erp/finance/receivables')),
  payables: () => isDemoMode() ? Promise.resolve<Payable[]>([]) : payload<Payable[]>(http.get('/erp/finance/payables')),

  // 收付款
  payments: () => isDemoMode() ? Promise.resolve<FinancePayment[]>([]) : payload<FinancePayment[]>(http.get('/erp/finance/payments')),
  createPayment: async (draft: PaymentDraft) => {
    if (isDemoMode()) throw demoError()
    return payload<{ paymentId: string }>(http.post('/erp/finance/payments', draft))
  },
  confirmPayment: async (id: string, requestKey: string) => {
    if (isDemoMode()) throw demoError()
    await http.post<Envelope>(`/erp/finance/payments/confirm/${encodeURIComponent(id)}`, { requestKey })
  },

  // 会计期间
  periods: () => isDemoMode() ? Promise.resolve<FinancePeriod[]>([]) : payload<FinancePeriod[]>(http.get('/erp/finance/periods')),
  closePeriod: async (period: string) => {
    if (isDemoMode()) throw demoError()
    await http.post<Envelope>('/erp/finance/period/close', { period })
  },
  reopenPeriod: async (period: string) => {
    if (isDemoMode()) throw demoError()
    await http.post<Envelope>('/erp/finance/period/reopen', { period })
  },
}

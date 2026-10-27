import { http, payload, type Envelope } from './client'

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

/** 账龄汇总（应收 / 应付 × 30/60/90 分段） */
export interface AgingSummaryRow {
  category: string
  categoryName: string
  notDue: number
  days1to30: number
  days31to60: number
  days61to90: number
  over90: number
  total: number
}

/** 月度现金流 */
export interface CashFlowMonth {
  period: string
  inflow: number
  outflow: number
  netFlow: number
}


export const financeApi = {
  // 待办 / 审批
  todos: () => payload<WorkflowTodo[]>(http.get('/erp/workflow/todos')),
  approvals: () => payload<PendingApproval[]>(http.get('/erp/workflow/approvals')),
  approvePurchase: async (docId: string, comment: string) => {
    await http.post<Envelope>(`/erp/pu/purchaseOrder/approve/${encodeURIComponent(docId)}`, { comment })
  },
  rejectPurchase: async (docId: string, comment: string) => {
    await http.post<Envelope>(`/erp/pu/purchaseOrder/reject/${encodeURIComponent(docId)}`, { comment })
  },

  // 资金总览 / 账户
  overview: () => payload<FinanceOverview>(http.get('/erp/finance/overview')),
  accounts: () => payload<FinanceAccount[]>(http.get('/erp/finance/accounts')),
  createAccount: async (draft: FinanceAccountDraft) => {
    return payload<{ accountId: string }>(http.post('/erp/finance/accounts', draft))
  },
  updateAccount: async (id: string, draft: FinanceAccountDraft) => {
    return payload<{ accountId: string }>(http.put(`/erp/finance/accounts/${encodeURIComponent(id)}`, draft))
  },

  // 流水 / 应收应付
  cashTxns: () => payload<CashTxn[]>(http.get('/erp/finance/cash')),
  receivables: () => payload<Receivable[]>(http.get('/erp/finance/receivables')),
  payables: () => payload<Payable[]>(http.get('/erp/finance/payables')),

  // 账龄汇总 / 月度现金流
  agingSummary: () => payload<AgingSummaryRow[]>(http.get('/erp/finance/aging-summary')),
  cashFlowMonthly: () => payload<CashFlowMonth[]>(http.get('/erp/finance/cash-flow-monthly')),

  // 收付款
  payments: () => payload<FinancePayment[]>(http.get('/erp/finance/payments')),
  createPayment: async (draft: PaymentDraft) => {
    return payload<{ paymentId: string }>(http.post('/erp/finance/payments', draft))
  },
  confirmPayment: async (id: string, requestKey: string) => {
    await http.post<Envelope>(`/erp/finance/payments/confirm/${encodeURIComponent(id)}`, { requestKey })
  },

  // 会计期间
  periods: () => payload<FinancePeriod[]>(http.get('/erp/finance/periods')),
  closePeriod: async (period: string) => {
    await http.post<Envelope>('/erp/finance/period/close', { period })
  },
  reopenPeriod: async (period: string) => {
    await http.post<Envelope>('/erp/finance/period/reopen', { period })
  },
}

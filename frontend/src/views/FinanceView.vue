<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { financeApi, type CashTxn, type FinanceAccount, type FinanceAccountDraft, type FinanceOverview, type FinancePayment, type FinancePeriod, type Payable, type PaymentDraft, type Receivable } from '../api/finance'
import { enumLabel, enumTagType } from '../api/labels'
import { useUserStore } from '../stores/user'

const user = useUserStore()
const route = useRoute()
const currency = (value: number | undefined | null) => `¥${Number(value || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`
const today = () => new Date().toLocaleDateString('sv-SE')
const currentPeriod = () => today().slice(0, 7)

const tabs = [
  { key: 'overview', label: '总览', permission: 'fn:account:list' },
  { key: 'accounts', label: '账户', permission: 'fn:account:list' },
  { key: 'cash', label: '资金流水', permission: 'fn:cashTxn:list' },
  { key: 'receivables', label: '应收', permission: 'fn:receivable:list' },
  { key: 'payables', label: '应付', permission: 'fn:payable:list' },
  { key: 'payments', label: '收付款', permission: 'fn:payment:list' },
] as const
type TabKey = typeof tabs[number]['key']
const accountTypes = [1, 2, 3].map(value => ({ value, label: enumLabel('accountType', value) }))

const overview = ref<FinanceOverview | null>(null)
const accounts = ref<FinanceAccount[]>([])
const cashTxns = ref<CashTxn[]>([])
const receivables = ref<Receivable[]>([])
const payables = ref<Payable[]>([])
const payments = ref<FinancePayment[]>([])
const periods = ref<FinancePeriod[]>([])
const enabledAccounts = computed(() => accounts.value.filter(account => Number(account.status) === 1))
const loading = ref(false)
const error = ref('')

const visibleTabs = computed(() => tabs.filter(tab => user.can(tab.permission)))
function tabFromRoute(): TabKey {
  if (route.path.endsWith('/receivables')) return 'receivables'
  if (route.path.endsWith('/payables')) return 'payables'
  if (route.path.endsWith('/payments')) return 'payments'
  const matched = tabs.find(tab => tab.key === String(route.query.tab || ''))
  return matched ? matched.key : 'overview'
}
function firstAllowedTab(): TabKey {
  const requested = tabFromRoute()
  if (visibleTabs.value.some(tab => tab.key === requested)) return requested
  return visibleTabs.value[0]?.key || 'overview'
}
const activeTab = ref<TabKey>(firstAllowedTab())

async function loadPaymentSources() {
  if (user.can('fn:account:list')) accounts.value = await financeApi.accounts()
  if (user.can('fn:receivable:list')) receivables.value = await financeApi.receivables()
  if (user.can('fn:payable:list')) payables.value = await financeApi.payables()
}
async function loadTab(tab: TabKey = activeTab.value) {
  loading.value = true; error.value = ''
  try {
    switch (tab) {
      case 'overview':
        if (user.can('fn:account:list')) overview.value = await financeApi.overview()
        if (user.can('fn:period:list')) periods.value = await financeApi.periods()
        break
      case 'accounts':
        accounts.value = await financeApi.accounts()
        break
      case 'cash':
        cashTxns.value = await financeApi.cashTxns()
        break
      case 'receivables':
        receivables.value = await financeApi.receivables()
        break
      case 'payables':
        payables.value = await financeApi.payables()
        break
      case 'payments':
        await loadPaymentSources()
        payments.value = await financeApi.payments()
        break
    }
  } catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
watch(activeTab, tab => loadTab(tab), { immediate: true })
watch(() => route.path, () => {
  const target = tabFromRoute()
  if (visibleTabs.value.some(tab => tab.key === target)) activeTab.value = target
})
watch(visibleTabs, list => {
  if (list.length && !list.some(tab => tab.key === activeTab.value)) activeTab.value = list[0].key
})

// 账户新增 / 编辑
const accountOpen = ref(false)
const accountSaving = ref(false)
const editingAccount = ref<FinanceAccount | null>(null)
const accountForm = reactive<FinanceAccountDraft>({ accountNo: '', accountName: '', accountType: 1, openingBalance: 0, status: 1, remark: '' })
function openAccount(row?: FinanceAccount) {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  editingAccount.value = row || null
  accountForm.accountNo = row?.code || ''
  accountForm.accountName = row?.name || ''
  accountForm.accountType = row?.accountType || 1
  accountForm.openingBalance = Number(row?.openingBalance || 0)
  accountForm.status = row?.status ?? 1
  accountForm.remark = row?.remark || ''
  accountOpen.value = true
}
async function saveAccount() {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  if (!accountForm.accountNo.trim() || !accountForm.accountName.trim()) { ElMessage.warning('账户编码和名称必填'); return }
  accountSaving.value = true
  try {
    const draft: FinanceAccountDraft = {
      ...accountForm,
      accountNo: accountForm.accountNo.trim(),
      accountName: accountForm.accountName.trim(),
      openingBalance: Number(accountForm.openingBalance || 0),
      remark: accountForm.remark.trim(),
    }
    if (editingAccount.value) await financeApi.updateAccount(editingAccount.value.id, draft)
    else await financeApi.createAccount(draft)
    accountOpen.value = false
    ElMessage.success(editingAccount.value ? '账户已更新' : '账户已新增')
    await loadTab('accounts')
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { accountSaving.value = false }
}

// 收付款登记 / 确认
interface PaymentTarget { id: string; label: string; balance: number; selected: boolean; amount: number }
const paymentOpen = ref(false)
const paymentSaving = ref(false)
const confirmingId = ref('')
const paymentTargets = ref<PaymentTarget[]>([])
const paymentForm = reactive({ accountId: '', direction: 1, bizType: 1, amount: 0, payDate: today(), remark: '', requestKey: '' })
const bizTypeOptions = computed(() => (paymentForm.direction === 1 ? [1, 4, 5] : [2, 3, 6])
  .map(value => ({ value, label: enumLabel('financeBizType', value) })))
const selectedTotal = computed(() => paymentTargets.value
  .filter(target => target.selected)
  .reduce((sum, target) => sum + Number(target.amount || 0), 0))

watch(paymentTargets, targets => {
  const total = targets.filter(target => target.selected).reduce((sum, target) => sum + Number(target.amount || 0), 0)
  if (total > 0) paymentForm.amount = Math.round(total * 100) / 100
}, { deep: true })

async function openPayment(direction: 1 | 2) {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  try {
    if (user.can('fn:account:list') && !accounts.value.length) accounts.value = await financeApi.accounts()
    if (direction === 1 && user.can('fn:receivable:list') && !receivables.value.length) receivables.value = await financeApi.receivables()
    if (direction === 2 && user.can('fn:payable:list') && !payables.value.length) payables.value = await financeApi.payables()
  } catch (e) { ElMessage.error((e as Error).message); return }
  if (!enabledAccounts.value.length) { ElMessage.warning('请先在账户页维护启用的资金账户'); return }
  paymentForm.direction = direction
  paymentForm.bizType = direction === 1 ? 1 : 2
  paymentForm.accountId = enabledAccounts.value[0].id
  paymentForm.amount = 0
  paymentForm.payDate = today()
  paymentForm.remark = ''
  paymentForm.requestKey = crypto.randomUUID()
  if (direction === 1) {
    paymentTargets.value = receivables.value
      .filter(row => Number(row.balance) > 0 && Number(row.status) !== 9)
      .map(row => ({ id: row.id, label: `${row.code} · ${row.customerName}`, balance: Number(row.balance), selected: false, amount: Number(row.balance) }))
  } else {
    paymentTargets.value = payables.value
      .filter(row => Number(row.balance) > 0 && Number(row.status) !== 9)
      .map(row => ({ id: row.id, label: `${row.code} · ${row.supplierName}`, balance: Number(row.balance), selected: false, amount: Number(row.balance) }))
  }
  paymentOpen.value = true
}
async function savePayment() {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  if (!paymentForm.accountId) { ElMessage.warning('请选择资金账户'); return }
  if (!paymentForm.payDate) { ElMessage.warning('请选择日期'); return }
  if (Number(paymentForm.amount) <= 0) { ElMessage.warning('金额必须大于 0'); return }
  const lines = paymentTargets.value
    .filter(target => target.selected)
    .map(target => ({ targetType: paymentForm.direction === 1 ? 1 : 2, targetId: target.id, amount: Number(target.amount || 0) }))
  if (lines.some(line => line.amount <= 0)) { ElMessage.warning('核销金额必须大于 0'); return }
  paymentSaving.value = true
  try {
    const draft: PaymentDraft = {
      accountId: paymentForm.accountId,
      direction: paymentForm.direction,
      bizType: paymentForm.bizType,
      amount: Number(paymentForm.amount),
      payDate: paymentForm.payDate,
      lines,
      remark: paymentForm.remark.trim(),
      requestKey: paymentForm.requestKey,
    }
    await financeApi.createPayment(draft)
    paymentOpen.value = false
    ElMessage.success('收付款单已登记，请在列表中确认过账')
    await loadTab('payments')
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { paymentSaving.value = false }
}
async function confirmPayment(row: FinancePayment) {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  try {
    await ElMessageBox.confirm(`确认收付款单 ${row.code}？确认后将立即过账，更新资金账户余额并核销应收/应付。`, '确认过账', { type: 'warning', confirmButtonText: '确认过账', cancelButtonText: '取消' })
  } catch { return }
  confirmingId.value = row.id
  try {
    await financeApi.confirmPayment(row.id, crypto.randomUUID())
    ElMessage.success('收付款单已确认并过账')
    await loadTab('payments')
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { confirmingId.value = '' }
}

// 会计期间
const periodSaving = ref(false)
async function closePeriod(period?: string) {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  let target = period
  if (!target) {
    try {
      const result = await ElMessageBox.prompt('请输入要结账的会计期间（YYYY-MM）', '期间结账', {
        inputValue: currentPeriod(),
        inputPattern: /^\d{4}-\d{2}$/,
        inputErrorMessage: '格式应为 YYYY-MM',
        confirmButtonText: '下一步',
        cancelButtonText: '取消',
      })
      target = (result.value || '').trim()
    } catch { return }
  }
  if (!target) return
  try {
    await ElMessageBox.confirm(`确认关闭会计期间 ${target}？结账后该期间禁止补录和过账。`, '期间结账', { type: 'warning', confirmButtonText: '确认结账', cancelButtonText: '取消' })
  } catch { return }
  periodSaving.value = true
  try {
    await financeApi.closePeriod(target)
    ElMessage.success(`期间 ${target} 已结账`)
    await loadTab('overview')
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { periodSaving.value = false }
}
async function reopenPeriod(period: string) {
  if (user.demo) { ElMessage.warning('演示模式不支持资金操作，请连接后端'); return }
  try {
    await ElMessageBox.confirm(`确认反结账会计期间 ${period}？反结账后允许继续补录与过账。`, '反结账', { type: 'warning', confirmButtonText: '确认反结账', cancelButtonText: '取消' })
  } catch { return }
  periodSaving.value = true
  try {
    await financeApi.reopenPeriod(period)
    ElMessage.success(`期间 ${period} 已反结账`)
    await loadTab('overview')
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { periodSaving.value = false }
}
</script>
<template>
  <div class="page-head"><div><h1>会计资金</h1><p>账户、资金流水、应收应付、收付款与期间结账</p></div><el-button @click="loadTab()">刷新</el-button></div>
  <el-alert v-if="user.demo" type="warning" :closable="false" show-icon title="演示模式仅展示页面结构，资金数据为空；新增、结账、登记与过账等写操作需连接后端。" class="demo-tip" />
  <div v-if="error" class="error-state">{{ error }} <el-button text type="primary" @click="loadTab()">重试</el-button></div>
  <section class="panel" v-loading="loading">
    <el-tabs v-model="activeTab">
      <el-tab-pane v-if="user.can('fn:account:list')" label="总览" name="overview">
        <template v-if="overview">
          <div class="kpi-grid finance-kpis">
            <div class="kpi"><div class="kpi-label">现金余额</div><div class="kpi-value">{{ currency(overview.cashBalance) }}</div></div>
            <div class="kpi"><div class="kpi-label">本月流入</div><div class="kpi-value">{{ currency(overview.monthIn) }}</div></div>
            <div class="kpi"><div class="kpi-label">本月流出</div><div class="kpi-value">{{ currency(overview.monthOut) }}</div></div>
            <div class="kpi"><div class="kpi-label">应收余额</div><div class="kpi-value">{{ currency(overview.receivableBalance) }}</div></div>
            <div class="kpi"><div class="kpi-label">应付余额</div><div class="kpi-value">{{ currency(overview.payableBalance) }}</div></div>
            <div class="kpi"><div class="kpi-label">逾期应收/应付（笔）</div><div class="kpi-value" :class="{ 'kpi-warning': Number(overview.receivableOverdue) + Number(overview.payableOverdue) > 0 }">{{ overview.receivableOverdue ?? 0 }} / {{ overview.payableOverdue ?? 0 }}</div></div>
          </div>
        </template>
        <div v-else class="empty-state">暂无资金总览数据</div>
        <div v-if="user.can('fn:period:list')" class="period-block">
          <div class="line-toolbar"><h2>会计期间</h2><el-button v-if="user.can('fn:period:close')" type="primary" :disabled="user.demo" :loading="periodSaving" @click="closePeriod()">结账</el-button></div>
          <div class="table-wrap"><el-table :data="periods" stripe empty-text="尚无结账记录，可点击「结账」登记当前期间">
            <el-table-column prop="period" label="期间" width="110" />
            <el-table-column label="状态" width="100"><template #default="scope"><el-tag :type="enumTagType('periodStatus', scope.row.status)" size="small">{{ enumLabel('periodStatus', scope.row.status) }}</el-tag></template></el-table-column>
            <el-table-column label="结账人" min-width="110"><template #default="scope">{{ scope.row.closedBy || '—' }}</template></el-table-column>
            <el-table-column label="结账时间" min-width="170"><template #default="scope">{{ scope.row.closedTime || '—' }}</template></el-table-column>
            <el-table-column v-if="user.can('fn:period:close') || user.can('fn:period:reopen')" label="操作" width="130"><template #default="scope"><el-button v-if="scope.row.status !== 1 && user.can('fn:period:close')" text type="primary" :disabled="user.demo" :loading="periodSaving" @click="closePeriod(scope.row.period)">结账</el-button><el-button v-if="scope.row.status === 1 && user.can('fn:period:reopen')" text type="warning" :disabled="user.demo" :loading="periodSaving" @click="reopenPeriod(scope.row.period)">反结账</el-button></template></el-table-column>
          </el-table></div>
        </div>
      </el-tab-pane>

      <el-tab-pane v-if="user.can('fn:account:list')" label="账户" name="accounts">
        <div class="line-toolbar"><h2>资金账户</h2><el-button v-if="user.can('fn:account:add')" type="primary" :disabled="user.demo" @click="openAccount()">新增账户</el-button></div>
        <div class="table-wrap"><el-table :data="accounts" stripe empty-text="暂无资金账户"><el-table-column prop="code" label="账户编码" min-width="130" /><el-table-column prop="name" label="账户名称" min-width="150" /><el-table-column label="类型" width="90"><template #default="scope">{{ enumLabel('accountType', scope.row.accountType) }}</template></el-table-column><el-table-column label="期初余额" min-width="120"><template #default="scope">{{ currency(scope.row.openingBalance) }}</template></el-table-column><el-table-column label="当前余额" min-width="120"><template #default="scope"><span class="money">{{ currency(scope.row.currentBalance) }}</span></template></el-table-column><el-table-column label="状态" width="90"><template #default="scope"><el-tag :type="enumTagType('masterStatus', scope.row.status)" size="small">{{ enumLabel('masterStatus', scope.row.status) }}</el-tag></template></el-table-column><el-table-column prop="remark" label="备注" min-width="160" /><el-table-column v-if="user.can('fn:account:edit')" label="操作" width="80"><template #default="scope"><el-button text type="primary" :disabled="user.demo" @click="openAccount(scope.row)">编辑</el-button></template></el-table-column></el-table></div>
      </el-tab-pane>

      <el-tab-pane v-if="user.can('fn:cashTxn:list')" label="资金流水" name="cash">
        <div class="table-wrap"><el-table :data="cashTxns" stripe empty-text="暂无资金流水"><el-table-column prop="txnNo" label="流水号" min-width="190" /><el-table-column prop="accountName" label="账户" min-width="130" /><el-table-column label="方向" width="80"><template #default="scope"><el-tag :type="enumTagType('financeDirection', scope.row.direction)" size="small">{{ enumLabel('financeDirection', scope.row.direction) }}</el-tag></template></el-table-column><el-table-column label="金额" min-width="120"><template #default="scope"><span class="money">{{ currency(scope.row.amount) }}</span></template></el-table-column><el-table-column label="业务类型" min-width="110"><template #default="scope">{{ enumLabel('financeBizType', scope.row.bizType) }}</template></el-table-column><el-table-column prop="txnDate" label="发生日期" min-width="115" /><el-table-column prop="remark" label="备注" min-width="180" /></el-table></div>
      </el-tab-pane>

      <el-tab-pane v-if="user.can('fn:receivable:list')" label="应收" name="receivables">
        <div class="table-wrap"><el-table :data="receivables" stripe empty-text="暂无应收账款"><el-table-column prop="code" label="应收单号" min-width="150" /><el-table-column prop="customerName" label="客户" min-width="140" /><el-table-column label="订单" min-width="90"><template #default="scope">#{{ scope.row.orderId || '—' }}</template></el-table-column><el-table-column label="应收金额" min-width="120"><template #default="scope">{{ currency(scope.row.amount) }}</template></el-table-column><el-table-column label="已收" min-width="110"><template #default="scope">{{ currency(scope.row.receivedAmount) }}</template></el-table-column><el-table-column label="未收余额" min-width="120"><template #default="scope"><span class="money">{{ currency(scope.row.balance) }}</span></template></el-table-column><el-table-column prop="dueDate" label="到期日" min-width="115" /><el-table-column prop="agingBucket" label="账龄" min-width="90" /><el-table-column label="逾期天数" width="100"><template #default="scope"><span :class="{ 'kpi-warning': Number(scope.row.overdueDays) > 0 }">{{ scope.row.overdueDays ?? 0 }}</span></template></el-table-column><el-table-column label="状态" width="95"><template #default="scope"><el-tag :type="enumTagType('arApStatus', scope.row.status)" size="small">{{ enumLabel('arApStatus', scope.row.status) }}</el-tag></template></el-table-column></el-table></div>
      </el-tab-pane>

      <el-tab-pane v-if="user.can('fn:payable:list')" label="应付" name="payables">
        <div class="table-wrap"><el-table :data="payables" stripe empty-text="暂无应付账款"><el-table-column prop="code" label="应付单号" min-width="150" /><el-table-column prop="supplierName" label="供应商" min-width="140" /><el-table-column label="采购单" min-width="90"><template #default="scope">#{{ scope.row.poId || '—' }}</template></el-table-column><el-table-column label="应付金额" min-width="120"><template #default="scope">{{ currency(scope.row.amount) }}</template></el-table-column><el-table-column label="已付" min-width="110"><template #default="scope">{{ currency(scope.row.paidAmount) }}</template></el-table-column><el-table-column label="未付余额" min-width="120"><template #default="scope"><span class="money">{{ currency(scope.row.balance) }}</span></template></el-table-column><el-table-column prop="dueDate" label="到期日" min-width="115" /><el-table-column prop="agingBucket" label="账龄" min-width="90" /><el-table-column label="逾期天数" width="100"><template #default="scope"><span :class="{ 'kpi-warning': Number(scope.row.overdueDays) > 0 }">{{ scope.row.overdueDays ?? 0 }}</span></template></el-table-column><el-table-column label="状态" width="95"><template #default="scope"><el-tag :type="enumTagType('arApStatus', scope.row.status)" size="small">{{ enumLabel('arApStatus', scope.row.status) }}</el-tag></template></el-table-column></el-table></div>
      </el-tab-pane>

      <el-tab-pane v-if="user.can('fn:payment:list')" label="收付款" name="payments">
        <div class="line-toolbar"><h2>收付款单</h2><div class="head-actions"><el-button v-if="user.can('fn:payment:add')" type="primary" :disabled="user.demo" @click="openPayment(1)">登记收款</el-button><el-button v-if="user.can('fn:payment:add')" type="primary" plain :disabled="user.demo" @click="openPayment(2)">登记付款</el-button></div></div>
        <div class="table-wrap"><el-table :data="payments" stripe empty-text="暂无收付款单"><el-table-column prop="code" label="单号" min-width="160" /><el-table-column label="方向" width="80"><template #default="scope"><el-tag :type="enumTagType('financeDirection', scope.row.direction)" size="small">{{ enumLabel('financeDirection', scope.row.direction) }}</el-tag></template></el-table-column><el-table-column label="业务类型" min-width="110"><template #default="scope">{{ enumLabel('financeBizType', scope.row.bizType) }}</template></el-table-column><el-table-column prop="accountName" label="账户" min-width="130" /><el-table-column label="金额" min-width="120"><template #default="scope"><span class="money">{{ currency(scope.row.amount) }}</span></template></el-table-column><el-table-column prop="payDate" label="日期" min-width="115" /><el-table-column label="状态" width="95"><template #default="scope"><el-tag :type="enumTagType('paymentStatus', scope.row.status)" size="small">{{ enumLabel('paymentStatus', scope.row.status) }}</el-tag></template></el-table-column><el-table-column prop="remark" label="备注" min-width="150" /><el-table-column v-if="user.can('fn:payment:confirm')" label="操作" width="90"><template #default="scope"><el-button v-if="scope.row.status === 0" text type="primary" :disabled="user.demo" :loading="confirmingId === scope.row.id" @click="confirmPayment(scope.row)">确认</el-button></template></el-table-column></el-table></div>
      </el-tab-pane>
    </el-tabs>
  </section>

  <el-dialog v-model="accountOpen" :title="editingAccount ? '编辑资金账户' : '新增资金账户'" width="min(520px, 94vw)">
    <el-form label-position="top">
      <el-form-item label="账户编码" required><el-input v-model="accountForm.accountNo" maxlength="32" placeholder="如 CASH-001" /></el-form-item>
      <el-form-item label="账户名称" required><el-input v-model="accountForm.accountName" maxlength="64" placeholder="如 工商银行基本户" /></el-form-item>
      <el-form-item label="账户类型" required><el-select v-model="accountForm.accountType" style="width:100%"><el-option v-for="type in accountTypes" :key="type.value" :label="type.label" :value="type.value" /></el-select></el-form-item>
      <el-form-item label="期初余额"><el-input-number v-model="accountForm.openingBalance" :min="0" :precision="2" controls-position="right" style="width:100%" /></el-form-item>
      <el-form-item v-if="editingAccount" label="状态"><el-radio-group v-model="accountForm.status"><el-radio :value="1">启用</el-radio><el-radio :value="0">停用</el-radio></el-radio-group></el-form-item>
      <el-form-item label="备注"><el-input v-model="accountForm.remark" type="textarea" :rows="2" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer><el-button @click="accountOpen=false">取消</el-button><el-button type="primary" :loading="accountSaving" :disabled="user.demo" @click="saveAccount">保存</el-button></template>
  </el-dialog>

  <el-dialog v-model="paymentOpen" :title="paymentForm.direction === 1 ? '登记收款' : '登记付款'" width="min(760px, 96vw)">
    <el-form label-position="top" class="order-form-grid">
      <el-form-item label="资金账户" required><el-select v-model="paymentForm.accountId" style="width:100%"><el-option v-for="account in enabledAccounts" :key="account.id" :label="`${account.code} · ${account.name}（${currency(account.currentBalance)}）`" :value="account.id" /></el-select></el-form-item>
      <el-form-item label="业务类型"><el-select v-model="paymentForm.bizType" style="width:100%"><el-option v-for="item in bizTypeOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select></el-form-item>
      <el-form-item label="金额" required><el-input-number v-model="paymentForm.amount" :min="0" :precision="2" controls-position="right" style="width:100%" /></el-form-item>
      <el-form-item label="日期" required><el-date-picker v-model="paymentForm.payDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
      <el-form-item label="备注" class="span-two"><el-input v-model="paymentForm.remark" maxlength="255" placeholder="选填" /></el-form-item>
    </el-form>
    <h3 class="sub-title">{{ paymentForm.direction === 1 ? '选择待核销应收' : '选择待核销应付' }}</h3>
    <div class="table-wrap"><el-table :data="paymentTargets" size="small" max-height="260" empty-text="暂无未结清单据"><el-table-column width="52"><template #default="scope"><el-checkbox v-model="scope.row.selected" /></template></el-table-column><el-table-column prop="label" label="单据" min-width="240" /><el-table-column label="未结清" width="130"><template #default="scope">{{ currency(scope.row.balance) }}</template></el-table-column><el-table-column label="本次核销" width="160"><template #default="scope"><el-input-number v-model="scope.row.amount" :min="0" :max="scope.row.balance" :precision="2" :disabled="!scope.row.selected" controls-position="right" size="small" style="width:100%" /></template></el-table-column></el-table></div>
    <p class="muted">核销合计 {{ currency(selectedTotal) }}；不勾选核销对象时可按金额登记其他收付款。</p>
    <template #footer><el-button @click="paymentOpen=false">取消</el-button><el-button type="primary" :loading="paymentSaving" :disabled="user.demo" @click="savePayment">登记</el-button></template>
  </el-dialog>
</template>
<style scoped>
.demo-tip { margin-bottom: 16px; }
.finance-kpis { grid-template-columns: repeat(6, minmax(0, 1fr)); }
.period-block { margin-top: 20px; border-top: 1px solid #e0e8f2; padding-top: 16px; }
.period-block .line-toolbar h2 { font-size: 15px; margin: 0; }
.span-two { grid-column: 1 / -1; }
.sub-title { font-size: 14px; margin: 18px 0 10px; }
@media (max-width: 1100px) { .finance-kpis { grid-template-columns: repeat(3, minmax(0, 1fr)); } }
@media (max-width: 760px) { .finance-kpis { grid-template-columns: repeat(2, minmax(0, 1fr)); } }
@media (max-width: 440px) { .finance-kpis { grid-template-columns: 1fr; } }
</style>

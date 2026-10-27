package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.FinanceRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpFinanceMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpPayrollMapper;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpFinanceService {
    private final ErpFinanceMapper mapper;
    private final ErpWorkflowService workflow;
    private final ErpStockService stock;
    private final ErpPayrollMapper salaryMapper;

    public ErpFinanceService(ErpFinanceMapper mapper, ErpWorkflowService workflow,
                             ErpStockService stock, ErpPayrollMapper salaryMapper) {
        this.mapper = mapper; this.workflow = workflow; this.stock = stock; this.salaryMapper = salaryMapper;
    }

    public List<Map<String,Object>> accounts() { return mapper.accounts(); }
    public List<Map<String,Object>> cashTxns() { return mapper.cashTxns(); }
    public List<Map<String,Object>> receivables() { return mapper.receivables(); }
    public List<Map<String,Object>> payables() { return mapper.payables(); }
    public List<Map<String,Object>> payments() { return mapper.payments(); }
    public Map<String,Object> overview() { return mapper.summary(); }

    public Map<String,Object> payment(Long id) {
        Map<String,Object> header = mapper.payments().stream()
                .filter(row -> String.valueOf(row.get("id")).equals(String.valueOf(id))).findFirst()
                .orElseThrow(() -> new ServiceException("收付款单不存在", 1001));
        Map<String,Object> result = new HashMap<>(header);
        result.put("lines", mapper.paymentLines(id));
        return result;
    }

    @Transactional
    public Map<String,String> saveAccount(FinanceRequest.Account request) {
        if (request == null || request.accountName() == null || request.accountName().isBlank()
                || request.accountType() == null || request.accountNo() == null || request.accountNo().isBlank()) {
            throw new ServiceException("账户编码、名称和类型必填", 1001);
        }
        Map<String,Object> row = new HashMap<>();
        row.put("accountNo", request.accountNo().trim());
        row.put("accountName", request.accountName().trim());
        row.put("accountType", request.accountType());
        row.put("openingBalance", request.openingBalance() == null ? BigDecimal.ZERO : request.openingBalance());
        row.put("status", request.status() == null ? 1 : request.status());
        row.put("remark", request.remark());
        row.put("username", SecurityUtils.getUsername());
        if (request.accountId() == null) {
            mapper.insertAccount(row);
            return Map.of("accountId", String.valueOf(row.get("accountId")));
        }
        row.put("accountId", request.accountId());
        if (mapper.updateAccount(row) != 1) throw new ServiceException("账户不存在", 1001);
        return Map.of("accountId", String.valueOf(request.accountId()));
    }

    @Transactional
    public Map<String,String> createPayment(FinanceRequest.Payment request) {
        if (request == null || request.accountId() == null || request.direction() == null
                || !List.of(1,2).contains(request.direction()) || request.amount() == null
                || request.amount().signum() <= 0 || request.payDate() == null) {
            throw new ServiceException("账户、方向、金额和日期必填", 1001);
        }
        if (request.direction() == 1 && request.lines() != null
                && request.lines().stream().anyMatch(line -> line.targetType() == null || line.targetType() != 1)) {
            throw new ServiceException("收款只能核销应收", 1001);
        }
        if (request.direction() == 2 && request.lines() != null
                && request.lines().stream().anyMatch(line -> line.targetType() == null || line.targetType() != 2)) {
            throw new ServiceException("付款只能核销应付", 1001);
        }
        if (mapper.lockAccount(request.accountId()) == null) throw new ServiceException("资金账户不存在", 1001);
        workflow.assertOpen(request.payDate());
        Map<String,Object> row = new HashMap<>();
        row.put("paymentNo", DocNos.temp("PM"));
        row.put("direction", request.direction());
        row.put("bizType", request.bizType() == null ? (request.direction() == 1 ? 1 : 2) : request.bizType());
        row.put("accountId", request.accountId());
        row.put("counterpartyType", request.counterpartyType());
        row.put("counterpartyId", request.counterpartyId());
        row.put("amount", money(request.amount()));
        row.put("payDate", request.payDate());
        row.put("requestKey", blank(request.requestKey()));
        row.put("remark", request.remark());
        row.put("username", SecurityUtils.getUsername());
        mapper.insertPayment(row);
        Long id = ((Number) row.get("paymentId")).longValue();
        mapper.setPaymentNo(id, DocNos.formal("PM", id));
        if (request.lines() != null) {
            for (FinanceRequest.Line line : request.lines()) {
                if (line == null || line.targetId() == null || line.amount() == null || line.amount().signum() <= 0) {
                    throw new ServiceException("核销明细不合法", 1001);
                }
                mapper.insertPaymentLine(id, line.targetType(), line.targetId(), money(line.amount()));
            }
        }
        return Map.of("paymentId", id.toString());
    }

    @Transactional
    public void confirmPayment(Long id, String requestKey) {
        if (id == null || requestKey == null || requestKey.isBlank()) throw new ServiceException("收付款单和请求键必填", 1001);
        if (stock.begin(requestKey, "payment_confirm", "fn_payment", id, Map.of("paymentId", id))) return;
        Map<String,Object> payment = mapper.lockPayment(id);
        if (payment == null || number(payment, "status") != 0) throw new ServiceException("收付款单状态不允许确认", 2001);
        workflow.assertOpen(toLocalDate(payment.get("payDate")));
        int direction = number(payment, "direction");
        for (Map<String,Object> line : mapper.paymentLines(id)) {
            int targetType = number(line, "targetType");
            Long targetId = Long.valueOf(String.valueOf(line.get("targetId")));
            BigDecimal amount = money(decimal(line, "amount"));
            if (targetType == 1) {
                if (mapper.lockReceivable(targetId) == null || mapper.settleReceivable(targetId, amount) != 1) {
                    throw new ServiceException("应收余额不足或已结清", 2003);
                }
                workflow.complete("collect", "fn_receivable", targetId);
            } else {
                if (mapper.lockPayable(targetId) == null || mapper.settlePayable(targetId, amount) != 1) {
                    throw new ServiceException("应付余额不足或已结清", 2003);
                }
                workflow.complete("pay", "fn_payable", targetId);
            }
        }
        BigDecimal amount = decimal(payment, "amount");
        if (direction == 1) {
            if (mapper.addBalance(longNum(payment, "accountId"), amount) != 1) throw new ServiceException("资金账户不存在", 1001);
        } else if (mapper.subtractBalance(longNum(payment, "accountId"), amount) != 1) {
            throw new ServiceException("资金账户余额不足", 2003);
        }
        Map<String,Object> txn = new HashMap<>();
        txn.put("txnNo", "CT" + UUID.randomUUID().toString().replace("-", "").substring(0, 24).toUpperCase());
        txn.put("accountId", longNum(payment, "accountId"));
        txn.put("direction", direction);
        txn.put("amount", amount);
        txn.put("bizType", payment.get("bizType") == null ? (direction == 1 ? 5 : 6) : number(payment, "bizType"));
        txn.put("counterpartyType", payment.get("counterpartyType"));
        txn.put("counterpartyId", payment.get("counterpartyId"));
        txn.put("sourceType", "fn_payment");
        txn.put("sourceId", id);
        txn.put("txnDate", payment.get("payDate"));
        txn.put("requestKey", "payment:" + id);
        txn.put("remark", payment.get("remark"));
        txn.put("username", SecurityUtils.getUsername());
        mapper.insertCashTxn(txn);
        if (mapper.confirmPayment(id, SecurityUtils.getUsername()) != 1) throw new ServiceException("收付款单状态已变化", 2001);
        stock.complete(requestKey, id);
    }

    @Transactional
    public Long createReceivable(Long orderId, Long shipmentId, Long customerId, BigDecimal amount, String requestKey) {
        if (amount == null || amount.signum() <= 0) return null;
        Map<String,Object> row = new HashMap<>();
        row.put("arNo", DocNos.temp("AR")); row.put("customerId", customerId);
        row.put("orderId", orderId); row.put("shipmentId", shipmentId);
        row.put("amount", money(amount));
        Integer days = mapper.customerCreditDays(customerId);
        row.put("dueDate", LocalDate.now().plusDays(days == null ? 0 : days));
        row.put("requestKey", requestKey); row.put("username", SecurityUtils.getUsername());
        if (mapper.insertReceivable(row) != 1) return mapper.receivableByRequest(requestKey);
        Long id = ((Number) row.get("arId")).longValue();
        mapper.setArNo(id, DocNos.formal("AR", id));
        workflow.todo("collect", "fn_receivable", id, "finance", "发货形成应收，待收款", "/finance/receivables",
                (LocalDate) row.get("dueDate"));
        return id;
    }

    @Transactional
    public Long createShipmentReceivable(Long orderId, Long shipmentId, Long customerId, String requestKey) {
        BigDecimal amount = mapper.shipmentAmount(shipmentId);
        return createReceivable(orderId, shipmentId, customerId, amount, requestKey);
    }

    @Transactional
    public Long createPayable(Long poId, Long supplierId, BigDecimal amount, String requestKey) {
        if (amount == null || amount.signum() <= 0) return null;
        Map<String,Object> row = new HashMap<>();
        row.put("apNo", DocNos.temp("AP")); row.put("supplierId", supplierId); row.put("poId", poId);
        row.put("amount", money(amount));
        Integer days = mapper.supplierCreditDays(supplierId);
        row.put("dueDate", LocalDate.now().plusDays(days == null ? 0 : days));
        row.put("requestKey", requestKey); row.put("username", SecurityUtils.getUsername());
        if (mapper.insertPayable(row) != 1) return mapper.payableByRequest(requestKey);
        Long id = ((Number) row.get("apId")).longValue();
        mapper.setApNo(id, DocNos.formal("AP", id));
        workflow.todo("pay", "fn_payable", id, "finance", "采购收货形成应付，待付款", "/finance/payables",
                (LocalDate) row.get("dueDate"));
        return id;
    }

    @Transactional
    public BigDecimal reduceReceivable(Long orderId, BigDecimal refundAmount, String requestKey) {
        if (refundAmount == null || refundAmount.signum() <= 0) return BigDecimal.ZERO;
        Map<String,Object> ar = mapper.latestReceivable(orderId);
        if (ar == null) return money(refundAmount);
        BigDecimal balance = money(decimal(ar, "balance"));
        BigDecimal take = balance.min(money(refundAmount));
        if (take.signum() > 0) mapper.settleReceivable(((Number) ar.get("arId")).longValue(), take);
        if (take.compareTo(balance) >= 0) workflow.complete("collect", "fn_receivable", ((Number) ar.get("arId")).longValue());
        return money(refundAmount).subtract(take);
    }

    @Transactional
    public Map<String,String> payPurchase(Long poId, Long supplierId, BigDecimal amount, String requestKey) {
        if (poId == null || supplierId == null || amount == null || amount.signum() <= 0
                || requestKey == null || requestKey.isBlank()) {
            throw new ServiceException("采购单、供应商、金额和请求键必填", 1001);
        }
        Long accountId = mapper.defaultPaymentAccount();
        if (accountId == null) throw new ServiceException("未找到可用资金账户，请先在财务页面创建并启用账户", 2001);
        LocalDate payDate = LocalDate.now();
        workflow.assertOpen(payDate);
        // 1. 先建应付（同时给会计发待付款待办，便于在会计页面看到这笔采购）
        Long apId = createPayable(poId, supplierId, amount, requestKey);
        // 2. 创建采购付款单（direction=2 付, bizType=2 采购付款, counterparty=供应商）
        Map<String,Object> row = new HashMap<>();
        row.put("paymentNo", DocNos.temp("PM"));
        row.put("direction", 2); row.put("bizType", 2);
        row.put("accountId", accountId);
        row.put("counterpartyType", 2); row.put("counterpartyId", supplierId);
        row.put("amount", money(amount));
        row.put("payDate", payDate);
        row.put("requestKey", requestKey);
        row.put("remark", "采购付款 PO" + poId);
        row.put("username", SecurityUtils.getUsername());
        mapper.insertPayment(row);
        Long paymentId = ((Number) row.get("paymentId")).longValue();
        mapper.setPaymentNo(paymentId, DocNos.formal("PM", paymentId));
        // 3. 关联核销应付
        if (apId != null) mapper.insertPaymentLine(paymentId, 2, apId, money(amount));
        // 4. 自动过账：扣账户余额、写资金流水、核销应付、撤回付款待办
        confirmPayment(paymentId, requestKey + "-confirm");
        return Map.of("paymentId", paymentId.toString(), "accountId", accountId.toString());
    }

    public Long payableByPo(Long poId) {
        if (poId == null) return null;
        return mapper.payableByPo(poId);
    }

    @Transactional
    public Map<String,String> payPayroll(Long slipId, Long accountId, LocalDate payDate, String requestKey) {
        Map<String,Object> slip = salaryMapper.lockForPay(slipId);
        if (slip == null || number(slip, "status") != 1) throw new ServiceException("工资单未确认或已发放", 2001);
        if (accountId == null || payDate == null || requestKey == null || requestKey.isBlank()) {
            throw new ServiceException("账户、发放日期和请求键必填", 1001);
        }
        workflow.assertOpen(payDate);
        Map<String,Object> row = new HashMap<>();
        row.put("paymentNo", DocNos.temp("PM"));
        row.put("direction", 2); row.put("bizType", 3);
        row.put("accountId", accountId);
        row.put("counterpartyType", 3); row.put("counterpartyId", longNum(slip, "empId"));
        row.put("amount", money(decimal(slip, "netPay")));
        row.put("payDate", payDate);
        row.put("requestKey", requestKey);
        row.put("remark", "工资发放 " + slip.get("wagePeriod"));
        row.put("username", SecurityUtils.getUsername());
        mapper.insertPayment(row);
        Long paymentId = ((Number) row.get("paymentId")).longValue();
        mapper.setPaymentNo(paymentId, DocNos.formal("PM", paymentId));
        confirmPayment(paymentId, requestKey + "-confirm");
        if (salaryMapper.markPaid(slipId, SecurityUtils.getUsername()) != 1) throw new ServiceException("工资单状态已变化", 2001);
        salaryMapper.markWagesPaid(longNum(slip, "empId"), String.valueOf(slip.get("wagePeriod")));
        workflow.complete("payroll", "hr_salary_slip", slipId);
        return Map.of("paymentId", paymentId.toString());
    }

    private static BigDecimal money(BigDecimal value) { return value.setScale(2, RoundingMode.HALF_UP); }
    private static int number(Map<String,Object> row, String key) { return ((Number) row.get(key)).intValue(); }
    private static long longNum(Map<String,Object> row, String key) { return ((Number) row.get(key)).longValue(); }
    private static BigDecimal decimal(Map<String,Object> row, String key) {
        Object value = row.get(key);
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }
    private static String blank(String value) { return value == null || value.isBlank() ? null : value; }
    private static LocalDate toLocalDate(Object value) {
        if (value == null) return null;
        if (value instanceof LocalDate date) return date;
        if (value instanceof java.sql.Date date) return date.toLocalDate();
        if (value instanceof java.util.Date date) return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return LocalDate.parse(value.toString());
    }
}

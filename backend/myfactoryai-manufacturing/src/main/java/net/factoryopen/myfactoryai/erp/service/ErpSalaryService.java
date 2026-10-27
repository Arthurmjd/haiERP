package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.SalaryPayRequest;
import net.factoryopen.myfactoryai.erp.domain.SalaryRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpPayrollMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpSalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpSalaryService {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal DEFAULT_SI_PERSONAL = new BigDecimal("10.5");
    private static final BigDecimal DEFAULT_FUND_PERSONAL = new BigDecimal("5");
    private static final BigDecimal DEFAULT_SI_COMPANY = new BigDecimal("27");
    private static final BigDecimal DEFAULT_FUND_COMPANY = new BigDecimal("5");
    private static final BigDecimal DEFAULT_TAX_THRESHOLD = new BigDecimal("5000");
    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal("3");

    private final ErpSalaryMapper mapper;
    private final ErpPayrollMapper payroll;
    private final ErpWorkflowService workflow;
    private final ErpFinanceService finance;

    public ErpSalaryService(ErpSalaryMapper mapper, ErpPayrollMapper payroll,
                            ErpWorkflowService workflow, ErpFinanceService finance) {
        this.mapper = mapper;
        this.payroll = payroll;
        this.workflow = workflow;
        this.finance = finance;
    }

    @Transactional
    public Map<String,String> generate(SalaryRequest request) {
        if (request == null || request.empId() == null) throw new ServiceException("员工与工资期必填",1001);
        validatePeriod(request.wagePeriod());
        Map<String,Object> employee = payroll.employeeForPay(request.empId());
        if (employee == null) throw new ServiceException("员工不存在",1001);
        BigDecimal base = decimal(employee,"baseSalary");
        BigDecimal siBase = decimal(employee,"siBase");
        if (siBase.signum() <= 0) siBase = base;
        BigDecimal piece = mapper.pieceTotal(request.empId(),request.wagePeriod());
        Map<String,BigDecimal> config = configs();
        Map<String,Object> previous = mapper.existing(request.empId(),request.wagePeriod());
        if (previous != null) {
            int previousStatus = number(previous,"status").intValue();
            if (previousStatus == 1) throw new ServiceException("工资单已确认，不能重新生成",2001);
            if (previousStatus == 2) throw new ServiceException("工资单已发放，不能重新生成",2001);
        }

        BigDecimal allowance = value(previous,"allowance");
        BigDecimal overtime = value(previous,"overtimePay");
        BigDecimal otherDeduction = value(previous,"otherDeduction");
        BigDecimal siPersonal = percent(siBase,config.get("si_personal"));
        BigDecimal fundPersonal = percent(siBase,config.get("fund_personal"));
        BigDecimal siCompany = percent(siBase,config.get("si_company"));
        BigDecimal fundCompany = percent(siBase,config.get("fund_company"));
        BigDecimal gross = money(base.add(piece).add(allowance).add(overtime));
        BigDecimal taxable = gross.subtract(siPersonal).subtract(fundPersonal)
                .subtract(config.get("tax_threshold"));
        if (taxable.signum() < 0) taxable = BigDecimal.ZERO;
        BigDecimal tax = percent(money(taxable),config.get("tax_rate"));
        BigDecimal net = money(gross.subtract(siPersonal).subtract(fundPersonal)
                .subtract(tax).subtract(otherDeduction));
        if (net.signum() < 0) throw new ServiceException("实发工资不能为负",2006);
        BigDecimal companyCost = money(gross.add(siCompany).add(fundCompany));

        Map<String,Object> row = new HashMap<>();
        row.put("empId",request.empId()); row.put("period",request.wagePeriod());
        row.put("base",base); row.put("allowance",allowance); row.put("piece",piece);
        row.put("overtime",overtime); row.put("gross",gross);
        row.put("siPersonal",siPersonal); row.put("fundPersonal",fundPersonal);
        row.put("tax",tax); row.put("otherDeduction",otherDeduction); row.put("net",net);
        row.put("siCompany",siCompany); row.put("fundCompany",fundCompany);
        row.put("companyCost",companyCost);
        row.put("username",SecurityUtils.getUsername());
        if (previous == null) {
            payroll.insertFull(row);
        } else {
            row.put("id",number(previous,"id"));
            if (payroll.refreshFull(row) != 1) throw new ServiceException("工资单状态已变化",2001);
        }
        return Map.of("id",number(row,"id").toString());
    }

    @Transactional
    public int generateBatch(String wagePeriod) {
        validatePeriod(wagePeriod);
        int count = 0;
        for (Long empId : payroll.activeEmployeeIds()) {
            generate(new SalaryRequest(empId,wagePeriod));
            count++;
        }
        return count;
    }

    @Transactional
    public void confirm(Long id) {
        Long empId = mapper.slipEmployee(id);
        if (empId == null) throw new ServiceException("工资单不存在",1001);
        mapper.employee(empId);
        Map<String,Object> row = mapper.lockSlip(id);
        String period = (String)row.get("wagePeriod");
        if (number(row,"status").intValue() != 0) throw new ServiceException("工资单状态不允许确认",2001);
        if (mapper.pieceTotal(empId,period).compareTo(decimal(row,"pieceAmount")) != 0) {
            throw new ServiceException("计件明细已变化，请重新生成工资单",2006);
        }
        workflow.assertOpenPeriod(period);
        if (mapper.confirm(id) != 1) throw new ServiceException("工资单状态已变化",2001);
        workflow.todo("payroll","hr_salary_slip",id,"finance","工资单已确认，待发放","/hr/salary",null);
    }

    @Transactional
    public Map<String,String> pay(Long id, SalaryPayRequest request) {
        if (request == null) throw new ServiceException("发放参数必填",1001);
        return finance.payPayroll(id,request.accountId(),request.payDate(),request.requestKey());
    }

    private Map<String,BigDecimal> configs() {
        Map<String,BigDecimal> values = new HashMap<>();
        values.put("si_personal",DEFAULT_SI_PERSONAL);
        values.put("fund_personal",DEFAULT_FUND_PERSONAL);
        values.put("si_company",DEFAULT_SI_COMPANY);
        values.put("fund_company",DEFAULT_FUND_COMPANY);
        values.put("tax_threshold",DEFAULT_TAX_THRESHOLD);
        values.put("tax_rate",DEFAULT_TAX_RATE);
        for (Map<String,Object> row : payroll.configs()) {
            Object key = row.get("configKey");
            Object value = row.get("configValue");
            if (key != null && value != null) values.put(String.valueOf(key),new BigDecimal(value.toString()));
        }
        return values;
    }

    private static void validatePeriod(String wagePeriod) {
        if (wagePeriod == null) throw new ServiceException("员工与工资期必填",1001);
        try {
            YearMonth month = YearMonth.parse(wagePeriod);
            if (!month.toString().equals(wagePeriod) || month.isAfter(YearMonth.now())) {
                throw new ServiceException("工资期不合法",1001);
            }
        } catch (DateTimeParseException e) {
            throw new ServiceException("工资期应为 yyyy-MM",1001);
        }
    }

    private static BigDecimal percent(BigDecimal base,BigDecimal rate) {
        return money(base.multiply(rate).divide(HUNDRED,10,RoundingMode.HALF_UP));
    }

    private static BigDecimal money(BigDecimal value) { return value.setScale(2,RoundingMode.HALF_UP); }

    private static BigDecimal value(Map<String,Object> row,String key) { return row == null ? BigDecimal.ZERO : decimal(row,key); }

    private static BigDecimal decimal(Map<String,Object> row,String key) {
        Object value = row.get(key);
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }

    private static Long number(Map<String,Object> row,String key) { return ((Number)row.get(key)).longValue(); }
}

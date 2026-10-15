package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.SalaryRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpSalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpSalaryService {
    private final ErpSalaryMapper mapper;
    public ErpSalaryService(ErpSalaryMapper mapper) { this.mapper=mapper; }

    @Transactional
    public Map<String,String> generate(SalaryRequest request) {
        if (request == null || request.empId() == null || request.wagePeriod() == null) throw new ServiceException("员工与工资期必填",1001);
        try {
            if (!YearMonth.parse(request.wagePeriod()).toString().equals(request.wagePeriod())
                    || YearMonth.parse(request.wagePeriod()).isAfter(YearMonth.now())) throw new ServiceException("工资期不合法",1001);
        } catch (DateTimeParseException e) { throw new ServiceException("工资期应为 yyyy-MM",1001); }
        Map<String,Object> employee = mapper.employee(request.empId());
        if (employee == null) throw new ServiceException("员工不存在",1001);
        Map<String,Object> previous = mapper.existing(request.empId(),request.wagePeriod());
        if (previous != null && number(previous,"status").intValue() == 1) throw new ServiceException("工资单已确认",2001);
        BigDecimal base = decimal(employee,"baseSalary");
        BigDecimal piece = mapper.pieceTotal(request.empId(),request.wagePeriod());
        BigDecimal gross = base.add(piece).add(value(previous,"allowance")).add(value(previous,"overtimePay"));
        BigDecimal net = gross.subtract(value(previous,"siPersonal")).subtract(value(previous,"fundPersonal"))
                .subtract(value(previous,"tax")).subtract(value(previous,"otherDeduction"));
        if (net.signum() < 0) throw new ServiceException("实发工资不能为负",2006);
        Map<String,Object> row = new HashMap<>();
        row.put("empId",request.empId()); row.put("period",request.wagePeriod());
        row.put("base",base); row.put("piece",piece); row.put("gross",gross); row.put("net",net);
        row.put("companyCost",gross.add(value(previous,"siCompany")).add(value(previous,"fundCompany")));
        row.put("username",SecurityUtils.getUsername());
        if (previous == null) mapper.insert(row);
        else { row.put("id",number(previous,"id")); if (mapper.refresh(row) != 1) throw new ServiceException("工资单状态已变化",2001); }
        return Map.of("id",number(row,"id").toString());
    }

    @Transactional
    public void confirm(Long id) {
        Long empId = mapper.slipEmployee(id);
        if (empId == null) throw new ServiceException("工资单不存在",1001);
        mapper.employee(empId);
        Map<String,Object> row = mapper.lockSlip(id);
        String period = (String)row.get("wagePeriod");
        if (number(row,"status").intValue() != 0) throw new ServiceException("工资单状态不允许确认",2001);
        if (mapper.pieceTotal(empId,period).compareTo(decimal(row,"pieceAmount")) != 0) throw new ServiceException("计件明细已变化，请重新生成工资单",2006);
        if (mapper.confirm(id) != 1) throw new ServiceException("工资单状态已变化",2001);
        mapper.markWagesPaid(empId,period);
    }

    private static BigDecimal value(Map<String,Object> row,String key) { return row == null ? BigDecimal.ZERO : decimal(row,key); }
    private static BigDecimal decimal(Map<String,Object> row,String key) { return (BigDecimal)row.get(key); }
    private static Long number(Map<String,Object> row,String key) { return ((Number)row.get(key)).longValue(); }
}

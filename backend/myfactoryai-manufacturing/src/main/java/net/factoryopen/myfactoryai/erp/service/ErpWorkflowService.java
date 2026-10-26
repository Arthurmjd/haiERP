package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.mapper.ErpWorkflowMapper;
import org.springframework.stereotype.Service;

@Service
public class ErpWorkflowService {
    private static final DateTimeFormatter MONTH = DateTimeFormatter.ofPattern("yyyy-MM");
    private final ErpWorkflowMapper mapper;

    public ErpWorkflowService(ErpWorkflowMapper mapper) { this.mapper = mapper; }

    public void todo(String todoType, String targetType, Long targetId, String role,
                     String title, String path, LocalDate dueDate) {
        mapper.insertTodo(todoType, targetType, targetId, role, title, path, dueDate);
    }

    public void complete(String todoType, String targetType, Long targetId) {
        mapper.completeTodo(todoType, targetType, targetId, SecurityUtils.getUsername());
    }

    public List<Map<String,Object>> myTodos() {
        Set<String> roles = roles();
        return mapper.openTodos().stream()
                .filter(row -> roles.contains(String.valueOf(row.get("assigneeRole"))))
                .toList();
    }

    public List<Map<String,Object>> pendingApprovals() { return mapper.pendingApprovals(); }

    public void requestApproval(String docType, Long docId, String type, BigDecimal amount) {
        mapper.insertApproval(docType, docId, type, amount, SecurityUtils.getUsername());
    }

    public void decide(String docType, Long docId, boolean approved, String comment) {
        Map<String,Object> pending = mapper.lockApproval(docType, docId);
        if (pending == null) throw new ServiceException("没有待审批记录", 2001);
        mapper.decideApproval(((Number) pending.get("approvalId")).longValue(), approved ? 1 : 2,
                SecurityUtils.getUsername(), comment == null ? "" : comment);
    }

    public BigDecimal purchaseThreshold() {
        String value = mapper.configValue("erp.purchase.approval.threshold");
        if (value == null || value.isBlank()) return new BigDecimal("10000");
        try { return new BigDecimal(value); } catch (NumberFormatException e) { return new BigDecimal("10000"); }
    }

    public void assertOpen(LocalDate date) {
        if (date != null) assertOpenPeriod(date.format(MONTH));
    }

    public void assertOpenPeriod(String period) {
        if (period != null && Integer.valueOf(1).equals(mapper.periodStatus(period))) {
            throw new ServiceException("会计期间 " + period + " 已结账，不能补录或过账", 2001);
        }
    }

    public void closePeriod(String period) {
        if (period == null || !period.matches("\\d{4}-\\d{2}")) throw new ServiceException("期间格式应为 yyyy-MM", 1001);
        mapper.closePeriod(period, SecurityUtils.getUsername());
    }

    public void reopenPeriod(String period) {
        if (period == null || !period.matches("\\d{4}-\\d{2}")) throw new ServiceException("期间格式应为 yyyy-MM", 1001);
        if (mapper.reopenPeriod(period, SecurityUtils.getUsername()) != 1) throw new ServiceException("期间未关闭或不存在", 2001);
    }

    public List<Map<String,Object>> periods() { return mapper.periods(); }

    private Set<String> roles() {
        try {
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if (roles == null) return Set.of();
            return roles.stream().map(SysRole::getRoleKey).filter(key -> key != null).collect(Collectors.toSet());
        } catch (RuntimeException e) {
            return Set.of();
        }
    }
}

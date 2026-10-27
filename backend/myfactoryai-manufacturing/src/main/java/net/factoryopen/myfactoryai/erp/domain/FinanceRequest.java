package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class FinanceRequest {
    private FinanceRequest() {}

    public record Account(Long accountId, String accountNo, String accountName, Integer accountType,
                          BigDecimal openingBalance, Integer status, String remark) {}

    public record Payment(Long accountId, Integer direction, Integer bizType, Integer counterpartyType,
                          Long counterpartyId, BigDecimal amount, LocalDate payDate,
                          List<Line> lines, String remark, String requestKey) {}

    public record Line(Integer targetType, Long targetId, BigDecimal amount) {}

    public record Action(String requestKey) {}

    public record Period(String period) {}
}

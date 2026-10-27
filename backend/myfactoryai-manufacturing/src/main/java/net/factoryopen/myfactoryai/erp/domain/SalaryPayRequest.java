package net.factoryopen.myfactoryai.erp.domain;

import java.time.LocalDate;

public record SalaryPayRequest(Long accountId, LocalDate payDate, String requestKey) {
    public record Batch(String wagePeriod) {}
}

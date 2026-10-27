package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record QuotationDraftRequest(Long customerId, LocalDate quoteDate, List<Line> items) {
    public record Line(Long styleId, Integer qty, BigDecimal unitPrice, BigDecimal costRef) {}
}

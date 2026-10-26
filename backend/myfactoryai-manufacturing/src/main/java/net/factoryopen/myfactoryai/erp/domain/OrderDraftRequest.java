package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDraftRequest(Long customerId, LocalDate deliveryDate, List<Line> items, Long quoteId) {
    public OrderDraftRequest(Long customerId, LocalDate deliveryDate, List<Line> items) {
        this(customerId, deliveryDate, items, null);
    }

    public record Line(Long styleId, Long colorId, Long sizeId, Integer qty, BigDecimal unitPrice) {}
}

package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseReceiptRequest(Long poId, LocalDate actualArrivalDate, List<Line> items, String requestKey) {
    public record Line(Long itemId, BigDecimal qty, String batchNo) {}
}

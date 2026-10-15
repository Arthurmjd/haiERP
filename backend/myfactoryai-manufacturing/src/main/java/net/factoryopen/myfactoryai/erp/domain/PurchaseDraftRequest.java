package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseDraftRequest(Long supplierId, LocalDate expectDate, List<Line> items) {
    public record Line(Long materialId, BigDecimal qty, BigDecimal unitPrice) {}
}

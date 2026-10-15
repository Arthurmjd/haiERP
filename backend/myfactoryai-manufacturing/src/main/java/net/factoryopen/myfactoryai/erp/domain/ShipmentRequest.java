package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.util.List;

public final class ShipmentRequest {
    private ShipmentRequest() {}
    public record Line(Long skuId, String batchNo, Integer qty) {}
    public record Create(Long orderId, String logisticsCompany, String logisticsNo, List<Line> items) {}
    public record Action(String requestKey) {}
    public record ReturnCreate(Long shipmentId, Integer returnType, Integer isRestock,
                               String reason, BigDecimal refundAmount, List<Line> items) {}
}

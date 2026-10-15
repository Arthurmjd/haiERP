package net.factoryopen.myfactoryai.erp.domain;

import java.time.LocalDate;
import java.util.List;

public final class WorkOrderRequest {
    private WorkOrderRequest() {}

    public record Create(Long orderId, Long styleId) {}
    public record Action(String requestKey) {}
    public record Report(Long workOrderProcessId, Long skuId, Long empId,
                         Integer doneQty, Integer badQty, LocalDate reportDate, String requestKey) {}
    public record Finish(List<FinishedSku> skuItems, String requestKey) {}
    public record FinishedSku(Long skuId, Integer goodQty, String batchNo) {}
}

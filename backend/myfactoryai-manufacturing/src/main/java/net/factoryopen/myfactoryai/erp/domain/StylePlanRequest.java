package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;
import java.util.List;

public final class StylePlanRequest {
    private StylePlanRequest() {}
    public record BomLine(Long materialId,BigDecimal qty,BigDecimal lossRate) {}
    public record ProcessLine(Long processId,Integer seq,BigDecimal unitPrice) {}
    public record Bom(List<BomLine> items) {}
    public record Processes(List<ProcessLine> items) {}
}

package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseHeader {
    private Long poId;
    private String poNo;
    private Long supplierId;
    private LocalDate expectDate;
    private BigDecimal totalAmount;
    private String createdBy;

    public Long getPoId() { return poId; }
    public void setPoId(Long poId) { this.poId = poId; }
    public String getPoNo() { return poNo; }
    public void setPoNo(String poNo) { this.poNo = poNo; }
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public LocalDate getExpectDate() { return expectDate; }
    public void setExpectDate(LocalDate expectDate) { this.expectDate = expectDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}

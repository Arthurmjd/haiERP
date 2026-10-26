package net.factoryopen.myfactoryai.erp.mapper;

import java.time.LocalDate;

public class WorkOrderHeader {
    private Long woId;
    private String woNo;
    private Long orderId;
    private Long styleId;
    private Integer qty;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private String createdBy;
    public Long getWoId() { return woId; }
    public void setWoId(Long woId) { this.woId = woId; }
    public String getWoNo() { return woNo; }
    public void setWoNo(String woNo) { this.woNo = woNo; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getStyleId() { return styleId; }
    public void setStyleId(Long styleId) { this.styleId = styleId; }
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
    public LocalDate getPlanStartDate() { return planStartDate; }
    public void setPlanStartDate(LocalDate planStartDate) { this.planStartDate = planStartDate; }
    public LocalDate getPlanEndDate() { return planEndDate; }
    public void setPlanEndDate(LocalDate planEndDate) { this.planEndDate = planEndDate; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}

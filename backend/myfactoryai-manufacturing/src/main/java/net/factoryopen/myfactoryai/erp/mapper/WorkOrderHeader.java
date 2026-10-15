package net.factoryopen.myfactoryai.erp.mapper;

public class WorkOrderHeader {
    private Long woId;
    private String woNo;
    private Long orderId;
    private Long styleId;
    private Integer qty;
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
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}

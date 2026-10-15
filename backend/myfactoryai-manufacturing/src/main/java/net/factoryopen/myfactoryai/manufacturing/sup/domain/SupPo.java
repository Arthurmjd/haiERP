package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购单管理对象 sup_po
 * 
 * @author admin
 * @date 2024-09-23
 */
public class SupPo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 采购单号 */
    @Excel(name = "采购单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 采购类型 */
    @Excel(name = "采购类型")
    private Integer purchasingType;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 未含税金额 */
    @Excel(name = "未含税金额")
    private BigDecimal amountUntaxed;

    /** 含税金额 */
    @Excel(name = "含税金额")
    private BigDecimal amountTaxed;

    /** 开票方式ID */
    @Excel(name = "开票方式ID")
    private Long invoicePatternId;

    /** 付款方式ID */
    @Excel(name = "付款方式ID")
    private Long payPatternId;

    /** 币别 */
    @Excel(name = "币别")
    private Long currencyId;

    /** 交货方式ID */
    @Excel(name = "交货方式ID")
    private Long shipmentTypeId;

    /** 汇率 */
    @Excel(name = "汇率")
    private BigDecimal exchangeRate;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDay;

    /** 联系人ID */
    @Excel(name = "联系人ID")
    private Long contactId;

    /** 采购员ID */
    @Excel(name = "采购员ID")
    private Long buyerId;

    /** 采购部门ID */
    @Excel(name = "采购部门ID")
    private Long buyerDeptId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPurchasingType() {
        return purchasingType;
    }

    public void setPurchasingType(Integer purchasingType) {
        this.purchasingType = purchasingType;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getAmountUntaxed() {
        return amountUntaxed;
    }

    public void setAmountUntaxed(BigDecimal amountUntaxed) {
        this.amountUntaxed = amountUntaxed;
    }

    public BigDecimal getAmountTaxed() {
        return amountTaxed;
    }

    public void setAmountTaxed(BigDecimal amountTaxed) {
        this.amountTaxed = amountTaxed;
    }

    public Long getInvoicePatternId() {
        return invoicePatternId;
    }

    public void setInvoicePatternId(Long invoicePatternId) {
        this.invoicePatternId = invoicePatternId;
    }

    public Long getPayPatternId() {
        return payPatternId;
    }

    public void setPayPatternId(Long payPatternId) {
        this.payPatternId = payPatternId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getShipmentTypeId() {
        return shipmentTypeId;
    }

    public void setShipmentTypeId(Long shipmentTypeId) {
        this.shipmentTypeId = shipmentTypeId;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(Date orderDay) {
        this.orderDay = orderDay;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getBuyerDeptId() {
        return buyerDeptId;
    }

    public void setBuyerDeptId(Long buyerDeptId) {
        this.buyerDeptId = buyerDeptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("purchasingType", getPurchasingType())
            .append("supplierId", getSupplierId())
            .append("amountUntaxed", getAmountUntaxed())
            .append("amountTaxed", getAmountTaxed())
            .append("invoicePatternId", getInvoicePatternId())
            .append("payPatternId", getPayPatternId())
            .append("currencyId", getCurrencyId())
            .append("shipmentTypeId", getShipmentTypeId())
            .append("exchangeRate", getExchangeRate())
            .append("orderDay", getOrderDay())
            .append("contactId", getContactId())
            .append("buyerId", getBuyerId())
            .append("buyerDeptId", getBuyerDeptId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

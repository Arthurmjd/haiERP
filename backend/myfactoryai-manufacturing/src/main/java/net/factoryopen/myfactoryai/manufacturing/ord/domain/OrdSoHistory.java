package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单历史查询对象 ord_so
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class OrdSoHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 合同号 */
    @Excel(name = "合同号")
    private String contractNo;

    /** 未含税金额 */
    @Excel(name = "未含税金额")
    private BigDecimal amountUntaxed;

    /** 含税金额 */
    @Excel(name = "含税金额")
    private BigDecimal amountTaxed;

    /** 开票方式 */
    @Excel(name = "开票方式")
    private Long invoicePatternId;

    /** 付款方式 */
    @Excel(name = "付款方式")
    private Long payPatternId;

    /** 币别 */
    @Excel(name = "币别")
    private Long currencyId;

    /** 交货方式 */
    @Excel(name = "交货方式")
    private Long shipmentTypeId;

    /** 汇率% */
    @Excel(name = "汇率%")
    private BigDecimal exchangeRate;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDay;

    /** 地址 */
    @Excel(name = "地址")
    private Long addressId;

    /** 联系人 */
    @Excel(name = "联系人")
    private Long contactId;

    /** 订单类型 */
    @Excel(name = "订单类型")
    private Integer soType;

    /** 项目 */
    @Excel(name = "项目")
    private Long projectId;

    /** 销售员 */
    @Excel(name = "销售员")
    private Long salesId;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Integer getSoType() {
        return soType;
    }

    public void setSoType(Integer soType) {
        this.soType = soType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "OrdSo{" +
                "id=" + id +
                ", billNo='" + billNo + '\'' +
                ", status=" + status +
                ", customerId=" + customerId +
                ", contractNo='" + contractNo + '\'' +
                ", amountUntaxed=" + amountUntaxed +
                ", amountTaxed=" + amountTaxed +
                ", invoicePatternId=" + invoicePatternId +
                ", payPatternId=" + payPatternId +
                ", currencyId=" + currencyId +
                ", shipmentTypeId=" + shipmentTypeId +
                ", exchangeRate=" + exchangeRate +
                ", orderDay=" + orderDay +
                ", addressId=" + addressId +
                ", contactId=" + contactId +
                ", soType=" + soType +
                ", projectId=" + projectId +
                ", salesId=" + salesId +
                ", deptId=" + deptId +
                '}';
    }
}

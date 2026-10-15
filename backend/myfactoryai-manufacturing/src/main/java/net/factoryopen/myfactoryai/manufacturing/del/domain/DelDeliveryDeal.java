package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 交货筹划对象 ord_so_item
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class DelDeliveryDeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 销售ID */
    @Excel(name = "销售ID")
    private Long soId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String soNo;

    /** 订单项 */
    @Excel(name = "订单项")
    private String seqNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 客户编码 */
    @Excel(name = "客户编码")
    private String customerNo;

    /** 客户编码 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 订单数量 */
    @Excel(name = "订单数量")
    private BigDecimal quantityOrder;

    /** 原价 */
    private BigDecimal price;

    /** 折扣价 */
    private BigDecimal discountPrice;

    /** 金额 */
    private BigDecimal amount;

    /** 折扣率% */
    private BigDecimal discountRate;

    /** 最终金额 */
    private BigDecimal finalAmount;

    /** 在发数量 */
    @Excel(name = "发货数量")
    private BigDecimal quantityDelivering;

    /** 交付数量 */
    @Excel(name = "完发数量")
    private BigDecimal quantityDelivered;

    /** 转包数量 */
    @Excel(name = "转包数量")
    private BigDecimal quantitySubcontract;

    /** 完包数量 */
    @Excel(name = "完包数量")
    private BigDecimal quantitySubcontracted;

    /** 自制数量 */
    @Excel(name = "自制数量")
    private BigDecimal quantityMps;

    /** 完制数量 */
    @Excel(name = "完制数量")
    private BigDecimal quantityManufactured;

    /** 自制数量 */
    @Excel(name = "库存数量")
    private BigDecimal quantityQualified;

    /** 自制数量 */
    @Excel(name = "预约数量")
    private BigDecimal  quantityBooked;

    /** 可用数量 */
    @Excel(name = "可用数量")
    private BigDecimal  quantityAvailable;

    /** 欠发数量 */
    @Excel(name = "欠交数量")
    private BigDecimal quantityDeliveredLack;

    /** 欠发数量 */
    @Excel(name = "欠发数量")
    private BigDecimal quantityDeliveringLack;

    /** 欠备数量 */
    @Excel(name = "欠备数量")
    private BigDecimal quantityPrepareLack;

    /** 开票数量 */
    @Excel(name = "开票数量")
    private BigDecimal quantityInvoiced;

    /** 欠备数量 */
    @Excel(name = "开票金额")
    private BigDecimal amountInvoiced;

    /** 优先级 */
    @Excel(name = "优先级")
    private Integer priority;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date needDay;

    /** 承诺交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "承诺交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date promiseDay;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSoId(Long soId) 
    {
        this.soId = soId;
    }

    public Long getSoId() 
    {
        return soId;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

    public String getSeqNo() 
    {
        return seqNo;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setProductSpec(String productSpec) 
    {
        this.productSpec = productSpec;
    }

    public String getProductSpec() 
    {
        return productSpec;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }

    public String getCustomerProductNo() {
        return customerProductNo;
    }

    public void setCustomerProductNo(String customerProductNo) {
        this.customerProductNo = customerProductNo;
    }

    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    public void setQuantityOrder(BigDecimal quantityOrder) 
    {
        this.quantityOrder = quantityOrder;
    }

    public BigDecimal getQuantityOrder() 
    {
        return quantityOrder;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setDiscountPrice(BigDecimal discountPrice) 
    {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountPrice() 
    {
        return discountPrice;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setDiscountRate(BigDecimal discountRate) 
    {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountRate() 
    {
        return discountRate;
    }
    public void setFinalAmount(BigDecimal finalAmount) 
    {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getFinalAmount() 
    {
        return finalAmount;
    }

    public BigDecimal getQuantityDelivering() {
        return quantityDelivering;
    }

    public void setQuantityDelivering(BigDecimal quantityDelivering) {
        this.quantityDelivering = quantityDelivering;
    }

    public BigDecimal getQuantityDelivered() {
        return quantityDelivered;
    }

    public void setQuantityDelivered(BigDecimal quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
    }

    public BigDecimal getQuantitySubcontract() {
        return quantitySubcontract;
    }

    public void setQuantitySubcontract(BigDecimal quantitySubcontract) {
        this.quantitySubcontract = quantitySubcontract;
    }

    public BigDecimal getQuantitySubcontracted() {
        return quantitySubcontracted;
    }

    public void setQuantitySubcontracted(BigDecimal quantitySubcontracted) {
        this.quantitySubcontracted = quantitySubcontracted;
    }

    public BigDecimal getQuantityMps() {
        return quantityMps;
    }

    public void setQuantityMps(BigDecimal quantityMps) {
        this.quantityMps = quantityMps;
    }

    public BigDecimal getQuantityManufactured() {
        return quantityManufactured;
    }

    public void setQuantityManufactured(BigDecimal quantityManufactured) {
        this.quantityManufactured = quantityManufactured;
    }

    public BigDecimal getQuantityQualified() {
        return quantityQualified;
    }

    public void setQuantityQualified(BigDecimal quantityQualified) {
        this.quantityQualified = quantityQualified;
    }

    public BigDecimal getQuantityBooked() {
        return quantityBooked;
    }

    public void setQuantityBooked(BigDecimal quantityBooked) {
        this.quantityBooked = quantityBooked;
    }

    public BigDecimal getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(BigDecimal quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public BigDecimal getQuantityDeliveredLack() {
        return quantityDeliveredLack;
    }

    public void setQuantityDeliveredLack(BigDecimal quantityDeliveredLack) {
        this.quantityDeliveredLack = quantityDeliveredLack;
    }

    public BigDecimal getQuantityDeliveringLack() {
        return quantityDeliveringLack;
    }

    public void setQuantityDeliveringLack(BigDecimal quantityDeliveringLack) {
        this.quantityDeliveringLack = quantityDeliveringLack;
    }

    public BigDecimal getQuantityPrepareLack() {
        return quantityPrepareLack;
    }

    public BigDecimal getQuantityInvoiced() {
        return quantityInvoiced;
    }

    public void setQuantityInvoiced(BigDecimal quantityInvoiced) {
        this.quantityInvoiced = quantityInvoiced;
    }

    public BigDecimal getAmountInvoiced() {
        return amountInvoiced;
    }

    public void setAmountInvoiced(BigDecimal amountInvoiced) {
        this.amountInvoiced = amountInvoiced;
    }

    public void setQuantityPrepareLack(BigDecimal quantityPrepareLack) {
        this.quantityPrepareLack = quantityPrepareLack;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public Integer getPriority() 
    {
        return priority;
    }

    public void setNeedDay(Date needDay) 
    {
        this.needDay = needDay;
    }

    public Date getNeedDay() 
    {
        return needDay;
    }

    public void setPromiseDay(Date promiseDay) 
    {
        this.promiseDay = promiseDay;
    }

    public Date getPromiseDay() 
    {
        return promiseDay;
    }
}

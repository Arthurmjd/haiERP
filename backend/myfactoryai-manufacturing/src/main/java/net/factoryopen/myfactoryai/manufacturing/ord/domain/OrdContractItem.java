package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同明细对象 ord_contract_item
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class OrdContractItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 销售合同 */
    @Excel(name = "销售合同")
    private Long contractId;

    /** 合同项 */
    @Excel(name = "合同项")
    private String seqNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

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

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 合同数量 */
    @Excel(name = "合同数量")
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

    public void setContractId(Long contractId)
    {
        this.contractId = contractId;
    }

    public Long getContractId()
    {
        return contractId;
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

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getAbroadProductNo() {
        return abroadProductNo;
    }

    public void setAbroadProductNo(String abroadProductNo) {
        this.abroadProductNo = abroadProductNo;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("contractId", getContractId())
                .append("seqNo", getSeqNo())
                .append("status", getStatus())
                .append("productId", getProductId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("productSpec", getProductSpec())
                .append("drawNo", getDrawNo())
                .append("customerProductNo", getCustomerProductNo())
                .append("abroadProductNo", getAbroadProductNo())
                .append("unitId", getUnitId())
                .append("quantityOrder", getQuantityOrder())
                .append("price", getPrice())
                .append("discountPrice", getDiscountPrice())
                .append("amount", getAmount())
                .append("discountRate", getDiscountRate())
                .append("finalAmount", getFinalAmount())
                .append("quantityDelivered", getQuantityDelivered())
                .append("quantityDelivering", getQuantityDelivering())
                .append("quantitySubcontract", getQuantitySubcontract())
                .append("quantitySubcontracted", getQuantitySubcontracted())
                .append("quantityMps", getQuantityMps())
                .append("quantityManufactured", getQuantityManufactured())
                .append("priority", getPriority())
                .append("needDay", getNeedDay())
                .append("promiseDay", getPromiseDay())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

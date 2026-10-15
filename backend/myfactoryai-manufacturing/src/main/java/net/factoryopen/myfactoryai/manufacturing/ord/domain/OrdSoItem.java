package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单明细对象 ord_so_item
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class OrdSoItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 销售订单 */
    @Excel(name = "销售订单")
    private Long soId;

    /** 订单项 */
    @Excel(name = "订单项")
    private String seqNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 合同明细ID */
    @Excel(name = "合同明细ID")
    private Long contractItemId;

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
    private String productSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 产品名称 */
    @Excel(name = "合同产品名称")
    private String cProductName;

    /** 产品规格 */
    @Excel(name = "合同产品规格")
    private String cProductSpec;

    /** 图号 */
    @Excel(name = "合同图号")
    private String cDrawNo;

    /** 客户料号 */
    @Excel(name = "合同客户料号")
    private String cCustomerProductNo;

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getContractItemId() {
        return contractItemId;
    }

    public void setContractItemId(Long contractItemId) {
        this.contractItemId = contractItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
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

    public String getCProductName() {
        return cProductName;
    }

    public void setCProductName(String cProductName) {
        this.cProductName = cProductName;
    }

    public String getCProductSpec() {
        return cProductSpec;
    }

    public void setCProductSpec(String cProductSpec) {
        this.cProductSpec = cProductSpec;
    }

    public String getCDrawNo() {
        return cDrawNo;
    }

    public void setCDrawNo(String cDrawNo) {
        this.cDrawNo = cDrawNo;
    }

    public String getCCustomerProductNo() {
        return cCustomerProductNo;
    }

    public void setCCustomerProductNo(String cCustomerProductNo) {
        this.cCustomerProductNo = cCustomerProductNo;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(BigDecimal quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getNeedDay() {
        return needDay;
    }

    public void setNeedDay(Date needDay) {
        this.needDay = needDay;
    }

    public Date getPromiseDay() {
        return promiseDay;
    }

    public void setPromiseDay(Date promiseDay) {
        this.promiseDay = promiseDay;
    }

    @Override
    public String toString() {
        return "OrdSoItem{" +
                "id=" + id +
                ", soId=" + soId +
                ", seqNo='" + seqNo + '\'' +
                ", status=" + status +
                ", contractItemId=" + contractItemId +
                ", productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productSpec='" + productSpec + '\'' +
                ", drawNo='" + drawNo + '\'' +
                ", customerProductNo='" + customerProductNo + '\'' +
                ", cProductName='" + cProductName + '\'' +
                ", cProductSpec='" + cProductSpec + '\'' +
                ", cDrawNo='" + cDrawNo + '\'' +
                ", cCustomerProductNo='" + cCustomerProductNo + '\'' +
                ", unitId=" + unitId +
                ", quantityOrder=" + quantityOrder +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", amount=" + amount +
                ", discountRate=" + discountRate +
                ", finalAmount=" + finalAmount +
                ", quantityDelivering=" + quantityDelivering +
                ", quantityDelivered=" + quantityDelivered +
                ", quantitySubcontract=" + quantitySubcontract +
                ", quantitySubcontracted=" + quantitySubcontracted +
                ", quantityMps=" + quantityMps +
                ", quantityManufactured=" + quantityManufactured +
                ", priority=" + priority +
                ", needDay=" + needDay +
                ", promiseDay=" + promiseDay +
                '}';
    }
}

package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 交货筹划对象 ord_so_item
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class OrdSoFinance extends BaseEntity
{

    /** ID */
    private Long id;

    /** 客户编码 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 销售订单 */
    private Long soId;

    /** 销售订单 */
    @Excel(name = "订单编号")
    private String  soNo;

    /** 订单项 */
    @Excel(name = "订单项")
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
    @Excel(name = "单价")
    private BigDecimal price;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 交付数量 */
    @Excel(name = "完发数量")
    private BigDecimal quantityDelivered;

    /** 开票数量 */
    @Excel(name = "开票数量")
    private BigDecimal quantityInvoiced;

    /** 欠备数量 */
    @Excel(name = "开票金额")
    private BigDecimal amountInvoiced;

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
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public BigDecimal getQuantityDelivered() {
        return quantityDelivered;
    }

    public void setQuantityDelivered(BigDecimal quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("soId", getSoId())
                .append("soNo", getSoNo())
                .append("seqNo", getSeqNo())
                .append("status", getStatus())
                .append("productId", getProductId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("productSpec", getProductSpec())
                .append("drawNo", getDrawNo())
                .append("customerProductNo", getCustomerProductNo())
                .append("unitId", getUnitId())
                .append("quantityOrder", getQuantityOrder())
                .append("price", getPrice())
                .append("amount", getAmount())
                .append("quantityDelivered", getQuantityDelivered())
                .append("quantityInvoiced", getQuantityInvoiced())
                .append("amountInvoiced", getAmountInvoiced())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

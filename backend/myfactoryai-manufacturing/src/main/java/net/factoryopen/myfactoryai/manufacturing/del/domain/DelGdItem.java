package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发货单明细对象 del_gd_item
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class DelGdItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 发货单 */
    @Excel(name = "发货单")
    private Long gdId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 销售订单 */
    @Excel(name = "发货计划")
    private Long dpId;

    /** 销售订单 */
    @Excel(name = "销售订单")
    private Long soId;

    /** 订单项 */
    @Excel(name = "订单项")
    private Long soItemId;

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

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 数量 */
    @Excel(name = "已发数量")
    private BigDecimal quantityDelivered;

    /** 单价 */
    private BigDecimal price;

    /** 价折扣 */
    private BigDecimal discountPrice;

    /** 额折扣 */
    private BigDecimal discountAmount;

    /** 金额 */
    private BigDecimal amount;

    /** 质保始日 */
    private Date serviceStartday;

    /** 质保终日 */
    private Date serviceEndday;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long houseId;

    /** 储位 */
    @Excel(name = "储位")
    private Long positionId;

    /** 批号 */
    @Excel(name = "批号")
    private String lotNo;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getGdId() {
        return gdId;
    }

    public void setGdId(Long gdId) {
        this.gdId = gdId;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Long getDpId() {
        return dpId;
    }

    public void setDpId(Long dpId) {
        this.dpId = dpId;
    }

    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public Long getSoItemId() {
        return soItemId;
    }

    public void setSoItemId(Long soItemId) {
        this.soItemId = soItemId;
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

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantityDelivered() {
        return quantityDelivered;
    }

    public void setQuantityDelivered(BigDecimal quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getServiceStartday() {
        return serviceStartday;
    }

    public void setServiceStartday(Date serviceStartday) {
        this.serviceStartday = serviceStartday;
    }

    public Date getServiceEndday() {
        return serviceEndday;
    }

    public void setServiceEndday(Date serviceEndday) {
        this.serviceEndday = serviceEndday;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gdId", getGdId())
            .append("seqNo", getSeqNo())
            .append("dpId", getDpId())
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("abroadProductNo", getAbroadProductNo())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("quantityDelivered", getQuantityDelivered())
            .append("price", getPrice())
            .append("discountPrice", getDiscountPrice())
            .append("discountAmount", getDiscountAmount())
            .append("amount", getAmount())
            .append("serviceStartday", getServiceStartday())
            .append("serviceEndday", getServiceEndday())
            .append("houseId", getHouseId())
            .append("positionId", getPositionId())
            .append("lotNo", getLotNo())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

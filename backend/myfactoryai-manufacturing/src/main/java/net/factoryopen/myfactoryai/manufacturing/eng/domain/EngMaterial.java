package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 外购料管理对象 eng_material
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 品类 */
    @Excel(name = "品类")
    private Long categoryId;

    /** 材质 */
    @Excel(name = "材质")
    private Long compoundId;

    /** 基本类型 */
    private String baseType;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 合格数量 */
    @Excel(name = "合格数量")
    private BigDecimal quantityQualified;

    /** 报废数量 */
    @Excel(name = "报废数量")
    private BigDecimal quantityScraped;

    /** 过期数量 */
    @Excel(name = "过期数量")
    private BigDecimal quantityExpired;

    /** 冻结数量 */
    @Excel(name = "冻结数量")
    private BigDecimal quantityFrozen;

    /** 待判数量 */
    @Excel(name = "待判数量")
    private BigDecimal quantityPending;

    /** 预约量 */
    @Excel(name = "预约量")
    private BigDecimal quantityBooked;

    /** 主仓库 */
    @Excel(name = "主仓库")
    private Long houseId;

    /** 主储位 */
    @Excel(name = "主储位")
    private Long positionId;

    /** 预约量 */
    @Excel(name = "内控价")
    private BigDecimal priceLast;

    /** 预约量 */
    @Excel(name = "采购价")
    private BigDecimal priceBuy;

    /** 预约量 */
    @Excel(name = "销售价")
    private BigDecimal priceSale;

    /** 采购类型 */
    @Excel(name = "采购类型")
    private String purchaseType;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private String consumeType;

    /** 产线ID */
    private Long lineId;

    /** 生产损耗比 */
    @Excel(name = "生产损耗比")
    private BigDecimal rateLoss;

    /** 采购周期 */
    @Excel(name = "采购周期")
    private BigDecimal daysCycletime;

    /** 提前时间 */
    @Excel(name = "提前时间")
    private BigDecimal daysLeadtime;

    /** 后置时间 */
    @Excel(name = "后置时间")
    private BigDecimal daysPosttime;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialSpec() {
        return materialSpec;
    }

    public void setMaterialSpec(String materialSpec) {
        this.materialSpec = materialSpec;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(Long compoundId) {
        this.compoundId = compoundId;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getQuantityQualified() {
        return quantityQualified;
    }

    public void setQuantityQualified(BigDecimal quantityQualified) {
        this.quantityQualified = quantityQualified;
    }

    public BigDecimal getQuantityScraped() {
        return quantityScraped;
    }

    public void setQuantityScraped(BigDecimal quantityScraped) {
        this.quantityScraped = quantityScraped;
    }

    public BigDecimal getQuantityExpired() {
        return quantityExpired;
    }

    public void setQuantityExpired(BigDecimal quantityExpired) {
        this.quantityExpired = quantityExpired;
    }

    public BigDecimal getQuantityFrozen() {
        return quantityFrozen;
    }

    public void setQuantityFrozen(BigDecimal quantityFrozen) {
        this.quantityFrozen = quantityFrozen;
    }

    public BigDecimal getQuantityPending() {
        return quantityPending;
    }

    public void setQuantityPending(BigDecimal quantityPending) {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityBooked() {
        return quantityBooked;
    }

    public void setQuantityBooked(BigDecimal quantityBooked) {
        this.quantityBooked = quantityBooked;
    }
    public void setHouseId(Long houseId)
    {
        this.houseId = houseId;
    }

    public Long getHouseId()
    {
        return houseId;
    }
    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }

    public Long getPositionId()
    {
        return positionId;
    }

    public BigDecimal getPriceLast() {
        return priceLast;
    }

    public void setPriceLast(BigDecimal priceLast) {
        this.priceLast = priceLast;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public BigDecimal getRateLoss() {
        return rateLoss;
    }

    public void setRateLoss(BigDecimal rateLoss) {
        this.rateLoss = rateLoss;
    }

    public BigDecimal getDaysCycletime() {
        return daysCycletime;
    }

    public void setDaysCycletime(BigDecimal daysCycletime) {
        this.daysCycletime = daysCycletime;
    }

    public BigDecimal getDaysLeadtime() {
        return daysLeadtime;
    }

    public void setDaysLeadtime(BigDecimal daysLeadtime) {
        this.daysLeadtime = daysLeadtime;
    }

    public BigDecimal getDaysPosttime() {
        return daysPosttime;
    }

    public void setDaysPosttime(BigDecimal daysPosttime) {
        this.daysPosttime = daysPosttime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(BigDecimal priceBuy) {
        this.priceBuy = priceBuy;
    }

    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }
}

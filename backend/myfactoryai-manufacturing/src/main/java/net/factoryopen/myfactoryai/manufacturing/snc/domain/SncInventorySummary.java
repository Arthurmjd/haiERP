package net.factoryopen.myfactoryai.manufacturing.snc.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存总览表对象 eng_material
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
public class SncInventorySummary extends BaseEntity
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

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 品类 */
    @Excel(name = "品类")
    private Long categoryId;

    /** 基本类型 */
    @Excel(name = "基本类型")
    private String baseType;

    /** 采购类型 */
    @Excel(name = "采购类型")
    private String purchaseType;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private String consumeType;

    /** 产线ID */
    private Long lineId;

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

    /** 交易价格 */
    @Excel(name = "价格")
    private BigDecimal priceLast;

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

    /** 预约量 */
    @Excel(name = "可用量")
    private BigDecimal quantityAvailable;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMaterialCode(String materialCode) 
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() 
    {
        return materialCode;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setMaterialSpec(String materialSpec) 
    {
        this.materialSpec = materialSpec;
    }

    public String getMaterialSpec() 
    {
        return materialSpec;
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
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setBaseType(String baseType) 
    {
        this.baseType = baseType;
    }

    public String getBaseType() 
    {
        return baseType;
    }
    public void setPurchaseType(String purchaseType) 
    {
        this.purchaseType = purchaseType;
    }

    public String getPurchaseType() 
    {
        return purchaseType;
    }
    public void setConsumeType(String consumeType) 
    {
        this.consumeType = consumeType;
    }

    public String getConsumeType() 
    {
        return consumeType;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setDaysCycletime(BigDecimal daysCycletime) 
    {
        this.daysCycletime = daysCycletime;
    }

    public BigDecimal getDaysCycletime() 
    {
        return daysCycletime;
    }
    public void setDaysLeadtime(BigDecimal daysLeadtime) 
    {
        this.daysLeadtime = daysLeadtime;
    }

    public BigDecimal getDaysLeadtime() 
    {
        return daysLeadtime;
    }
    public void setDaysPosttime(BigDecimal daysPosttime) 
    {
        this.daysPosttime = daysPosttime;
    }

    public BigDecimal getDaysPosttime() 
    {
        return daysPosttime;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public BigDecimal getPriceLast() {
        return priceLast;
    }

    public void setPriceLast(BigDecimal priceLast) {
        this.priceLast = priceLast;
    }

    public void setQuantityQualified(BigDecimal quantityQualified)
    {
        this.quantityQualified = quantityQualified;
    }

    public BigDecimal getQuantityQualified() 
    {
        return quantityQualified;
    }
    public void setQuantityScraped(BigDecimal quantityScraped) 
    {
        this.quantityScraped = quantityScraped;
    }

    public BigDecimal getQuantityScraped() 
    {
        return quantityScraped;
    }
    public void setQuantityExpired(BigDecimal quantityExpired) 
    {
        this.quantityExpired = quantityExpired;
    }

    public BigDecimal getQuantityExpired() 
    {
        return quantityExpired;
    }
    public void setQuantityFrozen(BigDecimal quantityFrozen) 
    {
        this.quantityFrozen = quantityFrozen;
    }

    public BigDecimal getQuantityFrozen() 
    {
        return quantityFrozen;
    }
    public void setQuantityPending(BigDecimal quantityPending) 
    {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityPending() 
    {
        return quantityPending;
    }
    public void setQuantityBooked(BigDecimal quantityBooked) 
    {
        this.quantityBooked = quantityBooked;
    }

    public BigDecimal getQuantityBooked() 
    {
        return quantityBooked;
    }

    public BigDecimal getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(BigDecimal quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("abroadProductNo", getAbroadProductNo())
            .append("unitId", getUnitId())
            .append("categoryId", getCategoryId())
            .append("baseType", getBaseType())
            .append("purchaseType", getPurchaseType())
            .append("consumeType", getConsumeType())
            .append("lineId", getLineId())
            .append("daysCycletime", getDaysCycletime())
            .append("daysLeadtime", getDaysLeadtime())
            .append("daysPosttime", getDaysPosttime())
            .append("status", getStatus())
            .append("quantityQualified", getQuantityQualified())
            .append("quantityScraped", getQuantityScraped())
            .append("quantityExpired", getQuantityExpired())
            .append("quantityFrozen", getQuantityFrozen())
            .append("quantityPending", getQuantityPending())
            .append("quantityBooked", getQuantityBooked())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

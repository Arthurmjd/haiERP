package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料销售维对象 eng_material_sales
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialSales extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 价格倍数 */
    @Excel(name = "价格倍数")
    private Long unitPriceTimes;

    /** 保质期 */
    @Excel(name = "保质期")
    private Long guaranteeDays;

    /** 出货提前期 */
    @Excel(name = "出货提前期")
    private BigDecimal leadtimeDelivery;

    /** 最小销售量 */
    @Excel(name = "最小销售量")
    private BigDecimal quantityMinisize;

    /** 销售经济批量 */
    @Excel(name = "销售经济批量")
    private BigDecimal quantityEconosize;

    /** 上次售价 */
    @Excel(name = "上次售价")
    private BigDecimal lastSalePrice;

    /** 上次销售日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上次销售日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastSaleDate;

    /** 项目 */
    @Excel(name = "项目")
    private Long projectId;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setUnitCost(BigDecimal unitCost) 
    {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() 
    {
        return unitCost;
    }
    public void setUnitPriceTimes(Long unitPriceTimes) 
    {
        this.unitPriceTimes = unitPriceTimes;
    }

    public Long getUnitPriceTimes() 
    {
        return unitPriceTimes;
    }
    public void setGuaranteeDays(Long guaranteeDays) 
    {
        this.guaranteeDays = guaranteeDays;
    }

    public Long getGuaranteeDays() 
    {
        return guaranteeDays;
    }
    public void setLeadtimeDelivery(BigDecimal leadtimeDelivery) 
    {
        this.leadtimeDelivery = leadtimeDelivery;
    }

    public BigDecimal getLeadtimeDelivery() 
    {
        return leadtimeDelivery;
    }
    public void setQuantityMinisize(BigDecimal quantityMinisize) 
    {
        this.quantityMinisize = quantityMinisize;
    }

    public BigDecimal getQuantityMinisize() 
    {
        return quantityMinisize;
    }
    public void setQuantityEconosize(BigDecimal quantityEconosize) 
    {
        this.quantityEconosize = quantityEconosize;
    }

    public BigDecimal getQuantityEconosize() 
    {
        return quantityEconosize;
    }
    public void setLastSalePrice(BigDecimal lastSalePrice) 
    {
        this.lastSalePrice = lastSalePrice;
    }

    public BigDecimal getLastSalePrice() 
    {
        return lastSalePrice;
    }
    public void setLastSaleDate(Date lastSaleDate) 
    {
        this.lastSaleDate = lastSaleDate;
    }

    public Date getLastSaleDate() 
    {
        return lastSaleDate;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("unitCost", getUnitCost())
            .append("unitPriceTimes", getUnitPriceTimes())
            .append("guaranteeDays", getGuaranteeDays())
            .append("leadtimeDelivery", getLeadtimeDelivery())
            .append("quantityMinisize", getQuantityMinisize())
            .append("quantityEconosize", getQuantityEconosize())
            .append("lastSalePrice", getLastSalePrice())
            .append("lastSaleDate", getLastSaleDate())
            .append("projectId", getProjectId())
            .append("customerId", getCustomerId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

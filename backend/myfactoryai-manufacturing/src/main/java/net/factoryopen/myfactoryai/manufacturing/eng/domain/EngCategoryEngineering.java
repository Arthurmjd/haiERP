package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 品类工艺对象 eng_category
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
public class EngCategoryEngineering extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 品类编号 */
    @Excel(name = "品类编号")
    private String categoryNo;

    /** 品类名称 */
    @Excel(name = "品类名称")
    private String categoryName;

    /** 父品类 */
    @Excel(name = "父品类")
    private Long parentId;

    /** 物料类型 */
    @Excel(name = "物料类型")
    private String materialType;

    /** 采购类型 */
    @Excel(name = "采购类型")
    private String purchasingType;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private String consumingType;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 损耗率 */
    @Excel(name = "损耗率")
    private BigDecimal lossRate;

    /** 产购周期 */
    @Excel(name = "产购周期")
    private BigDecimal daysCycletime;

    /** 提前时间 */
    @Excel(name = "提前时间")
    private BigDecimal daysLeadtime;

    /** 后置时间 */
    @Excel(name = "后置时间")
    private BigDecimal daysPosttime;

    /** 在用否 */
    @Excel(name = "在用否")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryNo(String categoryNo) 
    {
        this.categoryNo = categoryNo;
    }

    public String getCategoryNo() 
    {
        return categoryNo;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setMaterialType(String materialType) 
    {
        this.materialType = materialType;
    }

    public String getMaterialType() 
    {
        return materialType;
    }
    public void setPurchasingType(String purchasingType) 
    {
        this.purchasingType = purchasingType;
    }

    public String getPurchasingType() 
    {
        return purchasingType;
    }
    public void setConsumingType(String consumingType) 
    {
        this.consumingType = consumingType;
    }

    public String getConsumingType() 
    {
        return consumingType;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }

    public BigDecimal getLossRate() {
        return lossRate;
    }

    public void setLossRate(BigDecimal lossRate) {
        this.lossRate = lossRate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryNo", getCategoryNo())
            .append("categoryName", getCategoryName())
            .append("parentId", getParentId())
            .append("materialType", getMaterialType())
            .append("purchasingType", getPurchasingType())
            .append("consumingType", getConsumingType())
            .append("lineId", getLineId())
            .append("lossRate", getLossRate())
            .append("daysCycletime", getDaysCycletime())
            .append("daysLeadtime", getDaysLeadtime())
            .append("daysPosttime", getDaysPosttime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料生产维对象 eng_material_production
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialProduction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 固定前置时间 */
    @Excel(name = "固定前置时间")
    private BigDecimal leadtimeSolidhour;

    /** 可变前置时间 */
    @Excel(name = "可变前置时间")
    private BigDecimal leadtimeVariablehour;

    /** 最小生产量 */
    @Excel(name = "最小生产量")
    private BigDecimal quantityMinisize;

    /** 经济批量 */
    @Excel(name = "经济批量")
    private BigDecimal quantityEconosize;

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
    public void setLeadtimeSolidhour(BigDecimal leadtimeSolidhour) 
    {
        this.leadtimeSolidhour = leadtimeSolidhour;
    }

    public BigDecimal getLeadtimeSolidhour() 
    {
        return leadtimeSolidhour;
    }
    public void setLeadtimeVariablehour(BigDecimal leadtimeVariablehour) 
    {
        this.leadtimeVariablehour = leadtimeVariablehour;
    }

    public BigDecimal getLeadtimeVariablehour() 
    {
        return leadtimeVariablehour;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("leadtimeSolidhour", getLeadtimeSolidhour())
            .append("leadtimeVariablehour", getLeadtimeVariablehour())
            .append("quantityMinisize", getQuantityMinisize())
            .append("quantityEconosize", getQuantityEconosize())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

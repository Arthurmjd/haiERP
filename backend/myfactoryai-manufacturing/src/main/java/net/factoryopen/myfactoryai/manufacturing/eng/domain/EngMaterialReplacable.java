package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料替代对象 eng_material_replacable
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialReplacable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 优序 */
    @Excel(name = "优序")
    private Integer prioritySeq;

    /** 替代物料 */
    @Excel(name = "替代物料")
    private Long replacableMaterialId;

    /** 替代率% */
    @Excel(name = "替代率%")
    private BigDecimal replacableRate;

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
    public void setPrioritySeq(Integer prioritySeq) 
    {
        this.prioritySeq = prioritySeq;
    }

    public Integer getPrioritySeq() 
    {
        return prioritySeq;
    }
    public void setReplacableMaterialId(Long replacableMaterialId) 
    {
        this.replacableMaterialId = replacableMaterialId;
    }

    public Long getReplacableMaterialId() 
    {
        return replacableMaterialId;
    }
    public void setReplacableRate(BigDecimal replacableRate) 
    {
        this.replacableRate = replacableRate;
    }

    public BigDecimal getReplacableRate() 
    {
        return replacableRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("prioritySeq", getPrioritySeq())
            .append("replacableMaterialId", getReplacableMaterialId())
            .append("replacableRate", getReplacableRate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

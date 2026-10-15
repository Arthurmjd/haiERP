package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 品类BOM对象 eng_category_bom
 * 
 * @author admin
 * @date 2024-11-09
 */
public class EngCategoryBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 品类ID */
    @Excel(name = "品类ID")
    private Long categoryId;

    /** 序号 */
    @Excel(name = "序号")
    private Integer seqNo;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 标准用量 */
    @Excel(name = "标准用量")
    private BigDecimal standardQuantity;

    /** 损耗率% */
    @Excel(name = "损耗率%")
    private BigDecimal lossRate;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private String consumeType;

    /** 基本类型 */
    @Excel(name = "算料类型")
    private String calculateType;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
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
    public void setStandardQuantity(BigDecimal standardQuantity) 
    {
        this.standardQuantity = standardQuantity;
    }

    public BigDecimal getStandardQuantity() 
    {
        return standardQuantity;
    }
    public void setLossRate(BigDecimal lossRate) 
    {
        this.lossRate = lossRate;
    }

    public BigDecimal getLossRate() 
    {
        return lossRate;
    }
    public void setConsumeType(String consumeType)
    {
        this.consumeType = consumeType;
    }

    public String getConsumeType()
    {
        return consumeType;
    }
    public void setCalculateType(String calculateType)
    {
        this.calculateType = calculateType;
    }

    public String getCalculateType()
    {
        return calculateType;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("seqNo", getSeqNo())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("standardQuantity", getStandardQuantity())
            .append("lossRate", getLossRate())
            .append("consumeType", getConsumeType())
            .append("calculateType", getCalculateType())
            .append("lineId", getLineId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

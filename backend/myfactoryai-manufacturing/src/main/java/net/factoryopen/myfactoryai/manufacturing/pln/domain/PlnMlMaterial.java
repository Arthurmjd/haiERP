package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批次备料单对象 pln_ml_material
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public class PlnMlMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 制造批 */
    @Excel(name = "制造批")
    private Long mlId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 物料编码 */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 尺寸 */
    @Excel(name = "尺寸")
    private String materialSize;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private Integer consumeType;

    /** 必配料 */
    @Excel(name = "必配料")
    private String enableMust;

    /** 可替代 */
    @Excel(name = "可替代")
    private String enableReplace;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
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

    public String getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(String materialSize) {
        this.materialSize = materialSize;
    }

    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setConsumeType(Integer consumeType) 
    {
        this.consumeType = consumeType;
    }

    public Integer getConsumeType() 
    {
        return consumeType;
    }
    public void setEnableMust(String enableMust) 
    {
        this.enableMust = enableMust;
    }

    public String getEnableMust() 
    {
        return enableMust;
    }
    public void setEnableReplace(String enableReplace) 
    {
        this.enableReplace = enableReplace;
    }

    public String getEnableReplace() 
    {
        return enableReplace;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mlId", getMlId())
            .append("seqNo", getSeqNo())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialSize", getMaterialSize())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("consumeType", getConsumeType())
            .append("enableMust", getEnableMust())
            .append("enableReplace", getEnableReplace())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

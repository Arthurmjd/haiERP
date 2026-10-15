package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料需求对象 pln_mrp_pr
 * 
 * @author admin
 * @date 2024-07-04
 */
public class PlnMaterialRequirements extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 主计划 */
    @Excel(name = "主计划")
    private Long mpsId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String materialName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String materialSpec;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

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
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setMpsId(Long mpsId) 
    {
        this.mpsId = mpsId;
    }

    public Long getMpsId() 
    {
        return mpsId;
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
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
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
            .append("status", getStatus())
            .append("mpsId", getMpsId())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("quantity", getQuantity())
            .append("unitId", getUnitId())
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

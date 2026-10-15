package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批次关重料对象 pln_ml_routingkiparts
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public class PlnMlRoutingkiparts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 工序 */
    @Excel(name = "工序")
    private Long routingId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRoutingId(Long routingId) 
    {
        this.routingId = routingId;
    }

    public Long getRoutingId() 
    {
        return routingId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("routingId", getRoutingId())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

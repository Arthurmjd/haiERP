package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料参数对象 eng_material_parameters
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialParameters extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long materialId;

    /** 参数代码 */
    @Excel(name = "参数代码")
    private String parameterCode;

    /** 参数描述 */
    @Excel(name = "参数描述")
    private String parameterDesc;

    /** 参数类型 */
    @Excel(name = "参数类型")
    private String parameterType;

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
    public void setParameterCode(String parameterCode) 
    {
        this.parameterCode = parameterCode;
    }

    public String getParameterCode() 
    {
        return parameterCode;
    }
    public void setParameterDesc(String parameterDesc) 
    {
        this.parameterDesc = parameterDesc;
    }

    public String getParameterDesc() 
    {
        return parameterDesc;
    }
    public void setParameterType(String parameterType) 
    {
        this.parameterType = parameterType;
    }

    public String getParameterType() 
    {
        return parameterType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("parameterCode", getParameterCode())
            .append("parameterDesc", getParameterDesc())
            .append("parameterType", getParameterType())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

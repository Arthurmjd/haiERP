package net.factoryopen.myfactoryai.manufacturing.bas.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作业类型对象 bas_operation_type
 * 
 * @author admin
 * @date 2024-03-08
 */
public class BasOperationType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作业id */
    private Long id;

    /** 作业类型编码 */
    @Excel(name = "作业类型编码")
    private String typeCode;

    /** 作业类型名称 */
    @Excel(name = "作业类型名称")
    private String typeName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTypeCode(String typeCode) 
    {
        this.typeCode = typeCode;
    }

    public String getTypeCode() 
    {
        return typeCode;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeCode", getTypeCode())
            .append("typeName", getTypeName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

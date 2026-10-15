package net.factoryopen.myfactoryai.manufacturing.bas.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 交货方式对象 bas_shipment_type
 * 
 * @author admin
 * @date 2024-03-08
 */
public class BasShipmentType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 交货方式id */
    private Long id;

    /** 交货方式代码 */
    @Excel(name = "交货方式代码")
    private String typeCode;

    /** 交货方式名称 */
    @Excel(name = "交货方式名称")
    private String typeDesc;

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
    public void setTypeDesc(String typeDesc) 
    {
        this.typeDesc = typeDesc;
    }

    public String getTypeDesc() 
    {
        return typeDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeCode", getTypeCode())
            .append("typeDesc", getTypeDesc())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

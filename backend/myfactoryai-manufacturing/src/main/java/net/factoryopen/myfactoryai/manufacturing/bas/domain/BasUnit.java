package net.factoryopen.myfactoryai.manufacturing.bas.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单位对象 bas_unit
 * 
 * @author admin
 * @date 2024-03-08
 */
public class BasUnit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 单位尺寸 */
    @Excel(name = "单位尺寸")
    private Long unitSize;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setUnitSize(Long unitSize) 
    {
        this.unitSize = unitSize;
    }

    public Long getUnitSize() 
    {
        return unitSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitSize", getUnitSize())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

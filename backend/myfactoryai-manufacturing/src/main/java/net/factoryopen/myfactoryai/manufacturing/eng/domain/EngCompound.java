package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 材质数据对象 eng_compound
 * 
 * @author admin
 * @date 2024-11-29
 */
public class EngCompound extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 材质编号 */
    @Excel(name = "材质编号")
    private String compoundNo;

    /** 材质名称 */
    @Excel(name = "材质名称")
    private String compoundDesc;

    /** 父材质ID */
    @Excel(name = "父材质ID")
    private Long parentId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCompoundNo(String compoundNo) 
    {
        this.compoundNo = compoundNo;
    }

    public String getCompoundNo() 
    {
        return compoundNo;
    }
    public void setCompoundDesc(String compoundDesc) 
    {
        this.compoundDesc = compoundDesc;
    }

    public String getCompoundDesc() 
    {
        return compoundDesc;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compoundNo", getCompoundNo())
            .append("compoundDesc", getCompoundDesc())
            .append("parentId", getParentId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

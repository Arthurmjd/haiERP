package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺异常分类对象 eng_exception_categorytype
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EngExceptionCategorytype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 异常分类码 */
    @Excel(name = "异常分类码")
    private String categoryCode;

    /** 异常分类描述 */
    @Excel(name = "异常分类描述")
    private String categoryDesc;

    /** 在用否 */
    @Excel(name = "在用否")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }
    public void setCategoryDesc(String categoryDesc) 
    {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryDesc() 
    {
        return categoryDesc;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryCode", getCategoryCode())
            .append("categoryDesc", getCategoryDesc())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

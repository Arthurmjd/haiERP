package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺异常原因对象 eng_exception_reasontype
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EngExceptionReasontype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 异常分类ID */
    @Excel(name = "异常分类ID")
    private Long categoryId;

    /** 异常原因码 */
    @Excel(name = "异常原因码")
    private String reasonCode;

    /** 异常原因描述 */
    @Excel(name = "异常原因描述")
    private String reasonDesc;

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
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setReasonCode(String reasonCode) 
    {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() 
    {
        return reasonCode;
    }
    public void setReasonDesc(String reasonDesc) 
    {
        this.reasonDesc = reasonDesc;
    }

    public String getReasonDesc() 
    {
        return reasonDesc;
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
            .append("categoryId", getCategoryId())
            .append("reasonCode", getReasonCode())
            .append("reasonDesc", getReasonDesc())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

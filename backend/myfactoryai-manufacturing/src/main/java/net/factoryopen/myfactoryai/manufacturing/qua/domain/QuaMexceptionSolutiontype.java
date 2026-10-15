package net.factoryopen.myfactoryai.manufacturing.qua.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 质量异常处理类型对象 qua_mexception_solutiontype
 * 
 * @author admin
 * @date 2024-11-07
 */
public class QuaMexceptionSolutiontype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 处理类型码 */
    @Excel(name = "处理类型码")
    private String dealtypeCode;

    /** 处理类型描述 */
    @Excel(name = "处理类型描述")
    private String dealtypeDesc;

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
    public void setDealtypeCode(String dealtypeCode) 
    {
        this.dealtypeCode = dealtypeCode;
    }

    public String getDealtypeCode() 
    {
        return dealtypeCode;
    }
    public void setDealtypeDesc(String dealtypeDesc) 
    {
        this.dealtypeDesc = dealtypeDesc;
    }

    public String getDealtypeDesc() 
    {
        return dealtypeDesc;
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
            .append("dealtypeCode", getDealtypeCode())
            .append("dealtypeDesc", getDealtypeDesc())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

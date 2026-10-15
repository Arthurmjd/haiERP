package net.factoryopen.myfactoryai.manufacturing.bas.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单据状态对象 bas_bill_status
 * 
 * @author admin
 * @date 2024-03-08
 */
public class BasBillStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 状态码 */
    @Excel(name = "状态码")
    private Integer statusCode;

    /** 状态描述 */
    @Excel(name = "状态描述")
    private String statusDesc;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStatusCode(Integer statusCode) 
    {
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() 
    {
        return statusCode;
    }
    public void setStatusDesc(String statusDesc) 
    {
        this.statusDesc = statusDesc;
    }

    public String getStatusDesc() 
    {
        return statusDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("statusCode", getStatusCode())
            .append("statusDesc", getStatusDesc())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

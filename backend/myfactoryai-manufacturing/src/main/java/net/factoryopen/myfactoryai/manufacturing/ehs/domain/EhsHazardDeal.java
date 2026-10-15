package net.factoryopen.myfactoryai.manufacturing.ehs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全风险处理对象 ehs_hazard_deal
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EhsHazardDeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 风险ID */
    @Excel(name = "风险ID")
    private Long hazardId;

    /** 隐患描述 */
    @Excel(name = "隐患描述")
    private String dealDesc;

    /** 隐患等级 */
    @Excel(name = "隐患等级")
    private String dealLevel;

    /** 日期 */
    @Excel(name = "日期")
    private String day;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 责任人ID */
    @Excel(name = "责任人ID")
    private Long employeeId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHazardId(Long hazardId) 
    {
        this.hazardId = hazardId;
    }

    public Long getHazardId() 
    {
        return hazardId;
    }
    public void setDealDesc(String dealDesc) 
    {
        this.dealDesc = dealDesc;
    }

    public String getDealDesc() 
    {
        return dealDesc;
    }
    public void setDealLevel(String dealLevel) 
    {
        this.dealLevel = dealLevel;
    }

    public String getDealLevel() 
    {
        return dealLevel;
    }
    public void setDay(String day) 
    {
        this.day = day;
    }

    public String getDay() 
    {
        return day;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() 
    {
        return employeeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("hazardId", getHazardId())
            .append("dealDesc", getDealDesc())
            .append("dealLevel", getDealLevel())
            .append("day", getDay())
            .append("deptId", getDeptId())
            .append("employeeId", getEmployeeId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

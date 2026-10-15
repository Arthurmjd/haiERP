package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对账服务对象 ord_checkbusi
 * 
 * @author  jitfactory-generator
 * @date 2024-07-04
 */
public class OrdCheckbusi extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDay;

    /** 结束日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishDay;

    /** 销售员 */
    @Excel(name = "销售员")
    private Long salesId;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setStartDay(Date startDay) 
    {
        this.startDay = startDay;
    }

    public Date getStartDay() 
    {
        return startDay;
    }
    public void setFinishDay(Date finishDay) 
    {
        this.finishDay = finishDay;
    }

    public Date getFinishDay() 
    {
        return finishDay;
    }
    public void setSalesId(Long salesId) 
    {
        this.salesId = salesId;
    }

    public Long getSalesId() 
    {
        return salesId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("startDay", getStartDay())
            .append("finishDay", getFinishDay())
            .append("salesId", getSalesId())
            .append("deptId", getDeptId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

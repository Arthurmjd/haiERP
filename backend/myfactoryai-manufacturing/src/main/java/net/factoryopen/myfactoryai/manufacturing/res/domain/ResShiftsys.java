package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班制管理对象 res_shiftsys
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
public class ResShiftsys extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 班制名称 */
    @Excel(name = "班制名称")
    private String shiftsysName;

    /** 班别数 */
    @Excel(name = "班别数")
    private Long shiftQuantity;

    /** 上班时数 */
    @Excel(name = "上班时数")
    private Long hoursWork;

    /** 加班时数 */
    @Excel(name = "加班时数")
    private Long hoursExtra;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setShiftsysName(String shiftsysName) 
    {
        this.shiftsysName = shiftsysName;
    }

    public String getShiftsysName() 
    {
        return shiftsysName;
    }
    public void setShiftQuantity(Long shiftQuantity) 
    {
        this.shiftQuantity = shiftQuantity;
    }

    public Long getShiftQuantity() 
    {
        return shiftQuantity;
    }
    public void setHoursWork(Long hoursWork) 
    {
        this.hoursWork = hoursWork;
    }

    public Long getHoursWork() 
    {
        return hoursWork;
    }
    public void setHoursExtra(Long hoursExtra) 
    {
        this.hoursExtra = hoursExtra;
    }

    public Long getHoursExtra() 
    {
        return hoursExtra;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shiftsysName", getShiftsysName())
            .append("shiftQuantity", getShiftQuantity())
            .append("hoursWork", getHoursWork())
            .append("hoursExtra", getHoursExtra())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

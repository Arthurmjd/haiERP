package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班别对象 res_shift
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
public class ResShift extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 班制 */
    @Excel(name = "班制")
    private Long shiftsysId;

    /** 班别序号 */
    @Excel(name = "班别序号")
    private Long shiftSeq;

    /** 班别描述 */
    @Excel(name = "班别描述")
    private String shiftDesc;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private String timeBegin;

    /** 结束时间 */
    @Excel(name = "结束时间")
    private String timeEnd;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setShiftsysId(Long shiftsysId) 
    {
        this.shiftsysId = shiftsysId;
    }

    public Long getShiftsysId() 
    {
        return shiftsysId;
    }
    public void setShiftSeq(Long shiftSeq) 
    {
        this.shiftSeq = shiftSeq;
    }

    public Long getShiftSeq() 
    {
        return shiftSeq;
    }
    public void setShiftDesc(String shiftDesc) 
    {
        this.shiftDesc = shiftDesc;
    }

    public String getShiftDesc() 
    {
        return shiftDesc;
    }
    public void setTimeBegin(String timeBegin) 
    {
        this.timeBegin = timeBegin;
    }

    public String getTimeBegin() 
    {
        return timeBegin;
    }
    public void setTimeEnd(String timeEnd) 
    {
        this.timeEnd = timeEnd;
    }

    public String getTimeEnd() 
    {
        return timeEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shiftsysId", getShiftsysId())
            .append("shiftSeq", getShiftSeq())
            .append("shiftDesc", getShiftDesc())
            .append("timeBegin", getTimeBegin())
            .append("timeEnd", getTimeEnd())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班组管理对象 res_workgroup
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public class ResWorkgroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 班组名称 */
    @Excel(name = "班组名称")
    private String groupName;

    /** 班制 */
    @Excel(name = "班制")
    private Long shiftsysId;

    /** 产线 */
    @Excel(name = "产线")
    private Long lineId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setShiftsysId(Long shiftsysId) 
    {
        this.shiftsysId = shiftsysId;
    }

    public Long getShiftsysId() 
    {
        return shiftsysId;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupName", getGroupName())
            .append("shiftsysId", getShiftsysId())
            .append("lineId", getLineId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

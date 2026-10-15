package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班组工序对象 res_workgroup_machine
 * 
 * @author admin
 * @date 2025-01-02
 */
public class ResWorkgroupMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产线ID */
    private Long id;

    /** 班组ID */
    @Excel(name = "班组ID")
    private Long groupId;

    /** 机台ID */
    @Excel(name = "机台ID")
    private Long machineId;

    /** 机台名称 */
    @Excel(name = "机台名称")
    private String machineName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public void setMachineId(Long machineId)
    {
        this.machineId = machineId;
    }

    public Long getMachineId() 
    {
        return machineId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("machineId", getMachineId())
            .append("machineName", getMachineName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

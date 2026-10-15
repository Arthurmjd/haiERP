package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班组工序对象 res_workgroup_process
 * 
 * @author admin
 * @date 2025-01-02
 */
public class ResWorkgroupProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产线ID */
    private Long id;

    /** 班组ID */
    @Excel(name = "班组ID")
    private Long groupId;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }

    public String getProcessCode()
    {
        return processCode;
    }
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String getProcessName()
    {
        return processName;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public void setProcessId(Long processId)
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("lineId", getLineId())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

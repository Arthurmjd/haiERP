package net.factoryopen.myfactoryai.manufacturing.qua.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 质检请求处理对象 qua_inspect_request
 * 
 * @author admin
 * @date 2024-10-06
 */
public class QuaInspectReDeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 质检请求码 */
    @Excel(name = "质检请求码")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 来源单ID */
    @Excel(name = "来源单ID")
    private Long requestBillId;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String requestBillNo;

    /** 检验类型 */
    @Excel(name = "检验类型")
    private Integer inspectType;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 请检人ID */
    @Excel(name = "请检人ID")
    private Long requestorId;

    /** 来源厂商ID */
    @Excel(name = "来源厂商ID")
    private Long sourceSupplierId;

    /** 来源工序ID */
    @Excel(name = "来源工序ID")
    private Long sourceProcessId;

    /** 来源产线ID */
    @Excel(name = "来源产线ID")
    private Long sourceLineId;

    /** 来源班组ID */
    @Excel(name = "来源班组ID")
    private Long sourceGroupId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBillNo(String billNo) 
    {
        this.billNo = billNo;
    }

    public String getBillNo() 
    {
        return billNo;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setRequestBillId(Long requestBillId) 
    {
        this.requestBillId = requestBillId;
    }

    public Long getRequestBillId() 
    {
        return requestBillId;
    }
    public void setRequestBillNo(String requestBillNo) 
    {
        this.requestBillNo = requestBillNo;
    }

    public String getRequestBillNo() 
    {
        return requestBillNo;
    }
    public void setInspectType(Integer inspectType) 
    {
        this.inspectType = inspectType;
    }

    public Integer getInspectType() 
    {
        return inspectType;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setRequestorId(Long requestorId) 
    {
        this.requestorId = requestorId;
    }

    public Long getRequestorId() 
    {
        return requestorId;
    }
    public void setSourceSupplierId(Long sourceSupplierId) 
    {
        this.sourceSupplierId = sourceSupplierId;
    }

    public Long getSourceSupplierId() 
    {
        return sourceSupplierId;
    }
    public void setSourceProcessId(Long sourceProcessId) 
    {
        this.sourceProcessId = sourceProcessId;
    }

    public Long getSourceProcessId() 
    {
        return sourceProcessId;
    }
    public void setSourceLineId(Long sourceLineId) 
    {
        this.sourceLineId = sourceLineId;
    }

    public Long getSourceLineId() 
    {
        return sourceLineId;
    }
    public void setSourceGroupId(Long sourceGroupId) 
    {
        this.sourceGroupId = sourceGroupId;
    }

    public Long getSourceGroupId() 
    {
        return sourceGroupId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("requestBillId", getRequestBillId())
            .append("requestBillNo", getRequestBillNo())
            .append("inspectType", getInspectType())
            .append("projectId", getProjectId())
            .append("requestorId", getRequestorId())
            .append("sourceSupplierId", getSourceSupplierId())
            .append("sourceProcessId", getSourceProcessId())
            .append("sourceLineId", getSourceLineId())
            .append("sourceGroupId", getSourceGroupId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

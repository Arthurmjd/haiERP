package net.factoryopen.myfactoryai.manufacturing.snc.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 请出入库处理对象 snc_stock_request
 * 
 * @author admin
 * @date 2024-09-16
 */
public class SncStockReqdeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 出入库请求码 */
    @Excel(name = "出入库请求码")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 出入库类型 */
    @Excel(name = "出入库类型")
    private Integer ioType;

    /** 来源单ID */
    @Excel(name = "来源单ID")
    private Long requestBillId;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String requestBillNo;

    /** 请求人ID */
    @Excel(name = "请求人ID")
    private Long requestorId;

    /** 请求时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requestTime;

    /** 请求单ID */
    @Excel(name = "来源单ID")
    private Long sourceBillId;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String sourceBillNo;

    /** 来源产线ID */
    @Excel(name = "来源产线ID")
    private Long sourceLineId;

    /** 来源厂商ID */
    @Excel(name = "来源厂商ID")
    private Long sourceSupplierId;

    /** 来源客户ID */
    @Excel(name = "来源客户ID")
    private Long sourceCustomerId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

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
    public void setIoType(Integer ioType) 
    {
        this.ioType = ioType;
    }

    public Integer getIoType() 
    {
        return ioType;
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
    public void setRequestorId(Long requestorId) 
    {
        this.requestorId = requestorId;
    }

    public Long getRequestorId() 
    {
        return requestorId;
    }
    public void setRequestTime(Date requestTime) 
    {
        this.requestTime = requestTime;
    }

    public Date getRequestTime() 
    {
        return requestTime;
    }

    public Long getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(Long sourceBillId) {
        this.sourceBillId = sourceBillId;
    }

    public String getSourceBillNo() {
        return sourceBillNo;
    }

    public void setSourceBillNo(String sourceBillNo) {
        this.sourceBillNo = sourceBillNo;
    }
    public void setSourceLineId(Long sourceLineId) 
    {
        this.sourceLineId = sourceLineId;
    }

    public Long getSourceLineId() 
    {
        return sourceLineId;
    }
    public void setSourceSupplierId(Long sourceSupplierId) 
    {
        this.sourceSupplierId = sourceSupplierId;
    }

    public Long getSourceSupplierId() 
    {
        return sourceSupplierId;
    }
    public void setSourceCustomerId(Long sourceCustomerId) 
    {
        this.sourceCustomerId = sourceCustomerId;
    }

    public Long getSourceCustomerId() 
    {
        return sourceCustomerId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("ioType", getIoType())
            .append("requestBillId", getRequestBillId())
            .append("requestBillNo", getRequestBillNo())
            .append("requestorId", getRequestorId())
            .append("requestTime", getRequestTime())
            .append("sourceBillId", getSourceBillId())
            .append("sourceBillNo", getSourceBillNo())
            .append("sourceLineId", getSourceLineId())
            .append("sourceSupplierId", getSourceSupplierId())
            .append("sourceCustomerId", getSourceCustomerId())
            .append("projectId", getProjectId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

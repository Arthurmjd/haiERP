package net.factoryopen.myfactoryai.manufacturing.exe.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺异常登记对象 eng_exception
 * 
 * @author admin
 * @date 2024-07-04
 */
public class ExeExceptionReq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 异常单号 */
    @Excel(name = "异常单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 制造批ID */
    @Excel(name = "制造批ID")
    private Long mlId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;

    /** 工序号 */
    @Excel(name = "工序号")
    private Integer processSeq;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 班组ID */
    @Excel(name = "班组ID")
    private Long inspectGroupId;

    /** 异常描述 */
    @Excel(name = "异常描述")
    private String description;

    /** 检查部门ID */
    private Long inspectDeptId;

    /** 检查人 */
    private Long inspectBy;

    /** 检查时间 */
    private Date inspectTime;

    /** 处理类型 */
    private Integer dealType;

    /** 异常分类ID */
    private Long categoryId;

    /** 原因类型ID */
    private Long reasonTypeId;

    /** 方案类型ID */
    private Long solutionTypeId;

    /** 原因描述 */
    private String reasonDesc;

    /** 处理描述 */
    private String solutionDesc;

    /** 责任人ID */
    private Long dutyOwnerId;

    /** 责任产线ID */
    private Long dutyLineId;

    /** 责任工序ID */
    private Long dutyProcessId;

    /** 责任班组ID */
    private Long dutyGroupId;

    /** 处理部门ID */
    private Long dealDeptId;

    /** 处理人 */
    private String dealBy;

    /** 处理时间 */
    private Date dealTime;

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
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setProductSpec(String productSpec) 
    {
        this.productSpec = productSpec;
    }

    public String getProductSpec() 
    {
        return productSpec;
    }
    public void setProcessSeq(Integer processSeq) 
    {
        this.processSeq = processSeq;
    }

    public Integer getProcessSeq() 
    {
        return processSeq;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setInspectGroupId(Long inspectGroupId) 
    {
        this.inspectGroupId = inspectGroupId;
    }

    public Long getInspectGroupId() 
    {
        return inspectGroupId;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setInspectDeptId(Long inspectDeptId) 
    {
        this.inspectDeptId = inspectDeptId;
    }

    public Long getInspectDeptId() 
    {
        return inspectDeptId;
    }
    public void setInspectBy(Long inspectBy) 
    {
        this.inspectBy = inspectBy;
    }

    public Long getInspectBy() 
    {
        return inspectBy;
    }
    public void setInspectTime(Date inspectTime) 
    {
        this.inspectTime = inspectTime;
    }

    public Date getInspectTime() 
    {
        return inspectTime;
    }
    public void setDealType(Integer dealType) 
    {
        this.dealType = dealType;
    }

    public Integer getDealType() 
    {
        return dealType;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setReasonTypeId(Long reasonTypeId) 
    {
        this.reasonTypeId = reasonTypeId;
    }

    public Long getReasonTypeId() 
    {
        return reasonTypeId;
    }
    public void setSolutionTypeId(Long solutionTypeId) 
    {
        this.solutionTypeId = solutionTypeId;
    }

    public Long getSolutionTypeId() 
    {
        return solutionTypeId;
    }
    public void setReasonDesc(String reasonDesc) 
    {
        this.reasonDesc = reasonDesc;
    }

    public String getReasonDesc() 
    {
        return reasonDesc;
    }
    public void setSolutionDesc(String solutionDesc) 
    {
        this.solutionDesc = solutionDesc;
    }

    public String getSolutionDesc() 
    {
        return solutionDesc;
    }
    public void setDutyOwnerId(Long dutyOwnerId) 
    {
        this.dutyOwnerId = dutyOwnerId;
    }

    public Long getDutyOwnerId() 
    {
        return dutyOwnerId;
    }
    public void setDutyLineId(Long dutyLineId) 
    {
        this.dutyLineId = dutyLineId;
    }

    public Long getDutyLineId() 
    {
        return dutyLineId;
    }
    public void setDutyProcessId(Long dutyProcessId) 
    {
        this.dutyProcessId = dutyProcessId;
    }

    public Long getDutyProcessId() 
    {
        return dutyProcessId;
    }
    public void setDutyGroupId(Long dutyGroupId) 
    {
        this.dutyGroupId = dutyGroupId;
    }

    public Long getDutyGroupId() 
    {
        return dutyGroupId;
    }
    public void setDealDeptId(Long dealDeptId) 
    {
        this.dealDeptId = dealDeptId;
    }

    public Long getDealDeptId() 
    {
        return dealDeptId;
    }
    public void setDealBy(String dealBy) 
    {
        this.dealBy = dealBy;
    }

    public String getDealBy() 
    {
        return dealBy;
    }
    public void setDealTime(Date dealTime) 
    {
        this.dealTime = dealTime;
    }

    public Date getDealTime() 
    {
        return dealTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("mlId", getMlId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("processSeq", getProcessSeq())
            .append("lineId", getLineId())
            .append("processId", getProcessId())
            .append("inspectGroupId", getInspectGroupId())
            .append("description", getDescription())
            .append("inspectDeptId", getInspectDeptId())
            .append("inspectBy", getInspectBy())
            .append("inspectTime", getInspectTime())
            .append("dealType", getDealType())
            .append("categoryId", getCategoryId())
            .append("reasonTypeId", getReasonTypeId())
            .append("solutionTypeId", getSolutionTypeId())
            .append("reasonDesc", getReasonDesc())
            .append("solutionDesc", getSolutionDesc())
            .append("dutyOwnerId", getDutyOwnerId())
            .append("dutyLineId", getDutyLineId())
            .append("dutyProcessId", getDutyProcessId())
            .append("dutyGroupId", getDutyGroupId())
            .append("dealDeptId", getDealDeptId())
            .append("dealBy", getDealBy())
            .append("dealTime", getDealTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

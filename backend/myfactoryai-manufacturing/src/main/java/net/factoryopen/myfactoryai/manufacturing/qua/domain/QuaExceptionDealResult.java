package net.factoryopen.myfactoryai.manufacturing.qua.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 质量异常判处结果对象 qua_exception
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class QuaExceptionDealResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 异常单号 */
    private String billNo;

    /** 状态 */
    private Integer status;

    /** 产品ID */
    @Excel(name = "开工单ID")
    private Long wsId;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 单位 */
    private Long unitId;

    /** 异常数量 */
    private BigDecimal quantity;

    /** 异常描述 */
    private String description;

    /** 检查类型 */
    private Integer inspectType;

    /** 制造批 */
    private Long mlId;

    /** 产线 */
    private Long lineId;

    /** 工序号 */
    private Integer processSeq;

    /** 工序 */
    private Long processId;

    /** 供应商ID */
    private Long supplierId;

    /** 供应商批号 */
    private String supplierLotNo;

    /** 班组ID */
    private Long inspectGroupId;

    /** 检查部门ID */
    private Long inspectDeptId;

    /** 检查人 */
    private Long inspectBy;

    /** 检查时间 */
    private Date inspectTime;

    /** 处理类型 */
    @Excel(name = "处理类型")
    private Integer dealType;

    /** 处理数量 */
    @Excel(name = "处理数量")
    private BigDecimal dealQuantity;

    /** 制异分类ID */
    @Excel(name = "制异分类ID")
    private Long mcategoryId;

    /** 制异原因ID */
    @Excel(name = "制异原因ID")
    private Long mreasonTypeId;

    /** 制异方案ID */
    @Excel(name = "制异方案ID")
    private Long msolutionTypeId;

    /** 购异分类ID */
    @Excel(name = "购异分类ID")
    private Long pcategoryId;

    /** 购异原因ID */
    @Excel(name = "购异原因ID")
    private Long preasonTypeId;

    /** 购异方案ID */
    @Excel(name = "购异方案ID")
    private Long psolutionTypeId;

    /** 原因描述 */
    @Excel(name = "原因描述")
    private String reasonDesc;

    /** 处理描述 */
    @Excel(name = "处理描述")
    private String solutionDesc;

    /** 责任产线ID */
    @Excel(name = "责任产线ID")
    private Long dutyLineId;

    /** 责任工序ID */
    @Excel(name = "责任工序ID")
    private Long dutyProcessId;

    /** 责任班组ID */
    @Excel(name = "责任班组ID")
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

    public Long getWsId() {
        return wsId;
    }

    public void setWsId(Long wsId) {
        this.wsId = wsId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setMaterialSpec(String materialSpec) 
    {
        this.materialSpec = materialSpec;
    }

    public String getMaterialSpec() 
    {
        return materialSpec;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setInspectType(Integer inspectType) 
    {
        this.inspectType = inspectType;
    }

    public Integer getInspectType() 
    {
        return inspectType;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setProcessSeq(Integer processSeq) 
    {
        this.processSeq = processSeq;
    }

    public Integer getProcessSeq() 
    {
        return processSeq;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setSupplierLotNo(String supplierLotNo) 
    {
        this.supplierLotNo = supplierLotNo;
    }

    public String getSupplierLotNo() 
    {
        return supplierLotNo;
    }
    public void setInspectGroupId(Long inspectGroupId) 
    {
        this.inspectGroupId = inspectGroupId;
    }

    public Long getInspectGroupId() 
    {
        return inspectGroupId;
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
    public void setDealQuantity(BigDecimal dealQuantity) 
    {
        this.dealQuantity = dealQuantity;
    }

    public BigDecimal getDealQuantity() 
    {
        return dealQuantity;
    }
    public void setMcategoryId(Long mcategoryId) 
    {
        this.mcategoryId = mcategoryId;
    }

    public Long getMcategoryId() 
    {
        return mcategoryId;
    }
    public void setMreasonTypeId(Long mreasonTypeId) 
    {
        this.mreasonTypeId = mreasonTypeId;
    }

    public Long getMreasonTypeId() 
    {
        return mreasonTypeId;
    }
    public void setMsolutionTypeId(Long msolutionTypeId) 
    {
        this.msolutionTypeId = msolutionTypeId;
    }

    public Long getMsolutionTypeId() 
    {
        return msolutionTypeId;
    }
    public void setPcategoryId(Long pcategoryId) 
    {
        this.pcategoryId = pcategoryId;
    }

    public Long getPcategoryId() 
    {
        return pcategoryId;
    }
    public void setPreasonTypeId(Long preasonTypeId) 
    {
        this.preasonTypeId = preasonTypeId;
    }

    public Long getPreasonTypeId() 
    {
        return preasonTypeId;
    }
    public void setPsolutionTypeId(Long psolutionTypeId) 
    {
        this.psolutionTypeId = psolutionTypeId;
    }

    public Long getPsolutionTypeId() 
    {
        return psolutionTypeId;
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
            .append("wsId", getWsId())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("description", getDescription())
            .append("inspectType", getInspectType())
            .append("mlId", getMlId())
            .append("lineId", getLineId())
            .append("processSeq", getProcessSeq())
            .append("processId", getProcessId())
            .append("supplierId", getSupplierId())
            .append("supplierLotNo", getSupplierLotNo())
            .append("inspectGroupId", getInspectGroupId())
            .append("inspectDeptId", getInspectDeptId())
            .append("inspectBy", getInspectBy())
            .append("inspectTime", getInspectTime())
            .append("dealType", getDealType())
            .append("dealQuantity", getDealQuantity())
            .append("mcategoryId", getMcategoryId())
            .append("mreasonTypeId", getMreasonTypeId())
            .append("msolutionTypeId", getMsolutionTypeId())
            .append("pcategoryId", getPcategoryId())
            .append("preasonTypeId", getPreasonTypeId())
            .append("psolutionTypeId", getPsolutionTypeId())
            .append("reasonDesc", getReasonDesc())
            .append("solutionDesc", getSolutionDesc())
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

package net.factoryopen.myfactoryai.manufacturing.exe.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工序出工对象 exe_process_work
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public class ExeProcessWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 开工单号 */
    @Excel(name = "开工单号")
    private String billNo;

    /** 制造批 */
    @Excel(name = "制造批")
    private Long mlId;

    /** 移转ID */
    @Excel(name = "移转ID")
    private Long moveId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 产品编码 */
    @Excel(name = "产品Id")
    private Long productId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 工序号 */
    @Excel(name = "工序号")
    private Integer processSeq;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    /** 出工日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出工日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDay;

    /** 班别 */
    @Excel(name = "班别")
    private Long workShifitId;

    /** 机台ID */
    @Excel(name = "机台ID")
    private Long machineId;

    /** 模具ID */
    @Excel(name = "模具ID")
    private Long moldId;

    /** 工具ID */
    @Excel(name = "工具ID")
    private Long toolId;

    /** 工具ID */
    @Excel(name = "工具ID")
    private Long gaugeId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 出工数量 */
    @Excel(name = "出工数量")
    private BigDecimal quantityStart;

    /** 报废数量 */
    @Excel(name = "报废数量")
    private BigDecimal quantityScrap;

    /** 分批数量 */
    @Excel(name = "分批数量")
    private BigDecimal quantitySplit;

    /** 重派数量 */
    @Excel(name = "重派数量")
    private BigDecimal quantityRedisp;

    /** 返工数量 */
    @Excel(name = "返工数量")
    private BigDecimal quantityRework;

    /** 待判数量 */
    @Excel(name = "待判数量")
    private BigDecimal quantityPending;

    /** 特采次数 */
    @Excel(name = "特采次数")
    private BigDecimal quantityAod;

    /** 完工数量 */
    @Excel(name = "完工数量")
    private BigDecimal quantityFinish;

    /** 出工时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出工时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 出工人 */
    @Excel(name = "出工人")
    private Long startBy;

    /** 收工时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "收工时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 收工人 */
    @Excel(name = "收工人")
    private Long finishBy;

    /** 班组 */
    private Long groupId;

    /** 产线 */
    private Long lineId;

    /** 部门 */
    private Long deptId;

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

    public Long getMlId() {
        return mlId;
    }

    public void setMlId(Long mlId) {
        this.mlId = mlId;
    }

    public void setMoveId(Long moveId)
    {
        this.moveId = moveId;
    }

    public Long getMoveId() 
    {
        return moveId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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
    public void setWorkDay(Date workDay) 
    {
        this.workDay = workDay;
    }

    public Date getWorkDay() 
    {
        return workDay;
    }
    public void setWorkShifitId(Long workShifitId) 
    {
        this.workShifitId = workShifitId;
    }

    public Long getWorkShifitId() 
    {
        return workShifitId;
    }
    public void setMachineId(Long machineId)
    {
        this.machineId = machineId;
    }

    public Long getMachineId()
    {
        return machineId;
    }
    public void setMoldId(Long moldId)
    {
        this.moldId = moldId;
    }

    public Long getMoldId()
    {
        return moldId;
    }
    public void setToolId(Long toolId)
    {
        this.toolId = toolId;
    }

    public Long getToolId()
    {
        return toolId;
    }
    public void setGaugeId(Long gaugeId)
    {
        this.gaugeId = gaugeId;
    }

    public Long getGaugeId()
    {
        return gaugeId;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantityStart(BigDecimal quantityStart) 
    {
        this.quantityStart = quantityStart;
    }

    public BigDecimal getQuantityStart() 
    {
        return quantityStart;
    }
    public void setQuantityScrap(BigDecimal quantityScrap) 
    {
        this.quantityScrap = quantityScrap;
    }

    public BigDecimal getQuantityScrap() 
    {
        return quantityScrap;
    }
    public void setQuantitySplit(BigDecimal quantitySplit) 
    {
        this.quantitySplit = quantitySplit;
    }

    public BigDecimal getQuantitySplit() 
    {
        return quantitySplit;
    }
    public void setQuantityRedisp(BigDecimal quantityRedisp) 
    {
        this.quantityRedisp = quantityRedisp;
    }

    public BigDecimal getQuantityRedisp() 
    {
        return quantityRedisp;
    }
    public void setQuantityRework(BigDecimal quantityRework) 
    {
        this.quantityRework = quantityRework;
    }

    public BigDecimal getQuantityRework() 
    {
        return quantityRework;
    }
    public void setQuantityPending(BigDecimal quantityPending) 
    {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityPending() 
    {
        return quantityPending;
    }
    public void setQuantityAod(BigDecimal quantityAod) 
    {
        this.quantityAod = quantityAod;
    }

    public BigDecimal getQuantityAod() 
    {
        return quantityAod;
    }
    public void setQuantityFinish(BigDecimal quantityFinish) 
    {
        this.quantityFinish = quantityFinish;
    }

    public BigDecimal getQuantityFinish() 
    {
        return quantityFinish;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setStartBy(Long startBy) 
    {
        this.startBy = startBy;
    }

    public Long getStartBy() 
    {
        return startBy;
    }
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setFinishBy(Long finishBy) 
    {
        this.finishBy = finishBy;
    }

    public Long getFinishBy() 
    {
        return finishBy;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
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
            .append("billNo", getBillNo())
            .append("mlId", getMlId())
            .append("moveId", getMoveId())
            .append("status", getStatus())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("processSeq", getProcessSeq())
            .append("processId", getProcessId())
            .append("workDay", getWorkDay())
            .append("workShifitId", getWorkShifitId())
            .append("machineId", getMachineId())
            .append("moldId", getMoldId())
            .append("toolId", getToolId())
            .append("gaugeId", getGaugeId())
            .append("unitId", getUnitId())
            .append("quantityStart", getQuantityStart())
            .append("quantityScrap", getQuantityScrap())
            .append("quantitySplit", getQuantitySplit())
            .append("quantityRedisp", getQuantityRedisp())
            .append("quantityRework", getQuantityRework())
            .append("quantityPending", getQuantityPending())
            .append("quantityAod", getQuantityAod())
            .append("quantityFinish", getQuantityFinish())
            .append("startTime", getStartTime())
            .append("startBy", getStartBy())
            .append("finishTime", getFinishTime())
            .append("finishBy", getFinishBy())
            .append("groupId", getGroupId())
            .append("lineId", getLineId())
            .append("deptId", getDeptId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

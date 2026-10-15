package net.factoryopen.myfactoryai.manufacturing.exe.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产成品入库对象 pln_ml
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ExeMlStockin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 批次号 */
    @Excel(name = "批次号")
    private String billNo;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品规格 */
    private String productSpec;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 工序进度 */
    private Integer processProgress;

    /** 优先级 */
    @Excel(name = "优先级")
    private Integer priority;

    /** 现工序 */
    private Integer currentProcessSeq;

    /** 现工序码 */
    private Long currentProcessId;

    /** 现班组 */
    private Long currentWorkgroupId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 派工数量 */
    @Excel(name = "派工数量")
    private BigDecimal quantityDispatched;

    /** 接收数量 */
    @Excel(name = "接收数量")
    private BigDecimal quantityAccepted;

    /** 报废数量 */
    @Excel(name = "报废数量")
    private BigDecimal quantityScrap;

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

    /** 分批数量 */
    @Excel(name = "分批数量")
    private BigDecimal quantitySplit;

    /** 产出数量 */
    private BigDecimal quantityOutput;

    /** 上线日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上线日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scheduleDay;

    /** 生产交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDay;

    /** 产线 */
    @Excel(name = "产线")
    private Long lineId;

    /** 项目 */
    private Long projectId;

    /** 批次类型 */
    @Excel(name = "批次类型")
    private Integer mlType;

    /** 来源批次 */
    private Long sourceMlId;

    /** 工单 */
    private Long woId;

    /** 派工时间 */
    private Date dispatchingTime;

    /** 派工人 */
    private Long dispatchingBy;

    /** 接收时间 */
    private Date acceptingTime;

    /** 接收人 */
    private Long acceptingBy;

    /** 开工时间 */
    private Date startTime;

    /** 开工人 */
    private Long startBy;

    /** 完工时间 */
    private Date finishTime;

    /** 完工人 */
    private Long finishBy;

    /** 请入库时间 */
    private Date stockreqTime;

    /** 请入库人 */
    private Long stockreqBy;

    /** 入库时间 */
    private Date stockinTime;

    /** 入库人 */
    private Long stockinBy;

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
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setProcessProgress(Integer processProgress) 
    {
        this.processProgress = processProgress;
    }

    public Integer getProcessProgress() 
    {
        return processProgress;
    }
    public void setPriority(Integer priority) 
    {
        this.priority = priority;
    }

    public Integer getPriority() 
    {
        return priority;
    }
    public void setCurrentProcessSeq(Integer currentProcessSeq) 
    {
        this.currentProcessSeq = currentProcessSeq;
    }

    public Integer getCurrentProcessSeq() 
    {
        return currentProcessSeq;
    }
    public void setCurrentProcessId(Long currentProcessId) 
    {
        this.currentProcessId = currentProcessId;
    }

    public Long getCurrentProcessId() 
    {
        return currentProcessId;
    }
    public void setCurrentWorkgroupId(Long currentWorkgroupId) 
    {
        this.currentWorkgroupId = currentWorkgroupId;
    }

    public Long getCurrentWorkgroupId() 
    {
        return currentWorkgroupId;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantityDispatched(BigDecimal quantityDispatched) 
    {
        this.quantityDispatched = quantityDispatched;
    }

    public BigDecimal getQuantityDispatched() 
    {
        return quantityDispatched;
    }
    public void setQuantityAccepted(BigDecimal quantityAccepted) 
    {
        this.quantityAccepted = quantityAccepted;
    }

    public BigDecimal getQuantityAccepted() 
    {
        return quantityAccepted;
    }
    public void setQuantityScrap(BigDecimal quantityScrap) 
    {
        this.quantityScrap = quantityScrap;
    }

    public BigDecimal getQuantityScrap() 
    {
        return quantityScrap;
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
    public void setQuantitySplit(BigDecimal quantitySplit) 
    {
        this.quantitySplit = quantitySplit;
    }

    public BigDecimal getQuantitySplit() 
    {
        return quantitySplit;
    }
    public void setQuantityOutput(BigDecimal quantityOutput) 
    {
        this.quantityOutput = quantityOutput;
    }

    public BigDecimal getQuantityOutput() 
    {
        return quantityOutput;
    }
    public void setScheduleDay(Date scheduleDay) 
    {
        this.scheduleDay = scheduleDay;
    }

    public Date getScheduleDay() 
    {
        return scheduleDay;
    }
    public void setDeliveryDay(Date deliveryDay) 
    {
        this.deliveryDay = deliveryDay;
    }

    public Date getDeliveryDay() 
    {
        return deliveryDay;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setMlType(Integer mlType) 
    {
        this.mlType = mlType;
    }

    public Integer getMlType() 
    {
        return mlType;
    }
    public void setSourceMlId(Long sourceMlId) 
    {
        this.sourceMlId = sourceMlId;
    }

    public Long getSourceMlId() 
    {
        return sourceMlId;
    }
    public void setWoId(Long woId) 
    {
        this.woId = woId;
    }

    public Long getWoId() 
    {
        return woId;
    }
    public void setDispatchingTime(Date dispatchingTime) 
    {
        this.dispatchingTime = dispatchingTime;
    }

    public Date getDispatchingTime() 
    {
        return dispatchingTime;
    }
    public void setDispatchingBy(Long dispatchingBy) 
    {
        this.dispatchingBy = dispatchingBy;
    }

    public Long getDispatchingBy() 
    {
        return dispatchingBy;
    }
    public void setAcceptingTime(Date acceptingTime) 
    {
        this.acceptingTime = acceptingTime;
    }

    public Date getAcceptingTime() 
    {
        return acceptingTime;
    }
    public void setAcceptingBy(Long acceptingBy) 
    {
        this.acceptingBy = acceptingBy;
    }

    public Long getAcceptingBy() 
    {
        return acceptingBy;
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
    public void setStockreqTime(Date stockreqTime) 
    {
        this.stockreqTime = stockreqTime;
    }

    public Date getStockreqTime() 
    {
        return stockreqTime;
    }
    public void setStockreqBy(Long stockreqBy) 
    {
        this.stockreqBy = stockreqBy;
    }

    public Long getStockreqBy() 
    {
        return stockreqBy;
    }
    public void setStockinTime(Date stockinTime) 
    {
        this.stockinTime = stockinTime;
    }

    public Date getStockinTime() 
    {
        return stockinTime;
    }
    public void setStockinBy(Long stockinBy) 
    {
        this.stockinBy = stockinBy;
    }

    public Long getStockinBy() 
    {
        return stockinBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("status", getStatus())
            .append("processProgress", getProcessProgress())
            .append("priority", getPriority())
            .append("currentProcessSeq", getCurrentProcessSeq())
            .append("currentProcessId", getCurrentProcessId())
            .append("currentWorkgroupId", getCurrentWorkgroupId())
            .append("unitId", getUnitId())
            .append("quantityDispatched", getQuantityDispatched())
            .append("quantityAccepted", getQuantityAccepted())
            .append("quantityScrap", getQuantityScrap())
            .append("quantityRedisp", getQuantityRedisp())
            .append("quantityRework", getQuantityRework())
            .append("quantityPending", getQuantityPending())
            .append("quantityAod", getQuantityAod())
            .append("quantitySplit", getQuantitySplit())
            .append("quantityOutput", getQuantityOutput())
            .append("scheduleDay", getScheduleDay())
            .append("deliveryDay", getDeliveryDay())
            .append("lineId", getLineId())
            .append("projectId", getProjectId())
            .append("mlType", getMlType())
            .append("sourceMlId", getSourceMlId())
            .append("woId", getWoId())
            .append("dispatchingTime", getDispatchingTime())
            .append("dispatchingBy", getDispatchingBy())
            .append("acceptingTime", getAcceptingTime())
            .append("acceptingBy", getAcceptingBy())
            .append("startTime", getStartTime())
            .append("startBy", getStartBy())
            .append("finishTime", getFinishTime())
            .append("finishBy", getFinishBy())
            .append("stockreqTime", getStockreqTime())
            .append("stockreqBy", getStockreqBy())
            .append("stockinTime", getStockinTime())
            .append("stockinBy", getStockinBy())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工单信息对象 pln_wo
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class PlnWoInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 工单号 */
    @Excel(name = "工单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 计划数量 */
    @Excel(name = "计划数量")
    private BigDecimal quantityPlanned;

    /** 实派数量 */
    private BigDecimal quantityAssigned;

    /** 生产交期 */
    private Date needDay;

    /** 承诺交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "承诺交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date promiseDay;

    /** 下单日期 */
    private Date orderDay;

    /** 优先级 */
    @Excel(name = "优先级")
    private Integer priority;

    /** 产线 */
    @Excel(name = "产线")
    private Long lineId;

    /** 类型 */
    @Excel(name = "类型")
    private Integer woType;

    /** 主计划 */
    @Excel(name = "主计划")
    private Long mpsId;

    /** 项目 */
    @Excel(name = "项目")
    private Long projectId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long soId;

    /** 订单项ID */
    @Excel(name = "订单项ID")
    private Long soItemId;

    /** 产出数量 */
    @Excel(name = "产出数量")
    private BigDecimal quantityOutput;

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
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantityPlanned(BigDecimal quantityPlanned) 
    {
        this.quantityPlanned = quantityPlanned;
    }

    public BigDecimal getQuantityPlanned() 
    {
        return quantityPlanned;
    }
    public void setQuantityAssigned(BigDecimal quantityAssigned) 
    {
        this.quantityAssigned = quantityAssigned;
    }

    public BigDecimal getQuantityAssigned() 
    {
        return quantityAssigned;
    }
    public void setNeedDay(Date needDay) 
    {
        this.needDay = needDay;
    }

    public Date getNeedDay() 
    {
        return needDay;
    }
    public void setPromiseDay(Date promiseDay) 
    {
        this.promiseDay = promiseDay;
    }

    public Date getPromiseDay() 
    {
        return promiseDay;
    }
    public void setOrderDay(Date orderDay) 
    {
        this.orderDay = orderDay;
    }

    public Date getOrderDay() 
    {
        return orderDay;
    }
    public void setPriority(Integer priority) 
    {
        this.priority = priority;
    }

    public Integer getPriority() 
    {
        return priority;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setWoType(Integer woType) 
    {
        this.woType = woType;
    }

    public Integer getWoType() 
    {
        return woType;
    }
    public void setMpsId(Long mpsId) 
    {
        this.mpsId = mpsId;
    }

    public Long getMpsId() 
    {
        return mpsId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setSoId(Long soId) 
    {
        this.soId = soId;
    }

    public Long getSoId() 
    {
        return soId;
    }
    public void setSoItemId(Long soItemId) 
    {
        this.soItemId = soItemId;
    }

    public Long getSoItemId() 
    {
        return soItemId;
    }
    public void setQuantityOutput(BigDecimal quantityOutput) 
    {
        this.quantityOutput = quantityOutput;
    }

    public BigDecimal getQuantityOutput() 
    {
        return quantityOutput;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("unitId", getUnitId())
            .append("quantityPlanned", getQuantityPlanned())
            .append("quantityAssigned", getQuantityAssigned())
            .append("needDay", getNeedDay())
            .append("promiseDay", getPromiseDay())
            .append("orderDay", getOrderDay())
            .append("priority", getPriority())
            .append("lineId", getLineId())
            .append("woType", getWoType())
            .append("mpsId", getMpsId())
            .append("projectId", getProjectId())
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
            .append("quantityOutput", getQuantityOutput())
            .append("quantityScrap", getQuantityScrap())
            .append("quantityRedisp", getQuantityRedisp())
            .append("quantityRework", getQuantityRework())
            .append("quantityPending", getQuantityPending())
            .append("quantityAod", getQuantityAod())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

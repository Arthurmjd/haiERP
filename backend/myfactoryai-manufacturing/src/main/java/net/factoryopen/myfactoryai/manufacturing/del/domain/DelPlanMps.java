package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主计划对象 del_mps
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class DelPlanMps extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    private Long id;

    /** 主计划号 */
    @Excel(name = "主计划号")
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

    /** 派工数量 */
    @Excel(name = "派工数量")
    private BigDecimal quantityDispatched;

    /** 完成数量 */
    @Excel(name = "完成数量")
    private BigDecimal quantityFinished;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date needDay;

    /** 承诺交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "承诺交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date promiseDay;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDay;

    /** 优先级 */
    @Excel(name = "优先级")
    private Integer priority;

    /** 类型 */
    @Excel(name = "类型")
    private Integer mpsType;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 销售订单 */
    @Excel(name = "销售订单")
    private Long soId;

    /** 订单项 */
    @Excel(name = "订单项")
    private Long soItemId;

    /** 项目 */
    @Excel(name = "项目")
    private Long projectId;

    /** 销售员 */
    @Excel(name = "销售员")
    private Long salesId;

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
    public void setQuantityDispatched(BigDecimal quantityDispatched) 
    {
        this.quantityDispatched = quantityDispatched;
    }

    public BigDecimal getQuantityDispatched() 
    {
        return quantityDispatched;
    }
    public void setQuantityFinished(BigDecimal quantityFinished) 
    {
        this.quantityFinished = quantityFinished;
    }

    public BigDecimal getQuantityFinished() 
    {
        return quantityFinished;
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
    public void setMpsType(Integer mpsType) 
    {
        this.mpsType = mpsType;
    }

    public Integer getMpsType() 
    {
        return mpsType;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
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
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setSalesId(Long salesId) 
    {
        this.salesId = salesId;
    }

    public Long getSalesId() 
    {
        return salesId;
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
            .append("quantityDispatched", getQuantityDispatched())
            .append("quantityFinished", getQuantityFinished())
            .append("needDay", getNeedDay())
            .append("promiseDay", getPromiseDay())
            .append("orderDay", getOrderDay())
            .append("priority", getPriority())
            .append("mpsType", getMpsType())
            .append("customerId", getCustomerId())
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
            .append("projectId", getProjectId())
            .append("salesId", getSalesId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

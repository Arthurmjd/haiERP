package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * MRP工单项对象 pln_mrp_wo
 * 
 * @author admin
 * @date 2024-07-04
 */
public class PlnMrpWo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 主计划 */
    @Excel(name = "主计划")
    private Long mpsId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 生产交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产交期", width = 30, dateFormat = "yyyy-MM-dd")
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

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 工单类型 */
    @Excel(name = "工单类型")
    private Integer woType;

    /** 销售订单 */
    @Excel(name = "销售订单")
    private Long soId;

    /** 订单项 */
    @Excel(name = "订单项")
    private Long soItemId;

    /** 项目 */
    @Excel(name = "项目")
    private Long projectId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setMpsId(Long mpsId) 
    {
        this.mpsId = mpsId;
    }

    public Long getMpsId() 
    {
        return mpsId;
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

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }

    public String getCustomerProductNo() {
        return customerProductNo;
    }

    public void setCustomerProductNo(String customerProductNo) {
        this.customerProductNo = customerProductNo;
    }

    public String getAbroadProductNo() {
        return abroadProductNo;
    }

    public void setAbroadProductNo(String abroadProductNo) {
        this.abroadProductNo = abroadProductNo;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("status", getStatus())
            .append("mpsId", getMpsId())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("abroadProductNo", getAbroadProductNo())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("needDay", getNeedDay())
            .append("promiseDay", getPromiseDay())
            .append("orderDay", getOrderDay())
            .append("priority", getPriority())
            .append("lineId", getLineId())
            .append("woType", getWoType())
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("projectId", getProjectId())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

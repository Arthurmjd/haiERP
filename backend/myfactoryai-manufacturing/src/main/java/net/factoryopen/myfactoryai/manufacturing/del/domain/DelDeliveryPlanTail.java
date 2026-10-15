package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发货计划尾情对象 del_plan_item
 * 
 * @author admin
 * @date 2024-07-09
 */
public class DelDeliveryPlanTail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 外协单号 */
    @Excel(name = "发货计划号")
    private String billNo;

    /** 状态 */
    private Integer status;

    /** 销售订单 */
    private Long soId;

    /** 订单项 */
    private Long soItemId;

    /** 产品编码 */
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

    /** 单位 */
    private Long unitId;

    /** 需求日期 */
    private Date needDay;

    /** 承诺日期 */
    private Date promiseDay;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDay;

    /** 优先级 */
    private Integer priority;

    /** 计发数量 */
    private BigDecimal quantityPlanned;

    /** 累发数量 */
    private BigDecimal quantityReleased;

    /** 原价 */
    private BigDecimal price;

    /** 价折扣 */
    private BigDecimal discountPrice;

    /** 金额 */
    private BigDecimal amount;

    /** 额折扣 */
    private BigDecimal discountRate;

    /** 最终金额 */
    private BigDecimal finalAmount;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 销售员ID */
    @Excel(name = "销售员ID")
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
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
    public void setQuantityPlanned(BigDecimal quantityPlanned) 
    {
        this.quantityPlanned = quantityPlanned;
    }

    public BigDecimal getQuantityPlanned() 
    {
        return quantityPlanned;
    }
    public void setQuantityReleased(BigDecimal quantityReleased) 
    {
        this.quantityReleased = quantityReleased;
    }

    public BigDecimal getQuantityReleased() 
    {
        return quantityReleased;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setDiscountPrice(BigDecimal discountPrice) 
    {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountPrice() 
    {
        return discountPrice;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setDiscountRate(BigDecimal discountRate) 
    {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountRate() 
    {
        return discountRate;
    }
    public void setFinalAmount(BigDecimal finalAmount) 
    {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getFinalAmount() 
    {
        return finalAmount;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
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
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("unitId", getUnitId())
            .append("needDay", getNeedDay())
            .append("promiseDay", getPromiseDay())
            .append("orderDay", getOrderDay())
            .append("priority", getPriority())
            .append("quantityPlanned", getQuantityPlanned())
            .append("quantityReleased", getQuantityReleased())
            .append("price", getPrice())
            .append("discountPrice", getDiscountPrice())
            .append("amount", getAmount())
            .append("discountRate", getDiscountRate())
            .append("finalAmount", getFinalAmount())
            .append("customerId", getCustomerId())
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

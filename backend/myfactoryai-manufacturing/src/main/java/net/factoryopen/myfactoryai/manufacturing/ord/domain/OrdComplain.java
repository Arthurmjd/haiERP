package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客诉登记对象 ord_complain
 * 
 * @author admin
 * @date 2024-07-04
 */
public class OrdComplain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客诉单号 */
    @Excel(name = "客诉单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long soId;

    /** 订单项ID */
    @Excel(name = "订单项ID")
    private Long soItemId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 客诉类型 */
    @Excel(name = "客诉类型")
    private Integer complainType;

    /** 发货单号 */
    @Excel(name = "发货单号")
    private String gdNo;

    /** 制造批号 */
    @Excel(name = "制造批号")
    private String lotNo;

    /** 客诉日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "客诉日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date complainDay;

    /** 客诉人 */
    @Excel(name = "客诉人")
    private String complainBy;

    /** 客诉电话 */
    @Excel(name = "客诉电话")
    private String complainPhone;

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
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setComplainType(Integer complainType) 
    {
        this.complainType = complainType;
    }

    public Integer getComplainType() 
    {
        return complainType;
    }
    public void setGdNo(String gdNo) 
    {
        this.gdNo = gdNo;
    }

    public String getGdNo() 
    {
        return gdNo;
    }
    public void setLotNo(String lotNo) 
    {
        this.lotNo = lotNo;
    }

    public String getLotNo() 
    {
        return lotNo;
    }
    public void setComplainDay(Date complainDay) 
    {
        this.complainDay = complainDay;
    }

    public Date getComplainDay() 
    {
        return complainDay;
    }
    public void setComplainBy(String complainBy) 
    {
        this.complainBy = complainBy;
    }

    public String getComplainBy() 
    {
        return complainBy;
    }
    public void setComplainPhone(String complainPhone) 
    {
        this.complainPhone = complainPhone;
    }

    public String getComplainPhone() 
    {
        return complainPhone;
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
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("unitId", getUnitId())
            .append("price", getPrice())
            .append("quantity", getQuantity())
            .append("amount", getAmount())
            .append("complainType", getComplainType())
            .append("gdNo", getGdNo())
            .append("lotNo", getLotNo())
            .append("complainDay", getComplainDay())
            .append("complainBy", getComplainBy())
            .append("complainPhone", getComplainPhone())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

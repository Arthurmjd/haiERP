package net.factoryopen.myfactoryai.manufacturing.cha.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户数据管理对象 cha_customer
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ChaCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 客户全名 */
    @Excel(name = "客户全名")
    private String customerFullname;

    /** 客户全名 */
    @Excel(name = "客户别名")
    private String customerAliasname;

    /** 客户编码 */
    @Excel(name = "客户编码")
    private String customerCode;

    /** 客户类型 */
    @Excel(name = "客户类型")
    private Long customerType;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 付款方式 */
    @Excel(name = "付款方式")
    private Long payPatternId;

    /** 发票类型 */
    @Excel(name = "发票类型")
    private Long invoicePatternId;

    /** 币别 */
    @Excel(name = "币别")
    private Long currencyId;

    /** 出货方式 */
    @Excel(name = "出货方式")
    private Long shipmentTypeId;

    /** 销售员 */
    @Excel(name = "销售员")
    private Long salesId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setCustomerFullname(String customerFullname) 
    {
        this.customerFullname = customerFullname;
    }

    public String getCustomerFullname() 
    {
        return customerFullname;
    }
    public void setCustomerCode(String customerCode) 
    {
        this.customerCode = customerCode;
    }

    public String getCustomerAliasname() {
        return customerAliasname;
    }

    public void setCustomerAliasname(String customerAliasname) {
        this.customerAliasname = customerAliasname;
    }

    public String getCustomerCode()
    {
        return customerCode;
    }
    public void setCustomerType(Long customerType) 
    {
        this.customerType = customerType;
    }

    public Long getCustomerType() 
    {
        return customerType;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setPayPatternId(Long payPatternId) 
    {
        this.payPatternId = payPatternId;
    }

    public Long getPayPatternId() 
    {
        return payPatternId;
    }
    public void setInvoicePatternId(Long invoicePatternId) 
    {
        this.invoicePatternId = invoicePatternId;
    }

    public Long getInvoicePatternId() 
    {
        return invoicePatternId;
    }
    public void setCurrencyId(Long currencyId) 
    {
        this.currencyId = currencyId;
    }

    public Long getCurrencyId() 
    {
        return currencyId;
    }
    public void setShipmentTypeId(Long shipmentTypeId) 
    {
        this.shipmentTypeId = shipmentTypeId;
    }

    public Long getShipmentTypeId() 
    {
        return shipmentTypeId;
    }
    public void setSalesId(Long salesId) 
    {
        this.salesId = salesId;
    }

    public Long getSalesId() 
    {
        return salesId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("customerFullname", getCustomerFullname())
            .append("customerAliasname", getCustomerAliasname())
            .append("customerCode", getCustomerCode())
            .append("customerType", getCustomerType())
            .append("contact", getContact())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("payPatternId", getPayPatternId())
            .append("invoicePatternId", getInvoicePatternId())
            .append("currencyId", getCurrencyId())
            .append("shipmentTypeId", getShipmentTypeId())
            .append("salesId", getSalesId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

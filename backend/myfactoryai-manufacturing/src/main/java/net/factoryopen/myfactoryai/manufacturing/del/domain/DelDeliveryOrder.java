package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发货单管理对象 del_gd
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public class DelDeliveryOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 发货单编号 */
    @Excel(name = "发货单编号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDay;

    /** 发货地址ID */
    @Excel(name = "发货地址ID")
    private Long addressId;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 手机 */
    @Excel(name = "手机")
    private String phone;

    /** 物流单号 */
    @Excel(name = "物流单号")
    private String expressNo;

    /** 发票单号 */
    @Excel(name = "发票单号")
    private String invoiceNo;

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
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setDeliveryDay(Date deliveryDay) 
    {
        this.deliveryDay = deliveryDay;
    }

    public Date getDeliveryDay() 
    {
        return deliveryDay;
    }
    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
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
    public void setExpressNo(String expressNo) 
    {
        this.expressNo = expressNo;
    }

    public String getExpressNo() 
    {
        return expressNo;
    }
    public void setInvoiceNo(String invoiceNo) 
    {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() 
    {
        return invoiceNo;
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
            .append("customerId", getCustomerId())
            .append("deliveryDay", getDeliveryDay())
            .append("addressId", getAddressId())
            .append("contact", getContact())
            .append("phone", getPhone())
            .append("expressNo", getExpressNo())
            .append("invoiceNo", getInvoiceNo())
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

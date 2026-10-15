package net.factoryopen.myfactoryai.manufacturing.cha.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户地址对象 cha_customer_address
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ChaCustomerAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 手机 */
    @Excel(name = "手机")
    private String phone;

    /** 经度 */
    @Excel(name = "经度")
    private Long Longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private Long latitude;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
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
    public void setLongitude(Long Longitude) 
    {
        this.Longitude = Longitude;
    }

    public Long getLongitude() 
    {
        return Longitude;
    }
    public void setLatitude(Long latitude) 
    {
        this.latitude = latitude;
    }

    public Long getLatitude() 
    {
        return latitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("address", getAddress())
            .append("contact", getContact())
            .append("phone", getPhone())
            .append("Longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

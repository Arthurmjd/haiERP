package net.factoryopen.myfactoryai.manufacturing.cha.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户人员对象 cha_customer_contact
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ChaCustomerContact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 级别 */
    @Excel(name = "级别")
    private String talkLevel;

    /** 手机1 */
    @Excel(name = "手机1")
    private String cellphone1;

    /** 手机2 */
    @Excel(name = "手机2")
    private String cellphone2;

    /** 微信 */
    @Excel(name = "微信")
    private String wechat;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 电话 */
    @Excel(name = "电话")
    private String telephone;

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
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setTalkLevel(String talkLevel) 
    {
        this.talkLevel = talkLevel;
    }

    public String getTalkLevel() 
    {
        return talkLevel;
    }
    public void setCellphone1(String cellphone1) 
    {
        this.cellphone1 = cellphone1;
    }

    public String getCellphone1() 
    {
        return cellphone1;
    }
    public void setCellphone2(String cellphone2) 
    {
        this.cellphone2 = cellphone2;
    }

    public String getCellphone2() 
    {
        return cellphone2;
    }
    public void setWechat(String wechat) 
    {
        this.wechat = wechat;
    }

    public String getWechat() 
    {
        return wechat;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getTelephone() 
    {
        return telephone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("contact", getContact())
            .append("talkLevel", getTalkLevel())
            .append("cellphone1", getCellphone1())
            .append("cellphone2", getCellphone2())
            .append("wechat", getWechat())
            .append("email", getEmail())
            .append("telephone", getTelephone())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

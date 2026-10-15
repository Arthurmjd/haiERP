package net.factoryopen.myfactoryai.manufacturing.cha.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户账号对象 cha_customer_bank
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ChaCustomerBank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 账号 */
    @Excel(name = "账号")
    private String accountNo;

    /** 开户行 */
    @Excel(name = "开户行")
    private String bankName;

    /** 开户人 */
    @Excel(name = "开户人")
    private String ownerName;

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
    public void setAccountNo(String accountNo) 
    {
        this.accountNo = accountNo;
    }

    public String getAccountNo() 
    {
        return accountNo;
    }
    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }
    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("accountNo", getAccountNo())
            .append("bankName", getBankName())
            .append("ownerName", getOwnerName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

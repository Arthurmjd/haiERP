package net.factoryopen.myfactoryai.manufacturing.del.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商账号对象 del_supplier_bank
 * 
 * @author admin
 * @date 2024-07-04
 */
public class DelSupplierBank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 供应商 */
    @Excel(name = "供应商")
    private Long supplierId;

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
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
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
            .append("supplierId", getSupplierId())
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

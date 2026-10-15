package net.factoryopen.myfactoryai.manufacturing.snc.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 来料收货登记对象 snc_gr
 * 
 * @author admin
 * @date 2024-07-26
 */
public class SncGr extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 收货单号 */
    @Excel(name = "收货单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 收货类型 */
    @Excel(name = "收货类型")
    private Integer grType;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 收货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveDay;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactPerson;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

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
    public void setGrType(Integer grType) 
    {
        this.grType = grType;
    }

    public Integer getGrType() 
    {
        return grType;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setReceiveDay(Date receiveDay) 
    {
        this.receiveDay = receiveDay;
    }

    public Date getReceiveDay() 
    {
        return receiveDay;
    }
    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }
    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("grType", getGrType())
            .append("supplierId", getSupplierId())
            .append("receiveDay", getReceiveDay())
            .append("contactPerson", getContactPerson())
            .append("contactPhone", getContactPhone())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

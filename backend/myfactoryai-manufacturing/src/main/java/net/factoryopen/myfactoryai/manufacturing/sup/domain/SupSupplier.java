package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商管理对象 sup_supplier
 * 
 * @author jitfactory-generator
 * @date 2024-07-26
 */
public class SupSupplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /** 全名 */
    @Excel(name = "全名")
    private String fullName;

    /** 物料供应否 */
    @Excel(name = "物料供应否")
    private String materialEnabled;

    /** 转包否 */
    @Excel(name = "转包否")
    private String ordorOsEnabled;

    /** 工序委外否 */
    @Excel(name = "工序委外否")
    private String processOsEnabled;

    /** 物流配运否 */
    @Excel(name = "物流配运否")
    private String logisticEnabled;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 在用否 */
    @Excel(name = "在用否")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setSupplierCode(String supplierCode) 
    {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() 
    {
        return supplierCode;
    }
    public void setFullName(String fullName) 
    {
        this.fullName = fullName;
    }

    public String getFullName() 
    {
        return fullName;
    }
    public void setMaterialEnabled(String materialEnabled) 
    {
        this.materialEnabled = materialEnabled;
    }

    public String getMaterialEnabled() 
    {
        return materialEnabled;
    }
    public void setOrdorOsEnabled(String ordorOsEnabled) 
    {
        this.ordorOsEnabled = ordorOsEnabled;
    }

    public String getOrdorOsEnabled() 
    {
        return ordorOsEnabled;
    }
    public void setProcessOsEnabled(String processOsEnabled) 
    {
        this.processOsEnabled = processOsEnabled;
    }

    public String getProcessOsEnabled() 
    {
        return processOsEnabled;
    }
    public void setLogisticEnabled(String logisticEnabled) 
    {
        this.logisticEnabled = logisticEnabled;
    }

    public String getLogisticEnabled() 
    {
        return logisticEnabled;
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
            .append("supplierName", getSupplierName())
            .append("supplierCode", getSupplierCode())
            .append("fullName", getFullName())
            .append("materialEnabled", getMaterialEnabled())
            .append("ordorOsEnabled", getOrdorOsEnabled())
            .append("processOsEnabled", getProcessOsEnabled())
            .append("logisticEnabled", getLogisticEnabled())
            .append("contact", getContact())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

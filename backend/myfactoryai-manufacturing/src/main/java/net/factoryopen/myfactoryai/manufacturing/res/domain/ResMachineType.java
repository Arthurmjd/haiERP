package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机型对象 res_machine_type
 * 
 * @author admin
 * @date 2024-08-11
 */
public class ResMachineType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机型ID */
    private Long id;

    /** 机型名称 */
    @Excel(name = "机型名称")
    private String typeName;

    /** 节拍 */
    @Excel(name = "节拍")
    private Long takt;

    /** 节拍秒数 */
    @Excel(name = "节拍秒数")
    private Long taktSecsize;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 供应商联系人 */
    @Excel(name = "供应商联系人")
    private String supplierContact;

    /** 供应商电话 */
    @Excel(name = "供应商电话")
    private String supplierTel;

    /** 通信协议 */
    @Excel(name = "通信协议")
    private String plcProtocol;

    /** PLC供应商 */
    @Excel(name = "PLC供应商")
    private String plcSupplier;

    /** PLC规格 */
    @Excel(name = "PLC规格")
    private String plcSpec;

    /** 基工序图标 */
    @Excel(name = "基工序图标")
    private String icoUrl;

    /** 横坐标 */
    @Excel(name = "横坐标")
    private Integer xleft;

    /** 纵坐标 */
    @Excel(name = "纵坐标")
    private Integer ytop;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setTakt(Long takt) 
    {
        this.takt = takt;
    }

    public Long getTakt() 
    {
        return takt;
    }
    public void setTaktSecsize(Long taktSecsize) 
    {
        this.taktSecsize = taktSecsize;
    }

    public Long getTaktSecsize() 
    {
        return taktSecsize;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setSupplierContact(String supplierContact) 
    {
        this.supplierContact = supplierContact;
    }

    public String getSupplierContact() 
    {
        return supplierContact;
    }
    public void setSupplierTel(String supplierTel) 
    {
        this.supplierTel = supplierTel;
    }

    public String getSupplierTel() 
    {
        return supplierTel;
    }
    public void setPlcProtocol(String plcProtocol) 
    {
        this.plcProtocol = plcProtocol;
    }

    public String getPlcProtocol() 
    {
        return plcProtocol;
    }
    public void setPlcSupplier(String plcSupplier) 
    {
        this.plcSupplier = plcSupplier;
    }

    public String getPlcSupplier() 
    {
        return plcSupplier;
    }
    public void setPlcSpec(String plcSpec) 
    {
        this.plcSpec = plcSpec;
    }

    public String getPlcSpec() 
    {
        return plcSpec;
    }
    public void setIcoUrl(String icoUrl) 
    {
        this.icoUrl = icoUrl;
    }

    public String getIcoUrl() 
    {
        return icoUrl;
    }
    public void setXleft(Integer xleft) 
    {
        this.xleft = xleft;
    }

    public Integer getXleft() 
    {
        return xleft;
    }
    public void setYtop(Integer ytop) 
    {
        this.ytop = ytop;
    }

    public Integer getYtop() 
    {
        return ytop;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeName", getTypeName())
            .append("takt", getTakt())
            .append("taktSecsize", getTaktSecsize())
            .append("supplierName", getSupplierName())
            .append("supplierContact", getSupplierContact())
            .append("supplierTel", getSupplierTel())
            .append("plcProtocol", getPlcProtocol())
            .append("plcSupplier", getPlcSupplier())
            .append("plcSpec", getPlcSpec())
            .append("icoUrl", getIcoUrl())
            .append("xleft", getXleft())
            .append("ytop", getYtop())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

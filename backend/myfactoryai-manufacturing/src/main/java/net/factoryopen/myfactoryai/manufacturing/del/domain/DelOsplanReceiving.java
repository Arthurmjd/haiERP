package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 外协收货对象 del_osplan_receiving
 * 
 * @author admin
 * @date 2024-07-09
 */
public class DelOsplanReceiving extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 外协收货单号 */
    @Excel(name = "外协收货单号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 外协计划ID */
    @Excel(name = "外协计划ID")
    private Long osplanId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品编码 */
    private String productCode;

    /** 产品名称 */
    private String productName;

    /** 产品规格 */
    private String productSpec;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 计划数量 */
    @Excel(name = "计划数量")
    private BigDecimal quantityReceived;

    /** 报废数量 */
    @Excel(name = "报废数量")
    private BigDecimal quantityScrap;

    /** 重派数量 */
    @Excel(name = "重派数量")
    private BigDecimal quantityRedisp;

    /** 返工数量 */
    @Excel(name = "返工数量")
    private BigDecimal quantityRework;

    /** 待判数量 */
    @Excel(name = "待判数量")
    private BigDecimal quantityPending;

    /** 特采次数 */
    @Excel(name = "特采次数")
    private BigDecimal quantityAod;

    /** 产出数量 */
    @Excel(name = "产出数量")
    private BigDecimal quantityOutput;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 客户ID */
    private Long customerId;

    /** 销售订单ID */
    private Long soId;

    /** 订单项ID */
    private Long soItemId;

    /** 项目ID */
    private Long projectId;

    /** 销售员ID */
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
    public void setOsplanId(Long osplanId) 
    {
        this.osplanId = osplanId;
    }

    public Long getOsplanId() 
    {
        return osplanId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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
    public void setQuantityReceived(BigDecimal quantityReceived) 
    {
        this.quantityReceived = quantityReceived;
    }

    public BigDecimal getQuantityReceived() 
    {
        return quantityReceived;
    }
    public void setQuantityScrap(BigDecimal quantityScrap) 
    {
        this.quantityScrap = quantityScrap;
    }

    public BigDecimal getQuantityScrap() 
    {
        return quantityScrap;
    }
    public void setQuantityRedisp(BigDecimal quantityRedisp) 
    {
        this.quantityRedisp = quantityRedisp;
    }

    public BigDecimal getQuantityRedisp() 
    {
        return quantityRedisp;
    }
    public void setQuantityRework(BigDecimal quantityRework) 
    {
        this.quantityRework = quantityRework;
    }

    public BigDecimal getQuantityRework() 
    {
        return quantityRework;
    }
    public void setQuantityPending(BigDecimal quantityPending) 
    {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityPending() 
    {
        return quantityPending;
    }
    public void setQuantityAod(BigDecimal quantityAod) 
    {
        this.quantityAod = quantityAod;
    }

    public BigDecimal getQuantityAod() 
    {
        return quantityAod;
    }
    public void setQuantityOutput(BigDecimal quantityOutput) 
    {
        this.quantityOutput = quantityOutput;
    }

    public BigDecimal getQuantityOutput() 
    {
        return quantityOutput;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }
    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
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
            .append("osplanId", getOsplanId())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("unitId", getUnitId())
            .append("quantityReceived", getQuantityReceived())
            .append("quantityScrap", getQuantityScrap())
            .append("quantityRedisp", getQuantityRedisp())
            .append("quantityRework", getQuantityRework())
            .append("quantityPending", getQuantityPending())
            .append("quantityAod", getQuantityAod())
            .append("quantityOutput", getQuantityOutput())
            .append("supplierId", getSupplierId())
            .append("contactName", getContactName())
            .append("contactPhone", getContactPhone())
            .append("customerId", getCustomerId())
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
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

package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商配额对象 sup_supplier_quota
 * 
 * @author admin
 * @date 2024-07-26
 */
public class SupSupplierQuota extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 优先顺位 */
    @Excel(name = "优先顺位")
    private Integer prioritySeq;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 配额% */
    @Excel(name = "配额%")
    private BigDecimal quotaPercent;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setMaterialSpec(String materialSpec) 
    {
        this.materialSpec = materialSpec;
    }

    public String getMaterialSpec() 
    {
        return materialSpec;
    }
    public void setPrioritySeq(Integer prioritySeq) 
    {
        this.prioritySeq = prioritySeq;
    }

    public Integer getPrioritySeq() 
    {
        return prioritySeq;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setQuotaPercent(BigDecimal quotaPercent) 
    {
        this.quotaPercent = quotaPercent;
    }

    public BigDecimal getQuotaPercent() 
    {
        return quotaPercent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("prioritySeq", getPrioritySeq())
            .append("supplierId", getSupplierId())
            .append("quotaPercent", getQuotaPercent())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

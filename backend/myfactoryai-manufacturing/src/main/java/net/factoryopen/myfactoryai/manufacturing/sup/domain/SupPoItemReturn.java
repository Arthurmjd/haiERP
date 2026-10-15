package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 退货项跟踪对象 sup_po_item_return
 * 
 * @author admin
 * @date 2024-10-11
 */
public class SupPoItemReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 采购单ID */
    @Excel(name = "采购单ID")
    private Long poId;

    /** 单项序号 */
    @Excel(name = "单项序号")
    private Integer seqNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 采购类型 */
    @Excel(name = "采购类型")
    private Integer purchasingType;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 在退数量 */
    @Excel(name = "在退数量")
    private BigDecimal quantityReturning;

    /** 已退数量 */
    @Excel(name = "已退数量")
    private BigDecimal quantityReturned;

    /** 来源单ID */
    @Excel(name = "来源单ID")
    private Long sourceBillId;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String sourceBillNo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPoId(Long poId) 
    {
        this.poId = poId;
    }

    public Long getPoId() 
    {
        return poId;
    }
    public void setSeqNo(Integer seqNo)
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo()
    {
        return seqNo;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setPurchasingType(Integer purchasingType) 
    {
        this.purchasingType = purchasingType;
    }

    public Integer getPurchasingType() 
    {
        return purchasingType;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantityReturning(BigDecimal quantityReturning) 
    {
        this.quantityReturning = quantityReturning;
    }

    public BigDecimal getQuantityReturning() 
    {
        return quantityReturning;
    }
    public void setQuantityReturned(BigDecimal quantityReturned) 
    {
        this.quantityReturned = quantityReturned;
    }

    public BigDecimal getQuantityReturned() 
    {
        return quantityReturned;
    }
    public void setSourceBillId(Long sourceBillId) 
    {
        this.sourceBillId = sourceBillId;
    }

    public Long getSourceBillId() 
    {
        return sourceBillId;
    }
    public void setSourceBillNo(String sourceBillNo) 
    {
        this.sourceBillNo = sourceBillNo;
    }

    public String getSourceBillNo() 
    {
        return sourceBillNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("poId", getPoId())
            .append("seqNo", getSeqNo())
            .append("status", getStatus())
            .append("supplierId", getSupplierId())
            .append("purchasingType", getPurchasingType())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("drawNo", getDrawNo())
            .append("unitId", getUnitId())
            .append("quantityReturning", getQuantityReturning())
            .append("quantityReturned", getQuantityReturned())
            .append("sourceBillId", getSourceBillId())
            .append("sourceBillNo", getSourceBillNo())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

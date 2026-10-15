package net.factoryopen.myfactoryai.manufacturing.snc.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 收货单明细对象 snc_gr_item
 * 
 * @author admin
 * @date 2024-07-26
 */
public class SncGrItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 收货单ID */
    @Excel(name = "收货单ID")
    private Long grId;

    /** 项次号 */
    @Excel(name = "项次号")
    private Integer seqNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 采购单ID */
    @Excel(name = "采购单ID")
    private Long poId;

    /** 采购单明细ID */
    @Excel(name = "采购单明细ID")
    private Long poItemId;

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

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGrId(Long grId) 
    {
        this.grId = grId;
    }

    public Long getGrId() 
    {
        return grId;
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
    public void setPoId(Long poId) 
    {
        this.poId = poId;
    }

    public Long getPoId() 
    {
        return poId;
    }
    public void setPoItemId(Long poItemId) 
    {
        this.poItemId = poItemId;
    }

    public Long getPoItemId() 
    {
        return poItemId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
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
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("grId", getGrId())
            .append("seqNo", getSeqNo())
            .append("status", getStatus())
            .append("poId", getPoId())
            .append("poItemId", getPoItemId())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

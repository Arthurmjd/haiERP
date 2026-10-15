package net.factoryopen.myfactoryai.manufacturing.snc.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出入库请求明细对象 snc_stock_request_item
 * 
 * @author admin
 * @date 2025-05-17
 */
public class SncStockRequestItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 请求ID */
    @Excel(name = "请求ID")
    private Long requestId;

    /** 项次 */
    @Excel(name = "项次")
    private Integer seqNo;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String materialCode;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 材质ID */
    @Excel(name = "材质ID")
    private Long compoundId;

    /** 品类ID */
    @Excel(name = "品类ID")
    private Long categoryId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 物料料性 */
    @Excel(name = "物料料性")
    private Integer materialAvailableType;

    /** 来源批号 */
    @Excel(name = "来源批号")
    private String sourceLotNo;

    /** 现仓库ID */
    @Excel(name = "现仓库ID")
    private Long houseId;

    /** 现储位ID */
    @Excel(name = "现储位ID")
    private Long positionId;

    /** 现库批ID */
    @Excel(name = "现库批ID")
    private Long ilId;

    /** 源项ID */
    @Excel(name = "源项ID")
    private Long sourceId;

    /** 检异常数 */
    @Excel(name = "检异常数")
    private BigDecimal quantityQcExceptional;

    /** 检合格数 */
    @Excel(name = "检合格数")
    private BigDecimal quantityQcGood;

    /** 检不合格数 */
    @Excel(name = "检不合格数")
    private BigDecimal quantityQcNg;

    /** 检重制数 */
    @Excel(name = "检重制数")
    private BigDecimal quantityQcRedisp;

    /** 检返工数 */
    @Excel(name = "检返工数")
    private BigDecimal quantityQcRework;

    /** 检待判数 */
    @Excel(name = "检待判数")
    private BigDecimal quantityQcPending;

    /** 检特采数 */
    @Excel(name = "检特采数")
    private BigDecimal quantityQcAod;

    public SncStockRequestItem clone() throws CloneNotSupportedException {
        return (SncStockRequestItem) super.clone();
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRequestId(Long requestId) 
    {
        this.requestId = requestId;
    }

    public Long getRequestId() 
    {
        return requestId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
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
    public void setMaterialCode(String materialCode) 
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() 
    {
        return materialCode;
    }
    public void setMaterialSpec(String materialSpec) 
    {
        this.materialSpec = materialSpec;
    }

    public String getMaterialSpec() 
    {
        return materialSpec;
    }
    public void setDrawNo(String drawNo) 
    {
        this.drawNo = drawNo;
    }

    public String getDrawNo() 
    {
        return drawNo;
    }
    public void setCustomerProductNo(String customerProductNo) 
    {
        this.customerProductNo = customerProductNo;
    }

    public String getCustomerProductNo() 
    {
        return customerProductNo;
    }
    public void setAbroadProductNo(String abroadProductNo) 
    {
        this.abroadProductNo = abroadProductNo;
    }

    public String getAbroadProductNo() 
    {
        return abroadProductNo;
    }
    public void setCompoundId(Long compoundId) 
    {
        this.compoundId = compoundId;
    }

    public Long getCompoundId() 
    {
        return compoundId;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
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
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setMaterialAvailableType(Integer materialAvailableType) 
    {
        this.materialAvailableType = materialAvailableType;
    }

    public Integer getMaterialAvailableType() 
    {
        return materialAvailableType;
    }
    public void setSourceLotNo(String sourceLotNo) 
    {
        this.sourceLotNo = sourceLotNo;
    }

    public String getSourceLotNo() 
    {
        return sourceLotNo;
    }
    public void setHouseId(Long houseId) 
    {
        this.houseId = houseId;
    }

    public Long getHouseId() 
    {
        return houseId;
    }
    public void setPositionId(Long positionId) 
    {
        this.positionId = positionId;
    }

    public Long getPositionId() 
    {
        return positionId;
    }
    public void setIlId(Long ilId) 
    {
        this.ilId = ilId;
    }

    public Long getIlId() 
    {
        return ilId;
    }
    public void setSourceId(Long sourceId) 
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId() 
    {
        return sourceId;
    }
    public void setQuantityQcExceptional(BigDecimal quantityQcExceptional) 
    {
        this.quantityQcExceptional = quantityQcExceptional;
    }

    public BigDecimal getQuantityQcExceptional() 
    {
        return quantityQcExceptional;
    }
    public void setQuantityQcGood(BigDecimal quantityQcGood) 
    {
        this.quantityQcGood = quantityQcGood;
    }

    public BigDecimal getQuantityQcGood() 
    {
        return quantityQcGood;
    }
    public void setQuantityQcNg(BigDecimal quantityQcNg) 
    {
        this.quantityQcNg = quantityQcNg;
    }

    public BigDecimal getQuantityQcNg() 
    {
        return quantityQcNg;
    }
    public void setQuantityQcRedisp(BigDecimal quantityQcRedisp) 
    {
        this.quantityQcRedisp = quantityQcRedisp;
    }

    public BigDecimal getQuantityQcRedisp() 
    {
        return quantityQcRedisp;
    }
    public void setQuantityQcRework(BigDecimal quantityQcRework) 
    {
        this.quantityQcRework = quantityQcRework;
    }

    public BigDecimal getQuantityQcRework() 
    {
        return quantityQcRework;
    }
    public void setQuantityQcPending(BigDecimal quantityQcPending) 
    {
        this.quantityQcPending = quantityQcPending;
    }

    public BigDecimal getQuantityQcPending() 
    {
        return quantityQcPending;
    }
    public void setQuantityQcAod(BigDecimal quantityQcAod) 
    {
        this.quantityQcAod = quantityQcAod;
    }

    public BigDecimal getQuantityQcAod() 
    {
        return quantityQcAod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("requestId", getRequestId())
            .append("seqNo", getSeqNo())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("materialCode", getMaterialCode())
            .append("materialSpec", getMaterialSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("abroadProductNo", getAbroadProductNo())
            .append("compoundId", getCompoundId())
            .append("categoryId", getCategoryId())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("price", getPrice())
            .append("amount", getAmount())
            .append("materialAvailableType", getMaterialAvailableType())
            .append("sourceLotNo", getSourceLotNo())
            .append("houseId", getHouseId())
            .append("positionId", getPositionId())
            .append("ilId", getIlId())
            .append("sourceId", getSourceId())
            .append("quantityQcExceptional", getQuantityQcExceptional())
            .append("quantityQcGood", getQuantityQcGood())
            .append("quantityQcNg", getQuantityQcNg())
            .append("quantityQcRedisp", getQuantityQcRedisp())
            .append("quantityQcRework", getQuantityQcRework())
            .append("quantityQcPending", getQuantityQcPending())
            .append("quantityQcAod", getQuantityQcAod())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}

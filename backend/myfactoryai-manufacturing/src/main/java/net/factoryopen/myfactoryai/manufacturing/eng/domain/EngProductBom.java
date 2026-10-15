package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品BOM对象 eng_product_bom
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public class EngProductBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 物料编码 */
    @Excel(name = "物料编码")
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

    /** 尺寸 */
    @Excel(name = "尺寸")
    private String materialSize;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 品类 */
    @Excel(name = "品类")
    private Long categoryId;

    /** 材质 */
    @Excel(name = "材质")
    private Long compoundId;

    /** 标准用量 */
    @Excel(name = "标准用量")
    private BigDecimal standardQuantity;

    /** 损耗率% */
    @Excel(name = "损耗率%")
    private BigDecimal lossRate;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private String consumeType;

    /** 必配料 */
    @Excel(name = "必配料")
    private String enableMust;

    /** 可替代 */
    @Excel(name = "可替代")
    private String enableReplace;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 基本类型 */
    @Excel(name = "MRP算料类型")
    private String calculateType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
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

    public String getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(String materialSize) {
        this.materialSize = materialSize;
    }

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(Long compoundId) {
        this.compoundId = compoundId;
    }

    public void setStandardQuantity(BigDecimal standardQuantity)
    {
        this.standardQuantity = standardQuantity;
    }

    public BigDecimal getStandardQuantity() 
    {
        return standardQuantity;
    }
    public void setLossRate(BigDecimal lossRate) 
    {
        this.lossRate = lossRate;
    }

    public BigDecimal getLossRate() 
    {
        return lossRate;
    }
    public void setConsumeType(String consumeType)
    {
        this.consumeType = consumeType;
    }

    public String getConsumeType()
    {
        return consumeType;
    }
    public void setEnableMust(String enableMust) 
    {
        this.enableMust = enableMust;
    }

    public String getEnableMust() 
    {
        return enableMust;
    }
    public void setEnableReplace(String enableReplace) 
    {
        this.enableReplace = enableReplace;
    }

    public String getEnableReplace() 
    {
        return enableReplace;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setCalculateType(String calculateType)
    {
        this.calculateType = calculateType;
    }

    public String getCalculateType()
    {
        return calculateType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productId", getProductId())
            .append("seqNo", getSeqNo())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialSize", getMaterialSize())
            .append("drawNo", getDrawNo())
            .append("categoryId", getCategoryId())
            .append("compoundId", getCompoundId())
            .append("standardQuantity", getStandardQuantity())
            .append("lossRate", getLossRate())
            .append("consumeType", getConsumeType())
            .append("enableMust", getEnableMust())
            .append("enableReplace", getEnableReplace())
            .append("lineId", getLineId())
            .append("calculateType", getCalculateType())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

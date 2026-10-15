package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工单BOM对象 pln_wo_bom
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public class PlnWoBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 工单 */
    @Excel(name = "工单")
    private Long woId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 标准用量 */
    @Excel(name = "标准用量")
    private BigDecimal standardQuantity;

    /** 损耗率% */
    @Excel(name = "损耗率%")
    private BigDecimal lossRate;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private Integer consumeType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWoId(Long woId) 
    {
        this.woId = woId;
    }

    public Long getWoId() 
    {
        return woId;
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
    public void setConsumeType(Integer consumeType) 
    {
        this.consumeType = consumeType;
    }

    public Integer getConsumeType() 
    {
        return consumeType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("woId", getWoId())
            .append("seqNo", getSeqNo())
            .append("materialId", getMaterialId())
            .append("standardQuantity", getStandardQuantity())
            .append("lossRate", getLossRate())
            .append("consumeType", getConsumeType())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

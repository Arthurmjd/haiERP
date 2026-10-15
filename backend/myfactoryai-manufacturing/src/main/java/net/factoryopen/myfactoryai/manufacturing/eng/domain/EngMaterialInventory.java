package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料存货维对象 eng_material_inventory
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 批次管理否 */
    @Excel(name = "批次管理否")
    private String enableLot;

    /** 安全库存量 */
    @Excel(name = "安全库存量")
    private BigDecimal safeStockLevel;

    /** 最高库存量 */
    @Excel(name = "最高库存量")
    private BigDecimal topStockLevel;

    /** 保质期天数 */
    @Excel(name = "保质期天数")
    private BigDecimal expiredDays;

    /** 呆滞预警天数 */
    @Excel(name = "呆滞预警天数")
    private BigDecimal dullDays;

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

    public void setEnableLot(String enableLot) 
    {
        this.enableLot = enableLot;
    }

    public String getEnableLot() 
    {
        return enableLot;
    }
    public void setSafeStockLevel(BigDecimal safeStockLevel) 
    {
        this.safeStockLevel = safeStockLevel;
    }

    public BigDecimal getSafeStockLevel() 
    {
        return safeStockLevel;
    }
    public void setTopStockLevel(BigDecimal topStockLevel) 
    {
        this.topStockLevel = topStockLevel;
    }

    public BigDecimal getTopStockLevel() 
    {
        return topStockLevel;
    }
    public void setExpiredDays(BigDecimal expiredDays) 
    {
        this.expiredDays = expiredDays;
    }

    public BigDecimal getExpiredDays() 
    {
        return expiredDays;
    }
    public void setDullDays(BigDecimal dullDays) 
    {
        this.dullDays = dullDays;
    }

    public BigDecimal getDullDays() 
    {
        return dullDays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("enableLot", getEnableLot())
            .append("safeStockLevel", getSafeStockLevel())
            .append("topStockLevel", getTopStockLevel())
            .append("expiredDays", getExpiredDays())
            .append("dullDays", getDullDays())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

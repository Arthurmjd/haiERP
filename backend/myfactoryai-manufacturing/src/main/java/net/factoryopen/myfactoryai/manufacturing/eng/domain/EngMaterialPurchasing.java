package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料采购维对象 eng_material_purchasing
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialPurchasing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 保质期 */
    @Excel(name = "保质期")
    private Long availableDays;

    /** 采购前置天数 */
    @Excel(name = "采购前置天数")
    private BigDecimal leadtimeBuyday;

    /** 最小采购量 */
    @Excel(name = "最小采购量")
    private BigDecimal quantityMinisize;

    /** 采购经济批量 */
    @Excel(name = "采购经济批量")
    private BigDecimal quantityEconosize;

    /** 上次购价 */
    @Excel(name = "上次购价")
    private BigDecimal lastPrice;

    /** 上次采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上次采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastDate;

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
    public void setAvailableDays(Long availableDays) 
    {
        this.availableDays = availableDays;
    }

    public Long getAvailableDays() 
    {
        return availableDays;
    }
    public void setLeadtimeBuyday(BigDecimal leadtimeBuyday) 
    {
        this.leadtimeBuyday = leadtimeBuyday;
    }

    public BigDecimal getLeadtimeBuyday() 
    {
        return leadtimeBuyday;
    }
    public void setQuantityMinisize(BigDecimal quantityMinisize) 
    {
        this.quantityMinisize = quantityMinisize;
    }

    public BigDecimal getQuantityMinisize() 
    {
        return quantityMinisize;
    }
    public void setQuantityEconosize(BigDecimal quantityEconosize) 
    {
        this.quantityEconosize = quantityEconosize;
    }

    public BigDecimal getQuantityEconosize() 
    {
        return quantityEconosize;
    }
    public void setLastPrice(BigDecimal lastPrice) 
    {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getLastPrice() 
    {
        return lastPrice;
    }
    public void setLastDate(Date lastDate) 
    {
        this.lastDate = lastDate;
    }

    public Date getLastDate() 
    {
        return lastDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("availableDays", getAvailableDays())
            .append("leadtimeBuyday", getLeadtimeBuyday())
            .append("quantityMinisize", getQuantityMinisize())
            .append("quantityEconosize", getQuantityEconosize())
            .append("lastPrice", getLastPrice())
            .append("lastDate", getLastDate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 批次现对象 hom_achieve_lotrend
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomAchieveLotrend extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 日期
     */
    @Excel(name = "日期")
    private String day;

    /**
     * 在制批数
     */
    @Excel(name = "在制批数")
    private BigDecimal quantityWip;

    /**
     * 特急批数
     */
    @Excel(name = "特急批数")
    private BigDecimal quantitySuperurgent;

    /**
     * 紧急批数
     */
    @Excel(name = "紧急批数")
    private BigDecimal quantityUrgent;

    /**
     * 冻结批数
     */
    @Excel(name = "冻结批数")
    private BigDecimal quantityFrozen;

    /**
     * 取消批数
     */
    @Excel(name = "取消批数")
    private BigDecimal quantityKilled;

    /**
     * 结案批数
     */
    @Excel(name = "结案批数")
    private BigDecimal quantityCompleted;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setQuantityWip(BigDecimal quantityWip) {
        this.quantityWip = quantityWip;
    }

    public BigDecimal getQuantityWip() {
        return quantityWip;
    }

    public void setQuantitySuperurgent(BigDecimal quantitySuperurgent) {
        this.quantitySuperurgent = quantitySuperurgent;
    }

    public BigDecimal getQuantitySuperurgent() {
        return quantitySuperurgent;
    }

    public void setQuantityUrgent(BigDecimal quantityUrgent) {
        this.quantityUrgent = quantityUrgent;
    }

    public BigDecimal getQuantityUrgent() {
        return quantityUrgent;
    }

    public void setQuantityFrozen(BigDecimal quantityFrozen) {
        this.quantityFrozen = quantityFrozen;
    }

    public BigDecimal getQuantityFrozen() {
        return quantityFrozen;
    }

    public void setQuantityKilled(BigDecimal quantityKilled) {
        this.quantityKilled = quantityKilled;
    }

    public BigDecimal getQuantityKilled() {
        return quantityKilled;
    }

    public void setQuantityCompleted(BigDecimal quantityCompleted) {
        this.quantityCompleted = quantityCompleted;
    }

    public BigDecimal getQuantityCompleted() {
        return quantityCompleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("day", getDay())
                .append("quantityWip", getQuantityWip())
                .append("quantitySuperurgent", getQuantitySuperurgent())
                .append("quantityUrgent", getQuantityUrgent())
                .append("quantityFrozen", getQuantityFrozen())
                .append("quantityKilled", getQuantityKilled())
                .append("quantityCompleted", getQuantityCompleted())
                .toString();
    }
}

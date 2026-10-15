package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 质异结果对象 hom_quality_resultrend
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomQualityResultrend extends BaseEntity {
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
     * 报废数量
     */
    @Excel(name = "报废数量")
    private BigDecimal quantityScrap;

    /**
     * 重派数量
     */
    @Excel(name = "重派数量")
    private BigDecimal quantityRedisp;

    /**
     * 返工数量
     */
    @Excel(name = "返工数量")
    private BigDecimal quantityRework;

    /**
     * 待判数量
     */
    @Excel(name = "待判数量")
    private BigDecimal quantityPending;

    /**
     * 特采次数
     */
    @Excel(name = "特采次数")
    private BigDecimal quantityAod;

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

    public void setQuantityScrap(BigDecimal quantityScrap) {
        this.quantityScrap = quantityScrap;
    }

    public BigDecimal getQuantityScrap() {
        return quantityScrap;
    }

    public void setQuantityRedisp(BigDecimal quantityRedisp) {
        this.quantityRedisp = quantityRedisp;
    }

    public BigDecimal getQuantityRedisp() {
        return quantityRedisp;
    }

    public void setQuantityRework(BigDecimal quantityRework) {
        this.quantityRework = quantityRework;
    }

    public BigDecimal getQuantityRework() {
        return quantityRework;
    }

    public void setQuantityPending(BigDecimal quantityPending) {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityPending() {
        return quantityPending;
    }

    public void setQuantityAod(BigDecimal quantityAod) {
        this.quantityAod = quantityAod;
    }

    public BigDecimal getQuantityAod() {
        return quantityAod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("day", getDay())
                .append("quantityScrap", getQuantityScrap())
                .append("quantityRedisp", getQuantityRedisp())
                .append("quantityRework", getQuantityRework())
                .append("quantityPending", getQuantityPending())
                .append("quantityAod", getQuantityAod())
                .toString();
    }
}

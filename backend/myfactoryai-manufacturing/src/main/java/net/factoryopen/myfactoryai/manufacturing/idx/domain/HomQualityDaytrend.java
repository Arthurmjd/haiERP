package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 质异日势对象 hom_quality_daytrend
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomQualityDaytrend extends BaseEntity {
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
     * 产线ID
     */
    @Excel(name = "产线ID")
    private Long lineId;

    /**
     * 良率
     */
    @Excel(name = "良率")
    private BigDecimal yield;

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

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public BigDecimal getYield() {
        return yield;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("day", getDay())
                .append("lineId", getLineId())
                .append("yield", getYield())
                .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 质异原因对象 hom_quality_causetrend
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomQualityCausetrend extends BaseEntity {
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
     * 质异原因ID
     */
    @Excel(name = "质异原因ID")
    private Long causeId;

    /**
     * 次数
     */
    @Excel(name = "次数")
    private Long count;

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

    public void setCauseId(Long causeId) {
        this.causeId = causeId;
    }

    public Long getCauseId() {
        return causeId;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("day", getDay())
                .append("causeId", getCauseId())
                .append("count", getCount())
                .toString();
    }
}

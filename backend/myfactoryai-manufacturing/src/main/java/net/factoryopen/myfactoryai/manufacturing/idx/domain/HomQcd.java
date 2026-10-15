package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * QCD对象 hom_qcd
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomQcd extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 良率
     */
    @Excel(name = "良率")
    private BigDecimal yield;

    /**
     * 直通率
     */
    @Excel(name = "直通率")
    private BigDecimal fpy;

    /**
     * 存货金额
     */
    @Excel(name = "存货金额")
    private BigDecimal inventoryMoney;

    /**
     * 在制批数
     */
    @Excel(name = "在制批数")
    private Long lotCount;

    /**
     * 交期完成率
     */
    @Excel(name = "交期完成率")
    private BigDecimal completionRate;

    /**
     * 生产周期
     */
    @Excel(name = "生产周期")
    private BigDecimal cycleTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setFpy(BigDecimal fpy) {
        this.fpy = fpy;
    }

    public BigDecimal getFpy() {
        return fpy;
    }

    public void setInventoryMoney(BigDecimal inventoryMoney) {
        this.inventoryMoney = inventoryMoney;
    }

    public BigDecimal getInventoryMoney() {
        return inventoryMoney;
    }

    public void setLotCount(Long lotCount) {
        this.lotCount = lotCount;
    }

    public Long getLotCount() {
        return lotCount;
    }

    public void setCompletionRate(BigDecimal completionRate) {
        this.completionRate = completionRate;
    }

    public BigDecimal getCompletionRate() {
        return completionRate;
    }

    public void setCycleTime(BigDecimal cycleTime) {
        this.cycleTime = cycleTime;
    }

    public BigDecimal getCycleTime() {
        return cycleTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("yield", getYield())
                .append("fpy", getFpy())
                .append("inventoryMoney", getInventoryMoney())
                .append("lotCount", getLotCount())
                .append("completionRate", getCompletionRate())
                .append("cycleTime", getCycleTime())
                .toString();
    }
}

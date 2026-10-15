package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 日现对象 hom_achieve_daytrend
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomAchieveDaytrend extends BaseEntity {
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
     * 订单量
     */
    @Excel(name = "订单量")
    private BigDecimal quantityOrder;

    /**
     * 计划量
     */
    @Excel(name = "计划量")
    private BigDecimal quantityPlan;

    /**
     * 生产量
     */
    @Excel(name = "生产量")
    private BigDecimal quantityWork;

    /**
     * 质检量
     */
    @Excel(name = "质检量")
    private BigDecimal quantityInspection;

    /**
     * 入库量
     */
    @Excel(name = "入库量")
    private BigDecimal quantityStock;

    /**
     * 发货量
     */
    @Excel(name = "发货量")
    private BigDecimal quantityDelivery;

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

    public void setQuantityOrder(BigDecimal quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public BigDecimal getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityPlan(BigDecimal quantityPlan) {
        this.quantityPlan = quantityPlan;
    }

    public BigDecimal getQuantityPlan() {
        return quantityPlan;
    }

    public void setQuantityWork(BigDecimal quantityWork) {
        this.quantityWork = quantityWork;
    }

    public BigDecimal getQuantityWork() {
        return quantityWork;
    }

    public void setQuantityInspection(BigDecimal quantityInspection) {
        this.quantityInspection = quantityInspection;
    }

    public BigDecimal getQuantityInspection() {
        return quantityInspection;
    }

    public void setQuantityStock(BigDecimal quantityStock) {
        this.quantityStock = quantityStock;
    }

    public BigDecimal getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityDelivery(BigDecimal quantityDelivery) {
        this.quantityDelivery = quantityDelivery;
    }

    public BigDecimal getQuantityDelivery() {
        return quantityDelivery;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("day", getDay())
                .append("quantityOrder", getQuantityOrder())
                .append("quantityPlan", getQuantityPlan())
                .append("quantityWork", getQuantityWork())
                .append("quantityInspection", getQuantityInspection())
                .append("quantityStock", getQuantityStock())
                .append("quantityDelivery", getQuantityDelivery())
                .toString();
    }
}

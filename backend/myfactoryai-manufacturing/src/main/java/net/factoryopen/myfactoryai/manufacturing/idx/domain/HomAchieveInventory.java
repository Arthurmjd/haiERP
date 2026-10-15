package net.factoryopen.myfactoryai.manufacturing.idx.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 存货现对象 hom_achieve_inventory
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public class HomAchieveInventory extends BaseEntity {
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
     * 原料金额
     */
    @Excel(name = "原料金额")
    private BigDecimal moneyRaw;

    /**
     * 物料金额
     */
    @Excel(name = "物料金额")
    private BigDecimal moneyParts;

    /**
     * 产品金额
     */
    @Excel(name = "产品金额")
    private BigDecimal moneyProduct;

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

    public void setMoneyRaw(BigDecimal moneyRaw) {
        this.moneyRaw = moneyRaw;
    }

    public BigDecimal getMoneyRaw() {
        return moneyRaw;
    }

    public void setMoneyParts(BigDecimal moneyParts) {
        this.moneyParts = moneyParts;
    }

    public BigDecimal getMoneyParts() {
        return moneyParts;
    }

    public void setMoneyProduct(BigDecimal moneyProduct) {
        this.moneyProduct = moneyProduct;
    }

    public BigDecimal getMoneyProduct() {
        return moneyProduct;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("day", getDay())
                .append("moneyRaw", getMoneyRaw())
                .append("moneyParts", getMoneyParts())
                .append("moneyProduct", getMoneyProduct())
                .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.ehs.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电耗登记对象 ehs_power_consuming
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EhsPowerConsuming extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long foodieId;

    /** 日期 */
    @Excel(name = "日期")
    private String day;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFoodieId(Long foodieId) 
    {
        this.foodieId = foodieId;
    }

    public Long getFoodieId() 
    {
        return foodieId;
    }
    public void setDay(String day) 
    {
        this.day = day;
    }

    public String getDay() 
    {
        return day;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("foodieId", getFoodieId())
            .append("day", getDay())
            .append("quantity", getQuantity())
            .append("unitId", getUnitId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.ehs.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 污染物排放登记对象 ehs_pollution_record
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EhsPollutionRecord extends BaseEntity
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

    /** 指标1 */
    @Excel(name = "指标1")
    private Long index1Id;

    /** 指标1值 */
    @Excel(name = "指标1值")
    private BigDecimal index1Value;

    /** 指标2 */
    @Excel(name = "指标2")
    private Long index2Id;

    /** 指标2值 */
    @Excel(name = "指标2值")
    private BigDecimal index2Value;

    /** 指标3 */
    @Excel(name = "指标3")
    private Long index3Id;

    /** 指标3值 */
    @Excel(name = "指标3值")
    private BigDecimal index3Value;

    /** 指标4 */
    @Excel(name = "指标4")
    private Long index4Id;

    /** 指标4值 */
    @Excel(name = "指标4值")
    private BigDecimal index4Value;

    /** 指标5 */
    @Excel(name = "指标5")
    private Long index5Id;

    /** 指标5值 */
    @Excel(name = "指标5值")
    private BigDecimal index5Value;

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
    public void setIndex1Id(Long index1Id) 
    {
        this.index1Id = index1Id;
    }

    public Long getIndex1Id() 
    {
        return index1Id;
    }
    public void setIndex1Value(BigDecimal index1Value) 
    {
        this.index1Value = index1Value;
    }

    public BigDecimal getIndex1Value() 
    {
        return index1Value;
    }
    public void setIndex2Id(Long index2Id) 
    {
        this.index2Id = index2Id;
    }

    public Long getIndex2Id() 
    {
        return index2Id;
    }
    public void setIndex2Value(BigDecimal index2Value) 
    {
        this.index2Value = index2Value;
    }

    public BigDecimal getIndex2Value() 
    {
        return index2Value;
    }
    public void setIndex3Id(Long index3Id) 
    {
        this.index3Id = index3Id;
    }

    public Long getIndex3Id() 
    {
        return index3Id;
    }
    public void setIndex3Value(BigDecimal index3Value) 
    {
        this.index3Value = index3Value;
    }

    public BigDecimal getIndex3Value() 
    {
        return index3Value;
    }
    public void setIndex4Id(Long index4Id) 
    {
        this.index4Id = index4Id;
    }

    public Long getIndex4Id() 
    {
        return index4Id;
    }
    public void setIndex4Value(BigDecimal index4Value) 
    {
        this.index4Value = index4Value;
    }

    public BigDecimal getIndex4Value() 
    {
        return index4Value;
    }
    public void setIndex5Id(Long index5Id) 
    {
        this.index5Id = index5Id;
    }

    public Long getIndex5Id() 
    {
        return index5Id;
    }
    public void setIndex5Value(BigDecimal index5Value) 
    {
        this.index5Value = index5Value;
    }

    public BigDecimal getIndex5Value() 
    {
        return index5Value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("foodieId", getFoodieId())
            .append("day", getDay())
            .append("quantity", getQuantity())
            .append("index1Id", getIndex1Id())
            .append("index1Value", getIndex1Value())
            .append("index2Id", getIndex2Id())
            .append("index2Value", getIndex2Value())
            .append("index3Id", getIndex3Id())
            .append("index3Value", getIndex3Value())
            .append("index4Id", getIndex4Id())
            .append("index4Value", getIndex4Value())
            .append("index5Id", getIndex5Id())
            .append("index5Value", getIndex5Value())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

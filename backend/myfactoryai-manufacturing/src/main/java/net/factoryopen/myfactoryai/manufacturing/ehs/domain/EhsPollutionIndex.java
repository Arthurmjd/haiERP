package net.factoryopen.myfactoryai.manufacturing.ehs.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 污染排放指标对象 ehs_pollution_index
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EhsPollutionIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 绿线 */
    @Excel(name = "绿线")
    private BigDecimal limitGreen;

    /** 黄线 */
    @Excel(name = "黄线")
    private BigDecimal limitYellow;

    /** 红线 */
    @Excel(name = "红线")
    private BigDecimal limitRed;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setLimitGreen(BigDecimal limitGreen) 
    {
        this.limitGreen = limitGreen;
    }

    public BigDecimal getLimitGreen() 
    {
        return limitGreen;
    }
    public void setLimitYellow(BigDecimal limitYellow) 
    {
        this.limitYellow = limitYellow;
    }

    public BigDecimal getLimitYellow() 
    {
        return limitYellow;
    }
    public void setLimitRed(BigDecimal limitRed) 
    {
        this.limitRed = limitRed;
    }

    public BigDecimal getLimitRed() 
    {
        return limitRed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("status", getStatus())
            .append("unitId", getUnitId())
            .append("limitGreen", getLimitGreen())
            .append("limitYellow", getLimitYellow())
            .append("limitRed", getLimitRed())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

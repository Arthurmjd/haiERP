package net.factoryopen.myfactoryai.manufacturing.bas.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 开票方式对象 bas_invoice_pattern
 * 
 * @author admin
 * @date 2024-03-08
 */
public class BasInvoicePattern extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 方式名称 */
    @Excel(name = "方式名称")
    private String patternName;

    /** 税率 */
    @Excel(name = "税率")
    private BigDecimal patternRate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPatternName(String patternName) 
    {
        this.patternName = patternName;
    }

    public String getPatternName() 
    {
        return patternName;
    }
    public void setPatternRate(BigDecimal patternRate) 
    {
        this.patternRate = patternRate;
    }

    public BigDecimal getPatternRate() 
    {
        return patternRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patternName", getPatternName())
            .append("patternRate", getPatternRate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

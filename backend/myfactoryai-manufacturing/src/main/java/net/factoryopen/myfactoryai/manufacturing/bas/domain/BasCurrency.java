package net.factoryopen.myfactoryai.manufacturing.bas.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 货币对象 bas_currency
 * 
 * @author admin
 * @date 2024-03-08
 */
public class BasCurrency extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 货币名称 */
    @Excel(name = "货币名称")
    private String currencyName;

    /** 货币符号 */
    @Excel(name = "货币符号")
    private String currencySymbol;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCurrencyName(String currencyName) 
    {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() 
    {
        return currencyName;
    }
    public void setCurrencySymbol(String currencySymbol) 
    {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbol() 
    {
        return currencySymbol;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("currencyName", getCurrencyName())
            .append("currencySymbol", getCurrencySymbol())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

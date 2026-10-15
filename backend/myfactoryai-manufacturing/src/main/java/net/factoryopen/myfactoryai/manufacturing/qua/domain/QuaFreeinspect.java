package net.factoryopen.myfactoryai.manufacturing.qua.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 免检策略管理对象 qua_freeinspect
 * 
 * @author admin
 * @date 2024-11-12
 */
public class QuaFreeinspect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 免检水平 */
    @Excel(name = "免检水平")
    private Integer freeLevel;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setFreeLevel(Integer freeLevel) 
    {
        this.freeLevel = freeLevel;
    }

    public Integer getFreeLevel() 
    {
        return freeLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplierId", getSupplierId())
            .append("freeLevel", getFreeLevel())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

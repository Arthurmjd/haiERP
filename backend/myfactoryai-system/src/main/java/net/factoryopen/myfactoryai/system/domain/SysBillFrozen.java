package net.factoryopen.myfactoryai.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单对象冻结对象 sys_bill_frozen
 * 
 * @author admin
 * @date 2024-06-18
 */
public class SysBillFrozen extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 类名 */
    @Excel(name = "类名")
    private String className;

    /** 对象ID */
    @Excel(name = "对象ID")
    private Long objectId;

    /** 冻结时状态 */
    @Excel(name = "冻结时状态")
    private Long frozenStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClassName(String className) 
    {
        this.className = className;
    }

    public String getClassName() 
    {
        return className;
    }
    public void setObjectId(Long objectId) 
    {
        this.objectId = objectId;
    }

    public Long getObjectId() 
    {
        return objectId;
    }
    public void setFrozenStatus(Long frozenStatus) 
    {
        this.frozenStatus = frozenStatus;
    }

    public Long getFrozenStatus() 
    {
        return frozenStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("className", getClassName())
            .append("objectId", getObjectId())
            .append("frozenStatus", getFrozenStatus())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工人管理对象 res_labor
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public class ResLabor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 班组 */
    @Excel(name = "班组")
    private Long workgroupId;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWorkgroupId(Long workgroupId) 
    {
        this.workgroupId = workgroupId;
    }

    public Long getWorkgroupId() 
    {
        return workgroupId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workgroupId", getWorkgroupId())
            .append("userId", getUserId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

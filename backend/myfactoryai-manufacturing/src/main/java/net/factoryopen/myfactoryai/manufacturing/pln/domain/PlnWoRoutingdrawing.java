package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工单工序图对象 pln_wo_routingdrawing
 * 
 * @author admin
 * @date 2024-07-11
 */
public class PlnWoRoutingdrawing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 产品路线ID */
    @Excel(name = "产品路线ID")
    private Long routingId;

    /** 图纸描述 */
    @Excel(name = "图纸描述")
    private String description;

    /** 图纸链接 */
    @Excel(name = "图纸链接")
    private String drawingUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRoutingId(Long routingId) 
    {
        this.routingId = routingId;
    }

    public Long getRoutingId() 
    {
        return routingId;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setDrawingUrl(String drawingUrl) 
    {
        this.drawingUrl = drawingUrl;
    }

    public String getDrawingUrl() 
    {
        return drawingUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("routingId", getRoutingId())
            .append("description", getDescription())
            .append("drawingUrl", getDrawingUrl())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

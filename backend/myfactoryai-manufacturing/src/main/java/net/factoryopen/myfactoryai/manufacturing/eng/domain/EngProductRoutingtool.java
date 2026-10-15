package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品工具对象 eng_product_routingtool
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public class EngProductRoutingtool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 路线 */
    @Excel(name = "路线")
    private Long routingId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer itemSeq;

    /** 工具类型 */
    @Excel(name = "工具类型")
    private Long toolId;

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
    public void setItemSeq(Integer itemSeq) 
    {
        this.itemSeq = itemSeq;
    }

    public Integer getItemSeq() 
    {
        return itemSeq;
    }
    public void setToolId(Long toolId) 
    {
        this.toolId = toolId;
    }

    public Long getToolId() 
    {
        return toolId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("routingId", getRoutingId())
            .append("itemSeq", getItemSeq())
            .append("toolId", getToolId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

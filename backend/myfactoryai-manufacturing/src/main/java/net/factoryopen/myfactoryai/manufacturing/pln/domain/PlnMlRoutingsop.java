package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批次SOP对象 pln_ml_routingsop
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public class PlnMlRoutingsop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 工序 */
    @Excel(name = "工序")
    private Long routingId;

    /** 步序号 */
    @Excel(name = "步序号")
    private Integer stepSeq;

    /** 步骤标题 */
    @Excel(name = "步骤标题")
    private String stepTitle;

    /** 步骤描述 */
    @Excel(name = "步骤描述")
    private String description;

    /** SOP地址 */
    @Excel(name = "SOP地址")
    private String sopUrl;

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
    public void setStepSeq(Integer stepSeq) 
    {
        this.stepSeq = stepSeq;
    }

    public Integer getStepSeq() 
    {
        return stepSeq;
    }
    public void setStepTitle(String stepTitle) 
    {
        this.stepTitle = stepTitle;
    }

    public String getStepTitle() 
    {
        return stepTitle;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setSopUrl(String sopUrl) 
    {
        this.sopUrl = sopUrl;
    }

    public String getSopUrl() 
    {
        return sopUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("routingId", getRoutingId())
            .append("stepSeq", getStepSeq())
            .append("stepTitle", getStepTitle())
            .append("description", getDescription())
            .append("sopUrl", getSopUrl())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

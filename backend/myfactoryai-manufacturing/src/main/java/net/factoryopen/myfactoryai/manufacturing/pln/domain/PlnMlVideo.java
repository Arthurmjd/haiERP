package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批次作业视频对象 pln_ml_video
 * 
 * @author admin
 * @date 2024-07-13
 */
public class PlnMlVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 批次ID */
    @Excel(name = "批次ID")
    private Long mlId;

    /** 步序号 */
    @Excel(name = "步序号")
    private Integer stepSeq;

    /** 步骤标题 */
    @Excel(name = "步骤标题")
    private String stepTitle;

    /** 步骤描述 */
    @Excel(name = "步骤描述")
    private String description;

    /** 视频地址 */
    @Excel(name = "视频地址")
    private String videoUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
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
    public void setVideoUrl(String videoUrl) 
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() 
    {
        return videoUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mlId", getMlId())
            .append("stepSeq", getStepSeq())
            .append("stepTitle", getStepTitle())
            .append("description", getDescription())
            .append("videoUrl", getVideoUrl())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

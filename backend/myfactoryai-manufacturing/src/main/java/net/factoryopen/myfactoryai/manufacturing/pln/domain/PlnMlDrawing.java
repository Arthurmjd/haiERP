package net.factoryopen.myfactoryai.manufacturing.pln.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批次图纸对象 pln_ml_drawing
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public class PlnMlDrawing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 制造批 */
    @Excel(name = "制造批")
    private Long mlId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

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
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
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
            .append("mlId", getMlId())
            .append("seqNo", getSeqNo())
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

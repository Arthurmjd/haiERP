package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产线管理对象 res_line
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public class ResLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 产线名称 */
    @Excel(name = "产线名称")
    private String lineName;

    /** 产线类型 */
    @Excel(name = "产线类型")
    private Integer lineType;

    /** 外协厂商 */
    @Excel(name = "外协厂商")
    private Long supplierId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLineName(String lineName) 
    {
        this.lineName = lineName;
    }

    public String getLineName() 
    {
        return lineName;
    }
    public void setLineType(Integer lineType) 
    {
        this.lineType = lineType;
    }

    public Integer getLineType() 
    {
        return lineType;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("lineName", getLineName())
            .append("lineType", getLineType())
            .append("supplierId", getSupplierId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

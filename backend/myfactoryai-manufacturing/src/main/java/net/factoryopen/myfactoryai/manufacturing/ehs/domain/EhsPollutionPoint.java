package net.factoryopen.myfactoryai.manufacturing.ehs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 污染物排放点对象 ehs_pollution_point
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EhsPollutionPoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 设备描述 */
    @Excel(name = "设备描述")
    private String pointDesc;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 排放分类 */
    @Excel(name = "排放分类")
    private Long categoryId;

    /** 排放指标1 */
    @Excel(name = "排放指标1")
    private Long index1Id;

    /** 排放指标2 */
    @Excel(name = "排放指标2")
    private Long index2Id;

    /** 排放指标3 */
    @Excel(name = "排放指标3")
    private Long index3Id;

    /** 排放指标4 */
    @Excel(name = "排放指标4")
    private Long index4Id;

    /** 排放指标5 */
    @Excel(name = "排放指标5")
    private Long index5Id;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPointDesc(String pointDesc) 
    {
        this.pointDesc = pointDesc;
    }

    public String getPointDesc() 
    {
        return pointDesc;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setIndex1Id(Long index1Id) 
    {
        this.index1Id = index1Id;
    }

    public Long getIndex1Id() 
    {
        return index1Id;
    }
    public void setIndex2Id(Long index2Id) 
    {
        this.index2Id = index2Id;
    }

    public Long getIndex2Id() 
    {
        return index2Id;
    }
    public void setIndex3Id(Long index3Id) 
    {
        this.index3Id = index3Id;
    }

    public Long getIndex3Id() 
    {
        return index3Id;
    }
    public void setIndex4Id(Long index4Id) 
    {
        this.index4Id = index4Id;
    }

    public Long getIndex4Id() 
    {
        return index4Id;
    }
    public void setIndex5Id(Long index5Id) 
    {
        this.index5Id = index5Id;
    }

    public Long getIndex5Id() 
    {
        return index5Id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("pointDesc", getPointDesc())
            .append("status", getStatus())
            .append("location", getLocation())
            .append("categoryId", getCategoryId())
            .append("index1Id", getIndex1Id())
            .append("index2Id", getIndex2Id())
            .append("index3Id", getIndex3Id())
            .append("index4Id", getIndex4Id())
            .append("index5Id", getIndex5Id())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.ehs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电耗设备对象 ehs_power_foodie
 * 
 * @author admin
 * @date 2024-07-04
 */
public class EhsPowerFoodie extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 设备描述 */
    @Excel(name = "设备描述")
    private String foodieDesc;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 设备分类 */
    @Excel(name = "设备分类")
    private Long categoryId;

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
    public void setFoodieDesc(String foodieDesc) 
    {
        this.foodieDesc = foodieDesc;
    }

    public String getFoodieDesc() 
    {
        return foodieDesc;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("foodieDesc", getFoodieDesc())
            .append("status", getStatus())
            .append("location", getLocation())
            .append("categoryId", getCategoryId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库管理对象 res_ware_house
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
public class ResWareHouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 仓库码 */
    @Excel(name = "仓库码")
    private String houseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String houseName;

    /** 物理地点 */
    @Excel(name = "物理地点")
    private String houseLocation;

    /** 仓库类型 */
    @Excel(name = "仓库类型")
    private Integer houseType;

    /** 在用否 */
    @Excel(name = "在用否")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHouseCode(String houseCode) 
    {
        this.houseCode = houseCode;
    }

    public String getHouseCode() 
    {
        return houseCode;
    }
    public void setHouseName(String houseName) 
    {
        this.houseName = houseName;
    }

    public String getHouseName() 
    {
        return houseName;
    }
    public void setHouseLocation(String houseLocation) 
    {
        this.houseLocation = houseLocation;
    }

    public String getHouseLocation() 
    {
        return houseLocation;
    }
    public void setHouseType(Integer houseType)
    {
        this.houseType = houseType;
    }

    public Integer getHouseType()
    {
        return houseType;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("houseCode", getHouseCode())
            .append("houseName", getHouseName())
            .append("houseLocation", getHouseLocation())
            .append("houseType", getHouseType())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

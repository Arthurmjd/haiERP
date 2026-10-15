package net.factoryopen.myfactoryai.manufacturing.res.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 储位对象 res_ware_position
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
public class ResWarePosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String positionCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String positionName;

    /** 物理地点 */
    @Excel(name = "物理地点")
    private String positionLocation;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long houseId;

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
    public void setPositionCode(String positionCode) 
    {
        this.positionCode = positionCode;
    }

    public String getPositionCode() 
    {
        return positionCode;
    }
    public void setPositionName(String positionName) 
    {
        this.positionName = positionName;
    }

    public String getPositionName() 
    {
        return positionName;
    }
    public void setPositionLocation(String positionLocation) 
    {
        this.positionLocation = positionLocation;
    }

    public String getPositionLocation() 
    {
        return positionLocation;
    }
    public void setHouseId(Long houseId) 
    {
        this.houseId = houseId;
    }

    public Long getHouseId() 
    {
        return houseId;
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
            .append("positionCode", getPositionCode())
            .append("positionName", getPositionName())
            .append("positionLocation", getPositionLocation())
            .append("houseId", getHouseId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

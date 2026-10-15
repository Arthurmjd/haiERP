package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 请购审核对象 sup_pr
 * 
 * @author admin
 * @date 2024-09-16
 */
public class SupPurchaseReqAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 采购申请号 */
    @Excel(name = "采购申请号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 采购类型 */
    @Excel(name = "采购类型")
    private Integer purchasingType;

    /** 请购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requestDay;

    /** 请购员 */
    @Excel(name = "请购员")
    private Long requestorId;

    /** 请购部门 */
    @Excel(name = "请购部门")
    private Long deptId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBillNo(String billNo) 
    {
        this.billNo = billNo;
    }

    public String getBillNo() 
    {
        return billNo;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setPurchasingType(Integer purchasingType) 
    {
        this.purchasingType = purchasingType;
    }

    public Integer getPurchasingType() 
    {
        return purchasingType;
    }
    public void setRequestDay(Date requestDay) 
    {
        this.requestDay = requestDay;
    }

    public Date getRequestDay() 
    {
        return requestDay;
    }
    public void setRequestorId(Long requestorId) 
    {
        this.requestorId = requestorId;
    }

    public Long getRequestorId() 
    {
        return requestorId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("purchasingType", getPurchasingType())
            .append("requestDay", getRequestDay())
            .append("requestorId", getRequestorId())
            .append("deptId", getDeptId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

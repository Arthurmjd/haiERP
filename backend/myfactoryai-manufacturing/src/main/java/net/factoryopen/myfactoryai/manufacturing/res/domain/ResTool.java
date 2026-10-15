package net.factoryopen.myfactoryai.manufacturing.res.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工具管理对象 res_tool
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public class ResTool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 工具编号 */
    @Excel(name = "工具编号")
    private String toolCode;

    /** 工具描述 */
    @Excel(name = "工具描述")
    private String toolDesc;

    /** 工具类型 */
    @Excel(name = "工具类型")
    private String toolType;

    /** 产线 */
    @Excel(name = "产线")
    private Long lineId;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buyDay;

    /** 在制否 */
    @Excel(name = "在制否")
    private String isBusy;

    /** 始制时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "始制时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ioTime;

    /** 出工单号 */
    @Excel(name = "出工单号")
    private Long wsId;

    /** 批次号 */
    @Excel(name = "批次号")
    private String wsNo;

    /** 制造批ID */
    @Excel(name = "制造批ID")
    private Long mlId;

    /** 制造批号 */
    @Excel(name = "制造批号")
    private String mlNo;

    /** 工序号 */
    @Excel(name = "工序号")
    private Integer processSeq;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setToolCode(String toolCode) 
    {
        this.toolCode = toolCode;
    }

    public String getToolCode() 
    {
        return toolCode;
    }
    public void setToolDesc(String toolDesc) 
    {
        this.toolDesc = toolDesc;
    }

    public String getToolDesc() 
    {
        return toolDesc;
    }
    public void setToolType(String toolType) 
    {
        this.toolType = toolType;
    }

    public String getToolType() 
    {
        return toolType;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setBuyDay(Date buyDay) 
    {
        this.buyDay = buyDay;
    }

    public Date getBuyDay() 
    {
        return buyDay;
    }
    public void setIsBusy(String isBusy) 
    {
        this.isBusy = isBusy;
    }

    public String getIsBusy() 
    {
        return isBusy;
    }
    public void setIoTime(Date ioTime) 
    {
        this.ioTime = ioTime;
    }

    public Date getIoTime() 
    {
        return ioTime;
    }
    public void setWsId(Long wsId)
    {
        this.wsId = wsId;
    }

    public Long getWsId()
    {
        return wsId;
    }
    public void setWsNo(String wsNo) 
    {
        this.wsNo = wsNo;
    }

    public String getWsNo() 
    {
        return wsNo;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setMlNo(String mlNo) 
    {
        this.mlNo = mlNo;
    }

    public String getMlNo() 
    {
        return mlNo;
    }
    public void setProcessSeq(Integer processSeq) 
    {
        this.processSeq = processSeq;
    }

    public Integer getProcessSeq() 
    {
        return processSeq;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("toolCode", getToolCode())
            .append("toolDesc", getToolDesc())
            .append("toolType", getToolType())
            .append("lineId", getLineId())
            .append("buyDay", getBuyDay())
            .append("isBusy", getIsBusy())
            .append("ioTime", getIoTime())
            .append("wsId", getWsId())
            .append("wsNo", getWsNo())
            .append("mlId", getMlId())
            .append("mlNo", getMlNo())
            .append("processSeq", getProcessSeq())
            .append("processId", getProcessId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

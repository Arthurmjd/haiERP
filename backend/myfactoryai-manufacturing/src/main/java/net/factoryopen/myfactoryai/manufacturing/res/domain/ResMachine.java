package net.factoryopen.myfactoryai.manufacturing.res.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机台管理对象 res_machine
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public class ResMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 机台编号 */
    @Excel(name = "机台编号")
    private String machineCode;

    /** 机台名称 */
    @Excel(name = "机台名称")
    private String machineName;

    /** 机型ID */
    @Excel(name = "机型ID")
    private Long typeId;

    /** 产线 */
    @Excel(name = "产线")
    private Long lineId;

    /** 数采否 */
    @Excel(name = "数采否")
    private String enableIiot;

    /** IP */
    @Excel(name = "IP")
    private String ip;

    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买日期", width = 30, dateFormat = "yyyy-MM-dd")
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
    public void setMachineCode(String machineCode) 
    {
        this.machineCode = machineCode;
    }

    public String getMachineCode() 
    {
        return machineCode;
    }
    public void setMachineName(String machineName) 
    {
        this.machineName = machineName;
    }

    public String getMachineName() 
    {
        return machineName;
    }
    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setEnableIiot(String enableIiot) 
    {
        this.enableIiot = enableIiot;
    }

    public String getEnableIiot() 
    {
        return enableIiot;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
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
            .append("machineCode", getMachineCode())
            .append("machineName", getMachineName())
            .append("typeId", getTypeId())
            .append("lineId", getLineId())
            .append("enableIiot", getEnableIiot())
            .append("ip", getIp())
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

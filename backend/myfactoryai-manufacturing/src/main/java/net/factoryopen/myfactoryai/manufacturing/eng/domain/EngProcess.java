package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基本工序管理对象 eng_process
 * 
 * @author jitfactory-generator
 * @date 2026-02-22
 */
public class EngProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 制程段 */
    @Excel(name = "制程段")
    private Long categoryId;

    /** ID */
    private Long id;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 序号 */
    @Excel(name = "序号")
    private Integer processSeq;

    /** 工序类型 */
    @Excel(name = "工序类型")
    private String processType;

    /** 标准固定秒数 */
    @Excel(name = "标准固定秒数")
    private BigDecimal secondsFixed;

    /** 标准可变秒数 */
    @Excel(name = "标准可变秒数")
    private BigDecimal secondsVariable;

    /** 备料秒数 */
    @Excel(name = "备料秒数")
    private BigDecimal secondsRm;

    /** 架机秒数 */
    @Excel(name = "架机秒数")
    private BigDecimal secondsSetup;

    /** 堆垛秒数 */
    @Excel(name = "堆垛秒数")
    private BigDecimal secondsStacking;

    /** 移转秒数 */
    @Excel(name = "移转秒数")
    private BigDecimal secondsTransfer;

    /** 缓冲秒数 */
    @Excel(name = "缓冲秒数")
    private BigDecimal secondsBuffer;

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessCode() 
    {
        return processCode;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setProcessSeq(Integer processSeq) 
    {
        this.processSeq = processSeq;
    }

    public Integer getProcessSeq() 
    {
        return processSeq;
    }
    public void setProcessType(String processType) 
    {
        this.processType = processType;
    }

    public String getProcessType() 
    {
        return processType;
    }
    public void setSecondsFixed(BigDecimal secondsFixed) 
    {
        this.secondsFixed = secondsFixed;
    }

    public BigDecimal getSecondsFixed() 
    {
        return secondsFixed;
    }
    public void setSecondsVariable(BigDecimal secondsVariable) 
    {
        this.secondsVariable = secondsVariable;
    }

    public BigDecimal getSecondsVariable() 
    {
        return secondsVariable;
    }
    public void setSecondsRm(BigDecimal secondsRm) 
    {
        this.secondsRm = secondsRm;
    }

    public BigDecimal getSecondsRm() 
    {
        return secondsRm;
    }
    public void setSecondsSetup(BigDecimal secondsSetup) 
    {
        this.secondsSetup = secondsSetup;
    }

    public BigDecimal getSecondsSetup() 
    {
        return secondsSetup;
    }
    public void setSecondsStacking(BigDecimal secondsStacking) 
    {
        this.secondsStacking = secondsStacking;
    }

    public BigDecimal getSecondsStacking() 
    {
        return secondsStacking;
    }
    public void setSecondsTransfer(BigDecimal secondsTransfer) 
    {
        this.secondsTransfer = secondsTransfer;
    }

    public BigDecimal getSecondsTransfer() 
    {
        return secondsTransfer;
    }
    public void setSecondsBuffer(BigDecimal secondsBuffer) 
    {
        this.secondsBuffer = secondsBuffer;
    }

    public BigDecimal getSecondsBuffer() 
    {
        return secondsBuffer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("id", getId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("processSeq", getProcessSeq())
            .append("processType", getProcessType())
            .append("secondsFixed", getSecondsFixed())
            .append("secondsVariable", getSecondsVariable())
            .append("secondsRm", getSecondsRm())
            .append("secondsSetup", getSecondsSetup())
            .append("secondsStacking", getSecondsStacking())
            .append("secondsTransfer", getSecondsTransfer())
            .append("secondsBuffer", getSecondsBuffer())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

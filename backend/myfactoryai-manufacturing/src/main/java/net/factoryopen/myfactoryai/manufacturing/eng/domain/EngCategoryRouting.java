package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 品类工艺路线对象 eng_category_routing
 * 
 * @author admin
 * @date 2024-11-09
 */
public class EngCategoryRouting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 品类ID */
    @Excel(name = "品类ID")
    private Long categoryId;

    /** 序号 */
    @Excel(name = "序号")
    private Integer seqNo;

    /** 上序号 */
    @Excel(name = "上序号")
    private Integer lastSeqNo;

    /** 下序号 */
    @Excel(name = "下序号")
    private Integer nextSeqNo;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

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

    /** 产线类型 */
    @Excel(name = "产线类型")
    private Integer lineType;

    /** 工序类型 */
    @Excel(name = "工序类型")
    private Integer processType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
    }
    public void setLastSeqNo(Integer lastSeqNo) 
    {
        this.lastSeqNo = lastSeqNo;
    }

    public Integer getLastSeqNo() 
    {
        return lastSeqNo;
    }
    public void setNextSeqNo(Integer nextSeqNo) 
    {
        this.nextSeqNo = nextSeqNo;
    }

    public Integer getNextSeqNo() 
    {
        return nextSeqNo;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
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
    public void setLineType(Integer lineType) 
    {
        this.lineType = lineType;
    }

    public Integer getLineType() 
    {
        return lineType;
    }
    public void setProcessType(Integer processType) 
    {
        this.processType = processType;
    }

    public Integer getProcessType() 
    {
        return processType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("seqNo", getSeqNo())
            .append("lastSeqNo", getLastSeqNo())
            .append("nextSeqNo", getNextSeqNo())
            .append("lineId", getLineId())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("secondsFixed", getSecondsFixed())
            .append("secondsVariable", getSecondsVariable())
            .append("secondsRm", getSecondsRm())
            .append("secondsSetup", getSecondsSetup())
            .append("secondsStacking", getSecondsStacking())
            .append("secondsTransfer", getSecondsTransfer())
            .append("secondsBuffer", getSecondsBuffer())
            .append("lineType", getLineType())
            .append("processType", getProcessType())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

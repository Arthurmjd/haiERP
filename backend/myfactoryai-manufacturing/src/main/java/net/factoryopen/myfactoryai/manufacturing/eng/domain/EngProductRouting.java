package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 制造工艺管理对象 eng_product_routing
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public class EngProductRouting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 产线ID */
    @Excel(name = "产线ID")
    private Long lineId;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 上序号 */
    @Excel(name = "上序号")
    private Integer lastSeqNo;

    /** 下序号 */
    @Excel(name = "下序号")
    private Integer nextSeqNo;

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

    /** 工序类型 */
    @Excel(name = "工序类型")
    private Integer processType;

    /** 产线类型 */
    @Excel(name = "产线类型")
    private Integer lineType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
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
    public void setProcessType(Integer processType) 
    {
        this.processType = processType;
    }

    public Integer getProcessType() 
    {
        return processType;
    }
    public void setLineType(Integer lineType) 
    {
        this.lineType = lineType;
    }

    public Integer getLineType() 
    {
        return lineType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productId", getProductId())
            .append("seqNo", getSeqNo())
            .append("lineId", getLineId())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("lastSeqNo", getLastSeqNo())
            .append("nextSeqNo", getNextSeqNo())
            .append("secondsFixed", getSecondsFixed())
            .append("secondsVariable", getSecondsVariable())
            .append("secondsRm", getSecondsRm())
            .append("secondsSetup", getSecondsSetup())
            .append("secondsStacking", getSecondsStacking())
            .append("secondsTransfer", getSecondsTransfer())
            .append("secondsBuffer", getSecondsBuffer())
            .append("processType", getProcessType())
            .append("lineType", getLineType())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

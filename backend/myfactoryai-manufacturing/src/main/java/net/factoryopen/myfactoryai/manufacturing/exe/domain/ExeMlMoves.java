package net.factoryopen.myfactoryai.manufacturing.exe.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 历史移转查询对象 exe_process_moves
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public class ExeMlMoves extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 制造批 */
    @Excel(name = "制造批")
    private Long mlId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品规格 */
    private String productSpec;

    /** 工序号 */
    @Excel(name = "工序号")
    private Integer processSeq;

    /** 工序码 */
    @Excel(name = "工序码")
    private Long processId;

    /** 产线 */
    @Excel(name = "产线")
    private Long lineId;

    /** 上序号 */
    private Integer lastSeqNo;

    /** 下序号 */
    private Integer nextSeqNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 接收数量 */
    @Excel(name = "接收数量")
    private BigDecimal quantityReceive;

    /** 报废数量 */
    @Excel(name = "报废数量")
    private BigDecimal quantityScrap;

    /** 重派数量 */
    @Excel(name = "重派数量")
    private BigDecimal quantityRedisp;

    /** 返工数量 */
    @Excel(name = "返工数量")
    private BigDecimal quantityRework;

    /** 待判数量 */
    @Excel(name = "待判数量")
    private BigDecimal quantityPending;

    /** 特采次数 */
    @Excel(name = "特采次数")
    private BigDecimal quantityAod;

    /** 分批数量 */
    @Excel(name = "分批数量")
    private BigDecimal quantitySplit;

    /** 转出数量 */
    @Excel(name = "转出数量")
    private BigDecimal quantityPost;

    /** 排配接收时间 */
    private Date receiveTimeScheduled;

    /** 排配转出时间 */
    private Date postTimeScheduled;

    /** 产线类型 */
    @Excel(name = "产线类型")
    private Integer lineType;

    /** 工序类型 */
    @Excel(name = "工序类型")
    private Integer processType;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    /** 班组 */
    @Excel(name = "班组")
    private Long groupId;

    /** 标准固定秒数 */
    private BigDecimal secondsFixed;

    /** 标准可变秒数 */
    private BigDecimal secondsVariable;

    /** 备料秒数 */
    private BigDecimal secondsRm;

    /** 架机秒数 */
    private BigDecimal secondsSetup;

    /** 堆垛秒数 */
    private BigDecimal secondsStacking;

    /** 移转秒数 */
    private BigDecimal secondsTransfer;

    /** 缓冲秒数 */
    private BigDecimal secondsBuffer;

    /** 接收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "接收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 接收人 */
    @Excel(name = "接收人")
    private Long receiveBy;

    /** 转出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "转出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postTime;

    /** 转出人 */
    @Excel(name = "转出人")
    private Long postBy;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setProductSpec(String productSpec) 
    {
        this.productSpec = productSpec;
    }

    public String getProductSpec() 
    {
        return productSpec;
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
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
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
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantityReceive(BigDecimal quantityReceive) 
    {
        this.quantityReceive = quantityReceive;
    }

    public BigDecimal getQuantityReceive() 
    {
        return quantityReceive;
    }
    public void setQuantityScrap(BigDecimal quantityScrap) 
    {
        this.quantityScrap = quantityScrap;
    }

    public BigDecimal getQuantityScrap() 
    {
        return quantityScrap;
    }
    public void setQuantityRedisp(BigDecimal quantityRedisp) 
    {
        this.quantityRedisp = quantityRedisp;
    }

    public BigDecimal getQuantityRedisp() 
    {
        return quantityRedisp;
    }
    public void setQuantityRework(BigDecimal quantityRework) 
    {
        this.quantityRework = quantityRework;
    }

    public BigDecimal getQuantityRework() 
    {
        return quantityRework;
    }
    public void setQuantityPending(BigDecimal quantityPending) 
    {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityPending() 
    {
        return quantityPending;
    }
    public void setQuantityAod(BigDecimal quantityAod) 
    {
        this.quantityAod = quantityAod;
    }

    public BigDecimal getQuantityAod() 
    {
        return quantityAod;
    }
    public void setQuantitySplit(BigDecimal quantitySplit) 
    {
        this.quantitySplit = quantitySplit;
    }

    public BigDecimal getQuantitySplit() 
    {
        return quantitySplit;
    }
    public void setQuantityPost(BigDecimal quantityPost) 
    {
        this.quantityPost = quantityPost;
    }

    public BigDecimal getQuantityPost() 
    {
        return quantityPost;
    }
    public void setReceiveTimeScheduled(Date receiveTimeScheduled) 
    {
        this.receiveTimeScheduled = receiveTimeScheduled;
    }

    public Date getReceiveTimeScheduled() 
    {
        return receiveTimeScheduled;
    }
    public void setPostTimeScheduled(Date postTimeScheduled) 
    {
        this.postTimeScheduled = postTimeScheduled;
    }

    public Date getPostTimeScheduled() 
    {
        return postTimeScheduled;
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
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
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
    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime() 
    {
        return receiveTime;
    }
    public void setReceiveBy(Long receiveBy) 
    {
        this.receiveBy = receiveBy;
    }

    public Long getReceiveBy() 
    {
        return receiveBy;
    }
    public void setPostTime(Date postTime) 
    {
        this.postTime = postTime;
    }

    public Date getPostTime() 
    {
        return postTime;
    }
    public void setPostBy(Long postBy) 
    {
        this.postBy = postBy;
    }

    public Long getPostBy() 
    {
        return postBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mlId", getMlId())
            .append("status", getStatus())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("processSeq", getProcessSeq())
            .append("processId", getProcessId())
            .append("lineId", getLineId())
            .append("lastSeqNo", getLastSeqNo())
            .append("nextSeqNo", getNextSeqNo())
            .append("unitId", getUnitId())
            .append("quantityReceive", getQuantityReceive())
            .append("quantityScrap", getQuantityScrap())
            .append("quantityRedisp", getQuantityRedisp())
            .append("quantityRework", getQuantityRework())
            .append("quantityPending", getQuantityPending())
            .append("quantityAod", getQuantityAod())
            .append("quantitySplit", getQuantitySplit())
            .append("quantityPost", getQuantityPost())
            .append("receiveTimeScheduled", getReceiveTimeScheduled())
            .append("postTimeScheduled", getPostTimeScheduled())
            .append("lineType", getLineType())
            .append("processType", getProcessType())
            .append("deptId", getDeptId())
            .append("groupId", getGroupId())
            .append("remark", getRemark())
            .append("secondsFixed", getSecondsFixed())
            .append("secondsVariable", getSecondsVariable())
            .append("secondsRm", getSecondsRm())
            .append("secondsSetup", getSecondsSetup())
            .append("secondsStacking", getSecondsStacking())
            .append("secondsTransfer", getSecondsTransfer())
            .append("secondsBuffer", getSecondsBuffer())
            .append("receiveTime", getReceiveTime())
            .append("receiveBy", getReceiveBy())
            .append("postTime", getPostTime())
            .append("postBy", getPostBy())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

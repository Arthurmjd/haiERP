package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单透视尾情对象 del_perspectivity
 * 
 * @author admin
 * @date 2024-07-23
 */
public class DelPerspectivityTail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 客户ID */
    private Long customerId;

    /** 订单ID */
    private Long soId;

    /** 单项ID */
    private Long soItemId;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 单项状 */
    private Integer soItemStatus;

    /** 筹交序 */
    private Integer dpTypeSeq;

    /** 筹交类 */
    private String dpTypeDesc;

    /** 筹交单号 */
    private String dpBillno;

    /** 筹交态 */
    private Integer dpStatus;

    /** 工单ID */
    private Long woId;

    /** 工单态 */
    private Integer woStatus;

    /** 批次ID */
    private Long mlId;

    /** 批次态 */
    private Integer mlStatus;

    /** 批现产线ID */
    private Long mlLineId;

    /** 批现工序ID */
    private Long mlProccessId;

    /** 工序态 */
    private Integer mlProcessStatus;

    /** 任务数量 */
    private BigDecimal quantityTask;

    /** 完成数量 */
    private BigDecimal quantityOutput;

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

    /** 项目ID */
    private Long projectId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setSoId(Long soId) 
    {
        this.soId = soId;
    }

    public Long getSoId() 
    {
        return soId;
    }
    public void setSoItemId(Long soItemId) 
    {
        this.soItemId = soItemId;
    }

    public Long getSoItemId() 
    {
        return soItemId;
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
    public void setSoItemStatus(Integer soItemStatus) 
    {
        this.soItemStatus = soItemStatus;
    }

    public Integer getSoItemStatus() 
    {
        return soItemStatus;
    }
    public void setDpTypeSeq(Integer dpTypeSeq) 
    {
        this.dpTypeSeq = dpTypeSeq;
    }

    public Integer getDpTypeSeq() 
    {
        return dpTypeSeq;
    }
    public void setDpTypeDesc(String dpTypeDesc) 
    {
        this.dpTypeDesc = dpTypeDesc;
    }

    public String getDpTypeDesc() 
    {
        return dpTypeDesc;
    }
    public void setDpBillno(String dpBillno) 
    {
        this.dpBillno = dpBillno;
    }

    public String getDpBillno() 
    {
        return dpBillno;
    }
    public void setDpStatus(Integer dpStatus) 
    {
        this.dpStatus = dpStatus;
    }

    public Integer getDpStatus() 
    {
        return dpStatus;
    }
    public void setWoId(Long woId) 
    {
        this.woId = woId;
    }

    public Long getWoId() 
    {
        return woId;
    }
    public void setWoStatus(Integer woStatus) 
    {
        this.woStatus = woStatus;
    }

    public Integer getWoStatus() 
    {
        return woStatus;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setMlStatus(Integer mlStatus) 
    {
        this.mlStatus = mlStatus;
    }

    public Integer getMlStatus() 
    {
        return mlStatus;
    }
    public void setMlLineId(Long mlLineId) 
    {
        this.mlLineId = mlLineId;
    }

    public Long getMlLineId() 
    {
        return mlLineId;
    }
    public void setMlProccessId(Long mlProccessId) 
    {
        this.mlProccessId = mlProccessId;
    }

    public Long getMlProccessId() 
    {
        return mlProccessId;
    }
    public void setMlProcessStatus(Integer mlProcessStatus) 
    {
        this.mlProcessStatus = mlProcessStatus;
    }

    public Integer getMlProcessStatus() 
    {
        return mlProcessStatus;
    }
    public void setQuantityTask(BigDecimal quantityTask) 
    {
        this.quantityTask = quantityTask;
    }

    public BigDecimal getQuantityTask() 
    {
        return quantityTask;
    }
    public void setQuantityOutput(BigDecimal quantityOutput) 
    {
        this.quantityOutput = quantityOutput;
    }

    public BigDecimal getQuantityOutput() 
    {
        return quantityOutput;
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
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("customerId", getCustomerId())
            .append("soId", getSoId())
            .append("soItemId", getSoItemId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("soItemStatus", getSoItemStatus())
            .append("dpTypeSeq", getDpTypeSeq())
            .append("dpTypeDesc", getDpTypeDesc())
            .append("dpBillno", getDpBillno())
            .append("dpStatus", getDpStatus())
            .append("woId", getWoId())
            .append("woStatus", getWoStatus())
            .append("mlId", getMlId())
            .append("mlStatus", getMlStatus())
            .append("mlLineId", getMlLineId())
            .append("mlProccessId", getMlProccessId())
            .append("mlProcessStatus", getMlProcessStatus())
            .append("quantityTask", getQuantityTask())
            .append("quantityOutput", getQuantityOutput())
            .append("quantityScrap", getQuantityScrap())
            .append("quantityRedisp", getQuantityRedisp())
            .append("quantityRework", getQuantityRework())
            .append("quantityPending", getQuantityPending())
            .append("quantityAod", getQuantityAod())
            .append("quantitySplit", getQuantitySplit())
            .append("projectId", getProjectId())
            .append("remark", getRemark())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.del.domain;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单透视表对象 del_perspectivity
 * 
 * @author admin
 * @date 2024-07-23
 */
public class DelPerspectivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long soId;

    /** 单项ID */
    @Excel(name = "单项ID")
    private Long soItemId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String soNo;

    /** 订单项序 */
    @Excel(name = "订单项序")
    private String soItemSeq;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 海外料号 */
    private String abroadProductNo;

    /** 单项状 */
    @Excel(name = "单项状")
    private Integer soItemStatus;

    /** 类序 */
    @Excel(name = "类序")
    private Integer dpTypeSeq;

    /** 筹类 */
    @Excel(name = "筹类")
    private String dpTypeDesc;

    /** 筹交单号 */
    @Excel(name = "筹交单号")
    private String dpBillno;

    /** 筹交态 */
    @Excel(name = "筹交态")
    private Integer dpStatus;

    /** 工单项序 */
    @Excel(name = "工单项序")
    private Integer woSeq;

    /** 工单ID */
    @Excel(name = "工单ID")
    private Long woId;

    /** 工单态 */
    @Excel(name = "工单态")
    private Integer woStatus;

    /** 批次项序 */
    @Excel(name = "批次项序")
    private Integer mlSeq;

    /** 批次ID */
    @Excel(name = "批次ID")
    private Long mlId;

    /** 批次态 */
    @Excel(name = "批次态")
    private Integer mlStatus;

    /** 批现产线ID */
    @Excel(name = "批现产线ID")
    private Long mlLineId;

    /** 批现工序ID */
    @Excel(name = "批现工序ID")
    private Long mlProccessId;

    /** 工序态 */
    @Excel(name = "工序态")
    private Integer mlProcessStatus;

    /** 任务数量 */
    @Excel(name = "任务数量")
    private BigDecimal quantityTask;

    /** 完成数量 */
    @Excel(name = "完成数量")
    private BigDecimal quantityOutput;

    /** 报废数量 */
    private BigDecimal quantityScrap;

    /** 重派数量 */
    private BigDecimal quantityRedisp;

    /** 返工数量 */
    private BigDecimal quantityRework;

    /** 待判数量 */
    private BigDecimal quantityPending;

    /** 特采次数 */
    private BigDecimal quantityAod;

    /** 分批数量 */
    private BigDecimal quantitySplit;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public Long getSoItemId() {
        return soItemId;
    }

    public void setSoItemId(Long soItemId) {
        this.soItemId = soItemId;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getSoItemSeq() {
        return soItemSeq;
    }

    public void setSoItemSeq(String soItemSeq) {
        this.soItemSeq = soItemSeq;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }

    public String getCustomerProductNo() {
        return customerProductNo;
    }

    public void setCustomerProductNo(String customerProductNo) {
        this.customerProductNo = customerProductNo;
    }

    public String getAbroadProductNo() {
        return abroadProductNo;
    }

    public void setAbroadProductNo(String abroadProductNo) {
        this.abroadProductNo = abroadProductNo;
    }

    public Integer getSoItemStatus() {
        return soItemStatus;
    }

    public void setSoItemStatus(Integer soItemStatus) {
        this.soItemStatus = soItemStatus;
    }

    public Integer getDpTypeSeq() {
        return dpTypeSeq;
    }

    public void setDpTypeSeq(Integer dpTypeSeq) {
        this.dpTypeSeq = dpTypeSeq;
    }

    public String getDpTypeDesc() {
        return dpTypeDesc;
    }

    public void setDpTypeDesc(String dpTypeDesc) {
        this.dpTypeDesc = dpTypeDesc;
    }

    public String getDpBillno() {
        return dpBillno;
    }

    public void setDpBillno(String dpBillno) {
        this.dpBillno = dpBillno;
    }

    public Integer getDpStatus() {
        return dpStatus;
    }

    public void setDpStatus(Integer dpStatus) {
        this.dpStatus = dpStatus;
    }

    public Integer getWoSeq() {
        return woSeq;
    }

    public void setWoSeq(Integer woSeq) {
        this.woSeq = woSeq;
    }

    public Long getWoId() {
        return woId;
    }

    public void setWoId(Long woId) {
        this.woId = woId;
    }

    public Integer getWoStatus() {
        return woStatus;
    }

    public void setWoStatus(Integer woStatus) {
        this.woStatus = woStatus;
    }

    public Integer getMlSeq() {
        return mlSeq;
    }

    public void setMlSeq(Integer mlSeq) {
        this.mlSeq = mlSeq;
    }

    public Long getMlId() {
        return mlId;
    }

    public void setMlId(Long mlId) {
        this.mlId = mlId;
    }

    public Integer getMlStatus() {
        return mlStatus;
    }

    public void setMlStatus(Integer mlStatus) {
        this.mlStatus = mlStatus;
    }

    public Long getMlLineId() {
        return mlLineId;
    }

    public void setMlLineId(Long mlLineId) {
        this.mlLineId = mlLineId;
    }

    public Long getMlProccessId() {
        return mlProccessId;
    }

    public void setMlProccessId(Long mlProccessId) {
        this.mlProccessId = mlProccessId;
    }

    public Integer getMlProcessStatus() {
        return mlProcessStatus;
    }

    public void setMlProcessStatus(Integer mlProcessStatus) {
        this.mlProcessStatus = mlProcessStatus;
    }

    public BigDecimal getQuantityTask() {
        return quantityTask;
    }

    public void setQuantityTask(BigDecimal quantityTask) {
        this.quantityTask = quantityTask;
    }

    public BigDecimal getQuantityOutput() {
        return quantityOutput;
    }

    public void setQuantityOutput(BigDecimal quantityOutput) {
        this.quantityOutput = quantityOutput;
    }

    public BigDecimal getQuantityScrap() {
        return quantityScrap;
    }

    public void setQuantityScrap(BigDecimal quantityScrap) {
        this.quantityScrap = quantityScrap;
    }

    public BigDecimal getQuantityRedisp() {
        return quantityRedisp;
    }

    public void setQuantityRedisp(BigDecimal quantityRedisp) {
        this.quantityRedisp = quantityRedisp;
    }

    public BigDecimal getQuantityRework() {
        return quantityRework;
    }

    public void setQuantityRework(BigDecimal quantityRework) {
        this.quantityRework = quantityRework;
    }

    public BigDecimal getQuantityPending() {
        return quantityPending;
    }

    public void setQuantityPending(BigDecimal quantityPending) {
        this.quantityPending = quantityPending;
    }

    public BigDecimal getQuantityAod() {
        return quantityAod;
    }

    public void setQuantityAod(BigDecimal quantityAod) {
        this.quantityAod = quantityAod;
    }

    public BigDecimal getQuantitySplit() {
        return quantitySplit;
    }

    public void setQuantitySplit(BigDecimal quantitySplit) {
        this.quantitySplit = quantitySplit;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "DelPerspectivity{" +
                "id=" + id +
                ", userId=" + userId +
                ", customerId=" + customerId +
                ", soId=" + soId +
                ", soItemId=" + soItemId +
                ", soNo='" + soNo + '\'' +
                ", soItemSeq='" + soItemSeq + '\'' +
                ", productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productSpec='" + productSpec + '\'' +
                ", drawNo='" + drawNo + '\'' +
                ", customerProductNo='" + customerProductNo + '\'' +
                ", abroadProductNo='" + abroadProductNo + '\'' +
                ", soItemStatus=" + soItemStatus +
                ", dpTypeSeq=" + dpTypeSeq +
                ", dpTypeDesc='" + dpTypeDesc + '\'' +
                ", dpBillno='" + dpBillno + '\'' +
                ", dpStatus=" + dpStatus +
                ", woSeq=" + woSeq +
                ", woId=" + woId +
                ", woStatus=" + woStatus +
                ", mlSeq=" + mlSeq +
                ", mlId=" + mlId +
                ", mlStatus=" + mlStatus +
                ", mlLineId=" + mlLineId +
                ", mlProccessId=" + mlProccessId +
                ", mlProcessStatus=" + mlProcessStatus +
                ", quantityTask=" + quantityTask +
                ", quantityOutput=" + quantityOutput +
                ", quantityScrap=" + quantityScrap +
                ", quantityRedisp=" + quantityRedisp +
                ", quantityRework=" + quantityRework +
                ", quantityPending=" + quantityPending +
                ", quantityAod=" + quantityAod +
                ", quantitySplit=" + quantitySplit +
                ", projectId=" + projectId +
                '}';
    }
}

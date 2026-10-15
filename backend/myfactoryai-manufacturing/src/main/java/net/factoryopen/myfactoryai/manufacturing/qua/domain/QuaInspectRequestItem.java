package net.factoryopen.myfactoryai.manufacturing.qua.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 请检对象 qua_inspect_request_item
 * 
 * @author admin
 * @date 2024-10-06
 */
public class QuaInspectRequestItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 请求ID */
    @Excel(name = "请求ID")
    private Long requestId;

    /** 项次号 */
    @Excel(name = "项次号")
    private Integer seqNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 请检数量 */
    @Excel(name = "请检数量")
    private BigDecimal quantity;

    /** 源单ID */
    @Excel(name = "源单ID")
    private Long sourceId;

    /** 请检数量 */
    @Excel(name = "检异常数")
    private BigDecimal quantityQcExceptional;

    /** 请检数量 */
    @Excel(name = "检合格数")
    private BigDecimal quantityQcGood;

    /** 请检数量 */
    @Excel(name = "检不良数")
    private BigDecimal quantityQcNg;

    /** 请检数量 */
    @Excel(name = "检重制数")
    private BigDecimal quantityQcRedisp;

    /** 请检数量 */
    @Excel(name = "检重工数")
    private BigDecimal quantityQcRework;

    /** 请检数量 */
    @Excel(name = "检待判数")
    private BigDecimal quantityQcPending;

    /** 请检数量 */
    @Excel(name = "检特采数")
    private BigDecimal quantityQcAod;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRequestId(Long requestId) 
    {
        this.requestId = requestId;
    }

    public Long getRequestId() 
    {
        return requestId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setMaterialSpec(String materialSpec) 
    {
        this.materialSpec = materialSpec;
    }

    public String getMaterialSpec() 
    {
        return materialSpec;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public BigDecimal getQuantityQcExceptional() {
        return quantityQcExceptional;
    }

    public void setQuantityQcExceptional(BigDecimal quantityQcExceptional) {
        this.quantityQcExceptional = quantityQcExceptional;
    }

    public BigDecimal getQuantityQcGood() {
        return quantityQcGood;
    }

    public void setQuantityQcGood(BigDecimal quantityQcGood) {
        this.quantityQcGood = quantityQcGood;
    }

    public BigDecimal getQuantityQcNg() {
        return quantityQcNg;
    }

    public void setQuantityQcNg(BigDecimal quantityQcNg) {
        this.quantityQcNg = quantityQcNg;
    }

    public BigDecimal getQuantityQcRedisp() {
        return quantityQcRedisp;
    }

    public void setQuantityQcRedisp(BigDecimal quantityQcRedisp) {
        this.quantityQcRedisp = quantityQcRedisp;
    }

    public BigDecimal getQuantityQcRework() {
        return quantityQcRework;
    }

    public void setQuantityQcRework(BigDecimal quantityQcRework) {
        this.quantityQcRework = quantityQcRework;
    }

    public BigDecimal getQuantityQcPending() {
        return quantityQcPending;
    }

    public void setQuantityQcPending(BigDecimal quantityQcPending) {
        this.quantityQcPending = quantityQcPending;
    }

    public BigDecimal getQuantityQcAod() {
        return quantityQcAod;
    }

    public void setQuantityQcAod(BigDecimal quantityQcAod) {
        this.quantityQcAod = quantityQcAod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("requestId", getRequestId())
            .append("seqNo", getSeqNo())
            .append("status", getStatus())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("sourceId", getSourceId())
            .append("quantityQcExceptional", getQuantityQcExceptional())
            .append("quantityQcGood", getQuantityQcGood())
            .append("quantityQcNg", getQuantityQcNg())
            .append("quantityQcRedisp", getQuantityQcRedisp())
            .append("quantityQcRework", getQuantityQcRework())
            .append("quantityQcPending", getQuantityQcPending())
            .append("quantityQcAod", getQuantityQcAod())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

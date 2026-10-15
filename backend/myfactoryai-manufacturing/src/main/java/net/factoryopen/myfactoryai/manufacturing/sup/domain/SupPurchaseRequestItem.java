package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购申请明细对象 sup_pritem
 * 
 * @author admin
 * @date 2024-09-16
 */
public class SupPurchaseRequestItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 采购申请 */
    @Excel(name = "采购申请")
    private Long prId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 物料编码 */
    @Excel(name = "物料编码")
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

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 订单数量 */
    @Excel(name = "订单数量")
    private BigDecimal quantity;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requireDay;

    /** 优先级 */
    @Excel(name = "优先级")
    private Integer priority;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 销售价格 */
    @Excel(name = "销售价格")
    private BigDecimal priceSale;

    /** 来源单ID */
    @Excel(name = "来源单ID")
    private Long requestBillId;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String requestBillNo;

    /** 消耗类型 */
    @Excel(name = "消耗类型")
    private Integer consumeType;

    /** 必配料 */
    @Excel(name = "必配料")
    private String enableMust;

    /** 可替代 */
    @Excel(name = "可替代")
    private String enableReplace;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPrId(Long prId) 
    {
        this.prId = prId;
    }

    public Long getPrId() 
    {
        return prId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
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

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
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
    public void setRequireDay(Date requireDay) 
    {
        this.requireDay = requireDay;
    }

    public Date getRequireDay() 
    {
        return requireDay;
    }
    public void setPriority(Integer priority) 
    {
        this.priority = priority;
    }

    public Integer getPriority() 
    {
        return priority;
    }

    public Long getRequestBillId() {
        return requestBillId;
    }

    public void setRequestBillId(Long requestBillId) {
        this.requestBillId = requestBillId;
    }

    public String getRequestBillNo() {
        return requestBillNo;
    }

    public void setRequestBillNo(String requestBillNo) {
        this.requestBillNo = requestBillNo;
    }

    public void setConsumeType(Integer consumeType)
    {
        this.consumeType = consumeType;
    }

    public Integer getConsumeType() 
    {
        return consumeType;
    }
    public void setEnableMust(String enableMust) 
    {
        this.enableMust = enableMust;
    }

    public String getEnableMust() 
    {
        return enableMust;
    }
    public void setEnableReplace(String enableReplace) 
    {
        this.enableReplace = enableReplace;
    }

    public String getEnableReplace() 
    {
        return enableReplace;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }
}

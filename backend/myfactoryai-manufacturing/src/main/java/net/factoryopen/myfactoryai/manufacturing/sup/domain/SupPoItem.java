package net.factoryopen.myfactoryai.manufacturing.sup.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购单明细对象 sup_po_item
 * 
 * @author admin
 * @date 2024-09-23
 */
public class SupPoItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 采购单ID */
    @Excel(name = "采购单ID")
    private Long poId;

    /** 项序 */
    @Excel(name = "项序")
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

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 采购数量 */
    @Excel(name = "采购数量")
    private BigDecimal quantityOrder;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requireDay;

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
    private Long sourceBillId;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String sourceBillNo;

    /** 发货数量 */
    @Excel(name = "发货数量")
    private BigDecimal quantityShipped;

    /** 到货数量 */
    @Excel(name = "到货数量")
    private BigDecimal quantityArrival;

    /** 检验数量 */
    @Excel(name = "检验数量")
    private BigDecimal quantityInspected;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private BigDecimal quantityInventory;

    /** 请检数量 */
    @Excel(name = "检不良数")
    private BigDecimal quantityQcNg;

    /** 请检数量 */
    @Excel(name = "检待判数")
    private BigDecimal quantityQcPending;

    /** 请检数量 */
    @Excel(name = "检特采数")
    private BigDecimal quantityQcAod;

    /** 请检数量 */
    @Excel(name = "在退货数量")
    private BigDecimal quantityReturning;

    /** 请检数量 */
    @Excel(name = "已退货数量")
    private BigDecimal quantityReturned;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPoId(Long poId) 
    {
        this.poId = poId;
    }

    public Long getPoId() 
    {
        return poId;
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
    public void setQuantityOrder(BigDecimal quantityOrder) 
    {
        this.quantityOrder = quantityOrder;
    }

    public BigDecimal getQuantityOrder() 
    {
        return quantityOrder;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setRequireDay(Date requireDay) 
    {
        this.requireDay = requireDay;
    }

    public Date getRequireDay() 
    {
        return requireDay;
    }
    public void setSourceBillId(Long sourceBillId) 
    {
        this.sourceBillId = sourceBillId;
    }

    public Long getSourceBillId() 
    {
        return sourceBillId;
    }
    public void setSourceBillNo(String sourceBillNo) 
    {
        this.sourceBillNo = sourceBillNo;
    }

    public String getSourceBillNo() 
    {
        return sourceBillNo;
    }
    public void setQuantityShipped(BigDecimal quantityShipped) 
    {
        this.quantityShipped = quantityShipped;
    }

    public BigDecimal getQuantityShipped() 
    {
        return quantityShipped;
    }
    public void setQuantityArrival(BigDecimal quantityArrival) 
    {
        this.quantityArrival = quantityArrival;
    }

    public BigDecimal getQuantityArrival() 
    {
        return quantityArrival;
    }
    public void setQuantityInspected(BigDecimal quantityInspected) 
    {
        this.quantityInspected = quantityInspected;
    }

    public BigDecimal getQuantityInspected() 
    {
        return quantityInspected;
    }

    public void setQuantityInventory(BigDecimal quantityInventory)
    {
        this.quantityInventory = quantityInventory;
    }

    public BigDecimal getQuantityInventory()
    {
        return quantityInventory;
    }

    public BigDecimal getQuantityQcNg() {
        return quantityQcNg;
    }

    public void setQuantityQcNg(BigDecimal quantityQcNg) {
        this.quantityQcNg = quantityQcNg;
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

    public BigDecimal getQuantityReturning() {
        return quantityReturning;
    }

    public void setQuantityReturning(BigDecimal quantityReturning) {
        this.quantityReturning = quantityReturning;
    }

    public BigDecimal getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(BigDecimal quantityReturned) {
        this.quantityReturned = quantityReturned;
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

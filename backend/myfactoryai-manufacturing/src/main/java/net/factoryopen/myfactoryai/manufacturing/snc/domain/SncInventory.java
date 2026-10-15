package net.factoryopen.myfactoryai.manufacturing.snc.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库存货详单对象 snc_inventory
 * 
 * @author jitfactory-generator
 * @date 2024-07-21
 */
public class SncInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 库存批号 */
    @Excel(name = "库存批号")
    private String billNo;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long houseId;

    /** 储位 */
    @Excel(name = "储位")
    private Long positionId;

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

    /** 客户料号 */
    @Excel(name = "客户料号")
    private String customerProductNo;

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 仓库类型 */
    @Excel(name = "仓库类型")
    private Integer houseType;

    /** 保质时限 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "保质时限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timeExpired;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBillNo(String billNo) 
    {
        this.billNo = billNo;
    }

    public String getBillNo() 
    {
        return billNo;
    }
    public void setHouseId(Long houseId) 
    {
        this.houseId = houseId;
    }

    public Long getHouseId() 
    {
        return houseId;
    }
    public void setPositionId(Long positionId) 
    {
        this.positionId = positionId;
    }

    public Long getPositionId() 
    {
        return positionId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
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
    public void setHouseType(Integer houseType)
    {
        this.houseType = houseType;
    }

    public Integer getHouseType()
    {
        return houseType;
    }
    public void setTimeExpired(Date timeExpired) 
    {
        this.timeExpired = timeExpired;
    }

    public Date getTimeExpired() 
    {
        return timeExpired;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("houseId", getHouseId())
            .append("positionId", getPositionId())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("abroadProductNo", getAbroadProductNo())
            .append("unitId", getUnitId())
            .append("quantity", getQuantity())
            .append("price", getPrice())
            .append("amount", getAmount())
            .append("houseType", getHouseType())
            .append("timeExpired", getTimeExpired())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

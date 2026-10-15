package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 产成品管理对象 eng_material
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public class EngMaterialExcel extends BaseEntity
{
    /** 物料编码 */
    @Excel(name = "物料代码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "规格型号")
    private String materialSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 品类 */
    @Excel(name = "品类")
    private Long categoryId;

    /** 物料类型 */
    @Excel(name = "物料类型")
    private String baseType;

    /** 单位 */
    @Excel(name = "单位")
    private String unitDesc;

    /** 预约量 */
    @Excel(name = "单价")
    private BigDecimal priceLast;

    /** 合格数量 */
    @Excel(name = "库存")
    private BigDecimal quantityQualified;

    /** 报废 */
    @Excel(name = "报废")
    private BigDecimal quantityScraped;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal amount;

    /** 库别 */
    @Excel(name = "库别")
    private String house;

    /** 仓位 */
    @Excel(name = "仓位")
    private String position;

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialSpec() {
        return materialSpec;
    }

    public void setMaterialSpec(String materialSpec) {
        this.materialSpec = materialSpec;
    }

    public String getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    public BigDecimal getPriceLast() {
        return priceLast;
    }

    public void setPriceLast(BigDecimal priceLast) {
        this.priceLast = priceLast;
    }

    public BigDecimal getQuantityQualified() {
        return quantityQualified;
    }

    public void setQuantityQualified(BigDecimal quantityQualified) {
        this.quantityQualified = quantityQualified;
    }

    public BigDecimal getQuantityScraped() {
        return quantityScraped;
    }

    public void setQuantityScraped(BigDecimal quantityScraped) {
        this.quantityScraped = quantityScraped;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

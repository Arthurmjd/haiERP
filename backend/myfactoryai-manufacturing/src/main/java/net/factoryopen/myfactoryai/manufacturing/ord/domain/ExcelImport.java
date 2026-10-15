package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细Excel导入对象
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ExcelImport extends BaseEntity implements ExcelImportInteface
{

    /** 订单项 */
    @Excel(name = "序号")
    private String seqNo;

    /** 料号 */
    @Excel(name = "料号")
    private String productCode;

    /** 名称 */
    @Excel(name = "中文名称")
    private String productName;

    /** 尺寸 */
    @Excel(name = "规格型号")
    private String productSpec;

    /** 图号 */
    @Excel(name = "图号")
    private String drawNo;

    /** 客户料号 */
    @Excel(name = "用户物料编码")
    private String customerProductNo;

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 数量 */
    @Excel(name = "采购量")
    private BigDecimal quantity;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 合同号 */
    @Excel(name = "合同号")
    private String contractNo;

    /** 烟厂 */
    @Excel(name = "烟厂")
    private String customerName;

    /** 交货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date needDay;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDay;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    @Override
    public String getContractNo() {
        return contractNo;
    }

    @Override
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Override
    public String getSeqNo() {
        return seqNo;
    }

    @Override
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public String getProductCode() {
        return productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String getProductSpec() {
        return productSpec;
    }

    @Override
    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    @Override
    public String getDrawNo() {
        return drawNo;
    }

    @Override
    public void setDrawNo(String drawNo) {
        this.drawNo = drawNo;
    }

    @Override
    public String getCustomerProductNo() {
        return customerProductNo;
    }

    @Override
    public void setCustomerProductNo(String customerProductNo) {
        this.customerProductNo = customerProductNo;
    }

    @Override
    public String getAbroadProductNo() {
        return abroadProductNo;
    }

    @Override
    public void setAbroadProductNo(String abroadProductNo) {
        this.abroadProductNo = abroadProductNo;
    }

    @Override
    public Date getOrderDay() {
        return orderDay;
    }

    @Override
    public void setOrderDay(Date orderDay) {
        this.orderDay = orderDay;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public Date getNeedDay() {
        return needDay;
    }

    @Override
    public void setNeedDay(Date needDay) {
        this.needDay = needDay;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}

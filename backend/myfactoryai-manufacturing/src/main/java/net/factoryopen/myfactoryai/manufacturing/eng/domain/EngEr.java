package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺处理请求对象 eng_er
 * 
 * @author admin
 * @date 2024-12-12
 */
public class EngEr extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 采购申请号 */
    @Excel(name = "采购申请号")
    private String billNo;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 类型 */
    @Excel(name = "类型")
    private Integer erType;

    /** 主计划ID */
    @Excel(name = "主计划ID")
    private Long mpsId;

    /** 主计划号 */
    @Excel(name = "主计划号")
    private String mpsNo;

    /** 制造批ID */
    @Excel(name = "制造批ID")
    private Long mlId;

    /** 制造批号 */
    @Excel(name = "制造批号")
    private String mlNo;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 品类ID */
    @Excel(name = "品类ID")
    private Long categoryId;

    /** 材质 */
    @Excel(name = "材质")
    private Long compoundId;

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

    /** 进口料号 */
    @Excel(name = "进口料号")
    private String abroadProductNo;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

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
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setErType(Integer erType) 
    {
        this.erType = erType;
    }

    public Integer getErType() 
    {
        return erType;
    }
    public void setMpsId(Long mpsId) 
    {
        this.mpsId = mpsId;
    }

    public Long getMpsId() 
    {
        return mpsId;
    }
    public void setMpsNo(String mpsNo) 
    {
        this.mpsNo = mpsNo;
    }

    public String getMpsNo() 
    {
        return mpsNo;
    }
    public void setMlId(Long mlId) 
    {
        this.mlId = mlId;
    }

    public Long getMlId() 
    {
        return mlId;
    }
    public void setMlNo(String mlNo) 
    {
        this.mlNo = mlNo;
    }

    public String getMlNo() 
    {
        return mlNo;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
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
    public void setDrawNo(String drawNo) 
    {
        this.drawNo = drawNo;
    }

    public String getDrawNo() 
    {
        return drawNo;
    }
    public void setCustomerProductNo(String customerProductNo) 
    {
        this.customerProductNo = customerProductNo;
    }

    public String getCustomerProductNo() 
    {
        return customerProductNo;
    }
    public void setAbroadProductNo(String abroadProductNo) 
    {
        this.abroadProductNo = abroadProductNo;
    }

    public String getAbroadProductNo() 
    {
        return abroadProductNo;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(Long compoundId) {
        this.compoundId = compoundId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("status", getStatus())
            .append("erType", getErType())
            .append("mpsId", getMpsId())
            .append("mpsNo", getMpsNo())
            .append("mlId", getMlId())
            .append("mlNo", getMlNo())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("drawNo", getDrawNo())
            .append("customerProductNo", getCustomerProductNo())
            .append("abroadProductNo", getAbroadProductNo())
            .append("categoryId", getCategoryId())
            .append("compoundId", getCompoundId())
            .append("unitId", getUnitId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

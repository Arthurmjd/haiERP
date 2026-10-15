package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BOM物料清单Excel导入对象
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class EngProductBomExcel extends BaseEntity
{
    /** 项目号 */
    @Excel(name = "项目号")
    private String seqNo;

    /** 零件编码 */
    @Excel(name = "零件编码")
    private String materialCode;

    /** 零件名称 */
    @Excel(name = "零件名称")
    private String materialName;

    /** 零件尺寸 */
    @Excel(name = "零件尺寸")
    private String materialSpec;

    /** 零件图号 */
    @Excel(name = "零件图号")
    private String drawNo;

    /** 零件类型 */
    @Excel(name = "零件类型")
    private String materialType;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}

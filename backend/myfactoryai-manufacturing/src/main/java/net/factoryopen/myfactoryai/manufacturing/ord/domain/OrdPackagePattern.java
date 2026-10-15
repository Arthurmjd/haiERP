package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 包装方式预配对象 ord_package_pattern
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class OrdPackagePattern extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 方式名称 */
    @Excel(name = "方式名称")
    private String patternName;

    /** 包装容器 */
    @Excel(name = "包装容器")
    private String boxName;

    /** 包材名称 */
    @Excel(name = "包材名称")
    private String boxMaterial;

    /** 包材规格 */
    @Excel(name = "包材规格")
    private String boxSpec;

    /** 外包装长 */
    @Excel(name = "外包装长")
    private BigDecimal boxLength;

    /** 外包装宽 */
    @Excel(name = "外包装宽")
    private BigDecimal boxWidth;

    /** 外包装高 */
    @Excel(name = "外包装高")
    private BigDecimal boxHeight;

    /** 重量单位 */
    @Excel(name = "重量单位")
    private Long weightUnitId;

    /** 内包否 */
    @Excel(name = "内包否")
    private String enableChild;

    /** 内包装容器 */
    @Excel(name = "内包装容器")
    private String childboxName;

    /** 内包装名称 */
    @Excel(name = "内包装名称")
    private String childboxMaterial;

    /** 内包装规格 */
    @Excel(name = "内包装规格")
    private String childboxSpec;

    /** 长面个数 */
    @Excel(name = "长面个数")
    private Integer childLongnum;

    /** 宽面个数 */
    @Excel(name = "宽面个数")
    private Integer childWidenum;

    /** 高面个数 */
    @Excel(name = "高面个数")
    private Integer childHighnum;

    /** 内装单位 */
    @Excel(name = "内装单位")
    private Long childUnitId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPatternName(String patternName) 
    {
        this.patternName = patternName;
    }

    public String getPatternName() 
    {
        return patternName;
    }
    public void setBoxName(String boxName) 
    {
        this.boxName = boxName;
    }

    public String getBoxName() 
    {
        return boxName;
    }
    public void setBoxMaterial(String boxMaterial) 
    {
        this.boxMaterial = boxMaterial;
    }

    public String getBoxMaterial() 
    {
        return boxMaterial;
    }
    public void setBoxSpec(String boxSpec) 
    {
        this.boxSpec = boxSpec;
    }

    public String getBoxSpec() 
    {
        return boxSpec;
    }
    public void setBoxLength(BigDecimal boxLength) 
    {
        this.boxLength = boxLength;
    }

    public BigDecimal getBoxLength() 
    {
        return boxLength;
    }
    public void setBoxWidth(BigDecimal boxWidth) 
    {
        this.boxWidth = boxWidth;
    }

    public BigDecimal getBoxWidth() 
    {
        return boxWidth;
    }
    public void setBoxHeight(BigDecimal boxHeight) 
    {
        this.boxHeight = boxHeight;
    }

    public BigDecimal getBoxHeight() 
    {
        return boxHeight;
    }
    public void setWeightUnitId(Long weightUnitId) 
    {
        this.weightUnitId = weightUnitId;
    }

    public Long getWeightUnitId() 
    {
        return weightUnitId;
    }
    public void setEnableChild(String enableChild) 
    {
        this.enableChild = enableChild;
    }

    public String getEnableChild() 
    {
        return enableChild;
    }
    public void setChildboxName(String childboxName) 
    {
        this.childboxName = childboxName;
    }

    public String getChildboxName() 
    {
        return childboxName;
    }
    public void setChildboxMaterial(String childboxMaterial) 
    {
        this.childboxMaterial = childboxMaterial;
    }

    public String getChildboxMaterial() 
    {
        return childboxMaterial;
    }
    public void setChildboxSpec(String childboxSpec) 
    {
        this.childboxSpec = childboxSpec;
    }

    public String getChildboxSpec() 
    {
        return childboxSpec;
    }
    public void setChildLongnum(Integer childLongnum) 
    {
        this.childLongnum = childLongnum;
    }

    public Integer getChildLongnum() 
    {
        return childLongnum;
    }
    public void setChildWidenum(Integer childWidenum) 
    {
        this.childWidenum = childWidenum;
    }

    public Integer getChildWidenum() 
    {
        return childWidenum;
    }
    public void setChildHighnum(Integer childHighnum) 
    {
        this.childHighnum = childHighnum;
    }

    public Integer getChildHighnum() 
    {
        return childHighnum;
    }
    public void setChildUnitId(Long childUnitId) 
    {
        this.childUnitId = childUnitId;
    }

    public Long getChildUnitId() 
    {
        return childUnitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patternName", getPatternName())
            .append("boxName", getBoxName())
            .append("boxMaterial", getBoxMaterial())
            .append("boxSpec", getBoxSpec())
            .append("boxLength", getBoxLength())
            .append("boxWidth", getBoxWidth())
            .append("boxHeight", getBoxHeight())
            .append("weightUnitId", getWeightUnitId())
            .append("enableChild", getEnableChild())
            .append("childboxName", getChildboxName())
            .append("childboxMaterial", getChildboxMaterial())
            .append("childboxSpec", getChildboxSpec())
            .append("childLongnum", getChildLongnum())
            .append("childWidenum", getChildWidenum())
            .append("childHighnum", getChildHighnum())
            .append("childUnitId", getChildUnitId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

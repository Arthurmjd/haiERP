package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品图纸对象 eng_product_drawing
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public class EngProductDrawing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private Long productId;

    /** 图纸描述 */
    @Excel(name = "图纸描述")
    private String description;

    /** 图纸链接 */
    @Excel(name = "图纸链接")
    private String drawingUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setDrawingUrl(String drawingUrl) 
    {
        this.drawingUrl = drawingUrl;
    }

    public String getDrawingUrl() 
    {
        return drawingUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productId", getProductId())
            .append("description", getDescription())
            .append("drawingUrl", getDrawingUrl())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

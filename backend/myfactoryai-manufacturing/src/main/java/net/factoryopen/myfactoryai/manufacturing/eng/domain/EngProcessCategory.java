package net.factoryopen.myfactoryai.manufacturing.eng.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 制程数据对象 eng_process_category
 * 
 * @author generator
 * @date 2026-02-20
 */
public class EngProcessCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 制程段id */
    private Long id;

    /** 制程段名 */
    @Excel(name = "制程段名")
    private String categoryName;

    /** 制程段描述 */
    @Excel(name = "制程段描述")
    private String categoryDesc;

    /** 序号 */
    @Excel(name = "序号")
    private Integer categorySeq;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setCategoryDesc(String categoryDesc) 
    {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryDesc() 
    {
        return categoryDesc;
    }
    public void setCategorySeq(Integer categorySeq) 
    {
        this.categorySeq = categorySeq;
    }

    public Integer getCategorySeq() 
    {
        return categorySeq;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryName", getCategoryName())
            .append("categoryDesc", getCategoryDesc())
            .append("categorySeq", getCategorySeq())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

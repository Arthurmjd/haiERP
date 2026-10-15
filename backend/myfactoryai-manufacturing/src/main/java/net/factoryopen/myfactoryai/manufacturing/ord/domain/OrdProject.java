package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户项目预配对象 ord_project
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class OrdProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 项目名 */
    @Excel(name = "项目名")
    private String projectName;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String projectDesc;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setProjectDesc(String projectDesc) 
    {
        this.projectDesc = projectDesc;
    }

    public String getProjectDesc() 
    {
        return projectDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectName", getProjectName())
            .append("projectDesc", getProjectDesc())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

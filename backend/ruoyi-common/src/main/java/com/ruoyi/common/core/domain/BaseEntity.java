package com.ruoyi.common.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * Entity基类
 * 
 * @author ruoyi
 */
public class BaseEntity implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;

    private Boolean pageEnable;

    private Long id;

    /** 搜索值 */
    @JsonIgnore
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Boolean getPageEnable() {
        return pageEnable;
    }

    public void setPageEnable(Boolean pageEnable) {
        this.pageEnable = pageEnable;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void refreshCreatingStamp(){
        this.createBy= SecurityUtils.getUsername();
        this.createTime = DateUtils.getNowDate();
        this.updateBy= SecurityUtils.getUsername();
        this.updateTime = DateUtils.getNowDate();
    }

    public void refreshUpdatingStamp(){
        this.updateBy= SecurityUtils.getUsername();
        this.updateTime = DateUtils.getNowDate();
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

    public boolean equals(BaseEntity obj) {
        return this.id==obj.getId();
    }
}

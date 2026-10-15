package net.factoryopen.myfactoryai.manufacturing.cha.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 线索拜访对象 cha_leads_visiting
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public class ChaLeadsVisiting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 线索 */
    @Excel(name = "线索")
    private Long leadId;

    /** 项序 */
    @Excel(name = "项序")
    private Integer seqNo;

    /** 拜访日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拜访日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitDay;

    /** 接待级别 */
    @Excel(name = "接待级别")
    private String talkLevel;

    /** 拜访记录 */
    @Excel(name = "拜访记录")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLeadId(Long leadId) 
    {
        this.leadId = leadId;
    }

    public Long getLeadId() 
    {
        return leadId;
    }
    public void setSeqNo(Integer seqNo) 
    {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() 
    {
        return seqNo;
    }
    public void setVisitDay(Date visitDay) 
    {
        this.visitDay = visitDay;
    }

    public Date getVisitDay() 
    {
        return visitDay;
    }
    public void setTalkLevel(String talkLevel) 
    {
        this.talkLevel = talkLevel;
    }

    public String getTalkLevel() 
    {
        return talkLevel;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("leadId", getLeadId())
            .append("seqNo", getSeqNo())
            .append("visitDay", getVisitDay())
            .append("talkLevel", getTalkLevel())
            .append("description", getDescription())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package net.factoryopen.myfactoryai.manufacturing.tool.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 单据规则对象 mdt_billcode_rule
 *
 * @author admin
 * @date 2022-07-29
 */
public class BillManagerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 单码
     */
    private String billCode;

    /**
     * 单名
     */
    private String billName;

    /**
     * 启用规则否
     */
    private String disableRule;

    /**
     * 启用单码否
     */
    private String disableCode;

    /**
     * 启用前缀否
     */
    private String disablePrefix;

    /**
     * 前缀字符
     */
    private String prefixChars;

    /**
     * 启用时期否
     */
    private String disablePeriod;

    /**
     * 时期样式
     */
    private String periodPattern;

    /**
     * 最后日期
     */
    private String lastDate;

    /**
     * 最后时间
     */
    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    /**
     * 启用数字否
     */
    private String disableNumber;

    /**
     * 数字位数
     */
    private Integer numberSize;

    /**
     * 后数
     */
    private Integer nextNumber;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillName() {
        return billName;
    }

    public void setDisableRule(String disableRule) {
        this.disableRule = disableRule;
    }

    public String getDisableRule() {
        return disableRule;
    }

    public void setDisableCode(String disableCode) {
        this.disableCode = disableCode;
    }

    public String getDisableCode() {
        return disableCode;
    }

    public void setDisablePrefix(String disablePrefix) {
        this.disablePrefix = disablePrefix;
    }

    public String getDisablePrefix() {
        return disablePrefix;
    }

    public void setPrefixChars(String prefixChars) {
        this.prefixChars = prefixChars;
    }

    public String getPrefixChars() {
        return prefixChars;
    }

    public void setDisablePeriod(String disablePeriod) {
        this.disablePeriod = disablePeriod;
    }

    public String getDisablePeriod() {
        return disablePeriod;
    }

    public void setPeriodPattern(String periodPattern) {
        this.periodPattern = periodPattern;
    }

    public String getPeriodPattern() {
        return periodPattern;
    }

    public void setDisableNumber(String disableNumber) {
        this.disableNumber = disableNumber;
    }

    public String getDisableNumber() {
        return disableNumber;
    }

    public void setNumberSize(Integer numberSize) {
        this.numberSize = numberSize;
    }

    public Integer getNumberSize() {
        return numberSize;
    }

    public void setNextNumber(Integer nextNumber) {
        this.nextNumber = nextNumber;
    }

    public Integer getNextNumber() {
        return nextNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("billCode", getBillCode())
                .append("billName", getBillName())
                .append("disableRule", getDisableRule())
                .append("disableCode", getDisableCode())
                .append("lastDate", getLastDate())
                .append("disablePrefix", getDisablePrefix())
                .append("prefixChars", getPrefixChars())
                .append("disablePeriod", getDisablePeriod())
                .append("periodPattern", getPeriodPattern())
                .append("disableNumber", getDisableNumber())
                .append("numberSize", getNumberSize())
                .append("nextNumber", getNextNumber())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

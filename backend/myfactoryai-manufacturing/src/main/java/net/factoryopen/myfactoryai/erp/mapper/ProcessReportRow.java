package net.factoryopen.myfactoryai.erp.mapper;

import java.time.LocalDate;

public class ProcessReportRow {
    private Long reportId;
    private String key;
    private Long processId;
    private Long woId;
    private Long skuId;
    private Long empId;
    private Integer outsourced;
    private Integer good;
    private Integer bad;
    private LocalDate date;
    private String username;
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public Long getProcessId() { return processId; }
    public void setProcessId(Long processId) { this.processId = processId; }
    public Long getWoId() { return woId; }
    public void setWoId(Long woId) { this.woId = woId; }
    public Long getSkuId() { return skuId; }
    public void setSkuId(Long skuId) { this.skuId = skuId; }
    public Long getEmpId() { return empId; }
    public void setEmpId(Long empId) { this.empId = empId; }
    public Integer getOutsourced() { return outsourced; }
    public void setOutsourced(Integer outsourced) { this.outsourced = outsourced; }
    public Integer getGood() { return good; }
    public void setGood(Integer good) { this.good = good; }
    public Integer getBad() { return bad; }
    public void setBad(Integer bad) { this.bad = bad; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}

package net.factoryopen.myfactoryai.manufacturing.ord.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细Excel导入对象
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ExcelImportInteface{

    public String getSeqNo();

    public void setSeqNo(String seqNo);

    public String getProductCode();

    public void setProductCode(String productCode);

    public String getProductName();

    public void setProductName(String productName);

    public String getProductSpec();

    public void setProductSpec(String productSpec);

    public String getDrawNo();

    public void setDrawNo(String drawNo);

    public String getCustomerProductNo();

    public void setCustomerProductNo(String customerProductNo) ;

    public String getAbroadProductNo();

    public void setAbroadProductNo(String abroadProductNo) ;

    public String getUnit();

    public void setUnit(String unit);

    public BigDecimal getQuantity();

    public void setQuantity(BigDecimal quantity);

    public BigDecimal getPrice();

    public void setPrice(BigDecimal price);

    public String getContractNo();

    public void setContractNo(String contractNo);

    public String getCustomerName();

    public void setCustomerName(String customerName);

    public Date getOrderDay();

    public void setOrderDay(Date orderDay);

    public Date getNeedDay();

    public void setNeedDay(Date needDay);

    public String getRemark();

    public void setRemark(String remark);
}

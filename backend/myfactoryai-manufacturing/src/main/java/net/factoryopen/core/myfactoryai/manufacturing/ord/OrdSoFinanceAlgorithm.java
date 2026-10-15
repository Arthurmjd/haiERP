package net.factoryopen.core.myfactoryai.manufacturing.ord;

import net.factoryopen.core.common.exception.NumberNotEnoughException;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoFinance;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoFinanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 销售明细ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-25
 */
@Service
public class OrdSoFinanceAlgorithm
{
    @Autowired
    private OrdSoFinanceMapper sofinanceMapper;

    /**
     * 开票登记
     *
     * @param soFinance 订单明细
     * @return 结果
     */
    public synchronized int registerInvoice(OrdSoFinance soFinance) throws Exception{
        double quantityInvoicing =0;
        double amountInvoicing =0;

        //取开票数量
        Object obj = soFinance.getParams().get("quantityInvoicing");
        if (obj.getClass().equals(String.class)){
            quantityInvoicing = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            quantityInvoicing = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            quantityInvoicing = ((Integer)obj).doubleValue();
        }

        //计算开票金额
        OrdSoFinance dbSofinance = sofinanceMapper.selectOrdSoFinanceById(soFinance.getId());
        if (dbSofinance.getQuantityOrder().doubleValue()<dbSofinance.getQuantityInvoiced().doubleValue()+quantityInvoicing){
            String msg = "开票总数超过下单数";
            NumberNotEnoughException exception = new NumberNotEnoughException(msg);
            throw exception;
        }else{
            amountInvoicing=quantityInvoicing * dbSofinance.getPrice().doubleValue();
            double quantityInvoiced = quantityInvoicing + dbSofinance.getQuantityInvoiced().doubleValue();
            double amountInvoiced = amountInvoicing + dbSofinance.getAmountInvoiced().doubleValue();

            dbSofinance.setQuantityInvoiced(new BigDecimal(quantityInvoiced).setScale(2,BigDecimal.ROUND_HALF_UP));
            dbSofinance.setAmountInvoiced(new BigDecimal(amountInvoiced).setScale(2,BigDecimal.ROUND_HALF_UP));
            dbSofinance.refreshCreatingStamp();
            sofinanceMapper.updateOrdSoFinance(dbSofinance);
        }

        return 1;
    }

}

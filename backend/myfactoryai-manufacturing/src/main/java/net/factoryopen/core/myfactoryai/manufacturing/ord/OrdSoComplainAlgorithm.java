package net.factoryopen.core.myfactoryai.manufacturing.ord;

import java.math.BigDecimal;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoComplainMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoComplain;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdComplainMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdComplain;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.QuantityUnavailableException;

/**
 * 订单客诉登记ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdSoComplainAlgorithm
{
    @Autowired(required = false)
    private OrdSoComplainMapper soitemMapper;

    @Autowired(required = false)
    private OrdComplainMapper complainMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 客诉
     *
     * @param ordSoComplain 客诉
     * @return 结果
     */
    public int complainReg(OrdSoComplain ordSoComplain) throws Exception{
        // 取UI的form变量
        Integer quantity = Integer.parseInt((String) ordSoComplain.getParams().get("quantity"));
        Integer complainType =(Integer) ordSoComplain.getParams().get("complainType");
        //Integer complainType = Integer.parseInt((String) ordSoComplain.getParams().get("complainType"));
        String gdNo = (String) ordSoComplain.getParams().get("gdNo");
        String lotNo = (String) ordSoComplain.getParams().get("lotNo");
        String complainBy = (String) ordSoComplain.getParams().get("complainBy");
        String complainPhone = (String) ordSoComplain.getParams().get("complainPhone");
        String complainDay = (String) ordSoComplain.getParams().get("complainDay");

        // 判异，排除数量和状态的错配
        ordSoComplain = soitemMapper.selectOrdSoComplainById(ordSoComplain.getId());
        OrdSo so = soMapper.selectOrdSoById(ordSoComplain.getSoId());
        if (ordSoComplain.getStatus().intValue() < 4 || ordSoComplain.getStatus().intValue()==8) {
            String msg = "订单为[" + so.getBillNo() + "],项次为["+ordSoComplain.getSeqNo()+"]的订单明细状态不适合登记客诉";
            throw new StatusUnavailableException(msg);
        }
        if (ordSoComplain.getQuantityDelivered().doubleValue()<=0) {
            String msg = "订单为[" + so.getBillNo() + "],项次为["+ordSoComplain.getSeqNo()+"]的订单明细不曾发货";
            throw new QuantityUnavailableException(msg);
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno("SC");
        billcodeManager.updateBillSeq("SC");

        //创建客诉单，并设置关键值
        OrdComplain complain = new OrdComplain();
        complain.setBillNo(billNo);
        complain.setStatus(BillStatus.BASE_DRAFT);//设为“底稿”
        complain.setQuantity(new BigDecimal(quantity.intValue()));
        complain.setComplainType(complainType);
        complain.setComplainDay(DateUtils.parseDate(complainDay));
        complain.setGdNo(gdNo);
        complain.setLotNo(lotNo);
        complain.setComplainBy(complainBy);
        complain.setComplainPhone(complainPhone);
        //从工单里复制批次字段值
        complain.setProductId(ordSoComplain.getProductId());
        complain.setProductName(ordSoComplain.getProductName());
        complain.setProductSpec(ordSoComplain.getProductSpec());
        complain.setUnitId(ordSoComplain.getUnitId());
        complain.setPrice(ordSoComplain.getPrice());
        complain.setAmount(new BigDecimal(ordSoComplain.getPrice().doubleValue()*quantity.intValue()));
        complain.setSoItemId(ordSoComplain.getId());
        complain.setSoId(ordSoComplain.getSoId());
        //保存
        complain.refreshCreatingStamp();
        complainMapper.insertOrdComplain(complain);

        return 1;
    }

}

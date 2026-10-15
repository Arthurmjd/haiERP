package net.factoryopen.core.myfactoryai.manufacturing.exe;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeExceptionReqMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeExceptionReq;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 工艺异常登记ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class ExeExceptionReqAlgorithm
{
    @Autowired(required = false)
    private ExeExceptionReqMapper exeExceptionReqMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 请判
       *
       * @param exeExceptionReq 请判
       * @return 结果
       */
      public int requestDeal(ExeExceptionReq exeExceptionReq) throws Exception{
          // 取UI的form变量
          //String delieryDay = (String) plnMlDispatching.getParams().get("delieryDay");
          //Integer quantityAssigning = Integer.parseInt((String) plnMlDispatching.getParams().get("quantityAssigning"));
          //Integer priority = plnMlDispatching.getPriority();

          // 判异，排除数量和状态的错配
          //PlnWo wo = woMapper.selectPlnWoById(plnMlDispatching.getId());
          //if (wo.getQuantityPlanned().doubleValue()-wo.getQuantityAssigned().doubleValue()<=0) {
          //    String woName = wo.getBillNo();
          //     String msg = "工单[" + woName + "]的可派工数量不大于0";
          //    throw new QuantityUnavailableException(msg);
          //}
          //if (plan.getStatus().intValue() != 4) {
          //    OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //    String msg = "发货计划[" + so.getBillNo() + "]的状态不是[4-生效]";
          //    throw new StatusUnavailableException(msg);
          //}
          //if (DateUtils.parseDate(deliveryDay).before(DateUtils.getNowDate())) {
          //    OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //    String msg = "发货计划[" + so.getBillNo() + "]的交期早于现在";
          //    throw new DateUnavailableException(msg);
          //}

          // 取得并更新单据规则
          //String billNo = billcodeManager.getNextBillno("ML");
          //billcodeManager.updateBillSeq("ML");

          //创建制造批，并设置特殊值
          //PlnMl ml = new PlnMl();
          //ml.setBillNo(billNo);
          //ml.setStatus(4);//设为“生效”
          //ml.setProcessProgress(0);//设为“待接收”);
          // 设置批次派工量
          //ml.setQuantityDispatched(new BigDecimal(quantityAssigning.intValue()));
          // 若form传参不为空则赋值，否则从工单复制字段值
          //if(delieryDay!=null) ml.setDeliveryDay(DateUtils.parseDate(delieryDay));
          //else ml.setDeliveryDay(wo.getPromiseDay());
          //if(priority!=null) ml.setPriority(priority);
          //else ml.setPriority(wo.getPriority());
          //设置制造批次类型
          //switch (wo.getWoType()) {
          //    case 0: ml.setMlType(0); break; //wo普通-> ml普通
          //    case 1:                  break; //wo委外-> 无
          //    case 2: ml.setMlType(1); break; //wo首制-> ml首制
          //    case 3: ml.setMlType(2); break; //wo试制-> ml试制
          //     case 4: ml.setMlType(3); break; //wo重制-> ml重制
          //    case 5: ml.setMlType(0); break; //wo拆解-> ml普通
          //}
          //从工单里复制批次字段值
          //ml.setProductId(wo.getProductId());
          //ml.setProductName(wo.getProductName());
          //ml.setProductSpec(wo.getProductSpec());
          //ml.setUnitId(wo.getUnitId());
          //ml.setProjectId(wo.getProjectId());
          //ml.setLineId(wo.getLineId());
          //ml.setWoId(wo.getId());
          //保存批次
          //mlMapper.insertPlnMl(ml);

          // 更改原工单的状态和派工量
          //wo.setStatus(6);//设为“在制”
          //wo.setQuantityAssigned(wo.getQuantityAssigned().add(new BigDecimal(quantityAssigning.intValue())));//派工量增加新派量
          //woMapper.updatePlnWo(wo);

          return 1;
      }

}

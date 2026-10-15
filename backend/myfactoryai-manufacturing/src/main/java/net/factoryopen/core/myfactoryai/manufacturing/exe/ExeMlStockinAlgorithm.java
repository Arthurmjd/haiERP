package net.factoryopen.core.myfactoryai.manufacturing.exe;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlStockinMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlStockin;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 产成品入库ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ExeMlStockinAlgorithm
{
    @Autowired(required = false)
    private ExeMlStockinMapper exeMlStockinMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 入库
       *
       * @param ids id集合
       * @return 结果
       */
      public int productStockin(Long[] ids) throws Exception{
          // 判异，排除客户和状态的錯配
          //for (int i = 0; i < ids.length; i++) {
          //    DelDeliveryPlan plan = planMapper.selectDelDeliveryPlanById(ids[i]);

          //    if (!plan.getCustomerId().equals(customerId)) {
          //        OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //        ChaCustomer customer = customerMapper.selectChaCustomerById(customerId);
          //        String msg = "发货计划[" + so.getBillNo() + "]的客户不是[" + customer.getCustomerName() + "]";
          //        throw new CustomerUnavailableException(msg);
          //    }
          //    if (plan.getStatus().intValue() != 4) {
          //        OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //        String msg = "发货计划[" + so.getBillNo() + "]的状态不是[4-生效]";
          //        throw new StatusUnavailableException(msg);
          //    }
          //    if (DateUtils.parseDate(deliveryDay).before(DateUtils.getNowDate())) {
          //        OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //        String msg = "发货计划[" + so.getBillNo() + "]的交期早于现在";
          //        throw new DateUnavailableException(msg);
          //    }
          //}

          // 取得并更新单据规则
          //String billNo = billcodeManager.getNextBillno("SD");
          //billcodeManager.updateBillSeq("SD");

          // 创建发货单头
          //DelDeliveryOrder order = new DelDeliveryOrder();
          //order.setBillNo(billNo);
          //orderMapper.insertDelDeliveryOrder(order);

          // 逐一取原发货计划，建发货单明细
          //for (int i = 0; i < ids.length; i++) {
          //    DelDeliveryPlan plan = planMapper.selectDelDeliveryPlanById(ids[i]);

          //    //创建并保存发货单身
          //    DelGdItem orderItem = new DelGdItem();
          //    orderItem.setGdId(order.getId());
          //    orderItem.setSeqNo(i+1);
          //    orderItemMapper.insertDelGdItem(orderItem);

          //    // 将原发货计划的状态设为“在处理”
          //    plan.setStatus(6);
          //    planMapper.updateDelDeliveryPlan(plan);
          //}

          return 1;
      }

}

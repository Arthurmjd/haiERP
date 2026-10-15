package net.factoryopen.core.myfactoryai.manufacturing.sup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialPurchasingType;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInfo;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInfoMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchasingPlan;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchasingPlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseReqAuditMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseReqAudit;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 请购审核ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SupPurchaseReqAuditAlgorithm
{
    @Autowired(required = false)
    private SupPurchaseRequestMapper requestMapper;

    @Autowired(required = false)
    private SupPurchaseRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private SupPurchasingPlanMapper planMapper;

    @Autowired(required = false)
    private EngMaterialInfoMapper materialMapper;

    @Autowired(required = false)
    private DelOsplanMapper osplanMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 转计划
       *
       * @param ids id集合
       * @return 结果
       */
      public int toPlan(Long[] ids) throws Exception{
          // 判异，排除客户和状态的錯配
          for (int i = 0; i < ids.length; i++) {
              SupPurchaseRequest request = requestMapper.selectSupPurchaseRequestById(ids[i]);
              if (request.getStatus().intValue() != BillStatus.BASE_AUDIT_PASS) {
                  String msg = "发货计划[" + request.getBillNo() + "]的状态不是[3-已审]";
                  throw new StatusUnavailableException(msg);
              }
          }

          // 逐一取原发货计划，建发货单明细
          for (int i = 0; i < ids.length; i++) {
              SupPurchaseRequest request = requestMapper.selectSupPurchaseRequestById(ids[i]);
              List<SupPurchaseRequestItem> requestItemList = requestItemMapper.selectSupPurchaseRequestItemListByPrId(request.getId());
              if (requestItemList==null || requestItemList.isEmpty()) continue;

              for(SupPurchaseRequestItem item: requestItemList){
                  // 取得并更新单据规则
                  String billNo = billcodeManager.getNextBillno("PP");
                  billcodeManager.updateBillSeq("PP");

                  //获取物料（价格）信息
                  EngMaterialInfo material = materialMapper.selectEngMaterialInfoById(item.getMaterialId());

                  //创建计划
                  SupPurchasingPlan plan = new SupPurchasingPlan();
                  plan.setBillNo(billNo);
                  plan.setStatus(BillStatus.BASE_ENABLE);
                  plan.setMaterialId(item.getMaterialId());
                  plan.setMaterialCode(item.getMaterialCode());
                  plan.setMaterialName(item.getMaterialName());
                  plan.setMaterialSpec(item.getMaterialSpec());
                  plan.setDrawNo(item.getDrawNo());
                  plan.setUnitId(item.getUnitId());
                  plan.setQuantity(item.getQuantity());
                  plan.setPrice(material.getPriceLast());
                  plan.setAmount(new BigDecimal(plan.getQuantity().doubleValue()*plan.getPrice().doubleValue()));
                  plan.setRequireDay(item.getRequireDay());
                  plan.setPriority(item.getPriority());
                  plan.setCustomerId(item.getCustomerId());
                  plan.setCustomerName(item.getCustomerName());
                  plan.setPriceSale(item.getPriceSale());
                  plan.setPurchasingType(request.getPurchasingType());
                  plan.setSourceBillId(item.getRequestBillId());
                  plan.setSourceBillNo(item.getRequestBillNo());
                  plan.setPrId(item.getPrId());
                  plan.setPrItemId(item.getId());
                  plan.setConsumeType(item.getConsumeType());
                  plan.setEnableMust(item.getEnableMust());
                  plan.setEnableReplace(item.getEnableReplace());
                  plan.setRemark(item.getRemark());
                  //保存计划
                  plan.refreshCreatingStamp();
                  planMapper.insertSupPurchasingPlan(plan);

                  //依采购类型变更来源单据的状态
                  switch (request.getPurchasingType()){
                      case MaterialPurchasingType.SUBCONTRACT:
                          DelOsplan osplan = osplanMapper.selectDelOsplanById(plan.getSourceBillId());
                          osplan.setStatus(BillStatus.ACTIVE_PLANNING);
                          osplanMapper.updateDelOsplan(osplan);
                          break;
                  }
              }

              request.setStatus(BillStatus.FINISH);
              requestMapper.updateSupPurchaseRequest(request);
          }

          return 1;
      }

}

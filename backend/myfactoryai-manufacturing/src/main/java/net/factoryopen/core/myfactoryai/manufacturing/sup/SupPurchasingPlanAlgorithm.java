package net.factoryopen.core.myfactoryai.manufacturing.sup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.exception.DataInconsistentException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialPurchasingType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasInvoicePatternMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInfo;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInfoMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupSupplierMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchasingPlanMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchasingPlan;

import net.factoryopen.core.common.exception.StatusUnavailableException;

/**
 * 采购计划ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SupPurchasingPlanAlgorithm
{
    @Autowired(required = false)
    private SupPurchasingPlanMapper planMapper;

    @Autowired(required = false)
    private SupPoMapper poMapper;

    @Autowired(required = false)
    private SupPoItemMapper poitemMapper;

    @Autowired(required = false)
    private SupSupplierMapper supplierMapper;

    @Autowired(required = false)
    private EngMaterialInfoMapper materialMapper;

    @Autowired(required = false)
    private DelOsplanMapper osplanMapper;

    @Autowired(required = false)
    private BasInvoicePatternMapper invoiceMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 转单
       *
       * @param supPurchasingPlan 转单
       * @return 结果
       */
      public int toPo(Long[] ids, SupPurchasingPlan supPurchasingPlan) throws Exception{
          // 取UI的form变量
          Integer purchasingType = (Integer) supPurchasingPlan.getParams().get("purchasingType");
          Integer priority = (Integer) supPurchasingPlan.getParams().get("priority");
          Long supplierId =Integer.toUnsignedLong (((Integer) supPurchasingPlan.getParams().get("supplierId")).intValue());
          Long invoicePatternId =Integer.toUnsignedLong (((Integer) supPurchasingPlan.getParams().get("invoicePatternId")).intValue());
          Long payPatternId =Integer.toUnsignedLong (((Integer) supPurchasingPlan.getParams().get("payPatternId")).intValue());
          Long currencyId =Integer.toUnsignedLong (((Integer) supPurchasingPlan.getParams().get("currencyId")).intValue());
          Long shipmentTypeId =Integer.toUnsignedLong (((Integer) supPurchasingPlan.getParams().get("shipmentTypeId")).intValue());
          Date orderDay = DateUtils.parseDate(supPurchasingPlan.getParams().get("orderDay"));

          Double exchangeRate =0d;
          if (supPurchasingPlan.getParams().get("exchangeRate").getClass().equals(Integer.class)){
              exchangeRate = Double.valueOf(((Integer)supPurchasingPlan.getParams().get("exchangeRate")).doubleValue());
          }else if (supPurchasingPlan.getParams().get("exchangeRate").getClass().equals(Double.class)){
              exchangeRate = (Double) supPurchasingPlan.getParams().get("exchangeRate");
          }
          Long contactId =null;
          if(supPurchasingPlan.getParams().get("contactId")!=null) {
              Integer.toUnsignedLong (((Integer) supPurchasingPlan.getParams().get("contactId")).intValue());
          }

          // 判异，排除客户和状态的錯配
          for (int i = 0; i < ids.length; i++) {
              SupPurchasingPlan plan = planMapper.selectSupPurchasingPlanById(ids[i]);
              if (!plan.getStatus().equals(BillStatus.BASE_ENABLE)) {
                  String msg = "采购计划[" + plan.getBillNo() + "]的状态 不是 [4-生效]！";
                  throw new StatusUnavailableException(msg);
              }
              if (!plan.getPurchasingType().equals(purchasingType)) {
                  String msg = "设置的采购类型 不同于 采购计划[" + plan.getBillNo() + "]的采购类型！";
                  throw new DataInconsistentException(msg);
              }
              if (plan.getPriority().compareTo(priority)<0) {
                  String msg = "设置的优先级 低于 采购计划[" + plan.getBillNo() + "]优先级！";
                  throw new DataInconsistentException(msg);
              }
          }

          // 取更PO单号
          String billNo = billcodeManager.getNextBillno("PO");
          billcodeManager.updateBillSeq("PO");
          // 创建发货单头
          SupPo po = new SupPo();
          //设置必要字段
          po.setBillNo(billNo);
          po.setStatus(BillStatus.BASE_AUDIT_PASS);
          po.setPurchasingType(purchasingType);
          po.setSupplierId(supplierId);
          //基础信息
          po.setInvoicePatternId(invoicePatternId);
          po.setCurrencyId(currencyId);
          po.setExchangeRate(new BigDecimal(exchangeRate));
          po.setPayPatternId(payPatternId);
          po.setShipmentTypeId(shipmentTypeId);
          //非必填字段
          po.setOrderDay(orderDay);
          po.setContactId(contactId);
          po.setBuyerId(SecurityUtils.getUserId());
          po.setBuyerDeptId(SecurityUtils.getDeptId());
          //保存
          po.refreshCreatingStamp();
          poMapper.insertSupPo(po);

          // 逐一取原发货计划，建发货单明细
          double amount = 0;
          for (int i = 0; i < ids.length; i++) {
              SupPurchasingPlan plan = planMapper.selectSupPurchasingPlanById(ids[i]);
              //获取物料（价格）信息
              EngMaterialInfo material = materialMapper.selectEngMaterialInfoById(plan.getMaterialId());

              //创建并保存发货单身
              SupPoItem poitem = new SupPoItem();
              //关键字段
              poitem.setPoId(po.getId());
              poitem.setSeqNo(i+1);
              poitem.setStatus(BillStatus.BASE_AUDIT_PASS);
              //复杂字段
              poitem.setMaterialId(plan.getMaterialId());
              poitem.setMaterialCode(plan.getMaterialCode());
              poitem.setMaterialName(plan.getMaterialName());
              poitem.setMaterialSpec(plan.getMaterialSpec());
              poitem.setDrawNo(plan.getDrawNo());
              poitem.setUnitId(plan.getUnitId());
              //数量金额
              poitem.setQuantityOrder(plan.getQuantity());
              poitem.setPrice(material.getPriceLast());
              poitem.setAmount(new BigDecimal(poitem.getQuantityOrder().doubleValue()*poitem.getPrice().doubleValue()));
              //复杂字段
              poitem.setRequireDay(plan.getRequireDay());
              poitem.setCustomerId(plan.getCustomerId());
              poitem.setCustomerName(plan.getCustomerName());
              poitem.setPriceSale(plan.getPriceSale());
              poitem.setSourceBillId(plan.getSourceBillId());
              poitem.setSourceBillNo(plan.getSourceBillNo());
              poitem.setRemark(plan.getRemark());
              //保存
              poitemMapper.insertSupPoItem(poitem);

              BasInvoicePattern invoicePattern = invoiceMapper.selectBasInvoicePatternById(po.getInvoicePatternId());

              //PO金额合计
              amount+=poitem.getQuantityOrder().doubleValue()*poitem.getPrice().doubleValue();
              po.setAmountUntaxed(new BigDecimal(amount));
              po.setAmountTaxed(new BigDecimal(amount*(1+invoicePattern.getPatternRate().doubleValue())));
              poMapper.updateSupPo(po);

              //采购计划状态设为结案
              plan.setStatus(BillStatus.FINISH);
              planMapper.updateSupPurchasingPlan(plan);

              //依采购类型变更来源单据的发货指派数量
              switch (purchasingType){
                  case MaterialPurchasingType.SUBCONTRACT:
                      DelOsplan osplan = osplanMapper.selectDelOsplanById(plan.getSourceBillId());
                      osplan.setQuantityDispatched(new BigDecimal(osplan.getQuantityDispatched().doubleValue()+poitem.getQuantityOrder().doubleValue()));
                      osplan.setStatus(BillStatus.ACTIVE_PROCESSING);
                      osplanMapper.updateDelOsplan(osplan);
                      break;
              }
          }

          return 1;
      }

}

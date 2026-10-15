package net.factoryopen.core.myfactoryai.manufacturing.del;

import java.math.BigDecimal;
import java.util.Date;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryPlanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryOrderMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelGdItemMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 发货计划维护ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-06-27
 */
@Service
public class DelDeliveryPlanAlgorithm
{
    @Autowired(required = false)
    private DelDeliveryPlanMapper planMapper;

    @Autowired(required = false)
    private DelDeliveryOrderMapper orderMapper;

    @Autowired(required = false)
    private DelGdItemMapper orderItemMapper;

    @Autowired(required = false)
    private ChaCustomerMapper customerMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soitemMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 转单
     *
     * @param delDeliveryPlan 转单
     * @return 结果
     */
    public int billDelivery(Long[] ids, DelDeliveryPlan delDeliveryPlan) throws Exception{
        // 取UI的form变量
        Long customerId = delDeliveryPlan.getCustomerId();
        String deliveryDay = (String) delDeliveryPlan.getParams().get("deliveryDay");
        Long projectId = delDeliveryPlan.getProjectId();
        Long salesId = delDeliveryPlan.getSalesId();

        // 判异，排除客户和状态的錯配
        for (int i = 0; i < ids.length; i++) {
            DelDeliveryPlan plan = planMapper.selectDelDeliveryPlanById(ids[i]);

            OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
            if (!plan.getCustomerId().equals(customerId)) {
                ChaCustomer customer = customerMapper.selectChaCustomerById(customerId);
                String msg = "发货计划[" + so.getBillNo() + "]的客户不是[" + customer.getCustomerName() + "]";
                throw new CustomerUnavailableException(msg);
            }
            if (plan.getStatus().intValue() != 4) {
                String msg = "发货计划[" + so.getBillNo() + "]的状态不是[4-生效]";
                throw new StatusUnavailableException(msg);
            }
            if (DateUtils.parseDate(deliveryDay).before(DateUtils.getNowDate())) {
                String msg = "发货计划[" + so.getBillNo() + "]的交期早于现在";
                throw new DateUnavailableException(msg);
            }
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno(BillType.DELIVERY_ORDER);
        billcodeManager.updateBillSeq(BillType.DELIVERY_ORDER);

        // 创建发货单头
        DelDeliveryOrder order = new DelDeliveryOrder();
        order.setBillNo(billNo);
        order.setStatus(BillStatus.BASE_ENABLE);
        order.setCustomerId(customerId);
        order.setDeliveryDay(DateUtils.parseDate(deliveryDay));
        order.setProjectId(projectId);
        order.setSalesId(salesId);
        //保存
        order.refreshCreatingStamp();
        orderMapper.insertDelDeliveryOrder(order);

        // 逐一取原发货计划，建发货单明细
        for (int i = 0; i < ids.length; i++) {
            DelDeliveryPlan plan = planMapper.selectDelDeliveryPlanById(ids[i]);

            //创建并保存发货单身
            DelGdItem orderItem = new DelGdItem();
            orderItem.setGdId(order.getId());
            orderItem.setSeqNo(i+1);
            orderItem.setDpId(plan.getId());
            orderItem.setSoId(plan.getSoId());
            orderItem.setSoItemId(plan.getSoItemId());
            orderItem.setProductId(plan.getProductId());
            orderItem.setProductCode(plan.getProductCode());
            orderItem.setProductName(plan.getProductName());
            orderItem.setProductSpec(plan.getProductSpec());
            orderItem.setCustomerProductNo(plan.getCustomerProductNo());
            orderItem.setAbroadProductNo(null);
            orderItem.setUnitId(plan.getUnitId());
            orderItem.setQuantity(plan.getQuantityPlanned());
            orderItem.setPrice(plan.getPrice());
            orderItem.setDiscountPrice(plan.getDiscountPrice());
            orderItem.setDiscountAmount(plan.getDiscountRate());
            orderItem.setAmount(plan.getAmount());
            //创建戳
            orderItem.setCreateBy(SecurityUtils.getUsername());
            orderItem.setCreateTime(DateUtils.getNowDate());
            orderItem.setUpdateBy(SecurityUtils.getUsername());
            orderItem.setUpdateTime(DateUtils.getNowDate());
            //保存
            orderItemMapper.insertDelGdItem(orderItem);

            // 将原发货计划的状态设为“处理开始”，意为“已转发货单”
            plan.setStatus(BillStatus.ACTIVE_STARTED);
            plan.refreshUpdatingStamp();
            planMapper.updateDelDeliveryPlan(plan);

            //更新订单明细的发货计划量、状态
            OrdSoItem soitem = soitemMapper.selectOrdSoItemById(plan.getSoItemId());
            if (soitem.getStatus()==BillStatus.ACTIVE_PLANNING){soitem.setStatus(BillStatus.ACTIVE_PROCESSING);
                soitem.refreshUpdatingStamp();
                soitemMapper.updateOrdSoItem(soitem);
            }
            //更新订单的状态
            OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
            if(so.getStatus()==BillStatus.ACTIVE_PLANNING) {
                so.setStatus(BillStatus.ACTIVE_PROCESSING);
                so.refreshUpdatingStamp();
                soMapper.updateOrdSo(so);
            }
        }

        return 1;
    }

}

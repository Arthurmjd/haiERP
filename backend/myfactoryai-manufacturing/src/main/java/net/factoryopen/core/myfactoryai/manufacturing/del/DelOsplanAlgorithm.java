package net.factoryopen.core.myfactoryai.manufacturing.del;

import java.math.BigDecimal;

import net.factoryopen.core.common.exception.QuantityUnavailableException;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialPurchasingType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanReceivingMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplanReceiving;

/**
 * 转包计划维护ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-28
 */
@Service
public class DelOsplanAlgorithm
{
    @Autowired(required = false)
    private DelOsplanMapper osplanMapper;

    @Autowired
    private EngProductBasisMapper productMapper;

    @Autowired
    private ChaCustomerMapper customerMapper;

    @Autowired(required = false)
    private SupPurchaseRequestMapper prMapper;

    @Autowired(required = false)
    private SupPurchaseRequestItemMapper prItemMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soitemMapper;

    @Autowired(required = false)
    private DelOsplanReceivingMapper receivingMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 收货
     *
     * @param ids id集合
     * @return 结果
     */
    public int toPr(Long[] ids) throws Exception{
        // 判异
        for (int i = 0; i < ids.length; i++) {
            // 排除状态的錯配
            DelOsplan plan = osplanMapper.selectDelOsplanById(ids[i]);
            if (plan.getStatus().intValue() != BillStatus.BASE_ENABLE) {
                String msg = "转包计划[" + plan.getBillNo() + "]的状态不是[4-生效]";
                throw new StatusUnavailableException(msg);
            }
            // 排除数量的錯配
            if (plan.getQuantityPlanned().doubleValue()<=0){
                String msg = "转包计划[" + plan.getBillNo() + "]的数量不能小于等于0";
                throw new QuantityUnavailableException(msg);
            }
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno("PR");
        billcodeManager.updateBillSeq("PR");

        // 创建采购申请单头
        SupPurchaseRequest pr = new SupPurchaseRequest();
        pr.setBillNo(billNo);
        pr.setPurchasingType(MaterialPurchasingType.SUBCONTRACT);
        pr.setStatus(BillStatus.BASE_AUDIT_REQUEST);//设为“请审”状态
        pr.setRequestDay(DateUtils.getNowDate());
        pr.setRequestorId(SecurityUtils.getUserId());
        pr.setDeptId(SecurityUtils.getDeptId());
        pr.refreshCreatingStamp();
        prMapper.insertSupPurchaseRequest(pr);

        // 逐一取原物料需求，建采购申请明细
        for (int i = 0; i < ids.length; i++) {
            DelOsplan plan = osplanMapper.selectDelOsplanById(ids[i]);
            EngProductBasis product = productMapper.selectEngProductBasisById(plan.getProductId());
            ChaCustomer customer = customerMapper.selectChaCustomerById(plan.getCustomerId());
            OrdSoItem soItem = soitemMapper.selectOrdSoItemById(plan.getSoItemId());

            //创建并保存发货单身
            SupPurchaseRequestItem prItem = new SupPurchaseRequestItem();
            prItem.setSeqNo(i+1);
            prItem.setPrId(pr.getId());
            // 计算需求量，并设置之
            prItem.setQuantity(plan.getQuantityPlanned());
            prItem.setRequireDay(plan.getNeedDay());
            prItem.setPriority(plan.getPriority());
            prItem.setRequestBillId(plan.getId());
            prItem.setRequestBillNo(plan.getBillNo());
            //复制物料信息
            prItem.setMaterialId(product.getId());
            prItem.setMaterialCode(product.getMaterialCode());
            prItem.setMaterialName(product.getMaterialName());
            prItem.setMaterialSpec(product.getMaterialSpec());
            prItem.setDrawNo(product.getDrawNo());
            prItem.setUnitId(product.getUnitId());
            //复制订单信息
            if (customer!=null) prItem.setCustomerId(customer.getId());
            if (customer!=null) prItem.setCustomerName(customer.getCustomerName());
            if (soItem!=null) prItem.setPriceSale(soItem.getPrice());
            //不为null则赋值
            prItem.setConsumeType(product.getConsumeType()==null?null:Integer.parseInt(product.getConsumeType()));
            prItem.setEnableMust(null);
            prItem.setEnableReplace(null);
            //创建戳
            prItem.setCreateBy(SecurityUtils.getUsername());
            prItem.setCreateTime(DateUtils.getNowDate());
            prItem.setUpdateBy(SecurityUtils.getUsername());
            prItem.setUpdateTime(DateUtils.getNowDate());
            //保存
            prItemMapper.insertSupPurchaseRequestItem(prItem);

            // 将原转包计划的状态设为“请处理”
            plan.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
            plan.refreshUpdatingStamp();
            osplanMapper.updateDelOsplan(plan);

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

    /**
     * 收货
     *
     * @param delOsplan 收货
     * @return 结果
     */
    public int osReturn(DelOsplan delOsplan) throws Exception{
        // 取UI的form变量
        int quantityReceiving = ((Integer) delOsplan.getParams().get("quantityReceiving")).intValue();
        String contact = (String) delOsplan.getParams().get("contact");
        String phone = (String) delOsplan.getParams().get("phone");

        // 判异，排除数量不足的收货
        DelOsplan osplan = osplanMapper.selectDelOsplanById(delOsplan.getId());
        if (quantityReceiving > osplan.getQuantityPlanned().doubleValue()-osplan.getQuantityFinished().doubleValue()) {
            String osplanBillno = osplan.getBillNo();
            String msg = "外协计划[" + osplanBillno + "]的可收货数量不足";
            throw new QuantityUnavailableException(msg);
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno("OSR");
        billcodeManager.updateBillSeq("OSR");

        //创建外协收货单，并设置特殊值
        DelOsplanReceiving receiving = new DelOsplanReceiving();
        receiving.setBillNo(billNo);
        receiving.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
        receiving.setOsplanId(osplan.getId());
        // 设置外协收货量
        receiving.setQuantityReceived(new BigDecimal(quantityReceiving));
        // 若form传参不为空则赋值
        if(contact!=null) receiving.setContactName(contact);
        if(phone!=null) receiving.setContactPhone(phone);
        //从外协计划里复制字段值
        receiving.setProductId(osplan.getProductId());
        receiving.setProductCode(osplan.getProductCode());
        receiving.setProductName(osplan.getProductName());
        receiving.setProductSpec(osplan.getProductSpec());
        receiving.setUnitId(osplan.getUnitId());
        receiving.setProjectId(osplan.getProjectId());
        receiving.setSupplierId(null);
        receiving.setCustomerId(osplan.getCustomerId());
        receiving.setSoId(osplan.getSoId());
        receiving.setSoItemId(osplan.getSoItemId());
        receiving.setSalesId(osplan.getSalesId());
        //保存外协收货单
        receiving.refreshCreatingStamp();
        receivingMapper.insertDelOsplanReceiving(receiving);

        // 更改原外协计划单的状态和派工量
        osplan.setStatus(BillStatus.ACTIVE_PROCESSING);//设为“在处理”
        osplan.setQuantityFinished(osplan.getQuantityFinished().add(new BigDecimal(quantityReceiving)));//派工量增加新派量
        osplan.refreshUpdatingStamp();
        osplanMapper.updateDelOsplan(osplan);

        return 1;
    }

}

package net.factoryopen.core.myfactoryai.manufacturing.del;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialBaseType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryDealMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPlanItemMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import org.springframework.stereotype.Service;

/**
 * 交货筹划ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-25
 */
@Service
public class DelDeliveryDealAlgorithm
{
    @Autowired
    private DelDeliveryDealMapper masterMapper;
    @Autowired
    private OrdSoMapper soMapper;
    @Autowired
    private EngProductBasisMapper productMapper;
    @Autowired
    private DelPlanItemMapper delplanMapper;
    @Autowired
    private DelOsplanMapper osplanMapper;
    @Autowired
    private DelMpsMapper mpsMapper;
    @Autowired(required = false)
    private OrdSoItemMapper soItemMapper;
    @Autowired(required = false)
    private OrdContractItemMapper contractItemMapper;
    @Autowired(required = false)
    private OrdContractMapper contractMapper;
    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 计划发货
     *
     * @param delDeliveryDeal 发货
     * @return 结果
     */
    public synchronized int planDelivery(DelDeliveryDeal delDeliveryDeal){
        Integer priority = (Integer) delDeliveryDeal.getParams().get("priority");
        Date promisday =delDeliveryDeal.getPromiseDay();

        double quantityOperating =0;
        Object obj = delDeliveryDeal.getParams().get("quantityOperating");
        if (obj.getClass().equals(String.class)){
            quantityOperating = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            quantityOperating = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            quantityOperating = ((Integer)obj).doubleValue();
        }

        //读取订单明细
        Long objid = delDeliveryDeal.getId();
        DelDeliveryDeal masterObj = masterMapper.selectDelDeliveryDealById(objid);

        //取设发货计划号
        String billNo = billcodeManager.getNextBillno("DP");
        billcodeManager.updateBillSeq("DP");

        //创建发货计划项
        OrdSo soObj = soMapper.selectOrdSoById(masterObj.getSoId());
        DelPlanItem itemObj = new DelPlanItem();
        itemObj.setBillNo(billNo);
        itemObj.setSoId(masterObj.getSoId());
        itemObj.setSoItemId(masterObj.getId());
        itemObj.setStatus(BillStatus.BASE_ENABLE);
        itemObj.setProductId(masterObj.getProductId());
        itemObj.setProductCode(masterObj.getProductCode());
        itemObj.setProductName(masterObj.getProductName());
        itemObj.setProductSpec(masterObj.getProductSpec());
        itemObj.setDrawNo(masterObj.getDrawNo());
        itemObj.setCustomerProductNo(masterObj.getCustomerProductNo());
        itemObj.setUnitId(masterObj.getUnitId());
        itemObj.setQuantityPlanned(new BigDecimal(quantityOperating));
        itemObj.setPrice(masterObj.getPrice());
        itemObj.setDiscountPrice(masterObj.getDiscountPrice());
        itemObj.setDiscountRate(masterObj.getDiscountRate());
        itemObj.setNeedDay(masterObj.getNeedDay());
        itemObj.setPromiseDay(promisday);
        itemObj.setOrderDay(soObj.getOrderDay());
        itemObj.setPriority(priority);
        itemObj.setCustomerId(soObj.getCustomerId());
        itemObj.setProjectId(soObj.getProjectId());
        itemObj.setSalesId(soObj.getSalesId());
        itemObj.setAmount(new BigDecimal( quantityOperating * itemObj.getDiscountPrice().doubleValue()));
        itemObj.setFinalAmount(new BigDecimal(itemObj.getAmount().doubleValue() * itemObj.getDiscountRate().doubleValue()));
        //保存
        itemObj.refreshCreatingStamp();
        delplanMapper.insertDelPlanItem(itemObj);

        //更新订单明细的发货计划量、状态
        if (masterObj.getStatus()==BillStatus.BASE_ENABLE) masterObj.setStatus(BillStatus.ACTIVE_PLANNING);
        masterObj.setQuantityDelivering(new BigDecimal(masterObj.getQuantityDelivering().doubleValue() + quantityOperating));
        masterObj.refreshUpdatingStamp();
        masterMapper.updateDelDeliveryDeal(masterObj);

        //更新订单的状态
        if(soObj.getStatus()==BillStatus.BASE_ENABLE) {
            soObj.setStatus(BillStatus.ACTIVE_PLANNING);
            soObj.refreshUpdatingStamp();
            soMapper.updateOrdSo(soObj);
        }

        //更新产品的预约库存量
        EngProductBasis product = productMapper.selectEngProductBasisById(masterObj.getProductId());
        product.setQuantityBooked(new BigDecimal(product.getQuantityBooked().doubleValue() + quantityOperating));
        product.setUpdateBy(SecurityUtils.getUsername());
        product.setUpdateTime(DateUtils.getNowDate());
        productMapper.updateEngProductBasis(product);

        return 1;
    }

    /**
     * 计划转包
     *
     * @param delDeliveryDeal 转包
     * @return 结果
     */
    public synchronized int planOs(DelDeliveryDeal delDeliveryDeal){
        Integer priority = (Integer) delDeliveryDeal.getParams().get("priority");
        Date promisday =delDeliveryDeal.getPromiseDay();

        double quantityOperating =0;
        Object obj = delDeliveryDeal.getParams().get("quantityOperating");
        if (obj.getClass().equals(String.class)){
            quantityOperating = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            quantityOperating = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            quantityOperating = ((Integer)obj).doubleValue();
        }

        //更新订单明细的转包计划量、状态
        Long objid = delDeliveryDeal.getId();
        DelDeliveryDeal masterObj = masterMapper.selectDelDeliveryDealById(objid);
        if (masterObj.getStatus()==BillStatus.BASE_ENABLE) masterObj.setStatus(BillStatus.ACTIVE_PLANNING);
        masterObj.setQuantitySubcontract(new BigDecimal(masterObj.getQuantitySubcontract().doubleValue() + quantityOperating));
        masterObj.refreshUpdatingStamp();
        masterMapper.updateDelDeliveryDeal(masterObj);

        //更新订单的状态
        OrdSo soObj = soMapper.selectOrdSoById(masterObj.getSoId());
        if(soObj.getStatus()==BillStatus.BASE_ENABLE) {
            soObj.setStatus(BillStatus.ACTIVE_PLANNING);
            soObj.refreshCreatingStamp();
            soMapper.updateOrdSo(soObj);
        }

        //取存单号
        String billNo = billcodeManager.getNextBillno("OS");
        billcodeManager.updateBillSeq("OS");
        //创建外协单，并设置特殊值
        DelOsplan osObj = new DelOsplan();
        osObj.setBillNo(billNo);
        osObj.setStatus(BillStatus.BASE_ENABLE);
        // 设置外协量
        osObj.setQuantityPlanned(new BigDecimal(quantityOperating));
        osObj.setQuantityDispatched(new BigDecimal(0));
        //从订单明细里复制字段值
        osObj.setSoId(masterObj.getSoId());
        osObj.setSoItemId(masterObj.getId());
        osObj.setProductId(masterObj.getProductId());
        osObj.setProductCode(masterObj.getProductCode());
        osObj.setProductName(masterObj.getProductName());
        osObj.setProductSpec(masterObj.getProductSpec());
        osObj.setDrawNo(masterObj.getDrawNo());
        osObj.setCustomerProductNo(masterObj.getCustomerProductNo());
        osObj.setUnitId(masterObj.getUnitId());
        osObj.setNeedDay(masterObj.getNeedDay());
        osObj.setPromiseDay(promisday);
        osObj.setOrderDay(soObj.getOrderDay());
        osObj.setPriority(priority);
        osObj.setCustomerId(soObj.getCustomerId());
        osObj.setProjectId(soObj.getProjectId());
        osObj.setSalesId(soObj.getSalesId());
        //保存外协计划
        osObj.refreshCreatingStamp();
        osplanMapper.insertDelOsplan(osObj);

        return 1;
    }

    /**
     * 计划自制
     *
     * @param delDeliveryDeal 投产
     * @return 结果
     */
    public synchronized int planMps(DelDeliveryDeal delDeliveryDeal){
        Integer priority = (Integer) delDeliveryDeal.getParams().get("priority");
        Date promisday =delDeliveryDeal.getPromiseDay();

        double quantityOperating =0;
        Object obj = delDeliveryDeal.getParams().get("quantityOperating");
        if (obj.getClass().equals(String.class)){
            quantityOperating = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            quantityOperating = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            quantityOperating = ((Integer)obj).doubleValue();
        }

        //更新订单明细的自制计划量、状态
        Long objid = delDeliveryDeal.getId();
        DelDeliveryDeal masterObj = masterMapper.selectDelDeliveryDealById(objid);
        if (masterObj.getStatus()==BillStatus.BASE_ENABLE) masterObj.setStatus(BillStatus.ACTIVE_PLANNING);
        masterObj.setQuantityMps(new BigDecimal(masterObj.getQuantityMps().doubleValue() + quantityOperating));
        masterObj.refreshUpdatingStamp();
        masterMapper.updateDelDeliveryDeal(masterObj);

        //更新订单的状态
        OrdSo soObj = soMapper.selectOrdSoById(masterObj.getSoId());
        if(soObj.getStatus()==BillStatus.BASE_ENABLE) {
            soObj.setStatus(BillStatus.ACTIVE_PLANNING);
            soObj.refreshUpdatingStamp();
            soMapper.updateOrdSo(soObj);
        }

        //取存单号
        String billNo = billcodeManager.getNextBillno("MPS");
        billcodeManager.updateBillSeq("MPS");

        DelMps mpsObj = new DelMps();
        mpsObj.setBillNo(billNo);

        //判断类型
        if(soObj.getSoType().equals("0")) mpsObj.setMpsType(0);
        else if(soObj.getSoType().equals("1")) mpsObj.setMpsType(1);
        else if(soObj.getSoType().equals("2")) mpsObj.setMpsType(3);
        else if(soObj.getSoType().equals("3")) mpsObj.setMpsType(2);
        //状态设为已审
        mpsObj.setStatus(BillStatus.BASE_AUDIT_PASS);

        mpsObj.setSoId(masterObj.getSoId());
        mpsObj.setSoItemId(masterObj.getId());
        mpsObj.setProductId(masterObj.getProductId());
        mpsObj.setProductCode(masterObj.getProductCode());
        mpsObj.setProductName(masterObj.getProductName());
        mpsObj.setProductSpec(masterObj.getProductSpec());
        mpsObj.setDrawNo(masterObj.getDrawNo());
        mpsObj.setCustomerProductNo(masterObj.getCustomerProductNo());
        mpsObj.setUnitId(masterObj.getUnitId());
        mpsObj.setQuantityPlanned(new BigDecimal(quantityOperating));
        mpsObj.setNeedDay(masterObj.getNeedDay());
        mpsObj.setPromiseDay(promisday);
        mpsObj.setOrderDay(soObj.getOrderDay());
        mpsObj.setPriority(priority);
        mpsObj.setCustomerId(soObj.getCustomerId());
        mpsObj.setProjectId(soObj.getProjectId());
        mpsObj.setSalesId(soObj.getSalesId());
        //保存
        mpsObj.refreshCreatingStamp();
        mpsMapper.insertDelMps(mpsObj);

        return 1;
    }

    /**
     * 转正料号
     *
     * @param deliveryDeal 订单明细
     * @return 结果
     */
    public int normalizeCode(DelDeliveryDeal deliveryDeal) throws Exception{
        //校验料号重复否
        EngProductBasis productTarget = productMapper.selectEngProductBasisById(deliveryDeal.getProductId());
        if (productTarget==null || productTarget.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
            String msg = "目标料号[" + deliveryDeal.getProductCode() + "]不存在！";
            throw new MaterialUnavailableException(msg);
        }

        //取原料号
        DelDeliveryDeal soitemDatabase = masterMapper.selectDelDeliveryDealById(deliveryDeal.getId());
        EngProductBasis productSource = productMapper.selectEngProductBasisById(soitemDatabase.getProductId());

        //替代订单明细的临时料
        OrdSoItem soItem=new OrdSoItem();
        soItem.setProductId(productSource.getId());
        List<OrdSoItem> soItemList = soItemMapper.selectOrdSoItemList(soItem);
        if (soItemList!=null){
            for (OrdSoItem item : soItemList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                item.setProductName(productTarget.getMaterialName());
                item.setProductSpec(productTarget.getMaterialSpec());
                item.setDrawNo(productTarget.getDrawNo());
                item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.setUnitId(productTarget.getUnitId());
                item.refreshUpdatingStamp();
                soItemMapper.updateOrdSoItem(item);
            }
        }

        //替代合同明细的临时料
        OrdContractItem contractItem=new OrdContractItem();
        contractItem.setProductId(productSource.getId());
        List<OrdContractItem> contractItemList = contractItemMapper.selectOrdContractItemList(contractItem);
        if (contractItemList!=null){
            for (OrdContractItem item : contractItemList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                item.setUnitId(productTarget.getUnitId());
                //保留原合同数据
                //item.setDrawNo(productTarget.getDrawNo());
                //item.setProductName(productTarget.getMaterialName());
                //item.setProductSpec(productTarget.getMaterialSpec());
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                //item.setAbroadProductNo(productTarget.getAbroadProductNo());
                item.refreshUpdatingStamp();
                contractItemMapper.updateOrdContractItem(item);
            }
        }

        //替代合同头的临时料
        OrdContract contract = new OrdContract();
        contract.setProductId(productSource.getId());
        List<OrdContract> contractList = contractMapper.selectOrdContractList(contract);
        if (contractList!=null){
            for (OrdContract item : contractList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                //保留原合同数据
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.refreshUpdatingStamp();
                contractMapper.updateOrdContract(item);
            }
        }

        return 1;
    }

}

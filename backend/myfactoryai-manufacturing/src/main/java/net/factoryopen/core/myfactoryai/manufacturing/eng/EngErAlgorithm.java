package net.factoryopen.core.myfactoryai.manufacturing.eng;

import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.exception.*;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.ErType;
import net.factoryopen.core.common.type.MaterialBaseType;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngErMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;

/**
 * 工艺处理请求ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-12-12
 */
@Service
public class EngErAlgorithm
{
    @Autowired(required = false)
    private EngErMapper erMapper;

    @Autowired(required = false)
    private EngProductBasisMapper productMapper;

    @Autowired(required = false)
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
     * 生成产品料号
     *
     * @param er 请求
     * @return 结果
     */
    public int generateCode(EngEr er) throws Exception{
        //校验料号重复否
        EngProductBasis product = new EngProductBasis();
        product.setMaterialCode(er.getProductCode());
        List<EngProductBasis> productList = productMapper.selectEngProductBasisList(product);
        if (productList!=null && productList.size()>0){
            String msg = "料号[" + er.getProductCode() + "]已存在！";
            throw new MaterialUnavailableException(msg);
        }
        //校验是否工艺创建请求
        if (er.getErType()!= ErType.CREATING){
            String msg = "工艺请求[" + er.getBillNo() + "]不是工艺创建请求，不能生成料号！";
            throw new TypeMismatchException(msg);
        }
        //校验状态
        if (er.getStatus()!= BillStatus.BASE_ENABLE && er.getStatus()!=BillStatus.ACTIVE_PROCESSING){
            String msg = "自制主计划[" + er.getMpsNo() + "]当前状态生成料号是非法的！";
            throw new MaterialUnavailableException(msg);
        }

        //转正临时料
        product = productMapper.selectEngProductBasisById(er.getProductId());
        product.setMaterialCode(er.getProductCode());
        product.setCategoryId(er.getCategoryId());
        product.setDrawNo(er.getDrawNo());
        product.setBaseType(String.valueOf(MaterialBaseType.PRODUCT));
        product.setMaterialName(er.getProductName());
        product.setMaterialSpec(er.getProductSpec());
        product.setCompoundId(er.getCompoundId());
        product.setCustomerProductNo(er.getCustomerProductNo());
        product.setAbroadProductNo(er.getAbroadProductNo());
        product.setUnitId(er.getUnitId());
        product.refreshCreatingStamp();
        productMapper.updateEngProductBasis(product);

        //修改申请自身
        EngEr erTemp = new EngEr();
        erTemp.setProductId(er.getProductId());
        List<EngEr> erList = erMapper.selectEngErList(erTemp);
        if (erList!=null){
            for (EngEr item : erList){
                item.setProductCode(product.getMaterialCode());
                item.setCategoryId(product.getCategoryId());
                item.setDrawNo(product.getDrawNo());
                item.setProductName(product.getMaterialName());
                item.setProductSpec(product.getMaterialSpec());
                item.setCompoundId(product.getCompoundId());
                item.setCustomerProductNo(product.getCustomerProductNo());
                item.setAbroadProductNo(product.getAbroadProductNo());
                item.setUnitId(er.getUnitId());
                item.setStatus(BillStatus.ACTIVE_PROCESSING);//在处理
                item.refreshUpdatingStamp();
                erMapper.updateEngEr(item);
            }
        }

        //替代主计划的临时料
        DelMps mps=new DelMps();
        mps.setProductId(er.getProductId());
        List<DelMps> mpsList = mpsMapper.selectDelMpsList(mps);
        if (mpsList!=null){
            for (DelMps item : mpsList){
                item.setProductCode(er.getProductCode());
                item.setProductName(er.getProductName());
                item.setProductSpec(er.getProductSpec());
                item.setDrawNo(er.getDrawNo());
                item.setCustomerProductNo(er.getCustomerProductNo());
                item.setAbroadProductNo(er.getAbroadProductNo());
                item.setUnitId(er.getUnitId());
                item.refreshUpdatingStamp();
                mpsMapper.updateDelMps(item);
            }
        }

        //替代订单明细的临时料
        OrdSoItem soItem=new OrdSoItem();
        soItem.setProductId(er.getProductId());
        List<OrdSoItem> soItemList = soItemMapper.selectOrdSoItemList(soItem);
        if (soItemList!=null){
            for (OrdSoItem item : soItemList){
                item.setProductCode(er.getProductCode());
                item.setProductName(er.getProductName());
                item.setProductSpec(er.getProductSpec());
                item.setDrawNo(er.getDrawNo());
                item.setCustomerProductNo(er.getCustomerProductNo());
                item.setUnitId(er.getUnitId());
                item.refreshUpdatingStamp();
                soItemMapper.updateOrdSoItem(item);
            }
        }

        //替代合同明细的临时料
        OrdContractItem contractItem=new OrdContractItem();
        contractItem.setProductId(er.getProductId());
        List<OrdContractItem> contractItemList = contractItemMapper.selectOrdContractItemList(contractItem);
        if (contractItemList!=null){
            for (OrdContractItem item : contractItemList){
                item.setProductCode(er.getProductCode());
                item.setUnitId(er.getUnitId());
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
        contract.setProductId(er.getProductId());
        List<OrdContract> contractList = contractMapper.selectOrdContractList(contract);
        if (contractList!=null){
            for (OrdContract item : contractList){
                item.setProductCode(er.getProductCode());
                //保留原合同数据
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.refreshUpdatingStamp();
                contractMapper.updateOrdContract(item);
            }
        }

        return 1;
    }

    /**
     * 维护BOM
     *
     * @param engEr 维护BOM
     * @return 结果
     */
    public int updateBom(EngEr engEr) throws Exception{
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
    /**
     * 维护路线
     *
     * @param engEr 维护路线
     * @return 结果
     */
    public int updateRouting(EngEr engEr) throws Exception{
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
    /**
     * 维护文档
     *
     * @param engEr 维护文档
     * @return 结果
     */
    public int updateDocs(EngEr engEr) throws Exception{
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

package net.factoryopen.core.logic;

import net.factoryopen.core.common.exception.BomUnavailableException;
import net.factoryopen.core.common.type.MaterialBaseType;
import net.factoryopen.core.common.type.MaterialMrpCalculatingType;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductAlgorithm {

    @Autowired(required = false)
    private EngProductBomMapper bomMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    /**
     * 递归计算bom树的物料总配比
     *
     * @param bomTree bom数
     * @param productId 自制件虚拟件Id
     * @param k 物料配比
     * @return 物料与配比的地图
     */
    public HashMap<Long,Double> mergeBomTree(HashMap<Long,Double> bomTree, Long productId, double k) throws Exception{
        HashMap<Long,Double> result =bomTree;

        List<EngProductBom> bom = bomMapper.selectEngProductBomListByProductId(productId);
        if (bom==null){
            EngMaterial product = materialMapper.selectEngMaterialById(productId);
            String msg = "制成品["+product.getMaterialCode()+","+product.getMaterialName()+"]的BOM不存在";
            BomUnavailableException ex = new BomUnavailableException(msg);
            throw ex;
        }

        for (int i = 0; bom!=null && i < bom.size(); i++) {
            EngProductBom bomItem = bom.get(i);

            //防呆
            if (bomItem.getCalculateType()==null){
                throw new Exception("算料失败！BOM中该物料的算料类型是空的：["+bomItem.getMaterialCode()+"，"+bomItem.getMaterialName()+"]");
            }

            Integer calculateType = Integer.valueOf(bomItem.getCalculateType());
            Double rateSum=null;
            double rateNow=k * bomItem.getStandardQuantity().doubleValue()*(1+bomItem.getLossRate().doubleValue()/100);

            //若为采购件，则计量
            if (calculateType.equals(MaterialMrpCalculatingType.PURCHASING))
            {
                rateSum = result.get(bomItem.getMaterialId());
                if (rateSum==null) {
                    result.put(bomItem.getMaterialId(), rateNow);
                }
                else{
                    rateNow += rateSum ;
                    result.replace(bomItem.getMaterialId(), rateNow);
                }
            }
            //若为自制件虚拟件，则带比例递归
            else if (calculateType.equals(MaterialMrpCalculatingType.MANUFACTURING))//自制件虚拟件则带比例递归
            {
                this.mergeBomTree(result, bomItem.getMaterialId(), rateNow);
            }
            //若为库存件
            else if (calculateType.equals(MaterialMrpCalculatingType.USING_PRODUCT_INVENTORY))//自制件虚拟件则带比例递归
            {
                //不做任何处理，试之无物;
            }
        }

        return result;
    }

    /**
     * 递归分解bom树的产线计划（工单/生产订单）
     *
     * @param bomTree bom树
     * @param productId 成品半成品Id
     * @param k 物料配比
     * @param day 计划交期
     * @param priority 计划优先级
     * @return 物料与配比的地图
     */
    public HashMap<Long,Object[]> mergeWoTree(HashMap<Long,Object[]> bomTree,
                                                EngProductBom bomItem, Long productId, double k, Date day, Integer priority){
        HashMap<Long,Object[]> result =bomTree;

        //默认按采购料计算
        String calculateType = bomItem==null ? String.valueOf(MaterialMrpCalculatingType.PURCHASING) : bomItem.getCalculateType();
        Long lineId = null;

        EngMaterial product = materialMapper.selectEngMaterialById(productId);
        if (bomItem==null){
            //求lineId
            lineId = bomItem==null ? product.getLineId() : bomItem.getLineId();

            //求料的计算类型calculateType
            if (product.getBaseType().equals(String.valueOf(MaterialBaseType.MATERIAL))){
                calculateType = String.valueOf(MaterialMrpCalculatingType.PURCHASING);
            }else if (product.getBaseType().equals(String.valueOf(MaterialBaseType.PRODUCT))
                          || product.getBaseType().equals(String.valueOf(MaterialBaseType.VIRTUAL))){
                calculateType = String.valueOf(MaterialMrpCalculatingType.MANUFACTURING);
            }else if (product.getBaseType().equals(String.valueOf(MaterialBaseType.TRADE))){
                calculateType = String.valueOf(MaterialMrpCalculatingType.PURCHASING);
            }
        }

        //若为自制件，则创建计划，并递归分解
        if (calculateType.equals(String.valueOf(MaterialMrpCalculatingType.MANUFACTURING))
              && product.getBaseType().equals(String.valueOf(MaterialBaseType.PRODUCT.intValue())))
        {
            Date dayDelivery = day;
            Integer priorityOrder = priority;

            //计算物料配比
            Double rateMaterail = 1D;
            if (bomItem!=null){
                rateMaterail = k * bomItem.getStandardQuantity().doubleValue()*(1+bomItem.getLossRate().doubleValue()/100);
            }

            //计算前置天数
            int daysNeed = product.getDaysLeadtime().intValue()+product.getDaysCycletime().intValue()+product.getDaysPosttime().intValue();
            double daysLeft= DateUtils.differentDaysByMillisecond(dayDelivery,DateUtils.getNowDate());

            //若剩余天数小于需要天数，且剩余无时日
            if(daysLeft<=0){
                priorityOrder = 1;//特急
                dayDelivery =DateUtils.addDays(DateUtils.getNowDate(), daysNeed);
            }
            //若剩余天数大于需要天数
            else if (daysLeft>=daysNeed){
                dayDelivery =DateUtils.addDays(dayDelivery, 0-daysNeed);
            }
            //若剩余天数小于需要天数，且剩余有时日
            else if(daysLeft<daysNeed){
                priorityOrder = priority<2 ? priority:2;
                dayDelivery =DateUtils.addDays(DateUtils.getNowDate(), daysNeed);
            }

            //缓存产线计划
            Object[] objArray = bomTree.get(productId);
            if (objArray==null) result.put(productId, new Object[]{rateMaterail, dayDelivery,priorityOrder, lineId});
            else {
                rateMaterail += (Double)objArray[0];
                result.replace(productId, new Object[]{rateMaterail, dayDelivery,priorityOrder, lineId});
            }
            //取BOM
            List<EngProductBom> bom = bomMapper.selectEngProductBomListByProductId(productId);
            //递归计算下一级半成品
            for (int i = 0; bom!=null && i < bom.size(); i++) {
                this.mergeWoTree(result, bom.get(i), bom.get(i).getMaterialId(), rateMaterail, dayDelivery, priorityOrder);
            }
        }
        //若为虚拟件，则仅递归分解
        else if (calculateType.equals(String.valueOf(MaterialMrpCalculatingType.MANUFACTURING))
                    && product.getBaseType().equals(String.valueOf(MaterialBaseType.VIRTUAL.intValue())))
        {
            //取BOM
            List<EngProductBom> bom = bomMapper.selectEngProductBomListByProductId(productId);
            //递归计算下一级半成品
            for (int i = 0; bom!=null && i < bom.size(); i++) {
                this.mergeWoTree(result, bom.get(i), bom.get(i).getMaterialId(), k, day, priority);
            }
        }

        return result;
    }
}

package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

   import net.factoryopen.core.myfactoryai.manufacturing.del.DelDeliveryDealAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryDealMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryDealService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 交货筹划Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelDeliveryDealServiceImpl implements IDelDeliveryDealService 
{
    @Autowired(required = false)
    private DelDeliveryDealMapper delDeliveryDealMapper;

    @Autowired(required = false)
    private EngProductBasisMapper productMapper;

    @Autowired(required = false)
    private DelDeliveryDealAlgorithm delDeliveryDealAlgorithm;

    /**
     * 查询交货筹划
     * 
     * @param id 交货筹划主键
     * @return 交货筹划
     */
    @Override
    public DelDeliveryDeal selectDelDeliveryDealById(Long id)
    {
        DelDeliveryDeal deal = delDeliveryDealMapper.selectDelDeliveryDealById(id);
        if (deal!=null) this.fullfill(deal);

        return deal;
    }

    /**
     * 查询交货筹划列表
     * 
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealDraftList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealDraftList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealPublishingList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealPublishingList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealPublishedList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealPublishedList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealProcessingList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealProcessingList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealFinishingList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealFinishingList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealHistoryList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealHistoryList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划
     */
    @Override
    public List<DelDeliveryDeal> selectDelDeliveryDealAllList(DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealMapper.selectDelDeliveryDealAllList(delDeliveryDeal);
        if (list!=null){
            for (DelDeliveryDeal item : list) {
                this.fullfill(item);
            }
        }
        return list;
    }

    /**
     * 新增交货筹划
     * 
     * @param delDeliveryDeal 交货筹划
     * @return 结果
     */
    @Override
    public int insertDelDeliveryDeal(DelDeliveryDeal delDeliveryDeal)
    {
        //创建戳
        delDeliveryDeal.setCreateBy(SecurityUtils.getUsername());
        delDeliveryDeal.setCreateTime(DateUtils.getNowDate());
        delDeliveryDeal.setUpdateBy(SecurityUtils.getUsername());
        delDeliveryDeal.setUpdateTime(DateUtils.getNowDate());
        return delDeliveryDealMapper.insertDelDeliveryDeal(delDeliveryDeal);
    }

    /**
     * 修改交货筹划
     * 
     * @param delDeliveryDeal 交货筹划
     * @return 结果
     */
    @Override
    public int updateDelDeliveryDeal(DelDeliveryDeal delDeliveryDeal)
    {
        delDeliveryDeal.refreshUpdatingStamp();
        return delDeliveryDealMapper.updateDelDeliveryDeal(delDeliveryDeal);
    }

    /**
     * 批量删除交货筹划
     * 
     * @param ids 需要删除的交货筹划主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryDealByIds(Long[] ids)
    {
        return delDeliveryDealMapper.deleteDelDeliveryDealByIds(ids);
    }

    /**
     * 删除交货筹划信息
     * 
     * @param id 交货筹划主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryDealById(Long id)
    {
        return delDeliveryDealMapper.deleteDelDeliveryDealById(id);
    }

    /**
     * 发货
     *
     * @param delDeliveryDeal 发货
     * @return 结果
     */
    @Override
    public int planDelivery(DelDeliveryDeal delDeliveryDeal) throws Exception{
        try{
            return delDeliveryDealAlgorithm.planDelivery(delDeliveryDeal);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 投产
     *
     * @param delDeliveryDeal 投产
     * @return 结果
     */
    @Override
    public int planMps(DelDeliveryDeal delDeliveryDeal) throws Exception{
        try{
            return delDeliveryDealAlgorithm.planMps(delDeliveryDeal);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 外协
     *
     * @param delDeliveryDeal 外协
     * @return 结果
     */
    @Override
    public int planOs(DelDeliveryDeal delDeliveryDeal) throws Exception{
        try{
            return delDeliveryDealAlgorithm.planOs(delDeliveryDeal);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 转正料号
     *
     * @param delDeliveryDeal 订单明细
     * @return 结果
     */
    @Override
    public int normalizeCode(DelDeliveryDeal delDeliveryDeal) throws Exception{
        try{
            return delDeliveryDealAlgorithm.normalizeCode(delDeliveryDeal);
        }catch (Exception ex){
            throw ex;
        }
    }

    private void fullfill(DelDeliveryDeal deal){
        double availableInventory =0;

        //计算并记下可用量
        EngProductBasis product = productMapper.selectEngProductBasisById(deal.getProductId());
        if(product!=null){
            deal.setQuantityQualified(product.getQuantityQualified());
            deal.setQuantityBooked(product.getQuantityBooked());
            availableInventory = product.getQuantityQualified().doubleValue() - product.getQuantityBooked().doubleValue();
            deal.setQuantityAvailable(new BigDecimal(availableInventory));
        }

        //计算并记下缺额
        //订单量-可用库存-发货计划量-转包未完量-自制未完量
        double deliveredLack=deal.getQuantityOrder().doubleValue()- deal.getQuantityDelivered().doubleValue();
        double deliveringLack=deal.getQuantityOrder().doubleValue()- deal.getQuantityDelivering().doubleValue();
        deal.setQuantityDeliveredLack(new BigDecimal(deliveredLack));
        deal.setQuantityDeliveringLack(new BigDecimal(deliveringLack));

        //计算并记下缺额s
        //订单量-可用库存-发货计划量-转包未完量-自制未完量
//        double prepareLack=deal.getQuantityOrder().doubleValue()
//                -availableInventory
//                - deal.getQuantityDelivering().doubleValue()
//                -(deal.getQuantitySubcontract().doubleValue()-deal.getQuantitySubcontracted().doubleValue())
//                -(deal.getQuantityMps().doubleValue()-deal.getQuantityManufactured().doubleValue());
        double prepareLack=deal.getQuantityOrder().doubleValue()
                -deal.getQuantitySubcontract().doubleValue()
                -deal.getQuantityMps().doubleValue();
        deal.setQuantityPrepareLack(new BigDecimal(prepareLack));
    }
}

package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.del.DelDeliveryPlanAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryPlanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryPlanService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 发货计划维护Service业务层处理
 * 
 * @author admin
 * @date 2024-07-09
 */
@Service
public class DelDeliveryPlanServiceImpl implements IDelDeliveryPlanService 
{
    @Autowired(required = false)
    private DelDeliveryPlanMapper delDeliveryPlanMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private DelDeliveryPlanAlgorithm delDeliveryPlanAlgorithm;

    /**
     * 查询发货计划维护
     * 
     * @param id 发货计划维护主键
     * @return 发货计划维护
     */
    @Override
    public DelDeliveryPlan selectDelDeliveryPlanById(Long id)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanById(id);
    }

    /**
     * 查询发货计划维护列表
     * 
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanList(delDeliveryPlan);
    }

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanDraftList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanDraftList(delDeliveryPlan);
    }

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanPublishingList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanPublishingList(delDeliveryPlan);
    }

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanProcessingList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanProcessingList(delDeliveryPlan);
    }

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanFinishingList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanFinishingList(delDeliveryPlan);
    }

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanHistoryList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanHistoryList(delDeliveryPlan);
    }

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护
     */
    @Override
    public List<DelDeliveryPlan> selectDelDeliveryPlanAllList(DelDeliveryPlan delDeliveryPlan)
    {
        return delDeliveryPlanMapper.selectDelDeliveryPlanAllList(delDeliveryPlan);
    }

    /**
     * 新增发货计划维护
     * 
     * @param delDeliveryPlan 发货计划维护
     * @return 结果
     */
    @Override
    public int insertDelDeliveryPlan(DelDeliveryPlan delDeliveryPlan)
    {
        delDeliveryPlan.setCreateTime(DateUtils.getNowDate());
        return delDeliveryPlanMapper.insertDelDeliveryPlan(delDeliveryPlan);
    }

    /**
     * 修改发货计划维护
     * 
     * @param delDeliveryPlan 发货计划维护
     * @return 结果
     */
    @Override
    public int updateDelDeliveryPlan(DelDeliveryPlan delDeliveryPlan)
    {
        delDeliveryPlan.setUpdateTime(DateUtils.getNowDate());
        return delDeliveryPlanMapper.updateDelDeliveryPlan(delDeliveryPlan);
    }

    /**
     * 批量删除发货计划维护
     * 
     * @param ids 需要删除的发货计划维护主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryPlanByIds(Long[] ids)
    {
        return delDeliveryPlanMapper.deleteDelDeliveryPlanByIds(ids);
    }

    /**
     * 删除发货计划维护信息
     * 
     * @param id 发货计划维护主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryPlanById(Long id)
    {
        return delDeliveryPlanMapper.deleteDelDeliveryPlanById(id);
    }

     /**
      * 冻结
      *
      * @param delDeliveryPlan 冻结
      * @return 结果
      */
     @Override
     public int froze(DelDeliveryPlan delDeliveryPlan){
         Long id = delDeliveryPlan.getId();
         DelDeliveryPlan obj = delDeliveryPlanMapper.selectDelDeliveryPlanById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delDeliveryPlan");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         frozen.refreshCreatingStamp();
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return delDeliveryPlanMapper.updateDelDeliveryPlan(obj);
      }
     /**
      * 解冻
      *
      * @param delDeliveryPlan 解冻
      * @return 结果
      */
     @Override
     public int unfroze(DelDeliveryPlan delDeliveryPlan){
         Long id = delDeliveryPlan.getId();
         DelDeliveryPlan obj = delDeliveryPlanMapper.selectDelDeliveryPlanById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delDeliveryPlan");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             obj.refreshUpdatingStamp();
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             return delDeliveryPlanMapper.updateDelDeliveryPlan(obj);
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param delDeliveryPlan 停用
      * @return 结果
      */
     @Override
     public int disable(DelDeliveryPlan delDeliveryPlan){
         Long id = delDeliveryPlan.getId();
         DelDeliveryPlan obj = delDeliveryPlanMapper.selectDelDeliveryPlanById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return delDeliveryPlanMapper.updateDelDeliveryPlan(obj);
     }
     /**
      * 强结
      *
      * @param delDeliveryPlan 强结
      * @return 结果
      */
     @Override
     public int finishforcely(DelDeliveryPlan delDeliveryPlan){
         Long id = delDeliveryPlan.getId();
         DelDeliveryPlan obj = delDeliveryPlanMapper.selectDelDeliveryPlanById(id);
         obj.setStatus(BillStatus.FINISH_FORCELY);
         obj.refreshUpdatingStamp();
         return delDeliveryPlanMapper.updateDelDeliveryPlan(obj);
     }
    /**
     * 转单
     *
     * @param delDeliveryPlan 转单
     * @return 结果
     */
    @Override
    public int billDelivery(Long[] ids, DelDeliveryPlan delDeliveryPlan) throws Exception{
        try{
            return delDeliveryPlanAlgorithm.billDelivery(ids, delDeliveryPlan);
        }catch (Exception ex){
            throw ex;
        }
    }

}

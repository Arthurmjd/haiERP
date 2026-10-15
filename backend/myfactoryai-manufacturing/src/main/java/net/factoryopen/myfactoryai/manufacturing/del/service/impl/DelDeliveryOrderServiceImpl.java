package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.del.DelDeliveryOrderAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryOrderMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryOrderService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 发货单管理Service业务层处理
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelDeliveryOrderServiceImpl implements IDelDeliveryOrderService
{
    @Autowired(required = false)
    private DelDeliveryOrderMapper delDeliveryOrderMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private DelDeliveryOrderAlgorithm delDeliveryOrderAlgorithm;

    /**
     * 查询发货单管理
     *
     * @param id 发货单管理主键
     * @return 发货单管理
     */
    @Override
    public DelDeliveryOrder selectDelDeliveryOrderById(Long id)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderById(id);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderList(delDeliveryOrder);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderDraftList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderDraftList(delDeliveryOrder);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderPublishingList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderPublishingList(delDeliveryOrder);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderProcessingList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderProcessingList(delDeliveryOrder);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderFinishingList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderFinishingList(delDeliveryOrder);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderHistoryList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderHistoryList(delDeliveryOrder);
    }

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理
     */
    @Override
    public List<DelDeliveryOrder> selectDelDeliveryOrderAllList(DelDeliveryOrder delDeliveryOrder)
    {
        return delDeliveryOrderMapper.selectDelDeliveryOrderAllList(delDeliveryOrder);
    }

    /**
     * 新增发货单管理
     *
     * @param delDeliveryOrder 发货单管理
     * @return 结果
     */
    @Override
    public int insertDelDeliveryOrder(DelDeliveryOrder delDeliveryOrder)
    {
        //创建戳
        delDeliveryOrder.setCreateBy(SecurityUtils.getUsername());
        delDeliveryOrder.setCreateTime(DateUtils.getNowDate());
        delDeliveryOrder.setUpdateBy(SecurityUtils.getUsername());
        delDeliveryOrder.setUpdateTime(DateUtils.getNowDate());
        return delDeliveryOrderMapper.insertDelDeliveryOrder(delDeliveryOrder);
    }

    /**
     * 修改发货单管理
     *
     * @param delDeliveryOrder 发货单管理
     * @return 结果
     */
    @Override
    public int updateDelDeliveryOrder(DelDeliveryOrder delDeliveryOrder)
    {
        delDeliveryOrder.refreshUpdatingStamp();
        return delDeliveryOrderMapper.updateDelDeliveryOrder(delDeliveryOrder);
    }

    /**
     * 批量删除发货单管理
     *
     * @param ids 需要删除的发货单管理主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryOrderByIds(Long[] ids)
    {
        return delDeliveryOrderMapper.deleteDelDeliveryOrderByIds(ids);
    }

    /**
     * 删除发货单管理信息
     *
     * @param id 发货单管理主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryOrderById(Long id)
    {
        return delDeliveryOrderMapper.deleteDelDeliveryOrderById(id);
    }

     /**
      * 冻结
      *
      * @param delDeliveryOrder 冻结
      * @return 结果
      */
     @Override
     public int froze(DelDeliveryOrder delDeliveryOrder){
         Long id = delDeliveryOrder.getId();
         DelDeliveryOrder obj = delDeliveryOrderMapper.selectDelDeliveryOrderById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delDeliveryOrder");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return delDeliveryOrderMapper.updateDelDeliveryOrder(obj);
      }
     /**
      * 解冻
      *
      * @param delDeliveryOrder 解冻
      * @return 结果
      */
     @Override
     public int unfroze(DelDeliveryOrder delDeliveryOrder){
         Long id = delDeliveryOrder.getId();
         DelDeliveryOrder obj = delDeliveryOrderMapper.selectDelDeliveryOrderById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delDeliveryOrder");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             obj.refreshUpdatingStamp();
             return delDeliveryOrderMapper.updateDelDeliveryOrder(obj);
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param delDeliveryOrder 停用
      * @return 结果
      */
     @Override
     public int disable(DelDeliveryOrder delDeliveryOrder){
         Long id = delDeliveryOrder.getId();
         DelDeliveryOrder obj = delDeliveryOrderMapper.selectDelDeliveryOrderById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return delDeliveryOrderMapper.updateDelDeliveryOrder(obj);
     }
     /**
      * 强结
      *
      * @param delDeliveryOrder 强结
      * @return 结果
      */
     @Override
     public int finishforcely(DelDeliveryOrder delDeliveryOrder){
         Long id = delDeliveryOrder.getId();
         DelDeliveryOrder obj = delDeliveryOrderMapper.selectDelDeliveryOrderById(id);
         obj.setStatus(BillStatus.FINISH_FORCELY);
         obj.refreshUpdatingStamp();
         return delDeliveryOrderMapper.updateDelDeliveryOrder(obj);
     }
    /**
     * 变更
     *
     * @param delDeliveryOrder 变更
     * @return 结果
     */
    @Override
    public int changeDelDeliveryOrder(DelDeliveryOrder delDeliveryOrder){
        delDeliveryOrder.setUpdateTime(DateUtils.getNowDate());
        return delDeliveryOrderMapper.updateDelDeliveryOrder(delDeliveryOrder);
    }
      /**
       * 发货
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int delivery(Long[] ids) throws Exception{
          try{
              return delDeliveryOrderAlgorithm.delivery(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

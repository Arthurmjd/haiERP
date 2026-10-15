package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.sup.SupPurchasingPlanAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchasingPlanMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchasingPlan;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchasingPlanService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购计划Service业务层处理
 * 
 * @author admin
 * @date 2024-09-18
 */
@Service
public class SupPurchasingPlanServiceImpl implements ISupPurchasingPlanService 
{
    @Autowired(required = false)
    private SupPurchasingPlanMapper supPurchasingPlanMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SupPurchasingPlanAlgorithm supPurchasingPlanAlgorithm;

    /**
     * 查询采购计划
     * 
     * @param id 采购计划主键
     * @return 采购计划
     */
    @Override
    public SupPurchasingPlan selectSupPurchasingPlanById(Long id)
    {
        return supPurchasingPlanMapper.selectSupPurchasingPlanById(id);
    }

    /**
     * 查询采购计划列表
     * 
     * @param supPurchasingPlan 采购计划
     * @return 采购计划
     */
    @Override
    public List<SupPurchasingPlan> selectSupPurchasingPlanList(SupPurchasingPlan supPurchasingPlan)
    {
        return supPurchasingPlanMapper.selectSupPurchasingPlanList(supPurchasingPlan);
    }

    /**
     * 新增采购计划
     * 
     * @param supPurchasingPlan 采购计划
     * @return 结果
     */
    @Override
    public int insertSupPurchasingPlan(SupPurchasingPlan supPurchasingPlan)
    {
        supPurchasingPlan.setCreateTime(DateUtils.getNowDate());
        return supPurchasingPlanMapper.insertSupPurchasingPlan(supPurchasingPlan);
    }

    /**
     * 修改采购计划
     * 
     * @param supPurchasingPlan 采购计划
     * @return 结果
     */
    @Override
    public int updateSupPurchasingPlan(SupPurchasingPlan supPurchasingPlan)
    {
        supPurchasingPlan.setUpdateTime(DateUtils.getNowDate());
        return supPurchasingPlanMapper.updateSupPurchasingPlan(supPurchasingPlan);
    }

    /**
     * 批量删除采购计划
     * 
     * @param ids 需要删除的采购计划主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchasingPlanByIds(Long[] ids)
    {
        return supPurchasingPlanMapper.deleteSupPurchasingPlanByIds(ids);
    }

    /**
     * 删除采购计划信息
     * 
     * @param id 采购计划主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchasingPlanById(Long id)
    {
        return supPurchasingPlanMapper.deleteSupPurchasingPlanById(id);
    }

     /**
      * 启用
      *
      * @param supPurchasingPlan 启用
      * @return 结果
      */
     @Override
     public int enable(SupPurchasingPlan supPurchasingPlan){
         Long id = supPurchasingPlan.getId();
         SupPurchasingPlan obj = supPurchasingPlanMapper.selectSupPurchasingPlanById(id);

         obj.setStatus(BillStatus.BASE_ENABLE);
         obj.refreshUpdatingStamp();
         return supPurchasingPlanMapper.updateSupPurchasingPlan(obj);
     }
    /**
     * 转单
     *
     * @param supPurchasingPlan 转单
     * @return 结果
     */
    @Override
    public int toPo(Long[] ids, SupPurchasingPlan supPurchasingPlan) throws Exception{
        try{
            return supPurchasingPlanAlgorithm.toPo(ids, supPurchasingPlan);
        }catch (Exception ex){
            throw ex;
        }
    }
     /**
      * 冻结
      *
      * @param supPurchasingPlan 冻结
      * @return 结果
      */
     @Override
     public int froze(SupPurchasingPlan supPurchasingPlan){
         Long id = supPurchasingPlan.getId();
         SupPurchasingPlan obj = supPurchasingPlanMapper.selectSupPurchasingPlanById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("supPurchasingPlan");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return supPurchasingPlanMapper.updateSupPurchasingPlan(obj);
      }
     /**
      * 解冻
      *
      * @param supPurchasingPlan 解冻
      * @return 结果
      */
     @Override
     public int unfroze(SupPurchasingPlan supPurchasingPlan){
         Long id = supPurchasingPlan.getId();
         SupPurchasingPlan obj = supPurchasingPlanMapper.selectSupPurchasingPlanById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("supPurchasingPlan");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             obj.refreshUpdatingStamp();
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             return supPurchasingPlanMapper.updateSupPurchasingPlan(obj);
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param supPurchasingPlan 停用
      * @return 结果
      */
     @Override
     public int disable(SupPurchasingPlan supPurchasingPlan){
         Long id = supPurchasingPlan.getId();
         SupPurchasingPlan obj = supPurchasingPlanMapper.selectSupPurchasingPlanById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return supPurchasingPlanMapper.updateSupPurchasingPlan(obj);
     }

}

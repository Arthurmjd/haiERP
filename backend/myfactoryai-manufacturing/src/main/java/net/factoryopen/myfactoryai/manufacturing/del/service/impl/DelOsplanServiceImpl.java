package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.del.DelOsplanAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelOsplanService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 外协计划维护Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelOsplanServiceImpl implements IDelOsplanService 
{
    @Autowired(required = false)
    private DelOsplanMapper delOsplanMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private DelOsplanAlgorithm delOsplanAlgorithm;

    /**
     * 查询外协计划维护
     * 
     * @param id 外协计划维护主键
     * @return 外协计划维护
     */
    @Override
    public DelOsplan selectDelOsplanById(Long id)
    {
        return delOsplanMapper.selectDelOsplanById(id);
    }

    /**
     * 查询外协计划维护列表
     * 
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanList(delOsplan);
    }

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanDraftList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanDraftList(delOsplan);
    }

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanPublishingList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanPublishingList(delOsplan);
    }

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanProcessingList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanProcessingList(delOsplan);
    }

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanFinishingList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanFinishingList(delOsplan);
    }

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanHistoryList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanHistoryList(delOsplan);
    }

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护
     */
    @Override
    public List<DelOsplan> selectDelOsplanAllList(DelOsplan delOsplan)
    {
        return delOsplanMapper.selectDelOsplanAllList(delOsplan);
    }

    /**
     * 新增外协计划维护
     * 
     * @param delOsplan 外协计划维护
     * @return 结果
     */
    @Override
    public int insertDelOsplan(DelOsplan delOsplan)
    {
        //创建戳
        delOsplan.setCreateBy(SecurityUtils.getUsername());
        delOsplan.setCreateTime(DateUtils.getNowDate());
        delOsplan.setUpdateBy(SecurityUtils.getUsername());
        delOsplan.setUpdateTime(DateUtils.getNowDate());
        return delOsplanMapper.insertDelOsplan(delOsplan);
    }

    /**
     * 修改外协计划维护
     * 
     * @param delOsplan 外协计划维护
     * @return 结果
     */
    @Override
    public int updateDelOsplan(DelOsplan delOsplan)
    {
        delOsplan.refreshUpdatingStamp();
        return delOsplanMapper.updateDelOsplan(delOsplan);
    }

    /**
     * 批量删除外协计划维护
     * 
     * @param ids 需要删除的外协计划维护主键
     * @return 结果
     */
    @Override
    public int deleteDelOsplanByIds(Long[] ids)
    {
        return delOsplanMapper.deleteDelOsplanByIds(ids);
    }

    /**
     * 删除外协计划维护信息
     * 
     * @param id 外协计划维护主键
     * @return 结果
     */
    @Override
    public int deleteDelOsplanById(Long id)
    {
        return delOsplanMapper.deleteDelOsplanById(id);
    }

    /**
     * 变更
     *
     * @param delOsplan 变更
     * @return 结果
     */
    @Override
    public int changeDelOsplan(DelOsplan delOsplan){
        delOsplan.refreshUpdatingStamp();
        return delOsplanMapper.updateDelOsplan(delOsplan);
    }
     /**
      * 冻结
      *
      * @param delOsplan 冻结
      * @return 结果
      */
     @Override
     public int froze(DelOsplan delOsplan){
         Long id = delOsplan.getId();
         DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delOsplan");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return delOsplanMapper.updateDelOsplan(obj);
      }
     /**
      * 解冻
      *
      * @param delOsplan 解冻
      * @return 结果
      */
     @Override
     public int unfroze(DelOsplan delOsplan){
         Long id = delOsplan.getId();
         DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delOsplan");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             obj.refreshUpdatingStamp();
             return delOsplanMapper.updateDelOsplan(obj);
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param delOsplan 停用
      * @return 结果
      */
     @Override
     public int disable(DelOsplan delOsplan){
         Long id = delOsplan.getId();
         DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return delOsplanMapper.updateDelOsplan(obj);
     }
     /**
      * 强结
      *
      * @param delOsplan 强结
      * @return 结果
      */
     @Override
     public int finishforcely(DelOsplan delOsplan){
         Long id = delOsplan.getId();
         DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);
         obj.setStatus(BillStatus.FINISH_FORCELY);
         obj.refreshUpdatingStamp();
         return delOsplanMapper.updateDelOsplan(obj);
     }
    /**
     * 收货
     *
     * @param ids id集合
     * @return 结果
     */
    @Override
    public int toPr(Long[] ids) throws Exception{
        try{
            return delOsplanAlgorithm.toPr(ids);
        }catch (Exception ex){
            throw ex;
        }
    }

}

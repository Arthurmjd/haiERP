package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.myfactoryai.manufacturing.del.DelMpsAlgorithm;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelMpsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 主计划维护Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelMpsServiceImpl implements IDelMpsService 
{
    @Autowired(required = false)
    private DelMpsMapper delMpsMapper;

    @Autowired(required = false)
    private DelMpsAlgorithm mpsAlgorithm;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询主计划维护
     * 
     * @param id 主计划维护主键
     * @return 主计划维护
     */
    @Override
    public DelMps selectDelMpsById(Long id)
    {
        return delMpsMapper.selectDelMpsById(id);
    }

    /**
     * 查询主计划维护列表
     * 
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsList(DelMps delMps)
    {
        return delMpsMapper.selectDelMpsList(delMps);
    }

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsDraftList(DelMps delMps)
    {
        return delMpsMapper.selectDelMpsDraftList(delMps);
    }

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsPublishingList(DelMps delMps)
    {
        return delMpsMapper.selectDelMpsPublishingList(delMps);
    }

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsProcessingList(DelMps delMps)
    {
        return delMpsMapper.selectDelMpsProcessingList(delMps);
    }

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsFinishingList(DelMps delMps) { return delMpsMapper.selectDelMpsFinishingList(delMps); }

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsHistoryList(DelMps delMps)
    {
        return delMpsMapper.selectDelMpsHistoryList(delMps);
    }

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护
     */
    @Override
    public List<DelMps> selectDelMpsAllList(DelMps delMps)
    {
        return delMpsMapper.selectDelMpsAllList(delMps);
    }

    /**
     * 新增主计划维护
     * 
     * @param delMps 主计划维护
     * @return 结果
     */
    @Override
    public int insertDelMps(DelMps delMps)
    {
        //创建戳
        delMps.setCreateBy(SecurityUtils.getUsername());
        delMps.setCreateTime(DateUtils.getNowDate());
        delMps.setUpdateBy(SecurityUtils.getUsername());
        delMps.setUpdateTime(DateUtils.getNowDate());
        return delMpsMapper.insertDelMps(delMps);
    }

    /**
     * 修改主计划维护
     * 
     * @param delMps 主计划维护
     * @return 结果
     */
    @Override
    public int updateDelMps(DelMps delMps)
    {
        delMps.refreshUpdatingStamp();
        return delMpsMapper.updateDelMps(delMps);
    }

    /**
     * 批量删除主计划维护
     * 
     * @param ids 需要删除的主计划维护主键
     * @return 结果
     */
    @Override
    public int deleteDelMpsByIds(Long[] ids)
    {
        return delMpsMapper.deleteDelMpsByIds(ids);
    }

    /**
     * 删除主计划维护信息
     * 
     * @param id 主计划维护主键
     * @return 结果
     */
    @Override
    public int deleteDelMpsById(Long id)
    {
        return delMpsMapper.deleteDelMpsById(id);
    }

    /**
     * 投用
     *
     * @param delMps mps
     * @return 结果
     */
    @Override
    public int enable(DelMps delMps){
        Long id = delMps.getId();
        DelMps obj = delMpsMapper.selectDelMpsById(id);

        obj.setStatus(BillStatus.BASE_ENABLE);
        obj.refreshUpdatingStamp();
        return delMpsMapper.updateDelMps(obj);
    }

    /**
     * 变更
     *
     * @param delMps 变更
     * @return 结果
     */
    @Override
    public int changeDelMps(DelMps delMps){
        delMps.refreshUpdatingStamp();
        return delMpsMapper.updateDelMps(delMps);
    }

     /**
      * 冻结
      *
      * @param delMps 冻结
      * @return 结果
      */
     @Override
     public int froze(DelMps delMps){
         Long id = delMps.getId();
         DelMps obj = delMpsMapper.selectDelMpsById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delMps");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return delMpsMapper.updateDelMps(obj);
      }

     /**
      * 解冻
      *
      * @param delMps 解冻
      * @return 结果
      */
     @Override
     public int unfroze(DelMps delMps){
         Long id = delMps.getId();
         DelMps obj = delMpsMapper.selectDelMpsById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("delMps");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             obj.refreshUpdatingStamp();
             return delMpsMapper.updateDelMps(obj);
         }else{
             return 0;
         }
     }

     /**
      * 停用
      *
      * @param delMps 停用
      * @return 结果
      */
     @Override
     public int disable(DelMps delMps){
         Long id = delMps.getId();
         DelMps obj = delMpsMapper.selectDelMpsById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return delMpsMapper.updateDelMps(obj);
     }

    /**
     * 通知工艺
     *
     * @param delMps 主计划项
     * @return 结果
     */
    @Override
    public int noticeEngineering(DelMps delMps) throws Exception{
        try{
            return mpsAlgorithm.noticeEngineering(delMps);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 转正料号
     *
     * @param delMps 主计划项
     * @return 结果
     */
    @Override
    public int normalizeCode(DelMps delMps) throws Exception{
        try{
            return mpsAlgorithm.normalizeCode(delMps);
        }catch (Exception ex){
            throw ex;
        }
    }

}

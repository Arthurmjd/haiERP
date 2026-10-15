package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

  import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnMlAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlServiceImpl implements IPlnMlService 
{
    @Autowired(required = false)
    private PlnMlMapper plnMlMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnMlAlgorithm plnMlAlgorithm;

    /**
     * 查询批次管理
     * 
     * @param id 批次管理主键
     * @return 批次管理
     */
    @Override
    public PlnMl selectPlnMlById(Long id)
    {
        return plnMlMapper.selectPlnMlById(id);
    }

    /**
     * 查询批次管理列表
     * 
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlList(plnMl);
    }

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlDraftList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlDraftList(plnMl);
    }

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlPublishingList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlPublishingList(plnMl);
    }

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlProcessingList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlProcessingList(plnMl);
    }

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlFinishingList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlFinishingList(plnMl);
    }

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlHistoryList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlHistoryList(plnMl);
    }

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理
     */
    @Override
    public List<PlnMl> selectPlnMlAllList(PlnMl plnMl)
    {
        return plnMlMapper.selectPlnMlAllList(plnMl);
    }

    /**
     * 新增批次管理
     * 
     * @param plnMl 批次管理
     * @return 结果
     */
    @Override
    public int insertPlnMl(PlnMl plnMl)
    {
        plnMl.setCreateTime(DateUtils.getNowDate());
        return plnMlMapper.insertPlnMl(plnMl);
    }

    /**
     * 修改批次管理
     * 
     * @param plnMl 批次管理
     * @return 结果
     */
    @Override
    public int updatePlnMl(PlnMl plnMl)
    {
        plnMl.refreshUpdatingStamp();
        return plnMlMapper.updatePlnMl(plnMl);
    }

    /**
     * 批量删除批次管理
     * 
     * @param ids 需要删除的批次管理主键
     * @return 结果
     */
    @Override
    public int deletePlnMlByIds(Long[] ids)
    {
        return plnMlMapper.deletePlnMlByIds(ids);
    }

    /**
     * 删除批次管理信息
     * 
     * @param id 批次管理主键
     * @return 结果
     */
    @Override
    public int deletePlnMlById(Long id)
    {
        return plnMlMapper.deletePlnMlById(id);
    }

    /**
     * 变更
     *
     * @param plnMl 变更
     * @return 结果
     */
    @Override
    public int changeMl(PlnMl plnMl) throws Exception{
        try{
            return plnMlAlgorithm.changeMl(plnMl);
        }catch (Exception ex){
            throw ex;
        }
    }
     /**
      * 冻结
      *
      * @param plnMl 冻结
      * @return 结果
      */
     @Override
     public int frozeMl(PlnMl plnMl){
         Long id = plnMl.getId();
         PlnMl obj = plnMlMapper.selectPlnMlById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("plnMl");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         frozen.refreshCreatingStamp();
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return plnMlMapper.updatePlnMl(obj);
      }
     /**
      * 停用
      *
      * @param plnMl 停用
      * @return 结果
      */
     @Override
     public int disableMl(PlnMl plnMl){
         Long id = plnMl.getId();
         PlnMl obj = plnMlMapper.selectPlnMlById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return plnMlMapper.updatePlnMl(obj);
     }

    /**
     * 变更
     *
     * @param plnMl 变更
     * @return 结果
     */
    @Override
    public int change(PlnMl plnMl) throws Exception{
        try{
            return plnMlAlgorithm.change(plnMl);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 冻结
     *
     * @param plnMl 冻结
     * @return 结果
     */
    @Override
    public int froze(PlnMl plnMl){
        Long id = plnMl.getId();
        PlnMl obj = plnMlMapper.selectPlnMlById(id);

        SysBillFrozen frozen= new SysBillFrozen();
        frozen.setClassName("plnMl");
        frozen.setObjectId(id);
        frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
        sysBillFrozenMapper.insertSysBillFrozen(frozen);

        obj.setStatus(BillStatus.LIFE_FROZEN);
        obj.refreshUpdatingStamp();
        return plnMlMapper.updatePlnMl(obj);
    }
    /**
     * 停用
     *
     * @param plnMl 停用
     * @return 结果
     */
    @Override
    public int disable(PlnMl plnMl){
        Long id = plnMl.getId();
        PlnMl obj = plnMlMapper.selectPlnMlById(id);

        obj.setStatus(BillStatus.LIFE_DEAD);
        obj.refreshUpdatingStamp();
        return plnMlMapper.updatePlnMl(obj);
    }
}

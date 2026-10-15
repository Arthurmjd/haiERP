package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnWoAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
@Service
public class PlnWoServiceImpl implements IPlnWoService 
{
    @Autowired(required = false)
    private PlnWoMapper plnWoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnWoAlgorithm plnWoAlgorithm;

    /**
     * 查询工单管理
     * 
     * @param id 工单管理主键
     * @return 工单管理
     */
    @Override
    public PlnWo selectPlnWoById(Long id)
    {
        return plnWoMapper.selectPlnWoById(id);
    }

    /**
     * 查询工单管理列表
     * 
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoList(plnWo);
    }

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoDraftList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoDraftList(plnWo);
    }

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoPublishingList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoPublishingList(plnWo);
    }

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoProcessingList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoProcessingList(plnWo);
    }

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoFinishingList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoFinishingList(plnWo);
    }

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoHistoryList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoHistoryList(plnWo);
    }

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理
     */
    @Override
    public List<PlnWo> selectPlnWoAllList(PlnWo plnWo)
    {
        return plnWoMapper.selectPlnWoAllList(plnWo);
    }

    /**
     * 新增工单管理
     * 
     * @param plnWo 工单管理
     * @return 结果
     */
    @Override
    public int insertPlnWo(PlnWo plnWo)
    {
        plnWo.setCreateTime(DateUtils.getNowDate());
        return plnWoMapper.insertPlnWo(plnWo);
    }

    /**
     * 修改工单管理
     * 
     * @param plnWo 工单管理
     * @return 结果
     */
    @Override
    public int updatePlnWo(PlnWo plnWo)
    {
        plnWo.setUpdateTime(DateUtils.getNowDate());
        return plnWoMapper.updatePlnWo(plnWo);
    }

    /**
     * 批量删除工单管理
     * 
     * @param ids 需要删除的工单管理主键
     * @return 结果
     */
    @Override
    public int deletePlnWoByIds(Long[] ids)
    {
        return plnWoMapper.deletePlnWoByIds(ids);
    }

    /**
     * 删除工单管理信息
     * 
     * @param id 工单管理主键
     * @return 结果
     */
    @Override
    public int deletePlnWoById(Long id)
    {
        return plnWoMapper.deletePlnWoById(id);
    }

     /**
      * 发放
      *
      * @param plnWo 发放
      * @return 结果
      */
     @Override
     public int enable(PlnWo plnWo){
         Long id = plnWo.getId();
         PlnWo obj = plnWoMapper.selectPlnWoById(id);

         obj.setStatus(BillStatus.BASE_ENABLE);
         obj.refreshUpdatingStamp();
         return plnWoMapper.updatePlnWo(obj);
     }
    /**
     * 变更
     *
     * @param plnWo 变更
     * @return 结果
     */
    @Override
    public int changeWo(PlnWo plnWo) throws Exception{
        try{
            return plnWoAlgorithm.changeWo(plnWo);
        }catch (Exception ex){
            throw ex;
        }
    }
     /**
      * 冻结
      *
      * @param plnWo 冻结
      * @return 结果
      */
     @Override
     public int frozeWo(PlnWo plnWo){
         Long id = plnWo.getId();
         PlnWo obj = plnWoMapper.selectPlnWoById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("plnWo");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return plnWoMapper.updatePlnWo(obj);
      }
     /**
      * 停用
      *
      * @param plnWo 停用
      * @return 结果
      */
     @Override
     public int disableWo(PlnWo plnWo){
         Long id = plnWo.getId();
         PlnWo obj = plnWoMapper.selectPlnWoById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return plnWoMapper.updatePlnWo(obj);
     }

}

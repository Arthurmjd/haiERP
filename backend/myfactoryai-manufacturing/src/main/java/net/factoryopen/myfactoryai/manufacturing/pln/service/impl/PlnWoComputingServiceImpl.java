package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnWoComputingAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoComputingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoComputing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoComputingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 计划分解Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class PlnWoComputingServiceImpl implements IPlnWoComputingService 
{
    @Autowired(required = false)
    private PlnWoComputingMapper plnWoComputingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnWoComputingAlgorithm plnWoComputingAlgorithm;

    /**
     * 查询计划分解
     * 
     * @param id 计划分解主键
     * @return 计划分解
     */
    @Override
    public PlnWoComputing selectPlnWoComputingById(Long id)
    {
        return plnWoComputingMapper.selectPlnWoComputingById(id);
    }

    /**
     * 查询计划分解列表
     * 
     * @param plnWoComputing 计划分解
     * @return 计划分解
     */
    @Override
    public List<PlnWoComputing> selectPlnWoComputingList(PlnWoComputing plnWoComputing)
    {
        return plnWoComputingMapper.selectPlnWoComputingList(plnWoComputing);
    }

    /**
     * 新增计划分解
     * 
     * @param plnWoComputing 计划分解
     * @return 结果
     */
    @Override
    public int insertPlnWoComputing(PlnWoComputing plnWoComputing)
    {
        plnWoComputing.setCreateTime(DateUtils.getNowDate());
        return plnWoComputingMapper.insertPlnWoComputing(plnWoComputing);
    }

    /**
     * 修改计划分解
     * 
     * @param plnWoComputing 计划分解
     * @return 结果
     */
    @Override
    public int updatePlnWoComputing(PlnWoComputing plnWoComputing)
    {
        plnWoComputing.setUpdateTime(DateUtils.getNowDate());
        return plnWoComputingMapper.updatePlnWoComputing(plnWoComputing);
    }

    /**
     * 批量删除计划分解
     * 
     * @param ids 需要删除的计划分解主键
     * @return 结果
     */
    @Override
    public int deletePlnWoComputingByIds(Long[] ids)
    {
        return plnWoComputingMapper.deletePlnWoComputingByIds(ids);
    }

    /**
     * 删除计划分解信息
     * 
     * @param id 计划分解主键
     * @return 结果
     */
    @Override
    public int deletePlnWoComputingById(Long id)
    {
        return plnWoComputingMapper.deletePlnWoComputingById(id);
    }

      /**
       * 分解
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int splitMps(Long[] ids) throws Exception{
          try{
              return plnWoComputingAlgorithm.splitMps(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

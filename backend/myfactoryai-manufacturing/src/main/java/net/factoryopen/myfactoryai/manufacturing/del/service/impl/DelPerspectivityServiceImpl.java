package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.del.DelPerspectivityAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPerspectivityMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivity;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPerspectivityService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单透视表Service业务层处理
 * 
 * @author admin
 * @date 2024-07-23
 */
@Service
public class DelPerspectivityServiceImpl implements IDelPerspectivityService 
{
    @Autowired(required = false)
    private DelPerspectivityMapper delPerspectivityMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private DelPerspectivityAlgorithm delPerspectivityAlgorithm;

    /**
     * 查询订单透视表
     * 
     * @param id 订单透视表主键
     * @return 订单透视表
     */
    @Override
    public DelPerspectivity selectDelPerspectivityById(Long id)
    {
        return delPerspectivityMapper.selectDelPerspectivityById(id);
    }

    /**
     * 查询订单透视表列表
     * 
     * @param delPerspectivity 订单透视表
     * @return 订单透视表
     */
    @Override
    public List<DelPerspectivity> selectDelPerspectivityList(DelPerspectivity delPerspectivity)
    {
        return delPerspectivityMapper.selectDelPerspectivityList(delPerspectivity);
    }

    /**
     * 新增订单透视表
     * 
     * @param delPerspectivity 订单透视表
     * @return 结果
     */
    @Override
    public int insertDelPerspectivity(DelPerspectivity delPerspectivity)
    {
        return delPerspectivityMapper.insertDelPerspectivity(delPerspectivity);
    }

    /**
     * 修改订单透视表
     * 
     * @param delPerspectivity 订单透视表
     * @return 结果
     */
    @Override
    public int updateDelPerspectivity(DelPerspectivity delPerspectivity)
    {
        return delPerspectivityMapper.updateDelPerspectivity(delPerspectivity);
    }

    /**
     * 批量删除订单透视表
     * 
     * @param ids 需要删除的订单透视表主键
     * @return 结果
     */
    @Override
    public int deleteDelPerspectivityByIds(Long[] ids)
    {
        return delPerspectivityMapper.deleteDelPerspectivityByIds(ids);
    }

    /**
     * 删除订单透视表信息
     * 
     * @param id 订单透视表主键
     * @return 结果
     */
    @Override
    public int deleteDelPerspectivityById(Long id)
    {
        return delPerspectivityMapper.deleteDelPerspectivityById(id);
    }

      /**
       * 透算
       *
       * @param delPerspectivity 透算
       * @return 结果
       */
      @Override
      public int calculatePerspectivity(DelPerspectivity delPerspectivity) throws Exception{
          try{
              return delPerspectivityAlgorithm.calculatePerspectivity(delPerspectivity);
          }catch (Exception ex){
              throw ex;
          }
      }

}

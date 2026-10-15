package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.del.DelOsplanReceivingAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanReceivingMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplanReceiving;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelOsplanReceivingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 外协收货Service业务层处理
 * 
 * @author admin
 * @date 2024-07-09
 */
@Service
public class DelOsplanReceivingServiceImpl implements IDelOsplanReceivingService 
{
    @Autowired(required = false)
    private DelOsplanReceivingMapper delOsplanReceivingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private DelOsplanReceivingAlgorithm delOsplanReceivingAlgorithm;

    /**
     * 查询外协收货
     * 
     * @param id 外协收货主键
     * @return 外协收货
     */
    @Override
    public DelOsplanReceiving selectDelOsplanReceivingById(Long id)
    {
        return delOsplanReceivingMapper.selectDelOsplanReceivingById(id);
    }

    /**
     * 查询外协收货列表
     * 
     * @param delOsplanReceiving 外协收货
     * @return 外协收货
     */
    @Override
    public List<DelOsplanReceiving> selectDelOsplanReceivingList(DelOsplanReceiving delOsplanReceiving)
    {
        return delOsplanReceivingMapper.selectDelOsplanReceivingList(delOsplanReceiving);
    }

    /**
     * 新增外协收货
     * 
     * @param delOsplanReceiving 外协收货
     * @return 结果
     */
    @Override
    public int insertDelOsplanReceiving(DelOsplanReceiving delOsplanReceiving)
    {
        delOsplanReceiving.setCreateTime(DateUtils.getNowDate());
        return delOsplanReceivingMapper.insertDelOsplanReceiving(delOsplanReceiving);
    }

    /**
     * 修改外协收货
     * 
     * @param delOsplanReceiving 外协收货
     * @return 结果
     */
    @Override
    public int updateDelOsplanReceiving(DelOsplanReceiving delOsplanReceiving)
    {
        delOsplanReceiving.setUpdateTime(DateUtils.getNowDate());
        return delOsplanReceivingMapper.updateDelOsplanReceiving(delOsplanReceiving);
    }

    /**
     * 批量删除外协收货
     * 
     * @param ids 需要删除的外协收货主键
     * @return 结果
     */
    @Override
    public int deleteDelOsplanReceivingByIds(Long[] ids)
    {
        return delOsplanReceivingMapper.deleteDelOsplanReceivingByIds(ids);
    }

    /**
     * 删除外协收货信息
     * 
     * @param id 外协收货主键
     * @return 结果
     */
    @Override
    public int deleteDelOsplanReceivingById(Long id)
    {
        return delOsplanReceivingMapper.deleteDelOsplanReceivingById(id);
    }


      /**
       * 请检
       *
       * @param delOsplanReceiving 请检
       * @return 结果
       */
      @Override
      public int requestInspection(DelOsplanReceiving delOsplanReceiving) throws Exception{
          try{
              return delOsplanReceivingAlgorithm.requestInspection(delOsplanReceiving);
          }catch (Exception ex){
              throw ex;
          }
      }
}

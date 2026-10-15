package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnMaterialComputingAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMaterialComputingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMaterialComputingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料计算Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class PlnMaterialComputingServiceImpl implements IPlnMaterialComputingService 
{
    @Autowired(required = false)
    private PlnMaterialComputingMapper plnMaterialComputingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnMaterialComputingAlgorithm plnMaterialComputingAlgorithm;

    /**
     * 查询物料计算
     * 
     * @param id 物料计算主键
     * @return 物料计算
     */
    @Override
    public PlnMaterialComputing selectPlnMaterialComputingById(Long id)
    {
        return plnMaterialComputingMapper.selectPlnMaterialComputingById(id);
    }

    /**
     * 查询物料计算列表
     * 
     * @param plnMaterialComputing 物料计算
     * @return 物料计算
     */
    @Override
    public List<PlnMaterialComputing> selectPlnMaterialComputingList(PlnMaterialComputing plnMaterialComputing)
    {
        return plnMaterialComputingMapper.selectPlnMaterialComputingList(plnMaterialComputing);
    }

    /**
     * 新增物料计算
     * 
     * @param plnMaterialComputing 物料计算
     * @return 结果
     */
    @Override
    public int insertPlnMaterialComputing(PlnMaterialComputing plnMaterialComputing)
    {
        plnMaterialComputing.setCreateTime(DateUtils.getNowDate());
        return plnMaterialComputingMapper.insertPlnMaterialComputing(plnMaterialComputing);
    }

    /**
     * 修改物料计算
     * 
     * @param plnMaterialComputing 物料计算
     * @return 结果
     */
    @Override
    public int updatePlnMaterialComputing(PlnMaterialComputing plnMaterialComputing)
    {
        plnMaterialComputing.refreshUpdatingStamp();
        return plnMaterialComputingMapper.updatePlnMaterialComputing(plnMaterialComputing);
    }

    /**
     * 批量删除物料计算
     * 
     * @param ids 需要删除的物料计算主键
     * @return 结果
     */
    @Override
    public int deletePlnMaterialComputingByIds(Long[] ids)
    {
        return plnMaterialComputingMapper.deletePlnMaterialComputingByIds(ids);
    }

    /**
     * 删除物料计算信息
     * 
     * @param id 物料计算主键
     * @return 结果
     */
    @Override
    public int deletePlnMaterialComputingById(Long id)
    {
        return plnMaterialComputingMapper.deletePlnMaterialComputingById(id);
    }

      /**
       * 算料
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int runMrp(Long[] ids) throws Exception{
          try{
              return plnMaterialComputingAlgorithm.runMrp(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

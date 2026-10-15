package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.qua.QuaInspectReturnAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectReturnMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReturn;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectReturnService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 退货检异登记Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class QuaInspectReturnServiceImpl implements IQuaInspectReturnService 
{
    @Autowired(required = false)
    private QuaInspectReturnMapper quaInspectReturnMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private QuaInspectReturnAlgorithm quaInspectReturnAlgorithm;

    /**
     * 查询退货检异登记
     * 
     * @param id 退货检异登记主键
     * @return 退货检异登记
     */
    @Override
    public QuaInspectReturn selectQuaInspectReturnById(Long id)
    {
        return quaInspectReturnMapper.selectQuaInspectReturnById(id);
    }

    /**
     * 查询退货检异登记列表
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 退货检异登记
     */
    @Override
    public List<QuaInspectReturn> selectQuaInspectReturnList(QuaInspectReturn quaInspectReturn)
    {
        return quaInspectReturnMapper.selectQuaInspectReturnList(quaInspectReturn);
    }

    /**
     * 新增退货检异登记
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 结果
     */
    @Override
    public int insertQuaInspectReturn(QuaInspectReturn quaInspectReturn)
    {
        quaInspectReturn.setCreateTime(DateUtils.getNowDate());
        return quaInspectReturnMapper.insertQuaInspectReturn(quaInspectReturn);
    }

    /**
     * 修改退货检异登记
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 结果
     */
    @Override
    public int updateQuaInspectReturn(QuaInspectReturn quaInspectReturn)
    {
        quaInspectReturn.setUpdateTime(DateUtils.getNowDate());
        return quaInspectReturnMapper.updateQuaInspectReturn(quaInspectReturn);
    }

    /**
     * 批量删除退货检异登记
     * 
     * @param ids 需要删除的退货检异登记主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectReturnByIds(Long[] ids)
    {
        return quaInspectReturnMapper.deleteQuaInspectReturnByIds(ids);
    }

    /**
     * 删除退货检异登记信息
     * 
     * @param id 退货检异登记主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectReturnById(Long id)
    {
        return quaInspectReturnMapper.deleteQuaInspectReturnById(id);
    }

      /**
       * 请判
       *
       * @param quaInspectReturn 请判
       * @return 结果
       */
      @Override
      public int requestDeal(QuaInspectReturn quaInspectReturn) throws Exception{
          try{
              return quaInspectReturnAlgorithm.requestDeal(quaInspectReturn);
          }catch (Exception ex){
              throw ex;
          }
      }

}

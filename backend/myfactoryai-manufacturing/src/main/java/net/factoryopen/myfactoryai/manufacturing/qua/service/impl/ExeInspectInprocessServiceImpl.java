package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.qua.ExeInspectInprocessAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.ExeInspectInprocessMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectInprocess;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IExeInspectInprocessService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 制检异常登记Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class ExeInspectInprocessServiceImpl implements IExeInspectInprocessService 
{
    @Autowired(required = false)
    private ExeInspectInprocessMapper exeInspectInprocessMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private ExeInspectInprocessAlgorithm exeInspectInprocessAlgorithm;

    /**
     * 查询制检异常登记
     * 
     * @param id 制检异常登记主键
     * @return 制检异常登记
     */
    @Override
    public ExeInspectInprocess selectExeInspectInprocessById(Long id)
    {
        return exeInspectInprocessMapper.selectExeInspectInprocessById(id);
    }

    /**
     * 查询制检异常登记列表
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 制检异常登记
     */
    @Override
    public List<ExeInspectInprocess> selectExeInspectInprocessList(ExeInspectInprocess exeInspectInprocess)
    {
        return exeInspectInprocessMapper.selectExeInspectInprocessList(exeInspectInprocess);
    }

    /**
     * 新增制检异常登记
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 结果
     */
    @Override
    public int insertExeInspectInprocess(ExeInspectInprocess exeInspectInprocess)
    {
        exeInspectInprocess.setCreateTime(DateUtils.getNowDate());
        return exeInspectInprocessMapper.insertExeInspectInprocess(exeInspectInprocess);
    }

    /**
     * 修改制检异常登记
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 结果
     */
    @Override
    public int updateExeInspectInprocess(ExeInspectInprocess exeInspectInprocess)
    {
        exeInspectInprocess.setUpdateTime(DateUtils.getNowDate());
        return exeInspectInprocessMapper.updateExeInspectInprocess(exeInspectInprocess);
    }

    /**
     * 批量删除制检异常登记
     * 
     * @param ids 需要删除的制检异常登记主键
     * @return 结果
     */
    @Override
    public int deleteExeInspectInprocessByIds(Long[] ids)
    {
        return exeInspectInprocessMapper.deleteExeInspectInprocessByIds(ids);
    }

    /**
     * 删除制检异常登记信息
     * 
     * @param id 制检异常登记主键
     * @return 结果
     */
    @Override
    public int deleteExeInspectInprocessById(Long id)
    {
        return exeInspectInprocessMapper.deleteExeInspectInprocessById(id);
    }

      /**
       * 请判
       *
       * @param exeInspectInprocess 请判
       * @return 结果
       */
      @Override
      public int requestDeal(ExeInspectInprocess exeInspectInprocess) throws Exception{
          try{
              return exeInspectInprocessAlgorithm.requestDeal(exeInspectInprocess);
          }catch (Exception ex){
              throw ex;
          }
      }

}

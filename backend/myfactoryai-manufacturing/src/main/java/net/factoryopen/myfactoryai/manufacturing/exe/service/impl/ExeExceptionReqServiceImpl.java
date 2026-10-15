package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.exe.ExeExceptionReqAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeExceptionReqMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeExceptionReq;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeExceptionReqService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺异常登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class ExeExceptionReqServiceImpl implements IExeExceptionReqService 
{
    @Autowired(required = false)
    private ExeExceptionReqMapper exeExceptionReqMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private ExeExceptionReqAlgorithm exeExceptionReqAlgorithm;

    /**
     * 查询工艺异常登记
     * 
     * @param id 工艺异常登记主键
     * @return 工艺异常登记
     */
    @Override
    public ExeExceptionReq selectExeExceptionReqById(Long id)
    {
        return exeExceptionReqMapper.selectExeExceptionReqById(id);
    }

    /**
     * 查询工艺异常登记列表
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 工艺异常登记
     */
    @Override
    public List<ExeExceptionReq> selectExeExceptionReqList(ExeExceptionReq exeExceptionReq)
    {
        return exeExceptionReqMapper.selectExeExceptionReqList(exeExceptionReq);
    }

    /**
     * 新增工艺异常登记
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 结果
     */
    @Override
    public int insertExeExceptionReq(ExeExceptionReq exeExceptionReq)
    {
        exeExceptionReq.setCreateTime(DateUtils.getNowDate());
        return exeExceptionReqMapper.insertExeExceptionReq(exeExceptionReq);
    }

    /**
     * 修改工艺异常登记
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 结果
     */
    @Override
    public int updateExeExceptionReq(ExeExceptionReq exeExceptionReq)
    {
        exeExceptionReq.setUpdateTime(DateUtils.getNowDate());
        return exeExceptionReqMapper.updateExeExceptionReq(exeExceptionReq);
    }

    /**
     * 批量删除工艺异常登记
     * 
     * @param ids 需要删除的工艺异常登记主键
     * @return 结果
     */
    @Override
    public int deleteExeExceptionReqByIds(Long[] ids)
    {
        return exeExceptionReqMapper.deleteExeExceptionReqByIds(ids);
    }

    /**
     * 删除工艺异常登记信息
     * 
     * @param id 工艺异常登记主键
     * @return 结果
     */
    @Override
    public int deleteExeExceptionReqById(Long id)
    {
        return exeExceptionReqMapper.deleteExeExceptionReqById(id);
    }

      /**
       * 请判
       *
       * @param exeExceptionReq 请判
       * @return 结果
       */
      @Override
      public int requestDeal(ExeExceptionReq exeExceptionReq) throws Exception{
          try{
              return exeExceptionReqAlgorithm.requestDeal(exeExceptionReq);
          }catch (Exception ex){
              throw ex;
          }
      }

}

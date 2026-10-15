package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

     import net.factoryopen.core.myfactoryai.manufacturing.exe.ExeProcessOperatingAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessOperatingMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessOperating;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessOperatingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产线班组作业Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class ExeProcessOperatingServiceImpl implements IExeProcessOperatingService 
{
    @Autowired(required = false)
    private ExeProcessOperatingMapper exeProcessOperatingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private ExeProcessOperatingAlgorithm exeProcessOperatingAlgorithm;

    /**
     * 查询产线班组作业
     * 
     * @param id 产线班组作业主键
     * @return 产线班组作业
     */
    @Override
    public ExeProcessOperating selectExeProcessOperatingById(Long id)
    {
        return exeProcessOperatingMapper.selectExeProcessOperatingById(id);
    }

    /**
     * 查询产线班组作业列表
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 产线班组作业
     */
    @Override
    public List<ExeProcessOperating> selectExeProcessOperatingList(ExeProcessOperating exeProcessOperating)
    {
        return exeProcessOperatingMapper.selectExeProcessOperatingList(exeProcessOperating);
    }

    /**
     * 新增产线班组作业
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 结果
     */
    @Override
    public int insertExeProcessOperating(ExeProcessOperating exeProcessOperating)
    {
        exeProcessOperating.setCreateTime(DateUtils.getNowDate());
        return exeProcessOperatingMapper.insertExeProcessOperating(exeProcessOperating);
    }

    /**
     * 修改产线班组作业
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 结果
     */
    @Override
    public int updateExeProcessOperating(ExeProcessOperating exeProcessOperating)
    {
        exeProcessOperating.setUpdateTime(DateUtils.getNowDate());
        return exeProcessOperatingMapper.updateExeProcessOperating(exeProcessOperating);
    }

    /**
     * 批量删除产线班组作业
     * 
     * @param ids 需要删除的产线班组作业主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessOperatingByIds(Long[] ids)
    {
        return exeProcessOperatingMapper.deleteExeProcessOperatingByIds(ids);
    }

    /**
     * 删除产线班组作业信息
     * 
     * @param id 产线班组作业主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessOperatingById(Long id)
    {
        return exeProcessOperatingMapper.deleteExeProcessOperatingById(id);
    }

      /**
       * 接收
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int acceptMl(Long[] ids) throws Exception{
          try{
              return exeProcessOperatingAlgorithm.acceptMl(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
      /**
       * 请料
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int requestMaterial(Long[] ids) throws Exception{
          try{
              return exeProcessOperatingAlgorithm.requestMaterial(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
      /**
       * 转出
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int postMl(Long[] ids) throws Exception{
          try{
              return exeProcessOperatingAlgorithm.postMl(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
    /**
     * 开工
     *
     * @param exeProcessOperating 开工
     * @return 结果
     */
    @Override
    public int startWork(ExeProcessOperating exeProcessOperating) throws Exception{
        try{
            return exeProcessOperatingAlgorithm.startWork(exeProcessOperating);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 完工
     *
     * @param exeProcessOperating 完工
     * @return 结果
     */
    @Override
    public int finishWork(ExeProcessOperating exeProcessOperating) throws Exception{
        try{
            return exeProcessOperatingAlgorithm.finishWork(exeProcessOperating);
        }catch (Exception ex){
            throw ex;
        }
    }

}

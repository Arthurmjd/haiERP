package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.exe.ExeMlStockinAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlStockinMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlStockin;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlStockinService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产成品入库Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ExeMlStockinServiceImpl implements IExeMlStockinService 
{
    @Autowired(required = false)
    private ExeMlStockinMapper exeMlStockinMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private ExeMlStockinAlgorithm exeMlStockinAlgorithm;

    /**
     * 查询产成品入库
     * 
     * @param id 产成品入库主键
     * @return 产成品入库
     */
    @Override
    public ExeMlStockin selectExeMlStockinById(Long id)
    {
        return exeMlStockinMapper.selectExeMlStockinById(id);
    }

    /**
     * 查询产成品入库列表
     * 
     * @param exeMlStockin 产成品入库
     * @return 产成品入库
     */
    @Override
    public List<ExeMlStockin> selectExeMlStockinList(ExeMlStockin exeMlStockin)
    {
        return exeMlStockinMapper.selectExeMlStockinList(exeMlStockin);
    }

    /**
     * 新增产成品入库
     * 
     * @param exeMlStockin 产成品入库
     * @return 结果
     */
    @Override
    public int insertExeMlStockin(ExeMlStockin exeMlStockin)
    {
        exeMlStockin.setCreateTime(DateUtils.getNowDate());
        return exeMlStockinMapper.insertExeMlStockin(exeMlStockin);
    }

    /**
     * 修改产成品入库
     * 
     * @param exeMlStockin 产成品入库
     * @return 结果
     */
    @Override
    public int updateExeMlStockin(ExeMlStockin exeMlStockin)
    {
        exeMlStockin.setUpdateTime(DateUtils.getNowDate());
        return exeMlStockinMapper.updateExeMlStockin(exeMlStockin);
    }

    /**
     * 批量删除产成品入库
     * 
     * @param ids 需要删除的产成品入库主键
     * @return 结果
     */
    @Override
    public int deleteExeMlStockinByIds(Long[] ids)
    {
        return exeMlStockinMapper.deleteExeMlStockinByIds(ids);
    }

    /**
     * 删除产成品入库信息
     * 
     * @param id 产成品入库主键
     * @return 结果
     */
    @Override
    public int deleteExeMlStockinById(Long id)
    {
        return exeMlStockinMapper.deleteExeMlStockinById(id);
    }

      /**
       * 入库
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int productStockin(Long[] ids) throws Exception{
          try{
              return exeMlStockinAlgorithm.productStockin(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

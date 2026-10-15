package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

  import net.factoryopen.core.myfactoryai.manufacturing.eng.EngCategoryEngineeringAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngCategoryEngineeringMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryEngineeringService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 品类工艺Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
@Service
public class EngCategoryEngineeringServiceImpl implements IEngCategoryEngineeringService 
{
    @Autowired(required = false)
    private EngCategoryEngineeringMapper engCategoryEngineeringMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private EngCategoryEngineeringAlgorithm engCategoryEngineeringAlgorithm;

    /**
     * 查询品类工艺
     * 
     * @param id 品类工艺主键
     * @return 品类工艺
     */
    @Override
    public EngCategoryEngineering selectEngCategoryEngineeringById(Long id)
    {
        return engCategoryEngineeringMapper.selectEngCategoryEngineeringById(id);
    }

    /**
     * 查询品类工艺列表
     * 
     * @param engCategoryEngineering 品类工艺
     * @return 品类工艺
     */
    @Override
    public List<EngCategoryEngineering> selectEngCategoryEngineeringList(EngCategoryEngineering engCategoryEngineering)
    {
        return engCategoryEngineeringMapper.selectEngCategoryEngineeringList(engCategoryEngineering);
    }

    /**
     * 新增品类工艺
     * 
     * @param engCategoryEngineering 品类工艺
     * @return 结果
     */
    @Override
    public int insertEngCategoryEngineering(EngCategoryEngineering engCategoryEngineering)
    {
        engCategoryEngineering.setCreateTime(DateUtils.getNowDate());
        return engCategoryEngineeringMapper.insertEngCategoryEngineering(engCategoryEngineering);
    }

    /**
     * 修改品类工艺
     * 
     * @param engCategoryEngineering 品类工艺
     * @return 结果
     */
    @Override
    public int updateEngCategoryEngineering(EngCategoryEngineering engCategoryEngineering)
    {
        engCategoryEngineering.setUpdateTime(DateUtils.getNowDate());
        return engCategoryEngineeringMapper.updateEngCategoryEngineering(engCategoryEngineering);
    }

    /**
     * 批量删除品类工艺
     * 
     * @param ids 需要删除的品类工艺主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryEngineeringByIds(Long[] ids)
    {
        return engCategoryEngineeringMapper.deleteEngCategoryEngineeringByIds(ids);
    }

    /**
     * 删除品类工艺信息
     * 
     * @param id 品类工艺主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryEngineeringById(Long id)
    {
        return engCategoryEngineeringMapper.deleteEngCategoryEngineeringById(id);
    }

      /**
       * 填补子孙
       *
       * @param engCategoryEngineering 填补子孙
       * @return 结果
       */
      @Override
      public int fillChildren(EngCategoryEngineering engCategoryEngineering) throws Exception{
          try{
              return engCategoryEngineeringAlgorithm.fillChildren(engCategoryEngineering);
          }catch (Exception ex){
              throw ex;
          }
      }
      /**
       * 覆盖子孙
       *
       * @param engCategoryEngineering 覆盖子孙
       * @return 结果
       */
      @Override
      public int coverChildren(EngCategoryEngineering engCategoryEngineering) throws Exception{
          try{
              return engCategoryEngineeringAlgorithm.coverChildren(engCategoryEngineering);
          }catch (Exception ex){
              throw ex;
          }
      }

}

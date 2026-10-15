package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.qua.QuaInspectReDealAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectReDealMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectReDealService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 质检请求处理Service业务层处理
 * 
 * @author admin
 * @date 2024-10-06
 */
@Service
public class QuaInspectReDealServiceImpl implements IQuaInspectReDealService 
{
    @Autowired(required = false)
    private QuaInspectReDealMapper quaInspectReDealMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private QuaInspectReDealAlgorithm quaInspectReDealAlgorithm;

    /**
     * 查询质检请求处理
     * 
     * @param id 质检请求处理主键
     * @return 质检请求处理
     */
    @Override
    public QuaInspectReDeal selectQuaInspectReDealById(Long id)
    {
        return quaInspectReDealMapper.selectQuaInspectReDealById(id);
    }

    /**
     * 查询质检请求处理列表
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 质检请求处理
     */
    @Override
    public List<QuaInspectReDeal> selectQuaInspectReDealList(QuaInspectReDeal quaInspectReDeal)
    {
        return quaInspectReDealMapper.selectQuaInspectReDealList(quaInspectReDeal);
    }

    /**
     * 新增质检请求处理
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 结果
     */
    @Override
    public int insertQuaInspectReDeal(QuaInspectReDeal quaInspectReDeal)
    {
        quaInspectReDeal.refreshCreatingStamp();
        return quaInspectReDealMapper.insertQuaInspectReDeal(quaInspectReDeal);
    }

    /**
     * 修改质检请求处理
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 结果
     */
    @Override
    public int updateQuaInspectReDeal(QuaInspectReDeal quaInspectReDeal)
    {
        quaInspectReDeal.refreshUpdatingStamp();
        return quaInspectReDealMapper.updateQuaInspectReDeal(quaInspectReDeal);
    }

    /**
     * 批量删除质检请求处理
     * 
     * @param ids 需要删除的质检请求处理主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectReDealByIds(Long[] ids)
    {
        return quaInspectReDealMapper.deleteQuaInspectReDealByIds(ids);
    }

    /**
     * 删除质检请求处理信息
     * 
     * @param id 质检请求处理主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectReDealById(Long id)
    {
        return quaInspectReDealMapper.deleteQuaInspectReDealById(id);
    }

      /**
       * 收检
       *
       * @param quaInspectReDeal 收检
       * @return 结果
       */
      @Override
      public int acceptInspection(QuaInspectReDeal quaInspectReDeal) throws Exception{
          try{
              return quaInspectReDealAlgorithm.acceptInspection(quaInspectReDeal);
          }catch (Exception ex){
              throw ex;
          }
      }

}

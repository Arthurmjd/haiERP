package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

  import net.factoryopen.core.myfactoryai.manufacturing.qua.QuaInspectRequestItemAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequestItem;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectRequestItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 请检Service业务层处理
 * 
 * @author admin
 * @date 2024-10-06
 */
@Service
public class QuaInspectRequestItemServiceImpl implements IQuaInspectRequestItemService 
{
    @Autowired(required = false)
    private QuaInspectRequestItemMapper quaInspectRequestItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private QuaInspectRequestItemAlgorithm quaInspectRequestItemAlgorithm;

    /**
     * 查询请检
     * 
     * @param id 请检主键
     * @return 请检
     */
    @Override
    public QuaInspectRequestItem selectQuaInspectRequestItemById(Long id)
    {
        return quaInspectRequestItemMapper.selectQuaInspectRequestItemById(id);
    }

    /**
     * 查询请检列表
     * 
     * @param quaInspectRequestItem 请检
     * @return 请检
     */
    @Override
    public List<QuaInspectRequestItem> selectQuaInspectRequestItemList(QuaInspectRequestItem quaInspectRequestItem)
    {
        return quaInspectRequestItemMapper.selectQuaInspectRequestItemList(quaInspectRequestItem);
    }

    /**
     * 新增请检
     * 
     * @param quaInspectRequestItem 请检
     * @return 结果
     */
    @Override
    public int insertQuaInspectRequestItem(QuaInspectRequestItem quaInspectRequestItem)
    {
        quaInspectRequestItem.setCreateTime(DateUtils.getNowDate());
        return quaInspectRequestItemMapper.insertQuaInspectRequestItem(quaInspectRequestItem);
    }

    /**
     * 修改请检
     * 
     * @param quaInspectRequestItem 请检
     * @return 结果
     */
    @Override
    public int updateQuaInspectRequestItem(QuaInspectRequestItem quaInspectRequestItem)
    {
        quaInspectRequestItem.setUpdateTime(DateUtils.getNowDate());
        return quaInspectRequestItemMapper.updateQuaInspectRequestItem(quaInspectRequestItem);
    }

    /**
     * 批量删除请检
     * 
     * @param ids 需要删除的请检主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectRequestItemByIds(Long[] ids)
    {
        return quaInspectRequestItemMapper.deleteQuaInspectRequestItemByIds(ids);
    }

    /**
     * 删除请检信息
     * 
     * @param id 请检主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectRequestItemById(Long id)
    {
        return quaInspectRequestItemMapper.deleteQuaInspectRequestItemById(id);
    }

    /**
     * 检良
     *
     * @param quaInspectRequestItem 检异
     * @return 结果
     */
    @Override
    public int registerGoodInspection(Long requestId, QuaInspectRequestItem quaInspectRequestItem) throws Exception{
        try{
            return quaInspectRequestItemAlgorithm.registerGoodInspection(requestId, quaInspectRequestItem);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 检异
     *
     * @param quaInspectRequestItem 检异
     * @return 结果
     */
    @Override
    public int registerExceptionalInspection(Long requestId, QuaInspectRequestItem quaInspectRequestItem) throws Exception{
        try{
            return quaInspectRequestItemAlgorithm.registerExceptionalInspection(requestId, quaInspectRequestItem);
        }catch (Exception ex){
            throw ex;
        }
    }

      /**
       * 请判
       *
       * @param quaInspectRequestItem 请判
       * @return 结果
       */
      @Override
      public int requestJugde(QuaInspectRequestItem quaInspectRequestItem) throws Exception{
          try{
              return quaInspectRequestItemAlgorithm.requestJugde(quaInspectRequestItem);
          }catch (Exception ex){
              throw ex;
          }
      }
}

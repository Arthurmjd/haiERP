package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.snc.SncStockRequestAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockRequestService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 出入库请求Service业务层处理
 * 
 * @author admin
 * @date 2025-05-17
 */
@Service
public class SncStockRequestServiceImpl implements ISncStockRequestService 
{
    @Autowired(required = false)
    private SncStockRequestMapper sncStockRequestMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SncStockRequestAlgorithm sncStockRequestAlgorithm;

    /**
     * 查询出入库请求
     * 
     * @param id 出入库请求主键
     * @return 出入库请求
     */
    @Override
    public SncStockRequest selectSncStockRequestById(Long id)
    {
        return sncStockRequestMapper.selectSncStockRequestById(id);
    }

    /**
     * 查询出入库请求列表
     * 
     * @param sncStockRequest 出入库请求
     * @return 出入库请求
     */
    @Override
    public List<SncStockRequest> selectSncStockRequestList(SncStockRequest sncStockRequest)
    {
        return sncStockRequestMapper.selectSncStockRequestList(sncStockRequest);
    }

    /**
     * 查询出入库请求列表
     *
     * @param sncStockRequest 出入库请求
     * @return 出入库请求
     */
    @Override
    public List<SncStockRequest> selectSncStockRequestPMaterialList(SncStockRequest sncStockRequest)
    {
        return sncStockRequestMapper.selectSncStockRequestPMaterialList(sncStockRequest);
    }

    /**
     * 查询出入库请求列表
     *
     * @param sncStockRequest 出入库请求
     * @return 出入库请求
     */
    @Override
    public List<SncStockRequest> selectSncStockRequestFinishedList(SncStockRequest sncStockRequest)
    {
        return sncStockRequestMapper.selectSncStockRequestFinishedList(sncStockRequest);
    }

    /**
     * 新增出入库请求
     * 
     * @param sncStockRequest 出入库请求
     * @return 结果
     */
    @Override
    public int insertSncStockRequest(SncStockRequest sncStockRequest)
    {
        sncStockRequest.setCreateTime(DateUtils.getNowDate());
        return sncStockRequestMapper.insertSncStockRequest(sncStockRequest);
    }

    /**
     * 修改出入库请求
     * 
     * @param sncStockRequest 出入库请求
     * @return 结果
     */
    @Override
    public int updateSncStockRequest(SncStockRequest sncStockRequest)
    {
        sncStockRequest.setUpdateTime(DateUtils.getNowDate());
        return sncStockRequestMapper.updateSncStockRequest(sncStockRequest);
    }

    /**
     * 批量删除出入库请求
     * 
     * @param ids 需要删除的出入库请求主键
     * @return 结果
     */
    @Override
    public int deleteSncStockRequestByIds(Long[] ids)
    {
        return sncStockRequestMapper.deleteSncStockRequestByIds(ids);
    }

    /**
     * 删除出入库请求信息
     * 
     * @param id 出入库请求主键
     * @return 结果
     */
    @Override
    public int deleteSncStockRequestById(Long id)
    {
        return sncStockRequestMapper.deleteSncStockRequestById(id);
    }

     /**
      * 启用
      *
      * @param sncStockRequest 启用
      * @return 结果
      */
     @Override
     public int enable(SncStockRequest sncStockRequest){
         Long id = sncStockRequest.getId();
         SncStockRequest obj = sncStockRequestMapper.selectSncStockRequestById(id);

         obj.setStatus(4);
         obj.setRequestorId(SecurityUtils.getUserId());
         obj.setRequestTime(DateUtils.getNowDate());
         obj.refreshUpdatingStamp();
         return sncStockRequestMapper.updateSncStockRequest(obj);
     }
      /**
       * 请库
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int requestStock(Long[] ids) throws Exception{
          try{
              return sncStockRequestAlgorithm.requestStock(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

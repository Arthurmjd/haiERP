package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnMrpMpAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMrpMpMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMp;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMrpMpService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料需求Service业务层处理
 * 
 * @author admin
 * @date 2025-04-28
 */
@Service
public class PlnMrpMpServiceImpl implements IPlnMrpMpService 
{
    @Autowired(required = false)
    private PlnMrpMpMapper plnMrpMpMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnMrpMpAlgorithm plnMrpMpAlgorithm;

    /**
     * 查询物料需求
     * 
     * @param id 物料需求主键
     * @return 物料需求
     */
    @Override
    public PlnMrpMp selectPlnMrpMpById(Long id)
    {
        return plnMrpMpMapper.selectPlnMrpMpById(id);
    }

    /**
     * 查询物料需求列表
     * 
     * @param plnMrpMp 物料需求
     * @return 物料需求
     */
    @Override
    public List<PlnMrpMp> selectPlnMrpMpList(PlnMrpMp plnMrpMp)
    {
        return plnMrpMpMapper.selectPlnMrpMpList(plnMrpMp);
    }

    /**
     * 新增物料需求
     * 
     * @param plnMrpMp 物料需求
     * @return 结果
     */
    @Override
    public int insertPlnMrpMp(PlnMrpMp plnMrpMp)
    {
        plnMrpMp.setCreateTime(DateUtils.getNowDate());
        return plnMrpMpMapper.insertPlnMrpMp(plnMrpMp);
    }

    /**
     * 修改物料需求
     * 
     * @param plnMrpMp 物料需求
     * @return 结果
     */
    @Override
    public int updatePlnMrpMp(PlnMrpMp plnMrpMp)
    {
        plnMrpMp.setUpdateTime(DateUtils.getNowDate());
        return plnMrpMpMapper.updatePlnMrpMp(plnMrpMp);
    }

    /**
     * 批量删除物料需求
     * 
     * @param ids 需要删除的物料需求主键
     * @return 结果
     */
    @Override
    public int deletePlnMrpMpByIds(Long[] ids)
    {
        return plnMrpMpMapper.deletePlnMrpMpByIds(ids);
    }

    /**
     * 删除物料需求信息
     * 
     * @param id 物料需求主键
     * @return 结果
     */
    @Override
    public int deletePlnMrpMpById(Long id)
    {
        return plnMrpMpMapper.deletePlnMrpMpById(id);
    }

     /**
      * 启用
      *
      * @param plnMrpMp 启用
      * @return 结果
      */
     @Override
     public int enable(PlnMrpMp plnMrpMp){
         Long id = plnMrpMp.getId();
         PlnMrpMp obj = plnMrpMpMapper.selectPlnMrpMpById(id);

         obj.setStatus(4);
         return plnMrpMpMapper.updatePlnMrpMp(obj);
     }
      /**
       * 请购
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int requestPurchase(Long[] ids) throws Exception{
          try{
              return plnMrpMpAlgorithm.requestPurchase(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

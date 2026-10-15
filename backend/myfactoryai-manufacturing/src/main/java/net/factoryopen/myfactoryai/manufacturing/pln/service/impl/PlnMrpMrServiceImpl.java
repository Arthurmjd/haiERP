package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnMrpMrAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMrpMrMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMrpMrService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * MRP物料需求Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class PlnMrpMrServiceImpl implements IPlnMrpMrService 
{
    @Autowired(required = false)
    private PlnMrpMrMapper plnMrpMrMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnMrpMrAlgorithm plnMrpMrAlgorithm;

    /**
     * 查询MRP物料需求
     * 
     * @param id MRP物料需求主键
     * @return MRP物料需求
     */
    @Override
    public PlnMrpMr selectPlnMrpMrById(Long id)
    {
        return plnMrpMrMapper.selectPlnMrpMrById(id);
    }

    /**
     * 查询MRP物料需求列表
     * 
     * @param plnMrpMr MRP物料需求
     * @return MRP物料需求
     */
    @Override
    public List<PlnMrpMr> selectPlnMrpMrList(PlnMrpMr plnMrpMr)
    {
        return plnMrpMrMapper.selectPlnMrpMrList(plnMrpMr);
    }

    /**
     * 新增MRP物料需求
     * 
     * @param plnMrpMr MRP物料需求
     * @return 结果
     */
    @Override
    public int insertPlnMrpMr(PlnMrpMr plnMrpMr)
    {
        plnMrpMr.setCreateTime(DateUtils.getNowDate());
        return plnMrpMrMapper.insertPlnMrpMr(plnMrpMr);
    }

    /**
     * 修改MRP物料需求
     * 
     * @param plnMrpMr MRP物料需求
     * @return 结果
     */
    @Override
    public int updatePlnMrpMr(PlnMrpMr plnMrpMr)
    {
        plnMrpMr.refreshUpdatingStamp();
        return plnMrpMrMapper.updatePlnMrpMr(plnMrpMr);
    }

    /**
     * 批量删除MRP物料需求
     * 
     * @param ids 需要删除的MRP物料需求主键
     * @return 结果
     */
    @Override
    public int deletePlnMrpMrByIds(Long[] ids)
    {
        return plnMrpMrMapper.deletePlnMrpMrByIds(ids);
    }

    /**
     * 删除MRP物料需求信息
     * 
     * @param id MRP物料需求主键
     * @return 结果
     */
    @Override
    public int deletePlnMrpMrById(Long id)
    {
        return plnMrpMrMapper.deletePlnMrpMrById(id);
    }


      /**
       * 转需求
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int toMp(Long[] ids) throws Exception{
          try{
              return plnMrpMrAlgorithm.toMp(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
}

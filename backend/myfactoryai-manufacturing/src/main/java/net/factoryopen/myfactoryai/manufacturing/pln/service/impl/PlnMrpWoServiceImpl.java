package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnMrpWoAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMrpWoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpWo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMrpWoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * MRP工单项Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class PlnMrpWoServiceImpl implements IPlnMrpWoService 
{
    @Autowired(required = false)
    private PlnMrpWoMapper plnMrpWoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnMrpWoAlgorithm plnMrpWoAlgorithm;

    /**
     * 查询MRP工单项
     * 
     * @param id MRP工单项主键
     * @return MRP工单项
     */
    @Override
    public PlnMrpWo selectPlnMrpWoById(Long id)
    {
        return plnMrpWoMapper.selectPlnMrpWoById(id);
    }

    /**
     * 查询MRP工单项列表
     * 
     * @param plnMrpWo MRP工单项
     * @return MRP工单项
     */
    @Override
    public List<PlnMrpWo> selectPlnMrpWoList(PlnMrpWo plnMrpWo)
    {
        return plnMrpWoMapper.selectPlnMrpWoList(plnMrpWo);
    }

    /**
     * 新增MRP工单项
     * 
     * @param plnMrpWo MRP工单项
     * @return 结果
     */
    @Override
    public int insertPlnMrpWo(PlnMrpWo plnMrpWo)
    {
        plnMrpWo.setCreateTime(DateUtils.getNowDate());
        return plnMrpWoMapper.insertPlnMrpWo(plnMrpWo);
    }

    /**
     * 修改MRP工单项
     * 
     * @param plnMrpWo MRP工单项
     * @return 结果
     */
    @Override
    public int updatePlnMrpWo(PlnMrpWo plnMrpWo)
    {
        plnMrpWo.setUpdateTime(DateUtils.getNowDate());
        return plnMrpWoMapper.updatePlnMrpWo(plnMrpWo);
    }

    /**
     * 批量删除MRP工单项
     * 
     * @param ids 需要删除的MRP工单项主键
     * @return 结果
     */
    @Override
    public int deletePlnMrpWoByIds(Long[] ids)
    {
        return plnMrpWoMapper.deletePlnMrpWoByIds(ids);
    }

    /**
     * 删除MRP工单项信息
     * 
     * @param id MRP工单项主键
     * @return 结果
     */
    @Override
    public int deletePlnMrpWoById(Long id)
    {
        return plnMrpWoMapper.deletePlnMrpWoById(id);
    }


      /**
       * 转工单
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int toWo(Long[] ids) throws Exception{
          try{
              return plnMrpWoAlgorithm.toWo(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
}

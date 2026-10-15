package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdCheckbusiAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdCheckbusiMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdCheckbusi;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdCheckbusiService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单对账服务Service业务层处理
 * 
 * @author  jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdCheckbusiServiceImpl implements IOrdCheckbusiService 
{
    @Autowired(required = false)
    private OrdCheckbusiMapper ordCheckbusiMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private OrdCheckbusiAlgorithm ordCheckbusiAlgorithm;

    /**
     * 查询订单对账服务
     * 
     * @param id 订单对账服务主键
     * @return 订单对账服务
     */
    @Override
    public OrdCheckbusi selectOrdCheckbusiById(Long id)
    {
        return ordCheckbusiMapper.selectOrdCheckbusiById(id);
    }

    /**
     * 查询订单对账服务列表
     * 
     * @param ordCheckbusi 订单对账服务
     * @return 订单对账服务
     */
    @Override
    public List<OrdCheckbusi> selectOrdCheckbusiList(OrdCheckbusi ordCheckbusi)
    {
        return ordCheckbusiMapper.selectOrdCheckbusiList(ordCheckbusi);
    }

    /**
     * 新增订单对账服务
     * 
     * @param ordCheckbusi 订单对账服务
     * @return 结果
     */
    @Override
    public int insertOrdCheckbusi(OrdCheckbusi ordCheckbusi)
    {
        ordCheckbusi.setCreateTime(DateUtils.getNowDate());
        return ordCheckbusiMapper.insertOrdCheckbusi(ordCheckbusi);
    }

    /**
     * 修改订单对账服务
     * 
     * @param ordCheckbusi 订单对账服务
     * @return 结果
     */
    @Override
    public int updateOrdCheckbusi(OrdCheckbusi ordCheckbusi)
    {
        ordCheckbusi.refreshUpdatingStamp();
        return ordCheckbusiMapper.updateOrdCheckbusi(ordCheckbusi);
    }

    /**
     * 批量删除订单对账服务
     * 
     * @param ids 需要删除的订单对账服务主键
     * @return 结果
     */
    @Override
    public int deleteOrdCheckbusiByIds(Long[] ids)
    {
        return ordCheckbusiMapper.deleteOrdCheckbusiByIds(ids);
    }

    /**
     * 删除订单对账服务信息
     * 
     * @param id 订单对账服务主键
     * @return 结果
     */
    @Override
    public int deleteOrdCheckbusiById(Long id)
    {
        return ordCheckbusiMapper.deleteOrdCheckbusiById(id);
    }

      /**
       * 对账
       *
       * @param ordCheckbusi 对账
       * @return 结果
       */
      @Override
      public int checkOrderBusi(OrdCheckbusi ordCheckbusi) throws Exception{
          try{
              return ordCheckbusiAlgorithm.checkOrderBusi(ordCheckbusi);
          }catch (Exception ex){
              throw ex;
          }
      }

}

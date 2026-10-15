package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdComplainAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdComplainMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdComplain;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdComplainService;

import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客诉登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class OrdComplainServiceImpl implements IOrdComplainService 
{
    @Autowired(required = false)
    private OrdComplainMapper ordComplainMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private OrdComplainAlgorithm ordComplainAlgorithm;

    /**
     * 查询客诉登记
     * 
     * @param id 客诉登记主键
     * @return 客诉登记
     */
    @Override
    public OrdComplain selectOrdComplainById(Long id)
    {
        return ordComplainMapper.selectOrdComplainById(id);
    }

    /**
     * 查询客诉登记列表
     * 
     * @param ordComplain 客诉登记
     * @return 客诉登记
     */
    @Override
    public List<OrdComplain> selectOrdComplainList(OrdComplain ordComplain)
    {
        return ordComplainMapper.selectOrdComplainList(ordComplain);
    }

    /**
     * 新增客诉登记
     * 
     * @param ordComplain 客诉登记
     * @return 结果
     */
    @Override
    public int insertOrdComplain(OrdComplain ordComplain)
    {
        ordComplain.setCreateTime(DateUtils.getNowDate());
        return ordComplainMapper.insertOrdComplain(ordComplain);
    }

    /**
     * 修改客诉登记
     * 
     * @param ordComplain 客诉登记
     * @return 结果
     */
    @Override
    public int updateOrdComplain(OrdComplain ordComplain)
    {
        ordComplain.setUpdateTime(DateUtils.getNowDate());
        return ordComplainMapper.updateOrdComplain(ordComplain);
    }

    /**
     * 批量删除客诉登记
     * 
     * @param ids 需要删除的客诉登记主键
     * @return 结果
     */
    @Override
    public int deleteOrdComplainByIds(Long[] ids)
    {
        return ordComplainMapper.deleteOrdComplainByIds(ids);
    }

    /**
     * 删除客诉登记信息
     * 
     * @param id 客诉登记主键
     * @return 结果
     */
    @Override
    public int deleteOrdComplainById(Long id)
    {
        return ordComplainMapper.deleteOrdComplainById(id);
    }


    /**
     * 发布
     *
     * @param ordComplain 发布
     * @return 结果
     */
    @Override
    public int enableOrdComplain(OrdComplain ordComplain){
        Long id = ordComplain.getId();
        OrdComplain obj = ordComplainMapper.selectOrdComplainById(id);

        obj.setStatus(BillStatus.BASE_ENABLE);
        obj.refreshUpdatingStamp();
        return ordComplainMapper.updateOrdComplain(obj);
    }
      /**
       * 请检
       *
       * @param ordComplain 请检
       * @return 结果
       */
      @Override
      public int requestInspect(OrdComplain ordComplain) throws Exception{
          try{
              return ordComplainAlgorithm.requestInspect(ordComplain);
          }catch (Exception ex){
              throw ex;
          }
      }
}

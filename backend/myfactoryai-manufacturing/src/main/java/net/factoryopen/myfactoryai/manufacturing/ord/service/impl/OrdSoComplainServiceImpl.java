package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdSoComplainAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoComplainMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoComplain;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoComplainService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单客诉登记Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdSoComplainServiceImpl implements IOrdSoComplainService 
{
    @Autowired(required = false)
    private OrdSoComplainMapper ordSoComplainMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private OrdSoComplainAlgorithm ordSoComplainAlgorithm;

    /**
     * 查询订单客诉登记
     * 
     * @param id 订单客诉登记主键
     * @return 订单客诉登记
     */
    @Override
    public OrdSoComplain selectOrdSoComplainById(Long id)
    {
        return ordSoComplainMapper.selectOrdSoComplainById(id);
    }

    /**
     * 查询订单客诉登记列表
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 订单客诉登记
     */
    @Override
    public List<OrdSoComplain> selectOrdSoComplainList(OrdSoComplain ordSoComplain)
    {
        return ordSoComplainMapper.selectOrdSoComplainList(ordSoComplain);
    }

    /**
     * 新增订单客诉登记
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 结果
     */
    @Override
    public int insertOrdSoComplain(OrdSoComplain ordSoComplain)
    {
        ordSoComplain.setCreateTime(DateUtils.getNowDate());
        return ordSoComplainMapper.insertOrdSoComplain(ordSoComplain);
    }

    /**
     * 修改订单客诉登记
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 结果
     */
    @Override
    public int updateOrdSoComplain(OrdSoComplain ordSoComplain)
    {
        ordSoComplain.setUpdateTime(DateUtils.getNowDate());
        return ordSoComplainMapper.updateOrdSoComplain(ordSoComplain);
    }

    /**
     * 批量删除订单客诉登记
     * 
     * @param ids 需要删除的订单客诉登记主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoComplainByIds(Long[] ids)
    {
        return ordSoComplainMapper.deleteOrdSoComplainByIds(ids);
    }

    /**
     * 删除订单客诉登记信息
     * 
     * @param id 订单客诉登记主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoComplainById(Long id)
    {
        return ordSoComplainMapper.deleteOrdSoComplainById(id);
    }

    /**
     * 客诉
     *
     * @param ordSoComplain 客诉
     * @return 结果
     */
    @Override
    public int complainReg(OrdSoComplain ordSoComplain) throws Exception{
        try{
            return ordSoComplainAlgorithm.complainReg(ordSoComplain);
        }catch (Exception ex){
            throw ex;
        }
    }

}

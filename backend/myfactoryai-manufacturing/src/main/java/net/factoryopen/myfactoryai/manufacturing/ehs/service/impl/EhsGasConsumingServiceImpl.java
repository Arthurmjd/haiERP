package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsGasConsumingMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasConsuming;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsGasConsumingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 气耗登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsGasConsumingServiceImpl implements IEhsGasConsumingService 
{
    @Autowired(required = false)
    private EhsGasConsumingMapper ehsGasConsumingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询气耗登记
     * 
     * @param id 气耗登记主键
     * @return 气耗登记
     */
    @Override
    public EhsGasConsuming selectEhsGasConsumingById(Long id)
    {
        return ehsGasConsumingMapper.selectEhsGasConsumingById(id);
    }

    /**
     * 查询气耗登记列表
     * 
     * @param ehsGasConsuming 气耗登记
     * @return 气耗登记
     */
    @Override
    public List<EhsGasConsuming> selectEhsGasConsumingList(EhsGasConsuming ehsGasConsuming)
    {
        return ehsGasConsumingMapper.selectEhsGasConsumingList(ehsGasConsuming);
    }

    /**
     * 新增气耗登记
     * 
     * @param ehsGasConsuming 气耗登记
     * @return 结果
     */
    @Override
    public int insertEhsGasConsuming(EhsGasConsuming ehsGasConsuming)
    {
        ehsGasConsuming.setCreateTime(DateUtils.getNowDate());
        return ehsGasConsumingMapper.insertEhsGasConsuming(ehsGasConsuming);
    }

    /**
     * 修改气耗登记
     * 
     * @param ehsGasConsuming 气耗登记
     * @return 结果
     */
    @Override
    public int updateEhsGasConsuming(EhsGasConsuming ehsGasConsuming)
    {
        ehsGasConsuming.setUpdateTime(DateUtils.getNowDate());
        return ehsGasConsumingMapper.updateEhsGasConsuming(ehsGasConsuming);
    }

    /**
     * 批量删除气耗登记
     * 
     * @param ids 需要删除的气耗登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsGasConsumingByIds(Long[] ids)
    {
        return ehsGasConsumingMapper.deleteEhsGasConsumingByIds(ids);
    }

    /**
     * 删除气耗登记信息
     * 
     * @param id 气耗登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsGasConsumingById(Long id)
    {
        return ehsGasConsumingMapper.deleteEhsGasConsumingById(id);
    }


}

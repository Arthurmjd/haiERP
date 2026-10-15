package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsWaterConsumingMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterConsuming;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsWaterConsumingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 水耗登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsWaterConsumingServiceImpl implements IEhsWaterConsumingService 
{
    @Autowired(required = false)
    private EhsWaterConsumingMapper ehsWaterConsumingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询水耗登记
     * 
     * @param id 水耗登记主键
     * @return 水耗登记
     */
    @Override
    public EhsWaterConsuming selectEhsWaterConsumingById(Long id)
    {
        return ehsWaterConsumingMapper.selectEhsWaterConsumingById(id);
    }

    /**
     * 查询水耗登记列表
     * 
     * @param ehsWaterConsuming 水耗登记
     * @return 水耗登记
     */
    @Override
    public List<EhsWaterConsuming> selectEhsWaterConsumingList(EhsWaterConsuming ehsWaterConsuming)
    {
        return ehsWaterConsumingMapper.selectEhsWaterConsumingList(ehsWaterConsuming);
    }

    /**
     * 新增水耗登记
     * 
     * @param ehsWaterConsuming 水耗登记
     * @return 结果
     */
    @Override
    public int insertEhsWaterConsuming(EhsWaterConsuming ehsWaterConsuming)
    {
        ehsWaterConsuming.setCreateTime(DateUtils.getNowDate());
        return ehsWaterConsumingMapper.insertEhsWaterConsuming(ehsWaterConsuming);
    }

    /**
     * 修改水耗登记
     * 
     * @param ehsWaterConsuming 水耗登记
     * @return 结果
     */
    @Override
    public int updateEhsWaterConsuming(EhsWaterConsuming ehsWaterConsuming)
    {
        ehsWaterConsuming.setUpdateTime(DateUtils.getNowDate());
        return ehsWaterConsumingMapper.updateEhsWaterConsuming(ehsWaterConsuming);
    }

    /**
     * 批量删除水耗登记
     * 
     * @param ids 需要删除的水耗登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsWaterConsumingByIds(Long[] ids)
    {
        return ehsWaterConsumingMapper.deleteEhsWaterConsumingByIds(ids);
    }

    /**
     * 删除水耗登记信息
     * 
     * @param id 水耗登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsWaterConsumingById(Long id)
    {
        return ehsWaterConsumingMapper.deleteEhsWaterConsumingById(id);
    }


}

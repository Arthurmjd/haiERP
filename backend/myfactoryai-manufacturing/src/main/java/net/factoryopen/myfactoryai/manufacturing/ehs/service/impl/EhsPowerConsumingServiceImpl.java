package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPowerConsumingMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerConsuming;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPowerConsumingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 电耗登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPowerConsumingServiceImpl implements IEhsPowerConsumingService 
{
    @Autowired(required = false)
    private EhsPowerConsumingMapper ehsPowerConsumingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询电耗登记
     * 
     * @param id 电耗登记主键
     * @return 电耗登记
     */
    @Override
    public EhsPowerConsuming selectEhsPowerConsumingById(Long id)
    {
        return ehsPowerConsumingMapper.selectEhsPowerConsumingById(id);
    }

    /**
     * 查询电耗登记列表
     * 
     * @param ehsPowerConsuming 电耗登记
     * @return 电耗登记
     */
    @Override
    public List<EhsPowerConsuming> selectEhsPowerConsumingList(EhsPowerConsuming ehsPowerConsuming)
    {
        return ehsPowerConsumingMapper.selectEhsPowerConsumingList(ehsPowerConsuming);
    }

    /**
     * 新增电耗登记
     * 
     * @param ehsPowerConsuming 电耗登记
     * @return 结果
     */
    @Override
    public int insertEhsPowerConsuming(EhsPowerConsuming ehsPowerConsuming)
    {
        ehsPowerConsuming.setCreateTime(DateUtils.getNowDate());
        return ehsPowerConsumingMapper.insertEhsPowerConsuming(ehsPowerConsuming);
    }

    /**
     * 修改电耗登记
     * 
     * @param ehsPowerConsuming 电耗登记
     * @return 结果
     */
    @Override
    public int updateEhsPowerConsuming(EhsPowerConsuming ehsPowerConsuming)
    {
        ehsPowerConsuming.setUpdateTime(DateUtils.getNowDate());
        return ehsPowerConsumingMapper.updateEhsPowerConsuming(ehsPowerConsuming);
    }

    /**
     * 批量删除电耗登记
     * 
     * @param ids 需要删除的电耗登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsPowerConsumingByIds(Long[] ids)
    {
        return ehsPowerConsumingMapper.deleteEhsPowerConsumingByIds(ids);
    }

    /**
     * 删除电耗登记信息
     * 
     * @param id 电耗登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsPowerConsumingById(Long id)
    {
        return ehsPowerConsumingMapper.deleteEhsPowerConsumingById(id);
    }


}

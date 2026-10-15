package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoRoutingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRouting;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单工艺管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
@Service
public class PlnWoRoutingServiceImpl implements IPlnWoRoutingService 
{
    @Autowired(required = false)
    private PlnWoRoutingMapper plnWoRoutingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单工艺管理
     * 
     * @param id 工单工艺管理主键
     * @return 工单工艺管理
     */
    @Override
    public PlnWoRouting selectPlnWoRoutingById(Long id)
    {
        return plnWoRoutingMapper.selectPlnWoRoutingById(id);
    }


    /**
     * 查询工单工艺管理
     *
     * @param woId 工单Id
     * @return 工单工艺管理
     */
    @Override
    public PlnWoRouting selectPlnWoRoutingFirstByWoId(Long woId)
    {
        return plnWoRoutingMapper.selectPlnWoRoutingFirstByWoId(woId);
    }

    /**
     * 查询工单工艺管理列表
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 工单工艺管理
     */
    @Override
    public List<PlnWoRouting> selectPlnWoRoutingList(PlnWoRouting plnWoRouting)
    {
        return plnWoRoutingMapper.selectPlnWoRoutingList(plnWoRouting);
    }

    /**
     * 新增工单工艺管理
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 结果
     */
    @Override
    public int insertPlnWoRouting(PlnWoRouting plnWoRouting)
    {
        plnWoRouting.setCreateTime(DateUtils.getNowDate());
        return plnWoRoutingMapper.insertPlnWoRouting(plnWoRouting);
    }

    /**
     * 修改工单工艺管理
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 结果
     */
    @Override
    public int updatePlnWoRouting(PlnWoRouting plnWoRouting)
    {
        plnWoRouting.setUpdateTime(DateUtils.getNowDate());
        return plnWoRoutingMapper.updatePlnWoRouting(plnWoRouting);
    }

    /**
     * 批量删除工单工艺管理
     * 
     * @param ids 需要删除的工单工艺管理主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingByIds(Long[] ids)
    {
        return plnWoRoutingMapper.deletePlnWoRoutingByIds(ids);
    }

    /**
     * 删除工单工艺管理信息
     * 
     * @param id 工单工艺管理主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingById(Long id)
    {
        return plnWoRoutingMapper.deletePlnWoRoutingById(id);
    }


}

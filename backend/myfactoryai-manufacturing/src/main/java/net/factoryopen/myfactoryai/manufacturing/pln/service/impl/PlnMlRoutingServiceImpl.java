package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlRoutingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRouting;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次工艺管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlRoutingServiceImpl implements IPlnMlRoutingService 
{
    @Autowired(required = false)
    private PlnMlRoutingMapper plnMlRoutingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次工艺管理
     * 
     * @param id 批次工艺管理主键
     * @return 批次工艺管理
     */
    @Override
    public PlnMlRouting selectPlnMlRoutingById(Long id)
    {
        return plnMlRoutingMapper.selectPlnMlRoutingById(id);
    }

    /**
     * 查询批次工艺管理列表
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 批次工艺管理
     */
    @Override
    public List<PlnMlRouting> selectPlnMlRoutingList(PlnMlRouting plnMlRouting)
    {
        return plnMlRoutingMapper.selectPlnMlRoutingList(plnMlRouting);
    }

    /**
     * 新增批次工艺管理
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 结果
     */
    @Override
    public int insertPlnMlRouting(PlnMlRouting plnMlRouting)
    {
        plnMlRouting.setCreateTime(DateUtils.getNowDate());
        return plnMlRoutingMapper.insertPlnMlRouting(plnMlRouting);
    }

    /**
     * 修改批次工艺管理
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 结果
     */
    @Override
    public int updatePlnMlRouting(PlnMlRouting plnMlRouting)
    {
        plnMlRouting.refreshUpdatingStamp();
        return plnMlRoutingMapper.updatePlnMlRouting(plnMlRouting);
    }

    /**
     * 批量删除批次工艺管理
     * 
     * @param ids 需要删除的批次工艺管理主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingByIds(Long[] ids)
    {
        return plnMlRoutingMapper.deletePlnMlRoutingByIds(ids);
    }

    /**
     * 删除批次工艺管理信息
     * 
     * @param id 批次工艺管理主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingById(Long id)
    {
        return plnMlRoutingMapper.deletePlnMlRoutingById(id);
    }


}

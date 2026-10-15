package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 制造工艺管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngProductRoutingServiceImpl implements IEngProductRoutingService 
{
    @Autowired(required = false)
    private EngProductRoutingMapper engProductRoutingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询制造工艺管理
     * 
     * @param id 制造工艺管理主键
     * @return 制造工艺管理
     */
    @Override
    public EngProductRouting selectEngProductRoutingById(Long id)
    {
        return engProductRoutingMapper.selectEngProductRoutingById(id);
    }

    /**
     * 查询制造工艺管理列表
     * 
     * @param engProductRouting 制造工艺管理
     * @return 制造工艺管理
     */
    @Override
    public List<EngProductRouting> selectEngProductRoutingList(EngProductRouting engProductRouting)
    {
        return engProductRoutingMapper.selectEngProductRoutingList(engProductRouting);
    }

    /**
     * 新增制造工艺管理
     * 
     * @param engProductRouting 制造工艺管理
     * @return 结果
     */
    @Override
    public int insertEngProductRouting(EngProductRouting engProductRouting)
    {
        //创建戳
        engProductRouting.setCreateBy(SecurityUtils.getUsername());
        engProductRouting.setCreateTime(DateUtils.getNowDate());
        engProductRouting.setUpdateBy(SecurityUtils.getUsername());
        engProductRouting.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingMapper.insertEngProductRouting(engProductRouting);
    }

    /**
     * 修改制造工艺管理
     * 
     * @param engProductRouting 制造工艺管理
     * @return 结果
     */
    @Override
    public int updateEngProductRouting(EngProductRouting engProductRouting)
    {
        engProductRouting.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingMapper.updateEngProductRouting(engProductRouting);
    }

    /**
     * 批量删除制造工艺管理
     * 
     * @param ids 需要删除的制造工艺管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingByIds(Long[] ids)
    {
        return engProductRoutingMapper.deleteEngProductRoutingByIds(ids);
    }

    /**
     * 删除制造工艺管理信息
     * 
     * @param id 制造工艺管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingById(Long id)
    {
        return engProductRoutingMapper.deleteEngProductRoutingById(id);
    }


}

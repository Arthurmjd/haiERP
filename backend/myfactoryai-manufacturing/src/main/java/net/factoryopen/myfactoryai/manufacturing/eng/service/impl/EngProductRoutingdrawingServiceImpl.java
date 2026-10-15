package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingdrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingdrawing;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingdrawingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品工序图Service业务层处理
 * 
 * @author admin
 * @date 2024-07-11
 */
@Service
public class EngProductRoutingdrawingServiceImpl implements IEngProductRoutingdrawingService 
{
    @Autowired(required = false)
    private EngProductRoutingdrawingMapper engProductRoutingdrawingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品工序图
     * 
     * @param id 产品工序图主键
     * @return 产品工序图
     */
    @Override
    public EngProductRoutingdrawing selectEngProductRoutingdrawingById(Long id)
    {
        return engProductRoutingdrawingMapper.selectEngProductRoutingdrawingById(id);
    }

    /**
     * 查询产品工序图列表
     * 
     * @param engProductRoutingdrawing 产品工序图
     * @return 产品工序图
     */
    @Override
    public List<EngProductRoutingdrawing> selectEngProductRoutingdrawingList(EngProductRoutingdrawing engProductRoutingdrawing)
    {
        return engProductRoutingdrawingMapper.selectEngProductRoutingdrawingList(engProductRoutingdrawing);
    }

    /**
     * 新增产品工序图
     * 
     * @param engProductRoutingdrawing 产品工序图
     * @return 结果
     */
    @Override
    public int insertEngProductRoutingdrawing(EngProductRoutingdrawing engProductRoutingdrawing)
    {
        engProductRoutingdrawing.setCreateTime(DateUtils.getNowDate());
        return engProductRoutingdrawingMapper.insertEngProductRoutingdrawing(engProductRoutingdrawing);
    }

    /**
     * 修改产品工序图
     * 
     * @param engProductRoutingdrawing 产品工序图
     * @return 结果
     */
    @Override
    public int updateEngProductRoutingdrawing(EngProductRoutingdrawing engProductRoutingdrawing)
    {
        engProductRoutingdrawing.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingdrawingMapper.updateEngProductRoutingdrawing(engProductRoutingdrawing);
    }

    /**
     * 批量删除产品工序图
     * 
     * @param ids 需要删除的产品工序图主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingdrawingByIds(Long[] ids)
    {
        return engProductRoutingdrawingMapper.deleteEngProductRoutingdrawingByIds(ids);
    }

    /**
     * 删除产品工序图信息
     * 
     * @param id 产品工序图主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingdrawingById(Long id)
    {
        return engProductRoutingdrawingMapper.deleteEngProductRoutingdrawingById(id);
    }


}

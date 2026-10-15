package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlRoutingdrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingdrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingdrawingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次工序图Service业务层处理
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public class PlnMlRoutingdrawingServiceImpl implements IPlnMlRoutingdrawingService 
{
    @Autowired(required = false)
    private PlnMlRoutingdrawingMapper plnMlRoutingdrawingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次工序图
     * 
     * @param id 批次工序图主键
     * @return 批次工序图
     */
    @Override
    public PlnMlRoutingdrawing selectPlnMlRoutingdrawingById(Long id)
    {
        return plnMlRoutingdrawingMapper.selectPlnMlRoutingdrawingById(id);
    }

    /**
     * 查询批次工序图列表
     * 
     * @param plnMlRoutingdrawing 批次工序图
     * @return 批次工序图
     */
    @Override
    public List<PlnMlRoutingdrawing> selectPlnMlRoutingdrawingList(PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        return plnMlRoutingdrawingMapper.selectPlnMlRoutingdrawingList(plnMlRoutingdrawing);
    }

    /**
     * 新增批次工序图
     * 
     * @param plnMlRoutingdrawing 批次工序图
     * @return 结果
     */
    @Override
    public int insertPlnMlRoutingdrawing(PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        plnMlRoutingdrawing.setCreateTime(DateUtils.getNowDate());
        return plnMlRoutingdrawingMapper.insertPlnMlRoutingdrawing(plnMlRoutingdrawing);
    }

    /**
     * 修改批次工序图
     * 
     * @param plnMlRoutingdrawing 批次工序图
     * @return 结果
     */
    @Override
    public int updatePlnMlRoutingdrawing(PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        plnMlRoutingdrawing.setUpdateTime(DateUtils.getNowDate());
        return plnMlRoutingdrawingMapper.updatePlnMlRoutingdrawing(plnMlRoutingdrawing);
    }

    /**
     * 批量删除批次工序图
     * 
     * @param ids 需要删除的批次工序图主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingdrawingByIds(Long[] ids)
    {
        return plnMlRoutingdrawingMapper.deletePlnMlRoutingdrawingByIds(ids);
    }

    /**
     * 删除批次工序图信息
     * 
     * @param id 批次工序图主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingdrawingById(Long id)
    {
        return plnMlRoutingdrawingMapper.deletePlnMlRoutingdrawingById(id);
    }


}

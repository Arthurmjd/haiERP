package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoRoutingdrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingdrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingdrawingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单工序图Service业务层处理
 * 
 * @author admin
 * @date 2024-07-11
 */
@Service
public class PlnWoRoutingdrawingServiceImpl implements IPlnWoRoutingdrawingService 
{
    @Autowired(required = false)
    private PlnWoRoutingdrawingMapper plnWoRoutingdrawingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单工序图
     * 
     * @param id 工单工序图主键
     * @return 工单工序图
     */
    @Override
    public PlnWoRoutingdrawing selectPlnWoRoutingdrawingById(Long id)
    {
        return plnWoRoutingdrawingMapper.selectPlnWoRoutingdrawingById(id);
    }

    /**
     * 查询工单工序图列表
     * 
     * @param plnWoRoutingdrawing 工单工序图
     * @return 工单工序图
     */
    @Override
    public List<PlnWoRoutingdrawing> selectPlnWoRoutingdrawingList(PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        return plnWoRoutingdrawingMapper.selectPlnWoRoutingdrawingList(plnWoRoutingdrawing);
    }

    /**
     * 新增工单工序图
     * 
     * @param plnWoRoutingdrawing 工单工序图
     * @return 结果
     */
    @Override
    public int insertPlnWoRoutingdrawing(PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        plnWoRoutingdrawing.setCreateTime(DateUtils.getNowDate());
        return plnWoRoutingdrawingMapper.insertPlnWoRoutingdrawing(plnWoRoutingdrawing);
    }

    /**
     * 修改工单工序图
     * 
     * @param plnWoRoutingdrawing 工单工序图
     * @return 结果
     */
    @Override
    public int updatePlnWoRoutingdrawing(PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        plnWoRoutingdrawing.setUpdateTime(DateUtils.getNowDate());
        return plnWoRoutingdrawingMapper.updatePlnWoRoutingdrawing(plnWoRoutingdrawing);
    }

    /**
     * 批量删除工单工序图
     * 
     * @param ids 需要删除的工单工序图主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingdrawingByIds(Long[] ids)
    {
        return plnWoRoutingdrawingMapper.deletePlnWoRoutingdrawingByIds(ids);
    }

    /**
     * 删除工单工序图信息
     * 
     * @param id 工单工序图主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingdrawingById(Long id)
    {
        return plnWoRoutingdrawingMapper.deletePlnWoRoutingdrawingById(id);
    }


}

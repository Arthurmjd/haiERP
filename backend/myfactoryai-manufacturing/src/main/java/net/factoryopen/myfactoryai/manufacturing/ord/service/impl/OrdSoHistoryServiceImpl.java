package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoHistoryMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoHistory;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoHistoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单历史查询Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdSoHistoryServiceImpl implements IOrdSoHistoryService 
{
    @Autowired(required = false)
    private OrdSoHistoryMapper ordSoHistoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询订单历史查询
     * 
     * @param id 订单历史查询主键
     * @return 订单历史查询
     */
    @Override
    public OrdSoHistory selectOrdSoHistoryById(Long id)
    {
        return ordSoHistoryMapper.selectOrdSoHistoryById(id);
    }

    /**
     * 查询订单历史查询列表
     * 
     * @param ordSoHistory 订单历史查询
     * @return 订单历史查询
     */
    @Override
    public List<OrdSoHistory> selectOrdSoHistoryList(OrdSoHistory ordSoHistory)
    {
        return ordSoHistoryMapper.selectOrdSoHistoryList(ordSoHistory);
    }

    /**
     * 新增订单历史查询
     * 
     * @param ordSoHistory 订单历史查询
     * @return 结果
     */
    @Override
    public int insertOrdSoHistory(OrdSoHistory ordSoHistory)
    {
        ordSoHistory.setCreateTime(DateUtils.getNowDate());
        return ordSoHistoryMapper.insertOrdSoHistory(ordSoHistory);
    }

    /**
     * 修改订单历史查询
     * 
     * @param ordSoHistory 订单历史查询
     * @return 结果
     */
    @Override
    public int updateOrdSoHistory(OrdSoHistory ordSoHistory)
    {
        ordSoHistory.setUpdateTime(DateUtils.getNowDate());
        return ordSoHistoryMapper.updateOrdSoHistory(ordSoHistory);
    }

    /**
     * 批量删除订单历史查询
     * 
     * @param ids 需要删除的订单历史查询主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoHistoryByIds(Long[] ids)
    {
        return ordSoHistoryMapper.deleteOrdSoHistoryByIds(ids);
    }

    /**
     * 删除订单历史查询信息
     * 
     * @param id 订单历史查询主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoHistoryById(Long id)
    {
        return ordSoHistoryMapper.deleteOrdSoHistoryById(id);
    }


}

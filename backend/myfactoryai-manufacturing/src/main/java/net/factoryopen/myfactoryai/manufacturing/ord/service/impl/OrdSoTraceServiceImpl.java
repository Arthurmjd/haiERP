package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoTraceMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoTrace;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoTraceService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 在制订单跟踪Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdSoTraceServiceImpl implements IOrdSoTraceService 
{
    @Autowired(required = false)
    private OrdSoTraceMapper ordSoTraceMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询在制订单跟踪
     * 
     * @param id 在制订单跟踪主键
     * @return 在制订单跟踪
     */
    @Override
    public OrdSoTrace selectOrdSoTraceById(Long id)
    {
        return ordSoTraceMapper.selectOrdSoTraceById(id);
    }

    /**
     * 查询在制订单跟踪列表
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 在制订单跟踪
     */
    @Override
    public List<OrdSoTrace> selectOrdSoTraceList(OrdSoTrace ordSoTrace)
    {
        return ordSoTraceMapper.selectOrdSoTraceList(ordSoTrace);
    }

    /**
     * 新增在制订单跟踪
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 结果
     */
    @Override
    public int insertOrdSoTrace(OrdSoTrace ordSoTrace)
    {
        ordSoTrace.setCreateTime(DateUtils.getNowDate());
        return ordSoTraceMapper.insertOrdSoTrace(ordSoTrace);
    }

    /**
     * 修改在制订单跟踪
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 结果
     */
    @Override
    public int updateOrdSoTrace(OrdSoTrace ordSoTrace)
    {
        ordSoTrace.setUpdateTime(DateUtils.getNowDate());
        return ordSoTraceMapper.updateOrdSoTrace(ordSoTrace);
    }

    /**
     * 批量删除在制订单跟踪
     * 
     * @param ids 需要删除的在制订单跟踪主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoTraceByIds(Long[] ids)
    {
        return ordSoTraceMapper.deleteOrdSoTraceByIds(ids);
    }

    /**
     * 删除在制订单跟踪信息
     * 
     * @param id 在制订单跟踪主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoTraceById(Long id)
    {
        return ordSoTraceMapper.deleteOrdSoTraceById(id);
    }


}

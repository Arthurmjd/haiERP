package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlTraceMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTrace;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlTraceService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 在制批次跟踪Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ExeMlTraceServiceImpl implements IExeMlTraceService 
{
    @Autowired(required = false)
    private ExeMlTraceMapper exeMlTraceMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询在制批次跟踪
     * 
     * @param id 在制批次跟踪主键
     * @return 在制批次跟踪
     */
    @Override
    public ExeMlTrace selectExeMlTraceById(Long id)
    {
        return exeMlTraceMapper.selectExeMlTraceById(id);
    }

    /**
     * 查询在制批次跟踪列表
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 在制批次跟踪
     */
    @Override
    public List<ExeMlTrace> selectExeMlTraceList(ExeMlTrace exeMlTrace)
    {
        return exeMlTraceMapper.selectExeMlTraceList(exeMlTrace);
    }

    /**
     * 新增在制批次跟踪
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 结果
     */
    @Override
    public int insertExeMlTrace(ExeMlTrace exeMlTrace)
    {
        exeMlTrace.setCreateTime(DateUtils.getNowDate());
        return exeMlTraceMapper.insertExeMlTrace(exeMlTrace);
    }

    /**
     * 修改在制批次跟踪
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 结果
     */
    @Override
    public int updateExeMlTrace(ExeMlTrace exeMlTrace)
    {
        exeMlTrace.refreshUpdatingStamp();
        return exeMlTraceMapper.updateExeMlTrace(exeMlTrace);
    }

    /**
     * 批量删除在制批次跟踪
     * 
     * @param ids 需要删除的在制批次跟踪主键
     * @return 结果
     */
    @Override
    public int deleteExeMlTraceByIds(Long[] ids)
    {
        return exeMlTraceMapper.deleteExeMlTraceByIds(ids);
    }

    /**
     * 删除在制批次跟踪信息
     * 
     * @param id 在制批次跟踪主键
     * @return 结果
     */
    @Override
    public int deleteExeMlTraceById(Long id)
    {
        return exeMlTraceMapper.deleteExeMlTraceById(id);
    }


}

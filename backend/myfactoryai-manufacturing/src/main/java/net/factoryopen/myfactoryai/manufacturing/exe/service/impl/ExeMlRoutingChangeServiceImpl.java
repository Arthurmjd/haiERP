package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlRoutingChangeMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlRoutingChange;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlRoutingChangeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次制程变更Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ExeMlRoutingChangeServiceImpl implements IExeMlRoutingChangeService 
{
    @Autowired(required = false)
    private ExeMlRoutingChangeMapper exeMlRoutingChangeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次制程变更
     * 
     * @param id 批次制程变更主键
     * @return 批次制程变更
     */
    @Override
    public ExeMlRoutingChange selectExeMlRoutingChangeById(Long id)
    {
        return exeMlRoutingChangeMapper.selectExeMlRoutingChangeById(id);
    }

    /**
     * 查询批次制程变更列表
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 批次制程变更
     */
    @Override
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeList(ExeMlRoutingChange exeMlRoutingChange)
    {
        return exeMlRoutingChangeMapper.selectExeMlRoutingChangeList(exeMlRoutingChange);
    }

    /**
     * 新增批次制程变更
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 结果
     */
    @Override
    public int insertExeMlRoutingChange(ExeMlRoutingChange exeMlRoutingChange)
    {
        exeMlRoutingChange.setCreateTime(DateUtils.getNowDate());
        return exeMlRoutingChangeMapper.insertExeMlRoutingChange(exeMlRoutingChange);
    }

    /**
     * 修改批次制程变更
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 结果
     */
    @Override
    public int updateExeMlRoutingChange(ExeMlRoutingChange exeMlRoutingChange)
    {
        exeMlRoutingChange.refreshUpdatingStamp();
        return exeMlRoutingChangeMapper.updateExeMlRoutingChange(exeMlRoutingChange);
    }

    /**
     * 批量删除批次制程变更
     * 
     * @param ids 需要删除的批次制程变更主键
     * @return 结果
     */
    @Override
    public int deleteExeMlRoutingChangeByIds(Long[] ids)
    {
        return exeMlRoutingChangeMapper.deleteExeMlRoutingChangeByIds(ids);
    }

    /**
     * 删除批次制程变更信息
     * 
     * @param id 批次制程变更主键
     * @return 结果
     */
    @Override
    public int deleteExeMlRoutingChangeById(Long id)
    {
        return exeMlRoutingChangeMapper.deleteExeMlRoutingChangeById(id);
    }


}

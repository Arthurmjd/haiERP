package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProcessMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProcess;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProcessService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 基本工序管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2026-02-22
 */
@Service
public class EngProcessServiceImpl implements IEngProcessService 
{
    @Autowired(required = false)
    private EngProcessMapper engProcessMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询基本工序管理
     * 
     * @param id 基本工序管理主键
     * @return 基本工序管理
     */
    @Override
    public EngProcess selectEngProcessById(Long id)
    {
        return engProcessMapper.selectEngProcessById(id);
    }

    /**
     * 查询基本工序管理列表
     * 
     * @param engProcess 基本工序管理
     * @return 基本工序管理
     */
    @Override
    public List<EngProcess> selectEngProcessList(EngProcess engProcess)
    {
        return engProcessMapper.selectEngProcessList(engProcess);
    }

    /**
     * 新增基本工序管理
     * 
     * @param engProcess 基本工序管理
     * @return 结果
     */
    @Override
    public int insertEngProcess(EngProcess engProcess)
    {
        engProcess.setCreateTime(DateUtils.getNowDate());
        return engProcessMapper.insertEngProcess(engProcess);
    }

    /**
     * 修改基本工序管理
     * 
     * @param engProcess 基本工序管理
     * @return 结果
     */
    @Override
    public int updateEngProcess(EngProcess engProcess)
    {
        engProcess.setUpdateTime(DateUtils.getNowDate());
        return engProcessMapper.updateEngProcess(engProcess);
    }

    /**
     * 批量删除基本工序管理
     * 
     * @param ids 需要删除的基本工序管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProcessByIds(Long[] ids)
    {
        return engProcessMapper.deleteEngProcessByIds(ids);
    }

    /**
     * 删除基本工序管理信息
     * 
     * @param id 基本工序管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProcessById(Long id)
    {
        return engProcessMapper.deleteEngProcessById(id);
    }


}

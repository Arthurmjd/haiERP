package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlHistoryMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlHistory;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlHistoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 历史批次查询Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ExeMlHistoryServiceImpl implements IExeMlHistoryService 
{
    @Autowired(required = false)
    private ExeMlHistoryMapper exeMlHistoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询历史批次查询
     * 
     * @param id 历史批次查询主键
     * @return 历史批次查询
     */
    @Override
    public ExeMlHistory selectExeMlHistoryById(Long id)
    {
        return exeMlHistoryMapper.selectExeMlHistoryById(id);
    }

    /**
     * 查询历史批次查询列表
     * 
     * @param exeMlHistory 历史批次查询
     * @return 历史批次查询
     */
    @Override
    public List<ExeMlHistory> selectExeMlHistoryList(ExeMlHistory exeMlHistory)
    {
        return exeMlHistoryMapper.selectExeMlHistoryList(exeMlHistory);
    }

    /**
     * 新增历史批次查询
     * 
     * @param exeMlHistory 历史批次查询
     * @return 结果
     */
    @Override
    public int insertExeMlHistory(ExeMlHistory exeMlHistory)
    {
        exeMlHistory.setCreateTime(DateUtils.getNowDate());
        return exeMlHistoryMapper.insertExeMlHistory(exeMlHistory);
    }

    /**
     * 修改历史批次查询
     * 
     * @param exeMlHistory 历史批次查询
     * @return 结果
     */
    @Override
    public int updateExeMlHistory(ExeMlHistory exeMlHistory)
    {
        exeMlHistory.setUpdateTime(DateUtils.getNowDate());
        return exeMlHistoryMapper.updateExeMlHistory(exeMlHistory);
    }

    /**
     * 批量删除历史批次查询
     * 
     * @param ids 需要删除的历史批次查询主键
     * @return 结果
     */
    @Override
    public int deleteExeMlHistoryByIds(Long[] ids)
    {
        return exeMlHistoryMapper.deleteExeMlHistoryByIds(ids);
    }

    /**
     * 删除历史批次查询信息
     * 
     * @param id 历史批次查询主键
     * @return 结果
     */
    @Override
    public int deleteExeMlHistoryById(Long id)
    {
        return exeMlHistoryMapper.deleteExeMlHistoryById(id);
    }


}

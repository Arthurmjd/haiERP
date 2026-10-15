package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeWorkSegmentMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeWorkSegment;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeWorkSegmentService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 历史出工查询Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-08-08
 */
@Service
public class ExeWorkSegmentServiceImpl implements IExeWorkSegmentService 
{
    @Autowired(required = false)
    private ExeWorkSegmentMapper exeWorkSegmentMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询历史出工查询
     * 
     * @param id 历史出工查询主键
     * @return 历史出工查询
     */
    @Override
    public ExeWorkSegment selectExeWorkSegmentById(Long id)
    {
        return exeWorkSegmentMapper.selectExeWorkSegmentById(id);
    }

    /**
     * 查询历史出工查询列表
     * 
     * @param exeWorkSegment 历史出工查询
     * @return 历史出工查询
     */
    @Override
    public List<ExeWorkSegment> selectExeWorkSegmentList(ExeWorkSegment exeWorkSegment)
    {
        return exeWorkSegmentMapper.selectExeWorkSegmentList(exeWorkSegment);
    }

    /**
     * 新增历史出工查询
     * 
     * @param exeWorkSegment 历史出工查询
     * @return 结果
     */
    @Override
    public int insertExeWorkSegment(ExeWorkSegment exeWorkSegment)
    {
        exeWorkSegment.setCreateTime(DateUtils.getNowDate());
        return exeWorkSegmentMapper.insertExeWorkSegment(exeWorkSegment);
    }

    /**
     * 修改历史出工查询
     * 
     * @param exeWorkSegment 历史出工查询
     * @return 结果
     */
    @Override
    public int updateExeWorkSegment(ExeWorkSegment exeWorkSegment)
    {
        exeWorkSegment.setUpdateTime(DateUtils.getNowDate());
        return exeWorkSegmentMapper.updateExeWorkSegment(exeWorkSegment);
    }

    /**
     * 批量删除历史出工查询
     * 
     * @param ids 需要删除的历史出工查询主键
     * @return 结果
     */
    @Override
    public int deleteExeWorkSegmentByIds(Long[] ids)
    {
        return exeWorkSegmentMapper.deleteExeWorkSegmentByIds(ids);
    }

    /**
     * 删除历史出工查询信息
     * 
     * @param id 历史出工查询主键
     * @return 结果
     */
    @Override
    public int deleteExeWorkSegmentById(Long id)
    {
        return exeWorkSegmentMapper.deleteExeWorkSegmentById(id);
    }


}

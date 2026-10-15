package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeWorkSegment;

/**
 * 历史出工查询Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-08-08
 */
public interface IExeWorkSegmentService 
{
    /**
     * 查询历史出工查询
     * 
     * @param id 历史出工查询主键
     * @return 历史出工查询
     */
    public ExeWorkSegment selectExeWorkSegmentById(Long id);

    /**
     * 查询历史出工查询列表
     * 
     * @param exeWorkSegment 历史出工查询
     * @return 历史出工查询集合
     */
    public List<ExeWorkSegment> selectExeWorkSegmentList(ExeWorkSegment exeWorkSegment);

    /**
     * 新增历史出工查询
     * 
     * @param exeWorkSegment 历史出工查询
     * @return 结果
     */
    public int insertExeWorkSegment(ExeWorkSegment exeWorkSegment);

    /**
     * 修改历史出工查询
     * 
     * @param exeWorkSegment 历史出工查询
     * @return 结果
     */
    public int updateExeWorkSegment(ExeWorkSegment exeWorkSegment);

    /**
     * 批量删除历史出工查询
     * 
     * @param ids 需要删除的历史出工查询主键集合
     * @return 结果
     */
    public int deleteExeWorkSegmentByIds(Long[] ids);

    /**
     * 删除历史出工查询信息
     * 
     * @param id 历史出工查询主键
     * @return 结果
     */
    public int deleteExeWorkSegmentById(Long id);


}

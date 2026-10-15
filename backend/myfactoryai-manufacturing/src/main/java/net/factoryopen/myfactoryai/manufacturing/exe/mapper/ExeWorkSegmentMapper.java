package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeWorkSegment;
import org.springframework.stereotype.Service;

/**
 * 历史出工查询Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-08-08
 */
@Service
public interface ExeWorkSegmentMapper 
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
     * 查询历史出工查询列表
     *
     * @param moveId 批次号
     * @return 历史出工查询集合
     */
    public List<ExeWorkSegment> selectExeWorkSegmentListByMoveId(Long moveId);

    /**
     * 查询历史出工查询列表
     *
     * @param workShifitId 班别
     * @return 历史出工查询集合
     */
    public List<ExeWorkSegment> selectExeWorkSegmentListByWorkShifitId(Long workShifitId);

    /**
     * 查询历史出工查询列表
     *
     * @param machineId 机台ID
     * @return 历史出工查询集合
     */
    public List<ExeWorkSegment> selectExeWorkSegmentListByMachineId(Long machineId);

    /**
     * 查询在出工列表
     *
     * @param machineId 机台ID
     * @return 在出工集合
     */
    public List<ExeWorkSegment> selectExeWorkingSegmentListByMachineId(Long machineId);

    /**
     * 查询今天完工列表
     *
     * @param machineId 机台ID
     * @return 当天完工集合
     */
    public List<ExeWorkSegment> selectTodayWorkedSegmentListByMachineId(Long machineId);

    /**
     * 查询历史出工查询列表
     *
     * @param startBy 出工人
     * @return 历史出工查询集合
     */
    public List<ExeWorkSegment> selectExeWorkSegmentListByStartBy(Long startBy);

    /**
     * 查询历史出工查询列表
     *
     * @param finishBy 收工人
     * @return 历史出工查询集合
     */
    public List<ExeWorkSegment> selectExeWorkSegmentListByFinishBy(Long finishBy);

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
     * 删除历史出工查询
     * 
     * @param id 历史出工查询主键
     * @return 结果
     */
    public int deleteExeWorkSegmentById(Long id);

    /**
     * 批量删除历史出工查询
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeWorkSegmentByIds(Long[] ids);

    /**
     * 查询历史出工查询列表
     *
     * @param moveId 批次号
     * @return 历史出工查询集合
     */
    public int deleteExeWorkSegmentByMoveId(Long moveId);

    /**
     * 查询历史出工查询列表
     *
     * @param workShifitId 班别
     * @return 历史出工查询集合
     */
    public int deleteExeWorkSegmentByWorkShifitId(Long workShifitId);

    /**
     * 查询历史出工查询列表
     *
     * @param machineId 机台ID
     * @return 历史出工查询集合
     */
    public int deleteExeWorkSegmentByMachineId(Long machineId);

    /**
     * 查询历史出工查询列表
     *
     * @param startBy 出工人
     * @return 历史出工查询集合
     */
    public int deleteExeWorkSegmentByStartBy(Long startBy);

    /**
     * 查询历史出工查询列表
     *
     * @param finishBy 收工人
     * @return 历史出工查询集合
     */
    public int deleteExeWorkSegmentByFinishBy(Long finishBy);
}

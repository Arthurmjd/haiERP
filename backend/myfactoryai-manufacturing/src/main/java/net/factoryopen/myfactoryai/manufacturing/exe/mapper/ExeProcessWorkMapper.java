package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessWork;

/**
 * 工序出工Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public interface ExeProcessWorkMapper 
{
    /**
     * 查询工序出工
     * 
     * @param id 工序出工主键
     * @return 工序出工
     */
    public ExeProcessWork selectExeProcessWorkById(Long id);

    /**
     * 查询工序出工列表
     * 
     * @param exeProcessWork 工序出工
     * @return 工序出工集合
     */
    public List<ExeProcessWork> selectExeProcessWorkList(ExeProcessWork exeProcessWork);

    /**
     * 查询工序出工列表
     *
     * @param moveId 移转ID
     * @return 工序出工集合
     */
    public List<ExeProcessWork> selectExeProcessWorkListByMoveId(Long moveId);

    /**
     * 查询工序出工列表
     *
     * @param workShifitId 班别
     * @return 工序出工集合
     */
    public List<ExeProcessWork> selectExeProcessWorkListByWorkShifitId(Long workShifitId);

    /**
     * 查询工序出工列表
     *
     * @param startBy 出工人
     * @return 工序出工集合
     */
    public List<ExeProcessWork> selectExeProcessWorkListByStartBy(Long startBy);

    /**
     * 查询工序出工列表
     *
     * @param finishBy 收工人
     * @return 工序出工集合
     */
    public List<ExeProcessWork> selectExeProcessWorkListByFinishBy(Long finishBy);

    /**
     * 新增工序出工
     * 
     * @param exeProcessWork 工序出工
     * @return 结果
     */
    public int insertExeProcessWork(ExeProcessWork exeProcessWork);

    /**
     * 修改工序出工
     * 
     * @param exeProcessWork 工序出工
     * @return 结果
     */
    public int updateExeProcessWork(ExeProcessWork exeProcessWork);

    /**
     * 删除工序出工
     * 
     * @param id 工序出工主键
     * @return 结果
     */
    public int deleteExeProcessWorkById(Long id);

    /**
     * 批量删除工序出工
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeProcessWorkByIds(Long[] ids);

    /**
     * 查询工序出工列表
     *
     * @param moveId 移转ID
     * @return 工序出工集合
     */
    public int deleteExeProcessWorkByMoveId(Long moveId);

    /**
     * 查询工序出工列表
     *
     * @param workShifitId 班别
     * @return 工序出工集合
     */
    public int deleteExeProcessWorkByWorkShifitId(Long workShifitId);

    /**
     * 查询工序出工列表
     *
     * @param startBy 出工人
     * @return 工序出工集合
     */
    public int deleteExeProcessWorkByStartBy(Long startBy);

    /**
     * 查询工序出工列表
     *
     * @param finishBy 收工人
     * @return 工序出工集合
     */
    public int deleteExeProcessWorkByFinishBy(Long finishBy);
}

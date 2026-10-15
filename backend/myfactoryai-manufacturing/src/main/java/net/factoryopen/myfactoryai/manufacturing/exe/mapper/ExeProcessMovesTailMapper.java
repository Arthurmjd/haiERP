package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMovesTail;

/**
 * 工序移转记尾情Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public interface ExeProcessMovesTailMapper 
{
    /**
     * 查询工序移转记尾情
     * 
     * @param id 工序移转记尾情主键
     * @return 工序移转记尾情
     */
    public ExeProcessMovesTail selectExeProcessMovesTailById(Long id);

    /**
     * 查询工序移转记尾情列表
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailList(ExeProcessMovesTail exeProcessMovesTail);

    /**
     * 查询工序移转记尾情列表
     *
     * @param mlId 制造批
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByMlId(Long mlId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param productId 产品编码
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByProductId(Long productId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param processId 工序码
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByProcessId(Long processId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param lineId 产线
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByLineId(Long lineId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param unitId 单位
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByUnitId(Long unitId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param deptId 部门
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByDeptId(Long deptId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param groupId 班组
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByGroupId(Long groupId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param receiveBy 接收人
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByReceiveBy(Long receiveBy);

    /**
     * 查询工序移转记尾情列表
     *
     * @param postBy 转出人
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailListByPostBy(Long postBy);

    /**
     * 新增工序移转记尾情
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 结果
     */
    public int insertExeProcessMovesTail(ExeProcessMovesTail exeProcessMovesTail);

    /**
     * 修改工序移转记尾情
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 结果
     */
    public int updateExeProcessMovesTail(ExeProcessMovesTail exeProcessMovesTail);

    /**
     * 删除工序移转记尾情
     * 
     * @param id 工序移转记尾情主键
     * @return 结果
     */
    public int deleteExeProcessMovesTailById(Long id);

    /**
     * 批量删除工序移转记尾情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeProcessMovesTailByIds(Long[] ids);

    /**
     * 查询工序移转记尾情列表
     *
     * @param mlId 制造批
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByMlId(Long mlId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param productId 产品编码
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByProductId(Long productId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param processId 工序码
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByProcessId(Long processId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param lineId 产线
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByLineId(Long lineId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param unitId 单位
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByUnitId(Long unitId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param deptId 部门
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByDeptId(Long deptId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param groupId 班组
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByGroupId(Long groupId);

    /**
     * 查询工序移转记尾情列表
     *
     * @param receiveBy 接收人
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByReceiveBy(Long receiveBy);

    /**
     * 查询工序移转记尾情列表
     *
     * @param postBy 转出人
     * @return 工序移转记尾情集合
     */
    public int deleteExeProcessMovesTailByPostBy(Long postBy);
}

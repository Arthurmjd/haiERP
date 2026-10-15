package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import org.springframework.stereotype.Service;

/**
 * 工序移转记录Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public interface ExeProcessMovesMapper 
{
    /**
     * 查询工序移转记录
     * 
     * @param id 工序移转记录主键
     * @return 工序移转记录
     */
    public ExeProcessMoves selectExeProcessMovesById(Long id);

    /**
     * 查询工序移转记录列表
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesList(ExeProcessMoves exeProcessMoves);

    /**
     * 查询工序移转记录列表
     *
     * @param mlId 制造批
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByMlId(Long mlId);

    /**
     * 查询工序移转记录列表
     *
     * @param productId 产品编码
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByProductId(Long productId);

    /**
     * 查询工序移转记录列表
     *
     * @param processId 工序码
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByProcessId(Long processId);

    /**
     * 查询工序移转记录列表
     *
     * @param lineId 产线
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByLineId(Long lineId);

    /**
     * 查询工序移转记录列表
     *
     * @param unitId 单位
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByUnitId(Long unitId);

    /**
     * 查询工序移转记录列表
     *
     * @param deptId 部门
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByDeptId(Long deptId);

    /**
     * 查询工序移转记录列表
     *
     * @param groupId 班组
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByGroupId(Long groupId);

    /**
     * 查询工序移转记录列表
     *
     * @param receiveBy 接收人
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByReceiveBy(Long receiveBy);

    /**
     * 查询工序移转记录列表
     *
     * @param postBy 转出人
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesListByPostBy(Long postBy);

    /**
     * 新增工序移转记录
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 结果
     */
    public int insertExeProcessMoves(ExeProcessMoves exeProcessMoves);

    /**
     * 修改工序移转记录
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 结果
     */
    public int updateExeProcessMoves(ExeProcessMoves exeProcessMoves);

    /**
     * 删除工序移转记录
     * 
     * @param id 工序移转记录主键
     * @return 结果
     */
    public int deleteExeProcessMovesById(Long id);

    /**
     * 批量删除工序移转记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeProcessMovesByIds(Long[] ids);

    /**
     * 查询工序移转记录列表
     *
     * @param mlId 制造批
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByMlId(Long mlId);

    /**
     * 查询工序移转记录列表
     *
     * @param productId 产品编码
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByProductId(Long productId);

    /**
     * 查询工序移转记录列表
     *
     * @param processId 工序码
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByProcessId(Long processId);

    /**
     * 查询工序移转记录列表
     *
     * @param lineId 产线
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByLineId(Long lineId);

    /**
     * 查询工序移转记录列表
     *
     * @param unitId 单位
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByUnitId(Long unitId);

    /**
     * 查询工序移转记录列表
     *
     * @param deptId 部门
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByDeptId(Long deptId);

    /**
     * 查询工序移转记录列表
     *
     * @param groupId 班组
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByGroupId(Long groupId);

    /**
     * 查询工序移转记录列表
     *
     * @param receiveBy 接收人
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByReceiveBy(Long receiveBy);

    /**
     * 查询工序移转记录列表
     *
     * @param postBy 转出人
     * @return 工序移转记录集合
     */
    public int deleteExeProcessMovesByPostBy(Long postBy);
}

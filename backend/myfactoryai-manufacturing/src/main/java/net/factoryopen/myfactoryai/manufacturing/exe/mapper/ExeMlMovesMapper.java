package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlMoves;

/**
 * 历史移转查询Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public interface ExeMlMovesMapper 
{
    /**
     * 查询历史移转查询
     * 
     * @param id 历史移转查询主键
     * @return 历史移转查询
     */
    public ExeMlMoves selectExeMlMovesById(Long id);

    /**
     * 查询历史移转查询列表
     * 
     * @param exeMlMoves 历史移转查询
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesList(ExeMlMoves exeMlMoves);

    /**
     * 查询历史移转查询列表
     *
     * @param mlId 制造批
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByMlId(Long mlId);

    /**
     * 查询历史移转查询列表
     *
     * @param productId 产品编码
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByProductId(Long productId);

    /**
     * 查询历史移转查询列表
     *
     * @param processId 工序码
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByProcessId(Long processId);

    /**
     * 查询历史移转查询列表
     *
     * @param lineId 产线
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByLineId(Long lineId);

    /**
     * 查询历史移转查询列表
     *
     * @param unitId 单位
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByUnitId(Long unitId);

    /**
     * 查询历史移转查询列表
     *
     * @param deptId 部门
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByDeptId(Long deptId);

    /**
     * 查询历史移转查询列表
     *
     * @param groupId 班组
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByGroupId(Long groupId);

    /**
     * 查询历史移转查询列表
     *
     * @param receiveBy 接收人
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByReceiveBy(Long receiveBy);

    /**
     * 查询历史移转查询列表
     *
     * @param postBy 转出人
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesListByPostBy(Long postBy);

    /**
     * 新增历史移转查询
     * 
     * @param exeMlMoves 历史移转查询
     * @return 结果
     */
    public int insertExeMlMoves(ExeMlMoves exeMlMoves);

    /**
     * 修改历史移转查询
     * 
     * @param exeMlMoves 历史移转查询
     * @return 结果
     */
    public int updateExeMlMoves(ExeMlMoves exeMlMoves);

    /**
     * 删除历史移转查询
     * 
     * @param id 历史移转查询主键
     * @return 结果
     */
    public int deleteExeMlMovesById(Long id);

    /**
     * 批量删除历史移转查询
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlMovesByIds(Long[] ids);

    /**
     * 查询历史移转查询列表
     *
     * @param mlId 制造批
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByMlId(Long mlId);

    /**
     * 查询历史移转查询列表
     *
     * @param productId 产品编码
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByProductId(Long productId);

    /**
     * 查询历史移转查询列表
     *
     * @param processId 工序码
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByProcessId(Long processId);

    /**
     * 查询历史移转查询列表
     *
     * @param lineId 产线
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByLineId(Long lineId);

    /**
     * 查询历史移转查询列表
     *
     * @param unitId 单位
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByUnitId(Long unitId);

    /**
     * 查询历史移转查询列表
     *
     * @param deptId 部门
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByDeptId(Long deptId);

    /**
     * 查询历史移转查询列表
     *
     * @param groupId 班组
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByGroupId(Long groupId);

    /**
     * 查询历史移转查询列表
     *
     * @param receiveBy 接收人
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByReceiveBy(Long receiveBy);

    /**
     * 查询历史移转查询列表
     *
     * @param postBy 转出人
     * @return 历史移转查询集合
     */
    public int deleteExeMlMovesByPostBy(Long postBy);
}

package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMoves;

/**
 * 批次移转记录Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface PlnMlMovesMapper 
{
    /**
     * 查询批次移转记录
     * 
     * @param id 批次移转记录主键
     * @return 批次移转记录
     */
    public PlnMlMoves selectPlnMlMovesById(Long id);

    /**
     * 查询批次移转记录列表
     * 
     * @param plnMlMoves 批次移转记录
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesList(PlnMlMoves plnMlMoves);

    /**
     * 查询批次移转记录列表
     *
     * @param mlId 制造批
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByMlId(Long mlId);

    /**
     * 查询批次移转记录列表
     *
     * @param productId 产品编码
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByProductId(Long productId);

    /**
     * 查询批次移转记录列表
     *
     * @param processId 工序码
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByProcessId(Long processId);

    /**
     * 查询批次移转记录列表
     *
     * @param lineId 产线
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByLineId(Long lineId);

    /**
     * 查询批次移转记录列表
     *
     * @param unitId 单位
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByUnitId(Long unitId);

    /**
     * 查询批次移转记录列表
     *
     * @param deptId 部门
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByDeptId(Long deptId);

    /**
     * 查询批次移转记录列表
     *
     * @param groupId 班组
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByGroupId(Long groupId);

    /**
     * 查询批次移转记录列表
     *
     * @param receiveBy 接收人
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByReceiveBy(Long receiveBy);

    /**
     * 查询批次移转记录列表
     *
     * @param postBy 转出人
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesListByPostBy(Long postBy);

    /**
     * 新增批次移转记录
     * 
     * @param plnMlMoves 批次移转记录
     * @return 结果
     */
    public int insertPlnMlMoves(PlnMlMoves plnMlMoves);

    /**
     * 修改批次移转记录
     * 
     * @param plnMlMoves 批次移转记录
     * @return 结果
     */
    public int updatePlnMlMoves(PlnMlMoves plnMlMoves);

    /**
     * 删除批次移转记录
     * 
     * @param id 批次移转记录主键
     * @return 结果
     */
    public int deletePlnMlMovesById(Long id);

    /**
     * 批量删除批次移转记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlMovesByIds(Long[] ids);

    /**
     * 查询批次移转记录列表
     *
     * @param mlId 制造批
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByMlId(Long mlId);

    /**
     * 查询批次移转记录列表
     *
     * @param productId 产品编码
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByProductId(Long productId);

    /**
     * 查询批次移转记录列表
     *
     * @param processId 工序码
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByProcessId(Long processId);

    /**
     * 查询批次移转记录列表
     *
     * @param lineId 产线
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByLineId(Long lineId);

    /**
     * 查询批次移转记录列表
     *
     * @param unitId 单位
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByUnitId(Long unitId);

    /**
     * 查询批次移转记录列表
     *
     * @param deptId 部门
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByDeptId(Long deptId);

    /**
     * 查询批次移转记录列表
     *
     * @param groupId 班组
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByGroupId(Long groupId);

    /**
     * 查询批次移转记录列表
     *
     * @param receiveBy 接收人
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByReceiveBy(Long receiveBy);

    /**
     * 查询批次移转记录列表
     *
     * @param postBy 转出人
     * @return 批次移转记录集合
     */
    public int deletePlnMlMovesByPostBy(Long postBy);
}

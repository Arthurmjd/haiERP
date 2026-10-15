package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRouting;

/**
 * 批次工艺管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface PlnMlRoutingMapper 
{
    /**
     * 查询批次工艺管理
     * 
     * @param id 批次工艺管理主键
     * @return 批次工艺管理
     */
    public PlnMlRouting selectPlnMlRoutingById(Long id);

    /**
     * 查询批次工艺管理列表
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingList(PlnMlRouting plnMlRouting);

    /**
     * 查询批次工艺管理列表
     *
     * @param mlId 制造批
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByMlId(Long mlId);

    /**
     * 查询批次工艺管理列表
     *
     * @param productId 产品编码
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByProductId(Long productId);

    /**
     * 查询批次工艺管理列表
     *
     * @param processId 工序码
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByProcessId(Long processId);

    /**
     * 查询批次工艺管理列表
     *
     * @param lineId 产线
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByLineId(Long lineId);

    /**
     * 查询批次工艺管理列表
     *
     * @param unitId 单位
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByUnitId(Long unitId);

    /**
     * 查询批次工艺管理列表
     *
     * @param deptId 部门
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByDeptId(Long deptId);

    /**
     * 查询批次工艺管理列表
     *
     * @param groupId 班组
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByGroupId(Long groupId);

    /**
     * 查询批次工艺管理列表
     *
     * @param receiveBy 接收人
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByReceiveBy(Long receiveBy);

    /**
     * 查询批次工艺管理列表
     *
     * @param postBy 转出人
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingListByPostBy(Long postBy);

    /**
     * 新增批次工艺管理
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 结果
     */
    public int insertPlnMlRouting(PlnMlRouting plnMlRouting);

    /**
     * 修改批次工艺管理
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 结果
     */
    public int updatePlnMlRouting(PlnMlRouting plnMlRouting);

    /**
     * 删除批次工艺管理
     * 
     * @param id 批次工艺管理主键
     * @return 结果
     */
    public int deletePlnMlRoutingById(Long id);

    /**
     * 批量删除批次工艺管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingByIds(Long[] ids);

    /**
     * 查询批次工艺管理列表
     *
     * @param mlId 制造批
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByMlId(Long mlId);

    /**
     * 查询批次工艺管理列表
     *
     * @param productId 产品编码
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByProductId(Long productId);

    /**
     * 查询批次工艺管理列表
     *
     * @param processId 工序码
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByProcessId(Long processId);

    /**
     * 查询批次工艺管理列表
     *
     * @param lineId 产线
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByLineId(Long lineId);

    /**
     * 查询批次工艺管理列表
     *
     * @param unitId 单位
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByUnitId(Long unitId);

    /**
     * 查询批次工艺管理列表
     *
     * @param deptId 部门
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByDeptId(Long deptId);

    /**
     * 查询批次工艺管理列表
     *
     * @param groupId 班组
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByGroupId(Long groupId);

    /**
     * 查询批次工艺管理列表
     *
     * @param receiveBy 接收人
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByReceiveBy(Long receiveBy);

    /**
     * 查询批次工艺管理列表
     *
     * @param postBy 转出人
     * @return 批次工艺管理集合
     */
    public int deletePlnMlRoutingByPostBy(Long postBy);
}

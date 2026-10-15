package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivityTail;

/**
 * 订单透视尾情Mapper接口
 * 
 * @author admin
 * @date 2024-07-23
 */
public interface DelPerspectivityTailMapper 
{
    /**
     * 查询订单透视尾情
     * 
     * @param id 订单透视尾情主键
     * @return 订单透视尾情
     */
    public DelPerspectivityTail selectDelPerspectivityTailById(Long id);

    /**
     * 查询订单透视尾情列表
     * 
     * @param delPerspectivityTail 订单透视尾情
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailList(DelPerspectivityTail delPerspectivityTail);

    /**
     * 查询订单透视尾情列表
     *
     * @param userId 用户ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByUserId(Long userId);

    /**
     * 查询订单透视尾情列表
     *
     * @param customerId 客户ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByCustomerId(Long customerId);

    /**
     * 查询订单透视尾情列表
     *
     * @param soId 订单ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListBySoId(Long soId);

    /**
     * 查询订单透视尾情列表
     *
     * @param soItemId 单项ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListBySoItemId(Long soItemId);

    /**
     * 查询订单透视尾情列表
     *
     * @param productId 产品ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByProductId(Long productId);

    /**
     * 查询订单透视尾情列表
     *
     * @param woId 工单ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByWoId(Long woId);

    /**
     * 查询订单透视尾情列表
     *
     * @param mlId 批次ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByMlId(Long mlId);

    /**
     * 查询订单透视尾情列表
     *
     * @param mlLineId 批现产线ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByMlLineId(Long mlLineId);

    /**
     * 查询订单透视尾情列表
     *
     * @param mlProccessId 批现工序ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByMlProccessId(Long mlProccessId);

    /**
     * 查询订单透视尾情列表
     *
     * @param projectId 项目ID
     * @return 订单透视尾情集合
     */
    public List<DelPerspectivityTail> selectDelPerspectivityTailListByProjectId(Long projectId);

    /**
     * 新增订单透视尾情
     * 
     * @param delPerspectivityTail 订单透视尾情
     * @return 结果
     */
    public int insertDelPerspectivityTail(DelPerspectivityTail delPerspectivityTail);

    /**
     * 修改订单透视尾情
     * 
     * @param delPerspectivityTail 订单透视尾情
     * @return 结果
     */
    public int updateDelPerspectivityTail(DelPerspectivityTail delPerspectivityTail);

    /**
     * 删除订单透视尾情
     * 
     * @param id 订单透视尾情主键
     * @return 结果
     */
    public int deleteDelPerspectivityTailById(Long id);

    /**
     * 批量删除订单透视尾情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelPerspectivityTailByIds(Long[] ids);

    /**
     * 查询订单透视尾情列表
     *
     * @param userId 用户ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByUserId(Long userId);

    /**
     * 查询订单透视尾情列表
     *
     * @param customerId 客户ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByCustomerId(Long customerId);

    /**
     * 查询订单透视尾情列表
     *
     * @param soId 订单ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailBySoId(Long soId);

    /**
     * 查询订单透视尾情列表
     *
     * @param soItemId 单项ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailBySoItemId(Long soItemId);

    /**
     * 查询订单透视尾情列表
     *
     * @param productId 产品ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByProductId(Long productId);

    /**
     * 查询订单透视尾情列表
     *
     * @param woId 工单ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByWoId(Long woId);

    /**
     * 查询订单透视尾情列表
     *
     * @param mlId 批次ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByMlId(Long mlId);

    /**
     * 查询订单透视尾情列表
     *
     * @param mlLineId 批现产线ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByMlLineId(Long mlLineId);

    /**
     * 查询订单透视尾情列表
     *
     * @param mlProccessId 批现工序ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByMlProccessId(Long mlProccessId);

    /**
     * 查询订单透视尾情列表
     *
     * @param projectId 项目ID
     * @return 订单透视尾情集合
     */
    public int deleteDelPerspectivityTailByProjectId(Long projectId);
}

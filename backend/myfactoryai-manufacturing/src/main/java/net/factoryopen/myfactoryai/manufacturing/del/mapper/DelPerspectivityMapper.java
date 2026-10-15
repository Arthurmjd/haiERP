package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivity;

/**
 * 订单透视表Mapper接口
 * 
 * @author admin
 * @date 2024-07-23
 */
public interface DelPerspectivityMapper 
{
    /**
     * 查询订单透视表
     * 
     * @param id 订单透视表主键
     * @return 订单透视表
     */
    public DelPerspectivity selectDelPerspectivityById(Long id);

    /**
     * 查询订单透视表列表
     * 
     * @param delPerspectivity 订单透视表
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityList(DelPerspectivity delPerspectivity);

    /**
     * 查询订单透视表列表
     *
     * @param userId 用户ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByUserId(Long userId);

    /**
     * 查询订单透视表列表
     *
     * @param customerId 客户ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByCustomerId(Long customerId);

    /**
     * 查询订单透视表列表
     *
     * @param soId 订单ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListBySoId(Long soId);

    /**
     * 查询订单透视表列表
     *
     * @param soItemId 单项ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListBySoItemId(Long soItemId);

    /**
     * 查询订单透视表列表
     *
     * @param productId 产品ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByProductId(Long productId);

    /**
     * 查询订单透视表列表
     *
     * @param productName 产品名称
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByProductName(Long productName);

    /**
     * 查询订单透视表列表
     *
     * @param woId 工单ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByWoId(Long woId);

    /**
     * 查询订单透视表列表
     *
     * @param mlId 批次ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByMlId(Long mlId);

    /**
     * 查询订单透视表列表
     *
     * @param mlLineId 批现产线ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByMlLineId(Long mlLineId);

    /**
     * 查询订单透视表列表
     *
     * @param mlProccessId 批现工序ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByMlProccessId(Long mlProccessId);

    /**
     * 查询订单透视表列表
     *
     * @param projectId 项目ID
     * @return 订单透视表集合
     */
    public List<DelPerspectivity> selectDelPerspectivityListByProjectId(Long projectId);

    /**
     * 新增订单透视表
     * 
     * @param delPerspectivity 订单透视表
     * @return 结果
     */
    public int insertDelPerspectivity(DelPerspectivity delPerspectivity);

    /**
     * 修改订单透视表
     * 
     * @param delPerspectivity 订单透视表
     * @return 结果
     */
    public int updateDelPerspectivity(DelPerspectivity delPerspectivity);

    /**
     * 删除订单透视表
     * 
     * @param id 订单透视表主键
     * @return 结果
     */
    public int deleteDelPerspectivityById(Long id);

    /**
     * 批量删除订单透视表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelPerspectivityByIds(Long[] ids);

    /**
     * 查询订单透视表列表
     *
     * @param userId 用户ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByUserId(Long userId);

    /**
     * 查询订单透视表列表
     *
     * @param customerId 客户ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByCustomerId(Long customerId);

    /**
     * 查询订单透视表列表
     *
     * @param soId 订单ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityBySoId(Long soId);

    /**
     * 查询订单透视表列表
     *
     * @param soItemId 单项ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityBySoItemId(Long soItemId);

    /**
     * 查询订单透视表列表
     *
     * @param productId 产品ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByProductId(Long productId);

    /**
     * 查询订单透视表列表
     *
     * @param productName 产品名称
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByProductName(Long productName);

    /**
     * 查询订单透视表列表
     *
     * @param woId 工单ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByWoId(Long woId);

    /**
     * 查询订单透视表列表
     *
     * @param mlId 批次ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByMlId(Long mlId);

    /**
     * 查询订单透视表列表
     *
     * @param mlLineId 批现产线ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByMlLineId(Long mlLineId);

    /**
     * 查询订单透视表列表
     *
     * @param mlProccessId 批现工序ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByMlProccessId(Long mlProccessId);

    /**
     * 查询订单透视表列表
     *
     * @param projectId 项目ID
     * @return 订单透视表集合
     */
    public int deleteDelPerspectivityByProjectId(Long projectId);
}

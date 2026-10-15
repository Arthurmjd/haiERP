package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivity;

/**
 * 订单透视表Service接口
 * 
 * @author admin
 * @date 2024-07-23
 */
public interface IDelPerspectivityService 
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
     * 批量删除订单透视表
     * 
     * @param ids 需要删除的订单透视表主键集合
     * @return 结果
     */
    public int deleteDelPerspectivityByIds(Long[] ids);

    /**
     * 删除订单透视表信息
     * 
     * @param id 订单透视表主键
     * @return 结果
     */
    public int deleteDelPerspectivityById(Long id);

    /**
     * 透算
     *
     * @param delPerspectivity 透算
     * @return 结果
     */
    public int calculatePerspectivity(DelPerspectivity delPerspectivity) throws Exception;


}

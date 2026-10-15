package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivityTail;

/**
 * 订单透视尾情Service接口
 * 
 * @author admin
 * @date 2024-07-23
 */
public interface IDelPerspectivityTailService 
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
     * 批量删除订单透视尾情
     * 
     * @param ids 需要删除的订单透视尾情主键集合
     * @return 结果
     */
    public int deleteDelPerspectivityTailByIds(Long[] ids);

    /**
     * 删除订单透视尾情信息
     * 
     * @param id 订单透视尾情主键
     * @return 结果
     */
    public int deleteDelPerspectivityTailById(Long id);


}

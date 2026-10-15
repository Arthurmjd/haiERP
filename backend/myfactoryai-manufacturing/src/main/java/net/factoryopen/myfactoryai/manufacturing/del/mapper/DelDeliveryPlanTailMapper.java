package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlanTail;

/**
 * 发货计划尾情Mapper接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface DelDeliveryPlanTailMapper 
{
    /**
     * 查询发货计划尾情
     * 
     * @param id 发货计划尾情主键
     * @return 发货计划尾情
     */
    public DelDeliveryPlanTail selectDelDeliveryPlanTailById(Long id);

    /**
     * 查询发货计划尾情列表
     * 
     * @param delDeliveryPlanTail 发货计划尾情
     * @return 发货计划尾情集合
     */
    public List<DelDeliveryPlanTail> selectDelDeliveryPlanTailList(DelDeliveryPlanTail delDeliveryPlanTail);

    /**
     * 查询发货计划尾情列表
     *
     * @param soId 销售订单
     * @return 发货计划尾情集合
     */
    public List<DelDeliveryPlanTail> selectDelDeliveryPlanTailListBySoId(Long soId);

    /**
     * 查询发货计划尾情列表
     *
     * @param soItemId 订单项
     * @return 发货计划尾情集合
     */
    public List<DelDeliveryPlanTail> selectDelDeliveryPlanTailListBySoItemId(Long soItemId);

    /**
     * 查询发货计划尾情列表
     *
     * @param customerId 客户ID
     * @return 发货计划尾情集合
     */
    public List<DelDeliveryPlanTail> selectDelDeliveryPlanTailListByCustomerId(Long customerId);

    /**
     * 新增发货计划尾情
     * 
     * @param delDeliveryPlanTail 发货计划尾情
     * @return 结果
     */
    public int insertDelDeliveryPlanTail(DelDeliveryPlanTail delDeliveryPlanTail);

    /**
     * 修改发货计划尾情
     * 
     * @param delDeliveryPlanTail 发货计划尾情
     * @return 结果
     */
    public int updateDelDeliveryPlanTail(DelDeliveryPlanTail delDeliveryPlanTail);

    /**
     * 删除发货计划尾情
     * 
     * @param id 发货计划尾情主键
     * @return 结果
     */
    public int deleteDelDeliveryPlanTailById(Long id);

    /**
     * 批量删除发货计划尾情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelDeliveryPlanTailByIds(Long[] ids);

    /**
     * 查询发货计划尾情列表
     *
     * @param soId 销售订单
     * @return 发货计划尾情集合
     */
    public int deleteDelDeliveryPlanTailBySoId(Long soId);

    /**
     * 查询发货计划尾情列表
     *
     * @param soItemId 订单项
     * @return 发货计划尾情集合
     */
    public int deleteDelDeliveryPlanTailBySoItemId(Long soItemId);

    /**
     * 查询发货计划尾情列表
     *
     * @param customerId 客户ID
     * @return 发货计划尾情集合
     */
    public int deleteDelDeliveryPlanTailByCustomerId(Long customerId);
}

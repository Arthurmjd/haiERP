package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlanTail;

/**
 * 发货计划尾情Service接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface IDelDeliveryPlanTailService 
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
     * 批量删除发货计划尾情
     * 
     * @param ids 需要删除的发货计划尾情主键集合
     * @return 结果
     */
    public int deleteDelDeliveryPlanTailByIds(Long[] ids);

    /**
     * 删除发货计划尾情信息
     * 
     * @param id 发货计划尾情主键
     * @return 结果
     */
    public int deleteDelDeliveryPlanTailById(Long id);


}

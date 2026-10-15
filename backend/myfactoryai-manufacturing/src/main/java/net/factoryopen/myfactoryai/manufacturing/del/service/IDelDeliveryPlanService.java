package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;

/**
 * 发货计划维护Service接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface IDelDeliveryPlanService 
{
    /**
     * 查询发货计划维护
     * 
     * @param id 发货计划维护主键
     * @return 发货计划维护
     */
    public DelDeliveryPlan selectDelDeliveryPlanById(Long id);

    /**
     * 查询发货计划维护列表
     * 
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanDraftList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanPublishingList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanProcessingList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanFinishingList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanHistoryList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 查询发货计划维护列表
     *
     * @param delDeliveryPlan 发货计划维护
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanAllList(DelDeliveryPlan delDeliveryPlan);

    /**
     * 新增发货计划维护
     * 
     * @param delDeliveryPlan 发货计划维护
     * @return 结果
     */
    public int insertDelDeliveryPlan(DelDeliveryPlan delDeliveryPlan);

    /**
     * 修改发货计划维护
     * 
     * @param delDeliveryPlan 发货计划维护
     * @return 结果
     */
    public int updateDelDeliveryPlan(DelDeliveryPlan delDeliveryPlan);

    /**
     * 批量删除发货计划维护
     * 
     * @param ids 需要删除的发货计划维护主键集合
     * @return 结果
     */
    public int deleteDelDeliveryPlanByIds(Long[] ids);

    /**
     * 删除发货计划维护信息
     * 
     * @param id 发货计划维护主键
     * @return 结果
     */
    public int deleteDelDeliveryPlanById(Long id);

    /**
     * 冻结
     *
     * @param delDeliveryPlan 冻结
     * @return 结果
     */
    public int froze(DelDeliveryPlan delDeliveryPlan);

    /**
     * 解冻
     *
     * @param delDeliveryPlan 解冻
     * @return 结果
     */
    public int unfroze(DelDeliveryPlan delDeliveryPlan);

    /**
     * 停用
     *
     * @param delDeliveryPlan 停用
     * @return 结果
     */
    public int disable(DelDeliveryPlan delDeliveryPlan);

    /**
     * 强结
     *
     * @param delDeliveryPlan 强结
     * @return 结果
     */
    public int finishforcely(DelDeliveryPlan delDeliveryPlan);

    /**
     * 转单
     *
     * @param delDeliveryPlan 转单
     * @return 结果
     */
    public int billDelivery(Long[] ids, DelDeliveryPlan delDeliveryPlan) throws Exception;


}

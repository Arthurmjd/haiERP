package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;

/**
 * 发货计划维护Mapper接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface DelDeliveryPlanMapper 
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
     * 查询发货计划维护列表
     *
     * @param soId 销售订单
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanListBySoId(Long soId);

    /**
     * 查询发货计划维护列表
     *
     * @param soItemId 订单项
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanListBySoItemId(Long soItemId);

    /**
     * 查询发货计划维护列表
     *
     * @param customerId 客户ID
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanListByCustomerId(Long customerId);

    /**
     * 查询发货计划维护列表
     *
     * @param projectId 项目ID
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanListByProjectId(Long projectId);

    /**
     * 查询发货计划维护列表
     *
     * @param salesId 销售员ID
     * @return 发货计划维护集合
     */
    public List<DelDeliveryPlan> selectDelDeliveryPlanListBySalesId(Long salesId);

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
     * 删除发货计划维护
     * 
     * @param id 发货计划维护主键
     * @return 结果
     */
    public int deleteDelDeliveryPlanById(Long id);

    /**
     * 批量删除发货计划维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelDeliveryPlanByIds(Long[] ids);

    /**
     * 查询发货计划维护列表
     *
     * @param soId 销售订单
     * @return 发货计划维护集合
     */
    public int deleteDelDeliveryPlanBySoId(Long soId);

    /**
     * 查询发货计划维护列表
     *
     * @param soItemId 订单项
     * @return 发货计划维护集合
     */
    public int deleteDelDeliveryPlanBySoItemId(Long soItemId);

    /**
     * 查询发货计划维护列表
     *
     * @param customerId 客户ID
     * @return 发货计划维护集合
     */
    public int deleteDelDeliveryPlanByCustomerId(Long customerId);

    /**
     * 查询发货计划维护列表
     *
     * @param projectId 项目ID
     * @return 发货计划维护集合
     */
    public int deleteDelDeliveryPlanByProjectId(Long projectId);

    /**
     * 查询发货计划维护列表
     *
     * @param salesId 销售员ID
     * @return 发货计划维护集合
     */
    public int deleteDelDeliveryPlanBySalesId(Long salesId);
}

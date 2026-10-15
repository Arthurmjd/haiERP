package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;

/**
 * 发货单管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelDeliveryOrderService 
{
    /**
     * 查询发货单管理
     * 
     * @param id 发货单管理主键
     * @return 发货单管理
     */
    public DelDeliveryOrder selectDelDeliveryOrderById(Long id);

    /**
     * 查询发货单管理列表
     * 
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderDraftList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderPublishingList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderProcessingList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderFinishingList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderHistoryList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 查询发货单管理列表
     *
     * @param delDeliveryOrder 发货单管理
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderAllList(DelDeliveryOrder delDeliveryOrder);

    /**
     * 新增发货单管理
     * 
     * @param delDeliveryOrder 发货单管理
     * @return 结果
     */
    public int insertDelDeliveryOrder(DelDeliveryOrder delDeliveryOrder);

    /**
     * 修改发货单管理
     * 
     * @param delDeliveryOrder 发货单管理
     * @return 结果
     */
    public int updateDelDeliveryOrder(DelDeliveryOrder delDeliveryOrder);

    /**
     * 批量删除发货单管理
     * 
     * @param ids 需要删除的发货单管理主键集合
     * @return 结果
     */
    public int deleteDelDeliveryOrderByIds(Long[] ids);

    /**
     * 删除发货单管理信息
     * 
     * @param id 发货单管理主键
     * @return 结果
     */
    public int deleteDelDeliveryOrderById(Long id);

    /**
     * 冻结
     *
     * @param delDeliveryOrder 冻结
     * @return 结果
     */
    public int froze(DelDeliveryOrder delDeliveryOrder);

    /**
     * 解冻
     *
     * @param delDeliveryOrder 解冻
     * @return 结果
     */
    public int unfroze(DelDeliveryOrder delDeliveryOrder);

    /**
     * 停用
     *
     * @param delDeliveryOrder 停用
     * @return 结果
     */
    public int disable(DelDeliveryOrder delDeliveryOrder);

    /**
     * 强结
     *
     * @param delDeliveryOrder 强结
     * @return 结果
     */
    public int finishforcely(DelDeliveryOrder delDeliveryOrder);

    /**
     * 变更
     *
     * @param delDeliveryOrder 变更
     * @return 结果
     */
    public int changeDelDeliveryOrder(DelDeliveryOrder delDeliveryOrder);

    /**
     * 发货
     *
     * @param ids id集合
     * @return 结果
     */
    public int delivery(Long[] ids) throws Exception;


}

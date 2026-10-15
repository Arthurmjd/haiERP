package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;

/**
 * 发货单管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface DelDeliveryOrderMapper 
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
     * 查询发货单管理列表
     *
     * @param customerId 客户
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderListByCustomerId(Long customerId);

    /**
     * 查询发货单管理列表
     *
     * @param addressId 发货地址ID
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderListByAddressId(Long addressId);

    /**
     * 查询发货单管理列表
     *
     * @param projectId 项目
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderListByProjectId(Long projectId);

    /**
     * 查询发货单管理列表
     *
     * @param salesId 销售员
     * @return 发货单管理集合
     */
    public List<DelDeliveryOrder> selectDelDeliveryOrderListBySalesId(Long salesId);

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
     * 删除发货单管理
     * 
     * @param id 发货单管理主键
     * @return 结果
     */
    public int deleteDelDeliveryOrderById(Long id);

    /**
     * 批量删除发货单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelDeliveryOrderByIds(Long[] ids);

    /**
     * 查询发货单管理列表
     *
     * @param customerId 客户
     * @return 发货单管理集合
     */
    public int deleteDelDeliveryOrderByCustomerId(Long customerId);

    /**
     * 查询发货单管理列表
     *
     * @param addressId 发货地址ID
     * @return 发货单管理集合
     */
    public int deleteDelDeliveryOrderByAddressId(Long addressId);

    /**
     * 查询发货单管理列表
     *
     * @param projectId 项目
     * @return 发货单管理集合
     */
    public int deleteDelDeliveryOrderByProjectId(Long projectId);

    /**
     * 查询发货单管理列表
     *
     * @param salesId 销售员
     * @return 发货单管理集合
     */
    public int deleteDelDeliveryOrderBySalesId(Long salesId);
}

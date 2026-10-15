package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplanReceiving;

/**
 * 外协收货Mapper接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface DelOsplanReceivingMapper 
{
    /**
     * 查询外协收货
     * 
     * @param id 外协收货主键
     * @return 外协收货
     */
    public DelOsplanReceiving selectDelOsplanReceivingById(Long id);

    /**
     * 查询外协收货列表
     * 
     * @param delOsplanReceiving 外协收货
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingList(DelOsplanReceiving delOsplanReceiving);

    /**
     * 查询外协收货列表
     *
     * @param osplanId 外协计划ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListByOsplanId(Long osplanId);

    /**
     * 查询外协收货列表
     *
     * @param productId 产品ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListByProductId(Long productId);

    /**
     * 查询外协收货列表
     *
     * @param supplierId 供应商ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListBySupplierId(Long supplierId);

    /**
     * 查询外协收货列表
     *
     * @param customerId 客户ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListByCustomerId(Long customerId);

    /**
     * 查询外协收货列表
     *
     * @param soId 销售订单ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListBySoId(Long soId);

    /**
     * 查询外协收货列表
     *
     * @param soItemId 订单项ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListBySoItemId(Long soItemId);

    /**
     * 查询外协收货列表
     *
     * @param projectId 项目ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListByProjectId(Long projectId);

    /**
     * 查询外协收货列表
     *
     * @param salesId 销售员ID
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingListBySalesId(Long salesId);

    /**
     * 新增外协收货
     * 
     * @param delOsplanReceiving 外协收货
     * @return 结果
     */
    public int insertDelOsplanReceiving(DelOsplanReceiving delOsplanReceiving);

    /**
     * 修改外协收货
     * 
     * @param delOsplanReceiving 外协收货
     * @return 结果
     */
    public int updateDelOsplanReceiving(DelOsplanReceiving delOsplanReceiving);

    /**
     * 删除外协收货
     * 
     * @param id 外协收货主键
     * @return 结果
     */
    public int deleteDelOsplanReceivingById(Long id);

    /**
     * 批量删除外协收货
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelOsplanReceivingByIds(Long[] ids);

    /**
     * 查询外协收货列表
     *
     * @param osplanId 外协计划ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingByOsplanId(Long osplanId);

    /**
     * 查询外协收货列表
     *
     * @param productId 产品ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingByProductId(Long productId);

    /**
     * 查询外协收货列表
     *
     * @param supplierId 供应商ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingBySupplierId(Long supplierId);

    /**
     * 查询外协收货列表
     *
     * @param customerId 客户ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingByCustomerId(Long customerId);

    /**
     * 查询外协收货列表
     *
     * @param soId 销售订单ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingBySoId(Long soId);

    /**
     * 查询外协收货列表
     *
     * @param soItemId 订单项ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingBySoItemId(Long soItemId);

    /**
     * 查询外协收货列表
     *
     * @param projectId 项目ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingByProjectId(Long projectId);

    /**
     * 查询外协收货列表
     *
     * @param salesId 销售员ID
     * @return 外协收货集合
     */
    public int deleteDelOsplanReceivingBySalesId(Long salesId);
}

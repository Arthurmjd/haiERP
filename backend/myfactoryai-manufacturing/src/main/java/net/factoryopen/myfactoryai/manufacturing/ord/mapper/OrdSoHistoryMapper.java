package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoHistory;

/**
 * 订单历史查询Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface OrdSoHistoryMapper 
{
    /**
     * 查询订单历史查询
     * 
     * @param id 订单历史查询主键
     * @return 订单历史查询
     */
    public OrdSoHistory selectOrdSoHistoryById(Long id);

    /**
     * 查询订单历史查询列表
     * 
     * @param ordSoHistory 订单历史查询
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryList(OrdSoHistory ordSoHistory);

    /**
     * 查询订单历史查询列表
     *
     * @param customerId 客户
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByCustomerId(Long customerId);

    /**
     * 查询订单历史查询列表
     *
     * @param invoicePatternId 开票方式
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询订单历史查询列表
     *
     * @param payPatternId 付款方式
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByPayPatternId(Long payPatternId);

    /**
     * 查询订单历史查询列表
     *
     * @param currencyId 币别
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByCurrencyId(Long currencyId);

    /**
     * 查询订单历史查询列表
     *
     * @param shipmentTypeId 交货方式
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询订单历史查询列表
     *
     * @param addressId 地址
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByAddressId(Long addressId);

    /**
     * 查询订单历史查询列表
     *
     * @param contactId 联系人
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByContactId(Long contactId);

    /**
     * 查询订单历史查询列表
     *
     * @param projectId 项目
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByProjectId(Long projectId);

    /**
     * 查询订单历史查询列表
     *
     * @param salesId 销售员
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListBySalesId(Long salesId);

    /**
     * 查询订单历史查询列表
     *
     * @param deptId 部门
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryListByDeptId(Long deptId);

    /**
     * 新增订单历史查询
     * 
     * @param ordSoHistory 订单历史查询
     * @return 结果
     */
    public int insertOrdSoHistory(OrdSoHistory ordSoHistory);

    /**
     * 修改订单历史查询
     * 
     * @param ordSoHistory 订单历史查询
     * @return 结果
     */
    public int updateOrdSoHistory(OrdSoHistory ordSoHistory);

    /**
     * 删除订单历史查询
     * 
     * @param id 订单历史查询主键
     * @return 结果
     */
    public int deleteOrdSoHistoryById(Long id);

    /**
     * 批量删除订单历史查询
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdSoHistoryByIds(Long[] ids);

    /**
     * 查询订单历史查询列表
     *
     * @param customerId 客户
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByCustomerId(Long customerId);

    /**
     * 查询订单历史查询列表
     *
     * @param invoicePatternId 开票方式
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询订单历史查询列表
     *
     * @param payPatternId 付款方式
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByPayPatternId(Long payPatternId);

    /**
     * 查询订单历史查询列表
     *
     * @param currencyId 币别
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByCurrencyId(Long currencyId);

    /**
     * 查询订单历史查询列表
     *
     * @param shipmentTypeId 交货方式
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询订单历史查询列表
     *
     * @param addressId 地址
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByAddressId(Long addressId);

    /**
     * 查询订单历史查询列表
     *
     * @param contactId 联系人
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByContactId(Long contactId);

    /**
     * 查询订单历史查询列表
     *
     * @param projectId 项目
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByProjectId(Long projectId);

    /**
     * 查询订单历史查询列表
     *
     * @param salesId 销售员
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryBySalesId(Long salesId);

    /**
     * 查询订单历史查询列表
     *
     * @param deptId 部门
     * @return 订单历史查询集合
     */
    public int deleteOrdSoHistoryByDeptId(Long deptId);
}

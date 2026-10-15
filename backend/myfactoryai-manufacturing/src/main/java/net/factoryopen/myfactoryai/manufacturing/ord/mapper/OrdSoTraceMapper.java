package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoTrace;

/**
 * 在制订单跟踪Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface OrdSoTraceMapper 
{
    /**
     * 查询在制订单跟踪
     * 
     * @param id 在制订单跟踪主键
     * @return 在制订单跟踪
     */
    public OrdSoTrace selectOrdSoTraceById(Long id);

    /**
     * 查询在制订单跟踪列表
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceList(OrdSoTrace ordSoTrace);

    /**
     * 查询在制订单跟踪列表
     *
     * @param customerId 客户
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByCustomerId(Long customerId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param invoicePatternId 开票方式
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param payPatternId 付款方式
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByPayPatternId(Long payPatternId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param currencyId 币别
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByCurrencyId(Long currencyId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param shipmentTypeId 交货方式
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param addressId 地址
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByAddressId(Long addressId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param contactId 联系人
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByContactId(Long contactId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param projectId 项目
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByProjectId(Long projectId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param salesId 销售员
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListBySalesId(Long salesId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param deptId 部门
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceListByDeptId(Long deptId);

    /**
     * 新增在制订单跟踪
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 结果
     */
    public int insertOrdSoTrace(OrdSoTrace ordSoTrace);

    /**
     * 修改在制订单跟踪
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 结果
     */
    public int updateOrdSoTrace(OrdSoTrace ordSoTrace);

    /**
     * 删除在制订单跟踪
     * 
     * @param id 在制订单跟踪主键
     * @return 结果
     */
    public int deleteOrdSoTraceById(Long id);

    /**
     * 批量删除在制订单跟踪
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdSoTraceByIds(Long[] ids);

    /**
     * 查询在制订单跟踪列表
     *
     * @param customerId 客户
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByCustomerId(Long customerId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param invoicePatternId 开票方式
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param payPatternId 付款方式
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByPayPatternId(Long payPatternId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param currencyId 币别
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByCurrencyId(Long currencyId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param shipmentTypeId 交货方式
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param addressId 地址
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByAddressId(Long addressId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param contactId 联系人
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByContactId(Long contactId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param projectId 项目
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByProjectId(Long projectId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param salesId 销售员
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceBySalesId(Long salesId);

    /**
     * 查询在制订单跟踪列表
     *
     * @param deptId 部门
     * @return 在制订单跟踪集合
     */
    public int deleteOrdSoTraceByDeptId(Long deptId);
}

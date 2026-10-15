package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import org.springframework.stereotype.Service;

/**
 * 订单登记发布Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public interface OrdSoMapper 
{
    /**
     * 查询订单登记发布
     * 
     * @param id 订单登记发布主键
     * @return 订单登记发布
     */
    public OrdSo selectOrdSoById(Long id);

    /**
     * 查询订单登记发布列表
     * 
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoDraftList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoPublishingList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoProcessingList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoFinishingList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoHistoryList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoAllList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoList4Adult(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param customerId 客户
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByCustomerId(Long customerId);

    /**
     * 查询订单登记发布列表
     *
     * @param invoicePatternId 开票方式
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询订单登记发布列表
     *
     * @param payPatternId 付款方式
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByPayPatternId(Long payPatternId);

    /**
     * 查询订单登记发布列表
     *
     * @param currencyId 币别
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByCurrencyId(Long currencyId);

    /**
     * 查询订单登记发布列表
     *
     * @param shipmentTypeId 交货方式
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询订单登记发布列表
     *
     * @param addressId 地址
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByAddressId(Long addressId);

    /**
     * 查询订单登记发布列表
     *
     * @param contactId 联系人
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByContactId(Long contactId);

    /**
     * 查询订单登记发布列表
     *
     * @param projectId 项目
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByProjectId(Long projectId);

    /**
     * 查询订单登记发布列表
     *
     * @param salesId 销售员
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListBySalesId(Long salesId);

    /**
     * 查询订单登记发布列表
     *
     * @param deptId 部门
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoListByDeptId(Long deptId);

    /**
     * 新增订单登记发布
     * 
     * @param ordSo 订单登记发布
     * @return 结果
     */
    public int insertOrdSo(OrdSo ordSo);

    /**
     * 修改订单登记发布
     * 
     * @param ordSo 订单登记发布
     * @return 结果
     */
    public int updateOrdSo(OrdSo ordSo);

    /**
     * 删除订单登记发布
     * 
     * @param id 订单登记发布主键
     * @return 结果
     */
    public int deleteOrdSoById(Long id);

    /**
     * 批量删除订单登记发布
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdSoByIds(Long[] ids);

    /**
     * 查询订单登记发布列表
     *
     * @param customerId 客户
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByCustomerId(Long customerId);

    /**
     * 查询订单登记发布列表
     *
     * @param invoicePatternId 开票方式
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询订单登记发布列表
     *
     * @param payPatternId 付款方式
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByPayPatternId(Long payPatternId);

    /**
     * 查询订单登记发布列表
     *
     * @param currencyId 币别
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByCurrencyId(Long currencyId);

    /**
     * 查询订单登记发布列表
     *
     * @param shipmentTypeId 交货方式
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询订单登记发布列表
     *
     * @param addressId 地址
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByAddressId(Long addressId);

    /**
     * 查询订单登记发布列表
     *
     * @param contactId 联系人
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByContactId(Long contactId);

    /**
     * 查询订单登记发布列表
     *
     * @param projectId 项目
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByProjectId(Long projectId);

    /**
     * 查询订单登记发布列表
     *
     * @param salesId 销售员
     * @return 订单登记发布集合
     */
    public int deleteOrdSoBySalesId(Long salesId);

    /**
     * 查询订单登记发布列表
     *
     * @param deptId 部门
     * @return 订单登记发布集合
     */
    public int deleteOrdSoByDeptId(Long deptId);
}

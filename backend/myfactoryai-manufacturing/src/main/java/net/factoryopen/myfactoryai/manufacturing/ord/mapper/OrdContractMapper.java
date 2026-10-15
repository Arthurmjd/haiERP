package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import org.springframework.stereotype.Service;

/**
 * 合同登记Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public interface OrdContractMapper 
{
    /**
     * 查询合同登记发布
     * 
     * @param id 合同登记发布主键
     * @return 合同登记发布
     */
    public OrdContract selectOrdContractById(Long id);

    /**
     * 查询合同登记发布列表
     * 
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractDraftList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractPublishingList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractProcessingList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractFinishingList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractHistoryList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractAllList(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param ordContract 合同登记发布
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractList4Adult(OrdContract ordContract);

    /**
     * 查询合同登记发布列表
     *
     * @param customerId 客户
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByCustomerId(Long customerId);

    /**
     * 查询合同登记发布列表
     *
     * @param invoicePatternId 开票方式
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询合同登记发布列表
     *
     * @param payPatternId 付款方式
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByPayPatternId(Long payPatternId);

    /**
     * 查询合同登记发布列表
     *
     * @param currencyId 币别
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByCurrencyId(Long currencyId);

    /**
     * 查询合同登记发布列表
     *
     * @param shipmentTypeId 交货方式
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询合同登记发布列表
     *
     * @param addressId 地址
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByAddressId(Long addressId);

    /**
     * 查询合同登记发布列表
     *
     * @param contactId 联系人
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByContactId(Long contactId);

    /**
     * 查询合同登记发布列表
     *
     * @param projectId 项目
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByProjectId(Long projectId);

    /**
     * 查询合同登记发布列表
     *
     * @param salesId 销售员
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListBySalesId(Long salesId);

    /**
     * 查询合同登记发布列表
     *
     * @param deptId 部门
     * @return 合同登记发布集合
     */
    public List<OrdContract> selectOrdContractListByDeptId(Long deptId);

    /**
     * 新增合同登记发布
     * 
     * @param ordContract 合同登记发布
     * @return 结果
     */
    public int insertOrdContract(OrdContract ordContract);

    /**
     * 修改合同登记发布
     * 
     * @param ordContract 合同登记发布
     * @return 结果
     */
    public int updateOrdContract(OrdContract ordContract);

    /**
     * 删除合同登记发布
     * 
     * @param id 合同登记发布主键
     * @return 结果
     */
    public int deleteOrdContractById(Long id);

    /**
     * 批量删除合同登记发布
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdContractByIds(Long[] ids);

    /**
     * 查询合同登记发布列表
     *
     * @param customerId 客户
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByCustomerId(Long customerId);

    /**
     * 查询合同登记发布列表
     *
     * @param invoicePatternId 开票方式
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询合同登记发布列表
     *
     * @param payPatternId 付款方式
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByPayPatternId(Long payPatternId);

    /**
     * 查询合同登记发布列表
     *
     * @param currencyId 币别
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByCurrencyId(Long currencyId);

    /**
     * 查询合同登记发布列表
     *
     * @param shipmentTypeId 交货方式
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询合同登记发布列表
     *
     * @param addressId 地址
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByAddressId(Long addressId);

    /**
     * 查询合同登记发布列表
     *
     * @param contactId 联系人
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByContactId(Long contactId);

    /**
     * 查询合同登记发布列表
     *
     * @param projectId 项目
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByProjectId(Long projectId);

    /**
     * 查询合同登记发布列表
     *
     * @param salesId 销售员
     * @return 合同登记发布集合
     */
    public int deleteOrdContractBySalesId(Long salesId);

    /**
     * 查询合同登记发布列表
     *
     * @param deptId 部门
     * @return 合同登记发布集合
     */
    public int deleteOrdContractByDeptId(Long deptId);
}

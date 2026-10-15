package net.factoryopen.myfactoryai.manufacturing.cha.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import org.springframework.stereotype.Service;

/**
 * 客户数据管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public interface ChaCustomerMapper 
{
    /**
     * 查询客户数据管理
     * 
     * @param id 客户数据管理主键
     * @return 客户数据管理
     */
    public ChaCustomer selectChaCustomerById(Long id);

    /**
     * 查询客户数据管理
     *
     * @param name 客户名称
     * @return 客户数据管理
     */
    public ChaCustomer selectChaCustomerByName(String name);

    /**
     * 查询客户数据管理列表
     * 
     * @param chaCustomer 客户数据管理
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerList(ChaCustomer chaCustomer);

    /**
     * 查询客户数据管理列表
     *
     * @param payPatternId 付款方式
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerListByPayPatternId(Long payPatternId);

    /**
     * 查询客户数据管理列表
     *
     * @param invoicePatternId 发票类型
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询客户数据管理列表
     *
     * @param currencyId 币别
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerListByCurrencyId(Long currencyId);

    /**
     * 查询客户数据管理列表
     *
     * @param shipmentTypeId 出货方式
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询客户数据管理列表
     *
     * @param salesId 销售员
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerListBySalesId(Long salesId);

    /**
     * 新增客户数据管理
     * 
     * @param chaCustomer 客户数据管理
     * @return 结果
     */
    public int insertChaCustomer(ChaCustomer chaCustomer);

    /**
     * 修改客户数据管理
     * 
     * @param chaCustomer 客户数据管理
     * @return 结果
     */
    public int updateChaCustomer(ChaCustomer chaCustomer);

    /**
     * 删除客户数据管理
     * 
     * @param id 客户数据管理主键
     * @return 结果
     */
    public int deleteChaCustomerById(Long id);

    /**
     * 批量删除客户数据管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChaCustomerByIds(Long[] ids);

    /**
     * 查询客户数据管理列表
     *
     * @param payPatternId 付款方式
     * @return 客户数据管理集合
     */
    public int deleteChaCustomerByPayPatternId(Long payPatternId);

    /**
     * 查询客户数据管理列表
     *
     * @param invoicePatternId 发票类型
     * @return 客户数据管理集合
     */
    public int deleteChaCustomerByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询客户数据管理列表
     *
     * @param currencyId 币别
     * @return 客户数据管理集合
     */
    public int deleteChaCustomerByCurrencyId(Long currencyId);

    /**
     * 查询客户数据管理列表
     *
     * @param shipmentTypeId 出货方式
     * @return 客户数据管理集合
     */
    public int deleteChaCustomerByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询客户数据管理列表
     *
     * @param salesId 销售员
     * @return 客户数据管理集合
     */
    public int deleteChaCustomerBySalesId(Long salesId);
}

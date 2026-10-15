package net.factoryopen.myfactoryai.manufacturing.cha.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerAddress;

/**
 * 客户地址Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ChaCustomerAddressMapper 
{
    /**
     * 查询客户地址
     * 
     * @param id 客户地址主键
     * @return 客户地址
     */
    public ChaCustomerAddress selectChaCustomerAddressById(Long id);

    /**
     * 查询客户地址列表
     * 
     * @param chaCustomerAddress 客户地址
     * @return 客户地址集合
     */
    public List<ChaCustomerAddress> selectChaCustomerAddressList(ChaCustomerAddress chaCustomerAddress);

    /**
     * 查询客户地址列表
     *
     * @param customerId 客户
     * @return 客户地址集合
     */
    public List<ChaCustomerAddress> selectChaCustomerAddressListByCustomerId(Long customerId);

    /**
     * 新增客户地址
     * 
     * @param chaCustomerAddress 客户地址
     * @return 结果
     */
    public int insertChaCustomerAddress(ChaCustomerAddress chaCustomerAddress);

    /**
     * 修改客户地址
     * 
     * @param chaCustomerAddress 客户地址
     * @return 结果
     */
    public int updateChaCustomerAddress(ChaCustomerAddress chaCustomerAddress);

    /**
     * 删除客户地址
     * 
     * @param id 客户地址主键
     * @return 结果
     */
    public int deleteChaCustomerAddressById(Long id);

    /**
     * 批量删除客户地址
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChaCustomerAddressByIds(Long[] ids);

    /**
     * 查询客户地址列表
     *
     * @param customerId 客户
     * @return 客户地址集合
     */
    public int deleteChaCustomerAddressByCustomerId(Long customerId);
}

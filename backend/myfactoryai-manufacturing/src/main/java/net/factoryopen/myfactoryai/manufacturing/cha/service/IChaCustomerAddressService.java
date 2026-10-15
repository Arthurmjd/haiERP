package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerAddress;

/**
 * 客户地址Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaCustomerAddressService 
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
     * 批量删除客户地址
     * 
     * @param ids 需要删除的客户地址主键集合
     * @return 结果
     */
    public int deleteChaCustomerAddressByIds(Long[] ids);

    /**
     * 删除客户地址信息
     * 
     * @param id 客户地址主键
     * @return 结果
     */
    public int deleteChaCustomerAddressById(Long id);


}

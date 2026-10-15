package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierAddress;

/**
 * 供应商地址Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISupSupplierAddressService 
{
    /**
     * 查询供应商地址
     * 
     * @param id 供应商地址主键
     * @return 供应商地址
     */
    public SupSupplierAddress selectSupSupplierAddressById(Long id);

    /**
     * 查询供应商地址列表
     * 
     * @param supSupplierAddress 供应商地址
     * @return 供应商地址集合
     */
    public List<SupSupplierAddress> selectSupSupplierAddressList(SupSupplierAddress supSupplierAddress);

    /**
     * 新增供应商地址
     * 
     * @param supSupplierAddress 供应商地址
     * @return 结果
     */
    public int insertSupSupplierAddress(SupSupplierAddress supSupplierAddress);

    /**
     * 修改供应商地址
     * 
     * @param supSupplierAddress 供应商地址
     * @return 结果
     */
    public int updateSupSupplierAddress(SupSupplierAddress supSupplierAddress);

    /**
     * 批量删除供应商地址
     * 
     * @param ids 需要删除的供应商地址主键集合
     * @return 结果
     */
    public int deleteSupSupplierAddressByIds(Long[] ids);

    /**
     * 删除供应商地址信息
     * 
     * @param id 供应商地址主键
     * @return 结果
     */
    public int deleteSupSupplierAddressById(Long id);


}

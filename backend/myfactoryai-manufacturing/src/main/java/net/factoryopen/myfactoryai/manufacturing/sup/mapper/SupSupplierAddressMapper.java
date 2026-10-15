package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierAddress;

/**
 * 供应商地址Mapper接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface SupSupplierAddressMapper 
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
     * 查询供应商地址列表
     *
     * @param supplierId 供应商
     * @return 供应商地址集合
     */
    public List<SupSupplierAddress> selectSupSupplierAddressListBySupplierId(Long supplierId);

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
     * 删除供应商地址
     * 
     * @param id 供应商地址主键
     * @return 结果
     */
    public int deleteSupSupplierAddressById(Long id);

    /**
     * 批量删除供应商地址
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupSupplierAddressByIds(Long[] ids);

    /**
     * 查询供应商地址列表
     *
     * @param supplierId 供应商
     * @return 供应商地址集合
     */
    public int deleteSupSupplierAddressBySupplierId(Long supplierId);
}

package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierAddress;

/**
 * 供应商地址Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IDelSupplierAddressService 
{
    /**
     * 查询供应商地址
     * 
     * @param id 供应商地址主键
     * @return 供应商地址
     */
    public DelSupplierAddress selectDelSupplierAddressById(Long id);

    /**
     * 查询供应商地址列表
     * 
     * @param delSupplierAddress 供应商地址
     * @return 供应商地址集合
     */
    public List<DelSupplierAddress> selectDelSupplierAddressList(DelSupplierAddress delSupplierAddress);

    /**
     * 新增供应商地址
     * 
     * @param delSupplierAddress 供应商地址
     * @return 结果
     */
    public int insertDelSupplierAddress(DelSupplierAddress delSupplierAddress);

    /**
     * 修改供应商地址
     * 
     * @param delSupplierAddress 供应商地址
     * @return 结果
     */
    public int updateDelSupplierAddress(DelSupplierAddress delSupplierAddress);

    /**
     * 批量删除供应商地址
     * 
     * @param ids 需要删除的供应商地址主键集合
     * @return 结果
     */
    public int deleteDelSupplierAddressByIds(Long[] ids);

    /**
     * 删除供应商地址信息
     * 
     * @param id 供应商地址主键
     * @return 结果
     */
    public int deleteDelSupplierAddressById(Long id);


}

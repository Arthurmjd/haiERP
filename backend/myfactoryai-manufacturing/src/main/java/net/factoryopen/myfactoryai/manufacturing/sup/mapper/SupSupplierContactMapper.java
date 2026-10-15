package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierContact;

/**
 * 供应商人员Mapper接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface SupSupplierContactMapper 
{
    /**
     * 查询供应商人员
     * 
     * @param id 供应商人员主键
     * @return 供应商人员
     */
    public SupSupplierContact selectSupSupplierContactById(Long id);

    /**
     * 查询供应商人员列表
     * 
     * @param supSupplierContact 供应商人员
     * @return 供应商人员集合
     */
    public List<SupSupplierContact> selectSupSupplierContactList(SupSupplierContact supSupplierContact);

    /**
     * 查询供应商人员列表
     *
     * @param supplierId 供应商
     * @return 供应商人员集合
     */
    public List<SupSupplierContact> selectSupSupplierContactListBySupplierId(Long supplierId);

    /**
     * 新增供应商人员
     * 
     * @param supSupplierContact 供应商人员
     * @return 结果
     */
    public int insertSupSupplierContact(SupSupplierContact supSupplierContact);

    /**
     * 修改供应商人员
     * 
     * @param supSupplierContact 供应商人员
     * @return 结果
     */
    public int updateSupSupplierContact(SupSupplierContact supSupplierContact);

    /**
     * 删除供应商人员
     * 
     * @param id 供应商人员主键
     * @return 结果
     */
    public int deleteSupSupplierContactById(Long id);

    /**
     * 批量删除供应商人员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupSupplierContactByIds(Long[] ids);

    /**
     * 查询供应商人员列表
     *
     * @param supplierId 供应商
     * @return 供应商人员集合
     */
    public int deleteSupSupplierContactBySupplierId(Long supplierId);
}

package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierContact;

/**
 * 供应商人员Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISupSupplierContactService 
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
     * 批量删除供应商人员
     * 
     * @param ids 需要删除的供应商人员主键集合
     * @return 结果
     */
    public int deleteSupSupplierContactByIds(Long[] ids);

    /**
     * 删除供应商人员信息
     * 
     * @param id 供应商人员主键
     * @return 结果
     */
    public int deleteSupSupplierContactById(Long id);


}

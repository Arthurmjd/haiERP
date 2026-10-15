package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierContact;

/**
 * 供应商人员Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IDelSupplierContactService 
{
    /**
     * 查询供应商人员
     * 
     * @param id 供应商人员主键
     * @return 供应商人员
     */
    public DelSupplierContact selectDelSupplierContactById(Long id);

    /**
     * 查询供应商人员列表
     * 
     * @param delSupplierContact 供应商人员
     * @return 供应商人员集合
     */
    public List<DelSupplierContact> selectDelSupplierContactList(DelSupplierContact delSupplierContact);

    /**
     * 新增供应商人员
     * 
     * @param delSupplierContact 供应商人员
     * @return 结果
     */
    public int insertDelSupplierContact(DelSupplierContact delSupplierContact);

    /**
     * 修改供应商人员
     * 
     * @param delSupplierContact 供应商人员
     * @return 结果
     */
    public int updateDelSupplierContact(DelSupplierContact delSupplierContact);

    /**
     * 批量删除供应商人员
     * 
     * @param ids 需要删除的供应商人员主键集合
     * @return 结果
     */
    public int deleteDelSupplierContactByIds(Long[] ids);

    /**
     * 删除供应商人员信息
     * 
     * @param id 供应商人员主键
     * @return 结果
     */
    public int deleteDelSupplierContactById(Long id);


}

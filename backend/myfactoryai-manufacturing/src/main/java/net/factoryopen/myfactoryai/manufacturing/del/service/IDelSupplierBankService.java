package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierBank;

/**
 * 供应商账号Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IDelSupplierBankService 
{
    /**
     * 查询供应商账号
     * 
     * @param id 供应商账号主键
     * @return 供应商账号
     */
    public DelSupplierBank selectDelSupplierBankById(Long id);

    /**
     * 查询供应商账号列表
     * 
     * @param delSupplierBank 供应商账号
     * @return 供应商账号集合
     */
    public List<DelSupplierBank> selectDelSupplierBankList(DelSupplierBank delSupplierBank);

    /**
     * 新增供应商账号
     * 
     * @param delSupplierBank 供应商账号
     * @return 结果
     */
    public int insertDelSupplierBank(DelSupplierBank delSupplierBank);

    /**
     * 修改供应商账号
     * 
     * @param delSupplierBank 供应商账号
     * @return 结果
     */
    public int updateDelSupplierBank(DelSupplierBank delSupplierBank);

    /**
     * 批量删除供应商账号
     * 
     * @param ids 需要删除的供应商账号主键集合
     * @return 结果
     */
    public int deleteDelSupplierBankByIds(Long[] ids);

    /**
     * 删除供应商账号信息
     * 
     * @param id 供应商账号主键
     * @return 结果
     */
    public int deleteDelSupplierBankById(Long id);


}

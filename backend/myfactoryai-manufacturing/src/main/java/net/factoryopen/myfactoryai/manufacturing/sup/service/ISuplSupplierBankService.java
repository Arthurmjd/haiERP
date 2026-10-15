package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SuplSupplierBank;

/**
 * 供应商账号Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISuplSupplierBankService 
{
    /**
     * 查询供应商账号
     * 
     * @param id 供应商账号主键
     * @return 供应商账号
     */
    public SuplSupplierBank selectSuplSupplierBankById(Long id);

    /**
     * 查询供应商账号列表
     * 
     * @param suplSupplierBank 供应商账号
     * @return 供应商账号集合
     */
    public List<SuplSupplierBank> selectSuplSupplierBankList(SuplSupplierBank suplSupplierBank);

    /**
     * 新增供应商账号
     * 
     * @param suplSupplierBank 供应商账号
     * @return 结果
     */
    public int insertSuplSupplierBank(SuplSupplierBank suplSupplierBank);

    /**
     * 修改供应商账号
     * 
     * @param suplSupplierBank 供应商账号
     * @return 结果
     */
    public int updateSuplSupplierBank(SuplSupplierBank suplSupplierBank);

    /**
     * 批量删除供应商账号
     * 
     * @param ids 需要删除的供应商账号主键集合
     * @return 结果
     */
    public int deleteSuplSupplierBankByIds(Long[] ids);

    /**
     * 删除供应商账号信息
     * 
     * @param id 供应商账号主键
     * @return 结果
     */
    public int deleteSuplSupplierBankById(Long id);


}

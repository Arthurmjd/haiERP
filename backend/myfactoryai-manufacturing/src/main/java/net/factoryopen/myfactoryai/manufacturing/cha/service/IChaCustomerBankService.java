package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerBank;

/**
 * 客户账号Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaCustomerBankService 
{
    /**
     * 查询客户账号
     * 
     * @param id 客户账号主键
     * @return 客户账号
     */
    public ChaCustomerBank selectChaCustomerBankById(Long id);

    /**
     * 查询客户账号列表
     * 
     * @param chaCustomerBank 客户账号
     * @return 客户账号集合
     */
    public List<ChaCustomerBank> selectChaCustomerBankList(ChaCustomerBank chaCustomerBank);

    /**
     * 新增客户账号
     * 
     * @param chaCustomerBank 客户账号
     * @return 结果
     */
    public int insertChaCustomerBank(ChaCustomerBank chaCustomerBank);

    /**
     * 修改客户账号
     * 
     * @param chaCustomerBank 客户账号
     * @return 结果
     */
    public int updateChaCustomerBank(ChaCustomerBank chaCustomerBank);

    /**
     * 批量删除客户账号
     * 
     * @param ids 需要删除的客户账号主键集合
     * @return 结果
     */
    public int deleteChaCustomerBankByIds(Long[] ids);

    /**
     * 删除客户账号信息
     * 
     * @param id 客户账号主键
     * @return 结果
     */
    public int deleteChaCustomerBankById(Long id);


}

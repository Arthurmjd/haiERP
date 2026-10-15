package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerContact;

/**
 * 客户人员Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaCustomerContactService 
{
    /**
     * 查询客户人员
     * 
     * @param id 客户人员主键
     * @return 客户人员
     */
    public ChaCustomerContact selectChaCustomerContactById(Long id);

    /**
     * 查询客户人员列表
     * 
     * @param chaCustomerContact 客户人员
     * @return 客户人员集合
     */
    public List<ChaCustomerContact> selectChaCustomerContactList(ChaCustomerContact chaCustomerContact);

    /**
     * 新增客户人员
     * 
     * @param chaCustomerContact 客户人员
     * @return 结果
     */
    public int insertChaCustomerContact(ChaCustomerContact chaCustomerContact);

    /**
     * 修改客户人员
     * 
     * @param chaCustomerContact 客户人员
     * @return 结果
     */
    public int updateChaCustomerContact(ChaCustomerContact chaCustomerContact);

    /**
     * 批量删除客户人员
     * 
     * @param ids 需要删除的客户人员主键集合
     * @return 结果
     */
    public int deleteChaCustomerContactByIds(Long[] ids);

    /**
     * 删除客户人员信息
     * 
     * @param id 客户人员主键
     * @return 结果
     */
    public int deleteChaCustomerContactById(Long id);


}

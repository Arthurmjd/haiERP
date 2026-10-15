package net.factoryopen.myfactoryai.manufacturing.cha.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerContact;

/**
 * 客户人员Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ChaCustomerContactMapper 
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
     * 查询客户人员列表
     *
     * @param customerId 客户
     * @return 客户人员集合
     */
    public List<ChaCustomerContact> selectChaCustomerContactListByCustomerId(Long customerId);

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
     * 删除客户人员
     * 
     * @param id 客户人员主键
     * @return 结果
     */
    public int deleteChaCustomerContactById(Long id);

    /**
     * 批量删除客户人员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChaCustomerContactByIds(Long[] ids);

    /**
     * 查询客户人员列表
     *
     * @param customerId 客户
     * @return 客户人员集合
     */
    public int deleteChaCustomerContactByCustomerId(Long customerId);
}

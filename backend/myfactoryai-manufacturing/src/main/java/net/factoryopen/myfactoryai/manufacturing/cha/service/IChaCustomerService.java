package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;

/**
 * 客户数据管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaCustomerService 
{
    /**
     * 查询客户数据管理
     * 
     * @param id 客户数据管理主键
     * @return 客户数据管理
     */
    public ChaCustomer selectChaCustomerById(Long id);

    /**
     * 查询客户数据管理
     *
     * @param name 客户名称
     * @return 客户数据管理
     */
    public ChaCustomer selectChaCustomerByName(String name);

    /**
     * 查询客户数据管理列表
     * 
     * @param chaCustomer 客户数据管理
     * @return 客户数据管理集合
     */
    public List<ChaCustomer> selectChaCustomerList(ChaCustomer chaCustomer);

    /**
     * 新增客户数据管理
     * 
     * @param chaCustomer 客户数据管理
     * @return 结果
     */
    public int insertChaCustomer(ChaCustomer chaCustomer);

    /**
     * 修改客户数据管理
     * 
     * @param chaCustomer 客户数据管理
     * @return 结果
     */
    public int updateChaCustomer(ChaCustomer chaCustomer);

    /**
     * 批量删除客户数据管理
     * 
     * @param ids 需要删除的客户数据管理主键集合
     * @return 结果
     */
    public int deleteChaCustomerByIds(Long[] ids);

    /**
     * 删除客户数据管理信息
     * 
     * @param id 客户数据管理主键
     * @return 结果
     */
    public int deleteChaCustomerById(Long id);


}

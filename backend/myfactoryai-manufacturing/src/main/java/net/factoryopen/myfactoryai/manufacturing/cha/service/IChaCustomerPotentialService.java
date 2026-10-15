package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerPotential;

/**
 * 潜客备案Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaCustomerPotentialService 
{
    /**
     * 查询潜客备案
     * 
     * @param id 潜客备案主键
     * @return 潜客备案
     */
    public ChaCustomerPotential selectChaCustomerPotentialById(Long id);

    /**
     * 查询潜客备案列表
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialList(ChaCustomerPotential chaCustomerPotential);

    /**
     * 新增潜客备案
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 结果
     */
    public int insertChaCustomerPotential(ChaCustomerPotential chaCustomerPotential);

    /**
     * 修改潜客备案
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 结果
     */
    public int updateChaCustomerPotential(ChaCustomerPotential chaCustomerPotential);

    /**
     * 批量删除潜客备案
     * 
     * @param ids 需要删除的潜客备案主键集合
     * @return 结果
     */
    public int deleteChaCustomerPotentialByIds(Long[] ids);

    /**
     * 删除潜客备案信息
     * 
     * @param id 潜客备案主键
     * @return 结果
     */
    public int deleteChaCustomerPotentialById(Long id);


}

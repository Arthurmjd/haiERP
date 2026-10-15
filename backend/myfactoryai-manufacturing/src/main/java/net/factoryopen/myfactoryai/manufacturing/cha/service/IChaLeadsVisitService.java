package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeadsVisit;

/**
 * 客访登记Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaLeadsVisitService 
{
    /**
     * 查询客访登记
     * 
     * @param id 客访登记主键
     * @return 客访登记
     */
    public ChaLeadsVisit selectChaLeadsVisitById(Long id);

    /**
     * 查询客访登记列表
     * 
     * @param chaLeadsVisit 客访登记
     * @return 客访登记集合
     */
    public List<ChaLeadsVisit> selectChaLeadsVisitList(ChaLeadsVisit chaLeadsVisit);

    /**
     * 新增客访登记
     * 
     * @param chaLeadsVisit 客访登记
     * @return 结果
     */
    public int insertChaLeadsVisit(ChaLeadsVisit chaLeadsVisit);

    /**
     * 修改客访登记
     * 
     * @param chaLeadsVisit 客访登记
     * @return 结果
     */
    public int updateChaLeadsVisit(ChaLeadsVisit chaLeadsVisit);

    /**
     * 批量删除客访登记
     * 
     * @param ids 需要删除的客访登记主键集合
     * @return 结果
     */
    public int deleteChaLeadsVisitByIds(Long[] ids);

    /**
     * 删除客访登记信息
     * 
     * @param id 客访登记主键
     * @return 结果
     */
    public int deleteChaLeadsVisitById(Long id);


}

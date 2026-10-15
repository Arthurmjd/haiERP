package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeads;

/**
 * 线索备案Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaLeadsService 
{
    /**
     * 查询线索备案
     * 
     * @param id 线索备案主键
     * @return 线索备案
     */
    public ChaLeads selectChaLeadsById(Long id);

    /**
     * 查询线索备案列表
     * 
     * @param chaLeads 线索备案
     * @return 线索备案集合
     */
    public List<ChaLeads> selectChaLeadsList(ChaLeads chaLeads);

    /**
     * 新增线索备案
     * 
     * @param chaLeads 线索备案
     * @return 结果
     */
    public int insertChaLeads(ChaLeads chaLeads);

    /**
     * 修改线索备案
     * 
     * @param chaLeads 线索备案
     * @return 结果
     */
    public int updateChaLeads(ChaLeads chaLeads);

    /**
     * 批量删除线索备案
     * 
     * @param ids 需要删除的线索备案主键集合
     * @return 结果
     */
    public int deleteChaLeadsByIds(Long[] ids);

    /**
     * 删除线索备案信息
     * 
     * @param id 线索备案主键
     * @return 结果
     */
    public int deleteChaLeadsById(Long id);


}

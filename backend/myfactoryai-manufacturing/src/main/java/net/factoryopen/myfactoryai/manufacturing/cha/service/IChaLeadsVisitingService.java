package net.factoryopen.myfactoryai.manufacturing.cha.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeadsVisiting;

/**
 * 线索拜访Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IChaLeadsVisitingService 
{
    /**
     * 查询线索拜访
     * 
     * @param id 线索拜访主键
     * @return 线索拜访
     */
    public ChaLeadsVisiting selectChaLeadsVisitingById(Long id);

    /**
     * 查询线索拜访列表
     * 
     * @param chaLeadsVisiting 线索拜访
     * @return 线索拜访集合
     */
    public List<ChaLeadsVisiting> selectChaLeadsVisitingList(ChaLeadsVisiting chaLeadsVisiting);

    /**
     * 新增线索拜访
     * 
     * @param chaLeadsVisiting 线索拜访
     * @return 结果
     */
    public int insertChaLeadsVisiting(ChaLeadsVisiting chaLeadsVisiting);

    /**
     * 修改线索拜访
     * 
     * @param chaLeadsVisiting 线索拜访
     * @return 结果
     */
    public int updateChaLeadsVisiting(ChaLeadsVisiting chaLeadsVisiting);

    /**
     * 批量删除线索拜访
     * 
     * @param ids 需要删除的线索拜访主键集合
     * @return 结果
     */
    public int deleteChaLeadsVisitingByIds(Long[] ids);

    /**
     * 删除线索拜访信息
     * 
     * @param id 线索拜访主键
     * @return 结果
     */
    public int deleteChaLeadsVisitingById(Long id);


}

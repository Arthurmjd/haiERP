package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventorySummary;

/**
 * 库存总览表Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
public interface ISncInventorySummaryService 
{
    /**
     * 查询库存总览表
     * 
     * @param id 库存总览表主键
     * @return 库存总览表
     */
    public SncInventorySummary selectSncInventorySummaryById(Long id);

    /**
     * 查询库存总览表列表
     * 
     * @param sncInventorySummary 库存总览表
     * @return 库存总览表集合
     */
    public List<SncInventorySummary> selectSncInventorySummaryList(SncInventorySummary sncInventorySummary);

    /**
     * 新增库存总览表
     * 
     * @param sncInventorySummary 库存总览表
     * @return 结果
     */
    public int insertSncInventorySummary(SncInventorySummary sncInventorySummary);

    /**
     * 修改库存总览表
     * 
     * @param sncInventorySummary 库存总览表
     * @return 结果
     */
    public int updateSncInventorySummary(SncInventorySummary sncInventorySummary);

    /**
     * 批量删除库存总览表
     * 
     * @param ids 需要删除的库存总览表主键集合
     * @return 结果
     */
    public int deleteSncInventorySummaryByIds(Long[] ids);

    /**
     * 删除库存总览表信息
     * 
     * @param id 库存总览表主键
     * @return 结果
     */
    public int deleteSncInventorySummaryById(Long id);


}

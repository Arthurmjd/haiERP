package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventorySummary;

/**
 * 库存总览表Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
public interface SncInventorySummaryMapper 
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
     * 查询库存总览表列表
     *
     * @param unitId 单位
     * @return 库存总览表集合
     */
    public List<SncInventorySummary> selectSncInventorySummaryListByUnitId(Long unitId);

    /**
     * 查询库存总览表列表
     *
     * @param categoryId 品类
     * @return 库存总览表集合
     */
    public List<SncInventorySummary> selectSncInventorySummaryListByCategoryId(Long categoryId);

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
     * 删除库存总览表
     * 
     * @param id 库存总览表主键
     * @return 结果
     */
    public int deleteSncInventorySummaryById(Long id);

    /**
     * 批量删除库存总览表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncInventorySummaryByIds(Long[] ids);

    /**
     * 查询库存总览表列表
     *
     * @param unitId 单位
     * @return 库存总览表集合
     */
    public int deleteSncInventorySummaryByUnitId(Long unitId);

    /**
     * 查询库存总览表列表
     *
     * @param categoryId 品类
     * @return 库存总览表集合
     */
    public int deleteSncInventorySummaryByCategoryId(Long categoryId);
}

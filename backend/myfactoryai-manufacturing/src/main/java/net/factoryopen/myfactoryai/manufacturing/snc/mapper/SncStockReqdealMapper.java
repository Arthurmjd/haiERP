package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqdeal;

/**
 * 请出入库处理Mapper接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface SncStockReqdealMapper 
{
    /**
     * 查询请出入库处理
     * 
     * @param id 请出入库处理主键
     * @return 请出入库处理
     */
    public SncStockReqdeal selectSncStockReqdealById(Long id);

    /**
     * 查询请出入库处理列表
     * 
     * @param sncStockReqdeal 请出入库处理
     * @return 请出入库处理集合
     */
    public List<SncStockReqdeal> selectSncStockReqdealList(SncStockReqdeal sncStockReqdeal);

    /**
     * 查询请出入库处理列表
     *
     * @param requestorId 请求人ID
     * @return 请出入库处理集合
     */
    public List<SncStockReqdeal> selectSncStockReqdealListByRequestorId(Long requestorId);

    /**
     * 查询请出入库处理列表
     *
     * @param sourceLineId 来源产线ID
     * @return 请出入库处理集合
     */
    public List<SncStockReqdeal> selectSncStockReqdealListBySourceLineId(Long sourceLineId);

    /**
     * 查询请出入库处理列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 请出入库处理集合
     */
    public List<SncStockReqdeal> selectSncStockReqdealListBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询请出入库处理列表
     *
     * @param sourceCustomerId 来源客户ID
     * @return 请出入库处理集合
     */
    public List<SncStockReqdeal> selectSncStockReqdealListBySourceCustomerId(Long sourceCustomerId);

    /**
     * 查询请出入库处理列表
     *
     * @param projectId 项目ID
     * @return 请出入库处理集合
     */
    public List<SncStockReqdeal> selectSncStockReqdealListByProjectId(Long projectId);

    /**
     * 新增请出入库处理
     * 
     * @param sncStockReqdeal 请出入库处理
     * @return 结果
     */
    public int insertSncStockReqdeal(SncStockReqdeal sncStockReqdeal);

    /**
     * 修改请出入库处理
     * 
     * @param sncStockReqdeal 请出入库处理
     * @return 结果
     */
    public int updateSncStockReqdeal(SncStockReqdeal sncStockReqdeal);

    /**
     * 删除请出入库处理
     * 
     * @param id 请出入库处理主键
     * @return 结果
     */
    public int deleteSncStockReqdealById(Long id);

    /**
     * 批量删除请出入库处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncStockReqdealByIds(Long[] ids);

    /**
     * 查询请出入库处理列表
     *
     * @param requestorId 请求人ID
     * @return 请出入库处理集合
     */
    public int deleteSncStockReqdealByRequestorId(Long requestorId);

    /**
     * 查询请出入库处理列表
     *
     * @param sourceLineId 来源产线ID
     * @return 请出入库处理集合
     */
    public int deleteSncStockReqdealBySourceLineId(Long sourceLineId);

    /**
     * 查询请出入库处理列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 请出入库处理集合
     */
    public int deleteSncStockReqdealBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询请出入库处理列表
     *
     * @param sourceCustomerId 来源客户ID
     * @return 请出入库处理集合
     */
    public int deleteSncStockReqdealBySourceCustomerId(Long sourceCustomerId);

    /**
     * 查询请出入库处理列表
     *
     * @param projectId 项目ID
     * @return 请出入库处理集合
     */
    public int deleteSncStockReqdealByProjectId(Long projectId);
}

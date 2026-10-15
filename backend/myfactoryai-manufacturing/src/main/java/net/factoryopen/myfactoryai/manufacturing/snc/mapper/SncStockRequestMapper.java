package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;

/**
 * 出入库请求Mapper接口
 * 
 * @author admin
 * @date 2025-05-17
 */
public interface SncStockRequestMapper 
{
    /**
     * 查询出入库请求
     * 
     * @param id 出入库请求主键
     * @return 出入库请求
     */
    public SncStockRequest selectSncStockRequestById(Long id);

    /**
     * 查询出入库请求列表
     * 
     * @param sncStockRequest 出入库请求
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestList(SncStockRequest sncStockRequest);

    /**
     * 查询出入库请求列表
     *
     * @param sncStockRequest 出入库请求
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestPMaterialList(SncStockRequest sncStockRequest);

    /**
     * 查询出入库请求列表
     *
     * @param sncStockRequest 出入库请求
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestFinishedList(SncStockRequest sncStockRequest);

    /**
     * 查询出入库请求列表
     *
     * @param requestorId 请求人ID
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestListByRequestorId(Long requestorId);

    /**
     * 查询出入库请求列表
     *
     * @param sourceLineId 来源产线ID
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestListBySourceLineId(Long sourceLineId);

    /**
     * 查询出入库请求列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestListBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询出入库请求列表
     *
     * @param sourceCustomerId 来源客户ID
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestListBySourceCustomerId(Long sourceCustomerId);

    /**
     * 查询出入库请求列表
     *
     * @param projectId 项目ID
     * @return 出入库请求集合
     */
    public List<SncStockRequest> selectSncStockRequestListByProjectId(Long projectId);

    /**
     * 新增出入库请求
     * 
     * @param sncStockRequest 出入库请求
     * @return 结果
     */
    public int insertSncStockRequest(SncStockRequest sncStockRequest);

    /**
     * 修改出入库请求
     * 
     * @param sncStockRequest 出入库请求
     * @return 结果
     */
    public int updateSncStockRequest(SncStockRequest sncStockRequest);

    /**
     * 删除出入库请求
     * 
     * @param id 出入库请求主键
     * @return 结果
     */
    public int deleteSncStockRequestById(Long id);

    /**
     * 批量删除出入库请求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncStockRequestByIds(Long[] ids);

    /**
     * 查询出入库请求列表
     *
     * @param requestorId 请求人ID
     * @return 出入库请求集合
     */
    public int deleteSncStockRequestByRequestorId(Long requestorId);

    /**
     * 查询出入库请求列表
     *
     * @param sourceLineId 来源产线ID
     * @return 出入库请求集合
     */
    public int deleteSncStockRequestBySourceLineId(Long sourceLineId);

    /**
     * 查询出入库请求列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 出入库请求集合
     */
    public int deleteSncStockRequestBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询出入库请求列表
     *
     * @param sourceCustomerId 来源客户ID
     * @return 出入库请求集合
     */
    public int deleteSncStockRequestBySourceCustomerId(Long sourceCustomerId);

    /**
     * 查询出入库请求列表
     *
     * @param projectId 项目ID
     * @return 出入库请求集合
     */
    public int deleteSncStockRequestByProjectId(Long projectId);
}

package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqhistory;

/**
 * 出入库历史Mapper接口
 * 
 * @author liqiang
 * @date 2025-05-17
 */
public interface SncStockReqhistoryMapper 
{
    /**
     * 查询出入库历史
     * 
     * @param id 出入库历史主键
     * @return 出入库历史
     */
    public SncStockReqhistory selectSncStockReqhistoryById(Long id);

    /**
     * 查询出入库历史列表
     * 
     * @param sncStockReqhistory 出入库历史
     * @return 出入库历史集合
     */
    public List<SncStockReqhistory> selectSncStockReqhistoryList(SncStockReqhistory sncStockReqhistory);

    /**
     * 查询出入库历史列表
     *
     * @param requestorId 请求人ID
     * @return 出入库历史集合
     */
    public List<SncStockReqhistory> selectSncStockReqhistoryListByRequestorId(Long requestorId);

    /**
     * 查询出入库历史列表
     *
     * @param sourceLineId 来源产线ID
     * @return 出入库历史集合
     */
    public List<SncStockReqhistory> selectSncStockReqhistoryListBySourceLineId(Long sourceLineId);

    /**
     * 查询出入库历史列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 出入库历史集合
     */
    public List<SncStockReqhistory> selectSncStockReqhistoryListBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询出入库历史列表
     *
     * @param sourceCustomerId 来源客户ID
     * @return 出入库历史集合
     */
    public List<SncStockReqhistory> selectSncStockReqhistoryListBySourceCustomerId(Long sourceCustomerId);

    /**
     * 查询出入库历史列表
     *
     * @param projectId 项目ID
     * @return 出入库历史集合
     */
    public List<SncStockReqhistory> selectSncStockReqhistoryListByProjectId(Long projectId);

    /**
     * 新增出入库历史
     * 
     * @param sncStockReqhistory 出入库历史
     * @return 结果
     */
    public int insertSncStockReqhistory(SncStockReqhistory sncStockReqhistory);

    /**
     * 修改出入库历史
     * 
     * @param sncStockReqhistory 出入库历史
     * @return 结果
     */
    public int updateSncStockReqhistory(SncStockReqhistory sncStockReqhistory);

    /**
     * 删除出入库历史
     * 
     * @param id 出入库历史主键
     * @return 结果
     */
    public int deleteSncStockReqhistoryById(Long id);

    /**
     * 批量删除出入库历史
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncStockReqhistoryByIds(Long[] ids);

    /**
     * 查询出入库历史列表
     *
     * @param requestorId 请求人ID
     * @return 出入库历史集合
     */
    public int deleteSncStockReqhistoryByRequestorId(Long requestorId);

    /**
     * 查询出入库历史列表
     *
     * @param sourceLineId 来源产线ID
     * @return 出入库历史集合
     */
    public int deleteSncStockReqhistoryBySourceLineId(Long sourceLineId);

    /**
     * 查询出入库历史列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 出入库历史集合
     */
    public int deleteSncStockReqhistoryBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询出入库历史列表
     *
     * @param sourceCustomerId 来源客户ID
     * @return 出入库历史集合
     */
    public int deleteSncStockReqhistoryBySourceCustomerId(Long sourceCustomerId);

    /**
     * 查询出入库历史列表
     *
     * @param projectId 项目ID
     * @return 出入库历史集合
     */
    public int deleteSncStockReqhistoryByProjectId(Long projectId);
}

package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;

/**
 * 出入库请求Service接口
 * 
 * @author admin
 * @date 2025-05-17
 */
public interface ISncStockRequestService 
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
     * 批量删除出入库请求
     * 
     * @param ids 需要删除的出入库请求主键集合
     * @return 结果
     */
    public int deleteSncStockRequestByIds(Long[] ids);

    /**
     * 删除出入库请求信息
     * 
     * @param id 出入库请求主键
     * @return 结果
     */
    public int deleteSncStockRequestById(Long id);

    /**
     * 启用
     *
     * @param sncStockRequest 启用
     * @return 结果
     */
    public int enable(SncStockRequest sncStockRequest);

    /**
     * 请库
     *
     * @param ids id集合
     * @return 结果
     */
    public int requestStock(Long[] ids) throws Exception;


}

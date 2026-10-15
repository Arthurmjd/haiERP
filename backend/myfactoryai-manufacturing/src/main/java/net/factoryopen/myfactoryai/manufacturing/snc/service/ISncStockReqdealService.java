package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqdeal;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;

/**
 * 请出入库处理Service接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface ISncStockReqdealService 
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
     * 批量删除请出入库处理
     * 
     * @param ids 需要删除的请出入库处理主键集合
     * @return 结果
     */
    public int deleteSncStockReqdealByIds(Long[] ids);

    /**
     * 删除请出入库处理信息
     * 
     * @param id 请出入库处理主键
     * @return 结果
     */
    public int deleteSncStockReqdealById(Long id);

    /**
     * 收货
     *
     * @param request 收货请求
     * @param reqItems 收货请求明细
     * @return 结果
     */
    public int receiveMaterial(SncStockRequest request, List<SncStockRequestItem> reqItems) throws Exception;

    /**
     * 请检
     *
     * @param request 存货交易请求
     * @return 结果
     */
    public int requestInspection(SncStockRequest request) throws Exception;

    /**
     * 入库
     *
     * @param id 入库请求头
     * @param reqItems 入库请求明细
     * @return 结果
     */
    public int stockIn(Long id, List<SncStockRequestItem> reqItems) throws Exception;

    /**
     * 出库
     *
     * @param id 出库请求头
     * @param reqItems 出库请求明细
     * @return 结果
     */
    public int stockout(Long id, List<SncStockRequestItem> reqItems) throws Exception;

    /**
     * 好物好库推荐
     *
     * @param reqItems 入库请求明细
     * @return 结果
     */
    public List<SncStockRequestItem> gmgwSuggest(List<SncStockRequestItem> reqItems);

    /**
     * 先进先出推荐
     *
     * @param reqItems 出库请求明细
     * @return 结果
     */
    public List<SncStockRequestItem> fifoSuggest(List<SncStockRequestItem> reqItems);

    /**
     * 转正料号
     *
     * @param requestItem 请求明细项
     * @return 结果
     */
    public int normalizeCode(SncStockRequestItem requestItem) throws Exception;

    /**
     * 生成料号
     *
     * @param requestItem 请求明细项
     * @return 结果
     */
    public int generateMaterialCode(SncStockRequestItem requestItem) throws Exception;

}

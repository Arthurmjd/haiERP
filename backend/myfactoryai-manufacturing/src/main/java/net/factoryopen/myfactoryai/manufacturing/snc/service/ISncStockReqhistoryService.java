package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqhistory;

/**
 * 出入库历史Service接口
 * 
 * @author liqiang
 * @date 2025-05-17
 */
public interface ISncStockReqhistoryService 
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
     * 批量删除出入库历史
     * 
     * @param ids 需要删除的出入库历史主键集合
     * @return 结果
     */
    public int deleteSncStockReqhistoryByIds(Long[] ids);

    /**
     * 删除出入库历史信息
     * 
     * @param id 出入库历史主键
     * @return 结果
     */
    public int deleteSncStockReqhistoryById(Long id);

    /**
     * 打印
     *
     * @param sncStockReqhistory 打印
     * @return 结果
     */
    public int printRequest(SncStockReqhistory sncStockReqhistory) throws Exception;


}

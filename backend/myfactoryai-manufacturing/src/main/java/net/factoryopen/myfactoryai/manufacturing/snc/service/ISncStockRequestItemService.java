package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;

/**
 * 出入库请求明细Service接口
 * 
 * @author admin
 * @date 2025-05-17
 */
public interface ISncStockRequestItemService 
{
    /**
     * 查询出入库请求明细
     * 
     * @param id 出入库请求明细主键
     * @return 出入库请求明细
     */
    public SncStockRequestItem selectSncStockRequestItemById(Long id);

    /**
     * 查询出入库请求明细列表
     * 
     * @param sncStockRequestItem 出入库请求明细
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemList(SncStockRequestItem sncStockRequestItem);

    /**
     * 新增出入库请求明细
     * 
     * @param sncStockRequestItem 出入库请求明细
     * @return 结果
     */
    public int insertSncStockRequestItem(SncStockRequestItem sncStockRequestItem);

    /**
     * 修改出入库请求明细
     * 
     * @param sncStockRequestItem 出入库请求明细
     * @return 结果
     */
    public int updateSncStockRequestItem(SncStockRequestItem sncStockRequestItem);

    /**
     * 批量删除出入库请求明细
     * 
     * @param ids 需要删除的出入库请求明细主键集合
     * @return 结果
     */
    public int deleteSncStockRequestItemByIds(Long[] ids);

    /**
     * 删除出入库请求明细信息
     * 
     * @param id 出入库请求明细主键
     * @return 结果
     */
    public int deleteSncStockRequestItemById(Long id);


}

package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;

/**
 * 出入库请求明细Mapper接口
 * 
 * @author admin
 * @date 2025-05-17
 */
public interface SncStockRequestItemMapper 
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
     * 查询出入库请求明细列表
     *
     * @param requestId 请求ID
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemListByRequestId(Long requestId);

    /**
     * 查询出入库请求明细列表
     *
     * @param materialId 物料ID
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemListByMaterialId(Long materialId);

    /**
     * 查询出入库请求明细列表
     *
     * @param categoryId 品类ID
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemListByCategoryId(Long categoryId);

    /**
     * 查询出入库请求明细列表
     *
     * @param houseId 现仓库ID
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemListByHouseId(Long houseId);

    /**
     * 查询出入库请求明细列表
     *
     * @param positionId 现储位ID
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemListByPositionId(Long positionId);

    /**
     * 查询出入库请求明细列表
     *
     * @param ilId 现库批ID
     * @return 出入库请求明细集合
     */
    public List<SncStockRequestItem> selectSncStockRequestItemListByIlId(Long ilId);

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
     * 删除出入库请求明细
     * 
     * @param id 出入库请求明细主键
     * @return 结果
     */
    public int deleteSncStockRequestItemById(Long id);

    /**
     * 批量删除出入库请求明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncStockRequestItemByIds(Long[] ids);

    /**
     * 查询出入库请求明细列表
     *
     * @param requestId 请求ID
     * @return 出入库请求明细集合
     */
    public int deleteSncStockRequestItemByRequestId(Long requestId);

    /**
     * 查询出入库请求明细列表
     *
     * @param materialId 物料ID
     * @return 出入库请求明细集合
     */
    public int deleteSncStockRequestItemByMaterialId(Long materialId);

    /**
     * 查询出入库请求明细列表
     *
     * @param categoryId 品类ID
     * @return 出入库请求明细集合
     */
    public int deleteSncStockRequestItemByCategoryId(Long categoryId);

    /**
     * 查询出入库请求明细列表
     *
     * @param houseId 现仓库ID
     * @return 出入库请求明细集合
     */
    public int deleteSncStockRequestItemByHouseId(Long houseId);

    /**
     * 查询出入库请求明细列表
     *
     * @param positionId 现储位ID
     * @return 出入库请求明细集合
     */
    public int deleteSncStockRequestItemByPositionId(Long positionId);

    /**
     * 查询出入库请求明细列表
     *
     * @param ilId 现库批ID
     * @return 出入库请求明细集合
     */
    public int deleteSncStockRequestItemByIlId(Long ilId);
}

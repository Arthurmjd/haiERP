package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;

/**
 * 发货单明细Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface DelGdItemMapper 
{
    /**
     * 查询发货单明细
     * 
     * @param id 发货单明细主键
     * @return 发货单明细
     */
    public DelGdItem selectDelGdItemById(Long id);

    /**
     * 查询发货单明细列表
     * 
     * @param delGdItem 发货单明细
     * @return 发货单明细集合
     */
    public List<DelGdItem> selectDelGdItemList(DelGdItem delGdItem);

    /**
     * 查询发货单明细列表
     *
     * @param gdId 发货单
     * @return 发货单明细集合
     */
    public List<DelGdItem> selectDelGdItemListByGdId(Long gdId);

    /**
     * 查询发货单明细列表
     *
     * @param soId 销售订单
     * @return 发货单明细集合
     */
    public List<DelGdItem> selectDelGdItemListBySoId(Long soId);

    /**
     * 查询发货单明细列表
     *
     * @param soItemId 订单项
     * @return 发货单明细集合
     */
    public List<DelGdItem> selectDelGdItemListBySoItemId(Long soItemId);

    /**
     * 查询发货单明细列表
     *
     * @param houseId 仓库
     * @return 发货单明细集合
     */
    public List<DelGdItem> selectDelGdItemListByHouseId(Long houseId);

    /**
     * 查询发货单明细列表
     *
     * @param positionId 储位
     * @return 发货单明细集合
     */
    public List<DelGdItem> selectDelGdItemListByPositionId(Long positionId);

    /**
     * 新增发货单明细
     * 
     * @param delGdItem 发货单明细
     * @return 结果
     */
    public int insertDelGdItem(DelGdItem delGdItem);

    /**
     * 修改发货单明细
     * 
     * @param delGdItem 发货单明细
     * @return 结果
     */
    public int updateDelGdItem(DelGdItem delGdItem);

    /**
     * 删除发货单明细
     * 
     * @param id 发货单明细主键
     * @return 结果
     */
    public int deleteDelGdItemById(Long id);

    /**
     * 批量删除发货单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelGdItemByIds(Long[] ids);

    /**
     * 查询发货单明细列表
     *
     * @param gdId 发货单
     * @return 发货单明细集合
     */
    public int deleteDelGdItemByGdId(Long gdId);

    /**
     * 查询发货单明细列表
     *
     * @param soId 销售订单
     * @return 发货单明细集合
     */
    public int deleteDelGdItemBySoId(Long soId);

    /**
     * 查询发货单明细列表
     *
     * @param soItemId 订单项
     * @return 发货单明细集合
     */
    public int deleteDelGdItemBySoItemId(Long soItemId);

    /**
     * 查询发货单明细列表
     *
     * @param houseId 仓库
     * @return 发货单明细集合
     */
    public int deleteDelGdItemByHouseId(Long houseId);

    /**
     * 查询发货单明细列表
     *
     * @param positionId 储位
     * @return 发货单明细集合
     */
    public int deleteDelGdItemByPositionId(Long positionId);
}

package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGrItem;

/**
 * 收货单明细Mapper接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface SncGrItemMapper 
{
    /**
     * 查询收货单明细
     * 
     * @param id 收货单明细主键
     * @return 收货单明细
     */
    public SncGrItem selectSncGrItemById(Long id);

    /**
     * 查询收货单明细列表
     * 
     * @param sncGrItem 收货单明细
     * @return 收货单明细集合
     */
    public List<SncGrItem> selectSncGrItemList(SncGrItem sncGrItem);

    /**
     * 查询收货单明细列表
     *
     * @param grId 收货单ID
     * @return 收货单明细集合
     */
    public List<SncGrItem> selectSncGrItemListByGrId(Long grId);

    /**
     * 查询收货单明细列表
     *
     * @param poId 采购单ID
     * @return 收货单明细集合
     */
    public List<SncGrItem> selectSncGrItemListByPoId(Long poId);

    /**
     * 查询收货单明细列表
     *
     * @param poItemId 采购单明细ID
     * @return 收货单明细集合
     */
    public List<SncGrItem> selectSncGrItemListByPoItemId(Long poItemId);

    /**
     * 新增收货单明细
     * 
     * @param sncGrItem 收货单明细
     * @return 结果
     */
    public int insertSncGrItem(SncGrItem sncGrItem);

    /**
     * 修改收货单明细
     * 
     * @param sncGrItem 收货单明细
     * @return 结果
     */
    public int updateSncGrItem(SncGrItem sncGrItem);

    /**
     * 删除收货单明细
     * 
     * @param id 收货单明细主键
     * @return 结果
     */
    public int deleteSncGrItemById(Long id);

    /**
     * 批量删除收货单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncGrItemByIds(Long[] ids);

    /**
     * 查询收货单明细列表
     *
     * @param grId 收货单ID
     * @return 收货单明细集合
     */
    public int deleteSncGrItemByGrId(Long grId);

    /**
     * 查询收货单明细列表
     *
     * @param poId 采购单ID
     * @return 收货单明细集合
     */
    public int deleteSncGrItemByPoId(Long poId);

    /**
     * 查询收货单明细列表
     *
     * @param poItemId 采购单明细ID
     * @return 收货单明细集合
     */
    public int deleteSncGrItemByPoItemId(Long poItemId);
}

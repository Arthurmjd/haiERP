package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGrItem;

/**
 * 收货单明细Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISncGrItemService 
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
     * 批量删除收货单明细
     * 
     * @param ids 需要删除的收货单明细主键集合
     * @return 结果
     */
    public int deleteSncGrItemByIds(Long[] ids);

    /**
     * 删除收货单明细信息
     * 
     * @param id 收货单明细主键
     * @return 结果
     */
    public int deleteSncGrItemById(Long id);


}

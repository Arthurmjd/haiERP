package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;

/**
 * 发货单明细Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelGdItemService 
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
     * 批量删除发货单明细
     * 
     * @param ids 需要删除的发货单明细主键集合
     * @return 结果
     */
    public int deleteDelGdItemByIds(Long[] ids);

    /**
     * 删除发货单明细信息
     * 
     * @param id 发货单明细主键
     * @return 结果
     */
    public int deleteDelGdItemById(Long id);


}

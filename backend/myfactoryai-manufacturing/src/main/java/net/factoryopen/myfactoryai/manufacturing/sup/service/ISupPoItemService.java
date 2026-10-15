package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;

/**
 * 采购单明细Service接口
 * 
 * @author admin
 * @date 2024-09-23
 */
public interface ISupPoItemService 
{
    /**
     * 查询采购单明细
     * 
     * @param id 采购单明细主键
     * @return 采购单明细
     */
    public SupPoItem selectSupPoItemById(Long id);

    /**
     * 查询采购单明细列表
     * 
     * @param supPoItem 采购单明细
     * @return 采购单明细集合
     */
    public List<SupPoItem> selectSupPoItemList(SupPoItem supPoItem);

    /**
     * 新增采购单明细
     * 
     * @param supPoItem 采购单明细
     * @return 结果
     */
    public int insertSupPoItem(SupPoItem supPoItem);

    /**
     * 修改采购单明细
     * 
     * @param supPoItem 采购单明细
     * @return 结果
     */
    public int updateSupPoItem(SupPoItem supPoItem);

    /**
     * 批量删除采购单明细
     * 
     * @param ids 需要删除的采购单明细主键集合
     * @return 结果
     */
    public int deleteSupPoItemByIds(Long[] ids);

    /**
     * 删除采购单明细信息
     * 
     * @param id 采购单明细主键
     * @return 结果
     */
    public int deleteSupPoItemById(Long id);


}

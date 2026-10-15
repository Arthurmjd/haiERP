package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;

/**
 * 采购申请明细Service接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface ISupPurchaseRequestItemService 
{
    /**
     * 查询采购申请明细
     * 
     * @param id 采购申请明细主键
     * @return 采购申请明细
     */
    public SupPurchaseRequestItem selectSupPurchaseRequestItemById(Long id);

    /**
     * 查询采购申请明细列表
     * 
     * @param supPurchaseRequestItem 采购申请明细
     * @return 采购申请明细集合
     */
    public List<SupPurchaseRequestItem> selectSupPurchaseRequestItemList(SupPurchaseRequestItem supPurchaseRequestItem);

    /**
     * 新增采购申请明细
     * 
     * @param supPurchaseRequestItem 采购申请明细
     * @return 结果
     */
    public int insertSupPurchaseRequestItem(SupPurchaseRequestItem supPurchaseRequestItem);

    /**
     * 修改采购申请明细
     * 
     * @param supPurchaseRequestItem 采购申请明细
     * @return 结果
     */
    public int updateSupPurchaseRequestItem(SupPurchaseRequestItem supPurchaseRequestItem);

    /**
     * 批量删除采购申请明细
     * 
     * @param ids 需要删除的采购申请明细主键集合
     * @return 结果
     */
    public int deleteSupPurchaseRequestItemByIds(Long[] ids);

    /**
     * 删除采购申请明细信息
     * 
     * @param id 采购申请明细主键
     * @return 结果
     */
    public int deleteSupPurchaseRequestItemById(Long id);


}

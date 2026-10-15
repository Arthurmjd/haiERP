package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;

/**
 * 采购申请明细Mapper接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface SupPurchaseRequestItemMapper 
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
     * 查询采购申请明细列表
     *
     * @param prId 采购申请
     * @return 采购申请明细集合
     */
    public List<SupPurchaseRequestItem> selectSupPurchaseRequestItemListByPrId(Long prId);

    /**
     * 查询采购申请明细列表
     *
     * @param requestBillId 请求单ID
     * @return 采购申请明细集合
     */
    public List<SupPurchaseRequestItem> selectSupPurchaseRequestItemListByRequestBillId(Long requestBillId);

    /**
     * 查询采购申请明细列表
     *
     * @param materialId 物料编码
     * @return 采购申请明细集合
     */
    public List<SupPurchaseRequestItem> selectSupPurchaseRequestItemListByMaterialId(Long materialId);

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
     * 删除采购申请明细
     * 
     * @param id 采购申请明细主键
     * @return 结果
     */
    public int deleteSupPurchaseRequestItemById(Long id);

    /**
     * 批量删除采购申请明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupPurchaseRequestItemByIds(Long[] ids);

    /**
     * 查询采购申请明细列表
     *
     * @param prId 采购申请
     * @return 采购申请明细集合
     */
    public int deleteSupPurchaseRequestItemByPrId(Long prId);

    /**
     * 查询采购申请明细列表
     *
     * @param materialId 物料编码
     * @return 采购申请明细集合
     */
    public int deleteSupPurchaseRequestItemByMaterialId(Long materialId);
}

package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;

/**
 * 采购单管理Mapper接口
 * 
 * @author admin
 * @date 2024-09-23
 */
public interface SupPoMapper 
{
    /**
     * 查询采购单管理
     * 
     * @param id 采购单管理主键
     * @return 采购单管理
     */
    public SupPo selectSupPoById(Long id);

    /**
     * 查询采购单管理列表
     * 
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoDraftList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoPublishingList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoProcessingList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoFinishingList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoHistoryList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoAllList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supplierId 供应商ID
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListBySupplierId(Long supplierId);

    /**
     * 查询采购单管理列表
     *
     * @param invoicePatternId 开票方式ID
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询采购单管理列表
     *
     * @param payPatternId 付款方式ID
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListByPayPatternId(Long payPatternId);

    /**
     * 查询采购单管理列表
     *
     * @param currencyId 币别
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListByCurrencyId(Long currencyId);

    /**
     * 查询采购单管理列表
     *
     * @param shipmentTypeId 交货方式ID
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询采购单管理列表
     *
     * @param contactId 联系人ID
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListByContactId(Long contactId);

    /**
     * 查询采购单管理列表
     *
     * @param buyerId 采购员ID
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoListByBuyerId(Long buyerId);

    /**
     * 新增采购单管理
     * 
     * @param supPo 采购单管理
     * @return 结果
     */
    public int insertSupPo(SupPo supPo);

    /**
     * 修改采购单管理
     * 
     * @param supPo 采购单管理
     * @return 结果
     */
    public int updateSupPo(SupPo supPo);

    /**
     * 删除采购单管理
     * 
     * @param id 采购单管理主键
     * @return 结果
     */
    public int deleteSupPoById(Long id);

    /**
     * 批量删除采购单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupPoByIds(Long[] ids);

    /**
     * 查询采购单管理列表
     *
     * @param supplierId 供应商ID
     * @return 采购单管理集合
     */
    public int deleteSupPoBySupplierId(Long supplierId);

    /**
     * 查询采购单管理列表
     *
     * @param invoicePatternId 开票方式ID
     * @return 采购单管理集合
     */
    public int deleteSupPoByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询采购单管理列表
     *
     * @param payPatternId 付款方式ID
     * @return 采购单管理集合
     */
    public int deleteSupPoByPayPatternId(Long payPatternId);

    /**
     * 查询采购单管理列表
     *
     * @param currencyId 币别
     * @return 采购单管理集合
     */
    public int deleteSupPoByCurrencyId(Long currencyId);

    /**
     * 查询采购单管理列表
     *
     * @param shipmentTypeId 交货方式ID
     * @return 采购单管理集合
     */
    public int deleteSupPoByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询采购单管理列表
     *
     * @param contactId 联系人ID
     * @return 采购单管理集合
     */
    public int deleteSupPoByContactId(Long contactId);

    /**
     * 查询采购单管理列表
     *
     * @param buyerId 采购员ID
     * @return 采购单管理集合
     */
    public int deleteSupPoByBuyerId(Long buyerId);
}

package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoQuickly;

/**
 * 急出订单Mapper接口
 * 
 * @author admin
 * @date 2024-11-09
 */
public interface OrdSoQuicklyMapper 
{
    /**
     * 查询急出订单
     * 
     * @param id 急出订单主键
     * @return 急出订单
     */
    public OrdSoQuickly selectOrdSoQuicklyById(Long id);

    /**
     * 查询急出订单列表
     * 
     * @param ordSoQuickly 急出订单
     * @return 急出订单集合
     */
    public List<OrdSoQuickly> selectOrdSoQuicklyList(OrdSoQuickly ordSoQuickly);

    /**
     * 查询急出订单列表
     *
     * @param customerId 客户ID
     * @return 急出订单集合
     */
    public List<OrdSoQuickly> selectOrdSoQuicklyListByCustomerId(Long customerId);

    /**
     * 查询急出订单列表
     *
     * @param productId 产品ID
     * @return 急出订单集合
     */
    public List<OrdSoQuickly> selectOrdSoQuicklyListByProductId(Long productId);

    /**
     * 查询急出订单列表
     *
     * @param invoicePatternId 开票方式ID
     * @return 急出订单集合
     */
    public List<OrdSoQuickly> selectOrdSoQuicklyListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询急出订单列表
     *
     * @param salesId 销售员ID
     * @return 急出订单集合
     */
    public List<OrdSoQuickly> selectOrdSoQuicklyListBySalesId(Long salesId);

    /**
     * 新增急出订单
     * 
     * @param ordSoQuickly 急出订单
     * @return 结果
     */
    public int insertOrdSoQuickly(OrdSoQuickly ordSoQuickly);

    /**
     * 修改急出订单
     * 
     * @param ordSoQuickly 急出订单
     * @return 结果
     */
    public int updateOrdSoQuickly(OrdSoQuickly ordSoQuickly);

    /**
     * 删除急出订单
     * 
     * @param id 急出订单主键
     * @return 结果
     */
    public int deleteOrdSoQuicklyById(Long id);

    /**
     * 批量删除急出订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdSoQuicklyByIds(Long[] ids);

    /**
     * 查询急出订单列表
     *
     * @param customerId 客户ID
     * @return 急出订单集合
     */
    public int deleteOrdSoQuicklyByCustomerId(Long customerId);

    /**
     * 查询急出订单列表
     *
     * @param productId 产品ID
     * @return 急出订单集合
     */
    public int deleteOrdSoQuicklyByProductId(Long productId);

    /**
     * 查询急出订单列表
     *
     * @param invoicePatternId 开票方式ID
     * @return 急出订单集合
     */
    public int deleteOrdSoQuicklyByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询急出订单列表
     *
     * @param salesId 销售员ID
     * @return 急出订单集合
     */
    public int deleteOrdSoQuicklyBySalesId(Long salesId);
}

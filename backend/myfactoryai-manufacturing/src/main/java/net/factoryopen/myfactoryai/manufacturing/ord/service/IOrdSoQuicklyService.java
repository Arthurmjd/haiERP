package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoQuickly;

/**
 * 急出订单Service接口
 * 
 * @author admin
 * @date 2024-11-09
 */
public interface IOrdSoQuicklyService 
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
     * 批量删除急出订单
     * 
     * @param ids 需要删除的急出订单主键集合
     * @return 结果
     */
    public int deleteOrdSoQuicklyByIds(Long[] ids);

    /**
     * 删除急出订单信息
     * 
     * @param id 急出订单主键
     * @return 结果
     */
    public int deleteOrdSoQuicklyById(Long id);

    /**
     * 发布
     *
     * @param ordSoQuickly 发布
     * @return 结果
     */
    public int enable(OrdSoQuickly ordSoQuickly);

    /**
     * 发货
     *
     * @param ids id集合
     * @return 结果
     */
    public int deliveryDirectly(Long[] ids) throws Exception;

    /**
     * 发货
     *
     * @param ids id集合
     * @return 结果
     */
    public int deliveryQuickly(Long[] ids) throws Exception;


}

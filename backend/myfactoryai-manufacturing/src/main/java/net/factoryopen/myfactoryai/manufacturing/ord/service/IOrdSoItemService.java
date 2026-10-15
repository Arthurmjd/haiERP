package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;

/**
 * 订单明细Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdSoItemService 
{
    /**
     * 查询订单明细
     * 
     * @param id 订单明细主键
     * @return 订单明细
     */
    public OrdSoItem selectOrdSoItemById(Long id);

    /**
     * 查询订单明细列表
     * 
     * @param ordSoItem 订单明细
     * @return 订单明细集合
     */
    public List<OrdSoItem> selectOrdSoItemList(OrdSoItem ordSoItem);

    /**
     * 查询订单明细列表
     *
     * @param soId 订单ID
     * @return 订单明细集合
     */
    public List<OrdSoItem> selectOrdSoItemListBySoId(Long soId);

    /**
     * 新增订单明细
     * 
     * @param ordSoItem 订单明细
     * @return 结果
     */
    public int insertOrdSoItem(OrdSoItem ordSoItem);

    /**
     * 修改订单明细
     * 
     * @param ordSoItem 订单明细
     * @return 结果
     */
    public int updateOrdSoItem(OrdSoItem ordSoItem);

    /**
     * 批量删除订单明细
     * 
     * @param ids 需要删除的订单明细主键集合
     * @return 结果
     */
    public int deleteOrdSoItemByIds(Long[] ids);

    /**
     * 删除订单明细信息
     * 
     * @param id 订单明细主键
     * @return 结果
     */
    public int deleteOrdSoItemById(Long id);


}

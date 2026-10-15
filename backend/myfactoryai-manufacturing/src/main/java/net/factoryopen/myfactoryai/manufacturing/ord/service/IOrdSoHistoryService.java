package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoHistory;

/**
 * 订单历史查询Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdSoHistoryService 
{
    /**
     * 查询订单历史查询
     * 
     * @param id 订单历史查询主键
     * @return 订单历史查询
     */
    public OrdSoHistory selectOrdSoHistoryById(Long id);

    /**
     * 查询订单历史查询列表
     * 
     * @param ordSoHistory 订单历史查询
     * @return 订单历史查询集合
     */
    public List<OrdSoHistory> selectOrdSoHistoryList(OrdSoHistory ordSoHistory);

    /**
     * 新增订单历史查询
     * 
     * @param ordSoHistory 订单历史查询
     * @return 结果
     */
    public int insertOrdSoHistory(OrdSoHistory ordSoHistory);

    /**
     * 修改订单历史查询
     * 
     * @param ordSoHistory 订单历史查询
     * @return 结果
     */
    public int updateOrdSoHistory(OrdSoHistory ordSoHistory);

    /**
     * 批量删除订单历史查询
     * 
     * @param ids 需要删除的订单历史查询主键集合
     * @return 结果
     */
    public int deleteOrdSoHistoryByIds(Long[] ids);

    /**
     * 删除订单历史查询信息
     * 
     * @param id 订单历史查询主键
     * @return 结果
     */
    public int deleteOrdSoHistoryById(Long id);


}

package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupBuyers;

/**
 * 采购员配置Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISupBuyersService 
{
    /**
     * 查询采购员配置
     * 
     * @param id 采购员配置主键
     * @return 采购员配置
     */
    public SupBuyers selectSupBuyersById(Long id);

    /**
     * 查询采购员配置列表
     * 
     * @param supBuyers 采购员配置
     * @return 采购员配置集合
     */
    public List<SupBuyers> selectSupBuyersList(SupBuyers supBuyers);

    /**
     * 新增采购员配置
     * 
     * @param supBuyers 采购员配置
     * @return 结果
     */
    public int insertSupBuyers(SupBuyers supBuyers);

    /**
     * 修改采购员配置
     * 
     * @param supBuyers 采购员配置
     * @return 结果
     */
    public int updateSupBuyers(SupBuyers supBuyers);

    /**
     * 批量删除采购员配置
     * 
     * @param ids 需要删除的采购员配置主键集合
     * @return 结果
     */
    public int deleteSupBuyersByIds(Long[] ids);

    /**
     * 删除采购员配置信息
     * 
     * @param id 采购员配置主键
     * @return 结果
     */
    public int deleteSupBuyersById(Long id);


}

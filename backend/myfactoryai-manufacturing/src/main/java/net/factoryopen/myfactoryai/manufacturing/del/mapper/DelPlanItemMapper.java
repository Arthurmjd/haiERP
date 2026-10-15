package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;
import org.springframework.stereotype.Service;

/**
 * 发货计划Mapper接口
 * 
 * @author admin
 * @date 2024-07-09
 */
@Service
public interface DelPlanItemMapper 
{
    /**
     * 查询发货计划
     * 
     * @param id 发货计划主键
     * @return 发货计划
     */
    public DelPlanItem selectDelPlanItemById(Long id);

    /**
     * 查询发货计划列表
     * 
     * @param delPlanItem 发货计划
     * @return 发货计划集合
     */
    public List<DelPlanItem> selectDelPlanItemList(DelPlanItem delPlanItem);

    /**
     * 查询发货计划列表
     *
     * @param soId 销售订单
     * @return 发货计划集合
     */
    public List<DelPlanItem> selectDelPlanItemListBySoId(Long soId);

    /**
     * 查询发货计划列表
     *
     * @param soItemId 订单项
     * @return 发货计划集合
     */
    public List<DelPlanItem> selectDelPlanItemListBySoItemId(Long soItemId);

    /**
     * 查询发货计划列表
     *
     * @param customerId 客户ID
     * @return 发货计划集合
     */
    public List<DelPlanItem> selectDelPlanItemListByCustomerId(Long customerId);

    /**
     * 新增发货计划
     * 
     * @param delPlanItem 发货计划
     * @return 结果
     */
    public int insertDelPlanItem(DelPlanItem delPlanItem);

    /**
     * 修改发货计划
     * 
     * @param delPlanItem 发货计划
     * @return 结果
     */
    public int updateDelPlanItem(DelPlanItem delPlanItem);

    /**
     * 删除发货计划
     * 
     * @param id 发货计划主键
     * @return 结果
     */
    public int deleteDelPlanItemById(Long id);

    /**
     * 批量删除发货计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelPlanItemByIds(Long[] ids);

    /**
     * 查询发货计划列表
     *
     * @param soId 销售订单
     * @return 发货计划集合
     */
    public int deleteDelPlanItemBySoId(Long soId);

    /**
     * 查询发货计划列表
     *
     * @param soItemId 订单项
     * @return 发货计划集合
     */
    public int deleteDelPlanItemBySoItemId(Long soItemId);

    /**
     * 查询发货计划列表
     *
     * @param customerId 客户ID
     * @return 发货计划集合
     */
    public int deleteDelPlanItemByCustomerId(Long customerId);
}

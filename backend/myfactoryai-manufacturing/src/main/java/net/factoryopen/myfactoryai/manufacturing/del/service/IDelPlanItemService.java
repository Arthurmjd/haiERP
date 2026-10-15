package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;

/**
 * 发货计划Service接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface IDelPlanItemService 
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
     * 批量删除发货计划
     * 
     * @param ids 需要删除的发货计划主键集合
     * @return 结果
     */
    public int deleteDelPlanItemByIds(Long[] ids);

    /**
     * 删除发货计划信息
     * 
     * @param id 发货计划主键
     * @return 结果
     */
    public int deleteDelPlanItemById(Long id);


}

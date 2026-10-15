package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchasingPlan;

/**
 * 采购计划Service接口
 * 
 * @author admin
 * @date 2024-09-18
 */
public interface ISupPurchasingPlanService 
{
    /**
     * 查询采购计划
     * 
     * @param id 采购计划主键
     * @return 采购计划
     */
    public SupPurchasingPlan selectSupPurchasingPlanById(Long id);

    /**
     * 查询采购计划列表
     * 
     * @param supPurchasingPlan 采购计划
     * @return 采购计划集合
     */
    public List<SupPurchasingPlan> selectSupPurchasingPlanList(SupPurchasingPlan supPurchasingPlan);

    /**
     * 新增采购计划
     * 
     * @param supPurchasingPlan 采购计划
     * @return 结果
     */
    public int insertSupPurchasingPlan(SupPurchasingPlan supPurchasingPlan);

    /**
     * 修改采购计划
     * 
     * @param supPurchasingPlan 采购计划
     * @return 结果
     */
    public int updateSupPurchasingPlan(SupPurchasingPlan supPurchasingPlan);

    /**
     * 批量删除采购计划
     * 
     * @param ids 需要删除的采购计划主键集合
     * @return 结果
     */
    public int deleteSupPurchasingPlanByIds(Long[] ids);

    /**
     * 删除采购计划信息
     * 
     * @param id 采购计划主键
     * @return 结果
     */
    public int deleteSupPurchasingPlanById(Long id);

    /**
     * 启用
     *
     * @param supPurchasingPlan 启用
     * @return 结果
     */
    public int enable(SupPurchasingPlan supPurchasingPlan);

    /**
     * 转单
     *
     * @param supPurchasingPlan 转单
     * @return 结果
     */
    public int toPo(Long[] ids, SupPurchasingPlan supPurchasingPlan) throws Exception;

    /**
     * 冻结
     *
     * @param supPurchasingPlan 冻结
     * @return 结果
     */
    public int froze(SupPurchasingPlan supPurchasingPlan);

    /**
     * 解冻
     *
     * @param supPurchasingPlan 解冻
     * @return 结果
     */
    public int unfroze(SupPurchasingPlan supPurchasingPlan);

    /**
     * 停用
     *
     * @param supPurchasingPlan 停用
     * @return 结果
     */
    public int disable(SupPurchasingPlan supPurchasingPlan);


}

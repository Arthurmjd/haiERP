package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchasingPlan;

/**
 * 采购计划Mapper接口
 * 
 * @author admin
 * @date 2024-09-18
 */
public interface SupPurchasingPlanMapper 
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
     * 查询采购计划列表
     *
     * @param materialId 物料ID
     * @return 采购计划集合
     */
    public List<SupPurchasingPlan> selectSupPurchasingPlanListByMaterialId(Long materialId);

    /**
     * 查询采购计划列表
     *
     * @param prId 采购申请ID
     * @return 采购计划集合
     */
    public List<SupPurchasingPlan> selectSupPurchasingPlanListByPrId(Long prId);

    /**
     * 查询采购计划列表
     *
     * @param prItemId 项序
     * @return 采购计划集合
     */
    public List<SupPurchasingPlan> selectSupPurchasingPlanListByPrItemId(Long prItemId);

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
     * 删除采购计划
     * 
     * @param id 采购计划主键
     * @return 结果
     */
    public int deleteSupPurchasingPlanById(Long id);

    /**
     * 批量删除采购计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupPurchasingPlanByIds(Long[] ids);

    /**
     * 查询采购计划列表
     *
     * @param materialId 物料ID
     * @return 采购计划集合
     */
    public int deleteSupPurchasingPlanByMaterialId(Long materialId);

    /**
     * 查询采购计划列表
     *
     * @param prId 采购申请ID
     * @return 采购计划集合
     */
    public int deleteSupPurchasingPlanByPrId(Long prId);

    /**
     * 查询采购计划列表
     *
     * @param prItemId 项序
     * @return 采购计划集合
     */
    public int deleteSupPurchasingPlanByPrItemId(Long prItemId);
}

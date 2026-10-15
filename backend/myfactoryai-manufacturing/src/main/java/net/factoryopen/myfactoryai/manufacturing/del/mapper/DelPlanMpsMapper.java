package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanMps;

/**
 * 主计划Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface DelPlanMpsMapper 
{
    /**
     * 查询主计划
     * 
     * @param id 主计划主键
     * @return 主计划
     */
    public DelPlanMps selectDelPlanMpsById(Long id);

    /**
     * 查询主计划列表
     * 
     * @param delPlanMps 主计划
     * @return 主计划集合
     */
    public List<DelPlanMps> selectDelPlanMpsList(DelPlanMps delPlanMps);

    /**
     * 查询主计划列表
     *
     * @param customerId 客户ID
     * @return 主计划集合
     */
    public List<DelPlanMps> selectDelPlanMpsListByCustomerId(Long customerId);

    /**
     * 查询主计划列表
     *
     * @param soId 销售订单
     * @return 主计划集合
     */
    public List<DelPlanMps> selectDelPlanMpsListBySoId(Long soId);

    /**
     * 新增主计划
     * 
     * @param delPlanMps 主计划
     * @return 结果
     */
    public int insertDelPlanMps(DelPlanMps delPlanMps);

    /**
     * 修改主计划
     * 
     * @param delPlanMps 主计划
     * @return 结果
     */
    public int updateDelPlanMps(DelPlanMps delPlanMps);

    /**
     * 删除主计划
     * 
     * @param id 主计划主键
     * @return 结果
     */
    public int deleteDelPlanMpsById(Long id);

    /**
     * 批量删除主计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelPlanMpsByIds(Long[] ids);

    /**
     * 查询主计划列表
     *
     * @param customerId 客户ID
     * @return 主计划集合
     */
    public int deleteDelPlanMpsByCustomerId(Long customerId);

    /**
     * 查询主计划列表
     *
     * @param soId 销售订单
     * @return 主计划集合
     */
    public int deleteDelPlanMpsBySoId(Long soId);
}

package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanMps;

/**
 * 主计划Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelPlanMpsService 
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
     * 批量删除主计划
     * 
     * @param ids 需要删除的主计划主键集合
     * @return 结果
     */
    public int deleteDelPlanMpsByIds(Long[] ids);

    /**
     * 删除主计划信息
     * 
     * @param id 主计划主键
     * @return 结果
     */
    public int deleteDelPlanMpsById(Long id);


}

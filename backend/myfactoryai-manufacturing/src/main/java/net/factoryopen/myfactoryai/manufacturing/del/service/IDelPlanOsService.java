package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanOs;

/**
 * 外协计划Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelPlanOsService 
{
    /**
     * 查询外协计划
     * 
     * @param id 外协计划主键
     * @return 外协计划
     */
    public DelPlanOs selectDelPlanOsById(Long id);

    /**
     * 查询外协计划列表
     * 
     * @param delPlanOs 外协计划
     * @return 外协计划集合
     */
    public List<DelPlanOs> selectDelPlanOsList(DelPlanOs delPlanOs);

    /**
     * 新增外协计划
     * 
     * @param delPlanOs 外协计划
     * @return 结果
     */
    public int insertDelPlanOs(DelPlanOs delPlanOs);

    /**
     * 修改外协计划
     * 
     * @param delPlanOs 外协计划
     * @return 结果
     */
    public int updateDelPlanOs(DelPlanOs delPlanOs);

    /**
     * 批量删除外协计划
     * 
     * @param ids 需要删除的外协计划主键集合
     * @return 结果
     */
    public int deleteDelPlanOsByIds(Long[] ids);

    /**
     * 删除外协计划信息
     * 
     * @param id 外协计划主键
     * @return 结果
     */
    public int deleteDelPlanOsById(Long id);


}

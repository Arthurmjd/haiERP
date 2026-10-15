package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanOs;

/**
 * 外协计划Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface DelPlanOsMapper 
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
     * 查询外协计划列表
     *
     * @param soItemId 订单项
     * @return 外协计划集合
     */
    public List<DelPlanOs> selectDelPlanOsListBySoItemId(Long soItemId);

    /**
     * 查询外协计划列表
     *
     * @param customerId 客户ID
     * @return 外协计划集合
     */
    public List<DelPlanOs> selectDelPlanOsListByCustomerId(Long customerId);

    /**
     * 查询外协计划列表
     *
     * @param soId 销售订单
     * @return 外协计划集合
     */
    public List<DelPlanOs> selectDelPlanOsListBySoId(Long soId);

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
     * 删除外协计划
     * 
     * @param id 外协计划主键
     * @return 结果
     */
    public int deleteDelPlanOsById(Long id);

    /**
     * 批量删除外协计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelPlanOsByIds(Long[] ids);

    /**
     * 查询外协计划列表
     *
     * @param soItemId 订单项
     * @return 外协计划集合
     */
    public int deleteDelPlanOsBySoItemId(Long soItemId);

    /**
     * 查询外协计划列表
     *
     * @param customerId 客户ID
     * @return 外协计划集合
     */
    public int deleteDelPlanOsByCustomerId(Long customerId);

    /**
     * 查询外协计划列表
     *
     * @param soId 销售订单
     * @return 外协计划集合
     */
    public int deleteDelPlanOsBySoId(Long soId);
}

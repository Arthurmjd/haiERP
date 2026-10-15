package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingtool;

/**
 * 工单工具Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface PlnWoRoutingtoolMapper 
{
    /**
     * 查询工单工具
     * 
     * @param id 工单工具主键
     * @return 工单工具
     */
    public PlnWoRoutingtool selectPlnWoRoutingtoolById(Long id);

    /**
     * 查询工单工具列表
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 工单工具集合
     */
    public List<PlnWoRoutingtool> selectPlnWoRoutingtoolList(PlnWoRoutingtool plnWoRoutingtool);

    /**
     * 查询工单工具列表
     *
     * @param routingId 工序
     * @return 工单工具集合
     */
    public List<PlnWoRoutingtool> selectPlnWoRoutingtoolListByRoutingId(Long routingId);

    /**
     * 查询工单工具列表
     *
     * @param toolId 工具类型
     * @return 工单工具集合
     */
    public List<PlnWoRoutingtool> selectPlnWoRoutingtoolListByToolId(Long toolId);

    /**
     * 新增工单工具
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 结果
     */
    public int insertPlnWoRoutingtool(PlnWoRoutingtool plnWoRoutingtool);

    /**
     * 修改工单工具
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 结果
     */
    public int updatePlnWoRoutingtool(PlnWoRoutingtool plnWoRoutingtool);

    /**
     * 删除工单工具
     * 
     * @param id 工单工具主键
     * @return 结果
     */
    public int deletePlnWoRoutingtoolById(Long id);

    /**
     * 批量删除工单工具
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingtoolByIds(Long[] ids);

    /**
     * 查询工单工具列表
     *
     * @param routingId 工序
     * @return 工单工具集合
     */
    public int deletePlnWoRoutingtoolByRoutingId(Long routingId);

    /**
     * 查询工单工具列表
     *
     * @param toolId 工具类型
     * @return 工单工具集合
     */
    public int deletePlnWoRoutingtoolByToolId(Long toolId);
}

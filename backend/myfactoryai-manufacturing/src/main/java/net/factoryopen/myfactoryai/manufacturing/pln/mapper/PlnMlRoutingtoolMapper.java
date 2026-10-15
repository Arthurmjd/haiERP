package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingtool;

/**
 * 批次工具Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface PlnMlRoutingtoolMapper 
{
    /**
     * 查询批次工具
     * 
     * @param id 批次工具主键
     * @return 批次工具
     */
    public PlnMlRoutingtool selectPlnMlRoutingtoolById(Long id);

    /**
     * 查询批次工具列表
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 批次工具集合
     */
    public List<PlnMlRoutingtool> selectPlnMlRoutingtoolList(PlnMlRoutingtool plnMlRoutingtool);

    /**
     * 查询批次工具列表
     *
     * @param routingId 工序
     * @return 批次工具集合
     */
    public List<PlnMlRoutingtool> selectPlnMlRoutingtoolListByRoutingId(Long routingId);

    /**
     * 查询批次工具列表
     *
     * @param toolId 工具类型
     * @return 批次工具集合
     */
    public List<PlnMlRoutingtool> selectPlnMlRoutingtoolListByToolId(Long toolId);

    /**
     * 新增批次工具
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 结果
     */
    public int insertPlnMlRoutingtool(PlnMlRoutingtool plnMlRoutingtool);

    /**
     * 修改批次工具
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 结果
     */
    public int updatePlnMlRoutingtool(PlnMlRoutingtool plnMlRoutingtool);

    /**
     * 删除批次工具
     * 
     * @param id 批次工具主键
     * @return 结果
     */
    public int deletePlnMlRoutingtoolById(Long id);

    /**
     * 批量删除批次工具
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingtoolByIds(Long[] ids);

    /**
     * 查询批次工具列表
     *
     * @param routingId 工序
     * @return 批次工具集合
     */
    public int deletePlnMlRoutingtoolByRoutingId(Long routingId);

    /**
     * 查询批次工具列表
     *
     * @param toolId 工具类型
     * @return 批次工具集合
     */
    public int deletePlnMlRoutingtoolByToolId(Long toolId);
}

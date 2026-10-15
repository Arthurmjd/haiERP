package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupTool;

/**
 * 班组工序Mapper接口
 * 
 * @author admin
 * @date 2025-01-02
 */
public interface ResWorkgroupToolMapper 
{
    /**
     * 查询班组工序
     * 
     * @param id 班组工序主键
     * @return 班组工序
     */
    public ResWorkgroupTool selectResWorkgroupToolById(Long id);

    /**
     * 查询班组工序列表
     * 
     * @param resWorkgroupTool 班组工序
     * @return 班组工序集合
     */
    public List<ResWorkgroupTool> selectResWorkgroupToolList(ResWorkgroupTool resWorkgroupTool);

    /**
     * 查询班组工序列表
     *
     * @param groupId 班组ID
     * @return 班组工序集合
     */
    public List<ResWorkgroupTool> selectResWorkgroupToolListByGroupId(Long groupId);

    /**
     * 查询班组工序列表
     *
     * @param toolId 工具ID
     * @return 班组工序集合
     */
    public List<ResWorkgroupTool> selectResWorkgroupToolListByToolId(Long toolId);

    /**
     * 新增班组工序
     * 
     * @param resWorkgroupTool 班组工序
     * @return 结果
     */
    public int insertResWorkgroupTool(ResWorkgroupTool resWorkgroupTool);

    /**
     * 修改班组工序
     * 
     * @param resWorkgroupTool 班组工序
     * @return 结果
     */
    public int updateResWorkgroupTool(ResWorkgroupTool resWorkgroupTool);

    /**
     * 删除班组工序
     * 
     * @param id 班组工序主键
     * @return 结果
     */
    public int deleteResWorkgroupToolById(Long id);

    /**
     * 批量删除班组工序
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResWorkgroupToolByIds(Long[] ids);

    /**
     * 查询班组工序列表
     *
     * @param groupId 班组ID
     * @return 班组工序集合
     */
    public int deleteResWorkgroupToolByGroupId(Long groupId);

    /**
     * 查询班组工序列表
     *
     * @param toolId 工具ID
     * @return 班组工序集合
     */
    public int deleteResWorkgroupToolByToolId(Long toolId);
}

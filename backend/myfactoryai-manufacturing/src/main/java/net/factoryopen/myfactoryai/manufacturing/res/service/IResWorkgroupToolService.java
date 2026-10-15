package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupTool;

/**
 * 班组工序Service接口
 * 
 * @author admin
 * @date 2025-01-02
 */
public interface IResWorkgroupToolService 
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
     * 批量删除班组工序
     * 
     * @param ids 需要删除的班组工序主键集合
     * @return 结果
     */
    public int deleteResWorkgroupToolByIds(Long[] ids);

    /**
     * 删除班组工序信息
     * 
     * @param id 班组工序主键
     * @return 结果
     */
    public int deleteResWorkgroupToolById(Long id);


}

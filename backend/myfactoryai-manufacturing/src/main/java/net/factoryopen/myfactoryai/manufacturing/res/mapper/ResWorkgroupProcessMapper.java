package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupProcess;

/**
 * 班组工序Mapper接口
 * 
 * @author admin
 * @date 2025-01-02
 */
public interface ResWorkgroupProcessMapper 
{
    /**
     * 查询班组工序
     * 
     * @param id 班组工序主键
     * @return 班组工序
     */
    public ResWorkgroupProcess selectResWorkgroupProcessById(Long id);

    /**
     * 查询班组工序列表
     * 
     * @param resWorkgroupProcess 班组工序
     * @return 班组工序集合
     */
    public List<ResWorkgroupProcess> selectResWorkgroupProcessList(ResWorkgroupProcess resWorkgroupProcess);

    /**
     * 查询班组工序列表
     *
     * @param groupId 班组ID
     * @return 班组工序集合
     */
    public List<ResWorkgroupProcess> selectResWorkgroupProcessListByGroupId(Long groupId);

    /**
     * 查询班组工序列表
     *
     * @param processId 工序ID
     * @return 班组工序集合
     */
    public List<ResWorkgroupProcess> selectResWorkgroupProcessListByProcessId(Long processId);

    /**
     * 新增班组工序
     * 
     * @param resWorkgroupProcess 班组工序
     * @return 结果
     */
    public int insertResWorkgroupProcess(ResWorkgroupProcess resWorkgroupProcess);

    /**
     * 修改班组工序
     * 
     * @param resWorkgroupProcess 班组工序
     * @return 结果
     */
    public int updateResWorkgroupProcess(ResWorkgroupProcess resWorkgroupProcess);

    /**
     * 删除班组工序
     * 
     * @param id 班组工序主键
     * @return 结果
     */
    public int deleteResWorkgroupProcessById(Long id);

    /**
     * 批量删除班组工序
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResWorkgroupProcessByIds(Long[] ids);

    /**
     * 查询班组工序列表
     *
     * @param groupId 班组ID
     * @return 班组工序集合
     */
    public int deleteResWorkgroupProcessByGroupId(Long groupId);

    /**
     * 查询班组工序列表
     *
     * @param processId 工序ID
     * @return 班组工序集合
     */
    public int deleteResWorkgroupProcessByProcessId(Long processId);
}

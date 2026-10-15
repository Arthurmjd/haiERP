package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlRoutingChange;

/**
 * 批次制程变更Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ExeMlRoutingChangeMapper 
{
    /**
     * 查询批次制程变更
     * 
     * @param id 批次制程变更主键
     * @return 批次制程变更
     */
    public ExeMlRoutingChange selectExeMlRoutingChangeById(Long id);

    /**
     * 查询批次制程变更列表
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeList(ExeMlRoutingChange exeMlRoutingChange);

    /**
     * 查询批次制程变更列表
     *
     * @param productId 产品编码
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByProductId(Long productId);

    /**
     * 查询批次制程变更列表
     *
     * @param currentProcessId 现工序码
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询批次制程变更列表
     *
     * @param currentWorkgroupId 现班组
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询批次制程变更列表
     *
     * @param unitId 单位
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByUnitId(Long unitId);

    /**
     * 查询批次制程变更列表
     *
     * @param lineId 产线
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByLineId(Long lineId);

    /**
     * 查询批次制程变更列表
     *
     * @param projectId 项目
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByProjectId(Long projectId);

    /**
     * 查询批次制程变更列表
     *
     * @param sourceMlId 来源批次
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListBySourceMlId(Long sourceMlId);

    /**
     * 查询批次制程变更列表
     *
     * @param woId 工单
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByWoId(Long woId);

    /**
     * 查询批次制程变更列表
     *
     * @param dispatchingBy 派工人
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询批次制程变更列表
     *
     * @param acceptingBy 接收人
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByAcceptingBy(Long acceptingBy);

    /**
     * 查询批次制程变更列表
     *
     * @param startBy 开工人
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByStartBy(Long startBy);

    /**
     * 查询批次制程变更列表
     *
     * @param finishBy 完工人
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByFinishBy(Long finishBy);

    /**
     * 查询批次制程变更列表
     *
     * @param stockinBy 入库人
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeListByStockinBy(Long stockinBy);

    /**
     * 新增批次制程变更
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 结果
     */
    public int insertExeMlRoutingChange(ExeMlRoutingChange exeMlRoutingChange);

    /**
     * 修改批次制程变更
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 结果
     */
    public int updateExeMlRoutingChange(ExeMlRoutingChange exeMlRoutingChange);

    /**
     * 删除批次制程变更
     * 
     * @param id 批次制程变更主键
     * @return 结果
     */
    public int deleteExeMlRoutingChangeById(Long id);

    /**
     * 批量删除批次制程变更
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlRoutingChangeByIds(Long[] ids);

    /**
     * 查询批次制程变更列表
     *
     * @param productId 产品编码
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByProductId(Long productId);

    /**
     * 查询批次制程变更列表
     *
     * @param currentProcessId 现工序码
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByCurrentProcessId(Long currentProcessId);

    /**
     * 查询批次制程变更列表
     *
     * @param currentWorkgroupId 现班组
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询批次制程变更列表
     *
     * @param unitId 单位
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByUnitId(Long unitId);

    /**
     * 查询批次制程变更列表
     *
     * @param lineId 产线
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByLineId(Long lineId);

    /**
     * 查询批次制程变更列表
     *
     * @param projectId 项目
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByProjectId(Long projectId);

    /**
     * 查询批次制程变更列表
     *
     * @param sourceMlId 来源批次
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeBySourceMlId(Long sourceMlId);

    /**
     * 查询批次制程变更列表
     *
     * @param woId 工单
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByWoId(Long woId);

    /**
     * 查询批次制程变更列表
     *
     * @param dispatchingBy 派工人
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByDispatchingBy(Long dispatchingBy);

    /**
     * 查询批次制程变更列表
     *
     * @param acceptingBy 接收人
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByAcceptingBy(Long acceptingBy);

    /**
     * 查询批次制程变更列表
     *
     * @param startBy 开工人
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByStartBy(Long startBy);

    /**
     * 查询批次制程变更列表
     *
     * @param finishBy 完工人
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByFinishBy(Long finishBy);

    /**
     * 查询批次制程变更列表
     *
     * @param stockinBy 入库人
     * @return 批次制程变更集合
     */
    public int deleteExeMlRoutingChangeByStockinBy(Long stockinBy);
}

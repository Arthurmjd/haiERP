package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlHistory;

/**
 * 历史批次查询Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ExeMlHistoryMapper 
{
    /**
     * 查询历史批次查询
     * 
     * @param id 历史批次查询主键
     * @return 历史批次查询
     */
    public ExeMlHistory selectExeMlHistoryById(Long id);

    /**
     * 查询历史批次查询列表
     * 
     * @param exeMlHistory 历史批次查询
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryList(ExeMlHistory exeMlHistory);

    /**
     * 查询历史批次查询列表
     *
     * @param productId 产品编码
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByProductId(Long productId);

    /**
     * 查询历史批次查询列表
     *
     * @param currentProcessId 现工序码
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询历史批次查询列表
     *
     * @param currentWorkgroupId 现班组
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询历史批次查询列表
     *
     * @param unitId 单位
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByUnitId(Long unitId);

    /**
     * 查询历史批次查询列表
     *
     * @param lineId 产线
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByLineId(Long lineId);

    /**
     * 查询历史批次查询列表
     *
     * @param projectId 项目
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByProjectId(Long projectId);

    /**
     * 查询历史批次查询列表
     *
     * @param sourceMlId 来源批次
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListBySourceMlId(Long sourceMlId);

    /**
     * 查询历史批次查询列表
     *
     * @param woId 工单
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByWoId(Long woId);

    /**
     * 查询历史批次查询列表
     *
     * @param dispatchingBy 派工人
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询历史批次查询列表
     *
     * @param acceptingBy 接收人
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByAcceptingBy(Long acceptingBy);

    /**
     * 查询历史批次查询列表
     *
     * @param startBy 开工人
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByStartBy(Long startBy);

    /**
     * 查询历史批次查询列表
     *
     * @param finishBy 完工人
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByFinishBy(Long finishBy);

    /**
     * 查询历史批次查询列表
     *
     * @param stockinBy 入库人
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryListByStockinBy(Long stockinBy);

    /**
     * 新增历史批次查询
     * 
     * @param exeMlHistory 历史批次查询
     * @return 结果
     */
    public int insertExeMlHistory(ExeMlHistory exeMlHistory);

    /**
     * 修改历史批次查询
     * 
     * @param exeMlHistory 历史批次查询
     * @return 结果
     */
    public int updateExeMlHistory(ExeMlHistory exeMlHistory);

    /**
     * 删除历史批次查询
     * 
     * @param id 历史批次查询主键
     * @return 结果
     */
    public int deleteExeMlHistoryById(Long id);

    /**
     * 批量删除历史批次查询
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlHistoryByIds(Long[] ids);

    /**
     * 查询历史批次查询列表
     *
     * @param productId 产品编码
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByProductId(Long productId);

    /**
     * 查询历史批次查询列表
     *
     * @param currentProcessId 现工序码
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByCurrentProcessId(Long currentProcessId);

    /**
     * 查询历史批次查询列表
     *
     * @param currentWorkgroupId 现班组
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询历史批次查询列表
     *
     * @param unitId 单位
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByUnitId(Long unitId);

    /**
     * 查询历史批次查询列表
     *
     * @param lineId 产线
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByLineId(Long lineId);

    /**
     * 查询历史批次查询列表
     *
     * @param projectId 项目
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByProjectId(Long projectId);

    /**
     * 查询历史批次查询列表
     *
     * @param sourceMlId 来源批次
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryBySourceMlId(Long sourceMlId);

    /**
     * 查询历史批次查询列表
     *
     * @param woId 工单
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByWoId(Long woId);

    /**
     * 查询历史批次查询列表
     *
     * @param dispatchingBy 派工人
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByDispatchingBy(Long dispatchingBy);

    /**
     * 查询历史批次查询列表
     *
     * @param acceptingBy 接收人
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByAcceptingBy(Long acceptingBy);

    /**
     * 查询历史批次查询列表
     *
     * @param startBy 开工人
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByStartBy(Long startBy);

    /**
     * 查询历史批次查询列表
     *
     * @param finishBy 完工人
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByFinishBy(Long finishBy);

    /**
     * 查询历史批次查询列表
     *
     * @param stockinBy 入库人
     * @return 历史批次查询集合
     */
    public int deleteExeMlHistoryByStockinBy(Long stockinBy);
}

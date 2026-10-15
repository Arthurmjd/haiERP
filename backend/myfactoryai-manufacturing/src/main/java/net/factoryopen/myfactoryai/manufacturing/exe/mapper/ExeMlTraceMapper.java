package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTrace;

/**
 * 在制批次跟踪Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ExeMlTraceMapper 
{
    /**
     * 查询在制批次跟踪
     * 
     * @param id 在制批次跟踪主键
     * @return 在制批次跟踪
     */
    public ExeMlTrace selectExeMlTraceById(Long id);

    /**
     * 查询在制批次跟踪列表
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceList(ExeMlTrace exeMlTrace);

    /**
     * 查询在制批次跟踪列表
     *
     * @param productId 产品编码
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByProductId(Long productId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param currentProcessId 现工序码
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param currentWorkgroupId 现班组
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param unitId 单位
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByUnitId(Long unitId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param lineId 产线
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByLineId(Long lineId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param projectId 项目
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByProjectId(Long projectId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param sourceMlId 来源批次
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListBySourceMlId(Long sourceMlId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param woId 工单
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByWoId(Long woId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param dispatchingBy 派工人
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param acceptingBy 接收人
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByAcceptingBy(Long acceptingBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param startBy 开工人
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByStartBy(Long startBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param finishBy 完工人
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByFinishBy(Long finishBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param stockinBy 入库人
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceListByStockinBy(Long stockinBy);

    /**
     * 新增在制批次跟踪
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 结果
     */
    public int insertExeMlTrace(ExeMlTrace exeMlTrace);

    /**
     * 修改在制批次跟踪
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 结果
     */
    public int updateExeMlTrace(ExeMlTrace exeMlTrace);

    /**
     * 删除在制批次跟踪
     * 
     * @param id 在制批次跟踪主键
     * @return 结果
     */
    public int deleteExeMlTraceById(Long id);

    /**
     * 批量删除在制批次跟踪
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlTraceByIds(Long[] ids);

    /**
     * 查询在制批次跟踪列表
     *
     * @param productId 产品编码
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByProductId(Long productId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param currentProcessId 现工序码
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByCurrentProcessId(Long currentProcessId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param currentWorkgroupId 现班组
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param unitId 单位
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByUnitId(Long unitId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param lineId 产线
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByLineId(Long lineId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param projectId 项目
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByProjectId(Long projectId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param sourceMlId 来源批次
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceBySourceMlId(Long sourceMlId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param woId 工单
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByWoId(Long woId);

    /**
     * 查询在制批次跟踪列表
     *
     * @param dispatchingBy 派工人
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByDispatchingBy(Long dispatchingBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param acceptingBy 接收人
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByAcceptingBy(Long acceptingBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param startBy 开工人
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByStartBy(Long startBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param finishBy 完工人
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByFinishBy(Long finishBy);

    /**
     * 查询在制批次跟踪列表
     *
     * @param stockinBy 入库人
     * @return 在制批次跟踪集合
     */
    public int deleteExeMlTraceByStockinBy(Long stockinBy);
}

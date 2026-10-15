package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlSplit;

/**
 * 制造批次分拆Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ExeMlSplitMapper 
{
    /**
     * 查询制造批次分拆
     * 
     * @param id 制造批次分拆主键
     * @return 制造批次分拆
     */
    public ExeMlSplit selectExeMlSplitById(Long id);

    /**
     * 查询制造批次分拆列表
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitList(ExeMlSplit exeMlSplit);

    /**
     * 查询制造批次分拆列表
     *
     * @param productId 产品编码
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByProductId(Long productId);

    /**
     * 查询制造批次分拆列表
     *
     * @param currentProcessId 现工序码
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询制造批次分拆列表
     *
     * @param currentWorkgroupId 现班组
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询制造批次分拆列表
     *
     * @param unitId 单位
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByUnitId(Long unitId);

    /**
     * 查询制造批次分拆列表
     *
     * @param projectId 项目
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByProjectId(Long projectId);

    /**
     * 查询制造批次分拆列表
     *
     * @param lineId 项目
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByLineId(Long lineId);

    /**
     * 查询制造批次分拆列表
     *
     * @param sourceMlId 来源批次
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListBySourceMlId(Long sourceMlId);

    /**
     * 查询制造批次分拆列表
     *
     * @param woId 工单
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByWoId(Long woId);

    /**
     * 查询制造批次分拆列表
     *
     * @param dispatchingBy 派工人
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param acceptingBy 接收人
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByAcceptingBy(Long acceptingBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param startBy 开工人
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByStartBy(Long startBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param finishBy 完工人
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByFinishBy(Long finishBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param stockinBy 入库人
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitListByStockinBy(Long stockinBy);

    /**
     * 新增制造批次分拆
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 结果
     */
    public int insertExeMlSplit(ExeMlSplit exeMlSplit);

    /**
     * 修改制造批次分拆
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 结果
     */
    public int updateExeMlSplit(ExeMlSplit exeMlSplit);

    /**
     * 删除制造批次分拆
     * 
     * @param id 制造批次分拆主键
     * @return 结果
     */
    public int deleteExeMlSplitById(Long id);

    /**
     * 批量删除制造批次分拆
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlSplitByIds(Long[] ids);

    /**
     * 查询制造批次分拆列表
     *
     * @param productId 产品编码
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByProductId(Long productId);

    /**
     * 查询制造批次分拆列表
     *
     * @param currentProcessId 现工序码
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByCurrentProcessId(Long currentProcessId);

    /**
     * 查询制造批次分拆列表
     *
     * @param currentWorkgroupId 现班组
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询制造批次分拆列表
     *
     * @param unitId 单位
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByUnitId(Long unitId);

    /**
     * 查询制造批次分拆列表
     *
     * @param projectId 项目
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByProjectId(Long projectId);

    /**
     * 查询制造批次分拆列表
     *
     * @param lineId 项目
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByLineId(Long lineId);

    /**
     * 查询制造批次分拆列表
     *
     * @param sourceMlId 来源批次
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitBySourceMlId(Long sourceMlId);

    /**
     * 查询制造批次分拆列表
     *
     * @param woId 工单
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByWoId(Long woId);

    /**
     * 查询制造批次分拆列表
     *
     * @param dispatchingBy 派工人
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByDispatchingBy(Long dispatchingBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param acceptingBy 接收人
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByAcceptingBy(Long acceptingBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param startBy 开工人
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByStartBy(Long startBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param finishBy 完工人
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByFinishBy(Long finishBy);

    /**
     * 查询制造批次分拆列表
     *
     * @param stockinBy 入库人
     * @return 制造批次分拆集合
     */
    public int deleteExeMlSplitByStockinBy(Long stockinBy);
}

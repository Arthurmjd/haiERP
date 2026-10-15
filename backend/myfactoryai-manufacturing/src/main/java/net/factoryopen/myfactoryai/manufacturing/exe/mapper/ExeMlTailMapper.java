package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTail;

/**
 * 制造批次尾情Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface ExeMlTailMapper 
{
    /**
     * 查询制造批次尾情
     * 
     * @param id 制造批次尾情主键
     * @return 制造批次尾情
     */
    public ExeMlTail selectExeMlTailById(Long id);

    /**
     * 查询制造批次尾情列表
     * 
     * @param exeMlTail 制造批次尾情
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailList(ExeMlTail exeMlTail);

    /**
     * 查询制造批次尾情列表
     *
     * @param productId 产品编码
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByProductId(Long productId);

    /**
     * 查询制造批次尾情列表
     *
     * @param currentProcessId 现工序码
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询制造批次尾情列表
     *
     * @param currentWorkgroupId 现班组
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询制造批次尾情列表
     *
     * @param unitId 单位
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByUnitId(Long unitId);

    /**
     * 查询制造批次尾情列表
     *
     * @param lineId 产线
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByLineId(Long lineId);

    /**
     * 查询制造批次尾情列表
     *
     * @param projectId 项目
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByProjectId(Long projectId);

    /**
     * 查询制造批次尾情列表
     *
     * @param sourceMlId 来源批次
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListBySourceMlId(Long sourceMlId);

    /**
     * 查询制造批次尾情列表
     *
     * @param woId 工单
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByWoId(Long woId);

    /**
     * 查询制造批次尾情列表
     *
     * @param dispatchingBy 派工人
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param acceptingBy 接收人
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByAcceptingBy(Long acceptingBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param startBy 开工人
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByStartBy(Long startBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param finishBy 完工人
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByFinishBy(Long finishBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param stockinBy 入库人
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailListByStockinBy(Long stockinBy);

    /**
     * 新增制造批次尾情
     * 
     * @param exeMlTail 制造批次尾情
     * @return 结果
     */
    public int insertExeMlTail(ExeMlTail exeMlTail);

    /**
     * 修改制造批次尾情
     * 
     * @param exeMlTail 制造批次尾情
     * @return 结果
     */
    public int updateExeMlTail(ExeMlTail exeMlTail);

    /**
     * 删除制造批次尾情
     * 
     * @param id 制造批次尾情主键
     * @return 结果
     */
    public int deleteExeMlTailById(Long id);

    /**
     * 批量删除制造批次尾情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlTailByIds(Long[] ids);

    /**
     * 查询制造批次尾情列表
     *
     * @param productId 产品编码
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByProductId(Long productId);

    /**
     * 查询制造批次尾情列表
     *
     * @param currentProcessId 现工序码
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByCurrentProcessId(Long currentProcessId);

    /**
     * 查询制造批次尾情列表
     *
     * @param currentWorkgroupId 现班组
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询制造批次尾情列表
     *
     * @param unitId 单位
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByUnitId(Long unitId);

    /**
     * 查询制造批次尾情列表
     *
     * @param lineId 产线
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByLineId(Long lineId);

    /**
     * 查询制造批次尾情列表
     *
     * @param projectId 项目
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByProjectId(Long projectId);

    /**
     * 查询制造批次尾情列表
     *
     * @param sourceMlId 来源批次
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailBySourceMlId(Long sourceMlId);

    /**
     * 查询制造批次尾情列表
     *
     * @param woId 工单
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByWoId(Long woId);

    /**
     * 查询制造批次尾情列表
     *
     * @param dispatchingBy 派工人
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByDispatchingBy(Long dispatchingBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param acceptingBy 接收人
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByAcceptingBy(Long acceptingBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param startBy 开工人
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByStartBy(Long startBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param finishBy 完工人
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByFinishBy(Long finishBy);

    /**
     * 查询制造批次尾情列表
     *
     * @param stockinBy 入库人
     * @return 制造批次尾情集合
     */
    public int deleteExeMlTailByStockinBy(Long stockinBy);
}

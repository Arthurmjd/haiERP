package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlOperating;
import org.springframework.stereotype.Service;

/**
 * 车间班组作业Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public interface ExeMlOperatingMapper 
{
    /**
     * 查询车间班组作业
     * 
     * @param id 车间班组作业主键
     * @return 车间班组作业
     */
    public ExeMlOperating selectExeMlOperatingById(Long id);

    /**
     * 查询车间班组作业列表
     * 
     * @param exeMlOperating 车间班组作业
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingList(ExeMlOperating exeMlOperating);

    /**
     * 查询车间班组作业列表
     *
     * @param exeMlOperating 车间班组作业
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByOperator(ExeMlOperating exeMlOperating);

    /**
     * 查询车间班组作业列表
     *
     * @param productId 产品编码
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByProductId(Long productId);

    /**
     * 查询车间班组作业列表
     *
     * @param currentProcessId 现工序码
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询车间班组作业列表
     *
     * @param currentWorkgroupId 现班组
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询车间班组作业列表
     *
     * @param unitId 单位
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByUnitId(Long unitId);

    /**
     * 查询车间班组作业列表
     *
     * @param lineId 产线
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByLineId(Long lineId);

    /**
     * 查询车间班组作业列表
     *
     * @param projectId 项目
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByProjectId(Long projectId);

    /**
     * 查询车间班组作业列表
     *
     * @param sourceMlId 来源批次
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListBySourceMlId(Long sourceMlId);

    /**
     * 查询车间班组作业列表
     *
     * @param woId 工单
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByWoId(Long woId);

    /**
     * 查询车间班组作业列表
     *
     * @param dispatchingBy 派工人
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询车间班组作业列表
     *
     * @param acceptingBy 接收人
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByAcceptingBy(Long acceptingBy);

    /**
     * 查询车间班组作业列表
     *
     * @param startBy 开工人
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByStartBy(Long startBy);

    /**
     * 查询车间班组作业列表
     *
     * @param finishBy 完工人
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByFinishBy(Long finishBy);

    /**
     * 查询车间班组作业列表
     *
     * @param stockinBy 入库人
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByStockinBy(Long stockinBy);

    /**
     * 新增车间班组作业
     * 
     * @param exeMlOperating 车间班组作业
     * @return 结果
     */
    public int insertExeMlOperating(ExeMlOperating exeMlOperating);

    /**
     * 修改车间班组作业
     * 
     * @param exeMlOperating 车间班组作业
     * @return 结果
     */
    public int updateExeMlOperating(ExeMlOperating exeMlOperating);

    /**
     * 删除车间班组作业
     * 
     * @param id 车间班组作业主键
     * @return 结果
     */
    public int deleteExeMlOperatingById(Long id);

    /**
     * 批量删除车间班组作业
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlOperatingByIds(Long[] ids);

    /**
     * 查询车间班组作业列表
     *
     * @param productId 产品编码
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByProductId(Long productId);

    /**
     * 查询车间班组作业列表
     *
     * @param currentProcessId 现工序码
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByCurrentProcessId(Long currentProcessId);

    /**
     * 查询车间班组作业列表
     *
     * @param currentWorkgroupId 现班组
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询车间班组作业列表
     *
     * @param unitId 单位
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByUnitId(Long unitId);

    /**
     * 查询车间班组作业列表
     *
     * @param lineId 产线
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByLineId(Long lineId);

    /**
     * 查询车间班组作业列表
     *
     * @param projectId 项目
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByProjectId(Long projectId);

    /**
     * 查询车间班组作业列表
     *
     * @param sourceMlId 来源批次
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingBySourceMlId(Long sourceMlId);

    /**
     * 查询车间班组作业列表
     *
     * @param woId 工单
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByWoId(Long woId);

    /**
     * 查询车间班组作业列表
     *
     * @param dispatchingBy 派工人
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByDispatchingBy(Long dispatchingBy);

    /**
     * 查询车间班组作业列表
     *
     * @param acceptingBy 接收人
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByAcceptingBy(Long acceptingBy);

    /**
     * 查询车间班组作业列表
     *
     * @param startBy 开工人
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByStartBy(Long startBy);

    /**
     * 查询车间班组作业列表
     *
     * @param finishBy 完工人
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByFinishBy(Long finishBy);

    /**
     * 查询车间班组作业列表
     *
     * @param stockinBy 入库人
     * @return 车间班组作业集合
     */
    public int deleteExeMlOperatingByStockinBy(Long stockinBy);
}

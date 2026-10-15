package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlStockin;

/**
 * 产成品入库Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ExeMlStockinMapper 
{
    /**
     * 查询产成品入库
     * 
     * @param id 产成品入库主键
     * @return 产成品入库
     */
    public ExeMlStockin selectExeMlStockinById(Long id);

    /**
     * 查询产成品入库列表
     * 
     * @param exeMlStockin 产成品入库
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinList(ExeMlStockin exeMlStockin);

    /**
     * 查询产成品入库列表
     *
     * @param productId 产品编码
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByProductId(Long productId);

    /**
     * 查询产成品入库列表
     *
     * @param currentProcessId 现工序码
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询产成品入库列表
     *
     * @param currentWorkgroupId 现班组
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询产成品入库列表
     *
     * @param unitId 单位
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByUnitId(Long unitId);

    /**
     * 查询产成品入库列表
     *
     * @param lineId 产线
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByLineId(Long lineId);

    /**
     * 查询产成品入库列表
     *
     * @param projectId 项目
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByProjectId(Long projectId);

    /**
     * 查询产成品入库列表
     *
     * @param sourceMlId 来源批次
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListBySourceMlId(Long sourceMlId);

    /**
     * 查询产成品入库列表
     *
     * @param woId 工单
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByWoId(Long woId);

    /**
     * 查询产成品入库列表
     *
     * @param dispatchingBy 派工人
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByDispatchingBy(Long dispatchingBy);

    /**
     * 查询产成品入库列表
     *
     * @param acceptingBy 接收人
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByAcceptingBy(Long acceptingBy);

    /**
     * 查询产成品入库列表
     *
     * @param startBy 开工人
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByStartBy(Long startBy);

    /**
     * 查询产成品入库列表
     *
     * @param finishBy 完工人
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByFinishBy(Long finishBy);

    /**
     * 查询产成品入库列表
     *
     * @param stockinBy 入库人
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinListByStockinBy(Long stockinBy);

    /**
     * 新增产成品入库
     * 
     * @param exeMlStockin 产成品入库
     * @return 结果
     */
    public int insertExeMlStockin(ExeMlStockin exeMlStockin);

    /**
     * 修改产成品入库
     * 
     * @param exeMlStockin 产成品入库
     * @return 结果
     */
    public int updateExeMlStockin(ExeMlStockin exeMlStockin);

    /**
     * 删除产成品入库
     * 
     * @param id 产成品入库主键
     * @return 结果
     */
    public int deleteExeMlStockinById(Long id);

    /**
     * 批量删除产成品入库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeMlStockinByIds(Long[] ids);

    /**
     * 查询产成品入库列表
     *
     * @param productId 产品编码
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByProductId(Long productId);

    /**
     * 查询产成品入库列表
     *
     * @param currentProcessId 现工序码
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByCurrentProcessId(Long currentProcessId);

    /**
     * 查询产成品入库列表
     *
     * @param currentWorkgroupId 现班组
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询产成品入库列表
     *
     * @param unitId 单位
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByUnitId(Long unitId);

    /**
     * 查询产成品入库列表
     *
     * @param lineId 产线
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByLineId(Long lineId);

    /**
     * 查询产成品入库列表
     *
     * @param projectId 项目
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByProjectId(Long projectId);

    /**
     * 查询产成品入库列表
     *
     * @param sourceMlId 来源批次
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinBySourceMlId(Long sourceMlId);

    /**
     * 查询产成品入库列表
     *
     * @param woId 工单
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByWoId(Long woId);

    /**
     * 查询产成品入库列表
     *
     * @param dispatchingBy 派工人
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByDispatchingBy(Long dispatchingBy);

    /**
     * 查询产成品入库列表
     *
     * @param acceptingBy 接收人
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByAcceptingBy(Long acceptingBy);

    /**
     * 查询产成品入库列表
     *
     * @param startBy 开工人
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByStartBy(Long startBy);

    /**
     * 查询产成品入库列表
     *
     * @param finishBy 完工人
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByFinishBy(Long finishBy);

    /**
     * 查询产成品入库列表
     *
     * @param stockinBy 入库人
     * @return 产成品入库集合
     */
    public int deleteExeMlStockinByStockinBy(Long stockinBy);
}

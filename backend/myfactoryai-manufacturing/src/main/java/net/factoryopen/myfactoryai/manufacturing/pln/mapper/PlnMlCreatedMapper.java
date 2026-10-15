package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlCreated;

/**
 * 新派批次Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface PlnMlCreatedMapper 
{
    /**
     * 查询新派批次
     * 
     * @param id 新派批次主键
     * @return 新派批次
     */
    public PlnMlCreated selectPlnMlCreatedById(Long id);

    /**
     * 查询新派批次列表
     * 
     * @param plnMlCreated 新派批次
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedList(PlnMlCreated plnMlCreated);

    /**
     * 查询新派批次列表
     *
     * @param productId 产品编码
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByProductId(Long productId);

    /**
     * 查询新派批次列表
     *
     * @param currentProcessId 现工序码
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询新派批次列表
     *
     * @param currentWorkgroupId 现班组
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询新派批次列表
     *
     * @param unitId 单位
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByUnitId(Long unitId);

    /**
     * 查询新派批次列表
     *
     * @param lineId 产线
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByLineId(Long lineId);

    /**
     * 查询新派批次列表
     *
     * @param projectId 项目
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByProjectId(Long projectId);

    /**
     * 查询新派批次列表
     *
     * @param sourceMlId 来源批次
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListBySourceMlId(Long sourceMlId);

    /**
     * 查询新派批次列表
     *
     * @param woId 工单
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByWoId(Long woId);

    /**
     * 查询新派批次列表
     *
     * @param startBy 开工人
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByStartBy(Long startBy);

    /**
     * 查询新派批次列表
     *
     * @param finishBy 完工人
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedListByFinishBy(Long finishBy);

    /**
     * 新增新派批次
     * 
     * @param plnMlCreated 新派批次
     * @return 结果
     */
    public int insertPlnMlCreated(PlnMlCreated plnMlCreated);

    /**
     * 修改新派批次
     * 
     * @param plnMlCreated 新派批次
     * @return 结果
     */
    public int updatePlnMlCreated(PlnMlCreated plnMlCreated);

    /**
     * 删除新派批次
     * 
     * @param id 新派批次主键
     * @return 结果
     */
    public int deletePlnMlCreatedById(Long id);

    /**
     * 批量删除新派批次
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlCreatedByIds(Long[] ids);

    /**
     * 查询新派批次列表
     *
     * @param productId 产品编码
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByProductId(Long productId);

    /**
     * 查询新派批次列表
     *
     * @param currentProcessId 现工序码
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByCurrentProcessId(Long currentProcessId);

    /**
     * 查询新派批次列表
     *
     * @param currentWorkgroupId 现班组
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询新派批次列表
     *
     * @param unitId 单位
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByUnitId(Long unitId);

    /**
     * 查询新派批次列表
     *
     * @param lineId 产线
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByLineId(Long lineId);

    /**
     * 查询新派批次列表
     *
     * @param projectId 项目
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByProjectId(Long projectId);

    /**
     * 查询新派批次列表
     *
     * @param sourceMlId 来源批次
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedBySourceMlId(Long sourceMlId);

    /**
     * 查询新派批次列表
     *
     * @param woId 工单
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByWoId(Long woId);

    /**
     * 查询新派批次列表
     *
     * @param startBy 开工人
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByStartBy(Long startBy);

    /**
     * 查询新派批次列表
     *
     * @param finishBy 完工人
     * @return 新派批次集合
     */
    public int deletePlnMlCreatedByFinishBy(Long finishBy);
}

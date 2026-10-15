package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import org.springframework.stereotype.Service;

/**
 * 批次管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public interface PlnMlMapper 
{
    /**
     * 查询批次管理
     * 
     * @param id 批次管理主键
     * @return 批次管理
     */
    public PlnMl selectPlnMlById(Long id);

    /**
     * 查询批次管理列表
     * 
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlDraftList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlPublishingList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlProcessingList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlFinishingList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlHistoryList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlAllList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param productId 产品编码
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByProductId(Long productId);

    /**
     * 查询批次管理列表
     *
     * @param currentProcessId 现工序码
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByCurrentProcessId(Long currentProcessId);

    /**
     * 查询批次管理列表
     *
     * @param currentWorkgroupId 现班组
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询批次管理列表
     *
     * @param unitId 单位
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByUnitId(Long unitId);

    /**
     * 查询批次管理列表
     *
     * @param lineId 产线
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByLineId(Long lineId);

    /**
     * 查询批次管理列表
     *
     * @param projectId 项目
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByProjectId(Long projectId);

    /**
     * 查询批次管理列表
     *
     * @param sourceMlId 来源批次
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListBySourceMlId(Long sourceMlId);

    /**
     * 查询批次管理列表
     *
     * @param woId 工单
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByWoId(Long woId);

    /**
     * 查询批次管理列表
     *
     * @param startBy 开工人
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByStartBy(Long startBy);

    /**
     * 查询批次管理列表
     *
     * @param finishBy 完工人
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlListByFinishBy(Long finishBy);

    /**
     * 新增批次管理
     * 
     * @param plnMl 批次管理
     * @return 结果
     */
    public int insertPlnMl(PlnMl plnMl);

    /**
     * 修改批次管理
     * 
     * @param plnMl 批次管理
     * @return 结果
     */
    public int updatePlnMl(PlnMl plnMl);

    /**
     * 删除批次管理
     * 
     * @param id 批次管理主键
     * @return 结果
     */
    public int deletePlnMlById(Long id);

    /**
     * 批量删除批次管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlByIds(Long[] ids);

    /**
     * 查询批次管理列表
     *
     * @param productId 产品编码
     * @return 批次管理集合
     */
    public int deletePlnMlByProductId(Long productId);

    /**
     * 查询批次管理列表
     *
     * @param currentProcessId 现工序码
     * @return 批次管理集合
     */
    public int deletePlnMlByCurrentProcessId(Long currentProcessId);

    /**
     * 查询批次管理列表
     *
     * @param currentWorkgroupId 现班组
     * @return 批次管理集合
     */
    public int deletePlnMlByCurrentWorkgroupId(Long currentWorkgroupId);

    /**
     * 查询批次管理列表
     *
     * @param unitId 单位
     * @return 批次管理集合
     */
    public int deletePlnMlByUnitId(Long unitId);

    /**
     * 查询批次管理列表
     *
     * @param lineId 产线
     * @return 批次管理集合
     */
    public int deletePlnMlByLineId(Long lineId);

    /**
     * 查询批次管理列表
     *
     * @param projectId 项目
     * @return 批次管理集合
     */
    public int deletePlnMlByProjectId(Long projectId);

    /**
     * 查询批次管理列表
     *
     * @param sourceMlId 来源批次
     * @return 批次管理集合
     */
    public int deletePlnMlBySourceMlId(Long sourceMlId);

    /**
     * 查询批次管理列表
     *
     * @param woId 工单
     * @return 批次管理集合
     */
    public int deletePlnMlByWoId(Long woId);

    /**
     * 查询批次管理列表
     *
     * @param startBy 开工人
     * @return 批次管理集合
     */
    public int deletePlnMlByStartBy(Long startBy);

    /**
     * 查询批次管理列表
     *
     * @param finishBy 完工人
     * @return 批次管理集合
     */
    public int deletePlnMlByFinishBy(Long finishBy);
}

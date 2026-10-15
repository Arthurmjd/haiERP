package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;

/**
 * 质检请求处理Mapper接口
 * 
 * @author admin
 * @date 2024-10-06
 */
public interface QuaInspectReDealMapper 
{
    /**
     * 查询质检请求处理
     * 
     * @param id 质检请求处理主键
     * @return 质检请求处理
     */
    public QuaInspectReDeal selectQuaInspectReDealById(Long id);

    /**
     * 查询质检请求处理列表
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealList(QuaInspectReDeal quaInspectReDeal);

    /**
     * 查询质检请求处理列表
     *
     * @param projectId 项目ID
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealListByProjectId(Long projectId);

    /**
     * 查询质检请求处理列表
     *
     * @param requestorId 请检人ID
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealListByRequestorId(Long requestorId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealListBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceProcessId 来源工序ID
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealListBySourceProcessId(Long sourceProcessId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceLineId 来源产线ID
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealListBySourceLineId(Long sourceLineId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceGroupId 来源班组ID
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealListBySourceGroupId(Long sourceGroupId);

    /**
     * 新增质检请求处理
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 结果
     */
    public int insertQuaInspectReDeal(QuaInspectReDeal quaInspectReDeal);

    /**
     * 修改质检请求处理
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 结果
     */
    public int updateQuaInspectReDeal(QuaInspectReDeal quaInspectReDeal);

    /**
     * 删除质检请求处理
     * 
     * @param id 质检请求处理主键
     * @return 结果
     */
    public int deleteQuaInspectReDealById(Long id);

    /**
     * 批量删除质检请求处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaInspectReDealByIds(Long[] ids);

    /**
     * 查询质检请求处理列表
     *
     * @param projectId 项目ID
     * @return 质检请求处理集合
     */
    public int deleteQuaInspectReDealByProjectId(Long projectId);

    /**
     * 查询质检请求处理列表
     *
     * @param requestorId 请检人ID
     * @return 质检请求处理集合
     */
    public int deleteQuaInspectReDealByRequestorId(Long requestorId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 质检请求处理集合
     */
    public int deleteQuaInspectReDealBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceProcessId 来源工序ID
     * @return 质检请求处理集合
     */
    public int deleteQuaInspectReDealBySourceProcessId(Long sourceProcessId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceLineId 来源产线ID
     * @return 质检请求处理集合
     */
    public int deleteQuaInspectReDealBySourceLineId(Long sourceLineId);

    /**
     * 查询质检请求处理列表
     *
     * @param sourceGroupId 来源班组ID
     * @return 质检请求处理集合
     */
    public int deleteQuaInspectReDealBySourceGroupId(Long sourceGroupId);
}

package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;

/**
 * 质检请求通知Mapper接口
 * 
 * @author admin
 * @date 2024-10-06
 */
public interface QuaInspectRequestMapper 
{
    /**
     * 查询质检请求通知
     * 
     * @param id 质检请求通知主键
     * @return 质检请求通知
     */
    public QuaInspectRequest selectQuaInspectRequestById(Long id);

    /**
     * 查询质检请求通知列表
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestList(QuaInspectRequest quaInspectRequest);

    /**
     * 查询质检请求通知列表
     *
     * @param projectId 项目ID
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestListByProjectId(Long projectId);

    /**
     * 查询质检请求通知列表
     *
     * @param requestorId 请检人ID
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestListByRequestorId(Long requestorId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestListBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceProcessId 来源工序ID
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestListBySourceProcessId(Long sourceProcessId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceLineId 来源产线ID
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestListBySourceLineId(Long sourceLineId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceGroupId 来源班组ID
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestListBySourceGroupId(Long sourceGroupId);

    /**
     * 新增质检请求通知
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 结果
     */
    public int insertQuaInspectRequest(QuaInspectRequest quaInspectRequest);

    /**
     * 修改质检请求通知
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 结果
     */
    public int updateQuaInspectRequest(QuaInspectRequest quaInspectRequest);

    /**
     * 删除质检请求通知
     * 
     * @param id 质检请求通知主键
     * @return 结果
     */
    public int deleteQuaInspectRequestById(Long id);

    /**
     * 批量删除质检请求通知
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaInspectRequestByIds(Long[] ids);

    /**
     * 查询质检请求通知列表
     *
     * @param projectId 项目ID
     * @return 质检请求通知集合
     */
    public int deleteQuaInspectRequestByProjectId(Long projectId);

    /**
     * 查询质检请求通知列表
     *
     * @param requestorId 请检人ID
     * @return 质检请求通知集合
     */
    public int deleteQuaInspectRequestByRequestorId(Long requestorId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceSupplierId 来源厂商ID
     * @return 质检请求通知集合
     */
    public int deleteQuaInspectRequestBySourceSupplierId(Long sourceSupplierId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceProcessId 来源工序ID
     * @return 质检请求通知集合
     */
    public int deleteQuaInspectRequestBySourceProcessId(Long sourceProcessId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceLineId 来源产线ID
     * @return 质检请求通知集合
     */
    public int deleteQuaInspectRequestBySourceLineId(Long sourceLineId);

    /**
     * 查询质检请求通知列表
     *
     * @param sourceGroupId 来源班组ID
     * @return 质检请求通知集合
     */
    public int deleteQuaInspectRequestBySourceGroupId(Long sourceGroupId);
}

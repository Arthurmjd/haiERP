package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import org.springframework.stereotype.Service;

/**
 * 主计划维护Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public interface DelMpsMapper 
{
    /**
     * 查询主计划维护
     * 
     * @param id 主计划维护主键
     * @return 主计划维护
     */
    public DelMps selectDelMpsById(Long id);

    /**
     * 查询主计划维护列表
     * 
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsDraftList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsPublishingList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsProcessingList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsFinishingList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsHistoryList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param delMps 主计划维护
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsAllList(DelMps delMps);

    /**
     * 查询主计划维护列表
     *
     * @param soId 销售订单
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsListBySoId(Long soId);

    /**
     * 查询主计划维护列表
     *
     * @param soItemId 订单项
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsListBySoItemId(Long soItemId);

    /**
     * 查询主计划维护列表
     *
     * @param customerId 客户ID
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsListByCustomerId(Long customerId);

    /**
     * 查询主计划维护列表
     *
     * @param projectId 项目
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsListByProjectId(Long projectId);

    /**
     * 查询主计划维护列表
     *
     * @param salesId 销售员
     * @return 主计划维护集合
     */
    public List<DelMps> selectDelMpsListBySalesId(Long salesId);

    /**
     * 新增主计划维护
     * 
     * @param delMps 主计划维护
     * @return 结果
     */
    public int insertDelMps(DelMps delMps);

    /**
     * 修改主计划维护
     * 
     * @param delMps 主计划维护
     * @return 结果
     */
    public int updateDelMps(DelMps delMps);

    /**
     * 删除主计划维护
     * 
     * @param id 主计划维护主键
     * @return 结果
     */
    public int deleteDelMpsById(Long id);

    /**
     * 批量删除主计划维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelMpsByIds(Long[] ids);

    /**
     * 查询主计划维护列表
     *
     * @param soId 销售订单
     * @return 主计划维护集合
     */
    public int deleteDelMpsBySoId(Long soId);

    /**
     * 查询主计划维护列表
     *
     * @param soItemId 订单项
     * @return 主计划维护集合
     */
    public int deleteDelMpsBySoItemId(Long soItemId);

    /**
     * 查询主计划维护列表
     *
     * @param customerId 客户ID
     * @return 主计划维护集合
     */
    public int deleteDelMpsByCustomerId(Long customerId);

    /**
     * 查询主计划维护列表
     *
     * @param projectId 项目
     * @return 主计划维护集合
     */
    public int deleteDelMpsByProjectId(Long projectId);

    /**
     * 查询主计划维护列表
     *
     * @param salesId 销售员
     * @return 主计划维护集合
     */
    public int deleteDelMpsBySalesId(Long salesId);
}

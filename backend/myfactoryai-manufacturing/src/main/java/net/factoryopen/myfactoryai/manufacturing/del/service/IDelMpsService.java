package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 主计划维护Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelMpsService 
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
     * 批量删除主计划维护
     * 
     * @param ids 需要删除的主计划维护主键集合
     * @return 结果
     */
    public int deleteDelMpsByIds(Long[] ids);

    /**
     * 删除主计划维护信息
     * 
     * @param id 主计划维护主键
     * @return 结果
     */
    public int deleteDelMpsById(Long id);

    /**
     * 投用
     *
     * @param delMps mps
     * @return 结果
     */
    public int enable(DelMps delMps);

    /**
     * 变更
     *
     * @param delMps 变更
     * @return 结果
     */
    public int changeDelMps(DelMps delMps);

    /**
     * 冻结
     *
     * @param delMps 冻结
     * @return 结果
     */
    public int froze(DelMps delMps);

    /**
     * 解冻
     *
     * @param delMps 解冻
     * @return 结果
     */
    public int unfroze(DelMps delMps);

    /**
     * 停用
     *
     * @param delMps 停用
     * @return 结果
     */
    public int disable(DelMps delMps);

    /**
     * 通知工艺
     *
     * @param delMps 主计划项
     * @return 结果
     */
    public int noticeEngineering(DelMps delMps) throws Exception;

    /**
     * 转正料号
     *
     * @param delMps 主计划项
     * @return 结果
     */
    public int normalizeCode(DelMps delMps) throws Exception;
}

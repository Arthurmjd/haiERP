package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;

/**
 * 外协计划维护Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelOsplanService 
{
    /**
     * 查询外协计划维护
     * 
     * @param id 外协计划维护主键
     * @return 外协计划维护
     */
    public DelOsplan selectDelOsplanById(Long id);

    /**
     * 查询外协计划维护列表
     * 
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanList(DelOsplan delOsplan);

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanDraftList(DelOsplan delOsplan);

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanPublishingList(DelOsplan delOsplan);

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanProcessingList(DelOsplan delOsplan);

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanFinishingList(DelOsplan delOsplan);

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanHistoryList(DelOsplan delOsplan);

    /**
     * 查询外协计划维护列表
     *
     * @param delOsplan 外协计划维护
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanAllList(DelOsplan delOsplan);

    /**
     * 新增外协计划维护
     * 
     * @param delOsplan 外协计划维护
     * @return 结果
     */
    public int insertDelOsplan(DelOsplan delOsplan);

    /**
     * 修改外协计划维护
     * 
     * @param delOsplan 外协计划维护
     * @return 结果
     */
    public int updateDelOsplan(DelOsplan delOsplan);

    /**
     * 批量删除外协计划维护
     * 
     * @param ids 需要删除的外协计划维护主键集合
     * @return 结果
     */
    public int deleteDelOsplanByIds(Long[] ids);

    /**
     * 删除外协计划维护信息
     * 
     * @param id 外协计划维护主键
     * @return 结果
     */
    public int deleteDelOsplanById(Long id);

    /**
     * 变更
     *
     * @param delOsplan 变更
     * @return 结果
     */
    public int changeDelOsplan(DelOsplan delOsplan);

    /**
     * 冻结
     *
     * @param delOsplan 冻结
     * @return 结果
     */
    public int froze(DelOsplan delOsplan);

    /**
     * 解冻
     *
     * @param delOsplan 解冻
     * @return 结果
     */
    public int unfroze(DelOsplan delOsplan);

    /**
     * 停用
     *
     * @param delOsplan 停用
     * @return 结果
     */
    public int disable(DelOsplan delOsplan);

    /**
     * 强结
     *
     * @param delOsplan 强结
     * @return 结果
     */
    public int finishforcely(DelOsplan delOsplan);

    /**
     * 收货
     *
     * @param ids id集合
     * @return 结果
     */
    public int toPr(Long[] ids) throws Exception;


}

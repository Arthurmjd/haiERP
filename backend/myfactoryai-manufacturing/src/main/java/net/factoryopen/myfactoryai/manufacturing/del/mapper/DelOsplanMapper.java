package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import org.springframework.stereotype.Service;

/**
 * 外协计划维护Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public interface DelOsplanMapper 
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
     * 查询外协计划维护列表
     *
     * @param soId 销售订单
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanListBySoId(Long soId);

    /**
     * 查询外协计划维护列表
     *
     * @param soItemId 订单项
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanListBySoItemId(Long soItemId);

    /**
     * 查询外协计划维护列表
     *
     * @param customerId 客户ID
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanListByCustomerId(Long customerId);

    /**
     * 查询外协计划维护列表
     *
     * @param projectId 项目
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanListByProjectId(Long projectId);

    /**
     * 查询外协计划维护列表
     *
     * @param salesId 销售员
     * @return 外协计划维护集合
     */
    public List<DelOsplan> selectDelOsplanListBySalesId(Long salesId);

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
     * 删除外协计划维护
     * 
     * @param id 外协计划维护主键
     * @return 结果
     */
    public int deleteDelOsplanById(Long id);

    /**
     * 批量删除外协计划维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelOsplanByIds(Long[] ids);

    /**
     * 查询外协计划维护列表
     *
     * @param soId 销售订单
     * @return 外协计划维护集合
     */
    public int deleteDelOsplanBySoId(Long soId);

    /**
     * 查询外协计划维护列表
     *
     * @param soItemId 订单项
     * @return 外协计划维护集合
     */
    public int deleteDelOsplanBySoItemId(Long soItemId);

    /**
     * 查询外协计划维护列表
     *
     * @param customerId 客户ID
     * @return 外协计划维护集合
     */
    public int deleteDelOsplanByCustomerId(Long customerId);

    /**
     * 查询外协计划维护列表
     *
     * @param projectId 项目
     * @return 外协计划维护集合
     */
    public int deleteDelOsplanByProjectId(Long projectId);

    /**
     * 查询外协计划维护列表
     *
     * @param salesId 销售员
     * @return 外协计划维护集合
     */
    public int deleteDelOsplanBySalesId(Long salesId);
}

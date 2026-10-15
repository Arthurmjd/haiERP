package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;

/**
 * 交货筹划Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IDelDeliveryDealService 
{
    /**
     * 查询交货筹划
     * 
     * @param id 交货筹划主键
     * @return 交货筹划
     */
    public DelDeliveryDeal selectDelDeliveryDealById(Long id);

    /**
     * 查询交货筹划列表
     * 
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealDraftList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealPublishingList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealPublishedList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealProcessingList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealFinishingList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealHistoryList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 查询交货筹划列表
     *
     * @param delDeliveryDeal 交货筹划
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealAllList(DelDeliveryDeal delDeliveryDeal);

    /**
     * 新增交货筹划
     * 
     * @param delDeliveryDeal 交货筹划
     * @return 结果
     */
    public int insertDelDeliveryDeal(DelDeliveryDeal delDeliveryDeal);

    /**
     * 修改交货筹划
     * 
     * @param delDeliveryDeal 交货筹划
     * @return 结果
     */
    public int updateDelDeliveryDeal(DelDeliveryDeal delDeliveryDeal);

    /**
     * 批量删除交货筹划
     * 
     * @param ids 需要删除的交货筹划主键集合
     * @return 结果
     */
    public int deleteDelDeliveryDealByIds(Long[] ids);

    /**
     * 删除交货筹划信息
     * 
     * @param id 交货筹划主键
     * @return 结果
     */
    public int deleteDelDeliveryDealById(Long id);

    /**
     * 发货
     *
     * @param delDeliveryDeal 发货
     * @return 结果
     */
    public int planDelivery(DelDeliveryDeal delDeliveryDeal) throws Exception;

    /**
     * 投产
     *
     * @param delDeliveryDeal 投产
     * @return 结果
     */
    public int planMps(DelDeliveryDeal delDeliveryDeal) throws Exception;

    /**
     * 外协
     *
     * @param delDeliveryDeal 外协
     * @return 结果
     */
    public int planOs(DelDeliveryDeal delDeliveryDeal) throws Exception;

    /**
     * 转改料号
     *
     * @param delDeliveryDeal 订单明细
     * @return 结果
     */
    public int normalizeCode(DelDeliveryDeal delDeliveryDeal) throws Exception;


}

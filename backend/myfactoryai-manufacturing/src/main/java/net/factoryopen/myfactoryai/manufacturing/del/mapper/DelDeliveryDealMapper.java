package net.factoryopen.myfactoryai.manufacturing.del.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import org.springframework.stereotype.Service;

/**
 * 交货筹划Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public interface DelDeliveryDealMapper 
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
     * 查询交货筹划列表
     *
     * @param soId 销售订单
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealListBySoId(Long soId);

    /**
     * 查询交货筹划列表
     *
     * @param productId 产品编码
     * @return 交货筹划集合
     */
    public List<DelDeliveryDeal> selectDelDeliveryDealListByProductId(Long productId);

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
     * 删除交货筹划
     * 
     * @param id 交货筹划主键
     * @return 结果
     */
    public int deleteDelDeliveryDealById(Long id);

    /**
     * 批量删除交货筹划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDelDeliveryDealByIds(Long[] ids);

    /**
     * 查询交货筹划列表
     *
     * @param soId 销售订单
     * @return 交货筹划集合
     */
    public int deleteDelDeliveryDealBySoId(Long soId);

    /**
     * 查询交货筹划列表
     *
     * @param productId 产品编码
     * @return 交货筹划集合
     */
    public int deleteDelDeliveryDealByProductId(Long productId);
}

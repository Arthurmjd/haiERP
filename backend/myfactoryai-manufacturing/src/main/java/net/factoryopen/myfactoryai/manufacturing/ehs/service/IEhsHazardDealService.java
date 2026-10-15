package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardDeal;

/**
 * 安全风险处理Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsHazardDealService 
{
    /**
     * 查询安全风险处理
     * 
     * @param id 安全风险处理主键
     * @return 安全风险处理
     */
    public EhsHazardDeal selectEhsHazardDealById(Long id);

    /**
     * 查询安全风险处理列表
     * 
     * @param ehsHazardDeal 安全风险处理
     * @return 安全风险处理集合
     */
    public List<EhsHazardDeal> selectEhsHazardDealList(EhsHazardDeal ehsHazardDeal);

    /**
     * 新增安全风险处理
     * 
     * @param ehsHazardDeal 安全风险处理
     * @return 结果
     */
    public int insertEhsHazardDeal(EhsHazardDeal ehsHazardDeal);

    /**
     * 修改安全风险处理
     * 
     * @param ehsHazardDeal 安全风险处理
     * @return 结果
     */
    public int updateEhsHazardDeal(EhsHazardDeal ehsHazardDeal);

    /**
     * 批量删除安全风险处理
     * 
     * @param ids 需要删除的安全风险处理主键集合
     * @return 结果
     */
    public int deleteEhsHazardDealByIds(Long[] ids);

    /**
     * 删除安全风险处理信息
     * 
     * @param id 安全风险处理主键
     * @return 结果
     */
    public int deleteEhsHazardDealById(Long id);


}

package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardDeal;

/**
 * 安全风险处理Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsHazardDealMapper 
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
     * 查询安全风险处理列表
     *
     * @param hazardId 风险ID
     * @return 安全风险处理集合
     */
    public List<EhsHazardDeal> selectEhsHazardDealListByHazardId(Long hazardId);

    /**
     * 查询安全风险处理列表
     *
     * @param deptId 部门ID
     * @return 安全风险处理集合
     */
    public List<EhsHazardDeal> selectEhsHazardDealListByDeptId(Long deptId);

    /**
     * 查询安全风险处理列表
     *
     * @param employeeId 责任人ID
     * @return 安全风险处理集合
     */
    public List<EhsHazardDeal> selectEhsHazardDealListByEmployeeId(Long employeeId);

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
     * 删除安全风险处理
     * 
     * @param id 安全风险处理主键
     * @return 结果
     */
    public int deleteEhsHazardDealById(Long id);

    /**
     * 批量删除安全风险处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsHazardDealByIds(Long[] ids);

    /**
     * 查询安全风险处理列表
     *
     * @param hazardId 风险ID
     * @return 安全风险处理集合
     */
    public int deleteEhsHazardDealByHazardId(Long hazardId);

    /**
     * 查询安全风险处理列表
     *
     * @param deptId 部门ID
     * @return 安全风险处理集合
     */
    public int deleteEhsHazardDealByDeptId(Long deptId);

    /**
     * 查询安全风险处理列表
     *
     * @param employeeId 责任人ID
     * @return 安全风险处理集合
     */
    public int deleteEhsHazardDealByEmployeeId(Long employeeId);
}

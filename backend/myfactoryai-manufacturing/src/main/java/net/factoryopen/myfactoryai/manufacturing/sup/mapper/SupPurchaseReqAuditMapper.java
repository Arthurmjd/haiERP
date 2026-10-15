package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseReqAudit;

/**
 * 请购审核Mapper接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface SupPurchaseReqAuditMapper 
{
    /**
     * 查询请购审核
     * 
     * @param id 请购审核主键
     * @return 请购审核
     */
    public SupPurchaseReqAudit selectSupPurchaseReqAuditById(Long id);

    /**
     * 查询请购审核列表
     * 
     * @param supPurchaseReqAudit 请购审核
     * @return 请购审核集合
     */
    public List<SupPurchaseReqAudit> selectSupPurchaseReqAuditList(SupPurchaseReqAudit supPurchaseReqAudit);

    /**
     * 查询请购审核列表
     *
     * @param requestorId 请购员
     * @return 请购审核集合
     */
    public List<SupPurchaseReqAudit> selectSupPurchaseReqAuditListByRequestorId(Long requestorId);

    /**
     * 查询请购审核列表
     *
     * @param deptId 请购部门
     * @return 请购审核集合
     */
    public List<SupPurchaseReqAudit> selectSupPurchaseReqAuditListByDeptId(Long deptId);

    /**
     * 新增请购审核
     * 
     * @param supPurchaseReqAudit 请购审核
     * @return 结果
     */
    public int insertSupPurchaseReqAudit(SupPurchaseReqAudit supPurchaseReqAudit);

    /**
     * 修改请购审核
     * 
     * @param supPurchaseReqAudit 请购审核
     * @return 结果
     */
    public int updateSupPurchaseReqAudit(SupPurchaseReqAudit supPurchaseReqAudit);

    /**
     * 删除请购审核
     * 
     * @param id 请购审核主键
     * @return 结果
     */
    public int deleteSupPurchaseReqAuditById(Long id);

    /**
     * 批量删除请购审核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupPurchaseReqAuditByIds(Long[] ids);

    /**
     * 查询请购审核列表
     *
     * @param requestorId 请购员
     * @return 请购审核集合
     */
    public int deleteSupPurchaseReqAuditByRequestorId(Long requestorId);

    /**
     * 查询请购审核列表
     *
     * @param deptId 请购部门
     * @return 请购审核集合
     */
    public int deleteSupPurchaseReqAuditByDeptId(Long deptId);
}

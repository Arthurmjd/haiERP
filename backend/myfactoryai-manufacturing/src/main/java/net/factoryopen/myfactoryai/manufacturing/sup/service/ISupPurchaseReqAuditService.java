package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseReqAudit;

/**
 * 请购审核Service接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface ISupPurchaseReqAuditService 
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
     * 批量删除请购审核
     * 
     * @param ids 需要删除的请购审核主键集合
     * @return 结果
     */
    public int deleteSupPurchaseReqAuditByIds(Long[] ids);

    /**
     * 删除请购审核信息
     * 
     * @param id 请购审核主键
     * @return 结果
     */
    public int deleteSupPurchaseReqAuditById(Long id);

    /**
     * 过审
     *
     * @param supPurchaseReqAudit 过审
     * @return 结果
     */
    public int auditPass(SupPurchaseReqAudit supPurchaseReqAudit);

    /**
     * 驳审
     *
     * @param supPurchaseReqAudit 驳审
     * @return 结果
     */
    public int auditReject(SupPurchaseReqAudit supPurchaseReqAudit);

    /**
     * 转计划
     *
     * @param ids id集合
     * @return 结果
     */
    public int toPlan(Long[] ids) throws Exception;


}

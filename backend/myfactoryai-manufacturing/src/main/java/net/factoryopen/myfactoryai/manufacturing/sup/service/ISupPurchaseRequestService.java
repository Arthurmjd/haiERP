package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;

/**
 * 采购申请Service接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface ISupPurchaseRequestService 
{
    /**
     * 查询采购申请
     * 
     * @param id 采购申请主键
     * @return 采购申请
     */
    public SupPurchaseRequest selectSupPurchaseRequestById(Long id);

    /**
     * 查询采购申请列表
     * 
     * @param supPurchaseRequest 采购申请
     * @return 采购申请集合
     */
    public List<SupPurchaseRequest> selectSupPurchaseRequestList(SupPurchaseRequest supPurchaseRequest);

    /**
     * 查询采购申请列表
     *
     * @param supPurchaseRequest 采购申请
     * @return 采购申请集合
     */
    public List<SupPurchaseRequest> selectSupPurchaseRequestList4fk(SupPurchaseRequest supPurchaseRequest);

    /**
     * 新增采购申请
     * 
     * @param supPurchaseRequest 采购申请
     * @return 结果
     */
    public int insertSupPurchaseRequest(SupPurchaseRequest supPurchaseRequest);

    /**
     * 修改采购申请
     * 
     * @param supPurchaseRequest 采购申请
     * @return 结果
     */
    public int updateSupPurchaseRequest(SupPurchaseRequest supPurchaseRequest);

    /**
     * 批量删除采购申请
     * 
     * @param ids 需要删除的采购申请主键集合
     * @return 结果
     */
    public int deleteSupPurchaseRequestByIds(Long[] ids);

    /**
     * 删除采购申请信息
     * 
     * @param id 采购申请主键
     * @return 结果
     */
    public int deleteSupPurchaseRequestById(Long id);

    /**
     * 请审
     *
     * @param supPurchaseRequest 请审
     * @return 结果
     */
    public int auditReq(SupPurchaseRequest supPurchaseRequest);


}

package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;

/**
 * 采购申请Mapper接口
 * 
 * @author admin
 * @date 2024-09-16
 */
public interface SupPurchaseRequestMapper 
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
     * 查询采购申请列表
     *
     * @param requestorId 请购员
     * @return 采购申请集合
     */
    public List<SupPurchaseRequest> selectSupPurchaseRequestListByRequestorId(Long requestorId);

    /**
     * 查询采购申请列表
     *
     * @param deptId 请购部门
     * @return 采购申请集合
     */
    public List<SupPurchaseRequest> selectSupPurchaseRequestListByDeptId(Long deptId);

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
     * 删除采购申请
     * 
     * @param id 采购申请主键
     * @return 结果
     */
    public int deleteSupPurchaseRequestById(Long id);

    /**
     * 批量删除采购申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupPurchaseRequestByIds(Long[] ids);

    /**
     * 查询采购申请列表
     *
     * @param requestorId 请购员
     * @return 采购申请集合
     */
    public int deleteSupPurchaseRequestByRequestorId(Long requestorId);

    /**
     * 查询采购申请列表
     *
     * @param deptId 请购部门
     * @return 采购申请集合
     */
    public int deleteSupPurchaseRequestByDeptId(Long deptId);
}

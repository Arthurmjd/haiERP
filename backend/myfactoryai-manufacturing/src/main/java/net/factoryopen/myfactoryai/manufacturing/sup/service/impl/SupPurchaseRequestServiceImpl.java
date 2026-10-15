package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchaseRequestService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购申请Service业务层处理
 * 
 * @author admin
 * @date 2024-09-16
 */
@Service
public class SupPurchaseRequestServiceImpl implements ISupPurchaseRequestService 
{
    @Autowired(required = false)
    private SupPurchaseRequestMapper supPurchaseRequestMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购申请
     * 
     * @param id 采购申请主键
     * @return 采购申请
     */
    @Override
    public SupPurchaseRequest selectSupPurchaseRequestById(Long id)
    {
        return supPurchaseRequestMapper.selectSupPurchaseRequestById(id);
    }

    /**
     * 查询采购申请列表
     * 
     * @param supPurchaseRequest 采购申请
     * @return 采购申请
     */
    @Override
    public List<SupPurchaseRequest> selectSupPurchaseRequestList(SupPurchaseRequest supPurchaseRequest)
    {
        return supPurchaseRequestMapper.selectSupPurchaseRequestList(supPurchaseRequest);
    }

    /**
     * 查询采购申请列表
     *
     * @param supPurchaseRequest 采购申请
     * @return 采购申请
     */
    @Override
    public List<SupPurchaseRequest> selectSupPurchaseRequestList4fk(SupPurchaseRequest supPurchaseRequest)
    {
        return supPurchaseRequestMapper.selectSupPurchaseRequestList4fk(supPurchaseRequest);
    }

    /**
     * 新增采购申请
     * 
     * @param supPurchaseRequest 采购申请
     * @return 结果
     */
    @Override
    public int insertSupPurchaseRequest(SupPurchaseRequest supPurchaseRequest)
    {
        supPurchaseRequest.refreshCreatingStamp();
        return supPurchaseRequestMapper.insertSupPurchaseRequest(supPurchaseRequest);
    }

    /**
     * 修改采购申请
     * 
     * @param supPurchaseRequest 采购申请
     * @return 结果
     */
    @Override
    public int updateSupPurchaseRequest(SupPurchaseRequest supPurchaseRequest)
    {
        supPurchaseRequest.refreshUpdatingStamp();
        return supPurchaseRequestMapper.updateSupPurchaseRequest(supPurchaseRequest);
    }

    /**
     * 批量删除采购申请
     * 
     * @param ids 需要删除的采购申请主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchaseRequestByIds(Long[] ids)
    {
        return supPurchaseRequestMapper.deleteSupPurchaseRequestByIds(ids);
    }

    /**
     * 删除采购申请信息
     * 
     * @param id 采购申请主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchaseRequestById(Long id)
    {
        return supPurchaseRequestMapper.deleteSupPurchaseRequestById(id);
    }

      /**
       * 请审
       *
       * @param supPurchaseRequest 请审
       * @return 结果
       */
      @Override
      public int auditReq(SupPurchaseRequest supPurchaseRequest){
          Long id = supPurchaseRequest.getId();
          SupPurchaseRequest obj = supPurchaseRequestMapper.selectSupPurchaseRequestById(id);

          obj.setStatus(BillStatus.BASE_AUDIT_REQUEST);
          obj.refreshUpdatingStamp();
          return supPurchaseRequestMapper.updateSupPurchaseRequest(obj);
      }

}

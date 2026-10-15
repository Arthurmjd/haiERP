package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.sup.SupPurchaseReqAuditAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseReqAuditMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseReqAudit;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchaseReqAuditService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 请购审核Service业务层处理
 * 
 * @author admin
 * @date 2024-09-16
 */
@Service
public class SupPurchaseReqAuditServiceImpl implements ISupPurchaseReqAuditService 
{
    @Autowired(required = false)
    private SupPurchaseReqAuditMapper supPurchaseReqAuditMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SupPurchaseReqAuditAlgorithm supPurchaseReqAuditAlgorithm;

    /**
     * 查询请购审核
     * 
     * @param id 请购审核主键
     * @return 请购审核
     */
    @Override
    public SupPurchaseReqAudit selectSupPurchaseReqAuditById(Long id)
    {
        return supPurchaseReqAuditMapper.selectSupPurchaseReqAuditById(id);
    }

    /**
     * 查询请购审核列表
     * 
     * @param supPurchaseReqAudit 请购审核
     * @return 请购审核
     */
    @Override
    public List<SupPurchaseReqAudit> selectSupPurchaseReqAuditList(SupPurchaseReqAudit supPurchaseReqAudit)
    {
        return supPurchaseReqAuditMapper.selectSupPurchaseReqAuditList(supPurchaseReqAudit);
    }

    /**
     * 新增请购审核
     * 
     * @param supPurchaseReqAudit 请购审核
     * @return 结果
     */
    @Override
    public int insertSupPurchaseReqAudit(SupPurchaseReqAudit supPurchaseReqAudit)
    {
        supPurchaseReqAudit.setCreateTime(DateUtils.getNowDate());
        return supPurchaseReqAuditMapper.insertSupPurchaseReqAudit(supPurchaseReqAudit);
    }

    /**
     * 修改请购审核
     * 
     * @param supPurchaseReqAudit 请购审核
     * @return 结果
     */
    @Override
    public int updateSupPurchaseReqAudit(SupPurchaseReqAudit supPurchaseReqAudit)
    {
        supPurchaseReqAudit.setUpdateTime(DateUtils.getNowDate());
        return supPurchaseReqAuditMapper.updateSupPurchaseReqAudit(supPurchaseReqAudit);
    }

    /**
     * 批量删除请购审核
     * 
     * @param ids 需要删除的请购审核主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchaseReqAuditByIds(Long[] ids)
    {
        return supPurchaseReqAuditMapper.deleteSupPurchaseReqAuditByIds(ids);
    }

    /**
     * 删除请购审核信息
     * 
     * @param id 请购审核主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchaseReqAuditById(Long id)
    {
        return supPurchaseReqAuditMapper.deleteSupPurchaseReqAuditById(id);
    }

      /**
       * 过审
       *
       * @param supPurchaseReqAudit 过审
       * @return 结果
       */
      @Override
      public int auditPass(SupPurchaseReqAudit supPurchaseReqAudit){
          Long id = supPurchaseReqAudit.getId();
          SupPurchaseReqAudit obj = supPurchaseReqAuditMapper.selectSupPurchaseReqAuditById(id);

          obj.setStatus(BillStatus.BASE_AUDIT_PASS);
          obj.refreshUpdatingStamp();
          return supPurchaseReqAuditMapper.updateSupPurchaseReqAudit(obj);
      }
      /**
       * 驳审
       *
       * @param supPurchaseReqAudit 驳审
       * @return 结果
       */
      @Override
      public int auditReject(SupPurchaseReqAudit supPurchaseReqAudit){
          Long id = supPurchaseReqAudit.getId();
          SupPurchaseReqAudit obj = supPurchaseReqAuditMapper.selectSupPurchaseReqAuditById(id);

          obj.setStatus(BillStatus.BASE_AUDIT_REJECT);
          obj.refreshUpdatingStamp();
          return supPurchaseReqAuditMapper.updateSupPurchaseReqAudit(obj);
      }
      /**
       * 转计划
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int toPlan(Long[] ids) throws Exception{
          try{
              return supPurchaseReqAuditAlgorithm.toPlan(ids);
          }catch (Exception ex){
              throw ex;
          }
      }

}

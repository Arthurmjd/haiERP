package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.sup.SupPoAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购单管理Service业务层处理
 * 
 * @author admin
 * @date 2024-09-23
 */
@Service
public class SupPoServiceImpl implements ISupPoService 
{
    @Autowired(required = false)
    private SupPoMapper poMapper;

    @Autowired(required = false)
    private SupPoItemMapper itemMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SupPoAlgorithm supPoAlgorithm;

    /**
     * 查询采购单管理
     * 
     * @param id 采购单管理主键
     * @return 采购单管理
     */
    @Override
    public SupPo selectSupPoById(Long id)
    {
        return poMapper.selectSupPoById(id);
    }

    /**
     * 查询采购单管理列表
     * 
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoList(SupPo supPo)
    {
        return poMapper.selectSupPoList(supPo);
    }

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoDraftList(SupPo supPo)
    {
        return poMapper.selectSupPoDraftList(supPo);
    }

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoPublishingList(SupPo supPo)
    {
        return poMapper.selectSupPoPublishingList(supPo);
    }

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoProcessingList(SupPo supPo)
    {
        return poMapper.selectSupPoProcessingList(supPo);
    }

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoFinishingList(SupPo supPo)
    {
        return poMapper.selectSupPoFinishingList(supPo);
    }

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoHistoryList(SupPo supPo)
    {
        return poMapper.selectSupPoHistoryList(supPo);
    }

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理
     */
    @Override
    public List<SupPo> selectSupPoAllList(SupPo supPo)
    {
        return poMapper.selectSupPoAllList(supPo);
    }

    /**
     * 新增采购单管理
     * 
     * @param supPo 采购单管理
     * @return 结果
     */
    @Override
    public int insertSupPo(SupPo supPo)
    {
        supPo.setCreateTime(DateUtils.getNowDate());
        return poMapper.insertSupPo(supPo);
    }

    /**
     * 修改采购单管理
     * 
     * @param supPo 采购单管理
     * @return 结果
     */
    @Override
    public int updateSupPo(SupPo supPo)
    {
        supPo.setUpdateTime(DateUtils.getNowDate());
        return poMapper.updateSupPo(supPo);
    }

    /**
     * 批量删除采购单管理
     * 
     * @param ids 需要删除的采购单管理主键
     * @return 结果
     */
    @Override
    public int deleteSupPoByIds(Long[] ids)
    {
        return poMapper.deleteSupPoByIds(ids);
    }

    /**
     * 删除采购单管理信息
     * 
     * @param id 采购单管理主键
     * @return 结果
     */
    @Override
    public int deleteSupPoById(Long id)
    {
        return poMapper.deleteSupPoById(id);
    }

     /**
      * 发放
      *
      * @param supPo 发放
      * @return 结果
      */
     @Override
     public int enable(SupPo supPo){
         Long id = supPo.getId();
         SupPo obj = poMapper.selectSupPoById(id);
         List<SupPoItem> itemList = itemMapper.selectSupPoItemListByPoId(id);

         //更新主表状态
         obj.setStatus(BillStatus.BASE_ENABLE);
         obj.refreshUpdatingStamp();
         poMapper.updateSupPo(obj);
         //更新子表状态
         if(itemList!=null){
             for(SupPoItem item:itemList){
                 item.setStatus(BillStatus.BASE_ENABLE);
                 item.refreshUpdatingStamp();
                 itemMapper.updateSupPoItem(item);

                 //更新物料的最后价
                 EngMaterial material = materialMapper.selectEngMaterialById(item.getMaterialId());
                 material.setPriceLast(item.getPrice());
                 material.refreshUpdatingStamp();
                 materialMapper.updateEngMaterial(material);
             }
         }

         return 1;
     }
    /**
     * 变更
     *
     * @param supPo 变更
     * @return 结果
     */
    @Override
    public int changeSupPo(SupPo supPo){
        supPo.setUpdateTime(DateUtils.getNowDate());
        return poMapper.updateSupPo(supPo);
    }
     /**
      * 冻结
      *
      * @param supPo 冻结
      * @return 结果
      */
     @Override
     public int froze(SupPo supPo){
         Long id = supPo.getId();
         SupPo obj = poMapper.selectSupPoById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("supPo");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         frozen.refreshCreatingStamp();
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return poMapper.updateSupPo(obj);
      }
     /**
      * 解冻
      *
      * @param supPo 解冻
      * @return 结果
      */
     @Override
     public int unfroze(SupPo supPo){
         Long id = supPo.getId();
         SupPo obj = poMapper.selectSupPoById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("supPo");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             obj.refreshUpdatingStamp();
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             return poMapper.updateSupPo(obj);
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param supPo 停用
      * @return 结果
      */
     @Override
     public int disable(SupPo supPo){
         Long id = supPo.getId();
         SupPo obj = poMapper.selectSupPoById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return poMapper.updateSupPo(obj);
     }

      /**
       * 通知收货
       *
       * @param supPo 通知收货
       * @return 结果
       */
      @Override
      public int noticeReceiving(SupPo supPo) throws Exception{
          try{
              return supPoAlgorithm.noticeReceiving(supPo);
          }catch (Exception ex){
              throw ex;
          }
      }

    /**
     * 通知退货
     *
     * @param poitem 采购明细
     * @return 结果
     */
    @Override
    public int noticeReturning(SupPoItem poitem) throws Exception{
        try{
            return supPoAlgorithm.noticeReturning(poitem);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 放弃退货
     *
     * @param poitem 采购明细
     * @return 结果
     */
    @Override
    public int ignoreReturning(SupPoItem poitem) throws Exception{
        try{
            return supPoAlgorithm.ignoreReturning(poitem);
        }catch (Exception ex){
            throw ex;
        }
    }

}

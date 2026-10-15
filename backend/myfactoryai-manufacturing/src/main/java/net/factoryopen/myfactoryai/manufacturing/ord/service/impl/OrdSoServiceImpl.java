package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单登记发布Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdSoServiceImpl implements IOrdSoService 
{
    @Autowired(required = false)
    private OrdSoMapper ordSoMapper;

    @Autowired(required = false)
    private OrdSoItemMapper ordSoItemMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询订单登记发布
     * 
     * @param id 订单登记发布主键
     * @return 订单登记发布
     */
    @Override
    public OrdSo selectOrdSoById(Long id)
    {
        return ordSoMapper.selectOrdSoById(id);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     * 
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoDraftList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoDraftList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoPublishingList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoPublishingList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoProcessingList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoProcessingList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoFinishingList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoFinishingList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoHistoryList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoHistoryList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoAllList(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoAllList(ordSo);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdSo> selectOrdSoList4Adult(OrdSo ordSo)
    {
        return ordSoMapper.selectOrdSoList4Adult(ordSo);
    }

    /**
     * 新增订单登记发布
     * 
     * @param ordSo 订单登记发布
     * @return 结果
     */
    @Override
    public int insertOrdSo(OrdSo ordSo)
    {
        ordSo.setCreateTime(DateUtils.getNowDate());
        return ordSoMapper.insertOrdSo(ordSo);
    }

    /**
     * 修改订单登记发布
     * 
     * @param ordSo 订单登记发布
     * @return 结果
     */
    @Override
    public int updateOrdSo(OrdSo ordSo)
    {
        ordSo.setUpdateTime(DateUtils.getNowDate());
        return ordSoMapper.updateOrdSo(ordSo);
    }

    /**
     * 批量删除订单登记发布
     * 
     * @param ids 需要删除的订单登记发布主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoByIds(Long[] ids)
    {
        return ordSoMapper.deleteOrdSoByIds(ids);
    }

    /**
     * 删除订单登记发布信息
     * 
     * @param id 订单登记发布主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoById(Long id)
    {
        return ordSoMapper.deleteOrdSoById(id);
    }

     /**
      * 发布
      *
      * @param ordSo 发布
      * @return 结果
      */
     @Override
     public int enable(OrdSo ordSo){
         Long id = ordSo.getId();
         OrdSo obj = ordSoMapper.selectOrdSoById(id);
         List<OrdSoItem> itemList =ordSoItemMapper.selectOrdSoItemListBySoId(obj.getId());

         //更新主表状态
         obj.setStatus(BillStatus.BASE_ENABLE);
         obj.refreshUpdatingStamp();
         ordSoMapper.updateOrdSo(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdSoItem item:itemList){
                 item.setStatus(BillStatus.BASE_ENABLE);
                 item.refreshUpdatingStamp();
                 ordSoItemMapper.updateOrdSoItem(item);

                 //更新产品的最后价
                 EngMaterial material = materialMapper.selectEngMaterialById(item.getProductId());
                 material.setPriceLast(new BigDecimal(item.getDiscountPrice().doubleValue()*item.getDiscountRate().doubleValue()));
                 material.refreshUpdatingStamp();
                 materialMapper.updateEngMaterial(material);
             }
         }

         return 1;
     }
    /**
     * 变更
     *
     * @param ordSo 变更
     * @return 结果
     */
    @Override
    public int changeOrdSo(OrdSo ordSo){
        ordSo.refreshUpdatingStamp();
        return ordSoMapper.updateOrdSo(ordSo);
    }
     /**
      * 冻结
      *
      * @param ordSo 冻结
      * @return 结果
      */
     @Override
     public int froze(OrdSo ordSo){
         Long id = ordSo.getId();
         OrdSo obj = ordSoMapper.selectOrdSoById(id);
         List<OrdSoItem> itemList =ordSoItemMapper.selectOrdSoItemListBySoId(obj.getId());

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("ordSo");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         frozen.refreshCreatingStamp();
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         //更新主表状态
         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         ordSoMapper.updateOrdSo(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdSoItem item:itemList){
                 item.setStatus(BillStatus.LIFE_FROZEN);
                 item.refreshUpdatingStamp();
                 ordSoItemMapper.updateOrdSoItem(item);
             }
         }

         return 1;
      }
     /**
      * 解冻
      *
      * @param ordSo 解冻
      * @return 结果
      */
     @Override
     public int unfroze(OrdSo ordSo){
         Long id = ordSo.getId();
         OrdSo obj = ordSoMapper.selectOrdSoById(id);
         List<OrdSoItem> itemList =ordSoItemMapper.selectOrdSoItemListBySoId(obj.getId());

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("ordSo");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());

             //更新主表状态
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             obj.refreshUpdatingStamp();
             ordSoMapper.updateOrdSo(obj);
             //更新子表状态
             if(itemList!=null){
                 for(OrdSoItem item:itemList){
                     item.setStatus(list.get(0).getFrozenStatus().intValue());
                     item.refreshUpdatingStamp();
                     ordSoItemMapper.updateOrdSoItem(item);
                 }
             }

             return 1;
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param ordSo 停用
      * @return 结果
      */
     @Override
     public int disable(OrdSo ordSo){
         Long id = ordSo.getId();
         OrdSo obj = ordSoMapper.selectOrdSoById(id);
         List<OrdSoItem> itemList =ordSoItemMapper.selectOrdSoItemListBySoId(obj.getId());

         //更新主表状态
         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         ordSoMapper.updateOrdSo(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdSoItem item:itemList){
                 item.setStatus(BillStatus.LIFE_DEAD);
                 item.refreshUpdatingStamp();
                 ordSoItemMapper.updateOrdSoItem(item);
             }
         }

         return 1;
     }
     /**
      * 强结
      *
      * @param ordSo 强结
      * @return 结果
      */
     @Override
     public int finishforcely(OrdSo ordSo){
         Long id = ordSo.getId();
         OrdSo obj = ordSoMapper.selectOrdSoById(id);
         List<OrdSoItem> itemList =ordSoItemMapper.selectOrdSoItemListBySoId(obj.getId());

         //更新主表状态
         obj.setStatus(BillStatus.FINISH_FORCELY);
         obj.refreshUpdatingStamp();
         ordSoMapper.updateOrdSo(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdSoItem item:itemList){
                 item.setStatus(BillStatus.FINISH_FORCELY);
                 item.refreshUpdatingStamp();
                 ordSoItemMapper.updateOrdSoItem(item);
             }
         }

         return 1;
     }

}

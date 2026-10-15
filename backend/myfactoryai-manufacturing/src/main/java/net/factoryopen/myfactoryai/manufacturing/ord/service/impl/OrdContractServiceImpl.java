package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdContractAlgorithm;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 合同发布Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdContractServiceImpl implements IOrdContractService 
{
    @Autowired(required = false)
    private OrdContractMapper ordContractMapper;

    @Autowired(required = false)
    private OrdContractItemMapper ordContractItemMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private OrdContractAlgorithm contractAlgorithm;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询订单登记发布
     * 
     * @param id 订单登记发布主键
     * @return 订单登记发布
     */
    @Override
    public OrdContract selectOrdContractById(Long id)
    {
        return ordContractMapper.selectOrdContractById(id);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     * 
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractDraftList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractDraftList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractPublishingList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractPublishingList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractProcessingList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractProcessingList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractFinishingList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractFinishingList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractHistoryList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractHistoryList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractAllList(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractAllList(ordContract);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param ordContract 订单登记发布
     * @return 订单登记发布
     */
    @Override
    public List<OrdContract> selectOrdContractList4Adult(OrdContract ordContract)
    {
        return ordContractMapper.selectOrdContractList4Adult(ordContract);
    }

    /**
     * 新增订单登记发布
     * 
     * @param ordContract 订单登记发布
     * @return 结果
     */
    @Override
    public int insertOrdContract(OrdContract ordContract)
    {
        ordContract.setCreateTime(DateUtils.getNowDate());
        return ordContractMapper.insertOrdContract(ordContract);
    }

    /**
     * 修改订单登记发布
     * 
     * @param ordContract 订单登记发布
     * @return 结果
     */
    @Override
    public int updateOrdContract(OrdContract ordContract)
    {
        ordContract.setUpdateTime(DateUtils.getNowDate());
        return ordContractMapper.updateOrdContract(ordContract);
    }

    /**
     * 批量删除订单登记发布
     * 
     * @param ids 需要删除的订单登记发布主键
     * @return 结果
     */
    @Override
    public int deleteOrdContractByIds(Long[] ids)
    {
        return ordContractMapper.deleteOrdContractByIds(ids);
    }

    /**
     * 删除订单登记发布信息
     * 
     * @param id 订单登记发布主键
     * @return 结果
     */
    @Override
    public int deleteOrdContractById(Long id)
    {
        return ordContractMapper.deleteOrdContractById(id);
    }

     /**
      * 发布
      *
      * @param ordContract 发布
      * @return 结果
      */
     @Override
     public int enable(OrdContract ordContract){
         Long id = ordContract.getId();
         OrdContract obj = ordContractMapper.selectOrdContractById(id);
         List<OrdContractItem> itemList =ordContractItemMapper.selectOrdContractItemListByContractId(obj.getId());

         //更新主表状态
         obj.setStatus(BillStatus.BASE_ENABLE);
         obj.refreshUpdatingStamp();
         ordContractMapper.updateOrdContract(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdContractItem item:itemList){
                 item.setStatus(BillStatus.BASE_ENABLE);
                 item.refreshUpdatingStamp();
                 ordContractItemMapper.updateOrdContractItem(item);

                 //更新产品的最后价
                 EngMaterial material = materialMapper.selectEngMaterialById(item.getProductId());
                 if (material!=null){
                     material.setPriceLast(new BigDecimal(item.getDiscountPrice().doubleValue()*item.getDiscountRate().doubleValue()));
                     material.refreshUpdatingStamp();
                     materialMapper.updateEngMaterial(material);

                 }
             }
         }

         return 1;
     }
    /**
     * 变更
     *
     * @param ordContract 变更
     * @return 结果
     */
    @Override
    public int changeOrdContract(OrdContract ordContract){
        ordContract.refreshUpdatingStamp();
        return ordContractMapper.updateOrdContract(ordContract);
    }
     /**
      * 冻结
      *
      * @param ordContract 冻结
      * @return 结果
      */
     @Override
     public int froze(OrdContract ordContract){
         Long id = ordContract.getId();
         OrdContract obj = ordContractMapper.selectOrdContractById(id);
         List<OrdContractItem> itemList =ordContractItemMapper.selectOrdContractItemListByContractId(obj.getId());

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("ordContract");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         frozen.refreshCreatingStamp();
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         //更新主表状态
         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         ordContractMapper.updateOrdContract(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdContractItem item:itemList){
                 item.setStatus(BillStatus.LIFE_FROZEN);
                 item.refreshUpdatingStamp();
                 ordContractItemMapper.updateOrdContractItem(item);
             }
         }

         return 1;
      }
     /**
      * 解冻
      *
      * @param ordContract 解冻
      * @return 结果
      */
     @Override
     public int unfroze(OrdContract ordContract){
         Long id = ordContract.getId();
         OrdContract obj = ordContractMapper.selectOrdContractById(id);
         List<OrdContractItem> itemList =ordContractItemMapper.selectOrdContractItemListByContractId(obj.getId());

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("ordContract");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());

             //更新主表状态
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             obj.refreshUpdatingStamp();
             ordContractMapper.updateOrdContract(obj);
             //更新子表状态
             if(itemList!=null){
                 for(OrdContractItem item:itemList){
                     item.setStatus(list.get(0).getFrozenStatus().intValue());
                     item.refreshUpdatingStamp();
                     ordContractItemMapper.updateOrdContractItem(item);
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
      * @param ordContract 停用
      * @return 结果
      */
     @Override
     public int disable(OrdContract ordContract){
         Long id = ordContract.getId();
         OrdContract obj = ordContractMapper.selectOrdContractById(id);
         List<OrdContractItem> itemList =ordContractItemMapper.selectOrdContractItemListByContractId(obj.getId());

         //更新主表状态
         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         ordContractMapper.updateOrdContract(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdContractItem item:itemList){
                 item.setStatus(BillStatus.LIFE_DEAD);
                 item.refreshUpdatingStamp();
                 ordContractItemMapper.updateOrdContractItem(item);
             }
         }

         return 1;
     }
     /**
      * 强结
      *
      * @param ordContract 强结
      * @return 结果
      */
     @Override
     public int finishforcely(OrdContract ordContract){
         Long id = ordContract.getId();
         OrdContract obj = ordContractMapper.selectOrdContractById(id);
         List<OrdContractItem> itemList =ordContractItemMapper.selectOrdContractItemListByContractId(obj.getId());

         //更新主表状态
         obj.setStatus(BillStatus.FINISH_FORCELY);
         obj.refreshUpdatingStamp();
         ordContractMapper.updateOrdContract(obj);
         //更新子表状态
         if(itemList!=null){
             for(OrdContractItem item:itemList){
                 item.setStatus(BillStatus.FINISH_FORCELY);
                 item.refreshUpdatingStamp();
                 ordContractItemMapper.updateOrdContractItem(item);
             }
         }

         return 1;
     }

    /**
     *  转单
     *
     * @param ids 订单ID集合
     * @return 结果
     */
    @Override
    public int toSo(Long[] ids) throws Exception
    {
        return contractAlgorithm.toSo(ids);
    }

    /**
     * 请求临时料号
     *
     * @param ordContract 合同
     * @return 结果
     */
    @Override
    public int requestTempProduct(OrdContract ordContract) throws Exception{
        ordContract.refreshUpdatingStamp();
        return contractAlgorithm.requestTempProduct(ordContract);
    }

}

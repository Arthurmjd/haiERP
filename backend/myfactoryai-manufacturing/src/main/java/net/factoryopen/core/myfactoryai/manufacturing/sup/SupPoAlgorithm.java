package net.factoryopen.core.myfactoryai.manufacturing.sup;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.exception.*;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.InventoryTransactionType;
import net.factoryopen.core.common.type.MaterialPurchasingType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInventory;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInventoryMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemReturnMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;

/**
 * 采购单管理ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-09-23
 */
@Service
public class SupPoAlgorithm
{
    @Autowired(required = false)
    private SupPoMapper poMapper;

    @Autowired(required = false)
    private SupPoItemMapper poItemMapper;

    @Autowired(required = false)
    private DelOsplanMapper osplanMapper;

    @Autowired(required = false)
    private SupPoItemReturnMapper returnMapperr;

    @Autowired(required = false)
    private SncStockRequestMapper stockreqMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private EngMaterialMapper productMapper;

    @Autowired(required = false)
    private EngMaterialInventoryMapper inventoryMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 通知收货
       *
       * @param supPo 通知收货
       * @return 结果
       */
      public int noticeReceiving(SupPo supPo) throws Exception{
          // 排除状态的錯配
          SupPo po = poMapper.selectSupPoById(supPo.getId());
          if (po.getStatus().intValue() != BillStatus.BASE_ENABLE) {
              String msg = "采购单[" + po.getBillNo() + "]的状态不是[4-生效]";
              throw new StatusUnavailableException(msg);
          }
          // 排除数量的錯配
          List<SupPoItem> poItems = poItemMapper.selectSupPoItemListByPoId(supPo.getId());
          if (poItems==null || poItems.size()==0){
              String msg = "采购单[" + po.getBillNo() + "]的明细不存在！";
              throw new RecordNotExistException(msg);
          }

          // 取得并更新单据规则
          String billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
          billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);

          //建采购收货通知请求
          SncStockRequest request= new SncStockRequest();
          //填写关键信息
          request.setBillNo(billNo);
          request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
          request.setRequestBillId(po.getId());
          request.setRequestBillNo(po.getBillNo());
          //判断采购类型
          if (po.getPurchasingType()== MaterialPurchasingType.SUBCONTRACT) request.setIoType(InventoryTransactionType.STOCKIN_4_SUBCONTRACT);
          else if (po.getPurchasingType()== MaterialPurchasingType.OUTERSUPPORT) request.setIoType(InventoryTransactionType.STOCKIN_OUTER_SUPPORT);
          else request.setIoType(InventoryTransactionType.STOCKIN_4_BUY);
          //补充来源信息
          request.setSourceCustomerId(null);
          request.setSourceLineId(null);
          request.setSourceSupplierId(po.getSupplierId());
          request.setProjectId(null);
          request.setRequestorId(SecurityUtils.getUserId());
          request.setRequestTime(DateUtils.getNowDate());
          //保存请求
          request.refreshCreatingStamp();
          stockreqMapper.insertSncStockRequest(request);

          // 按明细来更新采购收货量、库存量、发货请求明细
          for (SupPoItem poItem:poItems) {
              EngMaterial material = productMapper.selectEngMaterialById(poItem.getMaterialId());

              SncStockRequestItem requestItem = new SncStockRequestItem();
              requestItem.setSeqNo(poItem.getSeqNo());
              requestItem.setRequestId(request.getId());
              requestItem.setMaterialId(material.getId());
              requestItem.setMaterialCode(material.getMaterialCode());
              requestItem.setMaterialName(material.getMaterialName());
              requestItem.setMaterialSpec(material.getMaterialSpec());
              requestItem.setDrawNo(material.getDrawNo());
              requestItem.setCategoryId(material.getCategoryId());
              requestItem.setCompoundId(material.getCompoundId());
              requestItem.setCustomerProductNo(material.getCustomerProductNo());
              requestItem.setAbroadProductNo(material.getAbroadProductNo());
              requestItem.setUnitId(material.getUnitId());
              requestItem.setQuantity(poItem.getQuantityOrder());
              requestItem.setPrice(poItem.getPrice());
              requestItem.setAmount(poItem.getAmount());
              requestItem.setHouseId(material.getHouseId());
              requestItem.setPositionId(material.getPositionId());
              requestItem.setSourceId(poItem.getId());
              //创建戳
              requestItem.refreshCreatingStamp();
              requestItemMapper.insertSncStockRequestItem(requestItem);

              //设采购明细状态为“待收货”
              poItem.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
              poItemMapper.updateSupPoItem(poItem);

              //依采购类型变更来源单据的发货指派数量
              switch (po.getPurchasingType()){
                  case MaterialPurchasingType.SUBCONTRACT:
                      DelOsplan osplan = osplanMapper.selectDelOsplanById(poItem.getSourceBillId());
                      osplan.setStatus(BillStatus.ACTIVE_PROCESSED);
                      osplanMapper.updateDelOsplan(osplan);
                      break;
                  case MaterialPurchasingType.PRODUCTING:
                      //跳空。自制主计划聚焦生产状态的变化，不看采购状态的变化
                      break;
              }
          }

          // 更新发货单单头状态
          po.setStatus(BillStatus.ACTIVE_PROCESS_REQ); //状态为“请收货”
          po.refreshUpdatingStamp();
          poMapper.updateSupPo(po);

          return 1;
      }

    /**
     * 通知退货
     *
     * @param poitem 采购明细
     * @return 结果
     */
    public int noticeReturning(SupPoItem poitem) throws Exception{
        // 排除状态的錯配
        SupPo po = poMapper.selectSupPoById(poitem.getPoId());
        poitem = poItemMapper.selectSupPoItemById(poitem.getId());

        //状态判异
        if (po.getStatus().intValue() != BillStatus.ACTIVE_EXCEPTIONAL) {
            String msg = "采购单[" + po.getBillNo() + "]的状态不是[10-有异常]";
            throw new StatusUnavailableException(msg);
        }
        if (poitem.getStatus().intValue() != BillStatus.ACTIVE_STARTED) {
            String msg = "采购单[" + po.getBillNo() + "]明细[" + poitem.getSeqNo() + "]的状态不是[异入库]";
            throw new StatusUnavailableException(msg);
        }

        //创建采购退货项
        SupPoItemReturn itemReturn = new SupPoItemReturn();
        itemReturn.setPoId(poitem.getPoId());
        itemReturn.setSeqNo(poitem.getSeqNo());
        itemReturn.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
        itemReturn.setSupplierId(po.getSupplierId());
        itemReturn.setPurchasingType(po.getPurchasingType());
        itemReturn.setMaterialId(poitem.getMaterialId());
        itemReturn.setMaterialCode(poitem.getMaterialCode());
        itemReturn.setMaterialName(poitem.getMaterialName());
        itemReturn.setMaterialSpec(poitem.getMaterialSpec());
        itemReturn.setDrawNo(poitem.getDrawNo());
        itemReturn.setUnitId(poitem.getUnitId());
        itemReturn.setQuantityReturning(poitem.getQuantityQcNg());
        itemReturn.setSourceBillId(poitem.getSourceBillId());
        itemReturn.setSourceBillNo(poitem.getSourceBillNo());
        itemReturn.refreshCreatingStamp();
        returnMapperr.insertSupPoItemReturn(itemReturn);

        //采购单明细的在退数量.仅退回NG，不退Pending和Aod
        //状态为待退货,借用状态15标记待退货
        poitem.setQuantityReturning(poitem.getQuantityQcNg());
        poitem.setStatus(BillStatus.LIFE_EXCEPTIONAL);
        poitem.refreshUpdatingStamp();
        poItemMapper.updateSupPoItem(poitem);

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
        billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);
        //创建采购退货请求
        SncStockRequest request= new SncStockRequest();
        request.setBillNo(billNo);
        request.setIoType(InventoryTransactionType.STOCKOUT_4_BUYBACK);
        request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
        request.setRequestBillId(po.getId());
        request.setRequestBillNo(po.getBillNo());
        //补充来源信息
        request.setSourceCustomerId(null);
        request.setSourceLineId(null);
        request.setSourceSupplierId(po.getSupplierId());
        request.setProjectId(null);
        request.setRequestorId(SecurityUtils.getUserId());
        request.setRequestTime(DateUtils.getNowDate());
        //保存请求
        request.refreshCreatingStamp();
        stockreqMapper.insertSncStockRequest(request);

        //获取产品信息
        EngMaterial product = productMapper.selectEngMaterialById(poitem.getMaterialId());
        //建仓库交易明细
        SncStockRequestItem requestItem = new SncStockRequestItem();
        requestItem.setSeqNo(1);
        requestItem.setRequestId(request.getId());
        requestItem.setMaterialId(product.getId());
        requestItem.setMaterialCode(product.getMaterialCode());
        requestItem.setMaterialName(product.getMaterialName());
        requestItem.setMaterialSpec(product.getMaterialSpec());
        requestItem.setDrawNo(product.getDrawNo());
        requestItem.setCategoryId(product.getCategoryId());
        requestItem.setCompoundId(product.getCompoundId());
        requestItem.setCustomerProductNo(product.getCustomerProductNo());
        requestItem.setAbroadProductNo(product.getAbroadProductNo());
        requestItem.setUnitId(product.getUnitId());
        requestItem.setQuantity(poitem.getQuantityQcNg());
        requestItem.setPrice(poitem.getPrice());
        requestItem.setAmount(new BigDecimal(poitem.getQuantityQcNg().doubleValue()*poitem.getPrice().doubleValue()));
        requestItem.setHouseId(product.getHouseId());
        requestItem.setPositionId(product.getPositionId());
        requestItem.setSourceId(poitem.getId());
        //保存
        requestItem.refreshCreatingStamp();
        requestItemMapper.insertSncStockRequestItem(requestItem);

        return 1;
    }

    /**
     * 放弃退货
     *
     * @param poitem 采购明细
     * @return 结果
     */
    public int ignoreReturning(SupPoItem poitem) throws Exception{
        // 排除状态的錯配
        SupPo po = poMapper.selectSupPoById(poitem.getPoId());
        poitem = poItemMapper.selectSupPoItemById(poitem.getId());

        //状态判异
        if (po.getStatus().intValue() != BillStatus.ACTIVE_EXCEPTIONAL) {
            String msg = "采购单[" + po.getBillNo() + "]的状态不是[10-有异常]";
            throw new StatusUnavailableException(msg);
        }
        if (poitem.getStatus().intValue() != BillStatus.ACTIVE_STARTED) {
            String msg = "采购单[" + po.getBillNo() + "]明细[" + poitem.getSeqNo() + "]的状态不是[异入库]";
            throw new StatusUnavailableException(msg);
        }

        po.setStatus(BillStatus.FINISH_WAITING);
        po.refreshUpdatingStamp();
        poMapper.updateSupPo(po);

        return 1;
    }

}

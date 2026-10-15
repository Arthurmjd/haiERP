package net.factoryopen.core.myfactoryai.manufacturing.del;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.InventoryTransactionType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryPlanMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInventory;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInventoryMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryOrderMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelGdItemMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.QuantityUnavailableException;


/**
 * 发货单管理ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-30
 */
@Service
public class DelDeliveryOrderAlgorithm
{
    @Autowired(required = false)
    private DelDeliveryOrderMapper orderMapper;

    @Autowired(required = false)
    private DelGdItemMapper itemMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockreqMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private DelDeliveryPlanMapper dpMapper;

    @Autowired(required = false)
    private EngMaterialMapper productMapper;

    @Autowired(required = false)
    private EngMaterialInventoryMapper inventoryMapper;

    @Autowired
    private BillcodeManager billcodeManager;


    /**
       * 请发货
       *
       * @param ids id集合
       * @return 结果
       */
      public int delivery(Long[] ids) throws Exception{
          // 判异
          for (int i = 0; i < ids.length; i++) {
              // 排除状态的錯配
              DelDeliveryOrder order = orderMapper.selectDelDeliveryOrderById(ids[i]);
              if (order.getStatus().intValue() != BillStatus.BASE_ENABLE) {
                  String msg = "发货单[" + order.getBillNo() + "]的状态不是[4-生效]";
                  throw new StatusUnavailableException(msg);
              }
              // 排除数量的錯配
              List<DelGdItem> items = itemMapper.selectDelGdItemListByGdId(order.getId());
              for (DelGdItem item:items) {
                  EngMaterial product = productMapper.selectEngMaterialById(item.getProductId());
                  if (item.getQuantity().doubleValue()> product.getQuantityQualified().doubleValue()){
                      String msg = "发货单第[" + item.getSeqNo() + "]项的库存合格品数量不足";
                      throw new QuantityUnavailableException(msg);
                  }
              }
          }

          //逐一发货
          for (int i = 0; i < ids.length; i++) {
              // 取得并更新单据规则
              String billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
              billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);

              //取发货单
              DelDeliveryOrder order = orderMapper.selectDelDeliveryOrderById(ids[i]);

              //建销售发货请求
              SncStockRequest request= new SncStockRequest();
              //填写关键信息
              request.setBillNo(billNo);
              request.setIoType(InventoryTransactionType.STOCKOUT_4_SALE);
              request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
              request.setRequestBillId(order.getId());
              request.setRequestBillNo(order.getBillNo());
              //补充来源信息
              request.setSourceCustomerId(order.getCustomerId());
              request.setSourceLineId(null);
              request.setSourceSupplierId(null);
              request.setProjectId(null);
              request.setRequestorId(SecurityUtils.getUserId());
              request.setRequestTime(DateUtils.getNowDate());
              //保存请求
              request.refreshCreatingStamp();
              stockreqMapper.insertSncStockRequest(request);

              //取发货单明细
              List<DelGdItem> items = itemMapper.selectDelGdItemListByGdId(order.getId());
              // 按明细来更新订单发货量、库存量、发货请求明细
              for (DelGdItem item:items) {
                  EngMaterial product = productMapper.selectEngMaterialById(item.getProductId());

                  SncStockRequestItem requestItem = new SncStockRequestItem();
                  requestItem.setSeqNo(item.getSeqNo());
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
                  requestItem.setQuantity(item.getQuantity());
                  requestItem.setPrice(new BigDecimal(item.getDiscountPrice().doubleValue()*item.getDiscountAmount().doubleValue()));
                  requestItem.setAmount(new BigDecimal(item.getAmount().doubleValue()*item.getDiscountAmount().doubleValue()));
                  requestItem.setHouseId(product.getHouseId());
                  requestItem.setPositionId(product.getPositionId());
                  requestItem.setSourceId(item.getId());
                  //创建戳
                  requestItem.setCreateBy(SecurityUtils.getUsername());
                  requestItem.setCreateTime(DateUtils.getNowDate());
                  requestItem.setUpdateBy(SecurityUtils.getUsername());
                  requestItem.setUpdateTime(DateUtils.getNowDate());
                  requestItemMapper.insertSncStockRequestItem(requestItem);

                  // 将原发货计划的状态设为“请处理”，意为“已请仓库发货”
                  DelDeliveryPlan dp = dpMapper.selectDelDeliveryPlanById(item.getDpId());
                  dp.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
                  dp.refreshUpdatingStamp();
                  dpMapper.updateDelDeliveryPlan(dp);
              }

              // 更新发货单单头状态
              order.setStatus(BillStatus.ACTIVE_PROCESS_REQ); //状态为“请处理”
              order.refreshUpdatingStamp();
              orderMapper.updateDelDeliveryOrder(order);
          }

          return 1;
      }

}

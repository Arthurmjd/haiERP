package net.factoryopen.core.myfactoryai.manufacturing.ord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.priority.PriorityLevel;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.InventoryTransactionType;
import net.factoryopen.core.common.type.SaleOrderType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasInvoicePatternService;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryOrderMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelGdItemMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoItemService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoQuicklyMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoQuickly;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 急出订单ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-11-09
 */
@Service
public class OrdSoQuicklyAlgorithm
{
    @Autowired(required = false)
    private OrdSoQuicklyMapper quicksoMapper;

    @Autowired
    private IOrdSoService soService;

    @Autowired
    private IOrdSoItemService soitemService;

    @Autowired(required = false)
    private DelDeliveryOrderMapper doMapper;

    @Autowired(required = false)
    private DelGdItemMapper doitemMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockreqMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private EngMaterialMapper productMapper;

    @Autowired
    private IBasInvoicePatternService invoiceService;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 便捷订单生生单
     *
     * @param ids id集合
     * @return 结果
     */
    public int deliveryDirectly(Long[] ids) throws Exception{
        // 判异，排除客户和状态的錯配
        for (int i = 0; i < ids.length; i++) {
            OrdSoQuickly quickso = quicksoMapper.selectOrdSoQuicklyById(ids[i]);
            if (quickso.getStatus().intValue() != 4) {
                String msg = "便捷订单[" + quickso.getBillNo() + "]的状态不是[4-投用]";
                throw new StatusUnavailableException(msg);
            }
            if (quickso.getCustomerId()==null || quickso.getQuantity()==null || quickso.getQuantity().doubleValue()==0) {
                String msg = "便捷订单[" + quickso.getBillNo() + "]的客户或数量信息不完整！";
                throw new DateUnavailableException(msg);
            }
        }

        // 逐一取急单信息，自动补完“订单->通知发货”的流程
        for (int i = 0; i < ids.length; i++) {
            OrdSoQuickly quickso = quicksoMapper.selectOrdSoQuicklyById(ids[i]);
            BasInvoicePattern invoicePattern = invoiceService.selectBasInvoicePatternById(quickso.getInvoicePatternId());

            //取更销售订单
            String billNo = billcodeManager.getNextBillno("SO");
            billcodeManager.updateBillSeq("SO");
            //创建SO
            OrdSo so = new OrdSo();
            so.setBillNo(billNo);
            so.setStatus(BillStatus.BASE_ENABLE);
            so.setSoType(SaleOrderType.ORDER_DIRECTLY);
            so.setInvoicePatternId(quickso.getInvoicePatternId());
            so.setPayPatternId(1L);
            so.setContractNo(quickso.getContractNo());
            so.setCustomerId(quickso.getCustomerId());
            so.setInvoicePatternId(quickso.getInvoicePatternId());
            so.setAmountUntaxed(new BigDecimal(quickso.getQuantity().doubleValue()*quickso.getPrice().doubleValue()));
            so.setAmountTaxed(new BigDecimal(so.getAmountUntaxed().doubleValue()*(1+invoicePattern.getPatternRate().doubleValue())));
            so.setOrderDay(DateUtils.getNowDate());
            so.setCurrencyId(1L);
            so.setShipmentTypeId(1L);
            so.setExchangeRate(new BigDecimal(1));
            so.refreshCreatingStamp();
            soService.insertOrdSo(so);

            //取产品数据
            EngMaterial product = productMapper.selectEngMaterialById(quickso.getProductId());

            OrdSoItem soItem = new OrdSoItem();
            soItem.setStatus(BillStatus.BASE_ENABLE);
            soItem.setSoId(so.getId());
            soItem.setSeqNo("1");
            soItem.setProductId(quickso.getProductId());
            soItem.setProductCode(product.getMaterialCode());
            soItem.setProductName(quickso.getProductName());
            soItem.setProductSpec(quickso.getProductSpec());
            soItem.setDrawNo(product.getDrawNo());
            soItem.setCustomerProductNo(product.getCustomerProductNo());
            soItem.setQuantityOrder(quickso.getQuantity());
            soItem.setUnitId(quickso.getUnitId());
            soItem.setPrice(quickso.getPrice());
            soItem.setDiscountPrice(quickso.getPrice());
            soItem.setAmount(so.getAmountTaxed());
            soItem.setDiscountRate(new BigDecimal(1));
            soItem.setFinalAmount(so.getAmountTaxed());
            soItem.setPriority(PriorityLevel.PRIORITY_SUPER_CRITICAL);
            soItem.setNeedDay(DateUtils.getNowDate());
            soItem.refreshCreatingStamp();
            soitemService.insertOrdSoItem(soItem);

            //更新急单状态为结案
            quickso.setStatus(BillStatus.FINISH);
            quickso.refreshUpdatingStamp();
            quicksoMapper.updateOrdSoQuickly(quickso);
        }

        return 1;
    }

    /**
     * 便捷订单生生单
     *
     * @param ids id集合
     * @return 结果
     */
    public int deliveryQuickly(Long[] ids) throws Exception{
        // 判异，排除客户和状态的錯配
        for (int i = 0; i < ids.length; i++) {
            OrdSoQuickly quickso = quicksoMapper.selectOrdSoQuicklyById(ids[i]);
            if (quickso.getStatus().intValue() != 4) {
                String msg = "急单[" + quickso.getBillNo() + "]的状态不是[4-投用]";
                throw new StatusUnavailableException(msg);
            }
            if (quickso.getCustomerId()==null || quickso.getQuantity()==null || quickso.getQuantity().doubleValue()==0) {
                String msg = "急单[" + quickso.getBillNo() + "]的客户或数量信息不完整！";
                throw new DateUnavailableException(msg);
            }
        }

        // 逐一取急单信息，自动补完“订单->通知发货”的流程
        for (int i = 0; i < ids.length; i++) {
            OrdSoQuickly quickso = quicksoMapper.selectOrdSoQuicklyById(ids[i]);
            BasInvoicePattern invoicePattern = invoiceService.selectBasInvoicePatternById(quickso.getInvoicePatternId());

            //取更销售订单
            String billNo = billcodeManager.getNextBillno("SO");
            billcodeManager.updateBillSeq("SO");
            //创建SO
            OrdSo so = new OrdSo();
            so.setBillNo(billNo);
            so.setStatus(BillStatus.ACTIVE_PROCESSING);
            so.setSoType(SaleOrderType.ORDER_DIRECTLY);
            so.setInvoicePatternId(quickso.getInvoicePatternId());
            so.setPayPatternId(1L);
            so.setContractNo(quickso.getContractNo());
            so.setCustomerId(quickso.getCustomerId());
            so.setInvoicePatternId(quickso.getInvoicePatternId());
            so.setAmountUntaxed(new BigDecimal(quickso.getQuantity().doubleValue()*quickso.getPrice().doubleValue()));
            so.setAmountTaxed(new BigDecimal(so.getAmountUntaxed().doubleValue()*(1+invoicePattern.getPatternRate().doubleValue())));
            so.setOrderDay(DateUtils.getNowDate());
            so.setCurrencyId(1L);
            so.setShipmentTypeId(1L);
            so.setExchangeRate(new BigDecimal(1));
            so.refreshCreatingStamp();
            soService.insertOrdSo(so);

            //取产品数据
            EngMaterial product = productMapper.selectEngMaterialById(quickso.getProductId());

            OrdSoItem soItem = new OrdSoItem();
            soItem.setStatus(BillStatus.ACTIVE_PROCESSING);
            soItem.setSoId(so.getId());
            soItem.setSeqNo("1");
            soItem.setProductId(product.getId());
            soItem.setProductCode(product.getMaterialCode());
            soItem.setProductName(product.getMaterialName());
            soItem.setProductSpec(product.getMaterialSpec());
            soItem.setDrawNo(product.getDrawNo());
            soItem.setCustomerProductNo(product.getCustomerProductNo());
            soItem.setQuantityOrder(quickso.getQuantity());
            soItem.setUnitId(quickso.getUnitId());
            soItem.setPrice(quickso.getPrice());
            soItem.setDiscountPrice(quickso.getPrice());
            soItem.setAmount(so.getAmountTaxed());
            soItem.setDiscountRate(new BigDecimal(1));
            soItem.setFinalAmount(so.getAmountTaxed());
            soItem.setPriority(PriorityLevel.PRIORITY_SUPER_CRITICAL);
            soItem.setNeedDay(DateUtils.getNowDate());
            soItem.refreshCreatingStamp();
            soitemService.insertOrdSoItem(soItem);

            //取更发货单单
            billNo = billcodeManager.getNextBillno("DO");
            billcodeManager.updateBillSeq("DO");
            // 创建发货单头
            DelDeliveryOrder doo = new DelDeliveryOrder();
            doo.setBillNo(billNo);
            doo.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
            doo.setCustomerId(quickso.getCustomerId());
            doo.setDeliveryDay(DateUtils.getNowDate());
            doo.setProjectId(null);
            doo.setSalesId(quickso.getSalesId());
            doo.refreshCreatingStamp();
            doMapper.insertDelDeliveryOrder(doo);

            //创建并保存发货单身
            DelGdItem doitem = new DelGdItem();
            doitem.setGdId(doo.getId());
            doitem.setSeqNo(1);
            doitem.setDpId(null);
            doitem.setSoId(so.getId());
            doitem.setSoItemId(soItem.getId());
            doitem.setProductId(product.getId());
            doitem.setProductCode(product.getMaterialCode());
            doitem.setProductName(product.getMaterialName());
            doitem.setProductSpec(product.getMaterialSpec());
            doitem.setDrawNo(product.getDrawNo());
            doitem.setCustomerProductNo(product.getCustomerProductNo());
            doitem.setAbroadProductNo(product.getAbroadProductNo());
            doitem.setUnitId(quickso.getUnitId());
            doitem.setQuantity(quickso.getQuantity());
            doitem.setPrice(quickso.getPrice());
            doitem.setDiscountPrice(soItem.getDiscountPrice());
            doitem.setDiscountAmount(soItem.getDiscountRate());
            doitem.setAmount(soItem.getAmount());
            doitem.refreshCreatingStamp();
            doitemMapper.insertDelGdItem(doitem);

            // 取得并更新单据规则
            billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
            billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);
            //建销售发货请求
            SncStockRequest request= new SncStockRequest();
            request.setBillNo(billNo);
            request.setIoType(InventoryTransactionType.STOCKOUT_4_SALE);
            request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
            request.setRequestBillId(doo.getId());
            request.setRequestBillNo(doo.getBillNo());
            request.setSourceCustomerId(quickso.getCustomerId());
            request.setSourceLineId(null);
            request.setSourceSupplierId(null);
            request.setProjectId(null);
            request.setRequestorId(SecurityUtils.getUserId());
            request.setRequestTime(DateUtils.getNowDate());
            request.refreshCreatingStamp();
            stockreqMapper.insertSncStockRequest(request);

            //建销售发货请求明细
            SncStockRequestItem requestItem = new SncStockRequestItem();
            requestItem.setSeqNo(1);
            requestItem.setRequestId(request.getId());
            requestItem.setMaterialId(product.getId());
            requestItem.setMaterialCode(product.getMaterialCode());
            requestItem.setMaterialName(quickso.getProductName());
            requestItem.setMaterialSpec(quickso.getProductSpec());
            requestItem.setDrawNo(product.getDrawNo());
            requestItem.setCategoryId(product.getCategoryId());
            requestItem.setCompoundId(product.getCompoundId());
            requestItem.setCustomerProductNo(product.getCustomerProductNo());
            requestItem.setAbroadProductNo(product.getAbroadProductNo());
            requestItem.setUnitId(quickso.getUnitId());
            requestItem.setQuantity(quickso.getQuantity());
            requestItem.setPrice(quickso.getPrice());
            requestItem.setAmount(soItem.getFinalAmount());
            requestItem.setHouseId(product.getHouseId());
            requestItem.setPositionId(product.getPositionId());
            requestItem.setSourceId(doitem.getId());
            requestItem.refreshCreatingStamp();
            requestItemMapper.insertSncStockRequestItem(requestItem);

            //更新急单状态为结案
            quickso.setStatus(BillStatus.FINISH);
            quickso.refreshUpdatingStamp();
            quicksoMapper.updateOrdSoQuickly(quickso);
        }

        return 1;
    }

}

package net.factoryopen.core.myfactoryai.manufacturing.qua;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.exception.QuantityUnavailableException;
import net.factoryopen.core.common.exception.TypeMismatchException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.InspectionDealType;
import net.factoryopen.core.common.type.InspectionType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionDealMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectReDealMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequestItem;

import net.factoryopen.core.common.exception.StatusUnavailableException;

/**
 * 请检ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-10-06
 */
@Service
public class QuaInspectRequestItemAlgorithm
{
    @Autowired(required = false)
    private QuaInspectRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private QuaInspectReDealMapper requestMapper;

    @Autowired(required = false)
    private QuaExceptionDealMapper exceptionMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockRequestMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper stockRequestItemMapper;

    @Autowired(required = false)
    private SupPoMapper poMapper;

    @Autowired(required = false)
    private SupPoItemMapper poitemMapper;

    @Autowired(required = false)
    private PlnMlMapper mlMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 检良
     *
     * @param requestId 质检请求ID
     * @param requestItem 质检请求明细
     * @return 结果
     */
    public int registerGoodInspection(Long requestId, QuaInspectRequestItem requestItem) throws Exception{
        //状态防呆
        QuaInspectReDeal request=requestMapper.selectQuaInspectReDealById(requestItem.getRequestId());
        if (request.getStatus()!= BillStatus.ACTIVE_STARTED){
            String msg = "质检请求[" + request.getBillNo() + "]的状态不是[5-已收]";
            throw new StatusUnavailableException(msg);
        }

        //更新质检请求明细的合格数
        requestItem.setQuantityQcGood(requestItem.getQuantity());
        requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
        requestItem.refreshUpdatingStamp();
        requestItemMapper.updateQuaInspectRequestItem(requestItem);
        //刷新质检请求时间戳
        request.refreshUpdatingStamp();
        requestMapper.updateQuaInspectReDeal(request);
        //更新仓库交易请求明细的数量和状态
        SncStockRequestItem stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(requestItem.getSourceId());
        stockRequestItem.setQuantityQcGood(stockRequestItem.getQuantityQcGood());
        stockRequestItem.refreshUpdatingStamp();
        stockRequestItemMapper.updateSncStockRequestItem(stockRequestItem);
        //刷新仓库交易请求时间戳
        SncStockRequest stockRequest = stockRequestMapper.selectSncStockRequestById(stockRequestItem.getRequestId());
        stockRequest.refreshUpdatingStamp();
        stockRequestMapper.updateSncStockRequest(stockRequest);

        //更新质检请求、仓库交易请求的头状态
        boolean allInspectItemGood =true;
        List<QuaInspectRequestItem> inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
        if (inspectItemList!=null){
            for (QuaInspectRequestItem item:inspectItemList){
                allInspectItemGood = allInspectItemGood && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                if (!allInspectItemGood) break;
            }
        }
        if (allInspectItemGood){
            //仓库交易请求状态设为“待入库”
            stockRequest.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
            stockRequest.refreshUpdatingStamp();
            stockRequestMapper.updateSncStockRequest(stockRequest);
            //质检请求状态设为“已结案”
            request.setStatus(BillStatus.FINISH);
            request.refreshUpdatingStamp();
            requestMapper.updateQuaInspectReDeal(request);
        }

        //更新采购单明细的不良数
        SupPoItem poItem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
        poItem.setStatus(BillStatus.ACTIVE_PROCESSED);
        poItem.refreshUpdatingStamp();
        poitemMapper.updateSupPoItem(poItem);

        //更新采购单状态为“待入库”
        boolean allPoitemGood =true;
        List<SupPoItem> poitemList = poitemMapper.selectSupPoItemListByPoId(poItem.getPoId());
        if (poitemList!=null){
            for (SupPoItem item:poitemList){
                allPoitemGood = allPoitemGood && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                if (!allPoitemGood) break;
            }
        }
        if (allPoitemGood){
            SupPo po = poMapper.selectSupPoById(poItem.getPoId());
            po.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
            po.refreshUpdatingStamp();
            poMapper.updateSupPo(po);
        }

        return 1;
    }

    /**
     * 检异
     *
     * @param requestId 质检请求ID
     * @param requestItem 质检请求明细
     * @return 结果
     */
    public int registerExceptionalInspection(Long requestId, QuaInspectRequestItem requestItem) throws Exception{
        Integer dealType =null;
        double quantityExceptional =0;
        String description =null;
        Long categoryId =null;
        Long reasonTypeId =null;
        Long solutionTypeId =null;
        String reasonDesc =null;
        String solutionDesc =null;

        dealType = (Integer) requestItem.getParams().get("dealType");
        description = (String) requestItem.getParams().get("description");
        categoryId = requestItem.getParams().get("categoryId")==null?null:((Integer) requestItem.getParams().get("categoryId")).longValue();
        reasonTypeId = requestItem.getParams().get("reasonTypeId")==null?null:((Integer) requestItem.getParams().get("reasonTypeId")).longValue();
        solutionTypeId = requestItem.getParams().get("solutionTypeId")==null?null:((Integer) requestItem.getParams().get("solutionTypeId")).longValue();
        reasonDesc = (String) requestItem.getParams().get("reasonDesc");
        solutionDesc = (String) requestItem.getParams().get("solutionDesc");
        Object obj = requestItem.getParams().get("quantityExceptional");
        if (obj.getClass().equals(String.class)){
            quantityExceptional = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            quantityExceptional = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            quantityExceptional = ((Integer)obj).doubleValue();
        }

        QuaInspectReDeal request=requestMapper.selectQuaInspectReDealById(requestItem.getRequestId());
        //类型匹配防呆
        if (request.getInspectType()==InspectionType.RECEIVE_BUYING || request.getInspectType()==InspectionType.RECEIVE_SUBCONTRACT){
            if (dealType==InspectionDealType.RE_DISPATCHING || dealType==InspectionDealType.RE_WORK){
                String msg = "质检请求[" + request.getBillNo() + "]的质检类型与判处类型不匹配！";
                throw new TypeMismatchException(msg);
            }
        }
        if (request.getInspectType()==InspectionType.CHECK_SELF || request.getInspectType()==InspectionType.CHECK_IN_PROCESS
                || request.getInspectType()==InspectionType.RECEIVE_OUTERSUPPORT || request.getInspectType()==InspectionType.CHECK_OUTPUT
                || request.getInspectType()==InspectionType.CHECK_DELIVERY || request.getInspectType()==InspectionType.CHECK_OUTPUT){
            if (dealType==InspectionDealType.RETURN_BACK){
                String msg = "质检请求[" + request.getBillNo() + "]的质检类型与判处类型不匹配！";
                throw new TypeMismatchException(msg);
            }
        }
        //状态防呆
        if (request.getStatus()!= BillStatus.ACTIVE_STARTED){
            String msg = "质检请求[" + request.getBillNo() + "]的状态不是[5-已收]";
            throw new StatusUnavailableException(msg);
        }
        //数量过小防呆
        if (quantityExceptional <= 0){
            String msg = "质检请求[" + request.getBillNo() + "]的异常数须大于0！";
            throw new QuantityUnavailableException(msg);
        }
        //数量过大防呆
        if (dealType!=InspectionDealType.ACCEPT_ON_DEVIATION && dealType!=InspectionDealType.GOOD){
            if (requestItem.getQuantity().doubleValue() < quantityExceptional+requestItem.getQuantityQcNg().doubleValue()
                    +requestItem.getQuantityQcRedisp().doubleValue()+requestItem.getQuantityQcRework().doubleValue()+requestItem.getQuantityQcPending().doubleValue()){
                String msg = "质检请求[" + request.getBillNo() + "]的异常数超过可检数！";
                throw new QuantityUnavailableException(msg);
            }
        }

        //取更异常单号
        String billNo = billcodeManager.getNextBillno("QCE");
        billcodeManager.updateBillSeq("QCE");
        //创建异常单
        QuaExceptionDeal exception = new QuaExceptionDeal();
        //设置传带的基本字段
        exception.setBillNo(billNo);
        exception.setStatus(BillStatus.FINISH_WAITING);
        exception.setMaterialId(requestItem.getMaterialId());
        exception.setMaterialCode(requestItem.getMaterialCode());
        exception.setMaterialName(requestItem.getMaterialName());
        exception.setMaterialSpec(requestItem.getMaterialSpec());
        exception.setInspectType(request.getInspectType());
        exception.setUnitId(requestItem.getUnitId());
        //设置手输字段
        exception.setQuantity(new BigDecimal(quantityExceptional));
        exception.setDescription(description);
        //设置源请求之传带字段
        SncStockRequest stockRequest = stockRequestMapper.selectSncStockRequestById(request.getRequestBillId());
        SncStockRequestItem stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(requestItem.getSourceId());
        PlnMl ml=null;
        switch (request.getInspectType()){
            case InspectionType.RECEIVE_SUBCONTRACT:
                exception.setSupplierId(request.getSourceSupplierId());
                exception.setSupplierLotNo(stockRequestItem.getSourceLotNo());
                break;
            case InspectionType.RECEIVE_BUYING:
                exception.setSupplierId(request.getSourceSupplierId());
                exception.setSupplierLotNo(stockRequestItem.getSourceLotNo());
                break;
            case InspectionType.RECEIVE_OUTERSUPPORT:
                exception.setSupplierId(request.getSourceSupplierId());
                exception.setSupplierLotNo(stockRequestItem.getSourceLotNo());
                break;
            case InspectionType.CHECK_SELF:
                exception.setMlId(stockRequest.getSourceBillId());
                exception.setLineId(stockRequest.getSourceLineId());
                ml = mlMapper.selectPlnMlById(stockRequest.getSourceBillId());
                exception.setProcessSeq(ml.getCurrentProcessSeq());
                exception.setProcessId(ml.getCurrentProcessId());
                break;
            case InspectionType.CHECK_IN_PROCESS:
                exception.setMlId(stockRequest.getSourceBillId());
                exception.setLineId(stockRequest.getSourceLineId());
                ml = mlMapper.selectPlnMlById(stockRequest.getSourceBillId());
                exception.setProcessSeq(ml.getCurrentProcessSeq());
                exception.setProcessId(ml.getCurrentProcessId());
                break;
            case InspectionType.CHECK_OUTPUT:
                exception.setMlId(stockRequest.getSourceBillId());
                exception.setLineId(stockRequest.getSourceLineId());
                ml = mlMapper.selectPlnMlById(stockRequest.getSourceBillId());
                exception.setProcessSeq(ml.getCurrentProcessSeq());
                exception.setProcessId(ml.getCurrentProcessId());
                break;
            case InspectionType.CHECK_DELIVERY:
                break;
            case InspectionType.RECEIVE_DELIVERY_BACK:
                break;
            default:
        }
        //设置检验的戳
        exception.setInspectGroupId(SecurityUtils.getGroupId());
        exception.setInspectDeptId(SecurityUtils.getDeptId());
        exception.setInspectBy(SecurityUtils.getUserId());
        exception.setInspectTime(DateUtils.getNowDate());
        //设置判定
        exception.setDealType(dealType);
        exception.setDealQuantity(new BigDecimal(quantityExceptional));
        exception.setReasonDesc(reasonDesc);
        exception.setSolutionDesc(solutionDesc);
        switch (request.getInspectType()){
            case InspectionType.RECEIVE_SUBCONTRACT:
                exception.setPcategoryId(categoryId);
                exception.setPreasonTypeId(reasonTypeId);
                exception.setPsolutionTypeId(solutionTypeId);
                break;
            case InspectionType.RECEIVE_BUYING:
                exception.setPcategoryId(categoryId);
                exception.setPreasonTypeId(reasonTypeId);
                exception.setPsolutionTypeId(solutionTypeId);
                break;
            case InspectionType.RECEIVE_OUTERSUPPORT:
                exception.setPcategoryId(categoryId);
                exception.setPreasonTypeId(reasonTypeId);
                exception.setPsolutionTypeId(solutionTypeId);
                break;
            case InspectionType.CHECK_SELF:
                exception.setMcategoryId(categoryId);
                exception.setMreasonTypeId(reasonTypeId);
                exception.setMsolutionTypeId(solutionTypeId);
                break;
            case InspectionType.CHECK_IN_PROCESS:
                exception.setMcategoryId(categoryId);
                exception.setMreasonTypeId(reasonTypeId);
                exception.setMsolutionTypeId(solutionTypeId);
                break;
            case InspectionType.CHECK_OUTPUT:
                exception.setMcategoryId(categoryId);
                exception.setMreasonTypeId(reasonTypeId);
                exception.setMsolutionTypeId(solutionTypeId);
                break;
            case InspectionType.CHECK_DELIVERY:
                break;
            case InspectionType.RECEIVE_DELIVERY_BACK:
                break;
            default:
        }
        //责任部门 TODO 改成可输入
        exception.setDutyProcessId(ml!=null?ml.getCurrentProcessId():null);
        exception.setDutyLineId(ml!=null?ml.getLineId():null);
        exception.setDutyGroupId(ml!=null?ml.getCurrentWorkgroupId():null);
        //设置异常判处的戳
        exception.setInspectBy(SecurityUtils.getUserId());
        exception.setInspectTime(DateUtils.getNowDate());
        //保存
        exception.refreshCreatingStamp();
        exceptionMapper.insertQuaExceptionDeal(exception);

        //更新质检请求明细的异常数
        boolean allInspectItemProcessed =true;
         List<QuaInspectRequestItem> inspectItemList = null;
        switch (dealType){
            case InspectionDealType.GOOD:
                requestItem.setQuantityQcGood(new BigDecimal(requestItem.getQuantityQcGood().doubleValue()+quantityExceptional));
                requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateQuaInspectRequestItem(requestItem);
                //更新质检请求、仓库交易请求的头状态
                allInspectItemProcessed =true;
                inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
                if (inspectItemList!=null){
                    for (QuaInspectRequestItem item:inspectItemList){
                        allInspectItemProcessed = allInspectItemProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                        if (!allInspectItemProcessed) break;
                    }
                }
                if (allInspectItemProcessed){
                    //质检请求状态设为“已结案”
                    request.setStatus(BillStatus.FINISH);
                    request.refreshUpdatingStamp();
                    requestMapper.updateQuaInspectReDeal(request);
                };
                break;
            case InspectionDealType.SCRAP:
                requestItem.setQuantityQcNg(new BigDecimal(requestItem.getQuantityQcNg().doubleValue()+quantityExceptional));
                requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateQuaInspectRequestItem(requestItem);
                //更新质检请求、仓库交易请求的头状态
                allInspectItemProcessed =true;
                inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
                if (inspectItemList!=null){
                    for (QuaInspectRequestItem item:inspectItemList){
                        allInspectItemProcessed = allInspectItemProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                        if (!allInspectItemProcessed) break;
                    }
                }
                if (allInspectItemProcessed){
                    //质检请求状态设为“已结案”
                    request.setStatus(BillStatus.FINISH);
                    request.refreshUpdatingStamp();
                    requestMapper.updateQuaInspectReDeal(request);
                };
                break;
            case InspectionDealType.RE_DISPATCHING:
                requestItem.setQuantityQcRedisp(new BigDecimal(requestItem.getQuantityQcRedisp().doubleValue()+quantityExceptional));
                requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateQuaInspectRequestItem(requestItem);
                //更新质检请求、仓库交易请求的头状态
                allInspectItemProcessed =true;
                inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
                if (inspectItemList!=null){
                    for (QuaInspectRequestItem item:inspectItemList){
                        allInspectItemProcessed = allInspectItemProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                        if (!allInspectItemProcessed) break;
                    }
                }
                if (allInspectItemProcessed){
                    //质检请求状态设为“已结案”
                    request.setStatus(BillStatus.FINISH);
                    request.refreshUpdatingStamp();
                    requestMapper.updateQuaInspectReDeal(request);
                };
                break;
            case InspectionDealType.RE_WORK:
                requestItem.setQuantityQcRework(new BigDecimal(requestItem.getQuantityQcRework().doubleValue()+quantityExceptional));
                requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateQuaInspectRequestItem(requestItem);
                //更新质检请求、仓库交易请求的头状态
                allInspectItemProcessed =true;
                inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
                if (inspectItemList!=null){
                    for (QuaInspectRequestItem item:inspectItemList){
                        allInspectItemProcessed = allInspectItemProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                        if (!allInspectItemProcessed) break;
                    }
                }
                if (allInspectItemProcessed){
                    //质检请求状态设为“已结案”
                    request.setStatus(BillStatus.FINISH);
                    request.refreshUpdatingStamp();
                    requestMapper.updateQuaInspectReDeal(request);
                };
                break;
            case InspectionDealType.PENDING:
                requestItem.setQuantityQcPending(new BigDecimal(requestItem.getQuantityQcPending().doubleValue()+quantityExceptional));
                requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateQuaInspectRequestItem(requestItem);
                //更新质检请求、仓库交易请求的头状态
                allInspectItemProcessed =true;
                inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
                if (inspectItemList!=null){
                    for (QuaInspectRequestItem item:inspectItemList){
                        allInspectItemProcessed = allInspectItemProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                        if (!allInspectItemProcessed) break;
                    }
                }
                if (allInspectItemProcessed){
                    //质检请求状态设为“已结案”
                    request.setStatus(BillStatus.FINISH);
                    request.refreshUpdatingStamp();
                    requestMapper.updateQuaInspectReDeal(request);
                };
                break;
            case InspectionDealType.ACCEPT_ON_DEVIATION:
                requestItem.setQuantityQcAod(new BigDecimal(requestItem.getQuantityQcAod().doubleValue()+quantityExceptional));
                requestItem.setStatus(BillStatus.ACTIVE_PROCESSED);
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateQuaInspectRequestItem(requestItem);
                //更新质检请求、仓库交易请求的头状态
                allInspectItemProcessed =true;
                inspectItemList = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
                if (inspectItemList!=null){
                    for (QuaInspectRequestItem item:inspectItemList){
                        allInspectItemProcessed = allInspectItemProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSED;
                        if (!allInspectItemProcessed) break;
                    }
                }
                if (allInspectItemProcessed){
                    //质检请求状态设为“已结案”
                    request.setStatus(BillStatus.FINISH);
                    request.refreshUpdatingStamp();
                    requestMapper.updateQuaInspectReDeal(request);
                };
                break;
            case InspectionDealType.RETURN_BACK:
                break;
            default:
        }

        //更新请检者的异常数据
        switch (request.getInspectType()){
            case InspectionType.RECEIVE_SUBCONTRACT:
                this.updateExceptionalInpectingData4BuyReceiving(requestItem, quantityExceptional, dealType);
                break;
            case InspectionType.RECEIVE_BUYING:
                this.updateExceptionalInpectingData4BuyReceiving(requestItem, quantityExceptional, dealType);
                break;
            case InspectionType.RECEIVE_OUTERSUPPORT:
                this.updateExceptionalInpectingData4BuyReceiving(requestItem, quantityExceptional, dealType);
                break;
            case InspectionType.CHECK_SELF:
                break;
            case InspectionType.CHECK_IN_PROCESS:
                break;
            case InspectionType.CHECK_OUTPUT:
                break;
            case InspectionType.CHECK_DELIVERY:
                break;
            case InspectionType.RECEIVE_DELIVERY_BACK:
                break;
            default:
        }

        return 1;
    }

    /**
     * 请判
     *
     * @param requestItem 质检请求明细
     * @return 结果
     */
    public int requestJugde(QuaInspectRequestItem requestItem) throws Exception{
        //防呆检查
        QuaInspectReDeal request=requestMapper.selectQuaInspectReDealById(requestItem.getRequestId());
        if (request.getStatus()!= BillStatus.ACTIVE_STARTED){
            String msg = "质检请求[" + request.getBillNo() + "]的状态不是[5-已收]";
            throw new StatusUnavailableException(msg);
        }

        double quantityExceptional =0;
        String description =null;
        description = (String) requestItem.getParams().get("description");
        Object obj = requestItem.getParams().get("quantityExceptional");
        if (obj.getClass().equals(String.class)){
            quantityExceptional = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            quantityExceptional = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            quantityExceptional = ((Integer)obj).doubleValue();
        }

        //取更异常单号
        String billNo = billcodeManager.getNextBillno("QCE");
        billcodeManager.updateBillSeq("QCE");
        //创建异常单
        QuaExceptionDeal exception = new QuaExceptionDeal();
        //设置传带的基本字段
        exception.setBillNo(billNo);
        exception.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
        exception.setMaterialId(requestItem.getMaterialId());
        exception.setMaterialCode(requestItem.getMaterialCode());
        exception.setMaterialName(requestItem.getMaterialName());
        exception.setMaterialSpec(requestItem.getMaterialSpec());
        exception.setInspectType(request.getInspectType());
        exception.setUnitId(requestItem.getUnitId());
        //设置手输字段
        exception.setQuantity(new BigDecimal(quantityExceptional));
        exception.setDescription(description);
        //设置源请求之传带字段
        SncStockRequest stockRequest = stockRequestMapper.selectSncStockRequestById(request.getRequestBillId());
        SncStockRequestItem stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(requestItem.getSourceId());
        PlnMl ml=null;
        switch (request.getInspectType()){
            case InspectionType.RECEIVE_SUBCONTRACT:
                exception.setSupplierId(request.getSourceSupplierId());
                exception.setSupplierLotNo(stockRequestItem.getSourceLotNo());
                break;
            case InspectionType.RECEIVE_BUYING:
                exception.setSupplierId(request.getSourceSupplierId());
                exception.setSupplierLotNo(stockRequestItem.getSourceLotNo());
                break;
            case InspectionType.RECEIVE_OUTERSUPPORT:
                exception.setSupplierId(request.getSourceSupplierId());
                exception.setSupplierLotNo(stockRequestItem.getSourceLotNo());
                break;
            case InspectionType.CHECK_SELF:
                exception.setMlId(stockRequest.getSourceBillId());
                exception.setLineId(stockRequest.getSourceLineId());
                ml = mlMapper.selectPlnMlById(stockRequest.getSourceBillId());
                exception.setProcessSeq(ml.getCurrentProcessSeq());
                exception.setProcessId(ml.getCurrentProcessId());
                break;
            case InspectionType.CHECK_IN_PROCESS:
                exception.setMlId(stockRequest.getSourceBillId());
                exception.setLineId(stockRequest.getSourceLineId());
                ml = mlMapper.selectPlnMlById(stockRequest.getSourceBillId());
                exception.setProcessSeq(ml.getCurrentProcessSeq());
                exception.setProcessId(ml.getCurrentProcessId());
                break;
            case InspectionType.CHECK_OUTPUT:
                exception.setMlId(stockRequest.getSourceBillId());
                exception.setLineId(stockRequest.getSourceLineId());
                ml = mlMapper.selectPlnMlById(stockRequest.getSourceBillId());
                exception.setProcessSeq(ml.getCurrentProcessSeq());
                exception.setProcessId(ml.getCurrentProcessId());
                break;
            case InspectionType.CHECK_DELIVERY:
                break;
            case InspectionType.RECEIVE_DELIVERY_BACK:
                break;
            default:
        }
        //设置请判的戳
        exception.setInspectGroupId(SecurityUtils.getGroupId());
        exception.setInspectDeptId(SecurityUtils.getDeptId());
        exception.setInspectBy(SecurityUtils.getUserId());
        exception.setInspectTime(DateUtils.getNowDate());
        //保存
        exception.refreshCreatingStamp();
        exceptionMapper.insertQuaExceptionDeal(exception);

        //更新请检单和请检者的异常数据
        switch (request.getInspectType()){
            case InspectionType.RECEIVE_SUBCONTRACT:
                this.updateRquestJugdeData4BuyReceiving(request, requestItem, quantityExceptional);
                break;
            case InspectionType.RECEIVE_BUYING:
                this.updateRquestJugdeData4BuyReceiving(request, requestItem, quantityExceptional);
                break;
            case InspectionType.RECEIVE_OUTERSUPPORT:
                this.updateRquestJugdeData4BuyReceiving(request, requestItem, quantityExceptional);
                break;
            case InspectionType.CHECK_SELF:
                break;
            case InspectionType.CHECK_IN_PROCESS:
                break;
            case InspectionType.CHECK_OUTPUT:
                break;
            case InspectionType.CHECK_DELIVERY:
                break;
            case InspectionType.RECEIVE_DELIVERY_BACK:
                break;
            default:
        }

        return 1;
    }

    private void updateExceptionalInpectingData4BuyReceiving(QuaInspectRequestItem requestItem, double quantityDeal, Integer dealType){
        SncStockRequestItem stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(requestItem.getSourceId());

        SncStockRequest stockRequest=null;
        SupPo po=null;
        SupPoItem poItem=null;
        switch (dealType){
            case InspectionDealType.SCRAP:
                //更新仓库交易请求明细的数量和状态
                stockRequestItem.setQuantityQcNg(new BigDecimal(stockRequestItem.getQuantityQcNg().doubleValue()+quantityDeal));
                stockRequestItem.refreshUpdatingStamp();
                stockRequestItemMapper.updateSncStockRequestItem(stockRequestItem);
                //更新仓库交易请求状态
                stockRequest = stockRequestMapper.selectSncStockRequestById(stockRequestItem.getRequestId());
                stockRequest.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                stockRequest.refreshUpdatingStamp();
                stockRequestMapper.updateSncStockRequest(stockRequest);
                //更新采购单明细的不良数
                poItem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
                poItem.setQuantityQcNg(new BigDecimal(poItem.getQuantityQcNg().doubleValue()+quantityDeal));
                poItem.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                poItem.refreshUpdatingStamp();
                poitemMapper.updateSupPoItem(poItem);
                //更新采购单状态为“请检中”
                po = poMapper.selectSupPoById(poItem.getPoId());
                po.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                po.refreshUpdatingStamp();
                poMapper.updateSupPo(po);
                break;
            case InspectionDealType.PENDING:
                //更新仓库交易请求明细的数量和状态
                stockRequestItem.setQuantityQcPending(new BigDecimal(stockRequestItem.getQuantityQcPending().doubleValue()+quantityDeal));
                stockRequestItem.refreshUpdatingStamp();
                stockRequestItemMapper.updateSncStockRequestItem(stockRequestItem);
                //更新仓库交易请求状态
                stockRequest = stockRequestMapper.selectSncStockRequestById(stockRequestItem.getRequestId());
                stockRequest.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                stockRequest.refreshUpdatingStamp();
                stockRequestMapper.updateSncStockRequest(stockRequest);
                //更新采购单明细的待判数
                poItem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
                poItem.setQuantityQcPending(new BigDecimal(poItem.getQuantityQcPending().doubleValue()+quantityDeal));
                poItem.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                poItem.refreshUpdatingStamp();
                poitemMapper.updateSupPoItem(poItem);
                //更新采购单状态为“请检中”
                po = poMapper.selectSupPoById(poItem.getPoId());
                po.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                po.refreshUpdatingStamp();
                poMapper.updateSupPo(po);
                break;
            case InspectionDealType.ACCEPT_ON_DEVIATION:
                //更新仓库交易请求明细的数量和状态
                stockRequestItem.setQuantityQcAod(new BigDecimal(stockRequestItem.getQuantityQcAod().doubleValue()+quantityDeal));
                stockRequestItem.refreshUpdatingStamp();
                stockRequestItemMapper.updateSncStockRequestItem(stockRequestItem);
                //更新仓库交易请求状态
                stockRequest = stockRequestMapper.selectSncStockRequestById(stockRequestItem.getRequestId());
                stockRequest.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                stockRequest.refreshUpdatingStamp();
                stockRequestMapper.updateSncStockRequest(stockRequest);
                //更新采购单明细的特采数
                poItem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
                poItem.setQuantityQcAod(new BigDecimal(poItem.getQuantityQcAod().doubleValue()+quantityDeal));
                poItem.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                poItem.refreshUpdatingStamp();
                poitemMapper.updateSupPoItem(poItem);
                //更新采购单状态为“请检中”
                po = poMapper.selectSupPoById(poItem.getPoId());
                po.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
                po.refreshUpdatingStamp();
                poMapper.updateSupPo(po);
                break;
        }
    }

    private void updateRquestJugdeData4BuyReceiving(QuaInspectReDeal request, QuaInspectRequestItem requestItem, double quantityExceptional){
        //更新质检请求明细的异常数
        requestItem.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
        requestItem.setQuantityQcExceptional(new BigDecimal(requestItem.getQuantityQcExceptional().doubleValue()+quantityExceptional));
        requestItemMapper.updateQuaInspectRequestItem(requestItem);
        //更新质检请求状态为待判异（检验完成 判定有待）
        request.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
        request.refreshUpdatingStamp();
        requestMapper.updateQuaInspectReDeal(request);

        //更新出入库请求的异常数
        SncStockRequestItem stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(requestItem.getSourceId());
        stockRequestItem.setQuantityQcExceptional(new BigDecimal(stockRequestItem.getQuantityQcExceptional().doubleValue()+quantityExceptional));
        stockRequestItem.refreshUpdatingStamp();
        stockRequestItemMapper.updateSncStockRequestItem(stockRequestItem);
        //更新仓库交易请求状态
        SncStockRequest stockRequest = stockRequestMapper.selectSncStockRequestById(stockRequestItem.getRequestId());
        stockRequest.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
        stockRequest.refreshUpdatingStamp();
        stockRequestMapper.updateSncStockRequest(stockRequest);

        //更新采购单明细的状态为有异常
        SupPoItem poItem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
        poItem.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
        poItem.refreshUpdatingStamp();
        poitemMapper.updateSupPoItem(poItem);
        //更新采购单状态为“请检中”
        SupPo po = poMapper.selectSupPoById(poItem.getPoId());
        po.setStatus(BillStatus.ACTIVE_EXCEPTIONAL);
        po.refreshUpdatingStamp();
        poMapper.updateSupPo(po);
    }
}

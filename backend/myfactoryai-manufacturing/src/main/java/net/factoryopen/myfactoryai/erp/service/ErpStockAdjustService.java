package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.StockAdjustRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpStockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpStockAdjustService {
    private final ErpStockMapper mapper;
    private final ErpStockService stock;
    private final ErpWorkflowService workflow;
    public ErpStockAdjustService(ErpStockMapper mapper,ErpStockService stock,ErpWorkflowService workflow) {
        this.mapper=mapper; this.stock=stock; this.workflow=workflow;
    }

    @Transactional
    public Map<String,Object> adjust(StockAdjustRequest request) {
        if (request == null || request.itemType() == null || (request.itemType() != 1 && request.itemType() != 2)
                || request.itemId() == null || request.batchNo() == null || request.batchNo().length() > 30
                || request.actualQty() == null || request.actualQty().signum() < 0 || request.actualQty().scale() > 3
                || request.reason() == null || request.reason().isBlank() || request.reason().length() > 255)
            throw new ServiceException("盘点对象、数量或原因不合法", ErpErrorCode.INVALID_PARAM.code());
        // 盘点会改变库存与成本口径，已结账期间不允许再调
        workflow.assertOpen(LocalDate.now());
        if (stock.begin(request.requestKey(),"stock_adjust","inventory_item",request.itemId(),request))
            return Map.of("itemId",request.itemId().toString(),"actualQty",request.actualQty());
        int exists = request.itemType() == 1 ? mapper.materialExists(request.itemId()) : mapper.skuExists(request.itemId());
        if (exists != 1) throw new ServiceException("盘点对象不存在", ErpErrorCode.INVALID_PARAM.code());
        String batch = request.batchNo().trim();
        BigDecimal current = mapper.lockQuantity(request.itemType(),request.itemId(),batch);
        if (current == null) current = BigDecimal.ZERO;
        BigDecimal delta = request.actualQty().subtract(current);
        if (delta.signum() > 0) {
            mapper.addStock(request.itemType(),request.itemId(),batch,delta,SecurityUtils.getUsername());
            stock.txn(1,5,request.itemType(),request.itemId(),batch,delta,"stock_check",request.itemId(),null,request.requestKey(),LocalDate.now(),request.reason().trim());
        } else if (delta.signum() < 0) {
            if (mapper.subtractStock(request.itemType(),request.itemId(),batch,delta.abs()) != 1) throw new ServiceException("库存已变化，请刷新后重试", ErpErrorCode.INSUFFICIENT.code());
            stock.txn(2,6,request.itemType(),request.itemId(),batch,delta.abs(),"stock_check",request.itemId(),null,request.requestKey(),LocalDate.now(),request.reason().trim());
        }
        stock.complete(request.requestKey(),request.itemId());
        return Map.of("itemId",request.itemId().toString(),"previousQty",current,"actualQty",request.actualQty(),"delta",delta);
    }
}

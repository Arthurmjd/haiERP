package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.CostRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpCostMapper;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpCostService {
    private final ErpCostMapper mapper;
    private final ErpStockService stock;
    public ErpCostService(ErpCostMapper mapper,ErpStockService stock) { this.mapper=mapper; this.stock=stock; }

    public Map<String,Object> detail(Long id) {
        Map<String,Object> header = mapper.detail(id);
        if (header == null) throw new ServiceException("结算单不存在",1001);
        Map<String,Object> result = new HashMap<>(header);
        result.put("details",mapper.details(id)); result.put("costBasis","standard");
        return result;
    }

    @Transactional
    public Map<String,String> calculate(CostRequest request) {
        if (request == null || request.orderId() == null || request.styleId() == null) throw new ServiceException("订单和款式必填",1001);
        if (stock.begin(request.requestKey(),"settlement_calc","sd_order",request.orderId(),request)) {
            Map<String,Object> previous = mapper.lockSettlement(request.orderId(),request.styleId());
            if (previous == null) throw new ServiceException("请求记录与结算单不一致",2007);
            return Map.of("settleId",number(previous,"settleId").toString());
        }
        Integer orderStatus = mapper.lockOrder(request.orderId());
        if (orderStatus == null || orderStatus == 9) throw new ServiceException("订单不存在或已取消",2001);
        Map<String,Object> order = mapper.orderStyle(request.orderId(),request.styleId());
        if (order == null || order.get("qty") == null) throw new ServiceException("订单中没有该款式",1001);
        int qty = number(order,"qty").intValue();
        if (qty <= 0) throw new ServiceException("订单数量不合法",1001);
        Map<String,Object> wo = mapper.workOrder(request.orderId(),request.styleId());
        List<Map<String,Object>> materials = wo == null ? mapper.estimatedMaterials(request.styleId(),qty) : mapper.frozenMaterials(number(wo,"woId"));
        List<Map<String,Object>> processes = wo == null ? mapper.estimatedProcesses(request.styleId(),qty)
                : mapper.frozenProcesses(number(wo,"woId"),number(wo,"status").intValue() == 2 ? 1 : 0);
        if (materials.isEmpty() || processes.isEmpty()) throw new ServiceException("BOM 或工序缺失，无法试算",2005);
        Map<String,Object> settlement = mapper.lockSettlement(request.orderId(),request.styleId());
        if (settlement != null && number(settlement,"status").intValue() != 0) throw new ServiceException("已确认结算不得重算",2001);
        Long id;
        BigDecimal overheadRate = settlement == null ? BigDecimal.valueOf(8) : decimal(settlement,"overheadRate");
        if (settlement == null) {
            Map<String,Object> header = new HashMap<>();
            header.put("settleNo",DocNos.temp("CS"));
            header.put("orderId",request.orderId()); header.put("styleId",request.styleId());
            header.put("qty",qty); header.put("username",SecurityUtils.getUsername());
            mapper.insert(header); id=number(header,"settleId");
            mapper.setNo(id,DocNos.formal("CS",id));
        } else { id=number(settlement,"settleId"); mapper.deleteDetails(id); }
        BigDecimal materialCost = insertLines(id,1,materials);
        BigDecimal laborCost = insertLines(id,2,processes);
        BigDecimal overheadCost = materialCost.add(laborCost).multiply(overheadRate).movePointLeft(2).setScale(2,RoundingMode.HALF_UP);
        mapper.insertDetail(id,3,0L,BigDecimal.ONE,overheadCost,overheadCost,"frozen_overhead_rate",SecurityUtils.getUsername());
        BigDecimal total = materialCost.add(laborCost).add(overheadCost);
        BigDecimal revenue = decimal(order,"revenue");
        BigDecimal profit = revenue.subtract(total);
        Map<String,Object> totals = new HashMap<>();
        totals.put("settleId",id); totals.put("qty",qty); totals.put("materialCost",materialCost);
        totals.put("laborCost",laborCost); totals.put("overheadRate",overheadRate);
        totals.put("overheadCost",overheadCost); totals.put("totalCost",total);
        totals.put("unitCost",total.divide(BigDecimal.valueOf(qty),2,RoundingMode.HALF_UP));
        totals.put("revenue",revenue); totals.put("grossProfit",profit);
        totals.put("grossMargin",revenue.signum() == 0 ? BigDecimal.ZERO : profit.multiply(BigDecimal.valueOf(100)).divide(revenue,2,RoundingMode.HALF_UP));
        totals.put("username",SecurityUtils.getUsername());
        if (mapper.updateTotals(totals) != 1) throw new ServiceException("结算单状态已变化",2001);
        stock.complete(request.requestKey(),id);
        return Map.of("settleId",id.toString());
    }

    @Transactional
    public void confirm(Long id) {
        if (id == null) throw new ServiceException("结算单 ID 必填",1001);
        Map<String,Object> snapshot = mapper.detail(id);
        if (snapshot == null) throw new ServiceException("结算单不存在",1001);
        Long orderId = Long.valueOf((String) snapshot.get("orderId"));
        mapper.lockOrder(orderId);
        Map<String,Object> row = mapper.lockById(id);
        if (row == null || number(row,"status").intValue() != 0) throw new ServiceException("结算单状态不允许确认",2001);
        Long styleId = number(row,"styleId");
        Map<String,Object> wo = mapper.workOrder(orderId,styleId);
        if (wo == null || number(wo,"status").intValue() != 2 || mapper.unshippedStyle(orderId,styleId) != 0) throw new ServiceException("工单未完工或该款未发齐",2001);
        if (mapper.detailTotal(id).compareTo(decimal(row,"totalCost")) != 0) throw new ServiceException("成本明细与汇总不一致",2006);
        if (mapper.confirm(id,SecurityUtils.getUsername()) != 1) throw new ServiceException("结算单状态已变化",2001);
    }

    private BigDecimal insertLines(Long id,int type,List<Map<String,Object>> lines) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String,Object> line : lines) {
            BigDecimal qty = decimal(line,"qty"); BigDecimal price = decimal(line,"unitPrice");
            if (qty.signum() < 0 || price.signum() < 0) throw new ServiceException("成本数量或单价不合法",2005);
            BigDecimal amount = qty.multiply(price).setScale(2,RoundingMode.HALF_UP);
            mapper.insertDetail(id,type,number(line,"sourceId"),qty,price,amount,(String)line.get("priceSource"),SecurityUtils.getUsername());
            total = total.add(amount);
        }
        return total;
    }

    private static Long number(Map<String,Object> row,String key) { return ((Number)row.get(key)).longValue(); }
    private static BigDecimal decimal(Map<String,Object> row,String key) { return new BigDecimal(row.get(key).toString()); }
}

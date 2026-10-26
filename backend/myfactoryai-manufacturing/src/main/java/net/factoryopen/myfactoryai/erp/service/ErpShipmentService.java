package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.ShipmentRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpShipmentMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpStockMapper;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpShipmentService {
    private final ErpShipmentMapper mapper;
    private final ErpStockMapper stockMapper;
    private final ErpStockService stock;
    private final ErpFinanceService finance;
    private final ErpWorkflowService workflow;

    public ErpShipmentService(ErpShipmentMapper mapper, ErpStockMapper stockMapper, ErpStockService stock,
                              ErpFinanceService finance, ErpWorkflowService workflow) {
        this.mapper = mapper; this.stockMapper = stockMapper; this.stock = stock;
        this.finance = finance; this.workflow = workflow;
    }

    public Map<String,Object> shipment(Long id) {
        Map<String,Object> header = mapper.shipmentDetail(id);
        if (header == null) throw new ServiceException("出货单不存在",1001);
        Map<String,Object> result = new HashMap<>(header);
        result.put("items",mapper.shipmentDetailLines(id));
        return result;
    }

    public Map<String,Object> returned(Long id) {
        Map<String,Object> header = mapper.returnDetail(id);
        if (header == null) throw new ServiceException("退货单不存在",1001);
        Map<String,Object> result = new HashMap<>(header);
        result.put("items",mapper.returnDetailLines(id));
        return result;
    }

    @Transactional
    public Map<String,String> create(ShipmentRequest.Create request) {
        if (request == null || request.orderId() == null) throw new ServiceException("销售订单必填",1001);
        List<ShipmentRequest.Line> lines = validLines(request.items());
        Map<String,Object> order = mapper.lockOrder(request.orderId());
        if (order == null || !List.of(2,3).contains(number(order,"status").intValue())) throw new ServiceException("订单状态不允许出货",2001);
        Map<Long,Integer> ordered = quantities(mapper.ordered(request.orderId()));
        Map<Long,Integer> requested = new HashMap<>();
        for (ShipmentRequest.Line line : lines) requested.merge(line.skuId(),line.qty(),Integer::sum);
        for (Map.Entry<Long,Integer> entry : requested.entrySet()) {
            int remaining = ordered.getOrDefault(entry.getKey(),0) - mapper.shipped(request.orderId(),entry.getKey());
            if (remaining < entry.getValue()) throw new ServiceException("出货超过订单未发数量",2006);
        }
        Map<String,Object> header = new HashMap<>();
        header.put("shipmentNo",DocNos.temp("SH"));
        header.put("orderId",request.orderId()); header.put("customerId",number(order,"customerId"));
        header.put("logisticsCompany",request.logisticsCompany()); header.put("logisticsNo",request.logisticsNo());
        header.put("username",SecurityUtils.getUsername());
        mapper.insertShipment(header);
        Long id = number(header,"shipmentId");
        String no = DocNos.formal("SH",id);
        mapper.setShipmentNo(id,no);
        for (ShipmentRequest.Line line : lines) mapper.insertShipmentLine(id,line.skuId(),line.batchNo().trim(),line.qty(),SecurityUtils.getUsername());
        return Map.of("shipmentId",id.toString(),"shipmentNo",no);
    }

    @Transactional
    public void ship(Long id,ShipmentRequest.Action request) {
        if (id == null || request == null) throw new ServiceException("出货单或请求键缺失",1001);
        workflow.assertOpen(LocalDate.now());
        if (stock.begin(request.requestKey(),"shipment_ship","sh_shipment",id,Map.of("shipmentId",id))) return;
        Map<String,Object> snapshot = mapper.shipmentDetail(id);
        if (snapshot == null) throw new ServiceException("出货单不存在",1001);
        Long orderId = Long.valueOf((String) snapshot.get("orderId"));
        Map<String,Object> order = mapper.lockOrder(orderId);
        Map<String,Object> shipment = mapper.lockShipment(id);
        if (order == null || shipment == null || number(shipment,"status").intValue() != 0 || !List.of(2,3).contains(number(order,"status").intValue())) throw new ServiceException("状态不允许出货",2001);
        List<Map<String,Object>> lines = mapper.shipmentLines(id);
        if (lines.isEmpty()) throw new ServiceException("出货单无明细",1001);
        Map<Long,Integer> ordered = quantities(mapper.ordered(orderId));
        Map<Long,Integer> requested = new HashMap<>();
        for (Map<String,Object> line : lines) requested.merge(number(line,"skuId"),number(line,"qty").intValue(),Integer::sum);
        for (Map.Entry<Long,Integer> entry : requested.entrySet()) {
            if (mapper.shipped(orderId,entry.getKey()) + entry.getValue() > ordered.getOrDefault(entry.getKey(),0)) throw new ServiceException("出货超过订单未发数量",2006);
        }
        for (Map<String,Object> line : lines) {
            Long skuId = number(line,"skuId");
            String batch = (String) line.get("batchNo");
            BigDecimal qty = BigDecimal.valueOf(number(line,"qty"));
            if (stockMapper.subtractStock(2,skuId,batch,qty) != 1) throw new ServiceException("成品库存不足",2003);
            stock.txn(2,4,2,skuId,batch,qty,"sh_shipment",id,number(line,"itemId"),request.requestKey(),LocalDate.now());
        }
        if (mapper.ship(id,SecurityUtils.getUsername()) != 1) throw new ServiceException("出货状态已变化",2001);
        boolean full = true;
        for (Map.Entry<Long,Integer> entry : ordered.entrySet()) {
            if (mapper.shipped(orderId,entry.getKey()) < entry.getValue()) { full = false; break; }
        }
        if (full) mapper.markOrderShipped(orderId,SecurityUtils.getUsername());
        stock.complete(request.requestKey(),id);
        Long customerId = number(shipment,"customerId");
        finance.createShipmentReceivable(orderId,id,customerId,"ship:" + request.requestKey());
        workflow.complete("ship","sh_shipment",id);
        if (full) workflow.complete("produce","sd_order",orderId);
    }

    @Transactional
    public void sign(Long id) {
        Map<String,Object> snapshot = mapper.shipmentDetail(id);
        if (snapshot == null) throw new ServiceException("出货单不存在",1001);
        Long orderId = Long.valueOf((String) snapshot.get("orderId"));
        mapper.lockOrder(orderId);
        Map<String,Object> shipment = mapper.lockShipment(id);
        if (shipment == null || number(shipment,"status").intValue() != 1 || mapper.sign(id,SecurityUtils.getUsername()) != 1) throw new ServiceException("出货单状态不允许签收",2001);
        if (mapper.unsignedShipments(orderId) == 0) mapper.completeOrder(orderId,SecurityUtils.getUsername());
        workflow.complete("sign","sh_shipment",id);
    }

    @Transactional
    public Map<String,String> createReturn(ShipmentRequest.ReturnCreate request) {
        if (request == null || request.shipmentId() == null || request.returnType() == null || request.isRestock() == null
                || !List.of(1,2,3).contains(request.returnType()) || !List.of(0,1).contains(request.isRestock())
                || (request.returnType() == 3 && request.isRestock() != 0)
                || (request.returnType() != 3 && request.isRestock() != 1)
                || request.refundAmount() == null || request.refundAmount().signum() < 0
                || (request.returnType() == 2 && request.refundAmount().signum() != 0)) throw new ServiceException("退货类型、回库或退款金额不合法",1001);
        List<ShipmentRequest.Line> lines = validLines(request.items());
        Map<String,Object> shipment = mapper.lockShipment(request.shipmentId());
        if (shipment == null || !List.of(1,2).contains(number(shipment,"status").intValue())) throw new ServiceException("原出货单尚未出库",2001);
        validateReturnLines(request.shipmentId(),lines);
        Map<String,Object> header = new HashMap<>();
        header.put("returnNo",DocNos.temp("RT"));
        header.put("shipmentId",request.shipmentId()); header.put("orderId",number(shipment,"orderId"));
        header.put("customerId",number(shipment,"customerId")); header.put("reason",request.reason());
        header.put("returnType",request.returnType()); header.put("isRestock",request.isRestock());
        header.put("refundAmount",request.refundAmount()); header.put("username",SecurityUtils.getUsername());
        mapper.insertReturn(header);
        Long id = number(header,"returnId");
        String no = DocNos.formal("RT",id);
        mapper.setReturnNo(id,no);
        for (ShipmentRequest.Line line : lines) mapper.insertReturnLine(id,line.skuId(),line.batchNo().trim(),line.qty(),SecurityUtils.getUsername());
        return Map.of("returnId",id.toString(),"returnNo",no);
    }

    @Transactional
    public void handleReturn(Long id,ShipmentRequest.Action request) {
        if (id == null || request == null) throw new ServiceException("退货单或请求键缺失",1001);
        workflow.assertOpen(LocalDate.now());
        if (stock.begin(request.requestKey(),"return_handle","sh_return",id,Map.of("returnId",id))) return;
        Map<String,Object> snapshot = mapper.returnDetail(id);
        if (snapshot == null) throw new ServiceException("退货单不存在",1001);
        Long shipmentId = Long.valueOf((String) snapshot.get("shipmentId"));
        mapper.lockShipment(shipmentId);
        Map<String,Object> returned = mapper.lockReturn(id);
        if (returned == null || number(returned,"status").intValue() != 0) throw new ServiceException("退货单状态不允许处理",2001);
        List<Map<String,Object>> lines = mapper.returnLines(id);
        List<ShipmentRequest.Line> checked = new ArrayList<>();
        for (Map<String,Object> line : lines) checked.add(new ShipmentRequest.Line(number(line,"skuId"),(String) line.get("batchNo"),number(line,"qty").intValue()));
        validateReturnLines(shipmentId,checked);
        if (number(returned,"isRestock").intValue() == 1) {
            for (Map<String,Object> line : lines) {
                Long skuId = number(line,"skuId"); String batch = (String) line.get("batchNo");
                BigDecimal qty = BigDecimal.valueOf(number(line,"qty"));
                stockMapper.addStock(2,skuId,batch,qty,SecurityUtils.getUsername());
                stock.txn(1,7,2,skuId,batch,qty,"sh_return",id,number(line,"itemId"),request.requestKey(),LocalDate.now());
            }
        }
        if (mapper.handleReturn(id,SecurityUtils.getUsername()) != 1) throw new ServiceException("退货单状态已变化",2001);
        stock.complete(request.requestKey(),id);
        Long orderId = Long.valueOf((String) snapshot.get("orderId"));
        BigDecimal refundAmount = snapshot.get("refundAmount") == null
                ? BigDecimal.ZERO : new BigDecimal(snapshot.get("refundAmount").toString());
        if (refundAmount.signum() > 0) {
            BigDecimal remaining = finance.reduceReceivable(orderId,refundAmount,"return:" + request.requestKey());
            if (remaining.signum() > 0) {
                workflow.todo("pay","sh_return",id,"finance","退货退款待处理","/finance/payments",null);
            }
            workflow.complete("return","sh_return",id);
        }
    }

    private void validateReturnLines(Long shipmentId,List<ShipmentRequest.Line> lines) {
        Map<String,Integer> shipped = new HashMap<>();
        for (Map<String,Object> line : mapper.shipmentLines(shipmentId)) shipped.put(number(line,"skuId") + "|" + line.get("batchNo"),number(line,"qty").intValue());
        for (ShipmentRequest.Line line : lines) {
            String key = line.skuId() + "|" + line.batchNo().trim();
            int remaining = shipped.getOrDefault(key,0) - mapper.returned(shipmentId,line.skuId(),line.batchNo().trim());
            if (line.qty() > remaining) throw new ServiceException("退货超过原出货未退数量",2006);
        }
    }

    private static List<ShipmentRequest.Line> validLines(List<ShipmentRequest.Line> lines) {
        if (lines == null || lines.isEmpty()) throw new ServiceException("至少填写一条 SKU",1001);
        Set<String> seen = new HashSet<>();
        for (ShipmentRequest.Line line : lines) {
            if (line == null || line.skuId() == null || line.qty() == null || line.qty() <= 0
                    || line.batchNo() == null || line.batchNo().isBlank() || line.batchNo().length() > 30
                    || !seen.add(line.skuId() + "|" + line.batchNo().trim())) throw new ServiceException("SKU、批次或数量不合法",1001);
        }
        return lines;
    }

    private static Map<Long,Integer> quantities(List<Map<String,Object>> rows) {
        Map<Long,Integer> result = new HashMap<>();
        for (Map<String,Object> row : rows) result.put(number(row,"skuId"),number(row,"qty").intValue());
        return result;
    }

    private static Long number(Map<String,Object> row,String key) { return ((Number) row.get(key)).longValue(); }
}

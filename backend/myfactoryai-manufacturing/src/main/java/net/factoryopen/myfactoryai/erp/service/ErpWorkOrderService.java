package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.WorkOrderRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpStockMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpWorkOrderMapper;
import net.factoryopen.myfactoryai.erp.mapper.ProcessReportRow;
import net.factoryopen.myfactoryai.erp.mapper.WorkOrderHeader;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpWorkOrderService {
    private final ErpWorkOrderMapper mapper;
    private final ErpStockMapper stockMapper;
    private final ErpStockService stock;

    public ErpWorkOrderService(ErpWorkOrderMapper mapper, ErpStockMapper stockMapper, ErpStockService stock) {
        this.mapper = mapper;
        this.stockMapper = stockMapper;
        this.stock = stock;
    }

    public Map<String,Object> detail(Long id) {
        Map<String,Object> header = mapper.detail(id);
        if (header == null) throw new ServiceException("工单不存在",1001);
        Map<String,Object> result = new HashMap<>(header);
        result.put("skus",mapper.detailSkus(id));
        result.put("materials",mapper.detailMaterials(id));
        result.put("processes",mapper.detailProcesses(id));
        return result;
    }

    @Transactional
    public Map<String,String> create(WorkOrderRequest.Create request) {
        if (request == null || request.orderId() == null || request.styleId() == null) throw new ServiceException("订单和款式必填",1001);
        Integer orderStatus = mapper.lockOrder(request.orderId());
        if (orderStatus == null || (orderStatus != 1 && orderStatus != 2)) throw new ServiceException("订单未确认或状态不允许建工单",2001);
        List<Map<String,Object>> skus = mapper.orderSkus(request.orderId(),request.styleId());
        List<Map<String,Object>> bom = mapper.bom(request.styleId());
        List<Map<String,Object>> processes = mapper.processes(request.styleId());
        if (skus.isEmpty() || bom.isEmpty() || processes.isEmpty()) throw new ServiceException("订单色码、BOM 或工序缺失",2005);
        if (mapper.existingWorkOrder(request.orderId(),request.styleId()) > 0) throw new ServiceException("该订单款式已有工单",2006);
        int qty = skus.stream().mapToInt(row -> ((Number) row.get("qty")).intValue()).sum();
        if (qty <= 0) throw new ServiceException("生产数量不合法",1001);
        String username = SecurityUtils.getUsername();
        WorkOrderHeader header = new WorkOrderHeader();
        header.setWoNo(DocNos.temp("WO"));
        header.setOrderId(request.orderId());
        header.setStyleId(request.styleId());
        header.setQty(qty);
        header.setCreatedBy(username);
        mapper.insert(header);
        String no = DocNos.formal("WO",header.getWoId());
        mapper.setNo(header.getWoId(),no);
        for (Map<String,Object> row : skus) {
            mapper.insertSku(header.getWoId(),longValue(row,"skuId"),((Number) row.get("qty")).intValue(),username);
        }
        for (Map<String,Object> row : bom) {
            if (row.get("unit") != null && !row.get("unit").equals(row.get("materialUnit"))) {
                throw new ServiceException("BOM 单位与物料单位不一致",2005);
            }
            BigDecimal unitQty = decimal(row,"unitQty");
            BigDecimal lossRate = decimal(row,"lossRate");
            BigDecimal planQty = unitQty.multiply(BigDecimal.valueOf(qty))
                    .multiply(BigDecimal.ONE.add(lossRate.movePointLeft(2)))
                    .setScale(3,RoundingMode.CEILING);
            if (unitQty.signum() <= 0 || planQty.signum() <= 0) throw new ServiceException("BOM 用量不合法",2005);
            mapper.insertMaterial(header.getWoId(),longValue(row,"materialId"),
                    (String) row.get("materialUnit"),unitQty,lossRate,planQty,username);
        }
        for (Map<String,Object> row : processes) {
            mapper.insertProcess(header.getWoId(),longValue(row,"processId"),
                    ((Number) row.get("seq")).intValue(),qty,decimal(row,"unitPrice"),username);
        }
        return Map.of("woId",header.getWoId().toString(),"woNo",no);
    }

    @Transactional
    public void issue(Long woId,WorkOrderRequest.Action request) {
        if (woId == null || request == null) throw new ServiceException("工单或请求键缺失",1001);
        if (stock.begin(request.requestKey(),"work_order_issue","pr_wo",woId,Map.of("woId",woId))) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || status != 0) throw new ServiceException("工单状态不允许领料",2001);
        List<Map<String,Object>> materials = mapper.materials(woId);
        if (materials.isEmpty()) throw new ServiceException("工单没有冻结用料",2005);
        for (Map<String,Object> material : materials) {
            BigDecimal need = decimal(material,"planQty").subtract(decimal(material,"issuedQty"));
            if (need.signum() <= 0) continue;
            Long materialId = longValue(material,"materialId");
            List<Map<String,Object>> batches = mapper.stockBatches(materialId);
            BigDecimal available = batches.stream().map(row -> decimal(row,"qty")).reduce(BigDecimal.ZERO,BigDecimal::add);
            if (available.compareTo(need) < 0) throw new ServiceException("物料 " + materialId + " 库存不足",2003);
            for (Map<String,Object> batch : batches) {
                if (need.signum() == 0) break;
                BigDecimal taken = need.min(decimal(batch,"qty"));
                String batchNo = (String) batch.get("batchNo");
                if (stockMapper.subtractStock(1,materialId,batchNo,taken) != 1) throw new ServiceException("并发领料导致库存不足",2003);
                stock.txn(2,2,1,materialId,batchNo,taken,"pr_wo",woId,longValue(material,"id"),request.requestKey(),LocalDate.now());
                need = need.subtract(taken);
            }
            if (mapper.markIssued(longValue(material,"id"),decimal(material,"planQty").subtract(decimal(material,"issuedQty"))) != 1) {
                throw new ServiceException("用料数量已变化",2006);
            }
        }
        mapper.start(woId,SecurityUtils.getUsername());
        mapper.startOrder(longValue(mapper.header(woId),"orderId"),SecurityUtils.getUsername());
        stock.complete(request.requestKey(),woId);
    }

    @Transactional
    public void report(WorkOrderRequest.Report request) {
        if (request == null || request.workOrderProcessId() == null || request.skuId() == null
                || request.doneQty() == null || request.badQty() == null
                || request.doneQty() < 0 || request.badQty() < 0
                || request.doneQty() + request.badQty() <= 0 || request.reportDate() == null
                || request.reportDate().isAfter(LocalDate.now())) {
            throw new ServiceException("报工数量、日期或色码不合法",1001);
        }
        Map<String,Object> process = mapper.lockProcess(request.workOrderProcessId());
        if (process == null) throw new ServiceException("工序不存在",1001);
        Long woId = longValue(process,"woId");
        if (stock.begin(request.requestKey(),"process_report","pr_wo",woId,request)) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || status != 1) throw new ServiceException("工单尚未开工或已完工",2001);
        Map<String,Object> sku = mapper.skuPlan(woId,request.skuId());
        if (sku == null) throw new ServiceException("SKU 不属于该工单",1001);
        int priorGood = mapper.reportedGood(request.workOrderProcessId(),request.skuId());
        int planned = ((Number) sku.get("planQty")).intValue();
        if (priorGood + request.doneQty() > planned) throw new ServiceException("报工超过 SKU 计划量",2006);
        Long priorProcess = mapper.priorProcess(woId,((Number) process.get("seq")).intValue());
        if (priorProcess != null && priorGood + request.doneQty() > mapper.reportedGood(priorProcess,request.skuId())) {
            throw new ServiceException("前一道工序合格数量不足",2006);
        }
        boolean outsourced = ((Number) process.get("isOutsourced")).intValue() == 1;
        if (!outsourced && (request.empId() == null || !Integer.valueOf(1).equals(mapper.activeWorker(request.empId())))) throw new ServiceException("内部报工须选择在职生产员工",1001);
        if (outsourced && request.empId() != null) throw new ServiceException("外发工序不能计员工工资",1001);
        String wagePeriod = request.reportDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        if (!outsourced && mapper.closedWagePeriod(request.empId(),wagePeriod) > 0) throw new ServiceException("该员工工资期已确认，不能补录报工",2001);
        ProcessReportRow row = new ProcessReportRow();
        row.setKey(request.requestKey()); row.setProcessId(request.workOrderProcessId());
        row.setWoId(woId); row.setSkuId(request.skuId()); row.setEmpId(request.empId());
        row.setOutsourced(outsourced ? 1 : 0); row.setGood(request.doneQty());
        row.setBad(request.badQty()); row.setDate(request.reportDate()); row.setUsername(SecurityUtils.getUsername());
        mapper.insertReport(row);
        if (mapper.progress(request.workOrderProcessId(),request.doneQty(),request.badQty()) != 1) throw new ServiceException("工序进度超出计划",2006);
        if (!outsourced && request.doneQty() > 0) {
            BigDecimal price = decimal(process,"unitPrice");
            mapper.insertWage(request.empId(),woId,longValue(mapper.header(woId),"styleId"),
                    longValue(process,"baseProcessId"),request.doneQty(),price,
                    price.multiply(BigDecimal.valueOf(request.doneQty())),wagePeriod,
                    row.getReportId(),SecurityUtils.getUsername());
        }
        stock.complete(request.requestKey(),woId);
    }

    @Transactional
    public void finish(Long woId,WorkOrderRequest.Finish request) {
        if (woId == null || request == null || request.skuItems() == null || request.skuItems().isEmpty()) {
            throw new ServiceException("完工 SKU 明细必填",1001);
        }
        Set<Long> seen = new HashSet<>();
        for (WorkOrderRequest.FinishedSku item : request.skuItems()) {
            if (item == null || item.skuId() == null || item.goodQty() == null || item.goodQty() <= 0
                    || item.batchNo() == null || item.batchNo().isBlank() || item.batchNo().length() > 30
                    || !seen.add(item.skuId())) throw new ServiceException("完工数量、批次或 SKU 不合法",1001);
        }
        if (stock.begin(request.requestKey(),"work_order_finish","pr_wo",woId,request)) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || status != 1) throw new ServiceException("工单状态不允许完工",2001);
        Long finalProcess = mapper.finalProcess(woId);
        if (finalProcess == null) throw new ServiceException("工单没有工序",2005);
        for (WorkOrderRequest.FinishedSku item : request.skuItems()) {
            Map<String,Object> plan = mapper.skuPlan(woId,item.skuId());
            if (plan == null) throw new ServiceException("SKU 不属于工单",1001);
            int completed = ((Number) plan.get("completedQty")).intValue();
            int planned = ((Number) plan.get("planQty")).intValue();
            if (completed + item.goodQty() > planned || completed + item.goodQty() > mapper.reportedGood(finalProcess,item.skuId())) {
                throw new ServiceException("完工数量超过计划或最终工序合格量",2006);
            }
            if (mapper.finishSku(woId,item.skuId(),item.goodQty()) != 1) throw new ServiceException("完工数量已变化",2006);
            BigDecimal qty = BigDecimal.valueOf(item.goodQty());
            stockMapper.addStock(2,item.skuId(),item.batchNo().trim(),qty,SecurityUtils.getUsername());
            stock.txn(1,3,2,item.skuId(),item.batchNo().trim(),qty,"pr_wo",woId,null,request.requestKey(),LocalDate.now());
        }
        if (mapper.unfinishedSkuCount(woId) == 0) {
            mapper.finish(woId,SecurityUtils.getUsername());
            mapper.finishOrder(longValue(mapper.header(woId),"orderId"),SecurityUtils.getUsername());
        }
        stock.complete(request.requestKey(),woId);
    }

    private static Long longValue(Map<String,Object> row,String key) { return ((Number) row.get(key)).longValue(); }
    private static BigDecimal decimal(Map<String,Object> row,String key) { return (BigDecimal) row.get(key); }
}

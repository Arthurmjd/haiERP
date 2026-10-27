package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final ErpWorkflowService workflow;

    public ErpWorkOrderService(ErpWorkOrderMapper mapper, ErpStockMapper stockMapper, ErpStockService stock,
                               ErpWorkflowService workflow) {
        this.mapper = mapper;
        this.stockMapper = stockMapper;
        this.stock = stock;
        this.workflow = workflow;
    }

    public Map<String,Object> detail(Long id) {
        Map<String,Object> header = mapper.detail(id);
        if (header == null) throw new ServiceException("工单不存在", ErpErrorCode.INVALID_PARAM.code());
        Map<String,Object> result = new HashMap<>(header);
        result.put("skus",mapper.detailSkus(id));
        result.put("materials",mapper.detailMaterials(id));
        result.put("processes",mapper.detailProcesses(id));
        return result;
    }

    @Transactional
    public Map<String,String> create(WorkOrderRequest.Create request) {
        if (request == null || request.orderId() == null || request.styleId() == null) throw new ServiceException("订单和款式必填", ErpErrorCode.INVALID_PARAM.code());
        Integer orderStatus = mapper.lockOrder(request.orderId());
        if (orderStatus == null || (orderStatus != 1 && orderStatus != 2)) throw new ServiceException("订单未确认或状态不允许建工单", ErpErrorCode.INVALID_STATE.code());
        List<Map<String,Object>> skus = mapper.orderSkus(request.orderId(),request.styleId());
        List<Map<String,Object>> bom = mapper.bom(request.styleId());
        List<Map<String,Object>> processes = mapper.processes(request.styleId());
        if (skus.isEmpty() || bom.isEmpty() || processes.isEmpty()) throw new ServiceException("订单色码、BOM 或工序缺失", ErpErrorCode.CALC_PRECONDITION.code());
        if (mapper.existingWorkOrder(request.orderId(),request.styleId()) > 0) throw new ServiceException("该订单款式已有工单", ErpErrorCode.CONFLICT.code());
        int qty = skus.stream().mapToInt(row -> ((Number) row.get("qty")).intValue()).sum();
        if (qty <= 0) throw new ServiceException("生产数量不合法", ErpErrorCode.INVALID_PARAM.code());
        String username = SecurityUtils.getUsername();
        LocalDate today = LocalDate.now();
        LocalDate planEnd = mapper.orderDelivery(request.orderId());
        if (planEnd == null) planEnd = today.plusDays(7);
        LocalDate planStart = planEnd.minusDays(7);
        if (planStart.isBefore(today)) planStart = today;
        WorkOrderHeader header = new WorkOrderHeader();
        header.setWoNo(DocNos.temp("WO"));
        header.setOrderId(request.orderId());
        header.setStyleId(request.styleId());
        header.setQty(qty);
        header.setPlanStartDate(planStart);
        header.setPlanEndDate(planEnd);
        header.setCreatedBy(username);
        mapper.insert(header);
        String no = DocNos.formal("WO",header.getWoId());
        mapper.setNo(header.getWoId(),no);
        mapper.insertSkus(header.getWoId(), skus.stream()
                .map(row -> new ErpWorkOrderMapper.SkuInsert(longValue(row,"skuId"), ((Number) row.get("qty")).intValue()))
                .toList(), username);
        List<ErpWorkOrderMapper.MaterialInsert> materialParams = new ArrayList<>();
        for (Map<String,Object> row : bom) {
            if (row.get("unit") != null && !row.get("unit").equals(row.get("materialUnit"))) {
                throw new ServiceException("BOM 单位与物料单位不一致", ErpErrorCode.CALC_PRECONDITION.code());
            }
            BigDecimal unitQty = decimal(row,"unitQty");
            BigDecimal lossRate = decimal(row,"lossRate");
            BigDecimal planQty = unitQty.multiply(BigDecimal.valueOf(qty))
                    .multiply(BigDecimal.ONE.add(lossRate.movePointLeft(2)))
                    .setScale(3,RoundingMode.CEILING);
            if (unitQty.signum() <= 0 || planQty.signum() <= 0) throw new ServiceException("BOM 用量不合法", ErpErrorCode.CALC_PRECONDITION.code());
            Long materialId = longValue(row,"materialId");
            materialParams.add(new ErpWorkOrderMapper.MaterialInsert(materialId,
                    (String) row.get("materialUnit"),unitQty,lossRate,planQty));
            BigDecimal available = mapper.availableToReserve(materialId);
            BigDecimal reserve = available == null ? BigDecimal.ZERO : planQty.min(available);
            if (reserve.signum() > 0) {
                mapper.insertReservation(header.getWoId(),materialId,reserve,username);
            }
        }
        mapper.insertMaterials(header.getWoId(), materialParams, username);
        mapper.insertProcesses(header.getWoId(), processes.stream()
                .map(row -> new ErpWorkOrderMapper.ProcessInsert(longValue(row,"processId"),
                        ((Number) row.get("seq")).intValue(),qty,decimal(row,"unitPrice")))
                .toList(), username);
        workflow.todo("issue","pr_wo",header.getWoId(),"production","工单已下达，请领料开工",
                "/production/workOrders/"+header.getWoId(),planEnd);
        return Map.of("woId",header.getWoId().toString(),"woNo",no);
    }

    @Transactional
    public void issue(Long woId,WorkOrderRequest.Action request) {
        if (woId == null || request == null) throw new ServiceException("工单或请求键缺失", ErpErrorCode.INVALID_PARAM.code());
        if (stock.begin(request.requestKey(),"work_order_issue","pr_wo",woId,Map.of("woId",woId))) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || (status != 0 && status != 1)) throw new ServiceException("工单状态不允许领料", ErpErrorCode.INVALID_STATE.code());
        List<Map<String,Object>> materials = mapper.materials(woId);
        if (materials.isEmpty()) throw new ServiceException("工单没有冻结用料", ErpErrorCode.CALC_PRECONDITION.code());
        Set<Long> selected = request.materialIds() == null || request.materialIds().isEmpty()
                ? null : new HashSet<>(request.materialIds());
        List<Map<String,Object>> targets = selected == null ? materials : materials.stream()
                .filter(row -> selected.contains(longValue(row,"materialId"))).toList();
        for (Map<String,Object> material : targets) {
            BigDecimal need = decimal(material,"planQty").subtract(decimal(material,"issuedQty"));
            if (need.signum() <= 0) continue;
            BigDecimal originalNeed = need;
            Long materialId = longValue(material,"materialId");
            BigDecimal physical = mapper.availableForIssue(materialId,woId);
            if (physical == null || physical.compareTo(need) < 0) {
                throw new ServiceException("物料 " + materialId + " 库存不足", ErpErrorCode.INSUFFICIENT.code());
            }
            List<Map<String,Object>> batches = mapper.stockBatches(materialId);
            BigDecimal takenTotal = BigDecimal.ZERO;
            for (Map<String,Object> batch : batches) {
                if (need.signum() == 0) break;
                BigDecimal taken = need.min(decimal(batch,"qty"));
                String batchNo = (String) batch.get("batchNo");
                if (stockMapper.subtractStock(1,materialId,batchNo,taken) != 1) throw new ServiceException("并发领料导致库存不足", ErpErrorCode.INSUFFICIENT.code());
                stock.txn(2,2,1,materialId,batchNo,taken,"pr_wo",woId,longValue(material,"id"),request.requestKey(),LocalDate.now());
                need = need.subtract(taken);
                takenTotal = takenTotal.add(taken);
            }
            if (takenTotal.signum() > 0) {
                mapper.reduceReservation(woId,materialId,takenTotal.min(originalNeed));
            }
            if (mapper.markIssued(longValue(material,"id"),takenTotal) != 1) {
                throw new ServiceException("用料数量已变化", ErpErrorCode.CONFLICT.code());
            }
        }
        String username = SecurityUtils.getUsername();
        if (status == 0) {
            mapper.start(woId,username);
            mapper.startOrder(longValue(mapper.header(woId),"orderId"),username);
            workflow.todo("report","pr_wo",woId,"production","工单已开工，请报工",
                    "/production/workOrders/"+woId,null);
        }
        boolean allIssued = mapper.materials(woId).stream()
                .allMatch(row -> decimal(row,"issuedQty").compareTo(decimal(row,"planQty")) >= 0);
        if (allIssued) workflow.complete("issue","pr_wo",woId);
        stock.complete(request.requestKey(),woId);
    }

    @Transactional
    public void report(WorkOrderRequest.Report request) {
        if (request == null || request.workOrderProcessId() == null || request.skuId() == null
                || request.doneQty() == null || request.badQty() == null
                || request.doneQty() < 0 || request.badQty() < 0
                || request.doneQty() + request.badQty() <= 0 || request.reportDate() == null
                || request.reportDate().isAfter(LocalDate.now())) {
            throw new ServiceException("报工数量、日期或色码不合法", ErpErrorCode.INVALID_PARAM.code());
        }
        workflow.assertOpen(request.reportDate());
        Map<String,Object> process = mapper.lockProcess(request.workOrderProcessId());
        if (process == null) throw new ServiceException("工序不存在", ErpErrorCode.INVALID_PARAM.code());
        Long woId = longValue(process,"woId");
        if (stock.begin(request.requestKey(),"process_report","pr_wo",woId,request)) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || status != 1) throw new ServiceException("工单尚未开工或已完工", ErpErrorCode.INVALID_STATE.code());
        Map<String,Object> sku = mapper.skuPlan(woId,request.skuId());
        if (sku == null) throw new ServiceException("SKU 不属于该工单", ErpErrorCode.INVALID_PARAM.code());
        int priorGood = mapper.reportedGood(request.workOrderProcessId(),request.skuId());
        int planned = ((Number) sku.get("planQty")).intValue();
        if (priorGood + request.doneQty() > planned) throw new ServiceException("报工超过 SKU 计划量", ErpErrorCode.CONFLICT.code());
        Long priorProcess = mapper.priorProcess(woId,((Number) process.get("seq")).intValue());
        if (priorProcess != null && priorGood + request.doneQty() > mapper.reportedGood(priorProcess,request.skuId())) {
            throw new ServiceException("前一道工序合格数量不足", ErpErrorCode.CONFLICT.code());
        }
        boolean outsourced = ((Number) process.get("isOutsourced")).intValue() == 1;
        if (!outsourced && (request.empId() == null || !Integer.valueOf(1).equals(mapper.activeWorker(request.empId())))) throw new ServiceException("内部报工须选择在职生产员工", ErpErrorCode.INVALID_PARAM.code());
        if (outsourced && request.empId() != null) throw new ServiceException("外发工序不能计员工工资", ErpErrorCode.INVALID_PARAM.code());
        // 校验：若该工序已配置员工，则报工员工必须属于该工序
        if (!outsourced) {
            Long baseProcessId = longValue(process,"baseProcessId");
            if (baseProcessId != null && mapper.processWorkerCount(baseProcessId) > 0
                    && mapper.workerInProcess(request.empId(),baseProcessId) == 0) {
                String workerName = mapper.workerName(request.empId());
                throw new ServiceException("员工" + (workerName == null ? request.empId() : workerName)
                        + " 不属于该工序，不能报工",2001);
            }
        }
        String wagePeriod = request.reportDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        if (!outsourced && mapper.closedWagePeriod(request.empId(),wagePeriod) > 0) {
            String workerName = mapper.workerName(request.empId());
            throw new ServiceException("报工日期 " + request.reportDate() + " 所属工资期 " + wagePeriod
                    + " 已确认（员工：" + (workerName == null ? request.empId() : workerName)
                    + "），不能补录；如需修正请先作废该工资单", 2001);
        }
        ProcessReportRow row = new ProcessReportRow();
        row.setKey(request.requestKey()); row.setProcessId(request.workOrderProcessId());
        row.setWoId(woId); row.setSkuId(request.skuId()); row.setEmpId(request.empId());
        row.setOutsourced(outsourced ? 1 : 0); row.setGood(request.doneQty());
        row.setBad(request.badQty()); row.setDate(request.reportDate()); row.setUsername(SecurityUtils.getUsername());
        mapper.insertReport(row);
        if (mapper.progress(request.workOrderProcessId(),request.doneQty(),request.badQty()) != 1) throw new ServiceException("工序进度超出计划", ErpErrorCode.CONFLICT.code());
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
            throw new ServiceException("完工 SKU 明细必填", ErpErrorCode.INVALID_PARAM.code());
        }
        Set<Long> seen = new HashSet<>();
        for (WorkOrderRequest.FinishedSku item : request.skuItems()) {
            if (item == null || item.skuId() == null || item.goodQty() == null || item.goodQty() <= 0
                    || item.batchNo() == null || item.batchNo().isBlank() || item.batchNo().length() > 30
                    || !seen.add(item.skuId())) throw new ServiceException("完工数量、批次或 SKU 不合法", ErpErrorCode.INVALID_PARAM.code());
        }
        if (stock.begin(request.requestKey(),"work_order_finish","pr_wo",woId,request)) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || status != 1) throw new ServiceException("工单状态不允许完工", ErpErrorCode.INVALID_STATE.code());
        Long finalProcess = mapper.finalProcess(woId);
        if (finalProcess == null) throw new ServiceException("工单没有工序", ErpErrorCode.CALC_PRECONDITION.code());
        for (WorkOrderRequest.FinishedSku item : request.skuItems()) {
            Map<String,Object> plan = mapper.skuPlan(woId,item.skuId());
            if (plan == null) throw new ServiceException("SKU 不属于工单", ErpErrorCode.INVALID_PARAM.code());
            int completed = ((Number) plan.get("completedQty")).intValue();
            int planned = ((Number) plan.get("planQty")).intValue();
            if (completed + item.goodQty() > planned || completed + item.goodQty() > mapper.reportedGood(finalProcess,item.skuId())) {
                throw new ServiceException("完工数量超过计划或最终工序合格量", ErpErrorCode.CONFLICT.code());
            }
            if (mapper.finishSku(woId,item.skuId(),item.goodQty()) != 1) throw new ServiceException("完工数量已变化", ErpErrorCode.CONFLICT.code());
            BigDecimal qty = BigDecimal.valueOf(item.goodQty());
            stockMapper.addStock(2,item.skuId(),item.batchNo().trim(),qty,SecurityUtils.getUsername());
            stock.txn(1,3,2,item.skuId(),item.batchNo().trim(),qty,"pr_wo",woId,null,request.requestKey(),LocalDate.now());
        }
        if (mapper.unfinishedSkuCount(woId) == 0) {
            String username = SecurityUtils.getUsername();
            mapper.finish(woId,username);
            Long orderId = longValue(mapper.header(woId),"orderId");
            int orderFinished = mapper.finishOrder(orderId,username);
            workflow.complete("report","pr_wo",woId);
            workflow.complete("issue","pr_wo",woId);
            mapper.releaseReservations(woId);
            if (orderFinished == 1) {
                workflow.todo("ship","sd_order",orderId,"sales","订单已完工，可安排出货",
                        "/shipping/shipments",null);
            }
        }
        stock.complete(request.requestKey(),woId);
    }

    @Transactional
    public void returnMaterial(WorkOrderRequest.Return request) {
        if (request == null || request.woId() == null || request.items() == null || request.items().isEmpty()
                || request.requestKey() == null || request.requestKey().isBlank()) {
            throw new ServiceException("退料参数不合法", ErpErrorCode.INVALID_PARAM.code());
        }
        Long woId = request.woId();
        if (stock.begin(request.requestKey(),"work_order_material_return","pr_wo",woId,request)) return;
        Integer status = mapper.lockStatus(woId);
        if (status == null || status != 1) throw new ServiceException("工单不在生产中，不能退料", ErpErrorCode.INVALID_STATE.code());
        Map<Long,Map<String,Object>> materials = new HashMap<>();
        for (Map<String,Object> row : mapper.materials(woId)) materials.put(longValue(row,"materialId"),row);
        String username = SecurityUtils.getUsername();
        for (WorkOrderRequest.MaterialReturn item : request.items()) {
            if (item == null || item.materialId() == null || item.batchNo() == null || item.batchNo().isBlank()
                    || item.batchNo().length() > 30 || item.qty() == null || item.qty().signum() <= 0) {
                throw new ServiceException("退料物料、批次或数量不合法", ErpErrorCode.INVALID_PARAM.code());
            }
            Map<String,Object> material = materials.get(item.materialId());
            if (material == null) throw new ServiceException("物料不属于该工单", ErpErrorCode.INVALID_PARAM.code());
            BigDecimal issued = decimal(material,"issuedQty");
            BigDecimal returned = material.get("returnedQty") == null ? BigDecimal.ZERO : decimal(material,"returnedQty");
            if (returned.add(item.qty()).compareTo(issued) > 0) {
                throw new ServiceException("退料数量超过已领数量", ErpErrorCode.CONFLICT.code());
            }
            stockMapper.addStock(1,item.materialId(),item.batchNo().trim(),item.qty(),username);
            stock.txn(1,7,1,item.materialId(),item.batchNo().trim(),item.qty(),"pr_wo",woId,
                    longValue(material,"id"),request.requestKey(),LocalDate.now());
            if (mapper.returnMaterial(longValue(material,"id"),item.qty()) != 1) {
                throw new ServiceException("退料数量已变化", ErpErrorCode.CONFLICT.code());
            }
            material.put("issuedQty",issued.subtract(item.qty()));
            material.put("returnedQty",returned.add(item.qty()));
        }
        stock.complete(request.requestKey(),woId);
    }

    @Transactional
    public List<Map<String,String>> generate(Long orderId) {
        if (orderId == null) throw new ServiceException("订单必填", ErpErrorCode.INVALID_PARAM.code());
        Integer orderStatus = mapper.lockOrder(orderId);
        if (orderStatus == null || (orderStatus != 1 && orderStatus != 2)) {
            throw new ServiceException("订单未确认或状态不允许建工单", ErpErrorCode.INVALID_STATE.code());
        }
        List<Map<String,String>> created = new ArrayList<>();
        for (Long styleId : mapper.orderStyles(orderId)) {
            if (mapper.existingWorkOrder(orderId,styleId) == 0) {
                created.add(create(new WorkOrderRequest.Create(orderId,styleId)));
            }
        }
        return created;
    }

    private static Long longValue(Map<String,Object> row,String key) { return ((Number) row.get(key)).longValue(); }
    private static BigDecimal decimal(Map<String,Object> row,String key) { return (BigDecimal) row.get(key); }
}

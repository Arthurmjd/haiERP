package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

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
import net.factoryopen.myfactoryai.erp.domain.OrderDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.OrderHeader;
import net.factoryopen.myfactoryai.erp.mapper.ErpOrderMapper;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpOrderService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ErpOrderService.class);

    private final ErpOrderMapper mapper;
    private final ErpWorkflowService workflow;

    /**
     * 订单确认后要自动建工单。用字段注入 + required=false：
     * 一是避免 ErpOrderService / ErpWorkOrderService 构造器循环依赖，
     * 二是纯订单读写的单元测试（只 new ErpOrderService(mapper)）不受影响。
     */
    @org.springframework.beans.factory.annotation.Autowired(required = false)
    private ErpWorkOrderService workOrderService;

    @org.springframework.beans.factory.annotation.Autowired
    public ErpOrderService(ErpOrderMapper mapper, ErpWorkflowService workflow) {
        this.mapper = mapper;
        this.workflow = workflow;
    }

    /** 兼容仅需订单读写（如单元测试）的场景，confirm 不会产生待办。 */
    public ErpOrderService(ErpOrderMapper mapper) {
        this(mapper, null);
    }

    public Map<String, Object> detail(Long orderId) {
        if (orderId == null || orderId <= 0) throw new ServiceException("订单编号不合法", ErpErrorCode.INVALID_PARAM.code());
        Map<String, Object> header = mapper.detail(orderId);
        if (header == null) throw new ServiceException("订单不存在", ErpErrorCode.INVALID_PARAM.code());
        Map<String, Object> result = new HashMap<>(header);
        result.put("items", mapper.lines(orderId));
        result.put("workOrders", mapper.workOrders(orderId));
        return result;
    }

    @Transactional
    public Map<String, String> create(OrderDraftRequest request) {
        ValidatedDraft draft = validate(request);
        if (request.quoteId() != null) {
            Map<String, Object> quote = mapper.quoteForOrder(request.quoteId());
            if (quote == null || ((Number) quote.get("status")).intValue() != 1
                    || ((Number) quote.get("customerId")).longValue() != request.customerId()) {
                throw new ServiceException("报价未接受或客户不一致", ErpErrorCode.INVALID_STATE.code());
            }
            // 一张报价只能转一张有效订单，避免重复下单造成重复生产/重复应收
            if (mapper.countOrdersByQuote(request.quoteId()) > 0) {
                throw new ServiceException("该报价已生成订单，不能重复下单", ErpErrorCode.CONFLICT.code());
            }
        }
        OrderHeader header = new OrderHeader();
        header.setOrderNo(DocNos.temp("SO"));
        header.setCustomerId(request.customerId());
        header.setDeliveryDate(request.deliveryDate());
        header.setTotalAmount(draft.total());
        header.setQuoteId(request.quoteId());
        header.setCreatedBy(SecurityUtils.getUsername());
        mapper.insert(header);
        String orderNo = DocNos.formal("SO", header.getOrderId());
        mapper.setOrderNo(header.getOrderId(), orderNo);
        mapper.insertLines(header.getOrderId(), toLineParams(draft), header.getCreatedBy());
        // 报价转订单：把报价从「已接受(1)」置为「已转单(3)」。条件更新保证并发下只有一次成功，
        // 失败则抛异常回滚整张订单，避免同一报价被重复转单。
        if (request.quoteId() != null
                && mapper.markQuoteConverted(request.quoteId(), header.getCreatedBy()) != 1) {
            throw new ServiceException("该报价已转订单，不能重复下单", ErpErrorCode.CONFLICT.code());
        }
        return Map.of("orderId", header.getOrderId().toString(), "orderNo", orderNo);
    }

    @Transactional
    public Map<String, String> update(Long orderId, OrderDraftRequest request) {
        if (orderId == null || orderId <= 0) throw new ServiceException("订单编号不合法", ErpErrorCode.INVALID_PARAM.code());
        Map<String, Object> locked = mapper.lockDraft(orderId);
        if (locked == null) throw new ServiceException("订单不存在", ErpErrorCode.INVALID_PARAM.code());
        if (((Number) locked.get("status")).intValue() != 0) {
            throw new ServiceException("仅草稿订单可编辑", ErpErrorCode.INVALID_STATE.code());
        }
        ValidatedDraft draft = validate(request);
        String username = SecurityUtils.getUsername();
        if (mapper.update(orderId, request.customerId(), request.deliveryDate(),
                draft.total(), username) != 1) {
            throw new ServiceException("订单状态已变化，请刷新后重试", ErpErrorCode.INVALID_STATE.code());
        }
        mapper.deleteLines(orderId);
        mapper.insertLines(orderId, toLineParams(draft), username);
        return Map.of("orderId", orderId.toString(),
                "orderNo", String.valueOf(locked.get("orderNo")));
    }

    private ValidatedDraft validate(OrderDraftRequest request) {
        if (request == null || request.customerId() == null || request.deliveryDate() == null
                || request.items() == null || request.items().isEmpty()
                || request.items().size() > 100 || request.deliveryDate().isBefore(LocalDate.now())) {
            throw new ServiceException("请填写客户、有效交期和 1 至 100 条色码明细", ErpErrorCode.INVALID_PARAM.code());
        }
        if (mapper.activeCustomer(request.customerId()) != 1) {
            throw new ServiceException("客户不存在或已停用", ErpErrorCode.INVALID_PARAM.code());
        }
        Set<String> seen = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        List<ValidatedLine> lines = new ArrayList<>();
        for (OrderDraftRequest.Line line : request.items()) {
            if (line == null || line.styleId() == null || line.colorId() == null
                    || line.sizeId() == null || line.qty() == null || line.qty() <= 0
                    || line.unitPrice() == null || line.unitPrice().signum() <= 0
                    || line.unitPrice().scale() > 2) {
                throw new ServiceException("色码、数量或单价不合法", ErpErrorCode.INVALID_PARAM.code());
            }
            String key = line.styleId() + ":" + line.colorId() + ":" + line.sizeId();
            if (!seen.add(key)) throw new ServiceException("订单色码明细重复", ErpErrorCode.DUPLICATE.code());
            if (mapper.activeSku(line.styleId(), line.colorId(), line.sizeId()) == null) {
                throw new ServiceException("色码 SKU 不存在或已停用", ErpErrorCode.INVALID_PARAM.code());
            }
            BigDecimal amount = line.unitPrice().multiply(BigDecimal.valueOf(line.qty()));
            total = total.add(amount);
            lines.add(new ValidatedLine(line, amount));
        }
        if (total.compareTo(new BigDecimal("9999999999.99")) > 0) {
            throw new ServiceException("订单金额超出上限", ErpErrorCode.INVALID_PARAM.code());
        }
        return new ValidatedDraft(lines, total);
    }

    @Transactional
    public void confirm(Long orderId) {
        if (orderId == null || orderId <= 0) throw new ServiceException("订单编号不合法", ErpErrorCode.INVALID_PARAM.code());
        if (mapper.confirm(orderId, SecurityUtils.getUsername()) != 1) {
            throw new ServiceException("订单已确认、无明细或状态不允许确认", ErpErrorCode.INVALID_STATE.code());
        }
        // 订单确认后立刻按款式生成生产工单，让「订单 → 工单」是一条连续链路，
        // 不用再让人跑去订单详情点「一键生成工单」（漏点就会出现「工单列表里找不到这张订单」）。
        // 放在事务提交后执行：某个款式缺 BOM / 工序时只跳过它，不会把订单确认一起回滚。
        autoGenerateWorkOrders(orderId);
        if (workflow != null) {
            Map<String, Object> detail = mapper.detail(orderId);
            LocalDate dueDate = detail == null ? null : toLocalDate(detail.get("deliveryDate"));
            workflow.todo("plan", "sd_order", orderId, "planner",
                    "订单已确认，请采购物料并按款式生成工单", "/orders/" + orderId, dueDate);
        }
    }

    /**
     * 订单确认后自动建工单（幂等：已存在工单的款式会被跳过）。
     *
     * <p>用 afterCommit 而不是直接调用：ErpWorkOrderService.create 在缺少 BOM、工序或色码时
     * 会抛 ServiceException，如果在同一个事务里抛出，整张订单的确认都会被回滚 ——
     * 那才是真正的「订单确认不了」。现在最坏情况只是这张订单暂时没有工单，
     * 页面上的「一键生成工单」按钮还能补。</p>
     */
    private void autoGenerateWorkOrders(Long orderId) {
        if (workOrderService == null) return;
        if (!org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive()) {
            tryGenerate(orderId);
            return;
        }
        org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization(
                new org.springframework.transaction.support.TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        tryGenerate(orderId);
                    }
                });
    }

    private void tryGenerate(Long orderId) {
        try {
            workOrderService.generate(orderId);
        } catch (RuntimeException e) {
            log.warn("订单 {} 自动生成工单失败，可在订单详情点「一键生成工单」重试：{}", orderId, e.getMessage());
        }
    }

    private static LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate localDate) return localDate;
        if (value instanceof java.sql.Date sqlDate) return sqlDate.toLocalDate();
        if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    private static List<ErpOrderMapper.OrderLineInsert> toLineParams(ValidatedDraft draft) {
        return draft.lines().stream()
                .map(validated -> {
                    OrderDraftRequest.Line line = validated.line();
                    return new ErpOrderMapper.OrderLineInsert(line.styleId(), line.colorId(),
                            line.sizeId(), line.qty(), line.unitPrice(), validated.amount());
                })
                .toList();
    }

    private record ValidatedLine(OrderDraftRequest.Line line, BigDecimal amount) {}

    private record ValidatedDraft(List<ValidatedLine> lines, BigDecimal total) {}
}

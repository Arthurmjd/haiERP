package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.OrderDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.OrderHeader;
import net.factoryopen.myfactoryai.erp.mapper.ErpOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpOrderService {
    private final ErpOrderMapper mapper;

    public ErpOrderService(ErpOrderMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, Object> detail(Long orderId) {
        if (orderId == null || orderId <= 0) throw new ServiceException("订单编号不合法", 1001);
        Map<String, Object> header = mapper.detail(orderId);
        if (header == null) throw new ServiceException("订单不存在", 1001);
        Map<String, Object> result = new HashMap<>(header);
        result.put("items", mapper.lines(orderId));
        return result;
    }

    @Transactional
    public Map<String, String> create(OrderDraftRequest request) {
        if (request == null || request.customerId() == null || request.deliveryDate() == null
                || request.items() == null || request.items().isEmpty()
                || request.items().size() > 100 || request.deliveryDate().isBefore(LocalDate.now())) {
            throw new ServiceException("请填写客户、有效交期和 1 至 100 条色码明细", 1001);
        }
        if (mapper.activeCustomer(request.customerId()) != 1) {
            throw new ServiceException("客户不存在或已停用", 1001);
        }
        Set<String> seen = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        List<ValidatedLine> lines = new ArrayList<>();
        for (OrderDraftRequest.Line line : request.items()) {
            if (line == null || line.styleId() == null || line.colorId() == null
                    || line.sizeId() == null || line.qty() == null || line.qty() <= 0
                    || line.unitPrice() == null || line.unitPrice().signum() <= 0
                    || line.unitPrice().scale() > 2) {
                throw new ServiceException("色码、数量或单价不合法", 1001);
            }
            String key = line.styleId() + ":" + line.colorId() + ":" + line.sizeId();
            if (!seen.add(key)) throw new ServiceException("订单色码明细重复", 1002);
            if (mapper.activeSku(line.styleId(), line.colorId(), line.sizeId()) == null) {
                throw new ServiceException("色码 SKU 不存在或已停用", 1001);
            }
            BigDecimal amount = line.unitPrice().multiply(BigDecimal.valueOf(line.qty()));
            total = total.add(amount);
            lines.add(new ValidatedLine(line, amount));
        }
        if (total.compareTo(new BigDecimal("9999999999.99")) > 0) {
            throw new ServiceException("订单金额超出上限", 1001);
        }
        OrderHeader header = new OrderHeader();
        header.setOrderNo("SO-TMP-" + UUID.randomUUID());
        header.setCustomerId(request.customerId());
        header.setDeliveryDate(request.deliveryDate());
        header.setTotalAmount(total);
        header.setCreatedBy(SecurityUtils.getUsername());
        mapper.insert(header);
        String orderNo = "SO" + LocalDate.now().getYear() + String.format("%06d", header.getOrderId());
        mapper.setOrderNo(header.getOrderId(), orderNo);
        for (ValidatedLine validated : lines) {
            OrderDraftRequest.Line line = validated.line();
            mapper.insertLine(header.getOrderId(), line.styleId(), line.colorId(), line.sizeId(),
                    line.qty(), line.unitPrice(), validated.amount(), header.getCreatedBy());
        }
        return Map.of("orderId", header.getOrderId().toString(), "orderNo", orderNo);
    }

    @Transactional
    public void confirm(Long orderId) {
        if (orderId == null || orderId <= 0) throw new ServiceException("订单编号不合法", 1001);
        if (mapper.confirm(orderId, SecurityUtils.getUsername()) != 1) {
            throw new ServiceException("订单已确认、无明细或状态不允许确认", 2001);
        }
    }

    private record ValidatedLine(OrderDraftRequest.Line line, BigDecimal amount) {}
}

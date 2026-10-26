package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.PurchaseDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.PurchaseReceiptRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpPurchaseMapper;
import net.factoryopen.myfactoryai.erp.mapper.PurchaseHeader;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpPurchaseService {
    private final ErpPurchaseMapper mapper;
    private final ErpStockService stock;

    public ErpPurchaseService(ErpPurchaseMapper mapper, ErpStockService stock) {
        this.mapper = mapper;
        this.stock = stock;
    }

    public Map<String,Object> detail(Long id) {
        Map<String,Object> header = mapper.detail(id);
        if (header == null) throw new ServiceException("采购单不存在",1001);
        Map<String,Object> result = new HashMap<>(header);
        result.put("items",mapper.lines(id));
        return result;
    }

    @Transactional
    public Map<String,String> create(PurchaseDraftRequest request) {
        ValidatedDraft draft = validate(request);
        PurchaseHeader header = new PurchaseHeader();
        header.setPoNo(DocNos.temp("PO"));
        header.setSupplierId(request.supplierId());
        header.setExpectDate(request.expectDate());
        header.setTotalAmount(draft.total());
        header.setCreatedBy(SecurityUtils.getUsername());
        mapper.insert(header);
        String no = DocNos.formal("PO", header.getPoId());
        mapper.setPoNo(header.getPoId(),no);
        for (PurchaseDraftRequest.Line line : draft.lines()) {
            mapper.insertLine(header.getPoId(),line.materialId(),line.qty(),line.unitPrice(),
                    line.qty().multiply(line.unitPrice()).setScale(2,RoundingMode.HALF_UP),header.getCreatedBy());
        }
        return Map.of("poId",header.getPoId().toString(),"poNo",no);
    }

    @Transactional
    public Map<String,String> update(Long poId, PurchaseDraftRequest request) {
        if (poId == null || poId <= 0) throw new ServiceException("采购单编号不合法",1001);
        Map<String,Object> locked = mapper.lockDraft(poId);
        if (locked == null) throw new ServiceException("采购单不存在",1001);
        if (((Number) locked.get("status")).intValue() != 0) {
            throw new ServiceException("仅草稿采购单可编辑",2001);
        }
        ValidatedDraft draft = validate(request);
        String username = SecurityUtils.getUsername();
        if (mapper.update(poId,request.supplierId(),request.expectDate(),draft.total(),username) != 1) {
            throw new ServiceException("采购单状态已变化，请刷新后重试",2001);
        }
        mapper.deleteLines(poId);
        for (PurchaseDraftRequest.Line line : draft.lines()) {
            mapper.insertLine(poId,line.materialId(),line.qty(),line.unitPrice(),
                    line.qty().multiply(line.unitPrice()).setScale(2,RoundingMode.HALF_UP),username);
        }
        return Map.of("poId",poId.toString(),"poNo",String.valueOf(locked.get("poNo")));
    }

    private ValidatedDraft validate(PurchaseDraftRequest request) {
        if (request == null || request.supplierId() == null || request.expectDate() == null
                || request.expectDate().isBefore(LocalDate.now()) || request.items() == null
                || request.items().isEmpty() || request.items().size() > 100) {
            throw new ServiceException("请填写供应商、有效到货日期和 1 至 100 条物料",1001);
        }
        if (mapper.activeSupplier(request.supplierId()) != 1) throw new ServiceException("供应商不存在或已停用",1001);
        Set<Long> seen = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseDraftRequest.Line line : request.items()) {
            if (line == null || line.materialId() == null || line.qty() == null || line.unitPrice() == null
                    || line.qty().signum() <= 0 || line.qty().scale() > 3 || line.qty().compareTo(new BigDecimal("999999999.999")) > 0
                    || line.unitPrice().signum() <= 0 || line.unitPrice().scale() > 2
                    || line.unitPrice().compareTo(new BigDecimal("99999999.99")) > 0) {
                throw new ServiceException("物料数量或单价不合法",1001);
            }
            if (!seen.add(line.materialId())) throw new ServiceException("采购物料重复",1002);
            if (mapper.activeMaterial(line.materialId()) != 1) throw new ServiceException("物料不存在或已停用",1001);
            total = total.add(line.qty().multiply(line.unitPrice()).setScale(2,RoundingMode.HALF_UP));
        }
        if (total.compareTo(new BigDecimal("9999999999.99")) > 0) throw new ServiceException("采购金额超出上限",1001);
        return new ValidatedDraft(request.items(), total);
    }

    private record ValidatedDraft(List<PurchaseDraftRequest.Line> lines, BigDecimal total) {}

    @Transactional
    public void confirm(Long id) {
        if (id == null || id <= 0 || mapper.confirm(id,SecurityUtils.getUsername()) != 1) {
            throw new ServiceException("采购单状态不允许确认",2001);
        }
    }

    @Transactional
    public Map<String,String> receive(PurchaseReceiptRequest request) {
        if (request == null || request.poId() == null || request.poId() <= 0
                || request.actualArrivalDate() == null || request.actualArrivalDate().isAfter(LocalDate.now())
                || request.items() == null || request.items().isEmpty() || request.items().size() > 100) {
            throw new ServiceException("收货日期、采购单或明细不合法",1001);
        }
        Set<Long> seen = new HashSet<>();
        for (PurchaseReceiptRequest.Line line : request.items()) {
            if (line == null || line.itemId() == null || line.qty() == null || line.qty().signum() <= 0
                    || line.qty().scale() > 3 || line.batchNo() == null || line.batchNo().isBlank()
                    || line.batchNo().length() > 30 || !seen.add(line.itemId())) {
                throw new ServiceException("收货明细、数量或批次不合法",1001);
            }
        }
        if (stock.begin(request.requestKey(),"purchase_receive","pu_order",request.poId(),request)) {
            return Map.of("poId",request.poId().toString());
        }
        Integer status = mapper.lockStatus(request.poId());
        if (status == null || (status != 1 && status != 2)) throw new ServiceException("采购单状态不允许收货",2001);
        for (PurchaseReceiptRequest.Line line : request.items()) {
            Map<String,Object> current = mapper.receiptLine(request.poId(),line.itemId());
            if (current == null) throw new ServiceException("采购明细不存在",1001);
            BigDecimal remaining = (BigDecimal) current.get("qty");
            remaining = remaining.subtract((BigDecimal) current.get("receivedQty"));
            if (line.qty().compareTo(remaining) > 0) throw new ServiceException("收货数量超过未收数量",2006);
            stock.receipt(((Number) current.get("materialId")).longValue(),line.batchNo().trim(),
                    line.qty(),request.poId(),line.itemId(),request.requestKey(),request.actualArrivalDate());
        }
        mapper.finishReceipt(request.poId(),mapper.remainingLineCount(request.poId()) == 0 ? 3 : 2,
                request.actualArrivalDate(),SecurityUtils.getUsername());
        stock.complete(request.requestKey(),request.poId());
        return Map.of("poId",request.poId().toString());
    }
}

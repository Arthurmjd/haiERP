package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.QuotationDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.QuotationRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpQuotationMapper;
import net.factoryopen.myfactoryai.erp.support.DocNos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpQuotationService {
    private final ErpQuotationMapper mapper;
    private final ErpStockService stock;
    public ErpQuotationService(ErpQuotationMapper mapper,ErpStockService stock) { this.mapper=mapper; this.stock=stock; }

    public Map<String,Object> detail(Long id) {
        Map<String,Object> header = mapper.detail(id);
        if (header == null) throw new ServiceException("报价单不存在", ErpErrorCode.INVALID_PARAM.code());
        Map<String,Object> result = new HashMap<>(header);
        result.put("items",mapper.lines(id));
        return result;
    }

    @Transactional
    public void transition(Long id,int status) {
        if (id == null || id <= 0 || (status != 1 && status != 2)) throw new ServiceException("报价状态不合法", ErpErrorCode.INVALID_PARAM.code());
        if (mapper.transition(id,status,SecurityUtils.getUsername()) != 1) {
            throw new ServiceException("报价单不存在或已处理", ErpErrorCode.CONFLICT.code());
        }
    }

    @Transactional
    public Map<String,String> create(QuotationDraftRequest request) {
        if (request == null || request.customerId() == null || request.quoteDate() == null
                || request.items() == null || request.items().isEmpty() || request.items().size() > 100) {
            throw new ServiceException("请填写客户、报价日期和 1 至 100 条报价明细", ErpErrorCode.INVALID_PARAM.code());
        }
        if (mapper.customerActive(request.customerId()) != 1) throw new ServiceException("客户不存在或已停用", ErpErrorCode.INVALID_PARAM.code());
        Set<Long> styles = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        List<BigDecimal> amounts = new ArrayList<>();
        for (QuotationDraftRequest.Line line : request.items()) {
            if (line == null || line.styleId() == null || line.qty() == null || line.qty() <= 0
                    || line.unitPrice() == null || line.unitPrice().signum() <= 0) {
                throw new ServiceException("款式、数量或单价不合法", ErpErrorCode.INVALID_PARAM.code());
            }
            if (!styles.add(line.styleId())) throw new ServiceException("同一报价不能重复款式", ErpErrorCode.DUPLICATE.code());
            if (mapper.styleActive(line.styleId()) != 1) throw new ServiceException("款式不存在或已停用", ErpErrorCode.INVALID_PARAM.code());
            BigDecimal amount = money(line.unitPrice().multiply(BigDecimal.valueOf(line.qty())));
            amounts.add(amount);
            total = total.add(amount);
        }
        Map<String,Object> header = new HashMap<>();
        header.put("quoteNo",DocNos.temp("QT"));
        header.put("customerId",request.customerId());
        header.put("quoteDate",request.quoteDate());
        header.put("totalAmount",money(total));
        header.put("username",SecurityUtils.getUsername());
        mapper.insert(header);
        Long id = number(header,"quoteId");
        String no = DocNos.formal("QT",id);
        mapper.setNo(id,no);
        for (int i=0;i<request.items().size();i++) {
            QuotationDraftRequest.Line line = request.items().get(i);
            mapper.insertDraftLine(id,line.styleId(),line.qty(),line.unitPrice(),
                    line.costRef()==null?BigDecimal.ZERO:line.costRef(),amounts.get(i),
                    SecurityUtils.getUsername());
        }
        return Map.of("quoteId",id.toString(),"quoteNo",no);
    }

    @Transactional
    public Map<String,String> fromSettlement(QuotationRequest request) {
        if (request == null || request.settleIds() == null || request.settleIds().isEmpty() || request.settleIds().size() > 20
                || request.settleIds().stream().anyMatch(id -> id == null || id <= 0)
                || new HashSet<>(request.settleIds()).size() != request.settleIds().size()) throw new ServiceException("结算单列表不合法", ErpErrorCode.INVALID_PARAM.code());
        Long actionId = request.settleIds().stream().min(Long::compareTo).orElseThrow();
        if (stock.begin(request.requestKey(),"quotation_from_settlement","fn_settlement",actionId,request)) {
            Long previous = mapper.byRequestKey(request.requestKey());
            if (previous == null) throw new ServiceException("请求记录与报价单不一致", ErpErrorCode.IDEMPOTENCY_CONFLICT.code());
            return Map.of("quoteId",previous.toString());
        }
        List<Map<String,Object>> settlements = new ArrayList<>();
        Set<Long> styles = new HashSet<>();
        Long customerId = null;
        for (Long settleId : request.settleIds().stream().sorted().toList()) {
            Map<String,Object> line = mapper.confirmedSettlement(settleId);
            if (line == null) throw new ServiceException("结算单未确认或不存在", ErpErrorCode.INVALID_STATE.code());
            Long currentCustomer = number(line,"customerId");
            if (customerId != null && !customerId.equals(currentCustomer)) throw new ServiceException("报价只能引用同一客户的结算", ErpErrorCode.INVALID_PARAM.code());
            if (!styles.add(number(line,"styleId"))) throw new ServiceException("同一报价不能重复款式", ErpErrorCode.INVALID_PARAM.code());
            customerId = currentCustomer;
            settlements.add(line);
        }
        BigDecimal total = BigDecimal.ZERO;
        List<BigDecimal> prices = new ArrayList<>();
        for (Map<String,Object> line : settlements) {
            int qty = number(line,"qty").intValue();
            if (qty <= 0) throw new ServiceException("结算数量不合法", ErpErrorCode.CALC_PRECONDITION.code());
            BigDecimal price = decimal(line,"revenue").divide(BigDecimal.valueOf(qty),2,RoundingMode.HALF_UP);
            prices.add(price);
            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
        }
        Map<String,Object> header = new HashMap<>();
        header.put("quoteNo",DocNos.temp("QT")); header.put("customerId",customerId);
        header.put("quoteDate",LocalDate.now());
        header.put("totalAmount",total); header.put("requestKey",request.requestKey());
        header.put("username",SecurityUtils.getUsername());
        mapper.insert(header);
        Long id = number(header,"quoteId");
        String no = DocNos.formal("QT",id);
        mapper.setNo(id,no);
        for (int i=0;i<settlements.size();i++) {
            Map<String,Object> line = settlements.get(i);
            int qty = number(line,"qty").intValue();
            mapper.insertLine(id,number(line,"styleId"),qty,prices.get(i),decimal(line,"unitCost"),
                    number(line,"settleId"),prices.get(i).multiply(BigDecimal.valueOf(qty)),SecurityUtils.getUsername());
        }
        stock.complete(request.requestKey(),id);
        return Map.of("quoteId",id.toString(),"quoteNo",no);
    }

    private static BigDecimal money(BigDecimal value) { return value.setScale(2,RoundingMode.HALF_UP); }
    private static Long number(Map<String,Object> row,String key) { return ((Number)row.get(key)).longValue(); }
    private static BigDecimal decimal(Map<String,Object> row,String key) { return new BigDecimal(row.get(key).toString()); }
}

package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import net.factoryopen.myfactoryai.erp.domain.PurchaseDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.SuggestionRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpPurchaseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpSuggestionService {
    private final ErpPurchaseMapper mapper;
    private final ErpPurchaseService purchases;
    private final ErpStockService stock;
    public ErpSuggestionService(ErpPurchaseMapper mapper,ErpPurchaseService purchases,ErpStockService stock) {
        this.mapper=mapper; this.purchases=purchases; this.stock=stock;
    }

    @Transactional
    public Map<String,String> generate(SuggestionRequest request) {
        if (request == null || request.supplierId() == null || request.expectDate() == null
                || request.materialIds() == null || request.materialIds().isEmpty() || request.materialIds().size() > 100
                || request.materialIds().stream().anyMatch(id -> id == null || id <= 0)
                || new HashSet<>(request.materialIds()).size() != request.materialIds().size())
            throw new ServiceException("供应商、到货日期或物料列表不合法",1001);
        if (stock.begin(request.requestKey(),"suggestion_generate","md_supplier",request.supplierId(),request)) {
            Long previous = mapper.bySuggestionKey(request.requestKey());
            if (previous == null) throw new ServiceException("请求记录与采购单不一致",2007);
            return Map.of("poId",previous.toString());
        }
        List<PurchaseDraftRequest.Line> lines = new ArrayList<>();
        for (Long materialId : request.materialIds()) {
            Map<String,Object> suggested = mapper.suggestedMaterial(materialId);
            if (suggested == null) throw new ServiceException("物料当前没有净缺口",2006);
            Object rawQty = suggested.get("suggestQty"); Object rawPrice = suggested.get("unitPrice");
            if (rawQty == null || rawPrice == null) throw new ServiceException("建议数量或取价不可用",2005);
            BigDecimal qty = new BigDecimal(rawQty.toString());
            BigDecimal price = new BigDecimal(rawPrice.toString());
            if (qty.signum() <= 0 || price.signum() <= 0) throw new ServiceException("建议数量或取价不可用",2005);
            lines.add(new PurchaseDraftRequest.Line(materialId,qty,price));
        }
        Map<String,String> result = purchases.create(new PurchaseDraftRequest(request.supplierId(),request.expectDate(),lines));
        Long poId = Long.valueOf(result.get("poId"));
        if (mapper.markSuggestion(poId,request.requestKey()) != 1) throw new ServiceException("采购单请求键保存失败",2007);
        stock.complete(request.requestKey(),poId);
        return result;
    }
}

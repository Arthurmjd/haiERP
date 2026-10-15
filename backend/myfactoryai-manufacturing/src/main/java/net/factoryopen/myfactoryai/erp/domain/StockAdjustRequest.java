package net.factoryopen.myfactoryai.erp.domain;

import java.math.BigDecimal;

public record StockAdjustRequest(Integer itemType,Long itemId,String batchNo,BigDecimal actualQty,
                                 String reason,String requestKey) {}

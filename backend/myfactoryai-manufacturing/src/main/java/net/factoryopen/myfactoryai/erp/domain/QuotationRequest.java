package net.factoryopen.myfactoryai.erp.domain;

import java.util.List;

public record QuotationRequest(List<Long> settleIds, String requestKey) {}

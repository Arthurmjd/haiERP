package net.factoryopen.myfactoryai.erp.domain;

import java.time.LocalDate;
import java.util.List;

public record SuggestionRequest(Long supplierId,LocalDate expectDate,List<Long> materialIds,String requestKey) {}

package net.factoryopen.myfactoryai.web.controller.erp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.OrderDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.PurchaseDraftRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpOrderMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpPurchaseMapper;
import net.factoryopen.myfactoryai.erp.service.ErpOrderService;
import net.factoryopen.myfactoryai.erp.service.ErpPurchaseService;
import net.factoryopen.myfactoryai.erp.service.ErpStockService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ErpDraftUpdateTest {
    private OrderDraftRequest orderRequest() {
        return new OrderDraftRequest(1L, LocalDate.now().plusDays(3),
                List.of(new OrderDraftRequest.Line(1L, 2L, 3L, 2, new BigDecimal("10.00"))));
    }

    @Test
    void orderUpdateRejectsConfirmedDocument() {
        ErpOrderMapper mapper = mock(ErpOrderMapper.class);
        ErpOrderService service = new ErpOrderService(mapper);
        when(mapper.lockDraft(7L)).thenReturn(Map.of("status", 1, "orderNo", "SO2026000007"));

        ServiceException error = assertThrows(ServiceException.class, () -> service.update(7L, orderRequest()));

        assertEquals("仅草稿订单可编辑", error.getMessage());
        verify(mapper, never()).update(anyLong(), anyLong(), any(LocalDate.class), any(BigDecimal.class), anyString());
        verify(mapper, never()).deleteLines(anyLong());
    }

    @Test
    void orderUpdateRejectsInvalidLine() {
        ErpOrderMapper mapper = mock(ErpOrderMapper.class);
        ErpOrderService service = new ErpOrderService(mapper);
        when(mapper.lockDraft(7L)).thenReturn(Map.of("status", 0, "orderNo", "SO2026000007"));
        when(mapper.activeCustomer(1L)).thenReturn(1);
        OrderDraftRequest invalid = new OrderDraftRequest(1L, LocalDate.now().plusDays(3),
                List.of(new OrderDraftRequest.Line(1L, 2L, 3L, 0, new BigDecimal("10.00"))));

        assertThrows(ServiceException.class, () -> service.update(7L, invalid));

        verify(mapper, never()).update(anyLong(), anyLong(), any(LocalDate.class), any(BigDecimal.class), anyString());
    }

    @Test
    void orderUpdateReplacesLinesForDraft() {
        ErpOrderMapper mapper = mock(ErpOrderMapper.class);
        ErpOrderService service = new ErpOrderService(mapper);
        when(mapper.lockDraft(7L)).thenReturn(Map.of("status", 0, "orderNo", "SO2026000007"));
        when(mapper.activeCustomer(1L)).thenReturn(1);
        when(mapper.activeSku(1L, 2L, 3L)).thenReturn(10L);
        when(mapper.update(eq(7L), eq(1L), any(LocalDate.class), eq(new BigDecimal("20.00")), eq("tester")))
                .thenReturn(1);

        try (MockedStatic<SecurityUtils> security = mockStatic(SecurityUtils.class)) {
            security.when(SecurityUtils::getUsername).thenReturn("tester");

            Map<String, String> result = service.update(7L, orderRequest());

            assertEquals("SO2026000007", result.get("orderNo"));
            verify(mapper).deleteLines(7L);
            verify(mapper).insertLine(7L, 1L, 2L, 3L, 2, new BigDecimal("10.00"),
                    new BigDecimal("20.00"), "tester");
        }
    }

    private PurchaseDraftRequest purchaseRequest() {
        return new PurchaseDraftRequest(5L, LocalDate.now().plusDays(2),
                List.of(new PurchaseDraftRequest.Line(9L, new BigDecimal("1.500"), new BigDecimal("4.00"))));
    }

    @Test
    void purchaseUpdateRejectsConfirmedDocument() {
        ErpPurchaseMapper mapper = mock(ErpPurchaseMapper.class);
        ErpPurchaseService service = new ErpPurchaseService(mapper, mock(ErpStockService.class),
                mock(net.factoryopen.myfactoryai.erp.service.ErpFinanceService.class),
                mock(net.factoryopen.myfactoryai.erp.service.ErpWorkflowService.class));
        when(mapper.lockDraft(3L)).thenReturn(Map.of("status", 3, "poNo", "PO2026000003"));

        ServiceException error = assertThrows(ServiceException.class, () -> service.update(3L, purchaseRequest()));

        assertEquals("仅草稿采购单可编辑", error.getMessage());
        verify(mapper, never()).update(anyLong(), anyLong(), any(LocalDate.class), any(BigDecimal.class), anyString());
        verify(mapper, never()).deleteLines(anyLong());
    }

    @Test
    void purchaseUpdateReplacesLinesForDraft() {
        ErpPurchaseMapper mapper = mock(ErpPurchaseMapper.class);
        ErpPurchaseService service = new ErpPurchaseService(mapper, mock(ErpStockService.class),
                mock(net.factoryopen.myfactoryai.erp.service.ErpFinanceService.class),
                mock(net.factoryopen.myfactoryai.erp.service.ErpWorkflowService.class));
        when(mapper.lockDraft(3L)).thenReturn(Map.of("status", 0, "poNo", "PO2026000003"));
        when(mapper.activeSupplier(5L)).thenReturn(1);
        when(mapper.activeMaterial(9L)).thenReturn(1);
        when(mapper.update(eq(3L), eq(5L), any(LocalDate.class), eq(new BigDecimal("6.00")), eq("tester")))
                .thenReturn(1);

        try (MockedStatic<SecurityUtils> security = mockStatic(SecurityUtils.class)) {
            security.when(SecurityUtils::getUsername).thenReturn("tester");

            Map<String, String> result = service.update(3L, purchaseRequest());

            assertEquals("PO2026000003", result.get("poNo"));
            verify(mapper).deleteLines(3L);
            verify(mapper).insertLine(3L, 9L, new BigDecimal("1.500"), new BigDecimal("4.00"),
                    new BigDecimal("6.00"), "tester");
        }
    }
}

package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.PermissionService;
import net.factoryopen.myfactoryai.ai.config.LLMConfig;
import net.factoryopen.myfactoryai.erp.mapper.ErpAiReadMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpQuotationMapper;
import net.factoryopen.myfactoryai.erp.service.ErpQuotationService;
import net.factoryopen.myfactoryai.erp.service.ErpStockService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ErpAccessTest {
    @Test
    void quotationRejectsInvalidAndAlreadyProcessedTransitions() {
        ErpQuotationMapper mapper = mock(ErpQuotationMapper.class);
        ErpQuotationService service = new ErpQuotationService(mapper,mock(ErpStockService.class));
        assertThrows(ServiceException.class,() -> service.transition(1L,3));
        verifyNoInteractions(mapper);
        try (MockedStatic<SecurityUtils> security = mockStatic(SecurityUtils.class)) {
            security.when(SecurityUtils::getUsername).thenReturn("tester");
            when(mapper.transition(1L,1,"tester")).thenReturn(0);
            assertThrows(ServiceException.class,() -> service.transition(1L,1));
            verify(mapper).transition(1L,1,"tester");
        }
    }

    @Test
    void businessQuestionChecksScopeBeforeReading() {
        ErpAiReadMapper mapper = mock(ErpAiReadMapper.class);
        PermissionService permissions = mock(PermissionService.class);
        ErpAiQuestionController controller = new ErpAiQuestionController(mapper,permissions,mock(LLMConfig.class));
        assertThrows(ServiceException.class,() -> controller.ask(new ErpAiQuestionController.Question("cost","查看成本")));
        verify(permissions).hasPermi("dashboard:styleCost:list");
        verifyNoInteractions(mapper);
    }

    @Test
    void overviewMasksGrossProfitWithoutCostPermission() {
        ErpReadMapper mapper = mock(ErpReadMapper.class);
        PermissionService permissions = mock(PermissionService.class);
        when(mapper.overview()).thenReturn(Map.of("orderCount", 4, "standardGrossProfit", 42880));
        ErpReadController controller = new ErpReadController(mapper, permissions);

        Map<?, ?> restricted = (Map<?, ?>) controller.overview().get("data");
        assertEquals(4, restricted.get("orderCount"));
        assertFalse(restricted.containsKey("standardGrossProfit"));

        when(permissions.hasPermi("fn:settlement:list")).thenReturn(true);
        Map<?, ?> authorized = (Map<?, ?>) controller.overview().get("data");
        assertEquals(42880, authorized.get("standardGrossProfit"));
    }
}

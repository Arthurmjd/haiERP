package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.StockAdjustRequest;
import net.factoryopen.myfactoryai.erp.service.ErpStockAdjustService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/st/txn")
public class ErpStockAdjustController {
    private final ErpStockAdjustService service;
    public ErpStockAdjustController(ErpStockAdjustService service) { this.service=service; }

    @PreAuthorize("@ss.hasPermi('st:txn:adjust')")
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody StockAdjustRequest request) { return AjaxResult.success(service.adjust(request)); }
}

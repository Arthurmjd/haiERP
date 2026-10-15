package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.CostRequest;
import net.factoryopen.myfactoryai.erp.service.ErpCostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/fn/settlement")
public class ErpCostController {
    private final ErpCostService service;
    public ErpCostController(ErpCostService service) { this.service=service; }

    @PreAuthorize("@ss.hasPermi('fn:settlement:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('fn:settlement:calc')")
    @PostMapping("/calc")
    public AjaxResult calculate(@RequestBody CostRequest request) { return AjaxResult.success(service.calculate(request)); }

    @PreAuthorize("@ss.hasPermi('fn:settlement:confirm')")
    @PostMapping("/confirm/{id}")
    public AjaxResult confirm(@PathVariable Long id) { service.confirm(id); return AjaxResult.success(); }
}

package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.CostRequest;
import net.factoryopen.myfactoryai.erp.service.ErpCostService;
import net.factoryopen.myfactoryai.erp.service.ErpDocService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final ErpDocService docs;

    public ErpCostController(ErpCostService service,ErpDocService docs) { this.service=service; this.docs=docs; }

    @PreAuthorize("@ss.hasPermi('fn:settlement:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('fn:settlement:calc')")
    @PostMapping("/calc")
    public AjaxResult calculate(@RequestBody CostRequest request) { return AjaxResult.success(service.calculate(request)); }

    @PreAuthorize("@ss.hasPermi('fn:settlement:confirm')")
    @PostMapping("/confirm/{id}")
    public AjaxResult confirm(@PathVariable Long id) { service.confirm(id); return AjaxResult.success(); }

    /** 删除试算中的成本单（含明细）。已确认的不能删除。 */
    @PreAuthorize("@ss.hasPermi('fn:settlement:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) { docs.deleteDraft("fn:settlement", id); return AjaxResult.success(); }
}

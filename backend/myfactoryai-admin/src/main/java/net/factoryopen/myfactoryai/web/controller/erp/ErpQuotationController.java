package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.QuotationRequest;
import net.factoryopen.myfactoryai.erp.service.ErpQuotationService;
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
@RequestMapping("/erp/sd/quotation")
public class ErpQuotationController {
    private final ErpQuotationService service;
    private final ErpDocService docs;

    public ErpQuotationController(ErpQuotationService service,ErpDocService docs) { this.service=service; this.docs=docs; }

    @PreAuthorize("@ss.hasPermi('sd:quotation:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('sd:quotation:fromSettlement')")
    @PostMapping("/fromSettlement")
    public AjaxResult fromSettlement(@RequestBody QuotationRequest request) { return AjaxResult.success(service.fromSettlement(request)); }

    @PreAuthorize("@ss.hasPermi('sd:quotation:accept')")
    @PostMapping("/accept/{id}")
    public AjaxResult accept(@PathVariable Long id) { service.transition(id,1); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('sd:quotation:expire')")
    @PostMapping("/expire/{id}")
    public AjaxResult expire(@PathVariable Long id) { service.transition(id,2); return AjaxResult.success(); }

    /** 删除待确认报价单（含明细）。已接受或已失效的报价单保留备查。 */
    @PreAuthorize("@ss.hasPermi('sd:quotation:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) { docs.deleteDraft("sd:quotation", id); return AjaxResult.success(); }
}

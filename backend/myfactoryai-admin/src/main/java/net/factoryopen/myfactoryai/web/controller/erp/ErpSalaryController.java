package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.SalaryPayRequest;
import net.factoryopen.myfactoryai.erp.domain.SalaryRequest;
import net.factoryopen.myfactoryai.erp.service.ErpSalaryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/hr/salarySlip")
public class ErpSalaryController {
    private final ErpSalaryService service;
    public ErpSalaryController(ErpSalaryService service) { this.service=service; }

    @PreAuthorize("@ss.hasPermi('hr:salarySlip:generate')")
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody SalaryRequest request) { return AjaxResult.success(service.generate(request)); }

    @PreAuthorize("@ss.hasPermi('hr:salarySlip:generateBatch')")
    @PostMapping("/generateBatch")
    public AjaxResult generateBatch(@RequestBody SalaryPayRequest.Batch request) {
        return AjaxResult.success(service.generateBatch(request == null ? null : request.wagePeriod()));
    }

    @PreAuthorize("@ss.hasPermi('hr:salarySlip:confirm')")
    @PostMapping("/confirm/{id}")
    public AjaxResult confirm(@PathVariable Long id) { service.confirm(id); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('fn:payroll:pay')")
    @PostMapping("/pay/{id}")
    public AjaxResult pay(@PathVariable Long id, @RequestBody SalaryPayRequest request) {
        return AjaxResult.success(service.pay(id, request));
    }
}

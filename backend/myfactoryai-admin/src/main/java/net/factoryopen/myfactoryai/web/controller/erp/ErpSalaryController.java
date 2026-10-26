package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
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

    @Log(title = "工资单", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('hr:salarySlip:generate')")
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody SalaryRequest request) { return AjaxResult.success(service.generate(request)); }

    @Log(title = "工资单确认", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hr:salarySlip:confirm')")
    @PostMapping("/confirm/{id}")
    public AjaxResult confirm(@PathVariable Long id) { service.confirm(id); return AjaxResult.success(); }
}

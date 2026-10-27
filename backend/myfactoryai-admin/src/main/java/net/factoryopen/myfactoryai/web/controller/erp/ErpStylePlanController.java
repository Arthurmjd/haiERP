package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.erp.domain.StylePlanRequest;
import net.factoryopen.myfactoryai.erp.service.ErpStylePlanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/md/style")
public class ErpStylePlanController {
    private final ErpStylePlanService service;
    public ErpStylePlanController(ErpStylePlanService service) { this.service=service; }

    @PreAuthorize("@ss.hasPermi('md:style:list')")
    @GetMapping("/{id}/bom")
    public AjaxResult bom(@PathVariable Long id) { return AjaxResult.success(service.bom(id)); }

    @PreAuthorize("@ss.hasPermi('md:style:list')")
    @GetMapping("/{id}/process")
    public AjaxResult processes(@PathVariable Long id) { return AjaxResult.success(service.processes(id)); }

    @PreAuthorize("@ss.hasPermi('md:style:edit')")
    @GetMapping("/options")
    public AjaxResult options() { return AjaxResult.success(service.options()); }

    @Log(title = "款式BOM", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('md:style:edit')")
    @PutMapping("/{id}/bom")
    public AjaxResult saveBom(@PathVariable Long id,@RequestBody StylePlanRequest.Bom request) { service.saveBom(id,request); return AjaxResult.success(); }

    @Log(title = "款式工序", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('md:style:edit')")
    @PutMapping("/{id}/process")
    public AjaxResult saveProcesses(@PathVariable Long id,@RequestBody StylePlanRequest.Processes request) { service.saveProcesses(id,request); return AjaxResult.success(); }
}

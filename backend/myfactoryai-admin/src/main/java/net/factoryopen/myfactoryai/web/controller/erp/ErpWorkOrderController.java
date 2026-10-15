package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.WorkOrderRequest;
import net.factoryopen.myfactoryai.erp.service.ErpWorkOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/pr/workOrder")
public class ErpWorkOrderController {
    private final ErpWorkOrderService service;
    public ErpWorkOrderController(ErpWorkOrderService service) { this.service = service; }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:add')")
    @PostMapping
    public AjaxResult create(@RequestBody WorkOrderRequest.Create request) { return AjaxResult.success(service.create(request)); }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:issue')")
    @PostMapping("/issue/{id}")
    public AjaxResult issue(@PathVariable Long id,@RequestBody WorkOrderRequest.Action request) {
        service.issue(id,request); return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('pr:process:report')")
    @PostMapping("/process/report")
    public AjaxResult report(@RequestBody WorkOrderRequest.Report request) {
        service.report(request); return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:finish')")
    @PostMapping("/finish/{id}")
    public AjaxResult finish(@PathVariable Long id,@RequestBody WorkOrderRequest.Finish request) {
        service.finish(id,request); return AjaxResult.success();
    }
}

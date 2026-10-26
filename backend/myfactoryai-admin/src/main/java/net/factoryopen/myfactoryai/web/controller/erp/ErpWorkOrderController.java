package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.WorkOrderRequest;
import net.factoryopen.myfactoryai.erp.service.ErpWorkOrderService;
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
@RequestMapping("/erp/pr/workOrder")
public class ErpWorkOrderController {
    private final ErpWorkOrderService service;
    private final ErpDocService docs;

    public ErpWorkOrderController(ErpWorkOrderService service, ErpDocService docs) {
        this.service = service;
        this.docs = docs;
    }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:add')")
    @PostMapping
    public AjaxResult create(@RequestBody WorkOrderRequest.Create request) { return AjaxResult.success(service.create(request)); }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:generate')")
    @PostMapping("/generate/{orderId}")
    public AjaxResult generate(@PathVariable Long orderId) { return AjaxResult.success(service.generate(orderId)); }

    @PreAuthorize("@ss.hasPermi('pr:workOrder:return')")
    @PostMapping("/return")
    public AjaxResult returnMaterial(@RequestBody WorkOrderRequest.Return request) {
        service.returnMaterial(request); return AjaxResult.success();
    }

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

    /** 删除待开工工单（含 SKU/用料/工序）。已领料或已报工时不允许。 */
    @PreAuthorize("@ss.hasPermi('pr:workOrder:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) { docs.deleteDraft("pr:workOrder", id); return AjaxResult.success(); }

    /** 作废待开工工单；领料后库存已变动，不允许作废。 */
    @PreAuthorize("@ss.hasPermi('pr:workOrder:cancel')")
    @PostMapping("/cancel/{id}")
    public AjaxResult cancel(@PathVariable Long id) { docs.cancel("pr:workOrder", id); return AjaxResult.success(); }
}

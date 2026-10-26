package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.service.ErpWorkflowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/workflow")
public class ErpWorkflowController {
    private final ErpWorkflowService service;

    public ErpWorkflowController(ErpWorkflowService service) { this.service = service; }

    @PreAuthorize("@ss.hasPermi('workflow:todo:list')")
    @GetMapping("/todos")
    public AjaxResult todos() { return AjaxResult.success(service.myTodos()); }

    @PreAuthorize("@ss.hasPermi('workflow:approval:list')")
    @GetMapping("/approvals")
    public AjaxResult approvals() { return AjaxResult.success(service.pendingApprovals()); }
}

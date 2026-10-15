package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import com.ruoyi.framework.web.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp")
public class ErpReadController {
    private final ErpReadMapper mapper;
    private final PermissionService permissionService;

    public ErpReadController(ErpReadMapper mapper, PermissionService permissionService) {
        this.mapper = mapper;
        this.permissionService = permissionService;
    }

    @PreAuthorize("@ss.hasPermi('sd:order:add')")
    @GetMapping("/md/orderOptions")
    public AjaxResult orderOptions() {
        Map<String, Object> result = new HashMap<>();
        result.put("customers", mapper.customers());
        result.put("styles", mapper.styles());
        result.put("colors", mapper.colors());
        result.put("sizes", mapper.sizes());
        result.put("skus", mapper.skus());
        return AjaxResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:add')")
    @GetMapping("/md/purchaseOptions")
    public AjaxResult purchaseOptions() {
        return AjaxResult.success(Map.of("suppliers",mapper.suppliers(),"materials",mapper.materials()));
    }

    @PreAuthorize("@ss.hasPermi('pr:process:report')")
    @GetMapping("/md/workOrderOptions")
    public AjaxResult workOrderOptions() { return AjaxResult.success(Map.of("workers",mapper.workers())); }

    @PreAuthorize("@ss.hasPermi('pu:suggestion:list')")
    @GetMapping("/pu/suggestion/list")
    public AjaxResult mrp() {
        return AjaxResult.success(mapper.mrp());
    }

    @PreAuthorize("@ss.hasPermi('dashboard:mrp:list')")
    @GetMapping("/dashboard/mrp")
    public AjaxResult dashboardMrp() { return AjaxResult.success(mapper.mrp()); }

    @PreAuthorize("@ss.hasPermi('st:inventory:list')")
    @GetMapping("/st/inventory/list")
    public AjaxResult inventory() {
        return AjaxResult.success(mapper.inventory());
    }

    @PreAuthorize("@ss.hasPermi('dashboard:overview:list')")
    @GetMapping("/dashboard/overview")
    public AjaxResult overview() {
        Map<String, Object> overview = new HashMap<>(mapper.overview());
        if (!permissionService.hasPermi("fn:settlement:list")) {
            overview.remove("standardGrossProfit");
        }
        return AjaxResult.success(overview);
    }
}

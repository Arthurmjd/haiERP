package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.service.ErpMasterService;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/md")
public class ErpMasterController {
    private final ErpMasterService service;
    private final ErpReadMapper read;
    public ErpMasterController(ErpMasterService service,ErpReadMapper read) { this.service=service; this.read=read; }

    @PreAuthorize("@ss.hasPermi('md:sku:generate')")
    @GetMapping("/sku/options")
    public AjaxResult skuOptions() { return AjaxResult.success(Map.of("styles",read.styles(),"colors",read.colors(),"sizes",read.sizes())); }

    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':list')")
    @GetMapping("/{resource}/{id}")
    public AjaxResult detail(@P("resource") @PathVariable String resource,@PathVariable Long id) { return AjaxResult.success(service.detail(resource,id)); }

    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':add')")
    @PostMapping("/{resource}")
    public AjaxResult create(@P("resource") @PathVariable String resource,@RequestBody Map<String,Object> input) { return AjaxResult.success(service.create(resource,input)); }

    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':edit')")
    @PutMapping("/{resource}/{id}")
    public AjaxResult update(@P("resource") @PathVariable String resource,@PathVariable Long id,@RequestBody Map<String,Object> input) { service.update(resource,id,input); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('md:sku:generate')")
    @PostMapping("/sku/generate")
    public AjaxResult generateSku(@RequestBody Map<String,Long> input) {
        return AjaxResult.success(service.generateSku(input.get("styleId"),input.get("colorId"),input.get("sizeId")));
    }
}

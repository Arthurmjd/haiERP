package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.Map;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.service.ErpMasterService;
import net.factoryopen.myfactoryai.erp.mapper.ErpMasterMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final ErpMasterMapper master;
    public ErpMasterController(ErpMasterService service,ErpReadMapper read,ErpMasterMapper master) {
        this.service=service; this.read=read; this.master=master;
    }

    @PreAuthorize("@ss.hasPermi('md:sku:generate')")
    @GetMapping("/sku/options")
    public AjaxResult skuOptions() { return AjaxResult.success(Map.of("styles",read.styles(),"colors",read.colors(),"sizes",read.sizes())); }

    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':list')")
    @GetMapping("/{resource}/{id}")
    public AjaxResult detail(@P("resource") @PathVariable String resource,@PathVariable Long id) { return AjaxResult.success(service.detail(resource,id)); }

    @Log(title = "主数据", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':add')")
    @PostMapping("/{resource}")
    public AjaxResult create(@P("resource") @PathVariable String resource,@RequestBody Map<String,Object> input) { return AjaxResult.success(service.create(resource,input)); }

    @Log(title = "主数据", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':edit')")
    @PutMapping("/{resource}/{id}")
    public AjaxResult update(@P("resource") @PathVariable String resource,@PathVariable Long id,@RequestBody Map<String,Object> input) { service.update(resource,id,input); return AjaxResult.success(); }

    @Log(title = "主数据", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('md:' + #resource + ':remove')")
    @DeleteMapping("/{resource}/{id}")
    public AjaxResult remove(@P("resource") @PathVariable String resource,@PathVariable Long id) { service.delete(resource,id); return AjaxResult.success(); }

    @Log(title = "生成SKU", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('md:sku:generate')")
    @PostMapping("/sku/generate")
    public AjaxResult generateSku(@RequestBody Map<String,Long> input) {
        return AjaxResult.success(service.generateSku(input.get("styleId"),input.get("colorId"),input.get("sizeId")));
    }

    // ── 工序可报工员工（报工时按工序限定可选员工）────────────────────

    @PreAuthorize("@ss.hasPermi('md:process:assign')")
    @GetMapping("/process/{id}/worker")
    public AjaxResult processWorkers(@PathVariable Long id) {
        if (master.processActive(id) != 1) throw new ServiceException("工序不存在或已停用", ErpErrorCode.INVALID_PARAM.code());
        return AjaxResult.success(Map.of(
                "assigned", master.processWorkers(id),
                "candidates", master.processWorkerCandidates(id)));
    }

    @Log(title = "工序员工", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('md:process:assign')")
    @PostMapping("/process/{id}/worker/{empId}")
    public AjaxResult addProcessWorker(@PathVariable Long id,@PathVariable Long empId) {
        if (master.processActive(id) != 1) throw new ServiceException("工序不存在或已停用", ErpErrorCode.INVALID_PARAM.code());
        if (master.workerActive(empId) != 1) throw new ServiceException("员工不存在、已离职或不是生产工人", ErpErrorCode.INVALID_PARAM.code());
        master.insertProcessWorker(id, empId, SecurityUtils.getUsername());
        return AjaxResult.success();
    }

    @Log(title = "工序员工", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('md:process:assign')")
    @DeleteMapping("/process/{id}/worker/{empId}")
    public AjaxResult removeProcessWorker(@PathVariable Long id,@PathVariable Long empId) {
        master.deleteProcessWorker(id, empId);
        return AjaxResult.success();
    }
}

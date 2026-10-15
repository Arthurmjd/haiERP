package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.Map;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.service.ErpMasterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/hr/employee")
public class ErpEmployeeController {
    private final ErpMasterService service;
    public ErpEmployeeController(ErpMasterService service) { this.service=service; }

    @PreAuthorize("@ss.hasPermi('hr:employee:list')")
    @GetMapping("/options")
    public AjaxResult options() { return AjaxResult.success(service.departments()); }

    @PreAuthorize("@ss.hasPermi('hr:employee:list')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail("employee",id)); }

    @PreAuthorize("@ss.hasPermi('hr:employee:add')")
    @PostMapping
    public AjaxResult create(@RequestBody Map<String,Object> input) { return AjaxResult.success(service.create("employee",input)); }

    @PreAuthorize("@ss.hasPermi('hr:employee:edit')")
    @PutMapping("/{id}")
    public AjaxResult update(@PathVariable Long id,@RequestBody Map<String,Object> input) { service.update("employee",id,input); return AjaxResult.success(); }
}

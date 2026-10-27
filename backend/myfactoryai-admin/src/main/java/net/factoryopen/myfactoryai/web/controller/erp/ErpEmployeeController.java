package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.Map;
import java.util.List;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.erp.service.ErpMasterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Log(title = "员工", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('hr:employee:add')")
    @PostMapping
    public AjaxResult create(@RequestBody Map<String,Object> input) { return AjaxResult.success(service.create("employee",input)); }

    @Log(title = "员工", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hr:employee:edit')")
    @PutMapping("/{id}")
    public AjaxResult update(@PathVariable Long id,@RequestBody Map<String,Object> input) { service.update("employee",id,input); return AjaxResult.success(); }

    /** 离职 / 复职：请求体 {"status":0} 离职，{"status":1} 复职 */
    @Log(title = "员工离职复职", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hr:employee:edit')")
    @PostMapping("/{id}/status")
    public AjaxResult changeStatus(@PathVariable Long id,@RequestBody Map<String,Object> input) {
        Object raw = input == null ? null : input.get("status");
        if (raw == null) throw new com.ruoyi.common.exception.ServiceException("请指定 status：0 离职，1 复职",1001);
        int status;
        try { status = Integer.parseInt(raw.toString()); }
        catch (NumberFormatException e) { throw new com.ruoyi.common.exception.ServiceException("status 不合法",1001); }
        service.changeStatus("employee",id,status);
        return AjaxResult.success();
    }

    @Log(title = "员工", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('hr:employee:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) { service.delete("employee",id); return AjaxResult.success(); }
}

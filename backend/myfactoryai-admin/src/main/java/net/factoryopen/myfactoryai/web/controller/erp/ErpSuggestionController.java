package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.erp.domain.SuggestionRequest;
import net.factoryopen.myfactoryai.erp.service.ErpSuggestionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/pu/suggestion")
public class ErpSuggestionController {
    private final ErpSuggestionService service;
    public ErpSuggestionController(ErpSuggestionService service) { this.service=service; }

    @Log(title = "缺料生成采购", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('pu:suggestion:generate')")
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody SuggestionRequest request) { return AjaxResult.success(service.generate(request)); }
}

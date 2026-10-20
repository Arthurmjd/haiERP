package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import net.factoryopen.myfactoryai.erp.domain.PurchaseDraftRequest;
import net.factoryopen.myfactoryai.erp.domain.PurchaseReceiptRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpPurchaseMapper;
import net.factoryopen.myfactoryai.erp.service.ErpPurchaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/pu/purchaseOrder")
public class ErpPurchaseController {
    private final ErpPurchaseMapper mapper;
    private final ErpPurchaseService service;

    public ErpPurchaseController(ErpPurchaseMapper mapper, ErpPurchaseService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(defaultValue="1") int pageNum,
                              @RequestParam(defaultValue="10") int pageSize,
                              @RequestParam(required=false) String keyword) {
        PageHelper.startPage(Math.max(1,pageNum),Math.max(1,Math.min(500,pageSize)));
        List<Map<String,Object>> rows = mapper.list(keyword);
        TableDataInfo result = new TableDataInfo();
        result.setCode(200);
        result.setMsg("查询成功");
        result.setRows(rows);
        result.setTotal(new PageInfo<>(rows).getTotal());
        return result;
    }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:add')")
    @PostMapping
    public AjaxResult create(@RequestBody PurchaseDraftRequest request) { return AjaxResult.success(service.create(request)); }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:edit')")
    @PutMapping("/{id}")
    public AjaxResult update(@PathVariable Long id, @RequestBody PurchaseDraftRequest request) {
        return AjaxResult.success(service.update(id, request));
    }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:confirm')")
    @PostMapping("/confirm/{id}")
    public AjaxResult confirm(@PathVariable Long id) { service.confirm(id); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:receive')")
    @PostMapping("/receive")
    public AjaxResult receive(@RequestBody PurchaseReceiptRequest request) { return AjaxResult.success(service.receive(request)); }
}

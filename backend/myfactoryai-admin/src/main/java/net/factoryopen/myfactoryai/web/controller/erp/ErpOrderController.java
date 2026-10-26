package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import net.factoryopen.myfactoryai.erp.domain.OrderDraftRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpOrderMapper;
import net.factoryopen.myfactoryai.erp.service.ErpDocService;
import net.factoryopen.myfactoryai.erp.service.ErpOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/sd/order")
public class ErpOrderController {
    private final ErpOrderMapper mapper;
    private final ErpOrderService service;
    private final ErpDocService docs;

    public ErpOrderController(ErpOrderMapper mapper, ErpOrderService service, ErpDocService docs) {
        this.mapper = mapper;
        this.service = service;
        this.docs = docs;
    }

    @PreAuthorize("@ss.hasPermi('sd:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(required = false) String keyword) {
        int safePage = Math.max(1, pageNum);
        int safeSize = Math.max(1, Math.min(500, pageSize));
        PageHelper.startPage(safePage, safeSize);
        List<Map<String, Object>> rows = mapper.list(keyword);
        TableDataInfo response = new TableDataInfo();
        response.setCode(200);
        response.setMsg("查询成功");
        response.setRows(rows);
        response.setTotal(new PageInfo<>(rows).getTotal());
        return response;
    }

    @PreAuthorize("@ss.hasPermi('sd:order:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return AjaxResult.success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('sd:order:add')")
    @PostMapping
    public AjaxResult create(@RequestBody OrderDraftRequest request) {
        return AjaxResult.success(service.create(request));
    }

    @PreAuthorize("@ss.hasPermi('sd:order:edit')")
    @PutMapping("/{id}")
    public AjaxResult update(@PathVariable Long id, @RequestBody OrderDraftRequest request) {
        return AjaxResult.success(service.update(id, request));
    }

    @PreAuthorize("@ss.hasPermi('sd:order:confirm')")
    @PostMapping("/confirm/{id}")
    public AjaxResult confirm(@PathVariable Long id) {
        service.confirm(id);
        return AjaxResult.success();
    }

    /** 删除草稿订单（含明细）。已确认或已产生工单/出货单时不允许。 */
    @PreAuthorize("@ss.hasPermi('sd:order:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        docs.deleteDraft("sd:order", id);
        return AjaxResult.success();
    }

    /** 作废订单：草稿或已确认可作废，已有工单/出货单时不允许。 */
    @PreAuthorize("@ss.hasPermi('sd:order:cancel')")
    @PostMapping("/cancel/{id}")
    public AjaxResult cancel(@PathVariable Long id) {
        docs.cancel("sd:order", id);
        return AjaxResult.success();
    }
}

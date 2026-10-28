package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import java.util.ArrayList;
import java.util.List;
import net.factoryopen.myfactoryai.erp.domain.ShipmentRequest;
import net.factoryopen.myfactoryai.erp.service.ErpShipmentService;
import net.factoryopen.myfactoryai.erp.service.ErpDocService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/sh")
public class ErpShipmentController {
    private final ErpShipmentService service;
    private final ErpDocService docs;

    public ErpShipmentController(ErpShipmentService service, ErpDocService docs) {
        this.service = service;
        this.docs = docs;
    }

    @Log(title = "出货单", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('sh:shipment:query')")
    @GetMapping("/shipment/{id}")
    public AjaxResult shipment(@PathVariable Long id) { return AjaxResult.success(service.shipment(id)); }

    @PreAuthorize("@ss.hasPermi('sh:shipment:add')")
    @PostMapping("/shipment")
    public AjaxResult create(@RequestBody ShipmentRequest.Create request) { return AjaxResult.success(service.create(request)); }

    /**
     * 待出货订单清单（仓管员工作台用）。
     *
     * <p>出货入口原来只挂在销售订单详情页，而仓管员没有 sd:order:query 权限，
     * 于是「生产已完工、仓管却找不到这张单」。这里用出货自己的权限点暴露一份
     * 不含金额的清单：订单号、客户、每个 SKU 的未发数量与成品可用量。</p>
     */
    @PreAuthorize("@ss.hasPermi('sh:shipment:add')")
    @GetMapping("/pendingShipments")
    public AjaxResult pendingOrders() { return AjaxResult.success(service.pendingOrders()); }

    /**
     * 按 SKU 查可用成品批次及其结存，供「创建出货单」弹窗下拉选择。
     * 不传 skuIds 返回空列表；多个 SKU 用英文逗号分隔，如 skuIds=1,2,3。
     */
    @PreAuthorize("@ss.hasPermi('sh:shipment:query')")
    @GetMapping("/shipment/batches")
    public AjaxResult productBatches(@RequestParam(required = false) String skuIds) {
        List<Long> ids = new ArrayList<>();
        if (skuIds != null && !skuIds.isBlank()) {
            for (String part : skuIds.split(",")) {
                String text = part.trim();
                if (text.isEmpty()) continue;
                try { ids.add(Long.valueOf(text)); } catch (NumberFormatException ignored) { /* 非法 ID 直接跳过 */ }
            }
        }
        return AjaxResult.success(service.productBatches(ids));
    }

    @Log(title = "出货发货", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('sh:shipment:ship')")
    @PostMapping("/shipment/ship/{id}")
    public AjaxResult ship(@PathVariable Long id,@RequestBody ShipmentRequest.Action request) { service.ship(id,request); return AjaxResult.success(); }

    @Log(title = "出货签收", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('sh:shipment:sign')")
    @PostMapping("/shipment/sign/{id}")
    public AjaxResult sign(@PathVariable Long id) { service.sign(id); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('sh:return:query')")
    @GetMapping("/return/{id}")
    public AjaxResult returned(@PathVariable Long id) { return AjaxResult.success(service.returned(id)); }

    @Log(title = "退货单", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('sh:return:add')")
    @PostMapping("/return")
    public AjaxResult createReturn(@RequestBody ShipmentRequest.ReturnCreate request) { return AjaxResult.success(service.createReturn(request)); }

    @Log(title = "退货处理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('sh:return:handle')")
    @PostMapping("/return/handle/{id}")
    public AjaxResult handleReturn(@PathVariable Long id,@RequestBody ShipmentRequest.Action request) { service.handleReturn(id,request); return AjaxResult.success(); }

    /** 删除待发货出货单（含明细）。已发货后不允许，避免掩盖出库记录。 */
    @Log(title = "出货单", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('sh:shipment:remove')")
    @DeleteMapping("/shipment/{id}")
    public AjaxResult removeShipment(@PathVariable Long id) { docs.deleteDraft("sh:shipment", id); return AjaxResult.success(); }

    /** 作废待发货出货单。 */
    @Log(title = "出货单作废", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('sh:shipment:cancel')")
    @PostMapping("/shipment/cancel/{id}")
    public AjaxResult cancelShipment(@PathVariable Long id) { docs.cancel("sh:shipment", id); return AjaxResult.success(); }

    /** 删除待处理退货单（含明细）。已处理的不允许删除。 */
    @Log(title = "退货单", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('sh:return:remove')")
    @DeleteMapping("/return/{id}")
    public AjaxResult removeReturn(@PathVariable Long id) { docs.deleteDraft("sh:return", id); return AjaxResult.success(); }

    /** 作废待处理退货单。 */
    @Log(title = "退货单作废", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('sh:return:cancel')")
    @PostMapping("/return/cancel/{id}")
    public AjaxResult cancelReturn(@PathVariable Long id) { docs.cancel("sh:return", id); return AjaxResult.success(); }
}

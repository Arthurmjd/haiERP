package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
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

    @PreAuthorize("@ss.hasPermi('sh:shipment:query')")
    @GetMapping("/shipment/{id}")
    public AjaxResult shipment(@PathVariable Long id) { return AjaxResult.success(service.shipment(id)); }

    @PreAuthorize("@ss.hasPermi('sh:shipment:add')")
    @PostMapping("/shipment")
    public AjaxResult create(@RequestBody ShipmentRequest.Create request) { return AjaxResult.success(service.create(request)); }

    @PreAuthorize("@ss.hasPermi('sh:shipment:ship')")
    @PostMapping("/shipment/ship/{id}")
    public AjaxResult ship(@PathVariable Long id,@RequestBody ShipmentRequest.Action request) { service.ship(id,request); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('sh:shipment:sign')")
    @PostMapping("/shipment/sign/{id}")
    public AjaxResult sign(@PathVariable Long id) { service.sign(id); return AjaxResult.success(); }

    @PreAuthorize("@ss.hasPermi('sh:return:query')")
    @GetMapping("/return/{id}")
    public AjaxResult returned(@PathVariable Long id) { return AjaxResult.success(service.returned(id)); }

    @PreAuthorize("@ss.hasPermi('sh:return:add')")
    @PostMapping("/return")
    public AjaxResult createReturn(@RequestBody ShipmentRequest.ReturnCreate request) { return AjaxResult.success(service.createReturn(request)); }

    @PreAuthorize("@ss.hasPermi('sh:return:handle')")
    @PostMapping("/return/handle/{id}")
    public AjaxResult handleReturn(@PathVariable Long id,@RequestBody ShipmentRequest.Action request) { service.handleReturn(id,request); return AjaxResult.success(); }

    /** 删除待发货出货单（含明细）。已发货后不允许，避免掩盖出库记录。 */
    @PreAuthorize("@ss.hasPermi('sh:shipment:remove')")
    @DeleteMapping("/shipment/{id}")
    public AjaxResult removeShipment(@PathVariable Long id) { docs.deleteDraft("sh:shipment", id); return AjaxResult.success(); }

    /** 作废待发货出货单。 */
    @PreAuthorize("@ss.hasPermi('sh:shipment:cancel')")
    @PostMapping("/shipment/cancel/{id}")
    public AjaxResult cancelShipment(@PathVariable Long id) { docs.cancel("sh:shipment", id); return AjaxResult.success(); }

    /** 删除待处理退货单（含明细）。已处理的不允许删除。 */
    @PreAuthorize("@ss.hasPermi('sh:return:remove')")
    @DeleteMapping("/return/{id}")
    public AjaxResult removeReturn(@PathVariable Long id) { docs.deleteDraft("sh:return", id); return AjaxResult.success(); }

    /** 作废待处理退货单。 */
    @PreAuthorize("@ss.hasPermi('sh:return:cancel')")
    @PostMapping("/return/cancel/{id}")
    public AjaxResult cancelReturn(@PathVariable Long id) { docs.cancel("sh:return", id); return AjaxResult.success(); }
}

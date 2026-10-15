package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.ShipmentRequest;
import net.factoryopen.myfactoryai.erp.service.ErpShipmentService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ErpShipmentController(ErpShipmentService service) { this.service = service; }

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
}

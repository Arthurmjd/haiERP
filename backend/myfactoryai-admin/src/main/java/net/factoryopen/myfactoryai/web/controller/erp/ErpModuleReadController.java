package net.factoryopen.myfactoryai.web.controller.erp;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.function.Supplier;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import net.factoryopen.myfactoryai.erp.mapper.ErpModuleReadMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpStockMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp")
public class ErpModuleReadController {
    private final ErpModuleReadMapper mapper;
    private final ErpStockMapper stock;

    public ErpModuleReadController(ErpModuleReadMapper mapper, ErpStockMapper stock) {
        this.mapper = mapper;
        this.stock = stock;
    }

    private TableDataInfo page(int pageNum,int pageSize,Supplier<List<Map<String,Object>>> query) {
        PageHelper.startPage(Math.max(1,pageNum),Math.max(1,Math.min(500,pageSize)));
        List<Map<String,Object>> rows = query.get();
        TableDataInfo result = new TableDataInfo();
        result.setCode(200);
        result.setMsg("查询成功");
        result.setRows(rows);
        result.setTotal(new PageInfo<>(rows).getTotal());
        return result;
    }

    @PreAuthorize("@ss.hasPermi('md:category:list')") @GetMapping("/md/category/list")
    public TableDataInfo categories(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::categories); }
    @PreAuthorize("@ss.hasPermi('md:color:list')") @GetMapping("/md/color/list")
    public TableDataInfo colors(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::colors); }
    @PreAuthorize("@ss.hasPermi('md:size:list')") @GetMapping("/md/size/list")
    public TableDataInfo sizes(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::sizes); }
    @PreAuthorize("@ss.hasPermi('md:style:list')") @GetMapping("/md/style/list")
    public TableDataInfo styles(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::styles); }
    @PreAuthorize("@ss.hasPermi('md:sku:list')") @GetMapping("/md/sku/list")
    public TableDataInfo skus(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::skus); }
    @PreAuthorize("@ss.hasPermi('md:material:list')") @GetMapping("/md/material/list")
    public TableDataInfo materials(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::materials); }
    @PreAuthorize("@ss.hasPermi('md:process:list')") @GetMapping("/md/process/list")
    public TableDataInfo processes(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::processes); }
    @PreAuthorize("@ss.hasPermi('md:customer:list')") @GetMapping("/md/customer/list")
    public TableDataInfo customers(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::customers); }
    @PreAuthorize("@ss.hasPermi('md:supplier:list')") @GetMapping("/md/supplier/list")
    public TableDataInfo suppliers(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::suppliers); }
    @PreAuthorize("@ss.hasPermi('hr:employee:list')") @GetMapping("/hr/employee/list")
    public TableDataInfo employees(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::employees); }
    @PreAuthorize("@ss.hasPermi('hr:pieceWage:list')") @GetMapping("/hr/pieceWage/list")
    public TableDataInfo wages(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::pieceWages); }
    @PreAuthorize("@ss.hasPermi('hr:salarySlip:list')") @GetMapping("/hr/salarySlip/list")
    public TableDataInfo salary(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::salarySlips); }
    @PreAuthorize("@ss.hasPermi('sd:quotation:list')") @GetMapping("/sd/quotation/list")
    public TableDataInfo quotations(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::quotations); }
    @PreAuthorize("@ss.hasPermi('pr:workOrder:list')") @GetMapping("/pr/workOrder/list")
    public TableDataInfo workOrders(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::workOrders); }
    @PreAuthorize("@ss.hasPermi('st:txn:list')") @GetMapping("/st/txn/list")
    public TableDataInfo txns(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,stock::txns); }
    @PreAuthorize("@ss.hasPermi('sh:shipment:list')") @GetMapping("/sh/shipment/list")
    public TableDataInfo shipments(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::shipments); }
    @PreAuthorize("@ss.hasPermi('sh:return:list')") @GetMapping("/sh/return/list")
    public TableDataInfo returns(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::returns); }
    @PreAuthorize("@ss.hasPermi('fn:settlement:list')") @GetMapping("/fn/settlement/list")
    public TableDataInfo settlements(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::settlements); }
    @PreAuthorize("@ss.hasPermi('dashboard:ontime:list')") @GetMapping("/dashboard/ontime")
    public TableDataInfo ontime(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize,
                                @RequestParam(required=false) LocalDate asOfDate) {
        LocalDate date = asOfDate == null ? LocalDate.now() : asOfDate;
        if (date.isAfter(LocalDate.now())) throw new ServiceException("截至日期不能晚于今天",1001);
        return page(pageNum,pageSize,() -> mapper.ontimeAt(date));
    }
    @PreAuthorize("@ss.hasPermi('dashboard:styleCost:list')") @GetMapping("/dashboard/styleCost")
    public TableDataInfo styleCost(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="10") int pageSize) { return page(pageNum,pageSize,mapper::styleCost); }
}

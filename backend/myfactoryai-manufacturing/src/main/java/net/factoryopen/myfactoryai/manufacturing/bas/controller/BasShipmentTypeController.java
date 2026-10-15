package net.factoryopen.myfactoryai.manufacturing.bas.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasShipmentType;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasShipmentTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 交货方式Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/shipmentType")
public class BasShipmentTypeController extends BaseController
{
    @Autowired
    private IBasShipmentTypeService basShipmentTypeService;

/**
 * 查询交货方式列表
 */
//@PreAuthorize("@ss.hasPermi('bas:shipmentType:list')")
@GetMapping("/list")
    public TableDataInfo list(BasShipmentType basShipmentType)
    {
        //startPage();
        List<BasShipmentType> list = basShipmentTypeService.selectBasShipmentTypeList(basShipmentType);
        return getDataTable(list);
    }

    /**
     * 导出交货方式列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:shipmentType:export')")
    @Log(title = "交货方式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasShipmentType basShipmentType)
    {
        List<BasShipmentType> list = basShipmentTypeService.selectBasShipmentTypeList(basShipmentType);
        ExcelUtil<BasShipmentType> util = new ExcelUtil<BasShipmentType>(BasShipmentType.class);
        util.exportExcel(response, list, "交货方式数据");
    }

    /**
     * 获取交货方式详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:shipmentType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basShipmentTypeService.selectBasShipmentTypeById(id));
    }

    /**
     * 新增交货方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:shipmentType:add')")
    @Log(title = "交货方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasShipmentType basShipmentType)
    {
        return toAjax(basShipmentTypeService.insertBasShipmentType(basShipmentType));
    }

    /**
     * 修改交货方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:shipmentType:edit')")
    @Log(title = "交货方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasShipmentType basShipmentType)
    {
        return toAjax(basShipmentTypeService.updateBasShipmentType(basShipmentType));
    }

    /**
     * 删除交货方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:shipmentType:remove')")
    @Log(title = "交货方式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basShipmentTypeService.deleteBasShipmentTypeByIds(ids));
    }
}

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
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasInvoicePatternService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开票方式Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/invoicePattern")
public class BasInvoicePatternController extends BaseController
{
    @Autowired
    private IBasInvoicePatternService basInvoicePatternService;

/**
 * 查询开票方式列表
 */
//@PreAuthorize("@ss.hasPermi('bas:invoicePattern:list')")
@GetMapping("/list")
    public TableDataInfo list(BasInvoicePattern basInvoicePattern)
    {
        //startPage();
        List<BasInvoicePattern> list = basInvoicePatternService.selectBasInvoicePatternList(basInvoicePattern);
        return getDataTable(list);
    }

    /**
     * 导出开票方式列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:invoicePattern:export')")
    @Log(title = "开票方式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasInvoicePattern basInvoicePattern)
    {
        List<BasInvoicePattern> list = basInvoicePatternService.selectBasInvoicePatternList(basInvoicePattern);
        ExcelUtil<BasInvoicePattern> util = new ExcelUtil<BasInvoicePattern>(BasInvoicePattern.class);
        util.exportExcel(response, list, "开票方式数据");
    }

    /**
     * 获取开票方式详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:invoicePattern:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basInvoicePatternService.selectBasInvoicePatternById(id));
    }

    /**
     * 新增开票方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:invoicePattern:add')")
    @Log(title = "开票方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasInvoicePattern basInvoicePattern)
    {
        return toAjax(basInvoicePatternService.insertBasInvoicePattern(basInvoicePattern));
    }

    /**
     * 修改开票方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:invoicePattern:edit')")
    @Log(title = "开票方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasInvoicePattern basInvoicePattern)
    {
        return toAjax(basInvoicePatternService.updateBasInvoicePattern(basInvoicePattern));
    }

    /**
     * 删除开票方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:invoicePattern:remove')")
    @Log(title = "开票方式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basInvoicePatternService.deleteBasInvoicePatternByIds(ids));
    }
}

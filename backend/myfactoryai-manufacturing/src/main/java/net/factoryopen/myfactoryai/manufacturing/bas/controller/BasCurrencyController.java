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
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasCurrency;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasCurrencyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 货币Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/currency")
public class BasCurrencyController extends BaseController
{
    @Autowired
    private IBasCurrencyService basCurrencyService;

/**
 * 查询货币列表
 */
//@PreAuthorize("@ss.hasPermi('bas:currency:list')")
@GetMapping("/list")
    public TableDataInfo list(BasCurrency basCurrency)
    {
        //startPage();
        List<BasCurrency> list = basCurrencyService.selectBasCurrencyList(basCurrency);
        return getDataTable(list);
    }

    /**
     * 导出货币列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:currency:export')")
    @Log(title = "货币", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasCurrency basCurrency)
    {
        List<BasCurrency> list = basCurrencyService.selectBasCurrencyList(basCurrency);
        ExcelUtil<BasCurrency> util = new ExcelUtil<BasCurrency>(BasCurrency.class);
        util.exportExcel(response, list, "货币数据");
    }

    /**
     * 获取货币详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:currency:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basCurrencyService.selectBasCurrencyById(id));
    }

    /**
     * 新增货币
     */
//    @PreAuthorize("@ss.hasPermi('bas:currency:add')")
    @Log(title = "货币", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasCurrency basCurrency)
    {
        return toAjax(basCurrencyService.insertBasCurrency(basCurrency));
    }

    /**
     * 修改货币
     */
//    @PreAuthorize("@ss.hasPermi('bas:currency:edit')")
    @Log(title = "货币", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasCurrency basCurrency)
    {
        return toAjax(basCurrencyService.updateBasCurrency(basCurrency));
    }

    /**
     * 删除货币
     */
//    @PreAuthorize("@ss.hasPermi('bas:currency:remove')")
    @Log(title = "货币", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basCurrencyService.deleteBasCurrencyByIds(ids));
    }
}

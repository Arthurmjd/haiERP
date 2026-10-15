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
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单位Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/unit")
public class BasUnitController extends BaseController
{
    @Autowired
    private IBasUnitService basUnitService;

/**
 * 查询单位列表
 */
//@PreAuthorize("@ss.hasPermi('bas:unit:list')")
@GetMapping("/list")
    public TableDataInfo list(BasUnit basUnit)
    {
        //startPage();
        List<BasUnit> list = basUnitService.selectBasUnitList(basUnit);
        return getDataTable(list);
    }

    /**
     * 导出单位列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:unit:export')")
    @Log(title = "单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasUnit basUnit)
    {
        List<BasUnit> list = basUnitService.selectBasUnitList(basUnit);
        ExcelUtil<BasUnit> util = new ExcelUtil<BasUnit>(BasUnit.class);
        util.exportExcel(response, list, "单位数据");
    }

    /**
     * 获取单位详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:unit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basUnitService.selectBasUnitById(id));
    }

    /**
     * 新增单位
     */
//    @PreAuthorize("@ss.hasPermi('bas:unit:add')")
    @Log(title = "单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasUnit basUnit)
    {
        return toAjax(basUnitService.insertBasUnit(basUnit));
    }

    /**
     * 修改单位
     */
//    @PreAuthorize("@ss.hasPermi('bas:unit:edit')")
    @Log(title = "单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasUnit basUnit)
    {
        return toAjax(basUnitService.updateBasUnit(basUnit));
    }

    /**
     * 删除单位
     */
//    @PreAuthorize("@ss.hasPermi('bas:unit:remove')")
    @Log(title = "单位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basUnitService.deleteBasUnitByIds(ids));
    }
}

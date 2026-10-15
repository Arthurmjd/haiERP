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
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasPayPattern;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasPayPatternService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 付款方式Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/payPattern")
public class BasPayPatternController extends BaseController
{
    @Autowired
    private IBasPayPatternService basPayPatternService;

/**
 * 查询付款方式列表
 */
//@PreAuthorize("@ss.hasPermi('bas:payPattern:list')")
@GetMapping("/list")
    public TableDataInfo list(BasPayPattern basPayPattern)
    {
        //startPage();
        List<BasPayPattern> list = basPayPatternService.selectBasPayPatternList(basPayPattern);
        return getDataTable(list);
    }

    /**
     * 导出付款方式列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:payPattern:export')")
    @Log(title = "付款方式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasPayPattern basPayPattern)
    {
        List<BasPayPattern> list = basPayPatternService.selectBasPayPatternList(basPayPattern);
        ExcelUtil<BasPayPattern> util = new ExcelUtil<BasPayPattern>(BasPayPattern.class);
        util.exportExcel(response, list, "付款方式数据");
    }

    /**
     * 获取付款方式详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:payPattern:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basPayPatternService.selectBasPayPatternById(id));
    }

    /**
     * 新增付款方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:payPattern:add')")
    @Log(title = "付款方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasPayPattern basPayPattern)
    {
        return toAjax(basPayPatternService.insertBasPayPattern(basPayPattern));
    }

    /**
     * 修改付款方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:payPattern:edit')")
    @Log(title = "付款方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasPayPattern basPayPattern)
    {
        return toAjax(basPayPatternService.updateBasPayPattern(basPayPattern));
    }

    /**
     * 删除付款方式
     */
//    @PreAuthorize("@ss.hasPermi('bas:payPattern:remove')")
    @Log(title = "付款方式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basPayPatternService.deleteBasPayPatternByIds(ids));
    }
}

package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQcd;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * QCDController
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/qcd")
public class HomQcdController extends BaseController {
    @Autowired
    private IHomQcdService homQcdService;

    /**
     * 查询QCD列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:qcd:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomQcd homQcd,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomQcd> list = homQcdService.selectHomQcdList(homQcd);
        return getDataTable(list);
    }

    /**
     * 导出QCD列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:qcd:export')")
    @Log(title = "QCD", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomQcd homQcd) {
        List<HomQcd> list = homQcdService.selectHomQcdList(homQcd);
        ExcelUtil<HomQcd> util = new ExcelUtil<HomQcd>(HomQcd.class);
        util.exportExcel(response, list, "QCD数据");
    }

    /**
     * 获取QCD详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:qcd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homQcdService.selectHomQcdById(id));
    }

    /**
     * 新增QCD
     */
//    @PreAuthorize("@ss.hasPermi('idx:qcd:add')")
    @Log(title = "QCD", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomQcd homQcd) {
        return toAjax(homQcdService.insertHomQcd(homQcd));
    }

    /**
     * 修改QCD
     */
//    @PreAuthorize("@ss.hasPermi('idx:qcd:edit')")
    @Log(title = "QCD", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomQcd homQcd) {
        return toAjax(homQcdService.updateHomQcd(homQcd));
    }

    /**
     * 删除QCD
     */
//    @PreAuthorize("@ss.hasPermi('idx:qcd:remove')")
    @Log(title = "QCD", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homQcdService.deleteHomQcdByIds(ids));
    }
}

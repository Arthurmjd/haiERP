package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityDaytrend;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQualityDaytrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 质异日势Controller
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/hqdaytrend")
public class HomQualityDaytrendController extends BaseController {
    @Autowired
    private IHomQualityDaytrendService homQualityDaytrendService;

    /**
     * 查询质异日势列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:hqdaytrend:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomQualityDaytrend homQualityDaytrend,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomQualityDaytrend> list = homQualityDaytrendService.selectHomQualityDaytrendList(homQualityDaytrend);
        return getDataTable(list);
    }

    /**
     * 导出质异日势列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:hqdaytrend:export')")
    @Log(title = "质异日势", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomQualityDaytrend homQualityDaytrend) {
        List<HomQualityDaytrend> list = homQualityDaytrendService.selectHomQualityDaytrendList(homQualityDaytrend);
        ExcelUtil<HomQualityDaytrend> util = new ExcelUtil<HomQualityDaytrend>(HomQualityDaytrend.class);
        util.exportExcel(response, list, "质异日势数据");
    }

    /**
     * 获取质异日势详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:hqdaytrend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homQualityDaytrendService.selectHomQualityDaytrendById(id));
    }

    /**
     * 新增质异日势
     */
//    @PreAuthorize("@ss.hasPermi('idx:hqdaytrend:add')")
    @Log(title = "质异日势", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomQualityDaytrend homQualityDaytrend) {
        return toAjax(homQualityDaytrendService.insertHomQualityDaytrend(homQualityDaytrend));
    }

    /**
     * 修改质异日势
     */
//    @PreAuthorize("@ss.hasPermi('idx:hqdaytrend:edit')")
    @Log(title = "质异日势", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomQualityDaytrend homQualityDaytrend) {
        return toAjax(homQualityDaytrendService.updateHomQualityDaytrend(homQualityDaytrend));
    }

    /**
     * 删除质异日势
     */
//    @PreAuthorize("@ss.hasPermi('idx:hqdaytrend:remove')")
    @Log(title = "质异日势", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homQualityDaytrendService.deleteHomQualityDaytrendByIds(ids));
    }
}

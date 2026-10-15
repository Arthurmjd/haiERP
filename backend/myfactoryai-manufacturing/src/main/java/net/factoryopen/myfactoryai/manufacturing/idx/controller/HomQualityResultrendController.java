package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityResultrend;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQualityResultrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 质异结果Controller
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/resultrend")
public class HomQualityResultrendController extends BaseController {
    @Autowired
    private IHomQualityResultrendService homQualityResultrendService;

    /**
     * 查询质异结果列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:resultrend:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomQualityResultrend homQualityResultrend,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomQualityResultrend> list = homQualityResultrendService.selectHomQualityResultrendList(homQualityResultrend);
        return getDataTable(list);
    }

    /**
     * 导出质异结果列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:resultrend:export')")
    @Log(title = "质异结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomQualityResultrend homQualityResultrend) {
        List<HomQualityResultrend> list = homQualityResultrendService.selectHomQualityResultrendList(homQualityResultrend);
        ExcelUtil<HomQualityResultrend> util = new ExcelUtil<HomQualityResultrend>(HomQualityResultrend.class);
        util.exportExcel(response, list, "质异结果数据");
    }

    /**
     * 获取质异结果详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:resultrend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homQualityResultrendService.selectHomQualityResultrendById(id));
    }

    /**
     * 新增质异结果
     */
//    @PreAuthorize("@ss.hasPermi('idx:resultrend:add')")
    @Log(title = "质异结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomQualityResultrend homQualityResultrend) {
        return toAjax(homQualityResultrendService.insertHomQualityResultrend(homQualityResultrend));
    }

    /**
     * 修改质异结果
     */
//    @PreAuthorize("@ss.hasPermi('idx:resultrend:edit')")
    @Log(title = "质异结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomQualityResultrend homQualityResultrend) {
        return toAjax(homQualityResultrendService.updateHomQualityResultrend(homQualityResultrend));
    }

    /**
     * 删除质异结果
     */
//    @PreAuthorize("@ss.hasPermi('idx:resultrend:remove')")
    @Log(title = "质异结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homQualityResultrendService.deleteHomQualityResultrendByIds(ids));
    }
}

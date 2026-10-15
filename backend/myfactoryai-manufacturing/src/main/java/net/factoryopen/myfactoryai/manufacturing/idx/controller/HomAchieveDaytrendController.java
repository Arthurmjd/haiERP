package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveDaytrend;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomAchieveDaytrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 日现Controller
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/hadaytrend")
public class HomAchieveDaytrendController extends BaseController {
    @Autowired
    private IHomAchieveDaytrendService homAchieveDaytrendService;

    /**
     * 查询日现列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:hadaytrend:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomAchieveDaytrend homAchieveDaytrend,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomAchieveDaytrend> list = homAchieveDaytrendService.selectHomAchieveDaytrendList(homAchieveDaytrend);
        return getDataTable(list);
    }

    /**
     * 导出日现列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:hadaytrend:export')")
    @Log(title = "日现", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomAchieveDaytrend homAchieveDaytrend) {
        List<HomAchieveDaytrend> list = homAchieveDaytrendService.selectHomAchieveDaytrendList(homAchieveDaytrend);
        ExcelUtil<HomAchieveDaytrend> util = new ExcelUtil<HomAchieveDaytrend>(HomAchieveDaytrend.class);
        util.exportExcel(response, list, "日现数据");
    }

    /**
     * 获取日现详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:hadaytrend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homAchieveDaytrendService.selectHomAchieveDaytrendById(id));
    }

    /**
     * 新增日现
     */
//    @PreAuthorize("@ss.hasPermi('idx:hadaytrend:add')")
    @Log(title = "日现", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomAchieveDaytrend homAchieveDaytrend) {
        return toAjax(homAchieveDaytrendService.insertHomAchieveDaytrend(homAchieveDaytrend));
    }

    /**
     * 修改日现
     */
//    @PreAuthorize("@ss.hasPermi('idx:hadaytrend:edit')")
    @Log(title = "日现", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomAchieveDaytrend homAchieveDaytrend) {
        return toAjax(homAchieveDaytrendService.updateHomAchieveDaytrend(homAchieveDaytrend));
    }

    /**
     * 删除日现
     */
//    @PreAuthorize("@ss.hasPermi('idx:hadaytrend:remove')")
    @Log(title = "日现", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homAchieveDaytrendService.deleteHomAchieveDaytrendByIds(ids));
    }
}

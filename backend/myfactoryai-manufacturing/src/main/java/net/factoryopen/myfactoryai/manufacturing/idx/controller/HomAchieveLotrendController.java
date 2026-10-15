package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveLotrend;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomAchieveLotrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 批次现Controller
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/lotrend")
public class HomAchieveLotrendController extends BaseController {
    @Autowired
    private IHomAchieveLotrendService homAchieveLotrendService;

    /**
     * 查询批次现列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:lotrend:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomAchieveLotrend homAchieveLotrend,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomAchieveLotrend> list = homAchieveLotrendService.selectHomAchieveLotrendList(homAchieveLotrend);
        return getDataTable(list);
    }

    /**
     * 导出批次现列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:lotrend:export')")
    @Log(title = "批次现", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomAchieveLotrend homAchieveLotrend) {
        List<HomAchieveLotrend> list = homAchieveLotrendService.selectHomAchieveLotrendList(homAchieveLotrend);
        ExcelUtil<HomAchieveLotrend> util = new ExcelUtil<HomAchieveLotrend>(HomAchieveLotrend.class);
        util.exportExcel(response, list, "批次现数据");
    }

    /**
     * 获取批次现详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:lotrend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homAchieveLotrendService.selectHomAchieveLotrendById(id));
    }

    /**
     * 新增批次现
     */
//    @PreAuthorize("@ss.hasPermi('idx:lotrend:add')")
    @Log(title = "批次现", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomAchieveLotrend homAchieveLotrend) {
        return toAjax(homAchieveLotrendService.insertHomAchieveLotrend(homAchieveLotrend));
    }

    /**
     * 修改批次现
     */
//    @PreAuthorize("@ss.hasPermi('idx:lotrend:edit')")
    @Log(title = "批次现", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomAchieveLotrend homAchieveLotrend) {
        return toAjax(homAchieveLotrendService.updateHomAchieveLotrend(homAchieveLotrend));
    }

    /**
     * 删除批次现
     */
//    @PreAuthorize("@ss.hasPermi('idx:lotrend:remove')")
    @Log(title = "批次现", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homAchieveLotrendService.deleteHomAchieveLotrendByIds(ids));
    }
}

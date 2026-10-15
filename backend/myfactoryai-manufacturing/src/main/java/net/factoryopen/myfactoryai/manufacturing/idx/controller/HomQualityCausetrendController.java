package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityCausetrend;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQualityCausetrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 质异原因Controller
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/causetrend")
public class HomQualityCausetrendController extends BaseController {
    @Autowired
    private IHomQualityCausetrendService homQualityCausetrendService;

    /**
     * 查询质异原因列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:causetrend:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomQualityCausetrend homQualityCausetrend,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomQualityCausetrend> list = homQualityCausetrendService.selectHomQualityCausetrendList(homQualityCausetrend);
        return getDataTable(list);
    }

    /**
     * 导出质异原因列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:causetrend:export')")
    @Log(title = "质异原因", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomQualityCausetrend homQualityCausetrend) {
        List<HomQualityCausetrend> list = homQualityCausetrendService.selectHomQualityCausetrendList(homQualityCausetrend);
        ExcelUtil<HomQualityCausetrend> util = new ExcelUtil<HomQualityCausetrend>(HomQualityCausetrend.class);
        util.exportExcel(response, list, "质异原因数据");
    }

    /**
     * 获取质异原因详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:causetrend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homQualityCausetrendService.selectHomQualityCausetrendById(id));
    }

    /**
     * 新增质异原因
     */
//    @PreAuthorize("@ss.hasPermi('idx:causetrend:add')")
    @Log(title = "质异原因", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomQualityCausetrend homQualityCausetrend) {
        return toAjax(homQualityCausetrendService.insertHomQualityCausetrend(homQualityCausetrend));
    }

    /**
     * 修改质异原因
     */
//    @PreAuthorize("@ss.hasPermi('idx:causetrend:edit')")
    @Log(title = "质异原因", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomQualityCausetrend homQualityCausetrend) {
        return toAjax(homQualityCausetrendService.updateHomQualityCausetrend(homQualityCausetrend));
    }

    /**
     * 删除质异原因
     */
//    @PreAuthorize("@ss.hasPermi('idx:causetrend:remove')")
    @Log(title = "质异原因", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homQualityCausetrendService.deleteHomQualityCausetrendByIds(ids));
    }
}

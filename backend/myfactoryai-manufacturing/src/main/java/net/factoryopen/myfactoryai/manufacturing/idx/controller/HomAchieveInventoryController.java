package net.factoryopen.myfactoryai.manufacturing.idx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveInventory;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomAchieveInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 存货现Controller
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/idx/inventory")
public class HomAchieveInventoryController extends BaseController {
    @Autowired
    private IHomAchieveInventoryService homAchieveInventoryService;

    /**
     * 查询存货现列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomAchieveInventory homAchieveInventory,
                              @RequestParam(required = false, defaultValue = "0", name = "flag") int flag) {
        if (flag == 0) {
            startPage();
        }
        List<HomAchieveInventory> list = homAchieveInventoryService.selectHomAchieveInventoryList(homAchieveInventory);
        return getDataTable(list);
    }

    /**
     * 导出存货现列表
     */
//    @PreAuthorize("@ss.hasPermi('idx:inventory:export')")
    @Log(title = "存货现", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomAchieveInventory homAchieveInventory) {
        List<HomAchieveInventory> list = homAchieveInventoryService.selectHomAchieveInventoryList(homAchieveInventory);
        ExcelUtil<HomAchieveInventory> util = new ExcelUtil<HomAchieveInventory>(HomAchieveInventory.class);
        util.exportExcel(response, list, "存货现数据");
    }

    /**
     * 获取存货现详细信息
     */
//    @PreAuthorize("@ss.hasPermi('idx:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(homAchieveInventoryService.selectHomAchieveInventoryById(id));
    }

    /**
     * 新增存货现
     */
//    @PreAuthorize("@ss.hasPermi('idx:inventory:add')")
    @Log(title = "存货现", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomAchieveInventory homAchieveInventory) {
        return toAjax(homAchieveInventoryService.insertHomAchieveInventory(homAchieveInventory));
    }

    /**
     * 修改存货现
     */
//    @PreAuthorize("@ss.hasPermi('idx:inventory:edit')")
    @Log(title = "存货现", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomAchieveInventory homAchieveInventory) {
        return toAjax(homAchieveInventoryService.updateHomAchieveInventory(homAchieveInventory));
    }

    /**
     * 删除存货现
     */
//    @PreAuthorize("@ss.hasPermi('idx:inventory:remove')")
    @Log(title = "存货现", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(homAchieveInventoryService.deleteHomAchieveInventoryByIds(ids));
    }
}

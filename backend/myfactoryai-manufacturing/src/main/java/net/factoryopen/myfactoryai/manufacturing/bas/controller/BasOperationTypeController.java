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
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasOperationType;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasOperationTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作业类型Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/operationType")
public class BasOperationTypeController extends BaseController
{
    @Autowired
    private IBasOperationTypeService basOperationTypeService;

/**
 * 查询作业类型列表
 */
//@PreAuthorize("@ss.hasPermi('bas:operationType:list')")
@GetMapping("/list")
    public TableDataInfo list(BasOperationType basOperationType)
    {
        //startPage();
        List<BasOperationType> list = basOperationTypeService.selectBasOperationTypeList(basOperationType);
        return getDataTable(list);
    }

    /**
     * 导出作业类型列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:operationType:export')")
    @Log(title = "作业类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasOperationType basOperationType)
    {
        List<BasOperationType> list = basOperationTypeService.selectBasOperationTypeList(basOperationType);
        ExcelUtil<BasOperationType> util = new ExcelUtil<BasOperationType>(BasOperationType.class);
        util.exportExcel(response, list, "作业类型数据");
    }

    /**
     * 获取作业类型详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:operationType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basOperationTypeService.selectBasOperationTypeById(id));
    }

    /**
     * 新增作业类型
     */
//    @PreAuthorize("@ss.hasPermi('bas:operationType:add')")
    @Log(title = "作业类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasOperationType basOperationType)
    {
        return toAjax(basOperationTypeService.insertBasOperationType(basOperationType));
    }

    /**
     * 修改作业类型
     */
//    @PreAuthorize("@ss.hasPermi('bas:operationType:edit')")
    @Log(title = "作业类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasOperationType basOperationType)
    {
        return toAjax(basOperationTypeService.updateBasOperationType(basOperationType));
    }

    /**
     * 删除作业类型
     */
//    @PreAuthorize("@ss.hasPermi('bas:operationType:remove')")
    @Log(title = "作业类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basOperationTypeService.deleteBasOperationTypeByIds(ids));
    }
}

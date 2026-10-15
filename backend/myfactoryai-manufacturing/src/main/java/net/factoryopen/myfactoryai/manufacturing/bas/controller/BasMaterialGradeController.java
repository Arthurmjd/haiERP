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
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasMaterialGrade;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasMaterialGradeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料等级Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/materialGrade")
public class BasMaterialGradeController extends BaseController
{
    @Autowired
    private IBasMaterialGradeService basMaterialGradeService;

/**
 * 查询物料等级列表
 */
//@PreAuthorize("@ss.hasPermi('bas:materialGrade:list')")
@GetMapping("/list")
    public TableDataInfo list(BasMaterialGrade basMaterialGrade)
    {
        //startPage();
        List<BasMaterialGrade> list = basMaterialGradeService.selectBasMaterialGradeList(basMaterialGrade);
        return getDataTable(list);
    }

    /**
     * 导出物料等级列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:materialGrade:export')")
    @Log(title = "物料等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasMaterialGrade basMaterialGrade)
    {
        List<BasMaterialGrade> list = basMaterialGradeService.selectBasMaterialGradeList(basMaterialGrade);
        ExcelUtil<BasMaterialGrade> util = new ExcelUtil<BasMaterialGrade>(BasMaterialGrade.class);
        util.exportExcel(response, list, "物料等级数据");
    }

    /**
     * 获取物料等级详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:materialGrade:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basMaterialGradeService.selectBasMaterialGradeById(id));
    }

    /**
     * 新增物料等级
     */
//    @PreAuthorize("@ss.hasPermi('bas:materialGrade:add')")
    @Log(title = "物料等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasMaterialGrade basMaterialGrade)
    {
        return toAjax(basMaterialGradeService.insertBasMaterialGrade(basMaterialGrade));
    }

    /**
     * 修改物料等级
     */
//    @PreAuthorize("@ss.hasPermi('bas:materialGrade:edit')")
    @Log(title = "物料等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasMaterialGrade basMaterialGrade)
    {
        return toAjax(basMaterialGradeService.updateBasMaterialGrade(basMaterialGrade));
    }

    /**
     * 删除物料等级
     */
//    @PreAuthorize("@ss.hasPermi('bas:materialGrade:remove')")
    @Log(title = "物料等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basMaterialGradeService.deleteBasMaterialGradeByIds(ids));
    }
}

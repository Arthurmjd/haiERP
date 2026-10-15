package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProcessCategory;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProcessCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制程数据Controller
 *
 * @author generator
 * @date 2026-02-20
 */
@RestController
@RequestMapping("/eng/processCategory")
public class EngProcessCategoryController extends BaseController
{
    @Autowired
    private IEngProcessCategoryService engProcessCategoryService;

    /**
     * 查询制程数据列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:processCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProcessCategory engProcessCategory)
    {
        if(engProcessCategory!=null && engProcessCategory.getPageEnable()!=null && engProcessCategory.getPageEnable()) startPage();
        List<EngProcessCategory> list = engProcessCategoryService.selectEngProcessCategoryList(engProcessCategory);
        return getDataTable(list);
    }

    /**
     * 导出制程数据列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:processCategory:export')")
    @Log(title = "制程数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProcessCategory engProcessCategory)
    {
        List<EngProcessCategory> list = engProcessCategoryService.selectEngProcessCategoryList(engProcessCategory);
        ExcelUtil<EngProcessCategory> util = new ExcelUtil<EngProcessCategory>(EngProcessCategory.class);
        util.exportExcel(response, list, "制程数据数据");
    }

    /**
     * 获取制程数据详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:processCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProcessCategoryService.selectEngProcessCategoryById(id));
    }

    /**
     * 新增制程数据
     */
    //@PreAuthorize("@ss.hasPermi('eng:processCategory:add')")
    @Log(title = "制程数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProcessCategory engProcessCategory)
    {
        AjaxResult ar = null;
        try{
           int ret = engProcessCategoryService.insertEngProcessCategory(engProcessCategory);
           if (ret > 0){
               ar = AjaxResult.success("操作成功：[新增]1条数据！");
           }
           else{
               ar = AjaxResult.error(ret, "操作失败：[新增]0条数据！");
           }
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 修改制程数据
     */
    //@PreAuthorize("@ss.hasPermi('eng:processCategory:edit')")
    @Log(title = "制程数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProcessCategory engProcessCategory)
    {
        AjaxResult ar = null;
        try{
            int ret = engProcessCategoryService.updateEngProcessCategory(engProcessCategory);
            if (ret > 0){
                ar = AjaxResult.success("操作成功：[修改]"+ret+"条数据！");
            }
            else{
                ar = AjaxResult.error(ret, "操作失败：[修改]0条数据！");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 删除制程数据
     */
    //@PreAuthorize("@ss.hasPermi('eng:processCategory:remove')")
    @Log(title = "制程数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProcessCategoryService.deleteEngProcessCategoryByIds(ids);
            if (ret > 0){
                ar = AjaxResult.success("操作成功：[删除]"+ret+"条数据！");
            }
            else{
                ar = AjaxResult.error(ret, "操作失败：[删除]0条数据！");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }


}

package net.factoryopen.myfactoryai.manufacturing.qua.controller;

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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectCategory;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaFreeinspectCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 免检品类Controller
 *
 * @author admin
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/qua/freeinspectCategory")
public class QuaFreeinspectCategoryController extends BaseController
{
    @Autowired
    private IQuaFreeinspectCategoryService quaFreeinspectCategoryService;

    /**
     * 查询免检品类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaFreeinspectCategory quaFreeinspectCategory)
    {
        startPage();
        List<QuaFreeinspectCategory> list = quaFreeinspectCategoryService.selectQuaFreeinspectCategoryList(quaFreeinspectCategory);
        return getDataTable(list);
    }

    /**
     * 导出免检品类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectCategory:export')")
    @Log(title = "免检品类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaFreeinspectCategory quaFreeinspectCategory)
    {
        List<QuaFreeinspectCategory> list = quaFreeinspectCategoryService.selectQuaFreeinspectCategoryList(quaFreeinspectCategory);
        ExcelUtil<QuaFreeinspectCategory> util = new ExcelUtil<QuaFreeinspectCategory>(QuaFreeinspectCategory.class);
        util.exportExcel(response, list, "免检品类数据");
    }

    /**
     * 获取免检品类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaFreeinspectCategoryService.selectQuaFreeinspectCategoryById(id));
    }

    /**
     * 新增免检品类
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectCategory:add')")
    @Log(title = "免检品类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaFreeinspectCategory quaFreeinspectCategory)
    {
        AjaxResult ar = null;
        try{
           int ret = quaFreeinspectCategoryService.insertQuaFreeinspectCategory(quaFreeinspectCategory);
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
     * 修改免检品类
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectCategory:edit')")
    @Log(title = "免检品类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaFreeinspectCategory quaFreeinspectCategory)
    {
        AjaxResult ar = null;
        try{
            int ret = quaFreeinspectCategoryService.updateQuaFreeinspectCategory(quaFreeinspectCategory);
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
     * 删除免检品类
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectCategory:remove')")
    @Log(title = "免检品类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaFreeinspectCategoryService.deleteQuaFreeinspectCategoryByIds(ids);
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

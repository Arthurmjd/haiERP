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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategory;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料分类管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/eng/category")
public class EngCategoryController extends BaseController
{
    @Autowired
    private IEngCategoryService engCategoryService;

    /**
     * 查询物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngCategory engCategory)
    {
        if(engCategory!=null && engCategory.getPageEnable()!=null && engCategory.getPageEnable()) startPage();
        List<EngCategory> list = engCategoryService.selectEngCategoryList(engCategory);
        return getDataTable(list);
    }

    /**
     * 查询物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:listProduct')")
    @GetMapping("/listProduct")
    public TableDataInfo listProduct(EngCategory engCategory)
    {
        if(engCategory!=null && engCategory.getPageEnable()!=null && engCategory.getPageEnable()) startPage();
        List<EngCategory> list = engCategoryService.selectEngProductCategoryList(engCategory);
        return getDataTable(list);
    }

    /**
     * 查询物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:listPureProduct')")
    @GetMapping("/listPureProduct")
    public TableDataInfo listPureProduct(EngCategory engCategory)
    {
        if(engCategory!=null && engCategory.getPageEnable()!=null && engCategory.getPageEnable()) startPage();
        List<EngCategory> list = engCategoryService.selectEngPureProductCategoryList(engCategory);
        return getDataTable(list);
    }

    /**
     * 查询物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:listMaterial')")
    @GetMapping("/listMaterial")
    public TableDataInfo listMaterial(EngCategory engCategory)
    {
        if(engCategory!=null && engCategory.getPageEnable()!=null && engCategory.getPageEnable()) startPage();
        List<EngCategory> list = engCategoryService.selectEngMaterialCategoryList(engCategory);
        return getDataTable(list);
    }

    /**
     * 查询物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:listPureMaterialOrProduct')")
    @GetMapping("/listPureMaterialOrProduct")
    public TableDataInfo listPureMaterialOrProduct(EngCategory engCategory)
    {
        if(engCategory!=null && engCategory.getPageEnable()!=null && engCategory.getPageEnable()) startPage();
        List<EngCategory> list = engCategoryService.selectEngPureMaterialOrProductCategoryList(engCategory);
        return getDataTable(list);
    }

    /**
     * 查询物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:listUtility')")
    @GetMapping("/listUtility")
    public TableDataInfo listUtility(EngCategory engCategory)
    {
        if(engCategory!=null && engCategory.getPageEnable()!=null && engCategory.getPageEnable()) startPage();
        List<EngCategory> list = engCategoryService.selectEngUtilityCategoryList(engCategory);
        return getDataTable(list);
    }

    /**
     * 导出物料分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:export')")
    @Log(title = "物料分类管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngCategory engCategory)
    {
        List<EngCategory> list = engCategoryService.selectEngCategoryList(engCategory);
        ExcelUtil<EngCategory> util = new ExcelUtil<EngCategory>(EngCategory.class);
        util.exportExcel(response, list, "物料分类管理数据");
    }

    /**
     * 获取物料分类管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engCategoryService.selectEngCategoryById(id));
    }

    /**
     * 新增物料分类管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:add')")
    @Log(title = "物料分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngCategory engCategory)
    {
        AjaxResult ar = null;
        try{
           int ret = engCategoryService.insertEngCategory(engCategory);
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
     * 修改物料分类管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:edit')")
    @Log(title = "物料分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngCategory engCategory)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryService.updateEngCategory(engCategory);
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
     * 删除物料分类管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:category:remove')")
    @Log(title = "物料分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryService.deleteEngCategoryByIds(ids);
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

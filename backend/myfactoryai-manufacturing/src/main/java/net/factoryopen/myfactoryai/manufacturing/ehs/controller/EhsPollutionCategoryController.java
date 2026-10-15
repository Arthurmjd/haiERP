package net.factoryopen.myfactoryai.manufacturing.ehs.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 污染排放分类Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/pollutionCategory")
public class EhsPollutionCategoryController extends BaseController
{
    @Autowired
    private IEhsPollutionCategoryService ehsPollutionCategoryService;

    /**
     * 查询污染排放分类列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsPollutionCategory ehsPollutionCategory)
    {
        startPage();
        List<EhsPollutionCategory> list = ehsPollutionCategoryService.selectEhsPollutionCategoryList(ehsPollutionCategory);
        return getDataTable(list);
    }

    /**
     * 导出污染排放分类列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionCategory:export')")
    @Log(title = "污染排放分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsPollutionCategory ehsPollutionCategory)
    {
        List<EhsPollutionCategory> list = ehsPollutionCategoryService.selectEhsPollutionCategoryList(ehsPollutionCategory);
        ExcelUtil<EhsPollutionCategory> util = new ExcelUtil<EhsPollutionCategory>(EhsPollutionCategory.class);
        util.exportExcel(response, list, "污染排放分类数据");
    }

    /**
     * 获取污染排放分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsPollutionCategoryService.selectEhsPollutionCategoryById(id));
    }

    /**
     * 新增污染排放分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionCategory:add')")
    @Log(title = "污染排放分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsPollutionCategory ehsPollutionCategory)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsPollutionCategoryService.insertEhsPollutionCategory(ehsPollutionCategory);
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
     * 修改污染排放分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionCategory:edit')")
    @Log(title = "污染排放分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsPollutionCategory ehsPollutionCategory)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPollutionCategoryService.updateEhsPollutionCategory(ehsPollutionCategory);
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
     * 删除污染排放分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionCategory:remove')")
    @Log(title = "污染排放分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPollutionCategoryService.deleteEhsPollutionCategoryByIds(ids);
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

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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodieCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPowerFoodieCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 电耗设备分类Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/powerFoodieCategory")
public class EhsPowerFoodieCategoryController extends BaseController
{
    @Autowired
    private IEhsPowerFoodieCategoryService ehsPowerFoodieCategoryService;

    /**
     * 查询电耗设备分类列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodieCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        startPage();
        List<EhsPowerFoodieCategory> list = ehsPowerFoodieCategoryService.selectEhsPowerFoodieCategoryList(ehsPowerFoodieCategory);
        return getDataTable(list);
    }

    /**
     * 导出电耗设备分类列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodieCategory:export')")
    @Log(title = "电耗设备分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        List<EhsPowerFoodieCategory> list = ehsPowerFoodieCategoryService.selectEhsPowerFoodieCategoryList(ehsPowerFoodieCategory);
        ExcelUtil<EhsPowerFoodieCategory> util = new ExcelUtil<EhsPowerFoodieCategory>(EhsPowerFoodieCategory.class);
        util.exportExcel(response, list, "电耗设备分类数据");
    }

    /**
     * 获取电耗设备分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodieCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsPowerFoodieCategoryService.selectEhsPowerFoodieCategoryById(id));
    }

    /**
     * 新增电耗设备分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodieCategory:add')")
    @Log(title = "电耗设备分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsPowerFoodieCategoryService.insertEhsPowerFoodieCategory(ehsPowerFoodieCategory);
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
     * 修改电耗设备分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodieCategory:edit')")
    @Log(title = "电耗设备分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPowerFoodieCategoryService.updateEhsPowerFoodieCategory(ehsPowerFoodieCategory);
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
     * 删除电耗设备分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodieCategory:remove')")
    @Log(title = "电耗设备分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPowerFoodieCategoryService.deleteEhsPowerFoodieCategoryByIds(ids);
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

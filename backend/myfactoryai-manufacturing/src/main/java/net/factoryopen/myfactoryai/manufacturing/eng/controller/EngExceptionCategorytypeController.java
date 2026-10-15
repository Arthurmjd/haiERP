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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionCategorytypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺异常分类Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/eng/exceptionCategorytype")
public class EngExceptionCategorytypeController extends BaseController
{
    @Autowired
    private IEngExceptionCategorytypeService engExceptionCategorytypeService;

    /**
     * 查询工艺异常分类列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionCategorytype:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngExceptionCategorytype engExceptionCategorytype)
    {
        startPage();
        List<EngExceptionCategorytype> list = engExceptionCategorytypeService.selectEngExceptionCategorytypeList(engExceptionCategorytype);
        return getDataTable(list);
    }

    /**
     * 导出工艺异常分类列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionCategorytype:export')")
    @Log(title = "工艺异常分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngExceptionCategorytype engExceptionCategorytype)
    {
        List<EngExceptionCategorytype> list = engExceptionCategorytypeService.selectEngExceptionCategorytypeList(engExceptionCategorytype);
        ExcelUtil<EngExceptionCategorytype> util = new ExcelUtil<EngExceptionCategorytype>(EngExceptionCategorytype.class);
        util.exportExcel(response, list, "工艺异常分类数据");
    }

    /**
     * 获取工艺异常分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionCategorytype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engExceptionCategorytypeService.selectEngExceptionCategorytypeById(id));
    }

    /**
     * 新增工艺异常分类
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionCategorytype:add')")
    @Log(title = "工艺异常分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngExceptionCategorytype engExceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
           int ret = engExceptionCategorytypeService.insertEngExceptionCategorytype(engExceptionCategorytype);
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
     * 修改工艺异常分类
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionCategorytype:edit')")
    @Log(title = "工艺异常分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngExceptionCategorytype engExceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
            int ret = engExceptionCategorytypeService.updateEngExceptionCategorytype(engExceptionCategorytype);
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
     * 删除工艺异常分类
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionCategorytype:remove')")
    @Log(title = "工艺异常分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engExceptionCategorytypeService.deleteEngExceptionCategorytypeByIds(ids);
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

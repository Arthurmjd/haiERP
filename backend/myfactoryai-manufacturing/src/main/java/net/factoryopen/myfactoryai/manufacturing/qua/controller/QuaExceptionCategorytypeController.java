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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionCategorytypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常分类管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/qua/exceptionCategorytype")
public class QuaExceptionCategorytypeController extends BaseController
{
    @Autowired
    private IQuaExceptionCategorytypeService quaExceptionCategorytypeService;

    /**
     * 查询异常分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionCategorytype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaExceptionCategorytype quaExceptionCategorytype)
    {
        startPage();
        List<QuaExceptionCategorytype> list = quaExceptionCategorytypeService.selectQuaExceptionCategorytypeList(quaExceptionCategorytype);
        return getDataTable(list);
    }

    /**
     * 导出异常分类管理列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionCategorytype:export')")
    @Log(title = "异常分类管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaExceptionCategorytype quaExceptionCategorytype)
    {
        List<QuaExceptionCategorytype> list = quaExceptionCategorytypeService.selectQuaExceptionCategorytypeList(quaExceptionCategorytype);
        ExcelUtil<QuaExceptionCategorytype> util = new ExcelUtil<QuaExceptionCategorytype>(QuaExceptionCategorytype.class);
        util.exportExcel(response, list, "异常分类管理数据");
    }

    /**
     * 获取异常分类管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionCategorytype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaExceptionCategorytypeService.selectQuaExceptionCategorytypeById(id));
    }

    /**
     * 新增异常分类管理
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionCategorytype:add')")
    @Log(title = "异常分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaExceptionCategorytype quaExceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaExceptionCategorytypeService.insertQuaExceptionCategorytype(quaExceptionCategorytype);
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
     * 修改异常分类管理
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionCategorytype:edit')")
    @Log(title = "异常分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaExceptionCategorytype quaExceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionCategorytypeService.updateQuaExceptionCategorytype(quaExceptionCategorytype);
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
     * 删除异常分类管理
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionCategorytype:remove')")
    @Log(title = "异常分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionCategorytypeService.deleteQuaExceptionCategorytypeByIds(ids);
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

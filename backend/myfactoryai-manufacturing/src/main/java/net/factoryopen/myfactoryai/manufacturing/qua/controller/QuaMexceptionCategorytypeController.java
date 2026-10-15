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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaMexceptionCategorytypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制造异常分类Controller
 *
 * @author jitfactory-generator
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/qua/mexceptionCategorytype")
public class QuaMexceptionCategorytypeController extends BaseController
{
    @Autowired
    private IQuaMexceptionCategorytypeService quaMexceptionCategorytypeService;

    /**
     * 查询制造异常分类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionCategorytype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        startPage();
        List<QuaMexceptionCategorytype> list = quaMexceptionCategorytypeService.selectQuaMexceptionCategorytypeList(quaMexceptionCategorytype);
        return getDataTable(list);
    }

    /**
     * 导出制造异常分类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionCategorytype:export')")
    @Log(title = "制造异常分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        List<QuaMexceptionCategorytype> list = quaMexceptionCategorytypeService.selectQuaMexceptionCategorytypeList(quaMexceptionCategorytype);
        ExcelUtil<QuaMexceptionCategorytype> util = new ExcelUtil<QuaMexceptionCategorytype>(QuaMexceptionCategorytype.class);
        util.exportExcel(response, list, "制造异常分类数据");
    }

    /**
     * 获取制造异常分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionCategorytype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaMexceptionCategorytypeService.selectQuaMexceptionCategorytypeById(id));
    }

    /**
     * 新增制造异常分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionCategorytype:add')")
    @Log(title = "制造异常分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaMexceptionCategorytypeService.insertQuaMexceptionCategorytype(quaMexceptionCategorytype);
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
     * 修改制造异常分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionCategorytype:edit')")
    @Log(title = "制造异常分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaMexceptionCategorytypeService.updateQuaMexceptionCategorytype(quaMexceptionCategorytype);
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
     * 删除制造异常分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionCategorytype:remove')")
    @Log(title = "制造异常分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaMexceptionCategorytypeService.deleteQuaMexceptionCategorytypeByIds(ids);
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

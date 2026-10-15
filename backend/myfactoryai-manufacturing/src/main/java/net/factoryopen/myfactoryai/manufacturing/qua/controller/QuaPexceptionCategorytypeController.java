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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaPexceptionCategorytypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购异常分类Controller
 *
 * @author admin
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/qua/pexceptionCategorytype")
public class QuaPexceptionCategorytypeController extends BaseController
{
    @Autowired
    private IQuaPexceptionCategorytypeService quaPexceptionCategorytypeService;

    /**
     * 查询采购异常分类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionCategorytype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        startPage();
        List<QuaPexceptionCategorytype> list = quaPexceptionCategorytypeService.selectQuaPexceptionCategorytypeList(quaPexceptionCategorytype);
        return getDataTable(list);
    }

    /**
     * 导出采购异常分类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionCategorytype:export')")
    @Log(title = "采购异常分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        List<QuaPexceptionCategorytype> list = quaPexceptionCategorytypeService.selectQuaPexceptionCategorytypeList(quaPexceptionCategorytype);
        ExcelUtil<QuaPexceptionCategorytype> util = new ExcelUtil<QuaPexceptionCategorytype>(QuaPexceptionCategorytype.class);
        util.exportExcel(response, list, "采购异常分类数据");
    }

    /**
     * 获取采购异常分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionCategorytype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaPexceptionCategorytypeService.selectQuaPexceptionCategorytypeById(id));
    }

    /**
     * 新增采购异常分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionCategorytype:add')")
    @Log(title = "采购异常分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaPexceptionCategorytypeService.insertQuaPexceptionCategorytype(quaPexceptionCategorytype);
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
     * 修改采购异常分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionCategorytype:edit')")
    @Log(title = "采购异常分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaPexceptionCategorytypeService.updateQuaPexceptionCategorytype(quaPexceptionCategorytype);
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
     * 删除采购异常分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionCategorytype:remove')")
    @Log(title = "采购异常分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaPexceptionCategorytypeService.deleteQuaPexceptionCategorytypeByIds(ids);
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

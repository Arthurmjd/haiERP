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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionReasontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaMexceptionReasontypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常原因分类Controller
 *
 * @author jitfactory-generator
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/qua/mexceptionReasontype")
public class QuaMexceptionReasontypeController extends BaseController
{
    @Autowired
    private IQuaMexceptionReasontypeService quaMexceptionReasontypeService;

    /**
     * 查询异常原因分类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionReasontype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaMexceptionReasontype quaMexceptionReasontype)
    {
        startPage();
        List<QuaMexceptionReasontype> list = quaMexceptionReasontypeService.selectQuaMexceptionReasontypeList(quaMexceptionReasontype);
        return getDataTable(list);
    }

    /**
     * 导出异常原因分类列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionReasontype:export')")
    @Log(title = "异常原因分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaMexceptionReasontype quaMexceptionReasontype)
    {
        List<QuaMexceptionReasontype> list = quaMexceptionReasontypeService.selectQuaMexceptionReasontypeList(quaMexceptionReasontype);
        ExcelUtil<QuaMexceptionReasontype> util = new ExcelUtil<QuaMexceptionReasontype>(QuaMexceptionReasontype.class);
        util.exportExcel(response, list, "异常原因分类数据");
    }

    /**
     * 获取异常原因分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionReasontype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaMexceptionReasontypeService.selectQuaMexceptionReasontypeById(id));
    }

    /**
     * 新增异常原因分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionReasontype:add')")
    @Log(title = "异常原因分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaMexceptionReasontype quaMexceptionReasontype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaMexceptionReasontypeService.insertQuaMexceptionReasontype(quaMexceptionReasontype);
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
     * 修改异常原因分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionReasontype:edit')")
    @Log(title = "异常原因分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaMexceptionReasontype quaMexceptionReasontype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaMexceptionReasontypeService.updateQuaMexceptionReasontype(quaMexceptionReasontype);
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
     * 删除异常原因分类
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionReasontype:remove')")
    @Log(title = "异常原因分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaMexceptionReasontypeService.deleteQuaMexceptionReasontypeByIds(ids);
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

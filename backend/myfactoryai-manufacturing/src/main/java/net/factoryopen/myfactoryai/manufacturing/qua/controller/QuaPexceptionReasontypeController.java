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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionReasontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaPexceptionReasontypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购质量异常原因Controller
 *
 * @author admin
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/qua/pexceptionReasontype")
public class QuaPexceptionReasontypeController extends BaseController
{
    @Autowired
    private IQuaPexceptionReasontypeService quaPexceptionReasontypeService;

    /**
     * 查询采购质量异常原因列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionReasontype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaPexceptionReasontype quaPexceptionReasontype)
    {
        startPage();
        List<QuaPexceptionReasontype> list = quaPexceptionReasontypeService.selectQuaPexceptionReasontypeList(quaPexceptionReasontype);
        return getDataTable(list);
    }

    /**
     * 导出采购质量异常原因列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionReasontype:export')")
    @Log(title = "采购质量异常原因", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaPexceptionReasontype quaPexceptionReasontype)
    {
        List<QuaPexceptionReasontype> list = quaPexceptionReasontypeService.selectQuaPexceptionReasontypeList(quaPexceptionReasontype);
        ExcelUtil<QuaPexceptionReasontype> util = new ExcelUtil<QuaPexceptionReasontype>(QuaPexceptionReasontype.class);
        util.exportExcel(response, list, "采购质量异常原因数据");
    }

    /**
     * 获取采购质量异常原因详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionReasontype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaPexceptionReasontypeService.selectQuaPexceptionReasontypeById(id));
    }

    /**
     * 新增采购质量异常原因
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionReasontype:add')")
    @Log(title = "采购质量异常原因", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaPexceptionReasontype quaPexceptionReasontype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaPexceptionReasontypeService.insertQuaPexceptionReasontype(quaPexceptionReasontype);
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
     * 修改采购质量异常原因
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionReasontype:edit')")
    @Log(title = "采购质量异常原因", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaPexceptionReasontype quaPexceptionReasontype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaPexceptionReasontypeService.updateQuaPexceptionReasontype(quaPexceptionReasontype);
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
     * 删除采购质量异常原因
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionReasontype:remove')")
    @Log(title = "采购质量异常原因", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaPexceptionReasontypeService.deleteQuaPexceptionReasontypeByIds(ids);
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

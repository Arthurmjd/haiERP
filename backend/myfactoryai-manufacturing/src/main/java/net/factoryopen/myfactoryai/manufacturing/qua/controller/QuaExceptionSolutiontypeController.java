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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionSolutiontypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量异常处理类型Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/qua/exceptionSolutiontype")
public class QuaExceptionSolutiontypeController extends BaseController
{
    @Autowired
    private IQuaExceptionSolutiontypeService quaExceptionSolutiontypeService;

    /**
     * 查询质量异常处理类型列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionSolutiontype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        startPage();
        List<QuaExceptionSolutiontype> list = quaExceptionSolutiontypeService.selectQuaExceptionSolutiontypeList(quaExceptionSolutiontype);
        return getDataTable(list);
    }

    /**
     * 导出质量异常处理类型列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionSolutiontype:export')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        List<QuaExceptionSolutiontype> list = quaExceptionSolutiontypeService.selectQuaExceptionSolutiontypeList(quaExceptionSolutiontype);
        ExcelUtil<QuaExceptionSolutiontype> util = new ExcelUtil<QuaExceptionSolutiontype>(QuaExceptionSolutiontype.class);
        util.exportExcel(response, list, "质量异常处理类型数据");
    }

    /**
     * 获取质量异常处理类型详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionSolutiontype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaExceptionSolutiontypeService.selectQuaExceptionSolutiontypeById(id));
    }

    /**
     * 新增质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionSolutiontype:add')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaExceptionSolutiontypeService.insertQuaExceptionSolutiontype(quaExceptionSolutiontype);
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
     * 修改质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionSolutiontype:edit')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionSolutiontypeService.updateQuaExceptionSolutiontype(quaExceptionSolutiontype);
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
     * 删除质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionSolutiontype:remove')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionSolutiontypeService.deleteQuaExceptionSolutiontypeByIds(ids);
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

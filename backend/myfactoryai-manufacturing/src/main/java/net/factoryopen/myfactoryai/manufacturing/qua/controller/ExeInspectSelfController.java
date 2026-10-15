package net.factoryopen.myfactoryai.manufacturing.qua.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.type.InspectionType;
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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectSelf;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IExeInspectSelfService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自检异常登记Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/exe/inspectSelf")
public class ExeInspectSelfController extends BaseController
{
    @Autowired
    private IExeInspectSelfService exeInspectSelfService;

    /**
     * 查询自检异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeInspectSelf exeInspectSelf)
    {
        startPage();
        exeInspectSelf.setInspectType(InspectionType.CHECK_SELF);
        List<ExeInspectSelf> list = exeInspectSelfService.selectExeInspectSelfList(exeInspectSelf);
        return getDataTable(list);
    }

    /**
     * 查询自检异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:listByOperator')")
    @GetMapping("/listByOperator")
    public TableDataInfo listByOperator(ExeInspectSelf exeInspectSelf)
    {
        startPage();
        exeInspectSelf.setInspectType(InspectionType.CHECK_SELF);
        List<ExeInspectSelf> list = exeInspectSelfService.selectExeInspectSelfListByOperator(exeInspectSelf);
        return getDataTable(list);
    }

    /**
     * 导出自检异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:export')")
    @Log(title = "自检异常登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeInspectSelf exeInspectSelf)
    {
        List<ExeInspectSelf> list = exeInspectSelfService.selectExeInspectSelfList(exeInspectSelf);
        ExcelUtil<ExeInspectSelf> util = new ExcelUtil<ExeInspectSelf>(ExeInspectSelf.class);
        util.exportExcel(response, list, "自检异常登记数据");
    }

    /**
     * 获取自检异常登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeInspectSelfService.selectExeInspectSelfById(id));
    }

    /**
     * 新增自检异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:add')")
    @Log(title = "自检异常登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeInspectSelf exeInspectSelf)
    {
        AjaxResult ar = null;
        try{
            exeInspectSelf.setInspectType(InspectionType.CHECK_SELF);
            exeInspectSelf.refreshCreatingStamp();
           int ret = exeInspectSelfService.insertExeInspectSelf(exeInspectSelf);
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
     * 修改自检异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:edit')")
    @Log(title = "自检异常登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeInspectSelf exeInspectSelf)
    {
        AjaxResult ar = null;
        try{
            int ret = exeInspectSelfService.updateExeInspectSelf(exeInspectSelf);
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
     * 删除自检异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:remove')")
    @Log(title = "自检异常登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeInspectSelfService.deleteExeInspectSelfByIds(ids);
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

    /**
     * 请判
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectSelf:requestDeal')")
    @Log(title = "请判", businessType = BusinessType.OTHER)
    @PutMapping("/requestDeal")
    public AjaxResult requestDeal(@RequestBody ExeInspectSelf exeInspectSelf)
    {
        AjaxResult ar = null;
        try{
            exeInspectSelfService.requestDeal(exeInspectSelf);
            ar = AjaxResult.success("操作成功：[请判]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

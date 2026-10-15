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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectInprocess;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IExeInspectInprocessService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制检异常登记Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/exe/inspectInprocess")
public class ExeInspectInprocessController extends BaseController
{
    @Autowired
    private IExeInspectInprocessService exeInspectInprocessService;

    /**
     * 查询制检异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeInspectInprocess exeInspectInprocess)
    {
        startPage();
        List<ExeInspectInprocess> list = exeInspectInprocessService.selectExeInspectInprocessList(exeInspectInprocess);
        return getDataTable(list);
    }

    /**
     * 导出制检异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:export')")
    @Log(title = "制检异常登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeInspectInprocess exeInspectInprocess)
    {
        List<ExeInspectInprocess> list = exeInspectInprocessService.selectExeInspectInprocessList(exeInspectInprocess);
        ExcelUtil<ExeInspectInprocess> util = new ExcelUtil<ExeInspectInprocess>(ExeInspectInprocess.class);
        util.exportExcel(response, list, "制检异常登记数据");
    }

    /**
     * 获取制检异常登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeInspectInprocessService.selectExeInspectInprocessById(id));
    }

    /**
     * 新增制检异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:add')")
    @Log(title = "制检异常登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeInspectInprocess exeInspectInprocess)
    {
        AjaxResult ar = null;

        try{
            exeInspectInprocess.setInspectType(InspectionType.CHECK_IN_PROCESS);
            exeInspectInprocess.refreshCreatingStamp();
           int ret = exeInspectInprocessService.insertExeInspectInprocess(exeInspectInprocess);
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
     * 修改制检异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:edit')")
    @Log(title = "制检异常登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeInspectInprocess exeInspectInprocess)
    {
        AjaxResult ar = null;
        try{
            int ret = exeInspectInprocessService.updateExeInspectInprocess(exeInspectInprocess);
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
     * 删除制检异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:remove')")
    @Log(title = "制检异常登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeInspectInprocessService.deleteExeInspectInprocessByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('exe:inspectInprocess:requestDeal')")
    @Log(title = "请判", businessType = BusinessType.OTHER)
    @PutMapping("/requestDeal")
    public AjaxResult requestDeal(@RequestBody ExeInspectInprocess exeInspectInprocess)
    {
        AjaxResult ar = null;
        try{
            exeInspectInprocessService.requestDeal(exeInspectInprocess);
            ar = AjaxResult.success("操作成功：[请判]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

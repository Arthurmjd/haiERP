package net.factoryopen.myfactoryai.manufacturing.exe.controller;

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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlRoutingChange;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlRoutingChangeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次制程变更Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/exe/mlRoutingChange")
public class ExeMlRoutingChangeController extends BaseController
{
    @Autowired
    private IExeMlRoutingChangeService exeMlRoutingChangeService;

    /**
     * 查询批次制程变更列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlRoutingChange:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlRoutingChange exeMlRoutingChange)
    {
        startPage();
        List<ExeMlRoutingChange> list = exeMlRoutingChangeService.selectExeMlRoutingChangeList(exeMlRoutingChange);
        return getDataTable(list);
    }

    /**
     * 导出批次制程变更列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlRoutingChange:export')")
    @Log(title = "批次制程变更", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlRoutingChange exeMlRoutingChange)
    {
        List<ExeMlRoutingChange> list = exeMlRoutingChangeService.selectExeMlRoutingChangeList(exeMlRoutingChange);
        ExcelUtil<ExeMlRoutingChange> util = new ExcelUtil<ExeMlRoutingChange>(ExeMlRoutingChange.class);
        util.exportExcel(response, list, "批次制程变更数据");
    }

    /**
     * 获取批次制程变更详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlRoutingChange:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlRoutingChangeService.selectExeMlRoutingChangeById(id));
    }

    /**
     * 新增批次制程变更
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlRoutingChange:add')")
    @Log(title = "批次制程变更", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlRoutingChange exeMlRoutingChange)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlRoutingChangeService.insertExeMlRoutingChange(exeMlRoutingChange);
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
     * 修改批次制程变更
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlRoutingChange:edit')")
    @Log(title = "批次制程变更", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlRoutingChange exeMlRoutingChange)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlRoutingChangeService.updateExeMlRoutingChange(exeMlRoutingChange);
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
     * 删除批次制程变更
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlRoutingChange:remove')")
    @Log(title = "批次制程变更", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlRoutingChangeService.deleteExeMlRoutingChangeByIds(ids);
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

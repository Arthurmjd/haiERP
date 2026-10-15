package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanOs;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPlanOsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外协计划Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/planOs")
public class DelPlanOsController extends BaseController
{
    @Autowired
    private IDelPlanOsService delPlanOsService;

    /**
     * 查询外协计划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:planOs:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelPlanOs delPlanOs)
    {
        startPage();
        List<DelPlanOs> list = delPlanOsService.selectDelPlanOsList(delPlanOs);
        return getDataTable(list);
    }

    /**
     * 导出外协计划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:planOs:export')")
    @Log(title = "外协计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelPlanOs delPlanOs)
    {
        List<DelPlanOs> list = delPlanOsService.selectDelPlanOsList(delPlanOs);
        ExcelUtil<DelPlanOs> util = new ExcelUtil<DelPlanOs>(DelPlanOs.class);
        util.exportExcel(response, list, "外协计划数据");
    }

    /**
     * 获取外协计划详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:planOs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delPlanOsService.selectDelPlanOsById(id));
    }

    /**
     * 新增外协计划
     */
    //@PreAuthorize("@ss.hasPermi('del:planOs:add')")
    @Log(title = "外协计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelPlanOs delPlanOs)
    {
        AjaxResult ar = null;
        try{
           int ret = delPlanOsService.insertDelPlanOs(delPlanOs);
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
     * 修改外协计划
     */
    //@PreAuthorize("@ss.hasPermi('del:planOs:edit')")
    @Log(title = "外协计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelPlanOs delPlanOs)
    {
        AjaxResult ar = null;
        try{
            if (delPlanOs.getStatus()!= BillStatus.BASE_DRAFT
                    && delPlanOs.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delPlanOsService.updateDelPlanOs(delPlanOs);
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
     * 删除外协计划
     */
    //@PreAuthorize("@ss.hasPermi('del:planOs:remove')")
    @Log(title = "外协计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delPlanOsService.deleteDelPlanOsByIds(ids);
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

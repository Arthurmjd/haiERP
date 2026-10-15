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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanMps;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPlanMpsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主计划Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/plnMps")
public class DelPlanMpsController extends BaseController
{
    @Autowired
    private IDelPlanMpsService delPlanMpsService;

    /**
     * 查询主计划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:plnMps:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelPlanMps delPlanMps)
    {
        startPage();
        List<DelPlanMps> list = delPlanMpsService.selectDelPlanMpsList(delPlanMps);
        return getDataTable(list);
    }

    /**
     * 导出主计划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:plnMps:export')")
    @Log(title = "主计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelPlanMps delPlanMps)
    {
        List<DelPlanMps> list = delPlanMpsService.selectDelPlanMpsList(delPlanMps);
        ExcelUtil<DelPlanMps> util = new ExcelUtil<DelPlanMps>(DelPlanMps.class);
        util.exportExcel(response, list, "主计划数据");
    }

    /**
     * 获取主计划详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:plnMps:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delPlanMpsService.selectDelPlanMpsById(id));
    }

    /**
     * 新增主计划
     */
    //@PreAuthorize("@ss.hasPermi('del:plnMps:add')")
    @Log(title = "主计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelPlanMps delPlanMps)
    {
        AjaxResult ar = null;
        try{
           int ret = delPlanMpsService.insertDelPlanMps(delPlanMps);
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
     * 修改主计划
     */
    //@PreAuthorize("@ss.hasPermi('del:plnMps:edit')")
    @Log(title = "主计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelPlanMps delPlanMps)
    {
        AjaxResult ar = null;
        try{
            if (delPlanMps.getStatus()!= BillStatus.BASE_DRAFT
                    && delPlanMps.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delPlanMpsService.updateDelPlanMps(delPlanMps);
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
     * 删除主计划
     */
    //@PreAuthorize("@ss.hasPermi('del:plnMps:remove')")
    @Log(title = "主计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delPlanMpsService.deleteDelPlanMpsByIds(ids);
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

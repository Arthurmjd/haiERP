package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivity;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPerspectivityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单透视表Controller
 *
 * @author admin
 * @date 2024-07-23
 */
@RestController
@RequestMapping("/del/perspectivity")
public class DelPerspectivityController extends BaseController
{
    @Autowired
    private IDelPerspectivityService delPerspectivityService;

    /**
     * 查询订单透视表列表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelPerspectivity delPerspectivity)
    {
        delPerspectivity.setUserId(SecurityUtils.getUserId());

        startPage();
        List<DelPerspectivity> list = delPerspectivityService.selectDelPerspectivityList(delPerspectivity);
        return getDataTable(list);
    }

    /**
     * 导出订单透视表列表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:export')")
    @Log(title = "订单透视表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelPerspectivity delPerspectivity)
    {
        List<DelPerspectivity> list = delPerspectivityService.selectDelPerspectivityList(delPerspectivity);
        ExcelUtil<DelPerspectivity> util = new ExcelUtil<DelPerspectivity>(DelPerspectivity.class);
        util.exportExcel(response, list, "订单透视表数据");
    }

    /**
     * 获取订单透视表详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delPerspectivityService.selectDelPerspectivityById(id));
    }

    /**
     * 新增订单透视表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:add')")
    @Log(title = "订单透视表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelPerspectivity delPerspectivity)
    {
        AjaxResult ar = null;
        try{
           int ret = delPerspectivityService.insertDelPerspectivity(delPerspectivity);
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
     * 修改订单透视表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:edit')")
    @Log(title = "订单透视表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelPerspectivity delPerspectivity)
    {
        AjaxResult ar = null;
        try{
            int ret = delPerspectivityService.updateDelPerspectivity(delPerspectivity);
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
     * 删除订单透视表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:remove')")
    @Log(title = "订单透视表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delPerspectivityService.deleteDelPerspectivityByIds(ids);
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
     * 透算
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivity:calculatePerspectivity')")
    @Log(title = "透算", businessType = BusinessType.OTHER)
    @PutMapping("/calculatePerspectivity")
    public AjaxResult calculatePerspectivity()
    {
        AjaxResult ar = null;
        try{
            delPerspectivityService.calculatePerspectivity(null);
            ar = AjaxResult.success("操作成功：[透算]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

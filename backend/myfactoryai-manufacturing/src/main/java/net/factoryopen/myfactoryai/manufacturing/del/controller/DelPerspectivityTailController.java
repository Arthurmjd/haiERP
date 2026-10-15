package net.factoryopen.myfactoryai.manufacturing.del.controller;

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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivityTail;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPerspectivityTailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单透视尾情Controller
 *
 * @author admin
 * @date 2024-07-23
 */
@RestController
@RequestMapping("/del/perspectivityTail")
public class DelPerspectivityTailController extends BaseController
{
    @Autowired
    private IDelPerspectivityTailService delPerspectivityTailService;

    /**
     * 查询订单透视尾情列表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivityTail:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelPerspectivityTail delPerspectivityTail)
    {
        startPage();
        List<DelPerspectivityTail> list = delPerspectivityTailService.selectDelPerspectivityTailList(delPerspectivityTail);
        return getDataTable(list);
    }

    /**
     * 导出订单透视尾情列表
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivityTail:export')")
    @Log(title = "订单透视尾情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelPerspectivityTail delPerspectivityTail)
    {
        List<DelPerspectivityTail> list = delPerspectivityTailService.selectDelPerspectivityTailList(delPerspectivityTail);
        ExcelUtil<DelPerspectivityTail> util = new ExcelUtil<DelPerspectivityTail>(DelPerspectivityTail.class);
        util.exportExcel(response, list, "订单透视尾情数据");
    }

    /**
     * 获取订单透视尾情详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivityTail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delPerspectivityTailService.selectDelPerspectivityTailById(id));
    }

    /**
     * 新增订单透视尾情
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivityTail:add')")
    @Log(title = "订单透视尾情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelPerspectivityTail delPerspectivityTail)
    {
        AjaxResult ar = null;
        try{
           int ret = delPerspectivityTailService.insertDelPerspectivityTail(delPerspectivityTail);
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
     * 修改订单透视尾情
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivityTail:edit')")
    @Log(title = "订单透视尾情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelPerspectivityTail delPerspectivityTail)
    {
        AjaxResult ar = null;
        try{
            int ret = delPerspectivityTailService.updateDelPerspectivityTail(delPerspectivityTail);
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
     * 删除订单透视尾情
     */
    //@PreAuthorize("@ss.hasPermi('del:perspectivityTail:remove')")
    @Log(title = "订单透视尾情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delPerspectivityTailService.deleteDelPerspectivityTailByIds(ids);
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

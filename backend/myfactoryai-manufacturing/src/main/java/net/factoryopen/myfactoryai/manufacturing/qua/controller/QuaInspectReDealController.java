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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectReDealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质检请求处理Controller
 *
 * @author admin
 * @date 2024-10-06
 */
@RestController
@RequestMapping("/qua/inspectReDeal")
public class QuaInspectReDealController extends BaseController
{
    @Autowired
    private IQuaInspectReDealService quaInspectReDealService;

    /**
     * 查询质检请求处理列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaInspectReDeal quaInspectReDeal)
    {
        startPage();
        List<QuaInspectReDeal> list = quaInspectReDealService.selectQuaInspectReDealList(quaInspectReDeal);
        return getDataTable(list);
    }

    /**
     * 导出质检请求处理列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:export')")
    @Log(title = "质检请求处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaInspectReDeal quaInspectReDeal)
    {
        List<QuaInspectReDeal> list = quaInspectReDealService.selectQuaInspectReDealList(quaInspectReDeal);
        ExcelUtil<QuaInspectReDeal> util = new ExcelUtil<QuaInspectReDeal>(QuaInspectReDeal.class);
        util.exportExcel(response, list, "质检请求处理数据");
    }

    /**
     * 获取质检请求处理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaInspectReDealService.selectQuaInspectReDealById(id));
    }

    /**
     * 新增质检请求处理
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:add')")
    @Log(title = "质检请求处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaInspectReDeal quaInspectReDeal)
    {
        AjaxResult ar = null;
        try{
           int ret = quaInspectReDealService.insertQuaInspectReDeal(quaInspectReDeal);
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
     * 修改质检请求处理
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:edit')")
    @Log(title = "质检请求处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaInspectReDeal quaInspectReDeal)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectReDealService.updateQuaInspectReDeal(quaInspectReDeal);
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
     * 删除质检请求处理
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:remove')")
    @Log(title = "质检请求处理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectReDealService.deleteQuaInspectReDealByIds(ids);
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
     * 收检
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReDeal:acceptInspection')")
    @Log(title = "收检", businessType = BusinessType.OTHER)
    @PutMapping("/acceptInspection")
    public AjaxResult acceptInspection(@RequestBody QuaInspectReDeal quaInspectReDeal)
    {
        AjaxResult ar = null;
        try{
            quaInspectReDealService.acceptInspection(quaInspectReDeal);
            ar = AjaxResult.success("操作成功：[收检]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

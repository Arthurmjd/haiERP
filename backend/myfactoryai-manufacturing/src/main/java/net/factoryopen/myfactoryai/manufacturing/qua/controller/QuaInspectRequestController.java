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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectRequestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质检请求通知Controller
 *
 * @author admin
 * @date 2024-10-06
 */
@RestController
@RequestMapping("/qua/inspectRequest")
public class QuaInspectRequestController extends BaseController
{
    @Autowired
    private IQuaInspectRequestService quaInspectRequestService;

    /**
     * 查询质检请求通知列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequest:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaInspectRequest quaInspectRequest)
    {
        startPage();
        List<QuaInspectRequest> list = quaInspectRequestService.selectQuaInspectRequestList(quaInspectRequest);
        return getDataTable(list);
    }

    /**
     * 导出质检请求通知列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequest:export')")
    @Log(title = "质检请求通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaInspectRequest quaInspectRequest)
    {
        List<QuaInspectRequest> list = quaInspectRequestService.selectQuaInspectRequestList(quaInspectRequest);
        ExcelUtil<QuaInspectRequest> util = new ExcelUtil<QuaInspectRequest>(QuaInspectRequest.class);
        util.exportExcel(response, list, "质检请求通知数据");
    }

    /**
     * 获取质检请求通知详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaInspectRequestService.selectQuaInspectRequestById(id));
    }

    /**
     * 新增质检请求通知
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequest:add')")
    @Log(title = "质检请求通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaInspectRequest quaInspectRequest)
    {
        AjaxResult ar = null;
        try{
           int ret = quaInspectRequestService.insertQuaInspectRequest(quaInspectRequest);
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
     * 修改质检请求通知
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequest:edit')")
    @Log(title = "质检请求通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaInspectRequest quaInspectRequest)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectRequestService.updateQuaInspectRequest(quaInspectRequest);
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
     * 删除质检请求通知
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequest:remove')")
    @Log(title = "质检请求通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectRequestService.deleteQuaInspectRequestByIds(ids);
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

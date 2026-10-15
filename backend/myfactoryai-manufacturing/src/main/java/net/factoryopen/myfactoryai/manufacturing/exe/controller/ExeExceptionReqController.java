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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeExceptionReq;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeExceptionReqService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺异常登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/exe/exceptionReq")
public class ExeExceptionReqController extends BaseController
{
    @Autowired
    private IExeExceptionReqService exeExceptionReqService;

    /**
     * 查询工艺异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeExceptionReq exeExceptionReq)
    {
        startPage();
        List<ExeExceptionReq> list = exeExceptionReqService.selectExeExceptionReqList(exeExceptionReq);
        return getDataTable(list);
    }

    /**
     * 导出工艺异常登记列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:export')")
    @Log(title = "工艺异常登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeExceptionReq exeExceptionReq)
    {
        List<ExeExceptionReq> list = exeExceptionReqService.selectExeExceptionReqList(exeExceptionReq);
        ExcelUtil<ExeExceptionReq> util = new ExcelUtil<ExeExceptionReq>(ExeExceptionReq.class);
        util.exportExcel(response, list, "工艺异常登记数据");
    }

    /**
     * 获取工艺异常登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeExceptionReqService.selectExeExceptionReqById(id));
    }

    /**
     * 新增工艺异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:add')")
    @Log(title = "工艺异常登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeExceptionReq exeExceptionReq)
    {
        AjaxResult ar = null;
        try{
           int ret = exeExceptionReqService.insertExeExceptionReq(exeExceptionReq);
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
     * 修改工艺异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:edit')")
    @Log(title = "工艺异常登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeExceptionReq exeExceptionReq)
    {
        AjaxResult ar = null;
        try{
            int ret = exeExceptionReqService.updateExeExceptionReq(exeExceptionReq);
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
     * 删除工艺异常登记
     */
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:remove')")
    @Log(title = "工艺异常登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeExceptionReqService.deleteExeExceptionReqByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('exe:exceptionReq:requestDeal')")
    @Log(title = "请判", businessType = BusinessType.OTHER)
    @PutMapping("/requestDeal")
    public AjaxResult requestDeal(@RequestBody ExeExceptionReq exeExceptionReq)
    {
        AjaxResult ar = null;
        try{
            exeExceptionReqService.requestDeal(exeExceptionReq);
            ar = AjaxResult.success("操作成功：[请判]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

package net.factoryopen.myfactoryai.manufacturing.exe.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlOperating;
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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessOperating;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessOperatingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产线班组作业Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/exe/processOperating")
public class ExeProcessOperatingController extends BaseController
{
    @Autowired
    private IExeProcessOperatingService exeProcessOperatingService;

    /**
     * 查询产线班组作业列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeProcessOperating exeProcessOperating)
    {
        startPage();
        List<ExeProcessOperating> list = exeProcessOperatingService.selectExeProcessOperatingList(exeProcessOperating);
        return getDataTable(list);
    }

    /**
     * 导出产线班组作业列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:export')")
    @Log(title = "产线班组作业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeProcessOperating exeProcessOperating)
    {
        List<ExeProcessOperating> list = exeProcessOperatingService.selectExeProcessOperatingList(exeProcessOperating);
        ExcelUtil<ExeProcessOperating> util = new ExcelUtil<ExeProcessOperating>(ExeProcessOperating.class);
        util.exportExcel(response, list, "产线班组作业数据");
    }

    /**
     * 获取产线班组作业详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeProcessOperatingService.selectExeProcessOperatingById(id));
    }

    /**
     * 新增产线班组作业
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:add')")
    @Log(title = "产线班组作业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeProcessOperating exeProcessOperating)
    {
        AjaxResult ar = null;
        try{
           int ret = exeProcessOperatingService.insertExeProcessOperating(exeProcessOperating);
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
     * 修改产线班组作业
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:edit')")
    @Log(title = "产线班组作业", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeProcessOperating exeProcessOperating)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessOperatingService.updateExeProcessOperating(exeProcessOperating);
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
     * 删除产线班组作业
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:remove')")
    @Log(title = "产线班组作业", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessOperatingService.deleteExeProcessOperatingByIds(ids);
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
     *  ids 接收 主键
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:acceptMl')")
    @Log(title = "接收", businessType = BusinessType.OTHER)
    @PutMapping("/acceptMl/{ids}")
    public AjaxResult acceptMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeProcessOperatingService.acceptMl(ids);
            ar = AjaxResult.success("操作成功：[接收]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 请料 主键
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:requestMaterial')")
    @Log(title = "请料", businessType = BusinessType.OTHER)
    @PutMapping("/requestMaterial/{ids}")
    public AjaxResult requestMaterial(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeProcessOperatingService.requestMaterial(ids);
            ar = AjaxResult.success("操作成功：[请料]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 转出 主键
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:postMl')")
    @Log(title = "转出", businessType = BusinessType.OTHER)
    @PutMapping("/postMl/{ids}")
    public AjaxResult postMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeProcessOperatingService.postMl(ids);
            ar = AjaxResult.success("操作成功：[转出]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 开工
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:startWork')")
    @Log(title = "开工", businessType = BusinessType.OTHER)
    @PutMapping("/startWork")
    public AjaxResult startWork(@RequestBody ExeProcessOperating exeProcessOperating)
    {
        AjaxResult ar = null;
        try{
            exeProcessOperatingService.startWork(exeProcessOperating);
            ar = AjaxResult.success("操作成功：[开工]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 完工
     */
    //@PreAuthorize("@ss.hasPermi('exe:processOperating:finishWork')")
    @Log(title = "完工", businessType = BusinessType.OTHER)
    @PutMapping("/finishWork")
    public AjaxResult finishWork(@RequestBody ExeProcessOperating exeProcessOperating)
    {
        AjaxResult ar = null;
        try{
            exeProcessOperatingService.finishWork(exeProcessOperating);
            ar = AjaxResult.success("操作成功：[完工]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

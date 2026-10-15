package net.factoryopen.myfactoryai.manufacturing.exe.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessWork;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessWorkService;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachine;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupMachine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResMachineService;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupMachineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlOperating;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlOperatingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车间班组作业Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
@RestController
@RequestMapping("/exe/mlOperating")
public class ExeMlOperatingController extends BaseController
{
    @Autowired
    private IExeMlOperatingService exeMlOperatingService;

    @Autowired
    private IExeProcessWorkService exeProcessWorkService;

    @Autowired
    private IResWorkgroupMachineService resGroupMachineService;

    /**
     * 查询车间班组作业列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlOperating exeMlOperating)
    {
        startPage();
        List<ExeMlOperating> list = exeMlOperatingService.selectExeMlOperatingList(exeMlOperating);

        return getDataTable(list);
    }

    /**
     * 查询车间班组作业列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:listByOperator')")
    @GetMapping("/listByOperator")
    public TableDataInfo listByOperator(ExeMlOperating exeMlOperating)
    {
        startPage();
        List<ExeMlOperating> list = exeMlOperatingService.selectExeMlOperatingListByOperator(exeMlOperating);

        return getDataTable(list);
    }

    /**
     * 导出车间班组作业列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:export')")
    @Log(title = "车间班组作业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlOperating exeMlOperating)
    {
        List<ExeMlOperating> list = exeMlOperatingService.selectExeMlOperatingList(exeMlOperating);
        ExcelUtil<ExeMlOperating> util = new ExcelUtil<ExeMlOperating>(ExeMlOperating.class);
        util.exportExcel(response, list, "车间班组作业数据");
    }

    /**
     * 获取车间班组作业详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlOperatingService.selectExeMlOperatingById(id));
    }

    /**
     * 新增车间班组作业
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:add')")
    @Log(title = "车间班组作业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlOperating exeMlOperating)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlOperatingService.insertExeMlOperating(exeMlOperating);
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
     * 修改车间班组作业
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:edit')")
    @Log(title = "车间班组作业", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlOperating exeMlOperating)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlOperatingService.updateExeMlOperating(exeMlOperating);
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
     * 删除车间班组作业
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:remove')")
    @Log(title = "车间班组作业", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlOperatingService.deleteExeMlOperatingByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:acceptMl')")
    @Log(title = "接收", businessType = BusinessType.OTHER)
    @PutMapping("/acceptMl/{ids}")
    public AjaxResult acceptMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeMlOperatingService.acceptMl(ids);
            ar = AjaxResult.success("操作成功：[接收]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     *  ids 批次Ids
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:requestMaterial')")
    @Log(title = "请料", businessType = BusinessType.OTHER)
    @PutMapping("/requestMaterial/{ids}")
    public AjaxResult requestMaterial(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeMlOperatingService.requestMaterial(ids);
            ar = AjaxResult.success("操作成功：[请料]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     *  ids 批次Ids
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:postMl')")
    @Log(title = "转出", businessType = BusinessType.OTHER)
    @PutMapping("/postMl/{ids}")
    public AjaxResult postMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeMlOperatingService.postMl(ids);
            ar = AjaxResult.success("操作成功：[转出]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     *  ids 批次Ids
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:requestStockin')")
    @Log(title = "请入库", businessType = BusinessType.OTHER)
    @PutMapping("/requestStockin/{ids}")
    public AjaxResult requestStockin(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            exeMlOperatingService.requestStockin(ids);
            ar = AjaxResult.success("操作成功：[转出]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     *  ids 批次Ids
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:finishMl')")
    @Log(title = "批次结案", businessType = BusinessType.OTHER)
    @PutMapping("/finishMl/{ids}")
    public AjaxResult finishMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            exeMlOperatingService.finishMl(ids);
            ar = AjaxResult.success("操作成功：[转出]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 查询待完开工单
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:listStartableMachine')")
    @Log(title = "查询可开工机台", businessType = BusinessType.OTHER)
    @GetMapping("/listStartableMachine")
    public TableDataInfo listStartableMachine(ExeMlOperating exeMlOperating)
    {
        List<ResWorkgroupMachine> list = resGroupMachineService.selectStartableMachineListByGroupId(exeMlOperating.getCurrentWorkgroupId());
        return getDataTable(list);
    }

    /**
     * 开工
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:startWork')")
    @Log(title = "开工", businessType = BusinessType.OTHER)
    @PutMapping("/startWork")
    public AjaxResult startWork(@RequestBody ExeMlOperating exeMlOperating)
    {
        AjaxResult ar = null;
        try{
            exeMlOperatingService.startWork(exeMlOperating);
            ar = AjaxResult.success("操作成功：[开工]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 查询待完开工单
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:listUnfinishedWs')")
    @Log(title = "查询待完开工单", businessType = BusinessType.OTHER)
    @GetMapping("/listUnfinishedWs")
    public TableDataInfo listUnfinishedWs(ExeMlOperating exeMlOperating)
    {
        ExeProcessWork ws = new ExeProcessWork();
        ws.setMlId(exeMlOperating.getId());
        ws.setLineId(exeMlOperating.getLineId());
        ws.setGroupId(exeMlOperating.getCurrentWorkgroupId());
        ws.setProcessId(exeMlOperating.getCurrentProcessId());
        ws.setStatus(BillStatus.BASE_ENABLE);//已开工且未结
        List<ExeProcessWork> list = exeProcessWorkService.selectExeProcessWorkList(ws);

        return getDataTable(list);
    }

    /**
     * 完工
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlOperating:finishWork')")
    @Log(title = "完工", businessType = BusinessType.OTHER)
    @PutMapping("/finishWork")
    public AjaxResult finishWork(@RequestBody ExeMlOperating exeMlOperating)
    {
        AjaxResult ar = null;

        try{
            exeMlOperatingService.finishWork(exeMlOperating);
            ar = AjaxResult.success("操作成功：[完工]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }

        return ar;
    }

}

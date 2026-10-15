package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionJudge;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionJudgeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺异常判处Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/eng/exceptionJugge")
public class EngExceptionJudgeController extends BaseController
{
    @Autowired
    private IEngExceptionJudgeService engExceptionJudgeService;

    /**
     * 查询工艺异常判处列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngExceptionJudge engExceptionJudge)
    {
        startPage();
        List<EngExceptionJudge> list = engExceptionJudgeService.selectEngExceptionJudgeList(engExceptionJudge);
        return getDataTable(list);
    }

    /**
     * 导出工艺异常判处列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:export')")
    @Log(title = "工艺异常判处", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngExceptionJudge engExceptionJudge)
    {
        List<EngExceptionJudge> list = engExceptionJudgeService.selectEngExceptionJudgeList(engExceptionJudge);
        ExcelUtil<EngExceptionJudge> util = new ExcelUtil<EngExceptionJudge>(EngExceptionJudge.class);
        util.exportExcel(response, list, "工艺异常判处数据");
    }

    /**
     * 获取工艺异常判处详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engExceptionJudgeService.selectEngExceptionJudgeById(id));
    }

    /**
     * 新增工艺异常判处
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:add')")
    @Log(title = "工艺异常判处", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngExceptionJudge engExceptionJudge)
    {
        AjaxResult ar = null;
        try{
           int ret = engExceptionJudgeService.insertEngExceptionJudge(engExceptionJudge);
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
     * 修改工艺异常判处
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:edit')")
    @Log(title = "工艺异常判处", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngExceptionJudge engExceptionJudge)
    {
        AjaxResult ar = null;
        try{
            int ret = engExceptionJudgeService.updateEngExceptionJudge(engExceptionJudge);
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
     * 删除工艺异常判处
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:remove')")
    @Log(title = "工艺异常判处", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engExceptionJudgeService.deleteEngExceptionJudgeByIds(ids);
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
     * 判处
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionJugge:judge')")
    @Log(title = "判处", businessType = BusinessType.OTHER)
    @PutMapping("/judge")
    public AjaxResult judge(@RequestBody EngExceptionJudge engExceptionJudge)
    {
        AjaxResult ar = null;
        try{
            engExceptionJudgeService.judge(engExceptionJudge);
            ar = AjaxResult.success("操作成功：[判处]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

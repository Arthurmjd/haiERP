package net.factoryopen.myfactoryai.manufacturing.exe.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessWork;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessWorkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工序出工Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/exe/processWork")
public class ExeProcessWorkController extends BaseController
{
    @Autowired
    private IExeProcessWorkService exeProcessWorkService;

    /**
     * 查询工序出工列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeProcessWork exeProcessWork)
    {
        startPage();
        List<ExeProcessWork> list = exeProcessWorkService.selectExeProcessWorkList(exeProcessWork);
        return getDataTable(list);
    }

    /**
     * 查询工序出工列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:listByMl')")
    @GetMapping("/listByMl")
    public TableDataInfo listByMl(PlnMl ml)
    {
        List<ExeProcessWork> list = exeProcessWorkService.listByMl(ml);
        return getDataTable(list);
    }

    /**
     * 导出工序出工列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:export')")
    @Log(title = "工序出工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeProcessWork exeProcessWork)
    {
        List<ExeProcessWork> list = exeProcessWorkService.selectExeProcessWorkList(exeProcessWork);
        ExcelUtil<ExeProcessWork> util = new ExcelUtil<ExeProcessWork>(ExeProcessWork.class);
        util.exportExcel(response, list, "工序出工数据");
    }

    /**
     * 获取工序出工详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeProcessWorkService.selectExeProcessWorkById(id));
    }

    /**
     * 新增工序出工
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:add')")
    @Log(title = "工序出工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeProcessWork exeProcessWork)
    {
        AjaxResult ar = null;
        try{
           int ret = exeProcessWorkService.insertExeProcessWork(exeProcessWork);
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
     * 修改工序出工
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:edit')")
    @Log(title = "工序出工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeProcessWork exeProcessWork)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessWorkService.updateExeProcessWork(exeProcessWork);
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
     * 删除工序出工
     */
    //@PreAuthorize("@ss.hasPermi('exe:processWork:remove')")
    @Log(title = "工序出工", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessWorkService.deleteExeProcessWorkByIds(ids);
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

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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMovesTail;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessMovesTailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工序移转记尾情Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/exe/processMovesTail")
public class ExeProcessMovesTailController extends BaseController
{
    @Autowired
    private IExeProcessMovesTailService exeProcessMovesTailService;

    /**
     * 查询工序移转记尾情列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMovesTail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeProcessMovesTail exeProcessMovesTail)
    {
        startPage();
        List<ExeProcessMovesTail> list = exeProcessMovesTailService.selectExeProcessMovesTailList(exeProcessMovesTail);
        return getDataTable(list);
    }

    /**
     * 导出工序移转记尾情列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMovesTail:export')")
    @Log(title = "工序移转记尾情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeProcessMovesTail exeProcessMovesTail)
    {
        List<ExeProcessMovesTail> list = exeProcessMovesTailService.selectExeProcessMovesTailList(exeProcessMovesTail);
        ExcelUtil<ExeProcessMovesTail> util = new ExcelUtil<ExeProcessMovesTail>(ExeProcessMovesTail.class);
        util.exportExcel(response, list, "工序移转记尾情数据");
    }

    /**
     * 获取工序移转记尾情详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMovesTail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeProcessMovesTailService.selectExeProcessMovesTailById(id));
    }

    /**
     * 新增工序移转记尾情
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMovesTail:add')")
    @Log(title = "工序移转记尾情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeProcessMovesTail exeProcessMovesTail)
    {
        AjaxResult ar = null;
        try{
           int ret = exeProcessMovesTailService.insertExeProcessMovesTail(exeProcessMovesTail);
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
     * 修改工序移转记尾情
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMovesTail:edit')")
    @Log(title = "工序移转记尾情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeProcessMovesTail exeProcessMovesTail)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessMovesTailService.updateExeProcessMovesTail(exeProcessMovesTail);
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
     * 删除工序移转记尾情
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMovesTail:remove')")
    @Log(title = "工序移转记尾情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessMovesTailService.deleteExeProcessMovesTailByIds(ids);
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

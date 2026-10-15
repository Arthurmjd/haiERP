package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingtool;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingtoolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单工具Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/pln/woRoutingtool")
public class PlnWoRoutingtoolController extends BaseController
{
    @Autowired
    private IPlnWoRoutingtoolService plnWoRoutingtoolService;

    /**
     * 查询工单工具列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingtool:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoRoutingtool plnWoRoutingtool)
    {
        startPage();
        List<PlnWoRoutingtool> list = plnWoRoutingtoolService.selectPlnWoRoutingtoolList(plnWoRoutingtool);
        return getDataTable(list);
    }

    /**
     * 导出工单工具列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingtool:export')")
    @Log(title = "工单工具", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoRoutingtool plnWoRoutingtool)
    {
        List<PlnWoRoutingtool> list = plnWoRoutingtoolService.selectPlnWoRoutingtoolList(plnWoRoutingtool);
        ExcelUtil<PlnWoRoutingtool> util = new ExcelUtil<PlnWoRoutingtool>(PlnWoRoutingtool.class);
        util.exportExcel(response, list, "工单工具数据");
    }

    /**
     * 获取工单工具详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingtool:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoRoutingtoolService.selectPlnWoRoutingtoolById(id));
    }

    /**
     * 新增工单工具
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingtool:add')")
    @Log(title = "工单工具", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoRoutingtool plnWoRoutingtool)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoRoutingtoolService.insertPlnWoRoutingtool(plnWoRoutingtool);
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
     * 修改工单工具
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingtool:edit')")
    @Log(title = "工单工具", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoRoutingtool plnWoRoutingtool)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingtoolService.updatePlnWoRoutingtool(plnWoRoutingtool);
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
     * 删除工单工具
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingtool:remove')")
    @Log(title = "工单工具", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingtoolService.deletePlnWoRoutingtoolByIds(ids);
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

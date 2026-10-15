package net.factoryopen.myfactoryai.manufacturing.res.controller;

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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResMachineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机台管理Controller
 *
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/machine")
public class ResMachineController extends BaseController
{
    @Autowired
    private IResMachineService resMachineService;

    /**
     * 查询机台管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:machine:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResMachine resMachine)
    {
        if(resMachine!=null && resMachine.getPageEnable()!=null && resMachine.getPageEnable()) startPage();
        List<ResMachine> list = resMachineService.selectResMachineList(resMachine);
        return getDataTable(list);
    }

    /**
     * 导出机台管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:machine:export')")
    @Log(title = "机台管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResMachine resMachine)
    {
        List<ResMachine> list = resMachineService.selectResMachineList(resMachine);
        ExcelUtil<ResMachine> util = new ExcelUtil<ResMachine>(ResMachine.class);
        util.exportExcel(response, list, "机台管理数据");
    }

    /**
     * 获取机台管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:machine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resMachineService.selectResMachineById(id));
    }

    /**
     * 新增机台管理
     */
    //@PreAuthorize("@ss.hasPermi('res:machine:add')")
    @Log(title = "机台管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResMachine resMachine)
    {
        AjaxResult ar = null;
        try{
           int ret = resMachineService.insertResMachine(resMachine);
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
     * 修改机台管理
     */
    //@PreAuthorize("@ss.hasPermi('res:machine:edit')")
    @Log(title = "机台管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResMachine resMachine)
    {
        AjaxResult ar = null;
        try{
            int ret = resMachineService.updateResMachine(resMachine);
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
     * 删除机台管理
     */
    //@PreAuthorize("@ss.hasPermi('res:machine:remove')")
    @Log(title = "机台管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resMachineService.deleteResMachineByIds(ids);
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

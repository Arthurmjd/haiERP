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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingtool;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingtoolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品工具Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productRoutingtool")
public class EngProductRoutingtoolController extends BaseController
{
    @Autowired
    private IEngProductRoutingtoolService engProductRoutingtoolService;

    /**
     * 查询产品工具列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingtool:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductRoutingtool engProductRoutingtool)
    {
        startPage();
        List<EngProductRoutingtool> list = engProductRoutingtoolService.selectEngProductRoutingtoolList(engProductRoutingtool);
        return getDataTable(list);
    }

    /**
     * 导出产品工具列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingtool:export')")
    @Log(title = "产品工具", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductRoutingtool engProductRoutingtool)
    {
        List<EngProductRoutingtool> list = engProductRoutingtoolService.selectEngProductRoutingtoolList(engProductRoutingtool);
        ExcelUtil<EngProductRoutingtool> util = new ExcelUtil<EngProductRoutingtool>(EngProductRoutingtool.class);
        util.exportExcel(response, list, "产品工具数据");
    }

    /**
     * 获取产品工具详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingtool:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductRoutingtoolService.selectEngProductRoutingtoolById(id));
    }

    /**
     * 新增产品工具
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingtool:add')")
    @Log(title = "产品工具", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductRoutingtool engProductRoutingtool)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductRoutingtoolService.insertEngProductRoutingtool(engProductRoutingtool);
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
     * 修改产品工具
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingtool:edit')")
    @Log(title = "产品工具", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductRoutingtool engProductRoutingtool)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingtoolService.updateEngProductRoutingtool(engProductRoutingtool);
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
     * 删除产品工具
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingtool:remove')")
    @Log(title = "产品工具", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingtoolService.deleteEngProductRoutingtoolByIds(ids);
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

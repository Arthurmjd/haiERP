package net.factoryopen.myfactoryai.manufacturing.ord.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdProject;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户项目预配Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/project")
public class OrdProjectController extends BaseController
{
    @Autowired
    private IOrdProjectService ordProjectService;

    /**
     * 查询客户项目预配列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdProject ordProject)
    {
        startPage();
        List<OrdProject> list = ordProjectService.selectOrdProjectList(ordProject);
        return getDataTable(list);
    }

    /**
     * 导出客户项目预配列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:project:export')")
    @Log(title = "客户项目预配", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdProject ordProject)
    {
        List<OrdProject> list = ordProjectService.selectOrdProjectList(ordProject);
        ExcelUtil<OrdProject> util = new ExcelUtil<OrdProject>(OrdProject.class);
        util.exportExcel(response, list, "客户项目预配数据");
    }

    /**
     * 获取客户项目预配详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordProjectService.selectOrdProjectById(id));
    }

    /**
     * 新增客户项目预配
     */
    //@PreAuthorize("@ss.hasPermi('ord:project:add')")
    @Log(title = "客户项目预配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdProject ordProject)
    {
        AjaxResult ar = null;
        try{
           int ret = ordProjectService.insertOrdProject(ordProject);
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
     * 修改客户项目预配
     */
    //@PreAuthorize("@ss.hasPermi('ord:project:edit')")
    @Log(title = "客户项目预配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdProject ordProject)
    {
        AjaxResult ar = null;
        try{
            int ret = ordProjectService.updateOrdProject(ordProject);
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
     * 删除客户项目预配
     */
    //@PreAuthorize("@ss.hasPermi('ord:project:remove')")
    @Log(title = "客户项目预配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordProjectService.deleteOrdProjectByIds(ids);
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

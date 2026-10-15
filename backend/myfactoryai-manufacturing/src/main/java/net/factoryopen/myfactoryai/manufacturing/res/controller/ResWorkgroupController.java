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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroup;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班组管理Controller
 *
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/workgroup")
public class ResWorkgroupController extends BaseController
{
    @Autowired
    private IResWorkgroupService resWorkgroupService;

    /**
     * 查询班组管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroup:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResWorkgroup resWorkgroup)
    {
        if(resWorkgroup!=null && resWorkgroup.getPageEnable()!=null && resWorkgroup.getPageEnable()) startPage();
        List<ResWorkgroup> list = resWorkgroupService.selectResWorkgroupList(resWorkgroup);
        return getDataTable(list);
    }

    /**
     * 导出班组管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroup:export')")
    @Log(title = "班组管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResWorkgroup resWorkgroup)
    {
        List<ResWorkgroup> list = resWorkgroupService.selectResWorkgroupList(resWorkgroup);
        ExcelUtil<ResWorkgroup> util = new ExcelUtil<ResWorkgroup>(ResWorkgroup.class);
        util.exportExcel(response, list, "班组管理数据");
    }

    /**
     * 获取班组管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroup:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resWorkgroupService.selectResWorkgroupById(id));
    }

    /**
     * 新增班组管理
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroup:add')")
    @Log(title = "班组管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResWorkgroup resWorkgroup)
    {
        AjaxResult ar = null;
        try{
           int ret = resWorkgroupService.insertResWorkgroup(resWorkgroup);
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
     * 修改班组管理
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroup:edit')")
    @Log(title = "班组管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResWorkgroup resWorkgroup)
    {
        AjaxResult ar = null;
        try{
            int ret = resWorkgroupService.updateResWorkgroup(resWorkgroup);
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
     * 删除班组管理
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroup:remove')")
    @Log(title = "班组管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resWorkgroupService.deleteResWorkgroupByIds(ids);
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

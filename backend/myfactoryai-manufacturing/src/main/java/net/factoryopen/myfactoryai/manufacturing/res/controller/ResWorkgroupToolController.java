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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupTool;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupToolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班组工序Controller
 *
 * @author admin
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/workgroupTool")
public class ResWorkgroupToolController extends BaseController
{
    @Autowired
    private IResWorkgroupToolService resWorkgroupToolService;

    /**
     * 查询班组工序列表
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupTool:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResWorkgroupTool resWorkgroupTool)
    {
        if(resWorkgroupTool!=null && resWorkgroupTool.getPageEnable()!=null && resWorkgroupTool.getPageEnable()) startPage();
        List<ResWorkgroupTool> list = resWorkgroupToolService.selectResWorkgroupToolList(resWorkgroupTool);
        return getDataTable(list);
    }

    /**
     * 导出班组工序列表
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupTool:export')")
    @Log(title = "班组工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResWorkgroupTool resWorkgroupTool)
    {
        List<ResWorkgroupTool> list = resWorkgroupToolService.selectResWorkgroupToolList(resWorkgroupTool);
        ExcelUtil<ResWorkgroupTool> util = new ExcelUtil<ResWorkgroupTool>(ResWorkgroupTool.class);
        util.exportExcel(response, list, "班组工序数据");
    }

    /**
     * 获取班组工序详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupTool:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resWorkgroupToolService.selectResWorkgroupToolById(id));
    }

    /**
     * 新增班组工序
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupTool:add')")
    @Log(title = "班组工序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResWorkgroupTool resWorkgroupTool)
    {
        AjaxResult ar = null;
        try{
           int ret = resWorkgroupToolService.insertResWorkgroupTool(resWorkgroupTool);
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
     * 修改班组工序
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupTool:edit')")
    @Log(title = "班组工序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResWorkgroupTool resWorkgroupTool)
    {
        AjaxResult ar = null;
        try{
            int ret = resWorkgroupToolService.updateResWorkgroupTool(resWorkgroupTool);
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
     * 删除班组工序
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupTool:remove')")
    @Log(title = "班组工序", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resWorkgroupToolService.deleteResWorkgroupToolByIds(ids);
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

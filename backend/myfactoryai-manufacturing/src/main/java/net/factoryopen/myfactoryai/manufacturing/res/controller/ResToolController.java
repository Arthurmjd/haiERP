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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResTool;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResToolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工具管理Controller
 *
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/tool")
public class ResToolController extends BaseController
{
    @Autowired
    private IResToolService resToolService;

    /**
     * 查询工具管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:tool:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResTool resTool)
    {
        if(resTool!=null && resTool.getPageEnable()!=null && resTool.getPageEnable()) startPage();
        List<ResTool> list = resToolService.selectResToolList(resTool);
        return getDataTable(list);
    }

    /**
     * 导出工具管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:tool:export')")
    @Log(title = "工具管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResTool resTool)
    {
        List<ResTool> list = resToolService.selectResToolList(resTool);
        ExcelUtil<ResTool> util = new ExcelUtil<ResTool>(ResTool.class);
        util.exportExcel(response, list, "工具管理数据");
    }

    /**
     * 获取工具管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:tool:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resToolService.selectResToolById(id));
    }

    /**
     * 新增工具管理
     */
    //@PreAuthorize("@ss.hasPermi('res:tool:add')")
    @Log(title = "工具管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResTool resTool)
    {
        AjaxResult ar = null;
        try{
           int ret = resToolService.insertResTool(resTool);
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
     * 修改工具管理
     */
    //@PreAuthorize("@ss.hasPermi('res:tool:edit')")
    @Log(title = "工具管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResTool resTool)
    {
        AjaxResult ar = null;
        try{
            int ret = resToolService.updateResTool(resTool);
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
     * 删除工具管理
     */
    //@PreAuthorize("@ss.hasPermi('res:tool:remove')")
    @Log(title = "工具管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resToolService.deleteResToolByIds(ids);
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

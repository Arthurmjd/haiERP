package net.factoryopen.myfactoryai.manufacturing.qua.controller;

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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspect;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaFreeinspectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 免检策略管理Controller
 *
 * @author admin
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/qua/freeinspect")
public class QuaFreeinspectController extends BaseController
{
    @Autowired
    private IQuaFreeinspectService quaFreeinspectService;

    /**
     * 查询免检策略管理列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspect:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaFreeinspect quaFreeinspect)
    {
        startPage();
        List<QuaFreeinspect> list = quaFreeinspectService.selectQuaFreeinspectList(quaFreeinspect);
        return getDataTable(list);
    }

    /**
     * 导出免检策略管理列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspect:export')")
    @Log(title = "免检策略管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaFreeinspect quaFreeinspect)
    {
        List<QuaFreeinspect> list = quaFreeinspectService.selectQuaFreeinspectList(quaFreeinspect);
        ExcelUtil<QuaFreeinspect> util = new ExcelUtil<QuaFreeinspect>(QuaFreeinspect.class);
        util.exportExcel(response, list, "免检策略管理数据");
    }

    /**
     * 获取免检策略管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaFreeinspectService.selectQuaFreeinspectById(id));
    }

    /**
     * 新增免检策略管理
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspect:add')")
    @Log(title = "免检策略管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaFreeinspect quaFreeinspect)
    {
        AjaxResult ar = null;
        try{
           int ret = quaFreeinspectService.insertQuaFreeinspect(quaFreeinspect);
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
     * 修改免检策略管理
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspect:edit')")
    @Log(title = "免检策略管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaFreeinspect quaFreeinspect)
    {
        AjaxResult ar = null;
        try{
            int ret = quaFreeinspectService.updateQuaFreeinspect(quaFreeinspect);
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
     * 删除免检策略管理
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspect:remove')")
    @Log(title = "免检策略管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaFreeinspectService.deleteQuaFreeinspectByIds(ids);
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

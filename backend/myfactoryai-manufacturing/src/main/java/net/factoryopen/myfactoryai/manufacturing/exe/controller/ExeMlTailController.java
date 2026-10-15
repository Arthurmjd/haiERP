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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTail;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlTailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制造批次尾情Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/exe/mlHistoryTail")
public class ExeMlTailController extends BaseController
{
    @Autowired
    private IExeMlTailService exeMlTailService;

    /**
     * 查询制造批次尾情列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistoryTail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlTail exeMlTail)
    {
        startPage();
        List<ExeMlTail> list = exeMlTailService.selectExeMlTailList(exeMlTail);
        return getDataTable(list);
    }

    /**
     * 导出制造批次尾情列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistoryTail:export')")
    @Log(title = "制造批次尾情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlTail exeMlTail)
    {
        List<ExeMlTail> list = exeMlTailService.selectExeMlTailList(exeMlTail);
        ExcelUtil<ExeMlTail> util = new ExcelUtil<ExeMlTail>(ExeMlTail.class);
        util.exportExcel(response, list, "制造批次尾情数据");
    }

    /**
     * 获取制造批次尾情详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistoryTail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlTailService.selectExeMlTailById(id));
    }

    /**
     * 新增制造批次尾情
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistoryTail:add')")
    @Log(title = "制造批次尾情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlTail exeMlTail)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlTailService.insertExeMlTail(exeMlTail);
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
     * 修改制造批次尾情
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistoryTail:edit')")
    @Log(title = "制造批次尾情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlTail exeMlTail)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlTailService.updateExeMlTail(exeMlTail);
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
     * 删除制造批次尾情
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistoryTail:remove')")
    @Log(title = "制造批次尾情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlTailService.deleteExeMlTailByIds(ids);
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

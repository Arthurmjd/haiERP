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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlHistory;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 历史批次查询Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/exe/mlHistory")
public class ExeMlHistoryController extends BaseController
{
    @Autowired
    private IExeMlHistoryService exeMlHistoryService;

    /**
     * 查询历史批次查询列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlHistory exeMlHistory)
    {
        startPage();
        List<ExeMlHistory> list = exeMlHistoryService.selectExeMlHistoryList(exeMlHistory);
        return getDataTable(list);
    }

    /**
     * 导出历史批次查询列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistory:export')")
    @Log(title = "历史批次查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlHistory exeMlHistory)
    {
        List<ExeMlHistory> list = exeMlHistoryService.selectExeMlHistoryList(exeMlHistory);
        ExcelUtil<ExeMlHistory> util = new ExcelUtil<ExeMlHistory>(ExeMlHistory.class);
        util.exportExcel(response, list, "历史批次查询数据");
    }

    /**
     * 获取历史批次查询详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlHistoryService.selectExeMlHistoryById(id));
    }

    /**
     * 新增历史批次查询
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistory:add')")
    @Log(title = "历史批次查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlHistory exeMlHistory)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlHistoryService.insertExeMlHistory(exeMlHistory);
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
     * 修改历史批次查询
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistory:edit')")
    @Log(title = "历史批次查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlHistory exeMlHistory)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlHistoryService.updateExeMlHistory(exeMlHistory);
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
     * 删除历史批次查询
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlHistory:remove')")
    @Log(title = "历史批次查询", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlHistoryService.deleteExeMlHistoryByIds(ids);
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

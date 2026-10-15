package net.factoryopen.myfactoryai.manufacturing.snc.controller;

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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventorySummary;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncInventorySummaryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存总览表Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-20
 */
@RestController
@RequestMapping("/snc/inventorySummary")
public class SncInventorySummaryController extends BaseController
{
    @Autowired
    private ISncInventorySummaryService sncInventorySummaryService;

    /**
     * 查询库存总览表列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventorySummary:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncInventorySummary sncInventorySummary)
    {
        startPage();
        List<SncInventorySummary> list = sncInventorySummaryService.selectSncInventorySummaryList(sncInventorySummary);
        return getDataTable(list);
    }

    /**
     * 导出库存总览表列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventorySummary:export')")
    @Log(title = "库存总览表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncInventorySummary sncInventorySummary)
    {
        List<SncInventorySummary> list = sncInventorySummaryService.selectSncInventorySummaryList(sncInventorySummary);
        ExcelUtil<SncInventorySummary> util = new ExcelUtil<SncInventorySummary>(SncInventorySummary.class);
        util.exportExcel(response, list, "库存总览表数据");
    }

    /**
     * 获取库存总览表详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventorySummary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncInventorySummaryService.selectSncInventorySummaryById(id));
    }

    /**
     * 新增库存总览表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventorySummary:add')")
    @Log(title = "库存总览表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncInventorySummary sncInventorySummary)
    {
        AjaxResult ar = null;
        try{
           int ret = sncInventorySummaryService.insertSncInventorySummary(sncInventorySummary);
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
     * 修改库存总览表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventorySummary:edit')")
    @Log(title = "库存总览表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncInventorySummary sncInventorySummary)
    {
        AjaxResult ar = null;
        try{
            int ret = sncInventorySummaryService.updateSncInventorySummary(sncInventorySummary);
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
     * 删除库存总览表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventorySummary:remove')")
    @Log(title = "库存总览表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncInventorySummaryService.deleteSncInventorySummaryByIds(ids);
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

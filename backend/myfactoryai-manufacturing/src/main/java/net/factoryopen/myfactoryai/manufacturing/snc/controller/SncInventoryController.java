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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventory;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库存货详单Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-21
 */
@RestController
@RequestMapping("/snc/inventory")
public class SncInventoryController extends BaseController
{
    @Autowired
    private ISncInventoryService sncInventoryService;

    /**
     * 查询仓库存货详单列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncInventory sncInventory)
    {
        if(sncInventory!=null && sncInventory.getPageEnable()!=null && sncInventory.getPageEnable()) startPage();
        List<SncInventory> list = sncInventoryService.selectSncInventoryList(sncInventory);
        return getDataTable(list);
    }

    /**
     * 导出仓库存货详单列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventory:export')")
    @Log(title = "仓库存货详单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncInventory sncInventory)
    {
        List<SncInventory> list = sncInventoryService.selectSncInventoryList(sncInventory);
        ExcelUtil<SncInventory> util = new ExcelUtil<SncInventory>(SncInventory.class);
        util.exportExcel(response, list, "仓库存货详单数据");
    }

    /**
     * 获取仓库存货详单详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncInventoryService.selectSncInventoryById(id));
    }

    /**
     * 新增仓库存货详单
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventory:add')")
    @Log(title = "仓库存货详单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncInventory sncInventory)
    {
        AjaxResult ar = null;
        try{
           int ret = sncInventoryService.insertSncInventory(sncInventory);
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
     * 修改仓库存货详单
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventory:edit')")
    @Log(title = "仓库存货详单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncInventory sncInventory)
    {
        AjaxResult ar = null;
        try{
            int ret = sncInventoryService.updateSncInventory(sncInventory);
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
     * 删除仓库存货详单
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventory:remove')")
    @Log(title = "仓库存货详单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncInventoryService.deleteSncInventoryByIds(ids);
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

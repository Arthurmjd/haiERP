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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGrItem;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncGrItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收货单明细Controller
 *
 * @author admin
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/snc/grItem")
public class SncGrItemController extends BaseController
{
    @Autowired
    private ISncGrItemService sncGrItemService;

    /**
     * 查询收货单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:grItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncGrItem sncGrItem)
    {
        startPage();
        List<SncGrItem> list = sncGrItemService.selectSncGrItemList(sncGrItem);
        return getDataTable(list);
    }

    /**
     * 导出收货单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:grItem:export')")
    @Log(title = "收货单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncGrItem sncGrItem)
    {
        List<SncGrItem> list = sncGrItemService.selectSncGrItemList(sncGrItem);
        ExcelUtil<SncGrItem> util = new ExcelUtil<SncGrItem>(SncGrItem.class);
        util.exportExcel(response, list, "收货单明细数据");
    }

    /**
     * 获取收货单明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:grItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncGrItemService.selectSncGrItemById(id));
    }

    /**
     * 新增收货单明细
     */
    //@PreAuthorize("@ss.hasPermi('snc:grItem:add')")
    @Log(title = "收货单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncGrItem sncGrItem)
    {
        AjaxResult ar = null;
        try{
           int ret = sncGrItemService.insertSncGrItem(sncGrItem);
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
     * 修改收货单明细
     */
    //@PreAuthorize("@ss.hasPermi('snc:grItem:edit')")
    @Log(title = "收货单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncGrItem sncGrItem)
    {
        AjaxResult ar = null;
        try{
            int ret = sncGrItemService.updateSncGrItem(sncGrItem);
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
     * 删除收货单明细
     */
    //@PreAuthorize("@ss.hasPermi('snc:grItem:remove')")
    @Log(title = "收货单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncGrItemService.deleteSncGrItemByIds(ids);
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

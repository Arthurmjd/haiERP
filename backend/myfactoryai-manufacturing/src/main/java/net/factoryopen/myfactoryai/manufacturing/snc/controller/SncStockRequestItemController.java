package net.factoryopen.myfactoryai.manufacturing.snc.controller;

import java.math.BigDecimal;
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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockRequestItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出入库请求明细Controller
 *
 * @author admin
 * @date 2025-05-17
 */
@RestController
@RequestMapping("/snc/stockRequestItem")
public class SncStockRequestItemController extends BaseController
{
    @Autowired
    private ISncStockRequestItemService sncStockRequestItemService;

    /**
     * 查询出入库请求明细列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequestItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncStockRequestItem sncStockRequestItem)
    {
        startPage();
        List<SncStockRequestItem> list = sncStockRequestItemService.selectSncStockRequestItemList(sncStockRequestItem);
        return getDataTable(list);
    }

    /**
     * 导出出入库请求明细列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequestItem:export')")
    @Log(title = "出入库请求明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncStockRequestItem sncStockRequestItem)
    {
        List<SncStockRequestItem> list = sncStockRequestItemService.selectSncStockRequestItemList(sncStockRequestItem);
        ExcelUtil<SncStockRequestItem> util = new ExcelUtil<SncStockRequestItem>(SncStockRequestItem.class);
        util.exportExcel(response, list, "出入库请求明细数据");
    }

    /**
     * 获取出入库请求明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequestItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncStockRequestItemService.selectSncStockRequestItemById(id));
    }

    /**
     * 新增出入库请求明细
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequestItem:add')")
    @Log(title = "出入库请求明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncStockRequestItem sncStockRequestItem)
    {
        AjaxResult ar = null;
        try{
           int ret = sncStockRequestItemService.insertSncStockRequestItem(sncStockRequestItem);
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
     * 修改出入库请求明细
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequestItem:edit')")
    @Log(title = "出入库请求明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncStockRequestItem sncStockRequestItem)
    {
        AjaxResult ar = null;
        try{
            int ret = sncStockRequestItemService.updateSncStockRequestItem(sncStockRequestItem);
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
     * 删除出入库请求明细
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequestItem:remove')")
    @Log(title = "出入库请求明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncStockRequestItemService.deleteSncStockRequestItemByIds(ids);
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

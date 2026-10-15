package net.factoryopen.myfactoryai.manufacturing.sup.controller;

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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchaseRequestItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购申请明细Controller
 *
 * @author admin
 * @date 2024-09-16
 */
@RestController
@RequestMapping("/sup/purchaseRequestItem")
public class SupPurchaseRequestItemController extends BaseController
{
    @Autowired
    private ISupPurchaseRequestItemService supPurchaseRequestItemService;

    /**
     * 查询采购申请明细列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPurchaseRequestItem supPurchaseRequestItem)
    {
        if(supPurchaseRequestItem!=null && supPurchaseRequestItem.getPageEnable()!=null && supPurchaseRequestItem.getPageEnable()) startPage();
        List<SupPurchaseRequestItem> list = supPurchaseRequestItemService.selectSupPurchaseRequestItemList(supPurchaseRequestItem);
        return getDataTable(list);
    }

    /**
     * 导出采购申请明细列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestItem:export')")
    @Log(title = "采购申请明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPurchaseRequestItem supPurchaseRequestItem)
    {
        List<SupPurchaseRequestItem> list = supPurchaseRequestItemService.selectSupPurchaseRequestItemList(supPurchaseRequestItem);
        ExcelUtil<SupPurchaseRequestItem> util = new ExcelUtil<SupPurchaseRequestItem>(SupPurchaseRequestItem.class);
        util.exportExcel(response, list, "采购申请明细数据");
    }

    /**
     * 获取采购申请明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPurchaseRequestItemService.selectSupPurchaseRequestItemById(id));
    }

    /**
     * 新增采购申请明细
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestItem:add')")
    @Log(title = "采购申请明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPurchaseRequestItem supPurchaseRequestItem)
    {
        AjaxResult ar = null;
        try{
           int ret = supPurchaseRequestItemService.insertSupPurchaseRequestItem(supPurchaseRequestItem);
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
     * 修改采购申请明细
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestItem:edit')")
    @Log(title = "采购申请明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPurchaseRequestItem supPurchaseRequestItem)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchaseRequestItemService.updateSupPurchaseRequestItem(supPurchaseRequestItem);
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
     * 删除采购申请明细
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestItem:remove')")
    @Log(title = "采购申请明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchaseRequestItemService.deleteSupPurchaseRequestItemByIds(ids);
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

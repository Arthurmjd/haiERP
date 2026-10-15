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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPoItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购单明细Controller
 *
 * @author admin
 * @date 2024-09-23
 */
@RestController
@RequestMapping("/sup/poItem")
public class SupPoItemController extends BaseController
{
    @Autowired
    private ISupPoItemService supPoItemService;

    /**
     * 查询采购单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPoItem supPoItem)
    {
        startPage();
        List<SupPoItem> list = supPoItemService.selectSupPoItemList(supPoItem);
        return getDataTable(list);
    }

    /**
     * 导出采购单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItem:export')")
    @Log(title = "采购单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPoItem supPoItem)
    {
        List<SupPoItem> list = supPoItemService.selectSupPoItemList(supPoItem);
        ExcelUtil<SupPoItem> util = new ExcelUtil<SupPoItem>(SupPoItem.class);
        util.exportExcel(response, list, "采购单明细数据");
    }

    /**
     * 获取采购单明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPoItemService.selectSupPoItemById(id));
    }

    /**
     * 新增采购单明细
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItem:add')")
    @Log(title = "采购单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPoItem supPoItem)
    {
        AjaxResult ar = null;
        try{
           int ret = supPoItemService.insertSupPoItem(supPoItem);
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
     * 修改采购单明细
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItem:edit')")
    @Log(title = "采购单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPoItem supPoItem)
    {
        AjaxResult ar = null;
        try{
            int ret = supPoItemService.updateSupPoItem(supPoItem);
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
     * 删除采购单明细
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItem:remove')")
    @Log(title = "采购单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPoItemService.deleteSupPoItemByIds(ids);
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

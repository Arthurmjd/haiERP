package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryOrderService;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelGdItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发货单明细Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/gdItem")
public class DelGdItemController extends BaseController
{
    @Autowired
    private IDelGdItemService delGdItemService;

    @Autowired
    private IDelDeliveryOrderService doService;

    /**
     * 查询发货单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('del:gdItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelGdItem delGdItem)
    {
        startPage();
        List<DelGdItem> list = delGdItemService.selectDelGdItemList(delGdItem);
        return getDataTable(list);
    }

    /**
     * 导出发货单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('del:gdItem:export')")
    @Log(title = "发货单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelGdItem delGdItem)
    {
        List<DelGdItem> list = delGdItemService.selectDelGdItemList(delGdItem);
        ExcelUtil<DelGdItem> util = new ExcelUtil<DelGdItem>(DelGdItem.class);
        util.exportExcel(response, list, "发货单明细数据");
    }

    /**
     * 获取发货单明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:gdItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delGdItemService.selectDelGdItemById(id));
    }

    /**
     * 新增发货单明细
     */
    //@PreAuthorize("@ss.hasPermi('del:gdItem:add')")
    @Log(title = "发货单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelGdItem delGdItem)
    {
        AjaxResult ar = null;
        try{
           int ret = delGdItemService.insertDelGdItem(delGdItem);
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
     * 修改发货单明细
     */
    //@PreAuthorize("@ss.hasPermi('del:gdItem:edit')")
    @Log(title = "发货单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelGdItem delGdItem)
    {
        AjaxResult ar = null;
        try{
            DelDeliveryOrder doo = doService.selectDelDeliveryOrderById(delGdItem.getGdId());
            if (doo.getStatus()!= BillStatus.BASE_DRAFT
                    && doo.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！父对象的当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delGdItemService.updateDelGdItem(delGdItem);
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
     * 删除发货单明细
     */
    //@PreAuthorize("@ss.hasPermi('del:gdItem:remove')")
    @Log(title = "发货单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delGdItemService.deleteDelGdItemByIds(ids);
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

package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPlanItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发货计划Controller
 *
 * @author admin
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/planItem")
public class DelPlanItemController extends BaseController
{
    @Autowired
    private IDelPlanItemService delPlanItemService;

    /**
     * 查询发货计划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:planItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelPlanItem delPlanItem)
    {
        startPage();
        List<DelPlanItem> list = delPlanItemService.selectDelPlanItemList(delPlanItem);
        return getDataTable(list);
    }

    /**
     * 导出发货计划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:planItem:export')")
    @Log(title = "发货计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelPlanItem delPlanItem)
    {
        List<DelPlanItem> list = delPlanItemService.selectDelPlanItemList(delPlanItem);
        ExcelUtil<DelPlanItem> util = new ExcelUtil<DelPlanItem>(DelPlanItem.class);
        util.exportExcel(response, list, "发货计划数据");
    }

    /**
     * 获取发货计划详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:planItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delPlanItemService.selectDelPlanItemById(id));
    }

    /**
     * 新增发货计划
     */
    //@PreAuthorize("@ss.hasPermi('del:planItem:add')")
    @Log(title = "发货计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelPlanItem delPlanItem)
    {
        AjaxResult ar = null;
        try{
           int ret = delPlanItemService.insertDelPlanItem(delPlanItem);
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
     * 修改发货计划
     */
    //@PreAuthorize("@ss.hasPermi('del:planItem:edit')")
    @Log(title = "发货计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelPlanItem delPlanItem)
    {
        AjaxResult ar = null;
        try{
            if (delPlanItem.getStatus()!= BillStatus.BASE_DRAFT
                    && delPlanItem.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delPlanItemService.updateDelPlanItem(delPlanItem);
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
     * 删除发货计划
     */
    //@PreAuthorize("@ss.hasPermi('del:planItem:remove')")
    @Log(title = "发货计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delPlanItemService.deleteDelPlanItemByIds(ids);
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

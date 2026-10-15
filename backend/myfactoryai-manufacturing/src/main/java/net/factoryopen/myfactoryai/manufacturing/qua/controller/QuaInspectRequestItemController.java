package net.factoryopen.myfactoryai.manufacturing.qua.controller;

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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequestItem;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectRequestItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 请检Controller
 *
 * @author admin
 * @date 2024-10-06
 */
@RestController
@RequestMapping("/qua/inspectRequestItem")
public class QuaInspectRequestItemController extends BaseController
{
    @Autowired
    private IQuaInspectRequestItemService quaInspectRequestItemService;

    /**
     * 查询请检列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaInspectRequestItem quaInspectRequestItem)
    {
        startPage();
        List<QuaInspectRequestItem> list = quaInspectRequestItemService.selectQuaInspectRequestItemList(quaInspectRequestItem);
        return getDataTable(list);
    }

    /**
     * 导出请检列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:export')")
    @Log(title = "请检", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaInspectRequestItem quaInspectRequestItem)
    {
        List<QuaInspectRequestItem> list = quaInspectRequestItemService.selectQuaInspectRequestItemList(quaInspectRequestItem);
        ExcelUtil<QuaInspectRequestItem> util = new ExcelUtil<QuaInspectRequestItem>(QuaInspectRequestItem.class);
        util.exportExcel(response, list, "请检数据");
    }

    /**
     * 获取请检详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaInspectRequestItemService.selectQuaInspectRequestItemById(id));
    }

    /**
     * 新增请检
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:add')")
    @Log(title = "请检", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaInspectRequestItem quaInspectRequestItem)
    {
        AjaxResult ar = null;
        try{
           int ret = quaInspectRequestItemService.insertQuaInspectRequestItem(quaInspectRequestItem);
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
     * 修改请检
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:edit')")
    @Log(title = "请检", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaInspectRequestItem quaInspectRequestItem)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectRequestItemService.updateQuaInspectRequestItem(quaInspectRequestItem);
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
     * 删除请检
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:remove')")
    @Log(title = "请检", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectRequestItemService.deleteQuaInspectRequestItemByIds(ids);
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

    /**
     * 检良
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:registerGoodInspection')")
    @Log(title = "检良", businessType = BusinessType.OTHER)
    @PutMapping("/registerGoodInspection/{id}")
    public AjaxResult registerGoodInspection(@PathVariable Long id, @RequestBody QuaInspectRequestItem quaInspectRequestItem)
    {
        AjaxResult ar = null;
        try{
            quaInspectRequestItemService.registerGoodInspection(id, quaInspectRequestItem);
            ar = AjaxResult.success("操作成功：[合格]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 检异
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:registerExceptionalInspection')")
    @Log(title = "检异", businessType = BusinessType.OTHER)
    @PutMapping("/registerExceptionalInspection/{id}")
    public AjaxResult registerExceptionalInspection(@PathVariable Long id, @RequestBody QuaInspectRequestItem quaInspectRequestItem)
    {
        AjaxResult ar = null;
        try{
            quaInspectRequestItemService.registerExceptionalInspection(id, quaInspectRequestItem);
            ar = AjaxResult.success("操作成功：[检异]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 请判
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectRequestItem:requestJugde')")
    @Log(title = "请判", businessType = BusinessType.OTHER)
    @PutMapping("/requestJugde")
    public AjaxResult requestJugde(@RequestBody QuaInspectRequestItem quaInspectRequestItem)
    {
        AjaxResult ar = null;
        try{
            quaInspectRequestItemService.requestJugde(quaInspectRequestItem);
            ar = AjaxResult.success("操作成功：[请判]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
}

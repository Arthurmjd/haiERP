package net.factoryopen.myfactoryai.manufacturing.del.controller;

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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplanReceiving;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelOsplanReceivingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外协收货Controller
 *
 * @author admin
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/osplanReceiving")
public class DelOsplanReceivingController extends BaseController
{
    @Autowired
    private IDelOsplanReceivingService delOsplanReceivingService;

    /**
     * 查询外协收货列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelOsplanReceiving delOsplanReceiving)
    {
        startPage();
        List<DelOsplanReceiving> list = delOsplanReceivingService.selectDelOsplanReceivingList(delOsplanReceiving);
        return getDataTable(list);
    }

    /**
     * 导出外协收货列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:export')")
    @Log(title = "外协收货", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelOsplanReceiving delOsplanReceiving)
    {
        List<DelOsplanReceiving> list = delOsplanReceivingService.selectDelOsplanReceivingList(delOsplanReceiving);
        ExcelUtil<DelOsplanReceiving> util = new ExcelUtil<DelOsplanReceiving>(DelOsplanReceiving.class);
        util.exportExcel(response, list, "外协收货数据");
    }

    /**
     * 获取外协收货详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delOsplanReceivingService.selectDelOsplanReceivingById(id));
    }

    /**
     * 新增外协收货
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:add')")
    @Log(title = "外协收货", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelOsplanReceiving delOsplanReceiving)
    {
        AjaxResult ar = null;
        try{
           int ret = delOsplanReceivingService.insertDelOsplanReceiving(delOsplanReceiving);
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
     * 修改外协收货
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:edit')")
    @Log(title = "外协收货", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelOsplanReceiving delOsplanReceiving)
    {
        AjaxResult ar = null;
        try{
            int ret = delOsplanReceivingService.updateDelOsplanReceiving(delOsplanReceiving);
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
     * 删除外协收货
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:remove')")
    @Log(title = "外协收货", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delOsplanReceivingService.deleteDelOsplanReceivingByIds(ids);
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
     * 请检
     */
    //@PreAuthorize("@ss.hasPermi('del:osplanReceiving:requestInspection')")
    @Log(title = "请检", businessType = BusinessType.OTHER)
    @PutMapping("/requestInspection")
    public AjaxResult requestInspection(@RequestBody DelOsplanReceiving delOsplanReceiving)
    {
        AjaxResult ar = null;
        try{
                delOsplanReceivingService.requestInspection(delOsplanReceiving);
            ar = AjaxResult.success("操作成功：[请检]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
}

package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRouting;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单工艺管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/pln/woRouting")
public class PlnWoRoutingController extends BaseController
{
    @Autowired
    private IPlnWoRoutingService plnWoRoutingService;

    /**
     * 查询工单工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRouting:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoRouting plnWoRouting)
    {
        startPage();
        List<PlnWoRouting> list = plnWoRoutingService.selectPlnWoRoutingList(plnWoRouting);
        return getDataTable(list);
    }

    /**
     * 导出工单工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRouting:export')")
    @Log(title = "工单工艺管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoRouting plnWoRouting)
    {
        List<PlnWoRouting> list = plnWoRoutingService.selectPlnWoRoutingList(plnWoRouting);
        ExcelUtil<PlnWoRouting> util = new ExcelUtil<PlnWoRouting>(PlnWoRouting.class);
        util.exportExcel(response, list, "工单工艺管理数据");
    }

    /**
     * 获取工单工艺管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRouting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoRoutingService.selectPlnWoRoutingById(id));
    }

    /**
     * 新增工单工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRouting:add')")
    @Log(title = "工单工艺管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoRouting plnWoRouting)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoRoutingService.insertPlnWoRouting(plnWoRouting);
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
     * 修改工单工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRouting:edit')")
    @Log(title = "工单工艺管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoRouting plnWoRouting)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingService.updatePlnWoRouting(plnWoRouting);
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
     * 删除工单工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRouting:remove')")
    @Log(title = "工单工艺管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingService.deletePlnWoRoutingByIds(ids);
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

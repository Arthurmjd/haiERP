package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制造工艺管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productRouting")
public class EngProductRoutingController extends BaseController
{
    @Autowired
    private IEngProductRoutingService engProductRoutingService;

    /**
     * 查询制造工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRouting:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductRouting engProductRouting)
    {
        startPage();
        List<EngProductRouting> list = engProductRoutingService.selectEngProductRoutingList(engProductRouting);
        return getDataTable(list);
    }

    /**
     * 导出制造工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRouting:export')")
    @Log(title = "制造工艺管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductRouting engProductRouting)
    {
        List<EngProductRouting> list = engProductRoutingService.selectEngProductRoutingList(engProductRouting);
        ExcelUtil<EngProductRouting> util = new ExcelUtil<EngProductRouting>(EngProductRouting.class);
        util.exportExcel(response, list, "制造工艺管理数据");
    }

    /**
     * 获取制造工艺管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRouting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductRoutingService.selectEngProductRoutingById(id));
    }

    /**
     * 新增制造工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRouting:add')")
    @Log(title = "制造工艺管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductRouting engProductRouting)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductRoutingService.insertEngProductRouting(engProductRouting);
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
     * 修改制造工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRouting:edit')")
    @Log(title = "制造工艺管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductRouting engProductRouting)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingService.updateEngProductRouting(engProductRouting);
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
     * 删除制造工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRouting:remove')")
    @Log(title = "制造工艺管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingService.deleteEngProductRoutingByIds(ids);
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

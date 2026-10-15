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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRouting;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次工艺管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlRouting")
public class PlnMlRoutingController extends BaseController
{
    @Autowired
    private IPlnMlRoutingService plnMlRoutingService;

    /**
     * 查询批次工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRouting:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlRouting plnMlRouting)
    {
        startPage();
        List<PlnMlRouting> list = plnMlRoutingService.selectPlnMlRoutingList(plnMlRouting);
        return getDataTable(list);
    }

    /**
     * 导出批次工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRouting:export')")
    @Log(title = "批次工艺管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlRouting plnMlRouting)
    {
        List<PlnMlRouting> list = plnMlRoutingService.selectPlnMlRoutingList(plnMlRouting);
        ExcelUtil<PlnMlRouting> util = new ExcelUtil<PlnMlRouting>(PlnMlRouting.class);
        util.exportExcel(response, list, "批次工艺管理数据");
    }

    /**
     * 获取批次工艺管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRouting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlRoutingService.selectPlnMlRoutingById(id));
    }

    /**
     * 新增批次工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRouting:add')")
    @Log(title = "批次工艺管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlRouting plnMlRouting)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlRoutingService.insertPlnMlRouting(plnMlRouting);
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
     * 修改批次工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRouting:edit')")
    @Log(title = "批次工艺管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlRouting plnMlRouting)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingService.updatePlnMlRouting(plnMlRouting);
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
     * 删除批次工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRouting:remove')")
    @Log(title = "批次工艺管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingService.deletePlnMlRoutingByIds(ids);
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

package net.factoryopen.myfactoryai.manufacturing.ord.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoTrace;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoTraceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 在制订单跟踪Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/soTrace")
public class OrdSoTraceController extends BaseController
{
    @Autowired
    private IOrdSoTraceService ordSoTraceService;

    /**
     * 查询在制订单跟踪列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soTrace:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdSoTrace ordSoTrace)
    {
        startPage();
        List<OrdSoTrace> list = ordSoTraceService.selectOrdSoTraceList(ordSoTrace);
        return getDataTable(list);
    }

    /**
     * 导出在制订单跟踪列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soTrace:export')")
    @Log(title = "在制订单跟踪", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdSoTrace ordSoTrace)
    {
        List<OrdSoTrace> list = ordSoTraceService.selectOrdSoTraceList(ordSoTrace);
        ExcelUtil<OrdSoTrace> util = new ExcelUtil<OrdSoTrace>(OrdSoTrace.class);
        util.exportExcel(response, list, "在制订单跟踪数据");
    }

    /**
     * 获取在制订单跟踪详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:soTrace:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordSoTraceService.selectOrdSoTraceById(id));
    }

    /**
     * 新增在制订单跟踪
     */
    //@PreAuthorize("@ss.hasPermi('ord:soTrace:add')")
    @Log(title = "在制订单跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdSoTrace ordSoTrace)
    {
        AjaxResult ar = null;
        try{
           int ret = ordSoTraceService.insertOrdSoTrace(ordSoTrace);
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
     * 修改在制订单跟踪
     */
    //@PreAuthorize("@ss.hasPermi('ord:soTrace:edit')")
    @Log(title = "在制订单跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdSoTrace ordSoTrace)
    {
        AjaxResult ar = null;
        try{
            int ret = ordSoTraceService.updateOrdSoTrace(ordSoTrace);
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
     * 删除在制订单跟踪
     */
    //@PreAuthorize("@ss.hasPermi('ord:soTrace:remove')")
    @Log(title = "在制订单跟踪", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordSoTraceService.deleteOrdSoTraceByIds(ids);
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

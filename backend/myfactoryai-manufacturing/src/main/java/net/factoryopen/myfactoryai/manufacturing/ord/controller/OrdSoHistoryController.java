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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoHistory;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单历史查询Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/soHistory")
public class OrdSoHistoryController extends BaseController
{
    @Autowired
    private IOrdSoHistoryService ordSoHistoryService;

    /**
     * 查询订单历史查询列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdSoHistory ordSoHistory)
    {
        startPage();
        List<OrdSoHistory> list = ordSoHistoryService.selectOrdSoHistoryList(ordSoHistory);
        return getDataTable(list);
    }

    /**
     * 导出订单历史查询列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soHistory:export')")
    @Log(title = "订单历史查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdSoHistory ordSoHistory)
    {
        List<OrdSoHistory> list = ordSoHistoryService.selectOrdSoHistoryList(ordSoHistory);
        ExcelUtil<OrdSoHistory> util = new ExcelUtil<OrdSoHistory>(OrdSoHistory.class);
        util.exportExcel(response, list, "订单历史查询数据");
    }

    /**
     * 获取订单历史查询详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:soHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordSoHistoryService.selectOrdSoHistoryById(id));
    }

    /**
     * 新增订单历史查询
     */
    //@PreAuthorize("@ss.hasPermi('ord:soHistory:add')")
    @Log(title = "订单历史查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdSoHistory ordSoHistory)
    {
        AjaxResult ar = null;
        try{
           int ret = ordSoHistoryService.insertOrdSoHistory(ordSoHistory);
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
     * 修改订单历史查询
     */
    //@PreAuthorize("@ss.hasPermi('ord:soHistory:edit')")
    @Log(title = "订单历史查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdSoHistory ordSoHistory)
    {
        AjaxResult ar = null;
        try{
            int ret = ordSoHistoryService.updateOrdSoHistory(ordSoHistory);
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
     * 删除订单历史查询
     */
    //@PreAuthorize("@ss.hasPermi('ord:soHistory:remove')")
    @Log(title = "订单历史查询", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordSoHistoryService.deleteOrdSoHistoryByIds(ids);
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

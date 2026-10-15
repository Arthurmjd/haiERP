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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingdrawing;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingdrawingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品工序图Controller
 *
 * @author admin
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productRoutingdrawing")
public class EngProductRoutingdrawingController extends BaseController
{
    @Autowired
    private IEngProductRoutingdrawingService engProductRoutingdrawingService;

    /**
     * 查询产品工序图列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingdrawing:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductRoutingdrawing engProductRoutingdrawing)
    {
        startPage();
        List<EngProductRoutingdrawing> list = engProductRoutingdrawingService.selectEngProductRoutingdrawingList(engProductRoutingdrawing);
        return getDataTable(list);
    }

    /**
     * 导出产品工序图列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingdrawing:export')")
    @Log(title = "产品工序图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductRoutingdrawing engProductRoutingdrawing)
    {
        List<EngProductRoutingdrawing> list = engProductRoutingdrawingService.selectEngProductRoutingdrawingList(engProductRoutingdrawing);
        ExcelUtil<EngProductRoutingdrawing> util = new ExcelUtil<EngProductRoutingdrawing>(EngProductRoutingdrawing.class);
        util.exportExcel(response, list, "产品工序图数据");
    }

    /**
     * 获取产品工序图详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingdrawing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductRoutingdrawingService.selectEngProductRoutingdrawingById(id));
    }

    /**
     * 新增产品工序图
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingdrawing:add')")
    @Log(title = "产品工序图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductRoutingdrawing engProductRoutingdrawing)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductRoutingdrawingService.insertEngProductRoutingdrawing(engProductRoutingdrawing);
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
     * 修改产品工序图
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingdrawing:edit')")
    @Log(title = "产品工序图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductRoutingdrawing engProductRoutingdrawing)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingdrawingService.updateEngProductRoutingdrawing(engProductRoutingdrawing);
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
     * 删除产品工序图
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingdrawing:remove')")
    @Log(title = "产品工序图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingdrawingService.deleteEngProductRoutingdrawingByIds(ids);
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

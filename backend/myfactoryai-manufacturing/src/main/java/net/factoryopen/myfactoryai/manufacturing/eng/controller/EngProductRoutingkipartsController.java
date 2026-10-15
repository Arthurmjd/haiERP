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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingkiparts;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingkipartsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品关重料Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productRoutingkiparts")
public class EngProductRoutingkipartsController extends BaseController
{
    @Autowired
    private IEngProductRoutingkipartsService engProductRoutingkipartsService;

    /**
     * 查询产品关重料列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingkiparts:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductRoutingkiparts engProductRoutingkiparts)
    {
        startPage();
        List<EngProductRoutingkiparts> list = engProductRoutingkipartsService.selectEngProductRoutingkipartsList(engProductRoutingkiparts);
        return getDataTable(list);
    }

    /**
     * 导出产品关重料列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingkiparts:export')")
    @Log(title = "产品关重料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductRoutingkiparts engProductRoutingkiparts)
    {
        List<EngProductRoutingkiparts> list = engProductRoutingkipartsService.selectEngProductRoutingkipartsList(engProductRoutingkiparts);
        ExcelUtil<EngProductRoutingkiparts> util = new ExcelUtil<EngProductRoutingkiparts>(EngProductRoutingkiparts.class);
        util.exportExcel(response, list, "产品关重料数据");
    }

    /**
     * 获取产品关重料详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingkiparts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductRoutingkipartsService.selectEngProductRoutingkipartsById(id));
    }

    /**
     * 新增产品关重料
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingkiparts:add')")
    @Log(title = "产品关重料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductRoutingkiparts engProductRoutingkiparts)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductRoutingkipartsService.insertEngProductRoutingkiparts(engProductRoutingkiparts);
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
     * 修改产品关重料
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingkiparts:edit')")
    @Log(title = "产品关重料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductRoutingkiparts engProductRoutingkiparts)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingkipartsService.updateEngProductRoutingkiparts(engProductRoutingkiparts);
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
     * 删除产品关重料
     */
    //@PreAuthorize("@ss.hasPermi('eng:productRoutingkiparts:remove')")
    @Log(title = "产品关重料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductRoutingkipartsService.deleteEngProductRoutingkipartsByIds(ids);
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

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductRoutingsop;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngPruductRoutingsopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品SOPController
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/pruductRoutingsop")
public class EngPruductRoutingsopController extends BaseController
{
    @Autowired
    private IEngPruductRoutingsopService engPruductRoutingsopService;

    /**
     * 查询产品SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductRoutingsop:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngPruductRoutingsop engPruductRoutingsop)
    {
        startPage();
        List<EngPruductRoutingsop> list = engPruductRoutingsopService.selectEngPruductRoutingsopList(engPruductRoutingsop);
        return getDataTable(list);
    }

    /**
     * 导出产品SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductRoutingsop:export')")
    @Log(title = "产品SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngPruductRoutingsop engPruductRoutingsop)
    {
        List<EngPruductRoutingsop> list = engPruductRoutingsopService.selectEngPruductRoutingsopList(engPruductRoutingsop);
        ExcelUtil<EngPruductRoutingsop> util = new ExcelUtil<EngPruductRoutingsop>(EngPruductRoutingsop.class);
        util.exportExcel(response, list, "产品SOP数据");
    }

    /**
     * 获取产品SOP详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductRoutingsop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engPruductRoutingsopService.selectEngPruductRoutingsopById(id));
    }

    /**
     * 新增产品SOP
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductRoutingsop:add')")
    @Log(title = "产品SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngPruductRoutingsop engPruductRoutingsop)
    {
        AjaxResult ar = null;
        try{
           int ret = engPruductRoutingsopService.insertEngPruductRoutingsop(engPruductRoutingsop);
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
     * 修改产品SOP
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductRoutingsop:edit')")
    @Log(title = "产品SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngPruductRoutingsop engPruductRoutingsop)
    {
        AjaxResult ar = null;
        try{
            int ret = engPruductRoutingsopService.updateEngPruductRoutingsop(engPruductRoutingsop);
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
     * 删除产品SOP
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductRoutingsop:remove')")
    @Log(title = "产品SOP", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engPruductRoutingsopService.deleteEngPruductRoutingsopByIds(ids);
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

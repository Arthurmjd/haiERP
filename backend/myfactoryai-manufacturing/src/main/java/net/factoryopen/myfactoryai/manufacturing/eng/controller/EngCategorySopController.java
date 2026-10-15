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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategorySop;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategorySopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品类SOPController
 *
 * @author admin
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/eng/categorySop")
public class EngCategorySopController extends BaseController
{
    @Autowired
    private IEngCategorySopService engCategorySopService;

    /**
     * 查询品类SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categorySop:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngCategorySop engCategorySop)
    {
        startPage();
        List<EngCategorySop> list = engCategorySopService.selectEngCategorySopList(engCategorySop);
        return getDataTable(list);
    }

    /**
     * 导出品类SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categorySop:export')")
    @Log(title = "品类SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngCategorySop engCategorySop)
    {
        List<EngCategorySop> list = engCategorySopService.selectEngCategorySopList(engCategorySop);
        ExcelUtil<EngCategorySop> util = new ExcelUtil<EngCategorySop>(EngCategorySop.class);
        util.exportExcel(response, list, "品类SOP数据");
    }

    /**
     * 获取品类SOP详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:categorySop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engCategorySopService.selectEngCategorySopById(id));
    }

    /**
     * 新增品类SOP
     */
    //@PreAuthorize("@ss.hasPermi('eng:categorySop:add')")
    @Log(title = "品类SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngCategorySop engCategorySop)
    {
        AjaxResult ar = null;
        try{
           int ret = engCategorySopService.insertEngCategorySop(engCategorySop);
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
     * 修改品类SOP
     */
    //@PreAuthorize("@ss.hasPermi('eng:categorySop:edit')")
    @Log(title = "品类SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngCategorySop engCategorySop)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategorySopService.updateEngCategorySop(engCategorySop);
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
     * 删除品类SOP
     */
    //@PreAuthorize("@ss.hasPermi('eng:categorySop:remove')")
    @Log(title = "品类SOP", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategorySopService.deleteEngCategorySopByIds(ids);
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

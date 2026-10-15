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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductDrawing;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductDrawingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品图纸Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productDrawing")
public class EngProductDrawingController extends BaseController
{
    @Autowired
    private IEngProductDrawingService engProductDrawingService;

    /**
     * 查询产品图纸列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productDrawing:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductDrawing engProductDrawing)
    {
        startPage();
        List<EngProductDrawing> list = engProductDrawingService.selectEngProductDrawingList(engProductDrawing);
        return getDataTable(list);
    }

    /**
     * 导出产品图纸列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productDrawing:export')")
    @Log(title = "产品图纸", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductDrawing engProductDrawing)
    {
        List<EngProductDrawing> list = engProductDrawingService.selectEngProductDrawingList(engProductDrawing);
        ExcelUtil<EngProductDrawing> util = new ExcelUtil<EngProductDrawing>(EngProductDrawing.class);
        util.exportExcel(response, list, "产品图纸数据");
    }

    /**
     * 获取产品图纸详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productDrawing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductDrawingService.selectEngProductDrawingById(id));
    }

    /**
     * 新增产品图纸
     */
    //@PreAuthorize("@ss.hasPermi('eng:productDrawing:add')")
    @Log(title = "产品图纸", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductDrawing engProductDrawing)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductDrawingService.insertEngProductDrawing(engProductDrawing);
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
     * 修改产品图纸
     */
    //@PreAuthorize("@ss.hasPermi('eng:productDrawing:edit')")
    @Log(title = "产品图纸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductDrawing engProductDrawing)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductDrawingService.updateEngProductDrawing(engProductDrawing);
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
     * 删除产品图纸
     */
    //@PreAuthorize("@ss.hasPermi('eng:productDrawing:remove')")
    @Log(title = "产品图纸", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductDrawingService.deleteEngProductDrawingByIds(ids);
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

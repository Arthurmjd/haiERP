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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlDrawingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次图纸Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlDrawing")
public class PlnMlDrawingController extends BaseController
{
    @Autowired
    private IPlnMlDrawingService plnMlDrawingService;

    /**
     * 查询批次图纸列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDrawing:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlDrawing plnMlDrawing)
    {
        startPage();
        List<PlnMlDrawing> list = plnMlDrawingService.selectPlnMlDrawingList(plnMlDrawing);
        return getDataTable(list);
    }

    /**
     * 导出批次图纸列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDrawing:export')")
    @Log(title = "批次图纸", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlDrawing plnMlDrawing)
    {
        List<PlnMlDrawing> list = plnMlDrawingService.selectPlnMlDrawingList(plnMlDrawing);
        ExcelUtil<PlnMlDrawing> util = new ExcelUtil<PlnMlDrawing>(PlnMlDrawing.class);
        util.exportExcel(response, list, "批次图纸数据");
    }

    /**
     * 获取批次图纸详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDrawing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlDrawingService.selectPlnMlDrawingById(id));
    }

    /**
     * 新增批次图纸
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDrawing:add')")
    @Log(title = "批次图纸", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlDrawing plnMlDrawing)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlDrawingService.insertPlnMlDrawing(plnMlDrawing);
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
     * 修改批次图纸
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDrawing:edit')")
    @Log(title = "批次图纸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlDrawing plnMlDrawing)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlDrawingService.updatePlnMlDrawing(plnMlDrawing);
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
     * 删除批次图纸
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDrawing:remove')")
    @Log(title = "批次图纸", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlDrawingService.deletePlnMlDrawingByIds(ids);
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

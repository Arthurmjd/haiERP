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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialReplacable;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialReplacableService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料替代Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/eng/materialReplacable")
public class EngMaterialReplacableController extends BaseController
{
    @Autowired
    private IEngMaterialReplacableService engMaterialReplacableService;

    /**
     * 查询物料替代列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialReplacable:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngMaterialReplacable engMaterialReplacable)
    {
        startPage();
        List<EngMaterialReplacable> list = engMaterialReplacableService.selectEngMaterialReplacableList(engMaterialReplacable);
        return getDataTable(list);
    }

    /**
     * 导出物料替代列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialReplacable:export')")
    @Log(title = "物料替代", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngMaterialReplacable engMaterialReplacable)
    {
        List<EngMaterialReplacable> list = engMaterialReplacableService.selectEngMaterialReplacableList(engMaterialReplacable);
        ExcelUtil<EngMaterialReplacable> util = new ExcelUtil<EngMaterialReplacable>(EngMaterialReplacable.class);
        util.exportExcel(response, list, "物料替代数据");
    }

    /**
     * 获取物料替代详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialReplacable:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engMaterialReplacableService.selectEngMaterialReplacableById(id));
    }

    /**
     * 新增物料替代
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialReplacable:add')")
    @Log(title = "物料替代", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngMaterialReplacable engMaterialReplacable)
    {
        AjaxResult ar = null;
        try{
           int ret = engMaterialReplacableService.insertEngMaterialReplacable(engMaterialReplacable);
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
     * 修改物料替代
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialReplacable:edit')")
    @Log(title = "物料替代", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngMaterialReplacable engMaterialReplacable)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialReplacableService.updateEngMaterialReplacable(engMaterialReplacable);
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
     * 删除物料替代
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialReplacable:remove')")
    @Log(title = "物料替代", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialReplacableService.deleteEngMaterialReplacableByIds(ids);
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

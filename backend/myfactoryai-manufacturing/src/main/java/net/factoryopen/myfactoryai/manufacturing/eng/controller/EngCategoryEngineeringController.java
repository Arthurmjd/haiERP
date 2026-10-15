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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryEngineeringService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品类工艺Controller
 *
 * @author jitfactory-generator
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/eng/categoryEngineering")
public class EngCategoryEngineeringController extends BaseController
{
    @Autowired
    private IEngCategoryEngineeringService engCategoryEngineeringService;

    /**
     * 查询品类工艺列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngCategoryEngineering engCategoryEngineering)
    {
        startPage();
        List<EngCategoryEngineering> list = engCategoryEngineeringService.selectEngCategoryEngineeringList(engCategoryEngineering);
        return getDataTable(list);
    }

    /**
     * 导出品类工艺列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:export')")
    @Log(title = "品类工艺", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngCategoryEngineering engCategoryEngineering)
    {
        List<EngCategoryEngineering> list = engCategoryEngineeringService.selectEngCategoryEngineeringList(engCategoryEngineering);
        ExcelUtil<EngCategoryEngineering> util = new ExcelUtil<EngCategoryEngineering>(EngCategoryEngineering.class);
        util.exportExcel(response, list, "品类工艺数据");
    }

    /**
     * 获取品类工艺详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engCategoryEngineeringService.selectEngCategoryEngineeringById(id));
    }

    /**
     * 新增品类工艺
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:add')")
    @Log(title = "品类工艺", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngCategoryEngineering engCategoryEngineering)
    {
        AjaxResult ar = null;
        try{
           int ret = engCategoryEngineeringService.insertEngCategoryEngineering(engCategoryEngineering);
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
     * 修改品类工艺
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:edit')")
    @Log(title = "品类工艺", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngCategoryEngineering engCategoryEngineering)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryEngineeringService.updateEngCategoryEngineering(engCategoryEngineering);
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
     * 删除品类工艺
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:remove')")
    @Log(title = "品类工艺", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryEngineeringService.deleteEngCategoryEngineeringByIds(ids);
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

    /**
     * 填补子孙
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:fillChildren')")
    @Log(title = "填补子孙", businessType = BusinessType.OTHER)
    @PutMapping("/fillChildren")
    public AjaxResult fillChildren(@RequestBody EngCategoryEngineering engCategoryEngineering)
    {
        AjaxResult ar = null;
        try{
            engCategoryEngineeringService.fillChildren(engCategoryEngineering);
            ar = AjaxResult.success("操作成功：[填补子孙]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 覆盖子孙
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryEngineering:coverChildren')")
    @Log(title = "覆盖子孙", businessType = BusinessType.OTHER)
    @PutMapping("/coverChildren")
    public AjaxResult coverChildren(@RequestBody EngCategoryEngineering engCategoryEngineering)
    {
        AjaxResult ar = null;
        try{
            engCategoryEngineeringService.coverChildren(engCategoryEngineering);
            ar = AjaxResult.success("操作成功：[覆盖子孙]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}

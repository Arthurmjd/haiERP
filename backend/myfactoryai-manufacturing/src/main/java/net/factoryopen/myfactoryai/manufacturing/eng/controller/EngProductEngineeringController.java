package net.factoryopen.myfactoryai.manufacturing.eng.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductEngineeringService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品工艺管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productEngineering")
public class EngProductEngineeringController extends BaseController
{
    @Autowired
    private IEngProductEngineeringService engProductEngineeringService;

    /**
     * 查询产品工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductEngineering engProductEngineering)
    {
        startPage();
        List<EngProductEngineering> list = engProductEngineeringService.selectEngProductEngineeringList(engProductEngineering);
        return getDataTable(list);
    }

    /**
     * 导出产品工艺管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:export')")
    @Log(title = "产品工艺管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductEngineering engProductEngineering)
    {
        List<EngProductEngineering> list = engProductEngineeringService.selectEngProductEngineeringList(engProductEngineering);
        ExcelUtil<EngProductEngineering> util = new ExcelUtil<EngProductEngineering>(EngProductEngineering.class);
        util.exportExcel(response, list, "产品工艺管理数据");
    }

    /**
     * 获取产品工艺管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductEngineeringService.selectEngProductEngineeringById(id));
    }

    /**
     * 新增产品工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:add')")
    @Log(title = "产品工艺管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductEngineering engProductEngineering)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductEngineeringService.insertEngProductEngineering(engProductEngineering);
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
     * 修改产品工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:edit')")
    @Log(title = "产品工艺管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductEngineering engProductEngineering)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductEngineeringService.updateEngProductEngineering(engProductEngineering);
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
     * 删除产品工艺管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:remove')")
    @Log(title = "产品工艺管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductEngineeringService.deleteEngProductEngineeringByIds(ids);
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
     * 继承工艺
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:inheritEngineering')")
    @Log(title = "继承工艺", businessType = BusinessType.OTHER)
    @GetMapping("/inheritEngineering/{id}")
    public AjaxResult inheritEngineering(@PathVariable("id") Long productId)
    {
        AjaxResult ar = null;
        try{
            engProductEngineeringService.inheritEngineering(productId);
            ar = AjaxResult.success("操作成功：[填补子孙]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 生成采购料号
     */
    //@PreAuthorize("@ss.hasPermi('eng:productEngineering:generateCode')")
    @Log(title = "生成料号", businessType = BusinessType.OTHER)
    @PutMapping("/generateCode")
    public AjaxResult generateCode(@RequestBody EngProductBom bom)
    {
        AjaxResult ar = null;
        try{
            engProductEngineeringService.generateCode(bom);
            ar = AjaxResult.success("操作成功：[生成料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }


}

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryBom;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品类BOMController
 *
 * @author admin
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/eng/categoryBom")
public class EngCategoryBomController extends BaseController
{
    @Autowired
    private IEngCategoryBomService engCategoryBomService;

    /**
     * 查询品类BOM列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryBom:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngCategoryBom engCategoryBom)
    {
        if(engCategoryBom!=null && engCategoryBom.getPageEnable()!=null && engCategoryBom.getPageEnable()) startPage();
        List<EngCategoryBom> list = engCategoryBomService.selectEngCategoryBomList(engCategoryBom);
        return getDataTable(list);
    }

    /**
     * 导出品类BOM列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryBom:export')")
    @Log(title = "品类BOM", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngCategoryBom engCategoryBom)
    {
        List<EngCategoryBom> list = engCategoryBomService.selectEngCategoryBomList(engCategoryBom);
        ExcelUtil<EngCategoryBom> util = new ExcelUtil<EngCategoryBom>(EngCategoryBom.class);
        util.exportExcel(response, list, "品类BOM数据");
    }

    /**
     * 获取品类BOM详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryBom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engCategoryBomService.selectEngCategoryBomById(id));
    }

    /**
     * 新增品类BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryBom:add')")
    @Log(title = "品类BOM", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngCategoryBom engCategoryBom)
    {
        AjaxResult ar = null;
        try{
           int ret = engCategoryBomService.insertEngCategoryBom(engCategoryBom);
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
     * 修改品类BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryBom:edit')")
    @Log(title = "品类BOM", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngCategoryBom engCategoryBom)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryBomService.updateEngCategoryBom(engCategoryBom);
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
     * 删除品类BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryBom:remove')")
    @Log(title = "品类BOM", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryBomService.deleteEngCategoryBomByIds(ids);
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

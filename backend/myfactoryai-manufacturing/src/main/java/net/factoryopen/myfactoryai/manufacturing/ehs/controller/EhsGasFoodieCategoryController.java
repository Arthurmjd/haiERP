package net.factoryopen.myfactoryai.manufacturing.ehs.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasFoodieCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsGasFoodieCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 气耗设备分类Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/gasFoodieCategory")
public class EhsGasFoodieCategoryController extends BaseController
{
    @Autowired
    private IEhsGasFoodieCategoryService ehsGasFoodieCategoryService;

    /**
     * 查询气耗设备分类列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodieCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        startPage();
        List<EhsGasFoodieCategory> list = ehsGasFoodieCategoryService.selectEhsGasFoodieCategoryList(ehsGasFoodieCategory);
        return getDataTable(list);
    }

    /**
     * 导出气耗设备分类列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodieCategory:export')")
    @Log(title = "气耗设备分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        List<EhsGasFoodieCategory> list = ehsGasFoodieCategoryService.selectEhsGasFoodieCategoryList(ehsGasFoodieCategory);
        ExcelUtil<EhsGasFoodieCategory> util = new ExcelUtil<EhsGasFoodieCategory>(EhsGasFoodieCategory.class);
        util.exportExcel(response, list, "气耗设备分类数据");
    }

    /**
     * 获取气耗设备分类详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodieCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsGasFoodieCategoryService.selectEhsGasFoodieCategoryById(id));
    }

    /**
     * 新增气耗设备分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodieCategory:add')")
    @Log(title = "气耗设备分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsGasFoodieCategoryService.insertEhsGasFoodieCategory(ehsGasFoodieCategory);
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
     * 修改气耗设备分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodieCategory:edit')")
    @Log(title = "气耗设备分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsGasFoodieCategoryService.updateEhsGasFoodieCategory(ehsGasFoodieCategory);
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
     * 删除气耗设备分类
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodieCategory:remove')")
    @Log(title = "气耗设备分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsGasFoodieCategoryService.deleteEhsGasFoodieCategoryByIds(ids);
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

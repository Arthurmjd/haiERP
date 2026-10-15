package net.factoryopen.myfactoryai.manufacturing.sup.controller;

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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplier;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/sup/supplier")
public class SupSupplierController extends BaseController
{
    @Autowired
    private ISupSupplierService supSupplierService;

    /**
     * 查询供应商管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupSupplier supSupplier)
    {
        if(supSupplier!=null && supSupplier.getPageEnable()!=null && supSupplier.getPageEnable()) startPage();
        List<SupSupplier> list = supSupplierService.selectSupSupplierList(supSupplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplier:export')")
    @Log(title = "供应商管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupSupplier supSupplier)
    {
        List<SupSupplier> list = supSupplierService.selectSupSupplierList(supSupplier);
        ExcelUtil<SupSupplier> util = new ExcelUtil<SupSupplier>(SupSupplier.class);
        util.exportExcel(response, list, "供应商管理数据");
    }

    /**
     * 获取供应商管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supSupplierService.selectSupSupplierById(id));
    }

    /**
     * 新增供应商管理
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplier:add')")
    @Log(title = "供应商管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupSupplier supSupplier)
    {
        AjaxResult ar = null;
        try{
           int ret = supSupplierService.insertSupSupplier(supSupplier);
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
     * 修改供应商管理
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplier:edit')")
    @Log(title = "供应商管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupSupplier supSupplier)
    {
        AjaxResult ar = null;
        try{
            int ret = supSupplierService.updateSupSupplier(supSupplier);
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
     * 删除供应商管理
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplier:remove')")
    @Log(title = "供应商管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supSupplierService.deleteSupSupplierByIds(ids);
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

package net.factoryopen.myfactoryai.manufacturing.del.controller;

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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplier;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外协厂商管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/del/supplier")
public class DelSupplierController extends BaseController
{
    @Autowired
    private IDelSupplierService delSupplierService;

    /**
     * 查询外协厂商管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelSupplier delSupplier)
    {
        startPage();
        List<DelSupplier> list = delSupplierService.selectDelSupplierList(delSupplier);
        return getDataTable(list);
    }

    /**
     * 导出外协厂商管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplier:export')")
    @Log(title = "外协厂商管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelSupplier delSupplier)
    {
        List<DelSupplier> list = delSupplierService.selectDelSupplierList(delSupplier);
        ExcelUtil<DelSupplier> util = new ExcelUtil<DelSupplier>(DelSupplier.class);
        util.exportExcel(response, list, "外协厂商管理数据");
    }

    /**
     * 获取外协厂商管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:supplier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delSupplierService.selectDelSupplierById(id));
    }

    /**
     * 新增外协厂商管理
     */
    //@PreAuthorize("@ss.hasPermi('del:supplier:add')")
    @Log(title = "外协厂商管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelSupplier delSupplier)
    {
        AjaxResult ar = null;
        try{
           int ret = delSupplierService.insertDelSupplier(delSupplier);
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
     * 修改外协厂商管理
     */
    //@PreAuthorize("@ss.hasPermi('del:supplier:edit')")
    @Log(title = "外协厂商管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelSupplier delSupplier)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierService.updateDelSupplier(delSupplier);
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
     * 删除外协厂商管理
     */
    //@PreAuthorize("@ss.hasPermi('del:supplier:remove')")
    @Log(title = "外协厂商管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierService.deleteDelSupplierByIds(ids);
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

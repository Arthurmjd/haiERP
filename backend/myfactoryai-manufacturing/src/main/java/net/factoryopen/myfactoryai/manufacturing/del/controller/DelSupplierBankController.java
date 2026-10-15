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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierBank;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierBankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商账号Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/del/supplierBank")
public class DelSupplierBankController extends BaseController
{
    @Autowired
    private IDelSupplierBankService delSupplierBankService;

    /**
     * 查询供应商账号列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierBank:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelSupplierBank delSupplierBank)
    {
        startPage();
        List<DelSupplierBank> list = delSupplierBankService.selectDelSupplierBankList(delSupplierBank);
        return getDataTable(list);
    }

    /**
     * 导出供应商账号列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierBank:export')")
    @Log(title = "供应商账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelSupplierBank delSupplierBank)
    {
        List<DelSupplierBank> list = delSupplierBankService.selectDelSupplierBankList(delSupplierBank);
        ExcelUtil<DelSupplierBank> util = new ExcelUtil<DelSupplierBank>(DelSupplierBank.class);
        util.exportExcel(response, list, "供应商账号数据");
    }

    /**
     * 获取供应商账号详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierBank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delSupplierBankService.selectDelSupplierBankById(id));
    }

    /**
     * 新增供应商账号
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierBank:add')")
    @Log(title = "供应商账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelSupplierBank delSupplierBank)
    {
        AjaxResult ar = null;
        try{
           int ret = delSupplierBankService.insertDelSupplierBank(delSupplierBank);
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
     * 修改供应商账号
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierBank:edit')")
    @Log(title = "供应商账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelSupplierBank delSupplierBank)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierBankService.updateDelSupplierBank(delSupplierBank);
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
     * 删除供应商账号
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierBank:remove')")
    @Log(title = "供应商账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierBankService.deleteDelSupplierBankByIds(ids);
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

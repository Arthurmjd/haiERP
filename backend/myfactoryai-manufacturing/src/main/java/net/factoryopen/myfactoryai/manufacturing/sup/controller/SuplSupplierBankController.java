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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SuplSupplierBank;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISuplSupplierBankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商账号Controller
 *
 * @author admin
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/sup/supplierBank")
public class SuplSupplierBankController extends BaseController
{
    @Autowired
    private ISuplSupplierBankService suplSupplierBankService;

    /**
     * 查询供应商账号列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierBank:list')")
    @GetMapping("/list")
    public TableDataInfo list(SuplSupplierBank suplSupplierBank)
    {
        startPage();
        List<SuplSupplierBank> list = suplSupplierBankService.selectSuplSupplierBankList(suplSupplierBank);
        return getDataTable(list);
    }

    /**
     * 导出供应商账号列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierBank:export')")
    @Log(title = "供应商账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SuplSupplierBank suplSupplierBank)
    {
        List<SuplSupplierBank> list = suplSupplierBankService.selectSuplSupplierBankList(suplSupplierBank);
        ExcelUtil<SuplSupplierBank> util = new ExcelUtil<SuplSupplierBank>(SuplSupplierBank.class);
        util.exportExcel(response, list, "供应商账号数据");
    }

    /**
     * 获取供应商账号详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierBank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(suplSupplierBankService.selectSuplSupplierBankById(id));
    }

    /**
     * 新增供应商账号
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierBank:add')")
    @Log(title = "供应商账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SuplSupplierBank suplSupplierBank)
    {
        AjaxResult ar = null;
        try{
           int ret = suplSupplierBankService.insertSuplSupplierBank(suplSupplierBank);
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
    //@PreAuthorize("@ss.hasPermi('sup:supplierBank:edit')")
    @Log(title = "供应商账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SuplSupplierBank suplSupplierBank)
    {
        AjaxResult ar = null;
        try{
            int ret = suplSupplierBankService.updateSuplSupplierBank(suplSupplierBank);
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
    //@PreAuthorize("@ss.hasPermi('sup:supplierBank:remove')")
    @Log(title = "供应商账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = suplSupplierBankService.deleteSuplSupplierBankByIds(ids);
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

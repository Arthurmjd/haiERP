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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierAddress;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商地址Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/del/supplierAddress")
public class DelSupplierAddressController extends BaseController
{
    @Autowired
    private IDelSupplierAddressService delSupplierAddressService;

    /**
     * 查询供应商地址列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierAddress:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelSupplierAddress delSupplierAddress)
    {
        startPage();
        List<DelSupplierAddress> list = delSupplierAddressService.selectDelSupplierAddressList(delSupplierAddress);
        return getDataTable(list);
    }

    /**
     * 导出供应商地址列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierAddress:export')")
    @Log(title = "供应商地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelSupplierAddress delSupplierAddress)
    {
        List<DelSupplierAddress> list = delSupplierAddressService.selectDelSupplierAddressList(delSupplierAddress);
        ExcelUtil<DelSupplierAddress> util = new ExcelUtil<DelSupplierAddress>(DelSupplierAddress.class);
        util.exportExcel(response, list, "供应商地址数据");
    }

    /**
     * 获取供应商地址详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierAddress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delSupplierAddressService.selectDelSupplierAddressById(id));
    }

    /**
     * 新增供应商地址
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierAddress:add')")
    @Log(title = "供应商地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelSupplierAddress delSupplierAddress)
    {
        AjaxResult ar = null;
        try{
           int ret = delSupplierAddressService.insertDelSupplierAddress(delSupplierAddress);
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
     * 修改供应商地址
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierAddress:edit')")
    @Log(title = "供应商地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelSupplierAddress delSupplierAddress)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierAddressService.updateDelSupplierAddress(delSupplierAddress);
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
     * 删除供应商地址
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierAddress:remove')")
    @Log(title = "供应商地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierAddressService.deleteDelSupplierAddressByIds(ids);
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

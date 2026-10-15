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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierContact;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierContactService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商人员Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/del/supplierContact")
public class DelSupplierContactController extends BaseController
{
    @Autowired
    private IDelSupplierContactService delSupplierContactService;

    /**
     * 查询供应商人员列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierContact:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelSupplierContact delSupplierContact)
    {
        startPage();
        List<DelSupplierContact> list = delSupplierContactService.selectDelSupplierContactList(delSupplierContact);
        return getDataTable(list);
    }

    /**
     * 导出供应商人员列表
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierContact:export')")
    @Log(title = "供应商人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelSupplierContact delSupplierContact)
    {
        List<DelSupplierContact> list = delSupplierContactService.selectDelSupplierContactList(delSupplierContact);
        ExcelUtil<DelSupplierContact> util = new ExcelUtil<DelSupplierContact>(DelSupplierContact.class);
        util.exportExcel(response, list, "供应商人员数据");
    }

    /**
     * 获取供应商人员详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierContact:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delSupplierContactService.selectDelSupplierContactById(id));
    }

    /**
     * 新增供应商人员
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierContact:add')")
    @Log(title = "供应商人员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelSupplierContact delSupplierContact)
    {
        AjaxResult ar = null;
        try{
           int ret = delSupplierContactService.insertDelSupplierContact(delSupplierContact);
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
     * 修改供应商人员
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierContact:edit')")
    @Log(title = "供应商人员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelSupplierContact delSupplierContact)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierContactService.updateDelSupplierContact(delSupplierContact);
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
     * 删除供应商人员
     */
    //@PreAuthorize("@ss.hasPermi('del:supplierContact:remove')")
    @Log(title = "供应商人员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delSupplierContactService.deleteDelSupplierContactByIds(ids);
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

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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierContact;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierContactService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商人员Controller
 *
 * @author admin
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/sup/supplierContact")
public class SupSupplierContactController extends BaseController
{
    @Autowired
    private ISupSupplierContactService supSupplierContactService;

    /**
     * 查询供应商人员列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierContact:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupSupplierContact supSupplierContact)
    {
        startPage();
        List<SupSupplierContact> list = supSupplierContactService.selectSupSupplierContactList(supSupplierContact);
        return getDataTable(list);
    }

    /**
     * 导出供应商人员列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierContact:export')")
    @Log(title = "供应商人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupSupplierContact supSupplierContact)
    {
        List<SupSupplierContact> list = supSupplierContactService.selectSupSupplierContactList(supSupplierContact);
        ExcelUtil<SupSupplierContact> util = new ExcelUtil<SupSupplierContact>(SupSupplierContact.class);
        util.exportExcel(response, list, "供应商人员数据");
    }

    /**
     * 获取供应商人员详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierContact:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supSupplierContactService.selectSupSupplierContactById(id));
    }

    /**
     * 新增供应商人员
     */
    //@PreAuthorize("@ss.hasPermi('sup:supplierContact:add')")
    @Log(title = "供应商人员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupSupplierContact supSupplierContact)
    {
        AjaxResult ar = null;
        try{
           int ret = supSupplierContactService.insertSupSupplierContact(supSupplierContact);
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
    //@PreAuthorize("@ss.hasPermi('sup:supplierContact:edit')")
    @Log(title = "供应商人员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupSupplierContact supSupplierContact)
    {
        AjaxResult ar = null;
        try{
            int ret = supSupplierContactService.updateSupSupplierContact(supSupplierContact);
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
    //@PreAuthorize("@ss.hasPermi('sup:supplierContact:remove')")
    @Log(title = "供应商人员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supSupplierContactService.deleteSupSupplierContactByIds(ids);
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

package net.factoryopen.myfactoryai.manufacturing.cha.controller;

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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerContact;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerContactService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户人员Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/cha/customerContact")
public class ChaCustomerContactController extends BaseController
{
    @Autowired
    private IChaCustomerContactService chaCustomerContactService;

    /**
     * 查询客户人员列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerContact:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaCustomerContact chaCustomerContact)
    {
        startPage();
        List<ChaCustomerContact> list = chaCustomerContactService.selectChaCustomerContactList(chaCustomerContact);
        return getDataTable(list);
    }

    /**
     * 导出客户人员列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerContact:export')")
    @Log(title = "客户人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaCustomerContact chaCustomerContact)
    {
        List<ChaCustomerContact> list = chaCustomerContactService.selectChaCustomerContactList(chaCustomerContact);
        ExcelUtil<ChaCustomerContact> util = new ExcelUtil<ChaCustomerContact>(ChaCustomerContact.class);
        util.exportExcel(response, list, "客户人员数据");
    }

    /**
     * 获取客户人员详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerContact:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaCustomerContactService.selectChaCustomerContactById(id));
    }

    /**
     * 新增客户人员
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerContact:add')")
    @Log(title = "客户人员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaCustomerContact chaCustomerContact)
    {
        AjaxResult ar = null;
        try{
           int ret = chaCustomerContactService.insertChaCustomerContact(chaCustomerContact);
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
     * 修改客户人员
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerContact:edit')")
    @Log(title = "客户人员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaCustomerContact chaCustomerContact)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerContactService.updateChaCustomerContact(chaCustomerContact);
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
     * 删除客户人员
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerContact:remove')")
    @Log(title = "客户人员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerContactService.deleteChaCustomerContactByIds(ids);
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

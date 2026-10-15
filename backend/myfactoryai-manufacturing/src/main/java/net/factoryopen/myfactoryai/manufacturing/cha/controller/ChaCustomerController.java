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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户数据管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/cha/customer")
public class ChaCustomerController extends BaseController
{
    @Autowired
    private IChaCustomerService chaCustomerService;

    /**
     * 查询客户数据管理列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaCustomer chaCustomer)
    {
        if(chaCustomer!=null && chaCustomer.getPageEnable()!=null && chaCustomer.getPageEnable()) startPage();
        List<ChaCustomer> list = chaCustomerService.selectChaCustomerList(chaCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户数据管理列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customer:export')")
    @Log(title = "客户数据管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaCustomer chaCustomer)
    {
        List<ChaCustomer> list = chaCustomerService.selectChaCustomerList(chaCustomer);
        ExcelUtil<ChaCustomer> util = new ExcelUtil<ChaCustomer>(ChaCustomer.class);
        util.exportExcel(response, list, "客户数据管理数据");
    }

    /**
     * 获取客户数据管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaCustomerService.selectChaCustomerById(id));
    }

    /**
     * 新增客户数据管理
     */
    //@PreAuthorize("@ss.hasPermi('cha:customer:add')")
    @Log(title = "客户数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaCustomer chaCustomer)
    {
        AjaxResult ar = null;
        try{
           int ret = chaCustomerService.insertChaCustomer(chaCustomer);
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
     * 修改客户数据管理
     */
    //@PreAuthorize("@ss.hasPermi('cha:customer:edit')")
    @Log(title = "客户数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaCustomer chaCustomer)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerService.updateChaCustomer(chaCustomer);
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
     * 删除客户数据管理
     */
    //@PreAuthorize("@ss.hasPermi('cha:customer:remove')")
    @Log(title = "客户数据管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerService.deleteChaCustomerByIds(ids);
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

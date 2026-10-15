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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerBank;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerBankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户账号Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/cha/customerBank")
public class ChaCustomerBankController extends BaseController
{
    @Autowired
    private IChaCustomerBankService chaCustomerBankService;

    /**
     * 查询客户账号列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerBank:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaCustomerBank chaCustomerBank)
    {
        startPage();
        List<ChaCustomerBank> list = chaCustomerBankService.selectChaCustomerBankList(chaCustomerBank);
        return getDataTable(list);
    }

    /**
     * 导出客户账号列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerBank:export')")
    @Log(title = "客户账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaCustomerBank chaCustomerBank)
    {
        List<ChaCustomerBank> list = chaCustomerBankService.selectChaCustomerBankList(chaCustomerBank);
        ExcelUtil<ChaCustomerBank> util = new ExcelUtil<ChaCustomerBank>(ChaCustomerBank.class);
        util.exportExcel(response, list, "客户账号数据");
    }

    /**
     * 获取客户账号详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerBank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaCustomerBankService.selectChaCustomerBankById(id));
    }

    /**
     * 新增客户账号
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerBank:add')")
    @Log(title = "客户账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaCustomerBank chaCustomerBank)
    {
        AjaxResult ar = null;
        try{
           int ret = chaCustomerBankService.insertChaCustomerBank(chaCustomerBank);
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
     * 修改客户账号
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerBank:edit')")
    @Log(title = "客户账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaCustomerBank chaCustomerBank)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerBankService.updateChaCustomerBank(chaCustomerBank);
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
     * 删除客户账号
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerBank:remove')")
    @Log(title = "客户账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerBankService.deleteChaCustomerBankByIds(ids);
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

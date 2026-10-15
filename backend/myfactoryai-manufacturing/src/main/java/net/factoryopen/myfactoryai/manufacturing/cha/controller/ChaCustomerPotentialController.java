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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerPotential;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerPotentialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 潜客备案Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/cha/customerPotential")
public class ChaCustomerPotentialController extends BaseController
{
    @Autowired
    private IChaCustomerPotentialService chaCustomerPotentialService;

    /**
     * 查询潜客备案列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerPotential:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaCustomerPotential chaCustomerPotential)
    {
        startPage();
        List<ChaCustomerPotential> list = chaCustomerPotentialService.selectChaCustomerPotentialList(chaCustomerPotential);
        return getDataTable(list);
    }

    /**
     * 导出潜客备案列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerPotential:export')")
    @Log(title = "潜客备案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaCustomerPotential chaCustomerPotential)
    {
        List<ChaCustomerPotential> list = chaCustomerPotentialService.selectChaCustomerPotentialList(chaCustomerPotential);
        ExcelUtil<ChaCustomerPotential> util = new ExcelUtil<ChaCustomerPotential>(ChaCustomerPotential.class);
        util.exportExcel(response, list, "潜客备案数据");
    }

    /**
     * 获取潜客备案详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerPotential:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaCustomerPotentialService.selectChaCustomerPotentialById(id));
    }

    /**
     * 新增潜客备案
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerPotential:add')")
    @Log(title = "潜客备案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaCustomerPotential chaCustomerPotential)
    {
        AjaxResult ar = null;
        try{
           int ret = chaCustomerPotentialService.insertChaCustomerPotential(chaCustomerPotential);
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
     * 修改潜客备案
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerPotential:edit')")
    @Log(title = "潜客备案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaCustomerPotential chaCustomerPotential)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerPotentialService.updateChaCustomerPotential(chaCustomerPotential);
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
     * 删除潜客备案
     */
    //@PreAuthorize("@ss.hasPermi('cha:customerPotential:remove')")
    @Log(title = "潜客备案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaCustomerPotentialService.deleteChaCustomerPotentialByIds(ids);
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

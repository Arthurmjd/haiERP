package net.factoryopen.myfactoryai.manufacturing.qua.controller;

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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectPurchasing;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectPurchasingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外购检异登记Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/qua/inspectPurchasing")
public class QuaInspectPurchasingController extends BaseController
{
    @Autowired
    private IQuaInspectPurchasingService quaInspectPurchasingService;

    /**
     * 查询外购检异登记列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectPurchasing:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaInspectPurchasing quaInspectPurchasing)
    {
        startPage();
        List<QuaInspectPurchasing> list = quaInspectPurchasingService.selectQuaInspectPurchasingList(quaInspectPurchasing);
        return getDataTable(list);
    }

    /**
     * 导出外购检异登记列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectPurchasing:export')")
    @Log(title = "外购检异登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaInspectPurchasing quaInspectPurchasing)
    {
        List<QuaInspectPurchasing> list = quaInspectPurchasingService.selectQuaInspectPurchasingList(quaInspectPurchasing);
        ExcelUtil<QuaInspectPurchasing> util = new ExcelUtil<QuaInspectPurchasing>(QuaInspectPurchasing.class);
        util.exportExcel(response, list, "外购检异登记数据");
    }

    /**
     * 获取外购检异登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectPurchasing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaInspectPurchasingService.selectQuaInspectPurchasingById(id));
    }

    /**
     * 新增外购检异登记
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectPurchasing:add')")
    @Log(title = "外购检异登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaInspectPurchasing quaInspectPurchasing)
    {
        AjaxResult ar = null;
        try{
           int ret = quaInspectPurchasingService.insertQuaInspectPurchasing(quaInspectPurchasing);
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
     * 修改外购检异登记
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectPurchasing:edit')")
    @Log(title = "外购检异登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaInspectPurchasing quaInspectPurchasing)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectPurchasingService.updateQuaInspectPurchasing(quaInspectPurchasing);
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
     * 删除外购检异登记
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectPurchasing:remove')")
    @Log(title = "外购检异登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectPurchasingService.deleteQuaInspectPurchasingByIds(ids);
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

package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialPurchasing;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialPurchasingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料采购维Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/eng/materialPurchasing")
public class EngMaterialPurchasingController extends BaseController
{
    @Autowired
    private IEngMaterialPurchasingService engMaterialPurchasingService;

    /**
     * 查询物料采购维列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialPurchasing:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngMaterialPurchasing engMaterialPurchasing)
    {
        startPage();
        List<EngMaterialPurchasing> list = engMaterialPurchasingService.selectEngMaterialPurchasingList(engMaterialPurchasing);
        return getDataTable(list);
    }

    /**
     * 导出物料采购维列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialPurchasing:export')")
    @Log(title = "物料采购维", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngMaterialPurchasing engMaterialPurchasing)
    {
        List<EngMaterialPurchasing> list = engMaterialPurchasingService.selectEngMaterialPurchasingList(engMaterialPurchasing);
        ExcelUtil<EngMaterialPurchasing> util = new ExcelUtil<EngMaterialPurchasing>(EngMaterialPurchasing.class);
        util.exportExcel(response, list, "物料采购维数据");
    }

    /**
     * 获取物料采购维详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialPurchasing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engMaterialPurchasingService.selectEngMaterialPurchasingById(id));
    }

    /**
     * 新增物料采购维
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialPurchasing:add')")
    @Log(title = "物料采购维", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngMaterialPurchasing engMaterialPurchasing)
    {
        AjaxResult ar = null;
        try{
           int ret = engMaterialPurchasingService.insertEngMaterialPurchasing(engMaterialPurchasing);
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
     * 修改物料采购维
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialPurchasing:edit')")
    @Log(title = "物料采购维", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngMaterialPurchasing engMaterialPurchasing)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialPurchasingService.updateEngMaterialPurchasing(engMaterialPurchasing);
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
     * 删除物料采购维
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialPurchasing:remove')")
    @Log(title = "物料采购维", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialPurchasingService.deleteEngMaterialPurchasingByIds(ids);
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

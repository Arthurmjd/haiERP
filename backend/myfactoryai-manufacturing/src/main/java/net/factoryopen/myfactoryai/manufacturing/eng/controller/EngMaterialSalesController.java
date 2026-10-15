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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialSales;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialSalesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料销售维Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/eng/materialSales")
public class EngMaterialSalesController extends BaseController
{
    @Autowired
    private IEngMaterialSalesService engMaterialSalesService;

    /**
     * 查询物料销售维列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialSales:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngMaterialSales engMaterialSales)
    {
        startPage();
        List<EngMaterialSales> list = engMaterialSalesService.selectEngMaterialSalesList(engMaterialSales);
        return getDataTable(list);
    }

    /**
     * 导出物料销售维列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialSales:export')")
    @Log(title = "物料销售维", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngMaterialSales engMaterialSales)
    {
        List<EngMaterialSales> list = engMaterialSalesService.selectEngMaterialSalesList(engMaterialSales);
        ExcelUtil<EngMaterialSales> util = new ExcelUtil<EngMaterialSales>(EngMaterialSales.class);
        util.exportExcel(response, list, "物料销售维数据");
    }

    /**
     * 获取物料销售维详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialSales:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engMaterialSalesService.selectEngMaterialSalesById(id));
    }

    /**
     * 新增物料销售维
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialSales:add')")
    @Log(title = "物料销售维", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngMaterialSales engMaterialSales)
    {
        AjaxResult ar = null;
        try{
           int ret = engMaterialSalesService.insertEngMaterialSales(engMaterialSales);
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
     * 修改物料销售维
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialSales:edit')")
    @Log(title = "物料销售维", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngMaterialSales engMaterialSales)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialSalesService.updateEngMaterialSales(engMaterialSales);
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
     * 删除物料销售维
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialSales:remove')")
    @Log(title = "物料销售维", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialSalesService.deleteEngMaterialSalesByIds(ids);
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

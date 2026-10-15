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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialParameters;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialParametersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料参数Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/eng/materialParameters")
public class EngMaterialParametersController extends BaseController
{
    @Autowired
    private IEngMaterialParametersService engMaterialParametersService;

    /**
     * 查询物料参数列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialParameters:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngMaterialParameters engMaterialParameters)
    {
        startPage();
        List<EngMaterialParameters> list = engMaterialParametersService.selectEngMaterialParametersList(engMaterialParameters);
        return getDataTable(list);
    }

    /**
     * 导出物料参数列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialParameters:export')")
    @Log(title = "物料参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngMaterialParameters engMaterialParameters)
    {
        List<EngMaterialParameters> list = engMaterialParametersService.selectEngMaterialParametersList(engMaterialParameters);
        ExcelUtil<EngMaterialParameters> util = new ExcelUtil<EngMaterialParameters>(EngMaterialParameters.class);
        util.exportExcel(response, list, "物料参数数据");
    }

    /**
     * 获取物料参数详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialParameters:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engMaterialParametersService.selectEngMaterialParametersById(id));
    }

    /**
     * 新增物料参数
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialParameters:add')")
    @Log(title = "物料参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngMaterialParameters engMaterialParameters)
    {
        AjaxResult ar = null;
        try{
           int ret = engMaterialParametersService.insertEngMaterialParameters(engMaterialParameters);
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
     * 修改物料参数
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialParameters:edit')")
    @Log(title = "物料参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngMaterialParameters engMaterialParameters)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialParametersService.updateEngMaterialParameters(engMaterialParameters);
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
     * 删除物料参数
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialParameters:remove')")
    @Log(title = "物料参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialParametersService.deleteEngMaterialParametersByIds(ids);
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

package net.factoryopen.myfactoryai.manufacturing.eng.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import io.lettuce.core.dynamic.annotation.Param;
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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInfo;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料信息Controller
 *
 * @author jitfactory-generator
 * @date 2024-09-17
 */
@RestController
@RequestMapping("/eng/materialInfo")
public class EngMaterialInfoController extends BaseController
{
    @Autowired
    private IEngMaterialInfoService engMaterialInfoService;

    /**
     * 查询物料信息列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngMaterialInfo engMaterialInfo)
    {
        if(engMaterialInfo!=null && engMaterialInfo.getPageEnable()!=null && engMaterialInfo.getPageEnable()) startPage();
        List<EngMaterialInfo> list = engMaterialInfoService.selectEngMaterialInfoList(engMaterialInfo);
        return getDataTable(list);
    }

    /**
     * 查询物料信息列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:listPurchasing')")
    @GetMapping("/listPurchasing")
    public TableDataInfo listPurchasing(EngMaterialInfo engMaterialInfo)
    {
        if(engMaterialInfo!=null && engMaterialInfo.getPageEnable()!=null && engMaterialInfo.getPageEnable()) startPage();
        List<EngMaterialInfo> list = engMaterialInfoService.selectPurchasingMaterialInfoList(engMaterialInfo);
        return getDataTable(list);
    }

    /**
     * 查询物料信息列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:listProducting')")
    @GetMapping("/listProducting")
    public TableDataInfo listProducting(EngMaterialInfo engMaterialInfo)
    {
        if(engMaterialInfo!=null && engMaterialInfo.getPageEnable()!=null && engMaterialInfo.getPageEnable()) startPage();
        List<EngMaterialInfo> list = engMaterialInfoService.selectProductingMaterialInfoList(engMaterialInfo);
        return getDataTable(list);
    }

    /**
     * 查询物料信息列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:listProducting')")
    @GetMapping("/filterMaterialList")
    public TableDataInfo filterMaterialList(@Param("filter") String filter)
    {
        EngMaterialInfo materialInfo = new EngMaterialInfo();
        materialInfo.setMaterialName(filter);

        startPage();
        List<EngMaterialInfo> list = engMaterialInfoService.filterMaterialList(materialInfo);
        return getDataTable(list);
    }

    /**
     * 导出物料信息列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:export')")
    @Log(title = "物料信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngMaterialInfo engMaterialInfo)
    {
        List<EngMaterialInfo> list = engMaterialInfoService.selectEngMaterialInfoList(engMaterialInfo);
        ExcelUtil<EngMaterialInfo> util = new ExcelUtil<EngMaterialInfo>(EngMaterialInfo.class);
        util.exportExcel(response, list, "物料信息数据");
    }

    /**
     * 获取物料信息详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engMaterialInfoService.selectEngMaterialInfoById(id));
    }

    /**
     * 新增物料信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:add')")
    @Log(title = "物料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngMaterialInfo engMaterialInfo)
    {
        AjaxResult ar = null;
        try{
           int ret = engMaterialInfoService.insertEngMaterialInfo(engMaterialInfo);
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
     * 修改物料信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:edit')")
    @Log(title = "物料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngMaterialInfo engMaterialInfo)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialInfoService.updateEngMaterialInfo(engMaterialInfo);
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
     * 删除物料信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:materialInfo:remove')")
    @Log(title = "物料信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialInfoService.deleteEngMaterialInfoByIds(ids);
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
